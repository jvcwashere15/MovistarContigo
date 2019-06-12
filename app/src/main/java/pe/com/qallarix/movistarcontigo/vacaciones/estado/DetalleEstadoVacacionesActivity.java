package pe.com.qallarix.movistarcontigo.vacaciones.estado;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;

public class DetalleEstadoVacacionesActivity extends AppCompatActivity {
    private List<EstadoVacaciones> estadoVacaciones;
    private int id;
    private TextView
            tvLider,
            tvEstado,
            tvFechaSolicitud,
            tvFechaInicio,
            tvFechaFin,
            tvDiasSolicitados,
            tvDescripcion,tvDescLider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estado_vacaciones);
        configurarToolbar();
        cargarData();
        id = getIntent().getExtras().getInt("position");
        bindearVistas();
        displayDetalleEstado(id);
    }

    private void bindearVistas() {
        tvLider = findViewById(R.id.detalle_estado_tvLider);
        tvEstado = findViewById(R.id.detalle_estado_tvEstado);
        tvDescripcion = findViewById(R.id.detalle_estado_tvDescripcion);
        tvDescLider = findViewById(R.id.detalle_estado_tvDescLider);
        tvDiasSolicitados = findViewById(R.id.detalle_estado_tvDiasSolicitados);
        tvFechaSolicitud = findViewById(R.id.detalle_estado_tvFechaSolicitud);
        tvFechaFin = findViewById(R.id.detalle_estado_tvFechaFin);
        tvFechaInicio = findViewById(R.id.detalle_estado_tvFechaInicio);
    }

    private void displayDetalleEstado(int position) {
        EstadoVacaciones currentEstadoVacaciones = estadoVacaciones.get(position);
        tvLider.setText(currentEstadoVacaciones.getLider());
        tvFechaSolicitud.setText(currentEstadoVacaciones.getFechaSolicitud());
        tvFechaInicio.setText(currentEstadoVacaciones.getFechaInicio());
        tvFechaFin.setText(currentEstadoVacaciones.getFechaFin());
        tvDescLider.setText(currentEstadoVacaciones.getDescLider());
        tvDiasSolicitados.setText(currentEstadoVacaciones.getDiasSolicitados() + " días");
        tvDescripcion.setText(currentEstadoVacaciones.getDescripcionEstado());
        String strEstado = "";
        int colorEstado = 0;
        switch (currentEstadoVacaciones.getEstado()){
            case EstadoVacaciones.ESTADO_PENDIENTES: strEstado = "PENDIENTES";colorEstado = R.drawable.etiqueta_amarilla;break;
            case EstadoVacaciones.ESTADO_APROBADAS: strEstado = "APROBADAS";colorEstado = R.drawable.etiqueta_verde;break;
            case EstadoVacaciones.ESTADO_GOZADAS: strEstado = "GOZADAS";colorEstado = R.drawable.etiqueta_gris;break;
            case EstadoVacaciones.ESTADO_RECHAZADAS: strEstado = "RECHAZADAS";colorEstado = R.drawable.etiqueta_roja;break;
        }
        tvEstado.setText(strEstado);
        tvEstado.setBackgroundResource(colorEstado);
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
        getSupportActionBar().setTitle("Detalle de solicitud");
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
