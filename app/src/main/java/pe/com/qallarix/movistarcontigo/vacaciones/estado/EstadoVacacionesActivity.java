package pe.com.qallarix.movistarcontigo.vacaciones.estado;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.ResponseListaEstados;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstadoVacacionesActivity extends TranquiParentActivity {
    RecyclerView rvVacaciones;
    List<EstadoVacaciones> aprobadas;
    List<EstadoVacaciones> pendientes;
    List<EstadoVacaciones> rechazadas;
    EstadoVacacionesAdapter estadoVacacionesAdapter;
    private TextView tvMensajeViewLoader, tvEmptyViewMensaje;
    private View viewLoader, viewError, emptyView;
    private TabLayout tabLayout;
    private int currentTab;
    private final int PENDIENTES = 0;
    private final int APROBADAS = 1;
    private final int RECHAZADAS = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_vacaciones);
        configurarToolbar();
        bindearVistas();
        configurarRecyclerView();
        configurarTabs();
        cargarPendientes();
    }

    private void configurarTabs() {
        tabLayout = findViewById(R.id.estado_vacaciones_tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                currentTab = index;
                switch (index){
                    case APROBADAS: cargarAprobadas();break;
                    case PENDIENTES: cargarPendientes();break;
                    case RECHAZADAS: cargarRechazadas();break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void cargarAprobadas(){
        viewError.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        if (aprobadas == null){
            estadoVacacionesAdapter.setEstadoVacaciones(new ArrayList<EstadoVacaciones>());
            getAprobadasFromServices();
        }else{
            if (aprobadas.isEmpty()) emptyView.setVisibility(View.VISIBLE);
            else estadoVacacionesAdapter.setEstadoVacaciones(aprobadas);
        }
    }

    private void getAprobadasFromServices() {
        tvMensajeViewLoader.setText("Cargando lista de vacaciones aprobadas...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseListaEstados> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceEstadoVacacionesApi.class)
                .obtenerListaEstadoVacaciones(getCIP(),ServiceEstadoVacacionesApi.APROBADAS,1);
        call.enqueue(new Callback<ResponseListaEstados>() {
            @Override
            public void onResponse(Call<ResponseListaEstados> call, Response<ResponseListaEstados> response) {
                if (response.code() == 200){
                    aprobadas = Arrays.asList(response.body().getVacaciones());
                    if (aprobadas.size() == 0) mostrarEmptyView("No tienes vacaciones aprobadas " +
                            "hasta el momento");
                    else estadoVacacionesAdapter.setEstadoVacaciones(aprobadas);
                }else if (response.code() == 500){
                    viewError.setVisibility(View.VISIBLE);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseListaEstados> call, Throwable t) {
                Toast.makeText(EstadoVacacionesActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarPendientes() {
        viewError.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        if (pendientes == null){
            estadoVacacionesAdapter.setEstadoVacaciones(new ArrayList<EstadoVacaciones>());
            getPendientesFromServices();
        }else{
            if (pendientes.isEmpty()) emptyView.setVisibility(View.VISIBLE);
            else estadoVacacionesAdapter.setEstadoVacaciones(pendientes);
        }
    }

    private void getPendientesFromServices() {

        tvMensajeViewLoader.setText("Cargando lista de vacaciones pendientes...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseListaEstados> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceEstadoVacacionesApi.class)
                .obtenerListaEstadoVacaciones(getCIP(),ServiceEstadoVacacionesApi.PENDIENTES,1);
        call.enqueue(new Callback<ResponseListaEstados>() {
            @Override
            public void onResponse(Call<ResponseListaEstados> call, Response<ResponseListaEstados> response) {
                if (response.code() == 200){
                    pendientes = Arrays.asList(response.body().getVacaciones());
                    if (pendientes.size() == 0) mostrarEmptyView("No tienes vacaciones " +
                            "aprobadas hasta el momento");
                    else estadoVacacionesAdapter.setEstadoVacaciones(pendientes);
                }else if (response.code() == 500){
                    viewError.setVisibility(View.VISIBLE);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseListaEstados> call, Throwable t) {
                Toast.makeText(EstadoVacacionesActivity.this, "Error en el servidor",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarRechazadas(){
        viewError.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        if (rechazadas == null){
            estadoVacacionesAdapter.setEstadoVacaciones(new ArrayList<EstadoVacaciones>());
            getRechazadasFromServices();
        }else{
            if (rechazadas.isEmpty()) emptyView.setVisibility(View.VISIBLE);
            else estadoVacacionesAdapter.setEstadoVacaciones(rechazadas);
        }
    }

    private void getRechazadasFromServices() {
        tvMensajeViewLoader.setText("Cargando lista de vacaciones rechazadas...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseListaEstados> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceEstadoVacacionesApi.class)
                .obtenerListaEstadoVacaciones(getCIP(),ServiceEstadoVacacionesApi.RECHAZADAS,1);
        call.enqueue(new Callback<ResponseListaEstados>() {
            @Override
            public void onResponse(Call<ResponseListaEstados> call, Response<ResponseListaEstados> response) {
                if (response.code() == 200){
                    rechazadas = Arrays.asList(response.body().getVacaciones());
                    if (rechazadas.size() == 0) mostrarEmptyView("No tienes solicitudes de vacaciones" +
                            " rechazadas hasta le momento.");
                    else estadoVacacionesAdapter.setEstadoVacaciones(rechazadas);
                }else if (response.code() == 500){
                    viewError.setVisibility(View.VISIBLE);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseListaEstados> call, Throwable t) {
                Toast.makeText(EstadoVacacionesActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarEmptyView(String mensaje) {
        emptyView.setVisibility(View.VISIBLE);
        tvEmptyViewMensaje.setText(mensaje);
    }
    private void bindearVistas() {
        rvVacaciones = findViewById(R.id.estado_vacaciones_rvListaVacaciones);
        tvMensajeViewLoader = findViewById(R.id.viewLoad_tvMensaje);
        viewLoader = findViewById(R.id.lista_vacaciones_viewProgress);
        viewError = findViewById(R.id.view_error);
        emptyView = findViewById(R.id.empty_view_lista_vacaciones);
        tvEmptyViewMensaje = findViewById(R.id.empty_view_tvMensaje);
    }

    private void configurarRecyclerView() {
        rvVacaciones.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVacaciones.setLayoutManager(layoutManager);
        estadoVacacionesAdapter = new EstadoVacacionesAdapter(this,
                new EstadoVacacionesAdapter.EstadoOnClick() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(EstadoVacacionesActivity.this,
                        DetalleEstadoVacacionesActivity.class);
                EstadoVacaciones currentEstadoVacaciones = null;
                switch (currentTab){
                    case APROBADAS:
                        if (aprobadas != null) currentEstadoVacaciones = aprobadas.get(position);
                        break;
                    case RECHAZADAS:
                        if (rechazadas != null) currentEstadoVacaciones = rechazadas.get(position);
                        break;
                    case PENDIENTES:
                        if (pendientes != null) currentEstadoVacaciones = pendientes.get(position);
                        break;
                }
                if (currentEstadoVacaciones != null) {
                    String requestCode = currentEstadoVacaciones.getRequestCode();
                    intent.putExtra("requestCode",requestCode);
                    startActivity(intent);
                }
            }
        });
        rvVacaciones.setAdapter(estadoVacacionesAdapter);
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Estado de vacaciones");
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

    public void clickNull(View view) {
    }
}
