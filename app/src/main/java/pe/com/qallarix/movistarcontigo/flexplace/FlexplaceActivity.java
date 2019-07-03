package pe.com.qallarix.movistarcontigo.flexplace;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.historial.HistorialFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.miequipo.MiEquipoFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.RegistrarFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.flexplace.solicitudes.SolicitudesFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.vacaciones.AcercaActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.ServiceVacacionesApi;
import pe.com.qallarix.movistarcontigo.vacaciones.VacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.AprobacionVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.EstadoVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.pojos.FutureJoy;
import pe.com.qallarix.movistarcontigo.vacaciones.pojos.VacacionesDashboard;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.RegistroVacacionesActivity;
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
            tvDia,
            tvMeses,
            tvFechaDerecho;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje,tvMessageBoton;
    private ImageView ivMessageImagen;

    private boolean isLoading = true;
    private String liderName = "";

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
            }
        });
    }

    private void configurarBotonRegistrar() {
        vRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlexplaceActivity.this,
                        RegistrarFlexPlaceActivity.class);
                startActivity(intent);
            }
        });
    }


    private void cargarMenuFlexPlace() {
        displayResumenFlexPlace("Viernes",3,
                "20/10/2019");
    }

    private void displayResumenFlexPlace(String dia, int meses, String fechaDerecho) {
        tvDia.setText(dia);
        tvMeses.setText("Por " + meses + " meses");
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
        tvDia = findViewById(R.id.flexplace_tvDia);
        tvMeses = findViewById(R.id.flexplace_tvMeses);
        tvFechaDerecho = findViewById(R.id.flexplace_tvFechaDerecho);
        //view shimmer
        mShimmerViewContainer = findViewById(R.id.dashboard_vacaciones_shimerFrameLayout);
        //views message
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
        //view principal
        vDashboard = findViewById(R.id.view_dashboard_flexplace);
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        if (isLoading)
//            mShimmerViewContainer.startShimmer();
//    }
//
//    @Override
//    protected void onPause() {
//        mShimmerViewContainer.stopShimmer();
//        super.onPause();
//    }
}
