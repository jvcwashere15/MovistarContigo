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
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
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
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
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

                }else {
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
        tvDescripcion.setText("Tienes un plazo de 3 días hábiles para responder a esta solicitud, " +
                "de lo contrario se aprobarán automáticamente.");
    }


    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de solicitud");
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
                mostrarDialogAprobacionRechazo("aprobar",colaborador,true);
            }
        });
    }

    private void configurarBotonRechazar(final String colaborador) {
        tvButtonRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogAprobacionRechazo("rechazar",colaborador,false);
            }
        });
    }

    private void mostrarDialogAprobacionRechazo(String mensaje, final String nombre, final boolean approver) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetalleAprobacionActivity.this);
        builder.setTitle("¿Deseas continuar?");
        builder.setMessage("Vas a " + mensaje + " la solicitud de vacaciones de " + nombre);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DetalleAprobacionActivity.this,FinalizarAprobacionActivity.class);
                //Datos para aprobar o rechazar vacaciones
                intent.putExtra("employeeCode",getCIP());
                intent.putExtra("requestCode",detalleSolicitud.getRequestCode());
                intent.putExtra("approver",approver);
                //Datos para mostrar
                intent.putExtra("colaborador",nombre);
                intent.putExtra("dias",detalleSolicitud.getRequestDaysDifference());
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
        tvMessageBoton.setVisibility(View.VISIBLE);
        tvMessageBoton.setText("Cargar nuevamente");
        tvMessageBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetalleSolicitudFromNetwork();
            }
        });
    }





}
