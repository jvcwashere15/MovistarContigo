package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.EstadoVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.FinalizarRegistroActivity;

public class FinalizarAprobacionActivity extends AppCompatActivity {

    TextView tvResultado, tvDescripcion;
    ImageView ivResultado;
    private int aprobado = 0,dias;
    private String colaborador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_aprobacion);
        aprobado = getIntent().getExtras().getInt("aprobado");
        dias = getIntent().getExtras().getInt("dias");
        colaborador = getIntent().getExtras().getString("colaborador");
        configurarToolbar();
        bindearVistas();
        displayResultado();
    }

    private void bindearVistas() {
        ivResultado = findViewById(R.id.aprobacion_vacaciones_ivRespuesta);
        tvResultado = findViewById(R.id.aprobacion_vacaciones_tvMensajeRespuesta);
        tvDescripcion = findViewById(R.id.aprobacion_vacaciones_tvMensajeDescripcion);
    }

    private void displayResultado() {
        if (aprobado == 1){
            ivResultado.setImageResource(R.drawable.ic_check_ok);
            tvResultado.setText("Aprobación exitosa");
            tvDescripcion.setText("Has aprobado " + dias + " días de vacaciones a " + colaborador);
        }else{
            ivResultado.setImageResource(R.drawable.ic_check_alert);
            tvResultado.setText("Vacaciones denegadas");
            tvDescripcion.setText("Has rechazado la solicitud de " + colaborador + " por necesidad operativa.");
        }
    }

    public void verSolicitudesPendientes(View view) {
        Intent intent = new Intent(FinalizarAprobacionActivity.this, AprobacionVacacionesActivity.class);
        startActivity(intent);
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aprobación de vacaciones");
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
