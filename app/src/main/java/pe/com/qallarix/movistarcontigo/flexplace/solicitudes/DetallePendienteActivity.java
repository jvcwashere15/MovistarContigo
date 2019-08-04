package pe.com.qallarix.movistarcontigo.flexplace.solicitudes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.pojos.DetalleSolicitudFlex;
import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.pojos.ResponseDetalleSolicitudFlex;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePendienteActivity extends TranquiParentActivity {

    //codigo de la solicitud
    private int requestCode;
    private DetalleSolicitudFlex detalleSolicitudFlex;

    //etiquetas de informacion
    private TextView tvDescLider, tvNombreEmpleado, tvFechaSolicitudFlex,
            tvFechaInicioFlex, tvFechaFinFlex, tvDescripcionSolicitud, tvDiaSolicitado;

    //boton aprobar rechazar
    private TextView tvBotonAprobar, tvBotonRechazar;


    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
    private ImageView ivMessageImagen;

    private ShimmerFrameLayout mShimmerViewContainer;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pendiente);
        getDataFromIntent();
        configurarToolbar();
        bindearVistas();
        displayDetalleSolicitudFlexPlace();
        configurarBotonAprobar();
        configurarBotonRechazar();
        configurarBotonNotificar();
    }

    private void configurarBotonNotificar() {
        //todo boton notificar
    }

    private void configurarBotonAprobar() {
        tvBotonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogAprobacionFlexPlace(detalleSolicitudFlex.getEmployee());
            }
        });
    }

    private void configurarBotonRechazar() {
        tvBotonRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallePendienteActivity.this,
                        RechazoSolicitudFlexActivity.class);
                intent.putExtra("fechaSolicitud",detalleSolicitudFlex.getDateRequest());
                intent.putExtra("fechaInicio",detalleSolicitudFlex.getDateStart());
                intent.putExtra("nombreEmpleado",detalleSolicitudFlex.getEmployee());
                intent.putExtra("fechaFin",detalleSolicitudFlex.getDateEnd());
                intent.putExtra("dia",detalleSolicitudFlex.getDayWeek());
                intent.putExtra("idRequest",detalleSolicitudFlex.getId());
                startActivity(intent);
            }
        });
    }

    private void displayDetalleSolicitudFlexPlace() {
        Call<ResponseDetalleSolicitudFlex> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceSolicitudesApi.class)
                .getSolicitudFlexPlace(requestCode);

        call.enqueue(new Callback<ResponseDetalleSolicitudFlex>() {
            @Override
            public void onResponse(Call<ResponseDetalleSolicitudFlex> call, Response<ResponseDetalleSolicitudFlex> response) {
                if (response.code() == 200){
                    detalleSolicitudFlex =  response.body().getRequest();
                    tvDescLider.setText(detalleSolicitudFlex.getEmployee());
                    tvNombreEmpleado.setText(detalleSolicitudFlex.getMessageOne());

                    tvFechaInicioFlex.setText(detalleSolicitudFlex.getDateStart());
                    tvFechaFinFlex.setText(detalleSolicitudFlex.getDateEnd());

                    tvDescripcionSolicitud.setText(detalleSolicitudFlex.getMessageTwo());
                    tvDiaSolicitado.setText(detalleSolicitudFlex.getDayWeek());
                    tvFechaSolicitudFlex.setText(detalleSolicitudFlex.getDateRequest());

                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseDetalleSolicitudFlex> call, Throwable t) {
                Toast.makeText(DetallePendienteActivity.this,
                        "Error en el servicio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindearVistas() {
        tvDescLider = findViewById(R.id.detalle_solicitud_flex_tvNomEmpleado);
        tvNombreEmpleado = findViewById(R.id.detalle_solicitud_flex_tvDescLider);
        tvFechaSolicitudFlex = findViewById(R.id.detalle_solicitud_flex_tvFechaSolicitud);
        tvFechaInicioFlex = findViewById(R.id.detalle_solicitud_flex_tvFechaInicio);
        tvFechaFinFlex = findViewById(R.id.detalle_solicitud_flex_tvFechaFin);
        tvDescripcionSolicitud = findViewById(R.id.detalle_solicitud_flex_tvDescripcion);
        tvDiaSolicitado = findViewById(R.id.detalle_solicitud_flex_tvDiaSolicitado);
        mShimmerViewContainer = findViewById(R.id.detalle_solicitud_flex_shimerFrameLayout);
        tvBotonAprobar = findViewById(R.id.detalle_solicitud_flex_tvButtonAprobar);
        tvBotonRechazar = findViewById(R.id.detalle_solicitud_flex_tvButtonRechazar);

        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
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

    public void mostrarDialogAprobacionFlexPlace(String nombre){
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetallePendienteActivity.this);
        builder.setTitle("¿Deseas continuar?");
        String mensaje = "Vas a aprobar la solicitud de FlexPlace de " + nombre + ".";

        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(DetallePendienteActivity.this,
                        FinalizarAprobarRechazarFlexActivity.class);
                intent.putExtra("nombreEmpleado",detalleSolicitudFlex.getEmployee());
                intent.putExtra("idRequest",detalleSolicitudFlex.getId());
                intent.putExtra("dia",detalleSolicitudFlex.getDayWeek());
                intent.putExtra("aprobar",true);
                intent.putExtra("observacion","");
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Ahora no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        if (!isFinishing()) alertDialog.show();
    }
}
