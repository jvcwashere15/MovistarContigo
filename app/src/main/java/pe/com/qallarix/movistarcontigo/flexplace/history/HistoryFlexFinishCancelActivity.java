package pe.com.qallarix.movistarcontigo.flexplace.history;

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
import pe.com.qallarix.movistarcontigo.flexplace.AboutFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.FlexplaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseFinalizarCancelacion;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFlexFinishCancelActivity extends TranquiParentActivity {

    private String fechaInicio, fechaFin, observacion, statusId;
    private long idRequest;
    private ImageView ivRespuesta;
    private View viewProgress;
    private TextView tvRespuesta, tvRespuestaDescripcion, tvButtonEstado, tvButtonVolverMenu,
            tvButtonNormativa;

    private int typeRequest = HistoryFlexPlaceActivity.AWAITING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_history_finish_cancel);
        configurarToolbar();
        bindearVistas();
        getDataFromExtras();
        registrarFlexPlace();
    }

    private void getDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            //para mostrar mensaje de cancelación
            fechaInicio = extras.getString("fecha_inicio","");
            fechaFin = extras.getString("fecha_fin","");
            //para realizar la transacción
            idRequest = extras.getLong("idRequest",0);
            observacion = extras.getString("observacion","");
            statusId = extras.getString("statusId",ServiceFlexPlaceHistoryApi.PENDIENTE);
            if (statusId.equals(ServiceFlexPlaceHistoryApi.APROBADO))
                typeRequest = HistoryFlexPlaceActivity.APPROVED;
            else
                typeRequest = HistoryFlexPlaceActivity.AWAITING;

        }
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cancelar FlexPlace");
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
        upIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        upIntent.putExtra("initTab",typeRequest);
        startActivity(upIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }

    private void bindearVistas() {
        ivRespuesta = findViewById(R.id.registrar_flexplace_ivRespuesta);
        tvRespuesta = findViewById(R.id.registrar_flexplace_tvMensajeRespuesta);
        tvRespuestaDescripcion = findViewById(R.id.registrar_flexplace_tvMensajeDescripcion);
        tvButtonEstado = findViewById(R.id.registrar_flexplace_btVerHistorialFlexplace);
        tvButtonNormativa = findViewById(R.id.registrar_flexplace_btVerNormativa);
        tvButtonVolverMenu = findViewById(R.id.registrar_flexplace_btVolverAlMenu);
        viewProgress = findViewById(R.id.registrar_flexplace_viewProgress);
    }



    private void registrarFlexPlace() {
        viewProgress.setVisibility(View.VISIBLE);
        Call<ResponseFinalizarCancelacion> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexPlaceHistoryApi.class)
                .finalizarCancelacionFlexPlace(observacion,(int)idRequest);
        call.enqueue(new Callback<ResponseFinalizarCancelacion>() {
            @Override
            public void onResponse(Call<ResponseFinalizarCancelacion> call, Response<ResponseFinalizarCancelacion> response) {
                if (response.code() == 200){
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(date);
                    Analitycs.logEventoCancelacionFlexPlace(HistoryFlexFinishCancelActivity.this,strDate);
                    displayMensajeOK();
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


    public void goToFlexPlaceHistory(View view) {
//        Intent intent =  new Intent(this, HistoryFlexPlaceActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra("initTab",typeRequest);
//        startActivity(intent);
//        finish();
        goToParentActivity();
    }

    public void verNormativa(View view) {
        Intent intent = new Intent(HistoryFlexFinishCancelActivity.this, AboutFlexPlaceActivity.class);
        startActivity(intent);
        finish();
    }

    public void backToFlexPlace(View view) {
        Intent intent = new Intent(HistoryFlexFinishCancelActivity.this, FlexplaceActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickNull(View view) {
    }


    private void displayMensaje400(Response<ResponseFinalizarCancelacion> response) {
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
                "\nPor favor, verifica el estado de tu FlexPlace.");
    }

    private void displayMensajeOK() {
        tvRespuesta.setText("Cancelación hecha");
        tvButtonEstado.setVisibility(View.VISIBLE);
        tvButtonVolverMenu.setVisibility(View.VISIBLE);
        ivRespuesta.setImageResource(R.drawable.ic_check_alert);
        String mensaje = "";
        if (statusId.equals(ServiceFlexPlaceHistoryApi.PENDIENTE)){
            mensaje = "Has cancelado la solicitud de FlexPlace del "
                    + fechaInicio + " al " + fechaFin + ".";
        }else{
           mensaje = "Has cancelado tu FlexPlace en curso del " +
                   fechaInicio + " al " + fechaFin + ".";
        }
        tvRespuestaDescripcion.setText(mensaje);
    }
}
