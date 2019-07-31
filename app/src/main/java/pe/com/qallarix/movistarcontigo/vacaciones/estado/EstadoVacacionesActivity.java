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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
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
    private TextView tvMensajeViewLoader;
    private View viewLoader;
    private TabLayout tabLayout;
    private int tabSelected = 0;
    private int currentTab;
    private final int PENDIENTES = 0;
    private final int APROBADAS = 1;
    private final int RECHAZADAS = 2;

    private boolean mPantallaAnterior;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_vacaciones);
        configurarToolbar();
        bindearVistas();
        getDataFromExtras();
        configurarRecyclerView();
        configurarTabs();
        if (tabSelected != 0){
            TabLayout.Tab tab = tabLayout.getTabAt(tabSelected);
            tab.select();
        }else{
            cargarPendientes();
        }
    }

    private void getDataFromExtras() {
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("tabSelected")){
            tabSelected = getIntent().getExtras().getInt("tabSelected",0);
        }
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("lista_notificaciones")){
            mPantallaAnterior = getIntent().getExtras().getBoolean("lista_notificaciones",false);
        }
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
        viewMessage.setVisibility(View.GONE);
        if (aprobadas == null){
            estadoVacacionesAdapter.setEstadoVacaciones(new ArrayList<EstadoVacaciones>());
            getAprobadasFromServices();
        }else{
            displayDataListAprobadas();
        }
    }

    private void getAprobadasFromServices() {
        tvMensajeViewLoader.setText("Cargando lista de vacaciones aprobadas...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseListaEstados> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceEstadoVacacionesApi.class)
                .obtenerListaEstadoVacaciones(getCIP(),
                        ServiceEstadoVacacionesApi.APROBADAS,1);
        call.enqueue(new Callback<ResponseListaEstados>() {
            @Override
            public void onResponse(Call<ResponseListaEstados> call,
                                   Response<ResponseListaEstados> response) {
                if (response.code() == 200){
                    aprobadas = Arrays.asList(response.body().getVacaciones());
                    displayDataListAprobadas();
                }else {
                    mostrarMensaje500(APROBADAS);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseListaEstados> call, Throwable t) {
                mostrarMensaje500(APROBADAS);
            }
        });
    }

    private void displayDataListAprobadas() {
        if (aprobadas.size() == 0) mostrarEmptyView("", "No tienes " +
                "solicitudes de vacaciones aprobadas hasta el momento. En caso de tener " +
                "registradas vacaciones masivas, podrás visualizarlas desde el SGV.");
        else estadoVacacionesAdapter.setEstadoVacaciones(aprobadas);
    }

    private void cargarPendientes() {
        viewMessage.setVisibility(View.GONE);
        if (pendientes == null){
            estadoVacacionesAdapter.setEstadoVacaciones(new ArrayList<EstadoVacaciones>());
            getPendientesFromServices();
        }else{
            if (pendientes.isEmpty()) mostrarEmptyView("¡Genial!",
                    "Todas tus solicitudes de vacaciones han sido atendidas.");
            else estadoVacacionesAdapter.setEstadoVacaciones(pendientes);
        }
    }

    private void getPendientesFromServices() {

        tvMensajeViewLoader.setText("Cargando lista de vacaciones pendientes...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseListaEstados> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceEstadoVacacionesApi.class)
                .obtenerListaEstadoVacaciones(getCIP(),
                        ServiceEstadoVacacionesApi.PENDIENTES,1);
        call.enqueue(new Callback<ResponseListaEstados>() {
            @Override
            public void onResponse(Call<ResponseListaEstados> call,
                                   Response<ResponseListaEstados> response) {
                if (response.code() == 200){
                    pendientes = Arrays.asList(response.body().getVacaciones());
                    if (pendientes.size() == 0) mostrarEmptyView("¡Genial!",
                            "Todas tus solicitudes de vacaciones\n" +
                            "han sido atendidas.");
                    else estadoVacacionesAdapter.setEstadoVacaciones(pendientes);
                }else {
                    mostrarMensaje500(PENDIENTES);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseListaEstados> call, Throwable t) {
                mostrarMensaje500(PENDIENTES);
            }
        });
    }

    private void cargarRechazadas(){
        viewMessage.setVisibility(View.GONE);
        if (rechazadas == null){
            estadoVacacionesAdapter.setEstadoVacaciones(new ArrayList<EstadoVacaciones>());
            getRechazadasFromServices();
        }else{
            if (rechazadas.isEmpty()) mostrarEmptyView("¡Genial!",
                    "No tienes solicitudes de vacaciones rechazadas hasta el momento.");
            else estadoVacacionesAdapter.setEstadoVacaciones(rechazadas);
        }
    }

    private void getRechazadasFromServices() {
        tvMensajeViewLoader.setText("Cargando lista de vacaciones rechazadas...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseListaEstados> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceEstadoVacacionesApi.class)
                .obtenerListaEstadoVacaciones(getCIP(),
                        ServiceEstadoVacacionesApi.RECHAZADAS,1);
        call.enqueue(new Callback<ResponseListaEstados>() {
            @Override
            public void onResponse(Call<ResponseListaEstados> call,
                                   Response<ResponseListaEstados> response) {
                if (response.code() == 200){
                    rechazadas = Arrays.asList(response.body().getVacaciones());
                    if (rechazadas.size() == 0) mostrarEmptyView("¡Genial!",
                            "No tienes solicitudes de vacaciones" +
                            " rechazadas hasta el momento.");
                    else estadoVacacionesAdapter.setEstadoVacaciones(rechazadas);
                }else {
                    mostrarMensaje500(RECHAZADAS);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseListaEstados> call, Throwable t) {
                mostrarMensaje500(RECHAZADAS);
            }
        });
    }

    private void bindearVistas() {
        rvVacaciones = findViewById(R.id.estado_vacaciones_rvListaVacaciones);
        tvMensajeViewLoader = findViewById(R.id.viewLoad_tvMensaje);
        viewLoader = findViewById(R.id.lista_vacaciones_viewProgress);
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
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
                        if (aprobadas != null && aprobadas.size()> 0)
                            currentEstadoVacaciones = aprobadas.get(position);
                        break;
                    case RECHAZADAS:
                        if (rechazadas != null && rechazadas.size() > 0)
                            currentEstadoVacaciones = rechazadas.get(position);
                        break;
                    case PENDIENTES:
                        if (pendientes != null && pendientes.size()> 0)
                            currentEstadoVacaciones = pendientes.get(position);
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

    public void clickNull(View view) {
    }

    private void mostrarMensaje500(final int tipoLista) {
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
                switch (tipoLista){
                    case APROBADAS: cargarAprobadas(); break;
                    case PENDIENTES: cargarPendientes(); break;
                    case RECHAZADAS: cargarRechazadas(); break;
                }
            }
        });
    }


    private void mostrarEmptyView(String title, String mensaje) {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.ic_empty_view_lista_vacaciones);
        tvMessageTitle.setText(title);
        tvMessageMensaje.setText(mensaje);
        tvMessageBoton.setVisibility(View.GONE);
    }
}
