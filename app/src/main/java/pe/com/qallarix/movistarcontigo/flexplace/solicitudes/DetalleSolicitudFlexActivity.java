package pe.com.qallarix.movistarcontigo.flexplace.solicitudes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.historial.ServiceFlexplaceHistorialApi;
import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.pojos.DetalleSolicitudFlex;
import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.pojos.ResponseDetalleSolicitudFlex;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService3;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.ResponseDetalleSolicitud;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleSolicitudFlexActivity extends TranquiParentActivity {
    //codigo de la solicitud
    private int requestCode;
    private DetalleSolicitudFlex detalleSolicitudFlex;

    //etiquetas de informacion
    private TextView tvDescLider, tvNombreEmpleado, tvEstadoSolicitudFlex, tvFechaSolicitudFlex,
            tvFechaInicioFlex, tvFechaFinFlex, tvDescripcionSolicitud, tvDiaSolicitado;

    //boton aprobar rechazar
    private TextView tvBotonAprobarRechazar;
    private View viewNotification;
    private TextView tvMensajeNotificacion, tvTituloNotificacion;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
    private ImageView ivMessageImagen;

    private ShimmerFrameLayout mShimmerViewContainer;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_solicitud_flex);
        getDataFromIntent();
        configurarToolbar();
        bindearVistas();
        displayDetalleSolicitudFlexPlace();
//        configurarBotonCancelar();
    }

    private void displayDetalleSolicitudFlexPlace() {
        Call<ResponseDetalleSolicitudFlex> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceSolicitudesApi.class)
                .getSolicitudFlexPlace(requestCode);

        call.enqueue(new Callback<ResponseDetalleSolicitudFlex>() {
            @Override
            public void onResponse(Call<ResponseDetalleSolicitudFlex> call, Response<ResponseDetalleSolicitudFlex> response) {
                if (response.code() == 200){
                    detalleSolicitudFlex =  response.body().getRequest();
                    tvDescLider.setText(detalleSolicitudFlex.getMessageOne());
                    tvNombreEmpleado.setText(detalleSolicitudFlex.getEmployee());

                    tvFechaInicioFlex.setText(detalleSolicitudFlex.getDateStart());
                    tvFechaFinFlex.setText(detalleSolicitudFlex.getDateEnd());

                    tvDescripcionSolicitud.setText(detalleSolicitudFlex.getMessageTwo());
                    tvDiaSolicitado.setText(detalleSolicitudFlex.getDayWeek());

                    if (!TextUtils.isEmpty(detalleSolicitudFlex.getReason())){
                        viewNotification.setVisibility(View.VISIBLE);
                        if (detalleSolicitudFlex.getStatusId().equals(ServiceFlexplaceHistorialApi.RECHAZADO))
                            tvTituloNotificacion.setText("Motivo de rechazo");
                        tvMensajeNotificacion.setText(detalleSolicitudFlex.getReason());
                    }

                    String strEstado = "";
                    int colorEstado = 0;
                    if (detalleSolicitudFlex.getStatusId().equals(ServiceFlexplaceHistorialApi.APROBADO)){
                        strEstado = "APROBADO";colorEstado = R.drawable.etiqueta_verde;
                    }else if (detalleSolicitudFlex.getStatusId().equals(ServiceFlexplaceHistorialApi.PENDIENTE)){
                        strEstado = "PENDIENTE";colorEstado = R.drawable.etiqueta_amarilla;
                    }else if (detalleSolicitudFlex.getStatusId().equals(ServiceFlexplaceHistorialApi.RECHAZADO)){
                        strEstado = "RECHAZADO";colorEstado = R.drawable.etiqueta_roja;
                    }else if (detalleSolicitudFlex.getStatusId().equals(ServiceFlexplaceHistorialApi.CANCELADO)){
                        strEstado = "CANCELADO";colorEstado = R.drawable.etiqueta_morada;
                    }

                    tvFechaSolicitudFlex.setText(detalleSolicitudFlex.getDateRequest());
                    tvEstadoSolicitudFlex.setText(strEstado);
                    tvEstadoSolicitudFlex.setBackgroundResource(colorEstado);


                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseDetalleSolicitudFlex> call, Throwable t) {
                Toast.makeText(DetalleSolicitudFlexActivity.this,
                        "Error en el servicio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindearVistas() {
        tvDescLider = findViewById(R.id.detalle_solicitud_flex_tvDescLider);
        tvNombreEmpleado = findViewById(R.id.detalle_solicitud_flex_tvNombreEmpleado);
        tvEstadoSolicitudFlex = findViewById(R.id.detalle_solicitud_flex_tvEstado);
        tvFechaSolicitudFlex = findViewById(R.id.detalle_solicitud_flex_tvFechaSolicitud);
        tvFechaInicioFlex = findViewById(R.id.detalle_solicitud_flex_tvFechaInicio);
        tvFechaFinFlex = findViewById(R.id.detalle_solicitud_flex_tvFechaFin);
        tvDescripcionSolicitud = findViewById(R.id.detalle_solicitud_flex_tvDescripcion);
        tvDiaSolicitado = findViewById(R.id.detalle_solicitud_flex_tvDiaSolicitado);
        mShimmerViewContainer = findViewById(R.id.detalle_solicitud_flex_shimerFrameLayout);

        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);

        viewNotification = findViewById(R.id.detalle_solicitud_flex_viewNoticacion);
        tvTituloNotificacion = findViewById(R.id.detalle_solicitud_flex_tvTituloNotificacion);
        tvMensajeNotificacion = findViewById(R.id.detalle_solicitud_flex_tvMensajeNotificacion);
    }

    private void getDataFromIntent() {
        requestCode = getIntent().getExtras().getInt("requestCode");
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de FlexPlace");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
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

}
