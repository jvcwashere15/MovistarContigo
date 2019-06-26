package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;

public class DetalleAprobacionVacacionesActivity extends AppCompatActivity {

    private List<EstadoVacaciones> estadoVacaciones;
    private int id;
    private TextView
            tvLider,
            tvFechaSolicitud,
            tvFechaInicio,
            tvFechaFin,
            tvDiasSolicitados,
            tvDescripcion;
    private TextView
            tvButtonAprobar,
            tvButtonRechazar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_aprobacion_vacaciones);
        configurarToolbar();
        cargarData();
        id = getIntent().getExtras().getInt("position");
        bindearVistas();
        displayDetalleEstado(id);
//        configurarBotonAprobar(estadoVacaciones.get(id).getLider());
//        configurarBotonRechazar(estadoVacaciones.get(id).getLider());
    }

    private void bindearVistas() {
        tvLider = findViewById(R.id.detalle_aprobacion_tvLider);
        tvDescripcion = findViewById(R.id.detalle_aprobacion_tvDescripcion);
        tvDiasSolicitados = findViewById(R.id.detalle_aprobacion_tvDiasSolicitados);
        tvFechaSolicitud = findViewById(R.id.detalle_aprobacion_tvFechaSolicitud);
        tvFechaFin = findViewById(R.id.detalle_aprobacion_tvFechaFin);
        tvFechaInicio = findViewById(R.id.detalle_aprobacion_tvFechaInicio);
        tvButtonAprobar = findViewById(R.id.detalle_aprobacion_tvButtonAprobar);
        tvButtonRechazar = findViewById(R.id.detalle_aprobacion_tvButtonRechazar);
    }

    private void displayDetalleEstado(int position) {
//        EstadoVacaciones currentEstadoVacaciones = estadoVacaciones.get(position);
//        tvLider.setText(currentEstadoVacaciones.getLider());
//        tvFechaSolicitud.setText(currentEstadoVacaciones.getFechaSolicitud());
//        tvFechaInicio.setText(currentEstadoVacaciones.getFechaInicio());
//        tvFechaFin.setText(currentEstadoVacaciones.getFechaFin());
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
    }

    private void cargarData(){
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aprobación vacaciones");
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

    private void configurarBotonAprobar(final String colaborador) {
//        tvButtonAprobar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(DetalleAprobacionVacacionesActivity.this);
//                builder.setTitle("¿Deseas continuar?");
//                builder.setMessage("Vas a aprobar la solicitud de vacaciones de " + colaborador);
//                builder.setCancelable(false);
//                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(DetalleAprobacionVacacionesActivity.this,FinalizarAprobacionActivity.class);
//                        intent.putExtra("aprobado",1);
//                        intent.putExtra("colaborador",estadoVacaciones.get(id).getLider());
//                        intent.putExtra("dias",estadoVacaciones.get(id).getDiasSolicitados());
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//                builder.setNegativeButton("Ahora no", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                final AlertDialog alertDialog = builder.create();
//                if (!isFinishing()) alertDialog.show();
//            }
//        });
    }

    private void configurarBotonRechazar(final String colaborador) {
//        tvButtonRechazar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(DetalleAprobacionVacacionesActivity.this);
//                builder.setTitle("¿Deseas continuar?");
//                builder.setMessage("Vas a rechazar la solicitud de vacaciones de " + colaborador);
//                builder.setCancelable(false);
//                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(DetalleAprobacionVacacionesActivity.this,FinalizarAprobacionActivity.class);
//                        intent.putExtra("aprobado",0);
//                        intent.putExtra("colaborador",estadoVacaciones.get(id).getLider());
//                        intent.putExtra("dias",estadoVacaciones.get(id).getDiasSolicitados());
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//                builder.setNegativeButton("Ahora no", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                final AlertDialog alertDialog = builder.create();
//                if (!isFinishing()) alertDialog.show();
//            }
//        });
    }
}
