package pe.com.qallarix.movistarcontigo.vacations.pendings;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceVacations;
import pe.com.qallarix.movistarcontigo.vacations.pendings.pojos.DetalleVacaciones;
import pe.com.qallarix.movistarcontigo.vacations.pendings.pojos.EstadoVacaciones;
import pe.com.qallarix.movistarcontigo.vacations.pendings.pojos.ResponseDetalleSolicitud;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingRequestsDetailActivity extends TranquiParentActivity {
    private List<EstadoVacaciones> estadoVacaciones;
    private TextView
            tvLider,
            tvEstado,
            tvFechaInicio,
            tvFechaFin,
            tvDiasSolicitados,
            tvDescripcion,tvDescLider;
    private ShimmerFrameLayout mShimmerViewContainer;
    private String requestCode;
    private boolean isLoading;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
    private ImageView ivMessageImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estado_vacaciones);
        configurarToolbar();
        requestCode = getIntent().getExtras().getString("requestCode");

        bindearVistas();
        displayDetalleEstado();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoading)
            mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    private void bindearVistas() {
        tvLider = findViewById(R.id.detalle_estado_tvLider);
        tvEstado = findViewById(R.id.detalle_estado_tvEstado);
        tvDescripcion = findViewById(R.id.detalle_estado_tvDescripcion);
        tvDescLider = findViewById(R.id.detalle_estado_tvDescLider);
        tvDiasSolicitados = findViewById(R.id.detalle_estado_tvDiasSolicitados);
        tvFechaFin = findViewById(R.id.detalle_estado_tvFechaFin);
        tvFechaInicio = findViewById(R.id.detalle_estado_tvFechaInicio);
        mShimmerViewContainer = findViewById(R.id.detalle_vacaciones_shimerFrameLayout);
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
    }

    private void displayDetalleEstado() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        Call<ResponseDetalleSolicitud> call = WebServiceVacations
                .getInstance(getDocumentNumber())
                .createService(ServicePendingRequestsVacationApi.class)
                .obtenerDetalleVacaciones(getCIP(),requestCode);
        call.enqueue(new Callback<ResponseDetalleSolicitud>() {
            @Override
            public void onResponse(Call<ResponseDetalleSolicitud> call, Response<ResponseDetalleSolicitud> response) {
                if (response.code() == 200){
                    DetalleVacaciones currentDetalle = response.body().getRequest();
                    String descripcionLider = "";
                    tvFechaInicio.setText(currentDetalle.getRequestDateStart());
                    tvFechaFin.setText(currentDetalle.getRequestDateEnd());
                    tvDiasSolicitados.setText(currentDetalle.getRequestDaysDifference() + " días");
                    String strEstado = "";
                    int colorEstado = 0;
                    if (currentDetalle.getRequestStateCode()
                            .equals(ServicePendingRequestsVacationApi.APROBADAS)){
                        if (TextUtils.isEmpty(currentDetalle.getBossFirstName())){
                            tvLider.setText("TUS VACACIONES FUERON APROBADAS");
                            descripcionLider = "Comunícate con el CAE para cualquier consulta.";

                        }else{
                            tvLider.setText(currentDetalle.getBossFirstName());
                            descripcionLider = "Aprobó las siguientes fechas de vacaciones:";
                        }
                        strEstado = "APROBADAS";colorEstado = R.drawable.etiqueta_verde;

                    }else if (currentDetalle.getRequestStateCode()
                            .equals(ServicePendingRequestsVacationApi.PENDIENTES)){
                        if (TextUtils.isEmpty(currentDetalle.getBossFirstName())){
                            tvLider.setText("TUS VACACIONES FUERON SOLICITADAS");
                            descripcionLider = "Comunícate con el CAE para cualquier consulta.";

                        }else{
                            tvLider.setText(currentDetalle.getBossFirstName());
                            descripcionLider = "Está por aprobar las siguientes fechas de vacaciones:";
                        }
                        strEstado = "PENDIENTES";colorEstado = R.drawable.etiqueta_amarilla;
                    }else if (currentDetalle.getRequestStateCode()
                            .equals(ServicePendingRequestsVacationApi.RECHAZADAS)){
                        if (TextUtils.isEmpty(currentDetalle.getBossFirstName())){
                            tvLider.setText("TUS VACACIONES FUERON SOLICITADAS");
                            descripcionLider = "Comunícate con el CAE para cualquier consulta.";
                        }else{
                            tvLider.setText(currentDetalle.getBossFirstName());
                            descripcionLider = "Rechazó las siguientes fechas de vacaciones:";
                        }
                        tvDescripcion.setText("Tus vacaciones fueron rechazadas por necesidad operativa.");
                        strEstado = "RECHAZADAS";colorEstado = R.drawable.etiqueta_roja;
                    }
                    tvDescLider.setText(descripcionLider);
                    tvEstado.setText(strEstado);
                    tvEstado.setBackgroundResource(colorEstado);

                }else{
                    mostrarMensaje500();
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseDetalleSolicitud> call, Throwable t) {
                mostrarMensaje500();
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }
        });


    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de solicitud");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }

    private void mostrarMensaje500() {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.img_error_servidor);
        tvMessageTitle.setText("¡Ups!");
        tvMessageMensaje.setText("Hubo un problema con el servidor. " +
                "Estamos trabajando para solucionarlo.");
        tvMessageBoton.setVisibility(View.VISIBLE);
        tvMessageBoton.setText("Cargar nuevamente");
        tvMessageBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDetalleEstado();
            }
        });
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
}
