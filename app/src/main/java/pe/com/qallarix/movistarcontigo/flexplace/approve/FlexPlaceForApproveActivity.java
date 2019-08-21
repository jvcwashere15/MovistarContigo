package pe.com.qallarix.movistarcontigo.flexplace.approve;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
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
import pe.com.qallarix.movistarcontigo.flexplace.approve.pojos.ResponseSolicitudesFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.approve.pojos.SolicitudFlex;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlexPlaceForApproveActivity extends TranquiParentActivity {
    RecyclerView rvSolicitudesFlex;
    List<SolicitudFlex> aprobadas;
    List<SolicitudFlex> pendientes;
    List<SolicitudFlex> rechazadas;
    List<SolicitudFlex> cancelados;

    private int anio1 = 0,anio2=0,anio3=0,anio4=0;

    private Spinner spAnioPendiente,spAnioAprobado,spAnioRechazado,spAnioCancelado;
    SolicitudesFlexPlaceAdapter solicitudesFlexPlaceAdapter;

    private TextView tvMensajeViewLoader;
    private View viewLoader;
    private TabLayout tabLayout;

    private final int PENDIENTES = 0;
    private final int APROBADAS = 1;
    private final int RECHAZADAS = 2;
    private final int CANCELADO = 3;

    private SwipeRefreshLayout swipeRefreshLayout;

    private final String STATUS_PENDIENTES = "02";
    private final String STATUS_APROBADAS = "03";
    private final String STATUS_RECHAZADAS = "04";
    private final String STATUS_CANCELADO = "01";

    private int anioActual;
    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_flex_place);
        anioActual = Calendar.getInstance().get(Calendar.YEAR);
        configurarToolbar();
        bindearVistas();
        configurarRecyclerView();
        configurarTabs();
        displayListaHistorial(PENDIENTES,false);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayListaHistorial(tabLayout.getSelectedTabPosition(),true);
            }
        });
    }



    private void configurarTabs() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                displayListaHistorial(index,false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void displayListaHistorial(int tipoLista,boolean recargar) {

        spAnioCancelado.setVisibility(View.GONE);
        spAnioRechazado.setVisibility(View.GONE);
        spAnioAprobado.setVisibility(View.GONE);
        spAnioPendiente.setVisibility(View.GONE);

        switch (tipoLista){
            case APROBADAS:
                spAnioAprobado.setVisibility(View.VISIBLE);
                cargarListaHistorial(spAnioAprobado,tipoLista,STATUS_APROBADAS, aprobadas,
                        getString(R.string.flexplace_mensaje_carga_aprobadas),
                        getString(R.string.flexplace_mensaje_empty_aprobadas),recargar); break;
            case PENDIENTES:
                spAnioPendiente.setVisibility(View.VISIBLE);
                cargarListaHistorial(spAnioPendiente,tipoLista,STATUS_PENDIENTES, pendientes,
                        getString(R.string.flexplace_mensaje_carga_pendientes),
                        getString(R.string.flexplace_mensaje_empty_pendientes),recargar); break;
            case RECHAZADAS:
                spAnioRechazado.setVisibility(View.VISIBLE);
                cargarListaHistorial(spAnioRechazado,tipoLista,STATUS_RECHAZADAS, rechazadas,
                        getString(R.string.flexplace_mensaje_carga_rechazadas),
                        getString(R.string.flexplace_mensaje_empty_rechazadas),recargar); break;
            case CANCELADO:
                spAnioCancelado.setVisibility(View.VISIBLE);
                cargarListaHistorial(spAnioCancelado,tipoLista,STATUS_CANCELADO, cancelados,
                        getString(R.string.flexplace_mensaje_carga_cancelados),
                        getString(R.string.flexplace_mensaje_empty_cancelados),recargar); break;
        }
    }

    private void cargarListaHistorial(Spinner spinner, final int tipoLista, final String status, List<SolicitudFlex> solicitudesFlex,
                                      final String mensajeCarga, final String mensajeEmpty,boolean recargar){
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        viewMessage.setVisibility(View.GONE);
        if (solicitudesFlex == null || recargar){
            solicitudesFlexPlaceAdapter.setHistorialFlexPlace(new ArrayList<SolicitudFlex>());
            switch (tipoLista){
                case PENDIENTES:
                    if (spAnioPendiente.getOnItemSelectedListener() == null)
                        spAnioPendiente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (++anio1 > 1){
                                    getListaFlexPlaceFromServices(
                                            Integer.parseInt(spAnioPendiente.getSelectedItem().toString()),
                                            tipoLista,status,mensajeCarga,mensajeEmpty);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });
                    break;
                case APROBADAS:
                    if (spAnioAprobado.getOnItemSelectedListener() == null)
                        spAnioAprobado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (++anio2 > 1){
                                    getListaFlexPlaceFromServices(
                                            Integer.parseInt(spAnioAprobado.getSelectedItem().toString()),
                                            tipoLista,status,mensajeCarga,mensajeEmpty);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });
                    break;
                case RECHAZADAS :
                    if (spAnioRechazado.getOnItemSelectedListener() == null)
                        spAnioRechazado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (++anio3 > 1){
                                    getListaFlexPlaceFromServices(
                                            Integer.parseInt(spAnioRechazado.getSelectedItem().toString()),
                                            tipoLista,status,mensajeCarga,mensajeEmpty);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });
                    break;
                case CANCELADO :
                    if (spAnioCancelado.getOnItemSelectedListener() == null)
                        spAnioCancelado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (++anio4 > 1){
                                    getListaFlexPlaceFromServices(
                                            Integer.parseInt(spAnioCancelado.getSelectedItem().toString()),
                                            tipoLista,status,mensajeCarga,mensajeEmpty);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });
                    break;
            }
            String[] aniosFlex = getResources().getStringArray(R.array.anios_flex);
            List<String> anios = Arrays.asList(aniosFlex);
            int index = 0;
            index = anios.indexOf(String.valueOf(anioActual));
            spinner.setSelection(index);
            getListaFlexPlaceFromServices(anioActual,tipoLista,status,mensajeCarga,mensajeEmpty);
        }else{
            if (solicitudesFlex.isEmpty()) mostrarEmptyView("", mensajeEmpty);
            else solicitudesFlexPlaceAdapter.setHistorialFlexPlace(solicitudesFlex);
        }
    }

    private void getListaFlexPlaceFromServices(int anio, final int tipoLista, String status, String mensajeCarga, final String mensajeEmpty) {
        solicitudesFlexPlaceAdapter.setHistorialFlexPlace(new ArrayList<SolicitudFlex>());
        viewMessage.setVisibility(View.GONE);
        tvMensajeViewLoader.setText(mensajeCarga);
        viewLoader.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        Call<ResponseSolicitudesFlexPlace> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceSolicitudesApi.class)
                .getSolicitudesFlexPlace(status,anio);
        call.enqueue(new Callback<ResponseSolicitudesFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseSolicitudesFlexPlace> call, Response<ResponseSolicitudesFlexPlace> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.code() == 200){
                    List<SolicitudFlex> flexPlaces = response.body().getList();
                    cargarLista(tipoLista,flexPlaces,mensajeEmpty);
                }else {
                    mostrarMensaje500(tipoLista);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseSolicitudesFlexPlace> call, Throwable t) {
                mostrarMensaje500(tipoLista);
            }
        });
    }

    private void cargarLista(int tipoLista, List<SolicitudFlex> solicitudFlexes,String mensajeEmpty) {
        switch (tipoLista){
            case APROBADAS: aprobadas = solicitudFlexes; break;
            case PENDIENTES: pendientes = solicitudFlexes; break;
            case RECHAZADAS: rechazadas = solicitudFlexes; break;
            case CANCELADO: cancelados = solicitudFlexes; break;
        }
        if (solicitudFlexes.size() == 0)
            mostrarEmptyView("", mensajeEmpty);
        else{
            rvSolicitudesFlex.setVisibility(View.VISIBLE);
            solicitudesFlexPlaceAdapter.setHistorialFlexPlace(solicitudFlexes);
        }

    }

    private void configurarRecyclerView() {
        rvSolicitudesFlex.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvSolicitudesFlex.setLayoutManager(layoutManager);
        solicitudesFlexPlaceAdapter = new SolicitudesFlexPlaceAdapter(this,
                new SolicitudesFlexPlaceAdapter.SolicitudOnClick() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent = new Intent(FlexPlaceForApproveActivity.this,
                                DetalleSolicitudFlexActivity.class);
                        SolicitudFlex currentSolicitudFlex = null;
                        switch (tabLayout.getSelectedTabPosition()){
                            case APROBADAS:
                                if (aprobadas != null && aprobadas.size()> 0) currentSolicitudFlex = aprobadas.get(position);
                                break;
                            case RECHAZADAS:
                                if (rechazadas != null && rechazadas.size() > 0) currentSolicitudFlex = rechazadas.get(position);
                                break;
                            case PENDIENTES:
                                if (pendientes != null && pendientes.size()> 0) {
                                    currentSolicitudFlex = pendientes.get(position);
                                    intent = new Intent(FlexPlaceForApproveActivity.this,
                                            DetalleFlexPendienteActivity.class);
                                }
                                break;
                            case CANCELADO:
                                if (cancelados != null && cancelados.size()> 0) currentSolicitudFlex = cancelados.get(position);
                                break;
                        }
                        if (currentSolicitudFlex != null) {
                            int requestCode = (int)currentSolicitudFlex.getId();
                            intent.putExtra("requestCode",requestCode);
                            startActivity(intent);
                        }
                    }
                });
        rvSolicitudesFlex.setAdapter(solicitudesFlexPlaceAdapter);
    }

    private void mostrarMensaje500(final int tipoLista) {
        swipeRefreshLayout.setVisibility(View.GONE);
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
                displayListaHistorial(tipoLista,false);
            }
        });
    }

    private void mostrarEmptyView(String title, String mensaje) {
        swipeRefreshLayout.setVisibility(View.GONE);
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.ic_empty_view_lista_vacaciones);
        tvMessageTitle.setText(title);
        tvMessageMensaje.setText(mensaje);
        tvMessageBoton.setVisibility(View.GONE);
    }

    private void bindearVistas() {
        //tabs de solicitudes
        tabLayout = findViewById(R.id.solicitudes_flex_tabLayout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        //spinners
        spAnioPendiente = findViewById(R.id.solicitudes_flex_spFiltroAnio1);
        spAnioAprobado = findViewById(R.id.solicitudes_flex_spFiltroAnio2);
        spAnioRechazado = findViewById(R.id.solicitudes_flex_spFiltroAnio3);
        spAnioCancelado = findViewById(R.id.solicitudes_flex_spFiltroAnio4);

        //recycler para lista de solicitudes
        rvSolicitudesFlex = findViewById(R.id.solicitudes_flex_rvListaSolicitudes);

        //view para carga
        tvMensajeViewLoader = findViewById(R.id.viewLoad_tvMensaje);
        viewLoader = findViewById(R.id.lista_solicitudes_viewProgress);

        //view para respuesta server
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
    }


    @Override
    public void onBackPressed() {
        goToParentActivity();
    }



    public void clickNull(View view) {
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FlexPlace para aprobar");
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
