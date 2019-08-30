package pe.com.qallarix.movistarcontigo.flexplace.forapprove;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import pe.com.qallarix.movistarcontigo.flexplace.FlexplaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseFinalizarCancelacion;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForApproveFlexFinishProcessActivity extends TranquiParentActivity {

    private String observation, day, employeeName;
    private boolean approve;
    private long idRequest;
    private ImageView ivRespuesta;
    private View viewProgress;
    private TextView tvRespuesta, tvRespuestaDescripcion, tvViewProgressMessage,
            tvButtonEstado, tvButtonVolverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_forapprove_finish_process);
        bindViews();
        setUpToolbar();
        getDataFromExtras();
        setUpViewProgress();
        registerFlexPlace();
    }

    private void setUpViewProgress() {
        if (approve) tvViewProgressMessage.setText("Aprobando Flexplace...");
        else tvViewProgressMessage.setText("Rechazando Flexplace...");
    }

    private void getDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            day = extras.getString("dia","");
            employeeName = extras.getString("nombreEmpleado", "");
            //para realizar la transacción
            idRequest = extras.getLong("idRequest");
            observation = extras.getString("observacion","");
            //boolean para aprobar o rechazar
            approve = extras.getBoolean("aprobar",false);
        }
    }

    public void setUpToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de FlexPlace");
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

    private void goToParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(upIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }


    public void goToFlexPlaceForApprove(View view) {
        goToParentActivity();
    }

    public void backToFlexPlaceDashBoard(View view) {
        Intent intent = new Intent(this, FlexplaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void clickNull(View view) { }

    private void bindViews() {
        ivRespuesta = findViewById(R.id.forapprove_flex_finish_process_ivIcon);
        tvRespuesta = findViewById(R.id.forapprove_flex_finish_process_tvTitle);
        tvRespuestaDescripcion = findViewById(R.id.forapprove_flex_finish_process_tvMessage);
        tvButtonEstado = findViewById(R.id.forapprove_flex_finish_process_tvButtonGoApproveFlex);
        tvButtonVolverMenu = findViewById(R.id.forapprove_flex_finish_process_tvButtonBackToFlex);
        viewProgress = findViewById(R.id.registrar_flexplace_viewProgress);
        tvViewProgressMessage = findViewById(R.id.viewprogress_tvMessageProcess);
    }



    private void registerFlexPlace() {
        viewProgress.setVisibility(View.VISIBLE);
        Call<ResponseFinalizarCancelacion> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceforApproveApi.class)
                .aprobarRechazarFlexPlace((int)idRequest,approve,observation);
        call.enqueue(new Callback<ResponseFinalizarCancelacion>() {
            @Override
            public void onResponse(Call<ResponseFinalizarCancelacion> call, Response<ResponseFinalizarCancelacion> response) {
                if (response.code() == 200){
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(date);
                    if (approve)
                        Analitycs.logEventoAprobacionFlexPlace(ForApproveFlexFinishProcessActivity.this,strDate,"true");
                    else
                        Analitycs.logEventoAprobacionFlexPlace(ForApproveFlexFinishProcessActivity.this,strDate,"false");
                    if (approve) displayMensajeOKAprobado();
                    else displayMensajeOKRechazado();
                }else if (response.code() == 404 || response.code() == 500){
                    displayMensajeError();
                }else {
                    displayMensaje400(response);
                }
                viewProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseFinalizarCancelacion> call, Throwable t) {
                displayMensajeError();
            }
        });
    }



    private void displayMensaje400(Response<ResponseFinalizarCancelacion> response) {
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
                "\nPor favor, verifica el estado de tu FlexPlace.");
    }

    private void displayMensajeOKAprobado() {
        tvRespuesta.setText("Aprobación exitosa");
        tvButtonEstado.setVisibility(View.VISIBLE);
        tvButtonVolverMenu.setVisibility(View.VISIBLE);
        ivRespuesta.setImageResource(R.drawable.ic_check_ok);
        tvRespuestaDescripcion.setText("Has aprobado los " + day + " como FlexPlace para " +
                 employeeName + ".");
    }

    private void displayMensajeOKRechazado() {
        tvRespuesta.setText("FlexPlace denegado");
        tvButtonEstado.setVisibility(View.VISIBLE);
        tvButtonVolverMenu.setVisibility(View.VISIBLE);
        ivRespuesta.setImageResource(R.drawable.ic_check_alert);
        tvRespuestaDescripcion.setText("Has rechazado la solicitud de FlexPlace de " +
                employeeName + ".");
    }
}
