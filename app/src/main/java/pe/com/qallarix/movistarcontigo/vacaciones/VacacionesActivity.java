package pe.com.qallarix.movistarcontigo.vacaciones;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceVacaciones;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.ForApproveVacationsActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.pendientes.PendingRequestsActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.pojos.FutureJoy;
import pe.com.qallarix.movistarcontigo.vacaciones.pojos.VacacionesDashboard;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.RegisterVacationsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacacionesActivity extends TranquiParentActivity {
    private View
            vDashboard,
            vRegistro,
            vEstado,
            vAprobacion;
    private TextView
            tvPendientes,
            tvAdelanto,
            tvFechaDerecho;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
    private ImageView ivMessageImagen;

    private int resultadoPedidoVacaciones;

    private boolean isLoading = true;
    private String liderName = "";

    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacaciones);
        configurarToolbar();
        getDataFromExtras();
        bindearVistas();
        configurarBotonRegistroVacaciones();
        configurarBotonEstadoVacaciones();
        configurarBotonAprobacionVacaciones();
        cargarDatosVacaciones();
    }

    private void getDataFromExtras() {
        if (getIntent().getExtras()!= null && getIntent().getExtras().containsKey("resultadoPedidoVacaciones"))
            resultadoPedidoVacaciones = getIntent().getExtras().getInt("resultadoPedidoVacaciones",0);
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

    private void cargarDatosVacaciones() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        Call<VacacionesDashboard> call = WebServiceVacaciones
                .getInstance(getDocumentNumber())
                .createService(ServiceVacacionesApi.class)
                .getInfoDashboardVacaciones(getCIP());
        call.enqueue(new Callback<VacacionesDashboard>() {
            @Override
            public void onResponse(Call<VacacionesDashboard> call, Response<VacacionesDashboard> response) {
                if (response.code() == 200){
                    FutureJoy futureJoy = response.body().getFutureJoy();
                    displayFutureJoy(futureJoy);
                    if (resultadoPedidoVacaciones == 1)
                        mostrarPopUpNotificacion(
                                R.drawable.ic_vacacion_aprobada,
                                "¡Vacaciones aprobadas!",
                                "Tu solicitud de vacaciones fue aprobada por tu jefe aprobador. " +
                                        "Revisa el detalle en el SGV.");
                    else if (resultadoPedidoVacaciones == 2){
                        mostrarPopUpNotificacion(
                                R.drawable.ic_vacacion_rechazada,
                                "¡Vacaciones rechazadas!",
                                "Tu solicitud de vacaciones fue rechazada por tu jefe aprobador." +
                                        " Revisa el detalle en el SGV.");
                    }
                }else if (response.code() == 500 || response.code() == 404){
                    mostrarMensajeError();
                }else {
                    displayResultadoException(response.errorBody());
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<VacacionesDashboard> call, Throwable t) {
                mostrarMensajeError();
            }
        });
    }

    private void mostrarPopUpNotificacion(int drawableImage, String titulo, String mensaje) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_vacaciones_notificacion);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView ivResultado = dialog.findViewById(R.id.dialog_noti_vacaciones_ivResultado);
        TextView tvTitulo = dialog.findViewById(R.id.dialog_noti_vacaciones_tvTitulo);
        TextView tvMensaje = dialog.findViewById(R.id.dialog_noti_vacaciones_tvMensaje);
        TextView tvBotonOkEntiendo = dialog.findViewById(R.id.dialog_noti_vacaciones_btOkEntiendo);

        ivResultado.setImageResource(drawableImage);
        tvTitulo.setText(titulo);
        tvMensaje.setText(mensaje);

        tvBotonOkEntiendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void displayResultadoException(ResponseBody response) {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.img_error_servidor);
        tvMessageTitle.setText("¡Ups!");
        tvMessageBoton.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(response.string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("exception");
            String m = jsonObject1.getString("exceptionMessage");
            tvMessageMensaje.setText(m);
        } catch (Exception e) {
            e.printStackTrace();
            tvMessageMensaje.setText("Hubo un problema con el servidor. Estamos trabajando para solucionarlo." +
                    "\nPor favor, verifica tus solicitudes pendientes por aprobar.");
        }

    }

    private void displayFutureJoy(FutureJoy futureJoy) {
        tvPendientes.setText(String.valueOf(futureJoy.getPlannedDaysPending()) + " disponibles");
        tvAdelanto.setText(String.valueOf(futureJoy.getPlannedDaysTruncate()));
        tvFechaDerecho.setText(futureJoy.getDateOfRight());
        liderName = futureJoy.getLeadershipName();
        if (futureJoy.isLeadership()) vAprobacion.setVisibility(View.VISIBLE);
        vDashboard.setVisibility(View.VISIBLE);
        viewMessage.setVisibility(View.GONE);
    }


    private void configurarBotonRegistroVacaciones() {
        vRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, RegisterVacationsActivity.class);
                intent.putExtra("leadershipName",liderName);
                startActivity(intent);
                finish();
            }
        });
    }
    private void configurarBotonEstadoVacaciones() {
        vEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, PendingRequestsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void configurarBotonAprobacionVacaciones() {
        vAprobacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, ForApproveVacationsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    private void bindearVistas() {
        vRegistro = findViewById(R.id.vacaciones_vRegistro);
        vEstado = findViewById(R.id.vacaciones_vEstado);
        vAprobacion = findViewById(R.id.vacaciones_vAprobacion);
        tvPendientes = findViewById(R.id.vacaciones_tvPendientes);
        tvAdelanto = findViewById(R.id.vacaciones_tvAdelanto);
        tvFechaDerecho = findViewById(R.id.vacaciones_tvFechaDerecho);
        mShimmerViewContainer = findViewById(R.id.dashboard_vacaciones_shimerFrameLayout);
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
        vDashboard = findViewById(R.id.view_dashboard_vacaciones);
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gestión de vacaciones");
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

    private void mostrarMensajeError() {
        viewMessage.setVisibility(View.VISIBLE);
        vDashboard.setVisibility(View.GONE);
        ivMessageImagen.setImageResource(R.drawable.img_error_servidor);
        tvMessageTitle.setText("¡Ups!");
        tvMessageMensaje.setText("Hubo un problema con el servidor. " +
                "Estamos trabajando para solucionarlo.");
        tvMessageBoton.setVisibility(View.VISIBLE);
        tvMessageBoton.setText("Cargar nuevamente");
        tvMessageBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatosVacaciones();
            }
        });
    }

    public void verInformacionImportante(View view) {
        Intent intent = new Intent(this,AcercaActivity.class);
        Analitycs.logEventoClickBotonAcercaDeVacaciones(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }
}
