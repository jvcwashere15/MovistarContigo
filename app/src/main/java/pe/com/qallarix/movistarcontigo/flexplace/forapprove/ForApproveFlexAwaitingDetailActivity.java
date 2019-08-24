package pe.com.qallarix.movistarcontigo.flexplace.forapprove;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
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
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos.FlexRequestDetail;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos.ResponseDetalleSolicitudFlex;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForApproveFlexAwaitingDetailActivity extends TranquiParentActivity {

    //codigo de la solicitud
    private int requestCode;
    private FlexRequestDetail flexRequestDetail;

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
        setContentView(R.layout.activity_flexplace_forapprove_awaiting_request);
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
                mostrarDialogAprobacionFlexPlace(flexRequestDetail.getEmployee());
            }
        });
    }

    private void configurarBotonRechazar() {
        tvBotonRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForApproveFlexAwaitingDetailActivity.this,
                        ForApproveFlexRejectActivity.class);
                intent.putExtra("fechaSolicitud", flexRequestDetail.getDateRequest());
                intent.putExtra("fechaInicio", flexRequestDetail.getDateStart());
                intent.putExtra("nombreEmpleado", flexRequestDetail.getEmployee());
                intent.putExtra("fechaFin", flexRequestDetail.getDateEnd());
                intent.putExtra("dia", flexRequestDetail.getDayWeek());
                intent.putExtra("idRequest", flexRequestDetail.getId());
                startActivity(intent);
                finish();
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
                    tvDescLider.setText(flexRequestDetail.getEmployee());
                    tvNombreEmpleado.setText(flexRequestDetail.getMessageOne());

                    tvFechaInicioFlex.setText(flexRequestDetail.getDateStart());
                    tvFechaFinFlex.setText(flexRequestDetail.getDateEnd());

                    tvDescripcionSolicitud.setText(flexRequestDetail.getMessageTwo());
                    tvDiaSolicitado.setText(flexRequestDetail.getDayWeek());
                    tvFechaSolicitudFlex.setText(flexRequestDetail.getDateRequest());

                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseDetalleSolicitudFlex> call, Throwable t) {
                Toast.makeText(ForApproveFlexAwaitingDetailActivity.this,
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                ForApproveFlexAwaitingDetailActivity.this,R.style.DialogMensajeStyle);
        builder.setTitle("¿Deseas continuar?");
        String mensaje = "Vas a aprobar la solicitud de FlexPlace de " + nombre + ".";

        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ForApproveFlexAwaitingDetailActivity.this,
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

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }
}
