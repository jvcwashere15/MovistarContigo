package pe.com.qallarix.movistarcontigo.vacations.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceVacations;
import pe.com.qallarix.movistarcontigo.vacations.AboutVacationsActivity;
import pe.com.qallarix.movistarcontigo.vacations.VacationsActivity;
import pe.com.qallarix.movistarcontigo.vacations.pendings.PendingRequestsActivity;
import pe.com.qallarix.movistarcontigo.vacations.register.pojos.ResponseRegistrarVacaciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterVacationsFinishActivity extends TranquiParentActivity {
    private String fechaInicio = "", fechaFin = "";
    private ImageView ivRespuesta;
    private View viewProgress;
    private TextView tvRespuesta, tvRespuestaDescripcion, tvButtonEstado, tvButtonVolverMenu,
            tvButtonNormativa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_registro);
        bindearVistas();
        getDataFromExtras();
        registrarVacaciones();
        configurarToolbar();
    }

    private void bindearVistas() {
        ivRespuesta = findViewById(R.id.registrar_vacaciones_ivRespuesta);
        tvRespuesta = findViewById(R.id.registrar_vacaciones_tvMensajeRespuesta);
        tvRespuestaDescripcion = findViewById(R.id.registrar_vacaciones_tvMensajeDescripcion);
        tvButtonEstado = findViewById(R.id.registro_vacaciones_btVerEstadoVacaciones);
        tvButtonNormativa = findViewById(R.id.registro_vacaciones_btVerNormativa);
        tvButtonVolverMenu = findViewById(R.id.registro_vacaciones_btVolverAlMenu);
        viewProgress = findViewById(R.id.regitrando_vacaciones_viewProgress);
    }

    private void registrarVacaciones() {
        viewProgress.setVisibility(View.VISIBLE);
        Call<ResponseRegistrarVacaciones> call = WebServiceVacations
                .getInstance(getDocumentNumber())
                .createService(ServiceRegisterVacationsApi.class)
                .registrarVacaciones(getCIP(),fechaInicio,fechaFin);
        call.enqueue(new Callback<ResponseRegistrarVacaciones>() {
            @Override
            public void onResponse(Call<ResponseRegistrarVacaciones> call, Response<ResponseRegistrarVacaciones> response) {
                if (response.code() == 200){
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(date);
                    Analitycs.logEventoRegistroVacaciones(RegisterVacationsFinishActivity.this,strDate);
                    displayMensajeOK();
                }else if (response.code() == 404 || response.code() == 500){
                    displayMensajeError();
                }else {
                    displayMensaje400(response);
                }
                viewProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseRegistrarVacaciones> call, Throwable t) {
                displayMensajeError();
            }
        });
    }

    private void displayMensaje400(Response<ResponseRegistrarVacaciones> response) {
        tvButtonNormativa.setVisibility(View.VISIBLE);
        tvButtonVolverMenu.setVisibility(View.VISIBLE);
        tvRespuesta.setText("Registro inválido");
        ivRespuesta.setImageResource(R.drawable.ic_check_error);
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("exception");
            String m = jsonObject1.getString("exceptionMessage");
            tvRespuestaDescripcion.setText(m);
        } catch (Exception e) {
            e.printStackTrace();
            displayMensajeError();
        }
    }

    private void displayMensajeError() {
        tvButtonEstado.setVisibility(View.VISIBLE);
        tvButtonVolverMenu.setVisibility(View.VISIBLE);
        tvRespuesta.setText("¡Ups!");
        ivRespuesta.setImageResource(R.drawable.img_error_servidor);
        tvRespuestaDescripcion.setText("Hubo un problema con el servidor. " +
                "Estamos trabajando para solucionarlo." +
                "\nPor favor, verifica el estado de tus vacaciones.");
    }

    private void displayMensajeOK() {
        tvRespuesta.setText("Registro exitoso");
        tvButtonEstado.setVisibility(View.VISIBLE);
        tvButtonVolverMenu.setVisibility(View.VISIBLE);
        ivRespuesta.setImageResource(R.drawable.ic_check_ok);
        tvRespuestaDescripcion.setText("Has solicitado tus vacaciones. Te llegará una " +
                "notificación al momento de la aprobación.");
    }

    private void getDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            fechaInicio = extras.getString("fecha_inicio");
            fechaFin = extras.getString("fecha_fin");
        }
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro de vacaciones");
    }


    public void goToPendingRequests(View view) {
        Intent intent = new Intent(RegisterVacationsFinishActivity.this, PendingRequestsActivity.class);
        startActivity(intent);
        finish();
    }

    public void backToVacacionesDashboard(View view) {
        goToVacacionesDashboard();
    }

    public void seeImportantInformation(View view) {
        Intent intent = new Intent(RegisterVacationsFinishActivity.this, AboutVacationsActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToVacacionesDashboard() {
        Intent intent = new Intent(this, VacationsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToVacacionesDashboard();
    }

    public void clickNull(View view) { }
}
