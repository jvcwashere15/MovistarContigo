package pe.com.qallarix.movistarcontigo.flexplace.history;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
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
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.Request;
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseDetalleFlexPlace;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFlexDetailActivity extends TranquiParentActivity {

    private int requestCode;
    private TextView tvLider,tvDescripcionLider,
                    tvEstado,tvFechaSolicitud,
                    tvFechaInicio, tvFechaFin,
                    tvDiaElegido,tvDescripcion,tvDescripcionNotificacion,tvButtonCancelar;
    private View vNotificacion;
    private TextView tvMensajeNotificacion,tvTituloNotificacion;
    private Request currentRequest;
    private int typeRequest;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
    private ImageView ivMessageImagen;

    private ShimmerFrameLayout mShimmerViewContainer;
    private boolean isLoading;
    private boolean mPantallaAnterior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_history_detail);
        getDataFromIntent();
        configurarToolbar();
        bindearVistas();
        displayDetalleFlexPlace();
        configurarBotonCancelar();
    }

    private void configurarBotonCancelar() {
        tvButtonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRequest.getStatusId().equals(ServiceFlexPlaceHistoryApi.PENDIENTE)){
                    mostrarDialogCancelacionFlexPlace(currentRequest.getStatusId(),
                            currentRequest.getDateStart(),
                            currentRequest.getDateEnd());
                }else{
                    Intent intent = new Intent(HistoryFlexDetailActivity.this,
                            HistoryFlexCancelActivity.class);
                    intent.putExtra("fechaInicio",currentRequest.getDateStart());
                    intent.putExtra("fechaFin",currentRequest.getDateEnd());
                    intent.putExtra("dia",currentRequest.getDayWeek());
                    intent.putExtra("idRequest",currentRequest.getId());
                    intent.putExtra("statusId",currentRequest.getStatusId());
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void bindearVistas() {
        tvLider = findViewById(R.id.detalle_flexplace_tvLider);
        tvDescripcionLider = findViewById(R.id.detalle_flexplace_tvDescLider);
        tvEstado = findViewById(R.id.detalle_flexplace_tvEstado);
        tvFechaSolicitud = findViewById(R.id.detalle_flexplace_tvFechaSolicitud);
        tvFechaInicio = findViewById(R.id.detalle_flexplace_tvFechaInicio);
        tvFechaFin = findViewById(R.id.detalle_flexplace_tvFechaFin);
        tvDiaElegido = findViewById(R.id.detalle_flexplace_tvDiaSolicitado);
        tvDescripcion = findViewById(R.id.detalle_flexplace_tvDescripcion);
        tvDescripcionNotificacion = findViewById(R.id.detalle_flexplace_tvDescripcionCancelar);

        tvButtonCancelar = findViewById(R.id.detalle_flexplace_tvButtonCancelar);
        vNotificacion = findViewById(R.id.detalle_flexplace_viewNoticacion);
        tvMensajeNotificacion = findViewById(R.id.detalle_flexplace_tvMensajeNotificacion);
        tvTituloNotificacion = findViewById(R.id.detalle_flexplace_tvTituloNotificacion);
        mShimmerViewContainer = findViewById(R.id.detalle_vacaciones_shimerFrameLayout);

        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
    }

    private void displayDetalleFlexPlace() {
        Call<ResponseDetalleFlexPlace> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexPlaceHistoryApi.class)
                .getDetalleFlexPlace(requestCode);

        call.enqueue(new Callback<ResponseDetalleFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseDetalleFlexPlace> call, Response<ResponseDetalleFlexPlace> response) {
                if (response.code() ==  200){
                    currentRequest = response.body().getRequest();
                    tvLider.setText(currentRequest.getLeadership());
                    tvDescripcionLider.setText(currentRequest.getMessageOne());
                    tvFechaSolicitud.setText(currentRequest.getDateRequest());
                    tvFechaInicio.setText(currentRequest.getDateStart());
                    tvFechaFin.setText(currentRequest.getDateEnd());
                    tvDiaElegido.setText(currentRequest.getDayWeek());
                    if (!TextUtils.isEmpty(currentRequest.getMessageTwo())) {
                        tvDescripcion.setVisibility(View.VISIBLE);
                        tvDescripcion.setText(currentRequest.getMessageTwo());
                    }
                    if (!TextUtils.isEmpty(currentRequest.getMessageThree())) {
                        tvDescripcionNotificacion.setVisibility(View.VISIBLE);
                        tvDescripcionNotificacion.setText(currentRequest.getMessageThree());
                    }

                    if (currentRequest.isCancelled())
                        tvButtonCancelar.setVisibility(View.VISIBLE);

                    String strEstado = "";
                    int colorEstado = 0;
                    if (currentRequest.getStatusId().equals(ServiceFlexPlaceHistoryApi.APROBADO)){
                        strEstado = "APROBADO";colorEstado = R.drawable.etiqueta_verde;
                        typeRequest = HistoryFlexPlaceActivity.APPROVED;
                    }else if (currentRequest.getStatusId().equals(ServiceFlexPlaceHistoryApi.PENDIENTE)){
                        strEstado = "PENDIENTE";colorEstado = R.drawable.etiqueta_amarilla;
                        typeRequest = HistoryFlexPlaceActivity.AWAITING;
                    }else if (currentRequest.getStatusId().equals(ServiceFlexPlaceHistoryApi.RECHAZADO)){
                        strEstado = "RECHAZADO";colorEstado = R.drawable.etiqueta_roja;
                        typeRequest = HistoryFlexPlaceActivity.REJECTED;
                        tvTituloNotificacion.setText("Motivo de rechazo");
                    }else if (currentRequest.getStatusId().equals(ServiceFlexPlaceHistoryApi.CANCELADO)){
                        strEstado = "CANCELADO";colorEstado = R.drawable.etiqueta_morada;
                        typeRequest = HistoryFlexPlaceActivity.CANCELED;
                    }
                    tvEstado.setText(strEstado);
                    tvEstado.setBackgroundResource(colorEstado);

                    if (!TextUtils.isEmpty(currentRequest.getReason())){
                        vNotificacion.setVisibility(View.VISIBLE);
                        tvMensajeNotificacion.setText(currentRequest.getReason());
                    }
                } else{
                    mostrarMensaje500();
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseDetalleFlexPlace> call, Throwable t) {
                Toast.makeText(HistoryFlexDetailActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getDataFromIntent() {
        if (getIntent().getExtras()!= null){
            if (getIntent().getExtras().containsKey("id"))
                requestCode = Integer.parseInt(getIntent().getExtras().getString("id"));
            else if (getIntent().getExtras().containsKey("requestCode"))
                requestCode = getIntent().getExtras().getInt("requestCode");
        }
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("lista_notificaciones")){
            mPantallaAnterior = getIntent().getExtras().getBoolean("lista_notificaciones",false);
        }
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

    private void goToParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        upIntent.putExtra("initTab",typeRequest);
        startActivity(upIntent);
        finish();
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
                displayDetalleFlexPlace();
            }
        });
    }

    public void mostrarDialogCancelacionFlexPlace(String statusId, String fechaInicio, String fechaFin){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                HistoryFlexDetailActivity.this,R.style.DialogMensajeStyle);
        builder.setTitle("¿Deseas continuar?");
        String mensaje = "";
        if (statusId.equals(ServiceFlexPlaceHistoryApi.PENDIENTE))
            mensaje = "Vas a cancelar la solicitud de tus días Flex del "
                    + fechaInicio + " al " + fechaFin + ".";
        else{
            mensaje = "Vas a cancelar tu FlexPlace en curso del "
                    + fechaInicio + " al " + fechaFin + ".";
        }
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(HistoryFlexDetailActivity.this,
                        HistoryFlexFinishCancelActivity.class);
                intent.putExtra("fecha_inicio",currentRequest.getDateStart());
                intent.putExtra("fecha_fin",currentRequest.getDateEnd());
                intent.putExtra("idRequest",currentRequest.getId());
                intent.putExtra("statusId",currentRequest.getStatusId());
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

    @Override
    public void onBackPressed() {
        if (mPantallaAnterior) super.onBackPressed();
        else goToParentActivity();
    }
}
