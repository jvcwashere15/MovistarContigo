package pe.com.qallarix.movistarcontigo.flexplace.forapprove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.FlexplaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseFinalizarCancelacion;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForApproveFlexFinishNotificationActivity extends TranquiParentActivity {


    private String employeeName;
    private long idRequest;
    private ImageView ivIconResponse;
    private View viewProgress;
    private TextView tvResponseTitle, tvResponseMessage,
            tvButtonGoToFlexDashboard, tvButtonGoToFlexApproved;

    private final int TAB_APPROVED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_forapprove_finish_notification);
        setUpToolbar();
        bindViews();
        getDataFromExtras();
        notifyCancelation();
    }

    private void getDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            employeeName = extras.getString("nombreEmpleado", "");
            //para realizar la transacción
            idRequest = extras.getLong("idRequest");
        }
    }

    public void setUpToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de FlexPlace");
    }

    public void goToFlexApproved(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(this, ForApproveFlexPlaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void backToFlexPlace(View view) {
        Intent intent =  new Intent(this, FlexplaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void clickNull(View view) {}

    private void displayMensaje400(Response<ResponseFinalizarCancelacion> response) {
        tvResponseTitle.setText("Registro inválido");
        ivIconResponse.setImageResource(R.drawable.ic_check_error);
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("exception");
            String m = jsonObject1.getString("exceptionMessage");
            tvResponseMessage.setText(m);
        } catch (Exception e) {
            e.printStackTrace();
            displayMensajeError();
        }
    }

    private void displayMensajeError() {
        tvButtonGoToFlexApproved.setVisibility(View.VISIBLE);
        tvButtonGoToFlexDashboard.setVisibility(View.VISIBLE);
        tvResponseTitle.setText("¡Ups!");
        ivIconResponse.setImageResource(R.drawable.img_error_servidor);
        tvResponseMessage.setText("Hubo un problema con el servidor. " +
                "Estamos trabajando para solucionarlo." +
                "\nPor favor, verifica el estado de tu FlexPlace.");
    }

    private void displayMensajeOKNotificado() {
        tvButtonGoToFlexApproved.setVisibility(View.VISIBLE);
        tvButtonGoToFlexDashboard.setVisibility(View.VISIBLE);
        tvResponseTitle.setText("Notificación hecha");
        ivIconResponse.setImageResource(R.drawable.ic_check_ok);
        tvResponseMessage.setText("Has enviado una notificación a " +
                employeeName + " para que cancele su FlexPlace.");
    }



    private void bindViews() {
        ivIconResponse = findViewById(R.id.forapprove_flex_finish_notification_ivIcon);
        tvResponseTitle = findViewById(R.id.forapprove_flex_finish_notification_tvTitle);
        tvResponseMessage = findViewById(R.id.forapprove_flex_finish_notification_tvMessage);
        tvButtonGoToFlexApproved = findViewById(R.id.forapprove_flex_finish_notification_tvButtonGoFlexApproved);
        tvButtonGoToFlexDashboard = findViewById(R.id.forapprove_flex_finish_notification_tvButtonBackFlexDash);
        viewProgress = findViewById(R.id.registrar_flexplace_viewProgress);
    }


    private void notifyCancelation() {
        viewProgress.setVisibility(View.VISIBLE);
        Call<ResponseFinalizarCancelacion> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceforApproveApi.class)
                .notificarCancelacionFexPlace((int)idRequest);
        call.enqueue(new Callback<ResponseFinalizarCancelacion>() {
            @Override
            public void onResponse(Call<ResponseFinalizarCancelacion> call, Response<ResponseFinalizarCancelacion> response) {
                if (response.code() == 200){
                    displayMensajeOKNotificado();
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

}
