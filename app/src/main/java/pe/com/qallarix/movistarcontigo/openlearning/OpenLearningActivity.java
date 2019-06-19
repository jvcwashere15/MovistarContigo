package pe.com.qallarix.movistarcontigo.openlearning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.openlearning.pojos.OpenLearningItem;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;

public class OpenLearningActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private OpenLearningAdapter openLearningAdapter;
    private List<OpenLearningItem> openLearningItems;
    private RecyclerView rvOpenLearning;
    private Button btnAcceder;
    private String mDni;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_learning);
        mDni = getDocumentNumber();
        configurarToolbar();
        configurarRecyclerView();
        configurarBotonAcceder();
        cargaData();
        cargarRecyclerView();
    }

    private void configurarBotonAcceder() {
        btnAcceder = findViewById(R.id.open_learning_btGestionarAqui);
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.movistaropenlearning.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void cargaData() {
        openLearningItems = new ArrayList<>();
        openLearningItems.add(new OpenLearningItem("Convenios educativos","Conoce los convenios y descuentos con reconocidas instituciones educativas que tienes como colaborador de Telefónica."));
        openLearningItems.add(new OpenLearningItem("Cursos virtuales","Realiza cursos en línea que ayuden a tu desarrollo profesional en las plataformas corporativas y externas asociadas."));
        openLearningItems.add(new OpenLearningItem("Recursos formativos","Encuentra papers, infografías y artículos sobre transformación, agilidad y temas relevantes al negocio."));
    }


    private void cargarRecyclerView() {
        openLearningAdapter = new OpenLearningAdapter(OpenLearningActivity.this,openLearningItems);
        rvOpenLearning.setAdapter(openLearningAdapter);
    }

    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Open Learning");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void configurarRecyclerView(){
        rvOpenLearning = findViewById(R.id.open_learning_recycler);
        layoutManager = new LinearLayoutManager(this);
        rvOpenLearning.setHasFixedSize(true);
        rvOpenLearning.setLayoutManager(layoutManager);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_just_account, menu);
//        View view = menu.findItem(R.id.action_account).getActionView();
//        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
//        tvIniciales.setText(obtenerIniciales());
//        tvIniciales.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OpenLearningActivity.this,AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_account:
//                Intent intent = new Intent(OpenLearningActivity.this,AccountActivity.class);
//                startActivity(intent);
//                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    public String getDocumentNumber(){
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        return sharedPreferences.getString("documentNumber","");
    }
}
