package pe.com.qallarix.movistarcontigo.flexplace;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
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
import pe.com.qallarix.movistarcontigo.flexplace.historial.HistorialFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.miequipo.MiEquipoFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.pojos.DashBoardFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.pojos.Dashboard;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.RegistrarFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.SolicitudesFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FlexplaceActivity extends TranquiParentActivity {
    private View
            vDashboard,
            vRegistro,
            vHistorial,
            vSolicitudes,
            vFlexEquipo;
    private TextView
            tvMensaje,
            tvDia,
            tvMeses,
            tvDescripcion,
            tvFechaDerecho;

    private Dashboard dashboard;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
    private ImageView ivMessageImagen;

    private boolean isLoading = true;
    private String leadership = "";

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
        vFlexEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        MiEquipoFlexPlaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void configurarBotonSolicitudes() {
        vSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        SolicitudesFlexPlaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void configurarBotonHistorial() {
        vHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        HistorialFlexPlaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void configurarBotonRegistrar() {
        vRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        RegistrarFlexPlaceActivity.class);
                intent.putExtra("leadership",leadership);
                startActivity(intent);
                finish();
            }
        });
    }


    private void cargarMenuFlexPlace() {
        Call<DashBoardFlexPlace> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceApi.class)
                .getInfoDashboardFlexplace(getDocumentNumber());

        call.enqueue(new Callback<DashBoardFlexPlace>() {
            @Override
            public void onResponse(Call<DashBoardFlexPlace> call, Response<DashBoardFlexPlace> response) {
                if ( response.code() == 200){
                    dashboard = response.body().getDashboard();
                    leadership= dashboard.getLeadership();
                    if (dashboard.isLeadership()){
                        vFlexEquipo.setVisibility(View.VISIBLE);
                        vSolicitudes.setVisibility(View.VISIBLE);
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
        tvMensaje.setText("Tu solicitud");
        tvMeses.setText("FlexPlace");
        tvMeses.setVisibility(View.VISIBLE);
        tvDia.setText("está aprobada");
        tvDia.setVisibility(View.VISIBLE);
        tvDescripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.resumen_flexplace_periodo_futuro), dateStart);
        CharSequence styledText = Html.fromHtml(text);
        tvDescripcion.setText(styledText);
        tvFechaDerecho.setVisibility(View.GONE);
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
        tvMensaje.setText("Disfruta de tu");
        tvMeses.setText("FlexPlace");
        tvDia.setVisibility(View.VISIBLE);
        tvDia.setText("Regístralo ahora");
        tvDescripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tvDescripcion.setText("¡Elige un día de la semana para trabajar desde casa!");
        tvFechaDerecho.setVisibility(View.GONE);
    }

    private void displayDashboardPendientes() {
        tvMensaje.setText("Tu solicitud");
        tvMeses.setText("FlexPlace");
        tvMeses.setVisibility(View.VISIBLE);
        tvDia.setText("está pendiente");
        tvDia.setVisibility(View.VISIBLE);
        tvDescripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tvDescripcion.setText("Se te notificará al momento de la aprobación.");
        tvFechaDerecho.setVisibility(View.GONE);
    }

    private void displayResumenFlexPlace(String dia, String fechaDerecho) {
        tvMeses.setText("¡Son los " + dia + "!");
        tvFechaDerecho.setText(fechaDerecho);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vacaciones, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_informacion:
                Intent intent = new Intent(this, AcercaFlexPlace.class);
                Analitycs.logEventoClickBotonAcercaDeFlexPlace(this);
                startActivity(intent);
                return true;
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
        vRegistro = findViewById(R.id.flexplace_vRegistro);
        vHistorial = findViewById(R.id.flexplace_vHistorial);
        vSolicitudes = findViewById(R.id.flexplace_vSolicitudes);
        vFlexEquipo = findViewById(R.id.flexplace_vFlexEquipo);
        //views info resumen
        tvMensaje = findViewById(R.id.flexplace_tvMensajeInicial);
        tvDia = findViewById(R.id.flexplace_tvDia);
        tvMeses = findViewById(R.id.flexplace_tvMeses);
        tvDescripcion = findViewById(R.id.flexplace_tvDescripcionDashEmpty);
        tvFechaDerecho = findViewById(R.id.flexplace_tvFechaDerecho);
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
}
