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
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.RegistroVacaciones;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.ResponseRegistrarAprobacion;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.EstadoVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.FinalizarRegistroActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalizarAprobacionActivity extends TranquiParentActivity {

    TextView tvResultado, tvDescripcion;
    ImageView ivResultado;
    private View viewRespuestaAprobacionRechazo;
    //datos reenviados
    private long dias;
    private String colaborador;
    //data extraIntent
    private String employeeCode, requestCode, requestDateStart,requestDateEnd, bossCode, bossName;
    private boolean approver;
    private View viewProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_aprobacion);
        getDataExtraFromIntent();
        configurarToolbar();
        bindearVistas();
        registrarAprobacionORechazo();
    }

    private void registrarAprobacionORechazo() {
        viewProgress.setVisibility(View.VISIBLE);
        RegistroVacaciones registroVacaciones = new RegistroVacaciones(bossName,bossCode,employeeCode,
                approver,requestCode,requestDateEnd,requestDateStart);

        Call<ResponseRegistrarAprobacion> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceAprobacionVacacionesApi.class)
                .aprobarRechazarSolicitud(registroVacaciones);
        
        call.enqueue(new Callback<ResponseRegistrarAprobacion>() {
            @Override
            public void onResponse(Call<ResponseRegistrarAprobacion> call, Response<ResponseRegistrarAprobacion> response) {
                if (response.code() == 200){
                    displayResultadoRegistroOK();
                }else if (response.code() == 500 || response.code() == 404){
                    displayResultadoError();
                }else {
                    displayResultadoException(response.errorBody());
                }
                viewProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseRegistrarAprobacion> call, Throwable t) {
                displayResultadoError();
            }
        });
    }

    private void displayResultadoException(ResponseBody response) {
        viewRespuestaAprobacionRechazo.setVisibility(View.VISIBLE);
        ivResultado.setImageResource(R.drawable.ic_check_error);
        tvResultado.setText("Registro inválido");
        try {
            JSONObject jsonObject = new JSONObject(response.string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("exception");
            String m = jsonObject1.getString("exceptionMessage");
            tvDescripcion.setText(m);
        } catch (Exception e) {
            e.printStackTrace();
            tvDescripcion.setText("Hubo un problema con el servidor. Estamos trabajando para solucionarlo." +
                    "\nPor favor, verifica tus solicitudes pendientes por aprobar.");
        }

    }

    private void displayResultadoError() {
        viewRespuestaAprobacionRechazo.setVisibility(View.VISIBLE);
        tvResultado.setText("¡Ups!");
        ivResultado.setImageResource(R.drawable.img_error_servidor);
        tvDescripcion.setText("Hubo un problema con el servidor. Estamos trabajando para solucionarlo." +
                "\nPor favor, verifica tus solicitudes pendientes por aprobar.");
    }

    private void getDataExtraFromIntent() {
        approver = getIntent().getExtras().getBoolean("approver");
        employeeCode = getIntent().getExtras().getString("employeeCode");
        requestCode = getIntent().getExtras().getString("requestCode");
        dias = getIntent().getExtras().getLong("dias");
        colaborador = getIntent().getExtras().getString("colaborador");
        requestDateStart = getIntent().getExtras().getString("requestDateStart");
        requestDateEnd = getIntent().getExtras().getString("requestDateEnd");
        bossCode = getIntent().getExtras().getString("bossCode");
        bossName = getIntent().getExtras().getString("bossName");
    }

    private void bindearVistas() {
        ivResultado = findViewById(R.id.aprobacion_vacaciones_ivRespuesta);
        tvResultado = findViewById(R.id.aprobacion_vacaciones_tvMensajeRespuesta);
        tvDescripcion = findViewById(R.id.aprobacion_vacaciones_tvMensajeDescripcion);
        viewProgress = findViewById(R.id.aprobando_vacaciones_viewProgress);
        viewRespuestaAprobacionRechazo = findViewById(R.id.view_respuesta_aprobacion_rechazo);
    }

    private void displayResultadoRegistroOK() {
        viewRespuestaAprobacionRechazo.setVisibility(View.VISIBLE);
        if (approver){
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


    public void clickNull(View view) {
    }
}
