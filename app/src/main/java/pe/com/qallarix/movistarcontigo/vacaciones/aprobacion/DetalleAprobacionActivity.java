package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.DetalleSolicitud;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.ResponseSolicitudAprobacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleAprobacionActivity extends TranquiParentActivity {
    private String requestCode,fechaSolicitud;
    private TextView
            tvEmpleado,
            tvFechaSolicitud,
            tvFechaInicio,
            tvFechaFin,
            tvDiasSolicitados,
            tvDescripcion;
    private TextView
            tvButtonAprobar,
            tvButtonRechazar;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje;
    private ImageView ivMessageImagen;

    private DetalleSolicitud detalleSolicitud;

    private ShimmerFrameLayout mShimmerViewContainer;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_aprobacion_vacaciones);
        bindearVistas();
        configurarToolbar();
        getExtrasFromIntent();

        if (!TextUtils.isEmpty(requestCode)) getDetalleSolicitudFromNetwork();
    }

    private void getExtrasFromIntent() {
        requestCode = getIntent().getExtras().getString("requestCode","");
        fechaSolicitud = getIntent().getExtras().getString("requestDay","");
    }

    private void bindearVistas() {
        tvEmpleado = findViewById(R.id.detalle_aprobacion_tvEmpleado);
        tvDescripcion = findViewById(R.id.detalle_aprobacion_tvDescripcion);
        tvDiasSolicitados = findViewById(R.id.detalle_aprobacion_tvDiasSolicitados);
        tvFechaSolicitud = findViewById(R.id.detalle_aprobacion_tvFechaSolicitud);
        tvFechaFin = findViewById(R.id.detalle_aprobacion_tvFechaFin);
        tvFechaInicio = findViewById(R.id.detalle_aprobacion_tvFechaInicio);
        tvButtonAprobar = findViewById(R.id.detalle_aprobacion_tvButtonAprobar);
        tvButtonRechazar = findViewById(R.id.detalle_aprobacion_tvButtonRechazar);
        mShimmerViewContainer = findViewById(R.id.detalle_solicitud_shimerFrameLayout);
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
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

    private void getDetalleSolicitudFromNetwork() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        Call<ResponseSolicitudAprobacion> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceAprobacionVacacionesApi.class)
                .obtenerDetalleSolicitudAprobar(getCIP(),requestCode);
        call.enqueue(new Callback<ResponseSolicitudAprobacion>() {
            @Override
            public void onResponse(Call<ResponseSolicitudAprobacion> call,
                                   Response<ResponseSolicitudAprobacion> response) {
                if (response.code() == 200){
                    detalleSolicitud = response.body().getRequest();
                    displayDetalleSolicitud();
                    configurarBotonAprobar(detalleSolicitud.getEmployeeName());
                    configurarBotonRechazar(detalleSolicitud.getEmployeeName());
                }else if (response.code() == 400){
                    mostrarMensaje400(response.errorBody());
                } else if (response.code() == 500){
                    mostrarMensaje500();
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseSolicitudAprobacion> call, Throwable t) {
                Toast.makeText(DetalleAprobacionActivity.this,
                        "Error servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayDetalleSolicitud() {
        tvEmpleado.setText(detalleSolicitud.getEmployeeName());
        tvFechaSolicitud.setText(fechaSolicitud);
        tvFechaInicio.setText(detalleSolicitud.getRequestDateStart());
        tvFechaFin.setText(detalleSolicitud.getRequestDateEnd());
        tvDiasSolicitados.setText(detalleSolicitud.getRequestDaysDifference() + " días");
        tvDescripcion.setText("Tienes plazo para responder a esta solicitud hasta el " +
                "07/04/2019" + ", de lo contrario se aprobarán automáticamente.");
    }


    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aprobación vacaciones");
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

    private void configurarBotonAprobar(final String colaborador) {
        tvButtonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogAprobacionRechazo("aprobar",colaborador);
            }
        });
    }

    private void configurarBotonRechazar(final String colaborador) {
        tvButtonRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogAprobacionRechazo("rechazar",colaborador);
            }
        });
    }

    private void mostrarDialogAprobacionRechazo(String mensaje, String nombre) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetalleAprobacionActivity.this);
        builder.setTitle("¿Deseas continuar?");
        builder.setMessage("Vas a " + mensaje + " la solicitud de vacaciones de " + nombre);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DetalleAprobacionActivity.this,FinalizarAprobacionActivity.class);
                intent.putExtra("employeeCode",getCIP());
                intent.putExtra("requestCode",detalleSolicitud.getRequestCode());
                intent.putExtra("approver",true);
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


    private void mostrarMensaje500() {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.img_error_servidor);
        tvMessageTitle.setText("¡Ups!");
        tvMessageMensaje.setText("Hubo un problema con el servidor. " +
                "Estamos trabajando para solucionarlo.");
    }

    private void mostrarMensaje400(ResponseBody responseBody) {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.ic_check_error);
        tvMessageTitle.setText("Registro inválido");
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("exception");
            String m = jsonObject1.getString("exceptionMessage");
            tvMessageMensaje.setText(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarEmptyView() {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.ic_empty_view_lista_vacaciones);
        tvMessageTitle.setText("¡Genial!");
        tvMessageMensaje.setText("Todas tus solicitudes de vacaciones han sido atendidas.");
    }



}
