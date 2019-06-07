package pe.com.qallarix.movistarcontigo.vacaciones.estado;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;

public class EstadoVacacionesActivity extends AppCompatActivity {
    RecyclerView rvVacaciones;
    List<EstadoVacaciones> estadoVacaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_vacaciones);
        configurarToolbar();
        bindearVistas();
        configurarRecyclerView();
    }

    private void bindearVistas() {
        rvVacaciones = findViewById(R.id.estado_vacaciones_rvListaVacaciones);
    }

    private void configurarRecyclerView() {
        rvVacaciones.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVacaciones.setLayoutManager(layoutManager);
        cargarData();
        EstadoVacacionesAdapter estadoVacacionesAdapter = new EstadoVacacionesAdapter(this, estadoVacaciones, new EstadoVacacionesAdapter.EstadoOnClick() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(EstadoVacacionesActivity.this,DetalleEstadoVacacionesActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        rvVacaciones.setAdapter(estadoVacacionesAdapter);
    }

    private void cargarData(){
        estadoVacaciones = new ArrayList<>();
        estadoVacaciones.add(new EstadoVacaciones("José Eduardo Mendoza Zavaleta","11 de dic - 20 de dic","02/09/2018","11/12/2018",
                "20/12/2018","Está por aprobar las siguiente fechas de vacaciones:","",10,EstadoVacaciones.ESTADO_PENDIENTES));
        estadoVacaciones.add(new EstadoVacaciones("José Eduardo Mendoza Zavaleta","01 de oct - 05 de oct","10/07/2018","01/10/2018",
                "05/10/2018","Aprobó las siguiente fechas de vacaciones:","Tus vacaciones fueron aprobadas el 12/07/2018.",5,EstadoVacaciones.ESTADO_APROBADAS));
        estadoVacaciones.add(new EstadoVacaciones("José Eduardo Mendoza Zavaleta","26 de jul - 30 de jul","01/06/2018","26/07/2018",
                "30/07/2018","Rechazó las siguiente fechas de vacaciones:","Tus vacaciones fueron rechazadas por necesidad operativa.",5,EstadoVacaciones.ESTADO_RECHAZADAS));
        estadoVacaciones.add(new EstadoVacaciones("José Eduardo Mendoza Zavaleta","01 de abr - 15 de abr","01/03/2018","01/04/2018",
                "15/04/2018","Aprobó las siguiente fechas de vacaciones:","Tus vacaciones fueron aprobadas el 05/03/2018.",15,EstadoVacaciones.ESTADO_GOZADAS));
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Estado de vacaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
