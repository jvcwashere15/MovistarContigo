package pe.com.qallarix.movistarcontigo.vacaciones.registro;

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
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceVacaciones;
import pe.com.qallarix.movistarcontigo.vacaciones.AcercaActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.pendientes.PendientesVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.ResponseRegistrarVacaciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalizarRegistroActivity extends TranquiParentActivity {
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
        Call<ResponseRegistrarVacaciones> call = WebServiceVacaciones
                .getInstance(getDocumentNumber())
                .createService(ServiceRegistrarVacacionesApi.class)
                .registrarVacaciones(getCIP(),fechaInicio,fechaFin);
        call.enqueue(new Callback<ResponseRegistrarVacaciones>() {
            @Override
            public void onResponse(Call<ResponseRegistrarVacaciones> call, Response<ResponseRegistrarVacaciones> response) {
                if (response.code() == 200){
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = dateFormat.format(date);
                    Analitycs.logEventoRegistroVacaciones(FinalizarRegistroActivity.this,strDate);
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
        tvRespuestaDescripcion.setText("Has solicitado tus vacaciones. " +
                "Recibirás una notificación a tu correo corporativo al momento de la aprobación.");
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


    public void verEstadoVacaciones(View view) {
        Intent intent = new Intent(FinalizarRegistroActivity.this, PendientesVacacionesActivity.class);
        startActivity(intent);
        finish();
    }

    public void verNormativa(View view) {
        Intent intent = new Intent(FinalizarRegistroActivity.this, AcercaActivity.class);
        startActivity(intent);
        finish();
    }

    public void volverMenu(View view) {
        goToParentActivity();
    }

    public void clickNull(View view) {
    }


}
