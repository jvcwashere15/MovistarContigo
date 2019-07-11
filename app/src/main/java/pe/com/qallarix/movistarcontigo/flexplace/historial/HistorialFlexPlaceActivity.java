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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
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
    List<FlexPlace> cancelados;
    HistorialFlexPlaceAdapter historialFlexPlaceAdapter;
    private TextView tvMensajeViewLoader;
    private View viewLoader;
    private TabLayout tabLayout;
    private final int PENDIENTES = 0;
    private final int APROBADAS = 1;
    private final int RECHAZADAS = 2;
    private final int CANCELADO = 3;

    private final String STATUS_PENDIENTES = "02";
    private final String STATUS_APROBADAS = "03";
    private final String STATUS_RECHAZADAS = "04";
    private final String STATUS_CANCELADO = "01";

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_flex_place);
        configurarToolbar();
        bindearVistas();
        configurarRecyclerView();
        configurarTabs();
        displayListaHistorial(PENDIENTES);
    }

    private void configurarTabs() {
        tabLayout = findViewById(R.id.flexplace_historial_tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                displayListaHistorial(index);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void displayListaHistorial(int tipoLista) {
        switch (tipoLista){
            case APROBADAS:
                cargarListaHistorial(tipoLista,STATUS_APROBADAS, aprobadas,
                        getString(R.string.flexplace_mensaje_carga_aprobadas),
                        getString(R.string.flexplace_mensaje_empty_aprobadas)); break;
            case PENDIENTES:
                cargarListaHistorial(tipoLista,STATUS_PENDIENTES, pendientes,
                        getString(R.string.flexplace_mensaje_carga_pendientes),
                        getString(R.string.flexplace_mensaje_empty_pendientes)); break;
            case RECHAZADAS:
                cargarListaHistorial(tipoLista,STATUS_RECHAZADAS, rechazadas,
                        getString(R.string.flexplace_mensaje_carga_rechazadas),
                        getString(R.string.flexplace_mensaje_empty_rechazadas)); break;
            case CANCELADO:
                cargarListaHistorial(tipoLista,STATUS_CANCELADO, cancelados,
                        getString(R.string.flexplace_mensaje_carga_cancelados),
                        getString(R.string.flexplace_mensaje_empty_cancelados)); break;
        }
    }

    private void cargarListaHistorial(int tipoLista,String status, List<FlexPlace> flexPlaces,
                                      String mensajeCarga, String mensajeEmpty){
        viewMessage.setVisibility(View.GONE);
        if (flexPlaces == null){
            historialFlexPlaceAdapter.setHistorialFlexPlace(new ArrayList<FlexPlace>());
            getListaFlexPlaceFromServices(tipoLista,status,mensajeCarga,mensajeEmpty);
        }else{
            if (flexPlaces.isEmpty()) mostrarEmptyView("", mensajeEmpty);
            else historialFlexPlaceAdapter.setHistorialFlexPlace(flexPlaces);
        }
    }

    private void getListaFlexPlaceFromServices(final int tipoLista, String status, String mensajeCarga, final String mensajeEmpty) {
        tvMensajeViewLoader.setText(mensajeCarga);
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseHistorialFlexPlace> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceHistorialApi.class)
                .getHistorialFlexPlace(status,2019);
        call.enqueue(new Callback<ResponseHistorialFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseHistorialFlexPlace> call, Response<ResponseHistorialFlexPlace> response) {
                if (response.code() == 200){
                    List<FlexPlace> flexPlaces = response.body().getList();
                    cargarLista(tipoLista,flexPlaces,mensajeEmpty);
                }else {
                    mostrarMensaje500(tipoLista);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseHistorialFlexPlace> call, Throwable t) {
                mostrarMensaje500(tipoLista);
            }
        });
    }

    private void cargarLista(int tipoLista, List<FlexPlace> flexPlaces,String mensajeEmpty) {
        switch (tipoLista){
            case APROBADAS: aprobadas = flexPlaces; break;
            case PENDIENTES: pendientes = flexPlaces; break;
            case RECHAZADAS: rechazadas = flexPlaces; break;
            case CANCELADO: cancelados = flexPlaces; break;
        }
        if (flexPlaces.size() == 0) mostrarEmptyView("", mensajeEmpty);
        else historialFlexPlaceAdapter.setHistorialFlexPlace(flexPlaces);
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
                        Intent intent = new Intent(HistorialFlexPlaceActivity.this,
                                DetalleFlexPlaceActivity.class);
                        FlexPlace currentFlexPlace = null;
                        switch (tabLayout.getSelectedTabPosition()){
                            case APROBADAS:
                                if (aprobadas != null && aprobadas.size()> 0) currentFlexPlace = aprobadas.get(position);
                                break;
                            case RECHAZADAS:
                                if (rechazadas != null && rechazadas.size() > 0) currentFlexPlace = rechazadas.get(position);
                                break;
                            case PENDIENTES:
                                if (pendientes != null && pendientes.size()> 0) currentFlexPlace = pendientes.get(position);
                                break;
                        }
                        if (currentFlexPlace != null) {
                            int requestCode = (int)currentFlexPlace.getId();
                            intent.putExtra("requestCode",requestCode);
                            startActivity(intent);
                        }
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
                displayListaHistorial(tipoLista);
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

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }

}
