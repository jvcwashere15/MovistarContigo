package pe.com.qallarix.movistarcontigo.flexplace.historial;

import android.content.Intent;
import android.support.design.widget.TabLayout;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.FlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.ResponseHistorialFlexPlace;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.util.WebService3;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.DetalleEstadoVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.EstadoVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.EstadoVacacionesAdapter;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.ServiceEstadoVacacionesApi;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.ResponseListaEstados;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialFlexPlaceActivity extends TranquiParentActivity {

    RecyclerView rvVacaciones;
    List<FlexPlace> aprobadas;
    List<FlexPlace> pendientes;
    List<FlexPlace> rechazadas;
    HistorialFlexPlaceAdapter historialFlexPlaceAdapter;
    private TextView tvMensajeViewLoader;
    private View viewLoader;
    private TabLayout tabLayout;
    private int mAnio;
    private int currentTab;
    private final int PENDIENTES = 0;
    private final int APROBADAS = 1;
    private final int RECHAZADAS = 2;
    private final int CANCELADO = 3;

    private final int STATUS_PENDIENTES = 1;
    private final int STATUS_APROBADAS = 2;
    private final int STATUS_RECHAZADAS = 3;
    private final int STATUS_CANCELADO = 4;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_flex_place);
        setCurrentAnio();
        configurarToolbar();
        bindearVistas();
        configurarRecyclerView();
        configurarTabs();
        cargarPendientes();
    }

    private void setCurrentAnio() {
        mAnio = Calendar.getInstance().get(Calendar.YEAR);
    }

    private void configurarTabs() {
        tabLayout = findViewById(R.id.flexplace_historial_tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                currentTab = index;
                switch (index){
                    case APROBADAS: cargarAprobadas();break;
                    case PENDIENTES: cargarPendientes();break;
                    case RECHAZADAS: cargarRechazadas();break;
//                    case CANCELADO: cargarRechazadas();break;
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
            historialFlexPlaceAdapter.setHistorialFlexPlace(new ArrayList<FlexPlace>());
            getAprobadasFromServices();
        }else{
            if (aprobadas.isEmpty()) mostrarEmptyView("",
                    "No tienes solicitudes FlexPlace aprobadas hasta el momento");
            else historialFlexPlaceAdapter.setHistorialFlexPlace(aprobadas);
        }
    }

    private void getAprobadasFromServices() {
        tvMensajeViewLoader.setText("Cargando lista de solicitudes FlexPlace aprobadas...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseHistorialFlexPlace> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceHistorialApi.class)
                .getHistorialFlexPlace(getDocumentNumber(),STATUS_APROBADAS,mAnio);

        call.enqueue(new Callback<ResponseHistorialFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseHistorialFlexPlace> call, Response<ResponseHistorialFlexPlace> response) {
                if (response.code() == 200){
                    aprobadas = Arrays.asList(response.body().getHistorialFlexPlace());
                    if (aprobadas.size() == 0) mostrarEmptyView("", "No tienes " +
                            "solicitudes de FlexPlace aprobadas hasta el momento.");
                    else historialFlexPlaceAdapter.setHistorialFlexPlace(aprobadas);
                }else {
                    mostrarMensaje500(APROBADAS);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseHistorialFlexPlace> call, Throwable t) {
                mostrarMensaje500(APROBADAS);
            }
        });
    }

    private void cargarPendientes() {
        viewMessage.setVisibility(View.GONE);
        if (pendientes == null){
            historialFlexPlaceAdapter.setHistorialFlexPlace(new ArrayList<FlexPlace>());
            getPendientesFromServices();
        }else{
            if (pendientes.isEmpty()) mostrarEmptyView("¡Genial!",
                    "Todas tus solicitudes de FlexPlace han sido atendidas.");
            else historialFlexPlaceAdapter.setHistorialFlexPlace(pendientes);
        }
    }

    private void getPendientesFromServices() {

        tvMensajeViewLoader.setText("Cargando lista de vacaciones pendientes...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseHistorialFlexPlace> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceHistorialApi.class)
                .getHistorialFlexPlace(getDocumentNumber(),STATUS_PENDIENTES,mAnio);

        call.enqueue(new Callback<ResponseHistorialFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseHistorialFlexPlace> call, Response<ResponseHistorialFlexPlace> response) {
                if (response.code() == 200){
                    pendientes = Arrays.asList(response.body().getHistorialFlexPlace());
                    if (pendientes.size() == 0) mostrarEmptyView("¡Genial!",
                            "Todas tus solicitudes de vacaciones\n" +
                                    "han sido atendidas.");
                    else historialFlexPlaceAdapter.setHistorialFlexPlace(pendientes);
                }else {
                    mostrarMensaje500(PENDIENTES);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseHistorialFlexPlace> call, Throwable t) {
                mostrarMensaje500(PENDIENTES);
            }
        });
    }

    private void cargarRechazadas(){
        viewMessage.setVisibility(View.GONE);
        if (rechazadas == null){
            historialFlexPlaceAdapter.setHistorialFlexPlace(new ArrayList<FlexPlace>());
            getRechazadasFromServices();
        }else{
            if (rechazadas.isEmpty()) mostrarEmptyView("¡Genial!",
                    "No tienes solicitudes de vacaciones rechazadas hasta el momento.");
            else historialFlexPlaceAdapter.setHistorialFlexPlace(rechazadas);
        }
    }

    private void getRechazadasFromServices() {
        tvMensajeViewLoader.setText("Cargando lista de vacaciones rechazadas...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseHistorialFlexPlace> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceHistorialApi.class)
                .getHistorialFlexPlace(getDocumentNumber(),STATUS_RECHAZADAS,mAnio);

        call.enqueue(new Callback<ResponseHistorialFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseHistorialFlexPlace> call, Response<ResponseHistorialFlexPlace> response) {
                if (response.code() == 200){
                    rechazadas = Arrays.asList(response.body().getHistorialFlexPlace());
                    if (rechazadas.size() == 0) mostrarEmptyView("¡Genial!",
                            "No tienes solicitudes de vacaciones" +
                                    " rechazadas hasta el momento.");
                    else historialFlexPlaceAdapter.setHistorialFlexPlace(rechazadas);
                }else {
                    mostrarMensaje500(RECHAZADAS);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseHistorialFlexPlace> call, Throwable t) {
                mostrarMensaje500(RECHAZADAS);
            }
        });
    }

    private void bindearVistas() {
        rvVacaciones = findViewById(R.id.flexplace_historial_rvListaVacaciones);
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
        historialFlexPlaceAdapter = new HistorialFlexPlaceAdapter(this,
                new HistorialFlexPlaceAdapter.EstadoOnClick() {
                    @Override
                    public void onClick(View v, int position) {
//                        Intent intent = new Intent(HistorialFlexPlaceActivity.this,
//                                DetalleEstadoVacacionesActivity.class);
//                        EstadoVacaciones currentEstadoVacaciones = null;
//                        switch (currentTab){
//                            case APROBADAS:
//                                if (aprobadas != null && aprobadas.size()> 0) currentEstadoVacaciones = aprobadas.get(position);
//                                break;
//                            case RECHAZADAS:
//                                if (rechazadas != null && rechazadas.size() > 0) currentEstadoVacaciones = rechazadas.get(position);
//                                break;
//                            case PENDIENTES:
//                                if (pendientes != null && pendientes.size()> 0) currentEstadoVacaciones = pendientes.get(position);
//                                break;
//                        }
//                        if (currentEstadoVacaciones != null) {
//                            String requestCode = currentEstadoVacaciones.getRequestCode();
//                            intent.putExtra("requestCode",requestCode);
//                            startActivity(intent);
//                        }
                    }
                });
        rvVacaciones.setAdapter(historialFlexPlaceAdapter);
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

    public void clickNull(View view) {}

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mi historial FlexPlace");
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
}
