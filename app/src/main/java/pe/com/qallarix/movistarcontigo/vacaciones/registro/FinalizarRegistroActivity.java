package pe.com.qallarix.movistarcontigo.vacaciones.registro;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.vacaciones.VacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.EstadoVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.ResponseRegistrarVacaciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalizarRegistroActivity extends TranquiParentActivity {
    private String fechaInicio = "", fechaFin = "";
    private ImageView ivRespuesta;
    private View viewProgress;
    private TextView tvRespuesta, tvRespuestaDescripcion, tvButtonEstado, tvButtonVolverMenu,
            tvButtonNormativa, tvButtonCargarNuevamente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_registro);
        bindearVistas();
        getDataFromExtras();
        registrarVacaciones();
    }

    private void bindearVistas() {
        ivRespuesta = findViewById(R.id.registrar_vacaciones_ivRespuesta);
        tvRespuesta = findViewById(R.id.registrar_vacaciones_tvMensajeRespuesta);
        tvRespuestaDescripcion = findViewById(R.id.registrar_vacaciones_tvMensajeDescripcion);
        tvButtonCargarNuevamente = findViewById(R.id.registro_vacaciones_btCargarNuevamente);
        tvButtonEstado = findViewById(R.id.registro_vacaciones_btVerEstadoVacaciones);
        tvButtonNormativa = findViewById(R.id.registro_vacaciones_btVerNormativa);
        tvButtonVolverMenu = findViewById(R.id.registro_vacaciones_btVolverAlMenu);
        viewProgress = findViewById(R.id.regitrando_vacaciones_viewProgress);
    }

    private void registrarVacaciones() {
        viewProgress.setVisibility(View.VISIBLE);
        Call<ResponseRegistrarVacaciones> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceRegistrarVacacionesApi.class)
                .registrarVacaciones(getCIP(),fechaInicio,fechaFin);
        call.enqueue(new Callback<ResponseRegistrarVacaciones>() {
            @Override
            public void onResponse(Call<ResponseRegistrarVacaciones> call, Response<ResponseRegistrarVacaciones> response) {
                if (response.code() == 200){
                    tvRespuesta.setText("Registro exitoso");
                    tvButtonVolverMenu.setVisibility(View.VISIBLE);
                    ivRespuesta.setImageResource(R.drawable.ic_check_ok);
                    tvRespuestaDescripcion.setText("Has solicitado tus vacaciones. Recibirás una " +
                            "notificación al momento de la aprobación.");
                }else if (response.code() == 400){
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
                    }

                }else if (response.code() == 500){
                    tvButtonCargarNuevamente.setVisibility(View.VISIBLE);
                    tvRespuesta.setText("¡Ups!");
                    ivRespuesta.setImageResource(R.drawable.img_error_servidor);
                    Toast.makeText(FinalizarRegistroActivity.this, "Error servidor", Toast.LENGTH_SHORT).show();
                    //TODO mensaje error servidor
                }
                viewProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseRegistrarVacaciones> call, Throwable t) {
                Toast.makeText(FinalizarRegistroActivity.this, "Error del servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro de vacaciones");
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


    public void verEstadoVacaciones(View view) {
        Intent intent = new Intent(FinalizarRegistroActivity.this, EstadoVacacionesActivity.class);
        startActivity(intent);
        finish();
    }

    public void verNormativa(View view) {

    }

    public void volverMenu(View view) {
        Intent intent = new Intent(FinalizarRegistroActivity.this, VacacionesActivity.class);
        startActivity(intent);
        finish();
    }

    public void cargarNuevamente(View view) {

    }

    public void clickNull(View view) {
    }
}
