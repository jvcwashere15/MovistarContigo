package pe.com.qallarix.movistarcontigo.openlearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.discounts.ServiceDiscountApi;
import pe.com.qallarix.movistarcontigo.openlearning.pojos.Categorias;
import pe.com.qallarix.movistarcontigo.openlearning.pojos.Category;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasActivity extends AppCompatActivity {

    private String mDni;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private int tipoFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        tipoFiltro = getIntent().getExtras().getInt("tipo_descuento");
        mDni = getDocumentNumber();
        configurarToolbar();
        cargarRecyclerViewCategorias();

    }

    private void cargarRecyclerViewCategorias(){
        final RecyclerView rvCategorias = findViewById(R.id.categorias_rvCategorias);
        rvCategorias.setLayoutManager(new GridLayoutManager(this,3));
        Call<Categorias> call = getRequestCategoria();
        progressDialog = ProgressDialog.show(this, "Categorías Descuento", "Cargando categorías...", true);
        call.enqueue(new Callback<Categorias>() {
            @Override
            public void onResponse(Call<Categorias> call, Response<Categorias> response) {
                if (response.code() == 200){
                    final List<Category> categories = response.body().getCategories();
                    CategoriasAdapter categoriasAdapter = new CategoriasAdapter(categories, CategoriasActivity.this, new CategoriasAdapter.OnClickCategoria() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result",categories.get(position).getCode());
                            returnIntent.putExtra("icon",categories.get(position).getImageUrl());
                            returnIntent.putExtra("title",categories.get(position).getDescription());
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                    });
                    rvCategorias.setAdapter(categoriasAdapter);
                }else{
                    Toast.makeText(CategoriasActivity.this, "Error al intentar obtener categorías", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Categorias> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CategoriasActivity.this, "Error al intentar obtener categorías", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private Call<Categorias> getRequestCategoria() {
        if (tipoFiltro == 2)
            return WebService1.getInstance(mDni)
                .createService(ServiceEstudioApi.class)
                .getCategorias();
        return WebService1.getInstance(mDni)
                .createService(ServiceDiscountApi.class)
                .getCategorias();
    }

    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Filtrar por categoría");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public String getDocumentNumber(){
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        return sharedPreferences.getString("documentNumber","");
    }
}
