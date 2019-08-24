package pe.com.qallarix.movistarcontigo.flexplace.forapprove;

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
import pe.com.qallarix.movistarcontigo.flexplace.history.ServiceFlexPlaceHistoryApi;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos.FlexRequestDetail;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos.ResponseDetalleSolicitudFlex;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForApproveFlexRequestDetailActivity extends TranquiParentActivity {
    //codigo de la solicitud
    private int requestCode;
    private FlexRequestDetail flexRequestDetail;

    //etiquetas de informacion
    private TextView tvLeaderDescription, tvEmployeeName, tvFlexRequestState, tvFlexRequestDate,
            tvFlexStartDate, tvFlexEndDate, tvRequestDescription, tvCancellationDescription,
            tvRequestedDay;

    //boton aprobar rechazar
    private TextView tvApproveButton, tvRejectButton, tvNotifyButton;
    private View viewNotify;
    private TextView tvNotifyMessage, tvNotifyTitle;

    //view message
    private View viewMessage;
    private TextView tvMessageServerTitle, tvMessageServerMessage, tvMessageServerButton;
    private ImageView ivMessageServerImage;

    private ShimmerFrameLayout mShimmerViewContainer;
    private boolean isLoading;
    private boolean mPantallaAnterior;

    private String mTextoBoton = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_forapprove_detail);
        getDataFromIntent();
        configurarToolbar();
        bindViews();
        displayDetalleSolicitudFlexPlace();
        configurarBotonAprobar();
        configurarBotonRechazar();
        configurarBotonNotificar();
    }

    private void configurarBotonNotificar() {
        tvNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialogNotifyCancelattion(flexRequestDetail.getEmployee());
            }
        });
    }

    private void configurarBotonAprobar() {
        tvApproveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogAprobacionFlexPlace(flexRequestDetail.getEmployee());
            }
        });
    }

    private void configurarBotonRechazar() {
        tvRejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForApproveFlexRequestDetailActivity.this,
                        ForApproveFlexRejectActivity.class);
                intent.putExtra("fechaSolicitud", flexRequestDetail.getDateRequest());
                intent.putExtra("fechaInicio", flexRequestDetail.getDateStart());
                intent.putExtra("nombreEmpleado", flexRequestDetail.getEmployee());
                intent.putExtra("fechaFin", flexRequestDetail.getDateEnd());
                intent.putExtra("dia", flexRequestDetail.getDayWeek());
                intent.putExtra("idRequest", flexRequestDetail.getId());
                startActivity(intent);
            }
        });
    }

    private void displayDetalleSolicitudFlexPlace() {
        Call<ResponseDetalleSolicitudFlex> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceforApproveApi.class)
                .getSolicitudFlexPlace(requestCode);

        call.enqueue(new Callback<ResponseDetalleSolicitudFlex>() {
            @Override
            public void onResponse(Call<ResponseDetalleSolicitudFlex> call, Response<ResponseDetalleSolicitudFlex> response) {
                if (response.code() == 200){
                    flexRequestDetail =  response.body().getRequest();
                    tvLeaderDescription.setText(flexRequestDetail.getMessageOne());
                    tvEmployeeName.setText(flexRequestDetail.getEmployee());

                    tvFlexStartDate.setText(flexRequestDetail.getDateStart());
                    tvFlexEndDate.setText(flexRequestDetail.getDateEnd());

                    tvRequestDescription.setText(flexRequestDetail.getMessageTwo());
                    tvCancellationDescription.setText(flexRequestDetail.getMessageThree());
                    tvRequestedDay.setText(flexRequestDetail.getDayWeek());

                    if (!TextUtils.isEmpty(flexRequestDetail.getReason())){
                        viewNotify.setVisibility(View.VISIBLE);
                        if (flexRequestDetail.getStatusId().equals(ServiceFlexPlaceHistoryApi.RECHAZADO))
                            tvNotifyTitle.setText("Motivo de rechazo");
                        tvNotifyMessage.setText(flexRequestDetail.getReason());
                    }

                    String strEstado = "";
                    int colorEstado = 0;
                    if (flexRequestDetail.getStatusId().equals(ServiceFlexPlaceHistoryApi.APROBADO)){
                        strEstado = "APROBADO";colorEstado = R.drawable.etiqueta_verde;
                        tvNotifyButton.setVisibility(View.VISIBLE);
                    }else if (flexRequestDetail.getStatusId().equals(ServiceFlexPlaceHistoryApi.PENDIENTE)){
                        strEstado = "PENDIENTE";colorEstado = R.drawable.etiqueta_amarilla;
                        findViewById(R.id.detalle_solicitud_flex_viewLine).setVisibility(View.GONE);
                        findViewById(R.id.detalle_solicitud_flex_tvTextoEstado).setVisibility(View.GONE);
                        tvFlexRequestState.setVisibility(View.GONE);
                        tvApproveButton.setVisibility(View.VISIBLE);
                        tvRejectButton.setVisibility(View.VISIBLE);
                    }else if (flexRequestDetail.getStatusId().equals(ServiceFlexPlaceHistoryApi.RECHAZADO)){
                        strEstado = "RECHAZADO";colorEstado = R.drawable.etiqueta_roja;
                    }else if (flexRequestDetail.getStatusId().equals(ServiceFlexPlaceHistoryApi.CANCELADO)){
                        strEstado = "CANCELADO";colorEstado = R.drawable.etiqueta_morada;
                    }

                    tvFlexRequestDate.setText(flexRequestDetail.getDateRequest());
                    tvFlexRequestState.setText(strEstado);
                    tvFlexRequestState.setBackgroundResource(colorEstado);


                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseDetalleSolicitudFlex> call, Throwable t) {
                Toast.makeText(ForApproveFlexRequestDetailActivity.this,
                        "Error en el servicio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindViews() {
        tvLeaderDescription = findViewById(R.id.detalle_solicitud_flex_tvNomEmpleado);
        tvEmployeeName = findViewById(R.id.detalle_solicitud_flex_tvDescLider);
        tvFlexRequestState = findViewById(R.id.detalle_solicitud_flex_tvEstado);
        tvFlexRequestDate = findViewById(R.id.detalle_solicitud_flex_tvFechaSolicitud);
        tvFlexStartDate = findViewById(R.id.detalle_solicitud_flex_tvFechaInicio);
        tvFlexEndDate = findViewById(R.id.detalle_solicitud_flex_tvFechaFin);
        tvRequestDescription = findViewById(R.id.detalle_solicitud_flex_tvDescripcion);
        tvRequestedDay = findViewById(R.id.detalle_solicitud_flex_tvDiaSolicitado);
        mShimmerViewContainer = findViewById(R.id.detalle_solicitud_flex_shimerFrameLayout);
        tvApproveButton = findViewById(R.id.detalle_solicitud_flex_tvButtonAprobar);
        tvRejectButton = findViewById(R.id.detalle_solicitud_flex_tvButtonRechazar);
        tvNotifyButton = findViewById(R.id.detalle_solicitud_flex_tvButtonNotificar);

        // view message server error
        viewMessage = findViewById(R.id.flexplace_detail_view_message);
        ivMessageServerImage = findViewById(R.id.flexplace_view_message_ivImage);
        tvMessageServerTitle = findViewById(R.id.flexplace_view_message_tvTitle);
        tvMessageServerMessage = findViewById(R.id.flexplace_view_message_tvMessage);
        tvMessageServerButton = findViewById(R.id.flexplace_view_message_tvButton);

        //view for notificaction
        viewNotify = findViewById(R.id.detalle_solicitud_flex_viewNoticacion);
        tvNotifyTitle = findViewById(R.id.detalle_solicitud_flex_tvTituloNotificacion);
        tvNotifyMessage = findViewById(R.id.detalle_solicitud_flex_tvMensajeNotificacion);

        tvCancellationDescription = findViewById(R.id.detalle_solicitud_flex_tvMessageThree);
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

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("texto_boton")){
            mTextoBoton = getIntent().getExtras().getString("texto_boton","");
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
        startActivity(upIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mPantallaAnterior) super.onBackPressed();
        else goToParentActivity();
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(ForApproveFlexRequestDetailActivity.this);
        builder.setTitle("¿Deseas continuar?");
        String mensaje = "Vas a aprobar la solicitud de FlexPlace de " + nombre + ".";

        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ForApproveFlexRequestDetailActivity.this,
                        ForApproveFlexFinishProcessActivity.class);
                intent.putExtra("nombreEmpleado", flexRequestDetail.getEmployee());
                intent.putExtra("idRequest", flexRequestDetail.getId());
                intent.putExtra("dia", flexRequestDetail.getDayWeek());
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

    public void displayDialogNotifyCancelattion(String nombre){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ForApproveFlexRequestDetailActivity.this);
        builder.setTitle("¿Deseas continuar?");
        String mensaje = "Vas a notificar a " + nombre + " para que cancele su FlexPlace.";
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ForApproveFlexRequestDetailActivity.this,
                        ForApproveFlexFinishNotificationActivity.class);
                intent.putExtra("nombreEmpleado", flexRequestDetail.getEmployee());
                intent.putExtra("idRequest", flexRequestDetail.getId());
                intent.putExtra("texto_boton", mTextoBoton);
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
