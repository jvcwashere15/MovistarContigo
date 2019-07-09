package pe.com.qallarix.movistarcontigo.flexplace.registrar;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONObject;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.AcercaFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.FlexplaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.historial.HistorialFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.pojos.ResponseRegistrarFlexPlace;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalizarRegistroFlexActivity extends TranquiParentActivity {

    private String fechaInicio, fechaFin;
    private int dia;
    private ImageView ivRespuesta;
    private View viewProgress;
    private TextView tvRespuesta, tvRespuestaDescripcion, tvButtonEstado, tvButtonVolverMenu,
            tvButtonNormativa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_registro_flex);
        configurarToolbar();
        bindearVistas();
        getDataFromExtras();
        registrarFlexPlace();

    }

    private void getDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            fechaInicio = extras.getString("fecha_inicio");
            fechaFin = extras.getString("fecha_fin");
            dia = extras.getInt("dia");
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
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        Call<ResponseRegistrarFlexPlace> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceRegistrarApi.class)
                .registrarFlexPlace(getDocumentNumber(),
                        fechaInicio,fechaFin,dia);
        call.enqueue(new Callback<ResponseRegistrarFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseRegistrarFlexPlace> call, Response<ResponseRegistrarFlexPlace> response) {
                if (response.code() == 200){
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


    public void verHistorialFlexPlace(View view) {
        Intent intent =  new Intent(this, HistorialFlexPlaceActivity.class);
        startActivity(intent);
        finish();
    }

    public void verNormativa(View view) {
        Intent intent = new Intent(FinalizarRegistroFlexActivity.this, AcercaFlexPlace.class);
        startActivity(intent);
        finish();
    }

    public void volverMenu(View view) {
        Intent intent = new Intent(FinalizarRegistroFlexActivity.this, FlexplaceActivity.class);
        startActivity(intent);
        finish();
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
                "\nPor favor, verifica el estado de tus vacaciones.");
    }

    private void displayMensajeOK() {
        tvRespuesta.setText("Registro exitoso");
        tvButtonEstado.setVisibility(View.VISIBLE);
        tvButtonVolverMenu.setVisibility(View.VISIBLE);
        ivRespuesta.setImageResource(R.drawable.ic_check_ok);
        tvRespuestaDescripcion.setText("Has solicitado los días a utilizar tu Flex. " +
                "Recibirás una notificación al momento de la aprobación.");
    }
}