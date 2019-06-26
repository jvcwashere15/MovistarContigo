package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;

public class DetalleSolicitudAprobadaActivity extends AppCompatActivity {

    private List<EstadoVacaciones> aprobadas;
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
        setContentView(R.layout.activity_detalle_solicitud_aprobada);
        configurarToolbar();
        cargaData();
        id = getIntent().getExtras().getInt("position");
        bindearVistas();
        displayDetalleEstado(id);
    }

    private void bindearVistas() {
        tvLider = findViewById(R.id.detalle_solicitud_aprobada_tvLider);
        tvEstado = findViewById(R.id.detalle_solicitud_aprobada_tvEstado);
        tvDescripcion = findViewById(R.id.detalle_solicitud_aprobada_tvDescripcion);
        tvDescLider = findViewById(R.id.detalle_solicitud_aprobada_tvDescLider);
        tvDiasSolicitados = findViewById(R.id.detalle_solicitud_aprobada_tvDiasSolicitados);
        tvFechaSolicitud = findViewById(R.id.detalle_solicitud_aprobada_tvFechaSolicitud);
        tvFechaFin = findViewById(R.id.detalle_solicitud_aprobada_tvFechaFin);
        tvFechaInicio = findViewById(R.id.detalle_solicitud_aprobada_tvFechaInicio);
    }

    private void displayDetalleEstado(int position) {
//        EstadoVacaciones currentEstadoVacaciones = aprobadas.get(position);
//        tvLider.setText(currentEstadoVacaciones.getLider());
//        tvFechaSolicitud.setText(currentEstadoVacaciones.getFechaSolicitud());
//        tvFechaInicio.setText(currentEstadoVacaciones.getFechaInicio());
//        tvFechaFin.setText(currentEstadoVacaciones.getFechaFin());
//        tvDescLider.setText(currentEstadoVacaciones.getDescLider());
//        tvDiasSolicitados.setText(currentEstadoVacaciones.getDiasSolicitados() + " días");
//        tvDescripcion.setText(currentEstadoVacaciones.getDescripcionEstado());
//        String strEstado = "";
//        int colorEstado = 0;
//        switch (currentEstadoVacaciones.getEstado()){
//            case EstadoVacaciones.ESTADO_PENDIENTES: strEstado = "PENDIENTES";colorEstado = R.drawable.etiqueta_amarilla;break;
//            case EstadoVacaciones.ESTADO_APROBADAS: strEstado = "APROBADAS";colorEstado = R.drawable.etiqueta_verde;break;
//            case EstadoVacaciones.ESTADO_GOZADAS: strEstado = "GOZADAS";colorEstado = R.drawable.etiqueta_gris;break;
//            case EstadoVacaciones.ESTADO_RECHAZADAS: strEstado = "RECHAZADAS";colorEstado = R.drawable.etiqueta_roja;break;
//        }
//        tvEstado.setText(strEstado);
//        tvEstado.setBackgroundResource(colorEstado);
    }

    private void cargaData() {
        aprobadas = new ArrayList<>();

//        aprobadas.add(new EstadoVacaciones("Diego Armando Pachioni Retamozo","11 de dic - 20 de dic","20/05/2019","11/12/2019",
//                "20/12/2019","Aprobaste las siguientes fechas a:" ,"Esta solicitud la aprobaste el 09/01/2019.",10,EstadoVacaciones.ESTADO_APROBADAS));
//
//        aprobadas.add(new EstadoVacaciones("Maria Elena Retamozo Arrieta","01 de oct - 05 de oct","10/05/2019","01/10/2019",
//                "05/10/2019","Aprobaste las siguientes fechas a:","Esta solicitud la aprobaste el 09/01/2019.",5,EstadoVacaciones.ESTADO_APROBADAS));
//
//        aprobadas.add(new EstadoVacaciones("Carlos Franco Vilchez Roque","26 de jul - 30 de jul","11/02/2019","26/07/2019",
//                "30/07/2019","Aprobaste las siguientes fechas a:","Esta solicitud la aprobaste el 09/01/2019.",5,EstadoVacaciones.ESTADO_GOZADAS));
//
//        aprobadas.add(new EstadoVacaciones("Jorge Juan Benavides Gomez","01 de abr - 15 de abr","20/01/2019","01/04/2019",
//                "15/04/2019","Aprobaste las siguientes fechas a:","Esta solicitud la aprobaste el 09/01/2019.",15,EstadoVacaciones.ESTADO_GOZADAS));

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
                onBackPressed();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
