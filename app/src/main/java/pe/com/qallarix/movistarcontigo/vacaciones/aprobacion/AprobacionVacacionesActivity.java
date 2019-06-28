package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.ResponseListaSolicitudes;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.ResponseSolicitudAprobacion;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.SolicitudAprobacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AprobacionVacacionesActivity extends TranquiParentActivity {

    private RecyclerView rvLista;
    private PendientesAdapter pendientesAdapter;
    private List<SolicitudAprobacion> solicitudAprobacions;
    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;

    private boolean isLoading = true;

    private ShimmerFrameLayout mShimmerViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprobacion_vacaciones);
        configurarToolbar();
        bindearVistas();
        configurarRecycler();
        cargarListaSolicitudesPendientes();
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

    private void cargarListaSolicitudesPendientes() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        solicitudAprobacions = new ArrayList<>();
        Call<ResponseListaSolicitudes> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceAprobacionVacacionesApi.class)
                .obtenerListaSolicitudesAprobar(getCIP(),1);
        call.enqueue(new Callback<ResponseListaSolicitudes>() {
            @Override
            public void onResponse(Call<ResponseListaSolicitudes> call,
                                   Response<ResponseListaSolicitudes> response) {
                if (response.code() == 200){
                    solicitudAprobacions = Arrays.asList(response.body().getList());
                    if (!solicitudAprobacions.isEmpty()){
                        pendientesAdapter.setSolicitudes(solicitudAprobacions);
                    }else{
                        mostrarEmptyView();
                    }
                } else {
                        mostrarMensaje500();
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseListaSolicitudes> call, Throwable t) {
                Toast.makeText(AprobacionVacacionesActivity.this, "Error del servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
                cargarListaSolicitudesPendientes();
            }
        });
    }

    private void mostrarEmptyView() {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.ic_empty_view_lista_vacaciones);
        tvMessageTitle.setText("Sin solicitudes pendientes");
        tvMessageMensaje.setText("Si deseas ver el reporte de tus solicitudes aprobadas o " +
                "rechazadas puedes hacerlo desde el SGV.");
        tvMessageBoton.setVisibility(View.GONE);

    }

    private void configurarRecycler() {
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setHasFixedSize(true);
        pendientesAdapter = new PendientesAdapter(this,
                new PendientesAdapter.OnClickPendiente() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(AprobacionVacacionesActivity.this,
                        DetalleAprobacionActivity.class);
                SolicitudAprobacion currentSolicitud = solicitudAprobacions.get(position);
                intent.putExtra("requestCode",currentSolicitud.getRequestCode());
                intent.putExtra("requestDay",currentSolicitud.getRequestDay());
                startActivity(intent);
            }
        });
        rvLista.setAdapter(pendientesAdapter);
    }

    private void bindearVistas() {
        rvLista = findViewById(R.id.vacaciones_aprobacion_rvLista);
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
        mShimmerViewContainer = findViewById(R.id.lista_vacaciones_shimerFrameLayout);
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Vacaciones para aprobar");
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
}
