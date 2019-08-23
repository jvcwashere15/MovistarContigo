package pe.com.qallarix.movistarcontigo.flexplace;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.shimmer.ShimmerFrameLayout;
import org.json.JSONObject;
import okhttp3.ResponseBody;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.flexplace.history.HistoryFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.myteam.MyTeamFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.pojos.DashBoardFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.pojos.Dashboard;
import pe.com.qallarix.movistarcontigo.flexplace.register.RegistrarFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.ForApproveFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FlexplaceActivity extends TranquiParentActivity {
    private View
            vDashboard,
            vRegister,
            vHistory,
            vRequests,
            vFlexTeam;
    private TextView
            tvMessage,
            tvDay,
            tvMonth,
            tvDescription,
            tvRightDate;

    private Dashboard dashboard;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
    private ImageView ivMessageImagen;

    private boolean isLoading = true;
    private String leader = "";

    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace);
        configurarToolbar();
        bindearVistas();
        cargarMenuFlexPlace();
        configurarBotonRegistrar();
        configurarBotonHistorial();
        configurarBotonSolicitudes();
        configurarBotonMiEquipo();
    }

    private void configurarBotonMiEquipo() {
        vFlexTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        MyTeamFlexPlaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void configurarBotonSolicitudes() {
        vRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        ForApproveFlexPlaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void configurarBotonHistorial() {
        vHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        HistoryFlexPlaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void configurarBotonRegistrar() {
        vRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        RegistrarFlexPlaceActivity.class);
                intent.putExtra("leader", leader);
                startActivity(intent);
                finish();
            }
        });
    }


    private void cargarMenuFlexPlace() {
        Call<DashBoardFlexPlace> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceApi.class)
                .getInfoDashboardFlexplace(getDocumentNumber());

        call.enqueue(new Callback<DashBoardFlexPlace>() {
            @Override
            public void onResponse(Call<DashBoardFlexPlace> call, Response<DashBoardFlexPlace> response) {
                if ( response.code() == 200){
                    dashboard = response.body().getDashboard();
                    leader = dashboard.getLeadership();
                    if (dashboard.isLeadership()){
                        vFlexTeam.setVisibility(View.VISIBLE);
                        vRequests.setVisibility(View.VISIBLE);
                    }

                    if (dashboard.getIsStatus() == 1)
                        displayDashboardVacio();
                    else if(dashboard.getIsStatus() == 2)
                        displayDashboardPendientes();
                    else if(dashboard.getIsStatus() == 3)
                        displayResumenFlexPlace(dashboard.getDayWeek(), dashboard.getDateEnd());
                    else
                        displayResumenFlexPlaceFuturo(dashboard.getDateStart());

                }else if (response.code() == 400){
                    displayResultadoException(response.errorBody());
                }else{
                    mostrarMensajeError();
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<DashBoardFlexPlace> call, Throwable t) {
                Toast.makeText(FlexplaceActivity.this, "Error en el servicio", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void displayResumenFlexPlaceFuturo(String dateStart) {
        tvMessage.setText("Tu solicitud");
        tvMonth.setText("FlexPlace");
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        tvMonth.setVisibility(View.VISIBLE);
        tvDay.setText("está aprobada");
        tvDay.setVisibility(View.VISIBLE);
        tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tvDescription.setGravity(Gravity.CENTER);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.resumen_flexplace_periodo_futuro), dateStart);
        CharSequence styledText = Html.fromHtml(text);
        tvDescription.setText(styledText);
        tvRightDate.setVisibility(View.GONE);
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

    private void displayDashboardVacio() {
        tvMessage.setText("Disfruta de tu");
        tvMonth.setText("FlexPlace");
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        tvDay.setVisibility(View.VISIBLE);
        tvDay.setText("¡Regístralo ahora!");
        tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tvDescription.setGravity(Gravity.CENTER);
        tvDescription.setText("¡Elige un día de la semana para trabajar desde casa!");
        tvRightDate.setVisibility(View.GONE);
    }

    private void displayDashboardPendientes() {
        tvMessage.setText("Tu solicitud");
        tvMonth.setText("FlexPlace");
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        tvMonth.setVisibility(View.VISIBLE);
        tvDay.setText("está pendiente");
        tvDay.setVisibility(View.VISIBLE);
        tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tvDescription.setGravity(Gravity.CENTER);
        tvDescription.setText("Se te notificará al momento de la aprobación.");
        tvRightDate.setVisibility(View.GONE);
    }

    private void displayResumenFlexPlace(String dia, String fechaDerecho) {
        tvMonth.setText("¡Son los " + dia + "!");
        tvRightDate.setText(fechaDerecho);
    }


    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FlexPlace");
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
                cargarMenuFlexPlace();
            }
        });
    }

    private void bindearVistas() {
        //views opciones menu
        vRegister = findViewById(R.id.flexplace_vRegistro);
        vHistory = findViewById(R.id.flexplace_vHistorial);
        vRequests = findViewById(R.id.flexplace_vSolicitudes);
        vFlexTeam = findViewById(R.id.flexplace_vFlexEquipo);
        //views info resumen
        tvMessage = findViewById(R.id.flexplace_tvMensajeInicial);
        tvDay = findViewById(R.id.flexplace_tvDia);
        tvMonth = findViewById(R.id.flexplace_tvMeses);
        tvDescription = findViewById(R.id.flexplace_tvDescripcionDashEmpty);
        tvRightDate = findViewById(R.id.flexplace_tvFechaDerecho);
        //view shimmer
        mShimmerViewContainer = findViewById(R.id.dashboard_flexplace_shimerFrameLayout);
        //views message
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
        //view principal
        vDashboard = findViewById(R.id.view_dashboard_flexplace);
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

    public void verInformacionImportante(View view) {
        Intent intent = new Intent(this, AboutFlexPlaceActivity.class);
        Analitycs.logEventoClickBotonAcercaDeFlexPlace(this);
        startActivity(intent);
    }
}
