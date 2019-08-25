package pe.com.qallarix.movistarcontigo.flexplace.register;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import pe.com.qallarix.movistarcontigo.flexplace.history.HistoryFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.register.pojos.ResponseRegistrarFlexPlace;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalizarRegistroFlexActivity extends TranquiParentActivity {

    private String startDate, dayFlexplace;
    private int monthTaked;
    private ImageView ivRespuesta;
    private View viewProgress;
    private TextView tvRespuesta, tvRespuestaDescripcion, tvButtonEstado, tvButtonVolverMenu,
            tvButtonNormativa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_finish_register);
        getDataFromExtras();
        configurarToolbar();
        bindearVistas();
        registrarFlexPlace();

    }

    private void getDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            startDate = extras.getString("startDate");
            monthTaked = extras.getInt("monthTaked");
            dayFlexplace = extras.getString("dayFlexplace");
        }
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro de FlexPlace");
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
        Call<ResponseRegistrarFlexPlace> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceRegistrarApi.class)
                .registrarFlexPlace(startDate,monthTaked);
        call.enqueue(new Callback<ResponseRegistrarFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseRegistrarFlexPlace> call, Response<ResponseRegistrarFlexPlace> response) {
                if (response.code() == 200){
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(date);
                    Analitycs.logEventoRegistroFlexPlace(FinalizarRegistroFlexActivity.this,strDate);
                    displayMensajeOK();
                }else if (response.code() == 404 || response.code() == 500){
                    displayMensajeError();
                }else {
                    displayMensaje400(response);
                }
                viewProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseRegistrarFlexPlace> call, Throwable t) {
                displayMensajeError();
            }
        });
    }



    public void verNormativa(View view) {
        Intent intent = new Intent(FinalizarRegistroFlexActivity.this, AboutFlexPlaceActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToFlexPlaceHistory(View view) {
        Intent intent =  new Intent(this, HistoryFlexPlaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void backToFlexPlace(View view) {
        goToParentActivity();
    }

    public void clickNull(View view) {
    }


    private void displayMensaje400(Response<ResponseRegistrarFlexPlace> response) {
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
        tvRespuesta.setText("Registro exitoso");
        tvButtonEstado.setVisibility(View.VISIBLE);
        tvButtonVolverMenu.setVisibility(View.VISIBLE);
        ivRespuesta.setImageResource(R.drawable.ic_check_ok);
        tvRespuestaDescripcion.setText("Has solicitado los " + dayFlexplace.toLowerCase() + " como tu día Flex. Recibirás una notificación al momento de la aprobación.");
    }


}
