package pe.com.qallarix.movistarcontigo.salud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.salud.pojos.DataSalud;
import pe.com.qallarix.movistarcontigo.salud.pojos.Plan;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaludActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private RecyclerView rvPlanes;
    private List<Plan> planes;
    private String mDni;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud);
        mDni = getDocumentNumber();
        configurarToolbar();
        configurarRecyclerView();
        cargarPlanes();
    }

    private void cargarPlanes() {
        Call<DataSalud> call = WebService.getInstance(mDni)
                .createService(ServiceSaludApi.class)
                .getListaPlanesSalud();
        progressDialog = ProgressDialog.show(this, "Salud", "Cargando planes de salud...", true);

        call.enqueue(new Callback<DataSalud>() {
            @Override
            public void onResponse(Call<DataSalud> call, Response<DataSalud> response) {

                if (response.code() == 200){
                    planes = response.body().getPlans();
                    SaludAdapter saludAdapter = new SaludAdapter(SaludActivity.this, planes, new SaludAdapter.SaludClick() {
                        @Override
                        public void onClick(View v, int position) {
                            Plan currentPlan = planes.get(position);
                            Intent intent = new Intent(SaludActivity.this,DetalleSaludActivity.class);
                            intent.putExtra("id",currentPlan.getID());
                            intent.putExtra("title",currentPlan.getTitle());
                            startActivity(intent);
                            Analitycs.logEventoClickPlanSalud(SaludActivity.this,currentPlan);
                        }
                    });
                    rvPlanes.setAdapter(saludAdapter);
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                    mostrarMensaje("Se produjo un error al cargar los planes de salud");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DataSalud> call, Throwable t) {
                progressDialog.dismiss();
                mostrarMensaje("Se produjo un error al cargar los planes de salud");
                finish();
            }
        });

    }

    private void configurarRecyclerView() {
        rvPlanes = findViewById(R.id.salud_rvPlanesSalud);
        rvPlanes.setHasFixedSize(true);
        rvPlanes.setLayoutManager(new LinearLayoutManager(this));
    }


    public void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("Salud");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_just_account, menu);
        View view = menu.findItem(R.id.action_account).getActionView();
        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
        tvIniciales.setText(obtenerIniciales());
        tvIniciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaludActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_account:
                Intent intent = new Intent(SaludActivity.this,AccountActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                goToParentActivity();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    private void goToParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(upIntent);
        finish();
    }


    @Override
    public void onBackPressed() {
        goToParentActivity();
    }
}
