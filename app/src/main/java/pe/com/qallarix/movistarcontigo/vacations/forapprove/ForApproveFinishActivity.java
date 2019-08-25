package pe.com.qallarix.movistarcontigo.vacations.forapprove;

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

import okhttp3.ResponseBody;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceVacations;
import pe.com.qallarix.movistarcontigo.vacations.VacationsActivity;
import pe.com.qallarix.movistarcontigo.vacations.forapprove.pojos.RegistroVacaciones;
import pe.com.qallarix.movistarcontigo.vacations.forapprove.pojos.ResponseRegistrarAprobacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForApproveFinishActivity extends TranquiParentActivity {

    TextView tvResultado, tvDescripcion,
            tvButtonBackToVacationsDash, tvButtonGoToForApprove;
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
        setUpToolbar();
        bindViews();
        registerApproveOrReject();
    }

    private void registerApproveOrReject() {
        viewProgress.setVisibility(View.VISIBLE);
        RegistroVacaciones registroVacaciones = new RegistroVacaciones(bossName,bossCode,employeeCode,
                approver,requestCode,requestDateEnd,requestDateStart);

        Call<ResponseRegistrarAprobacion> call = WebServiceVacations
                .getInstance(getDocumentNumber())
                .createService(ServiceForApproveVacationsApi.class)
                .aprobarRechazarSolicitud(registroVacaciones);
        
        call.enqueue(new Callback<ResponseRegistrarAprobacion>() {
            @Override
            public void onResponse(Call<ResponseRegistrarAprobacion> call, Response<ResponseRegistrarAprobacion> response) {
                if (response.code() == 200){
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(date);
                    if (approver)
                        Analitycs.logEventoAprobacionVacaciones(ForApproveFinishActivity.this,strDate,"true");
                    else
                        Analitycs.logEventoAprobacionVacaciones(ForApproveFinishActivity.this,strDate,"false");
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
        tvButtonBackToVacationsDash.setVisibility(View.VISIBLE);
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
        tvButtonBackToVacationsDash.setVisibility(View.VISIBLE);
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

    private void bindViews() {
        ivResultado = findViewById(R.id.aprobacion_vacaciones_ivRespuesta);
        tvResultado = findViewById(R.id.aprobacion_vacaciones_tvMensajeRespuesta);
        tvDescripcion = findViewById(R.id.aprobacion_vacaciones_tvMensajeDescripcion);
        tvButtonGoToForApprove = findViewById(R.id.aprobacion_vacaciones_btVerEstadoVacaciones);
        tvButtonBackToVacationsDash = findViewById(R.id.aprobacion_vacaciones_btVolverVacaciones);
        viewProgress = findViewById(R.id.aprobando_vacaciones_viewProgress);
        viewRespuestaAprobacionRechazo = findViewById(R.id.view_respuesta_aprobacion_rechazo);
    }

    private void displayResultadoRegistroOK() {
        viewRespuestaAprobacionRechazo.setVisibility(View.VISIBLE);
        if (approver){
            tvButtonGoToForApprove.setVisibility(View.VISIBLE);
            tvButtonBackToVacationsDash.setVisibility(View.VISIBLE);
            ivResultado.setImageResource(R.drawable.ic_check_ok);
            tvResultado.setText("Aprobación exitosa");
            tvDescripcion.setText("Has aprobado " + dias + " días de vacaciones a " + colaborador);
        }else{
            tvButtonGoToForApprove.setVisibility(View.VISIBLE);
            tvButtonBackToVacationsDash.setVisibility(View.VISIBLE);
            ivResultado.setImageResource(R.drawable.ic_check_alert);
            tvResultado.setText("Vacaciones denegadas");
            tvDescripcion.setText("Has rechazado la solicitud de " + colaborador + " por necesidad operativa.");
        }
    }

    public void setUpToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de solicitud");
    }

    public void backVacationsForApprove (View view) {
        Intent intent = new Intent(ForApproveFinishActivity.this, ForApproveVacationsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void backToVacationsDashboard(View view) {
        goToVacationsDashBoard();
    }

    private void goToVacationsDashBoard() {
        Intent intent = new Intent(ForApproveFinishActivity.this, VacationsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToVacationsDashBoard();
    }


    public void clickNull(View view) {
    }
}
