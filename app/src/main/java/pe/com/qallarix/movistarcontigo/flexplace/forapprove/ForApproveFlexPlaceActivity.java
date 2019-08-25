package pe.com.qallarix.movistarcontigo.flexplace.forapprove;

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
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos.ResponseSolicitudesFlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.pojos.SolicitudFlex;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForApproveFlexPlaceActivity extends TranquiParentActivity {
    RecyclerView rvFlexRequests;
    List<SolicitudFlex> approveds;
    List<SolicitudFlex> awaitings;
    List<SolicitudFlex> rejecteds;
    List<SolicitudFlex> canceleds;

    private int year1 = 0, year2 = 0, year3 = 0, year4 = 0;

    private Spinner spAwaitingYear, spApprovedYear, spRejectedYear, spCanceledYear;
    FlexPlaceRequestAdapter flexPlaceRequestAdapter;

    private TextView tvMensajeViewLoading;
    private View viewLoading;
    private TabLayout tabLayout;

    private final int AWAITING = 0;
    private final int APPROVED = 1;
    private final int REJECTED = 2;
    private final int CANCELED = 3;

    private int initTab = AWAITING;

    private SwipeRefreshLayout swipeRefreshLayout;

    private final String STATUS_AWAITING = "02";
    private final String STATUS_APPROVED = "03";
    private final String STATUS_REJECTED = "04";
    private final String STATUS_CANCELED = "01";

    private int currentYear;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_for_approve);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        getDataFromIntent();
        bindViews();
        configurarToolbar();
        setUpRecyclerView();
        setUpTabs();
        setInitTab(initTab);
        displayHistoryList(initTab, false);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayHistoryList(tabLayout.getSelectedTabPosition(), true);
            }
        });
    }

    private void getDataFromIntent() {
        Bundle bundleExtras = getIntent().getExtras();
        if (bundleExtras!= null && bundleExtras.containsKey("initTab"))
            initTab = bundleExtras.getInt("initTab",AWAITING);
    }

    private void setInitTab(int initTab) {
        TabLayout.Tab tab = tabLayout.getTabAt(initTab);
        tab.select();
    }


    private void setUpTabs() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                displayHistoryList(index, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void displayHistoryList(int tipoLista, boolean recargar) {
        spCanceledYear.setVisibility(View.GONE);
        spRejectedYear.setVisibility(View.GONE);
        spApprovedYear.setVisibility(View.GONE);
        spAwaitingYear.setVisibility(View.GONE);
        switch (tipoLista) {
            case APPROVED:
                spApprovedYear.setVisibility(View.VISIBLE);
                cargarListaHistorial(spApprovedYear, tipoLista, STATUS_APPROVED, approveds,
                        getString(R.string.flexplace_mensaje_carga_aprobadas),
                        getString(R.string.flexplace_forapproved_emptyview_approved_title),
                        getString(R.string.flexplace_forapproved_emptyview_approved_message),
                        recargar);
                break;
            case AWAITING:
                spAwaitingYear.setVisibility(View.VISIBLE);
                cargarListaHistorial(spAwaitingYear, tipoLista, STATUS_AWAITING, awaitings,
                        getString(R.string.flexplace_mensaje_carga_pendientes),
                        getString(R.string.flexplace_forapproved_emptyview_awaiting_title),
                        getString(R.string.flexplace_forapproved_emptyview_awaiting_message),
                        recargar);
                break;
            case REJECTED:
                spRejectedYear.setVisibility(View.VISIBLE);
                cargarListaHistorial(spRejectedYear, tipoLista, STATUS_REJECTED, rejecteds,
                        getString(R.string.flexplace_mensaje_carga_rechazadas),
                        getString(R.string.flexplace_forapproved_emptyview_rejected_title),
                        getString(R.string.flexplace_forapproved_emptyview_rejected_message),
                        recargar);
                break;
            case CANCELED:
                spCanceledYear.setVisibility(View.VISIBLE);
                cargarListaHistorial(spCanceledYear, tipoLista, STATUS_CANCELED, canceleds,
                        getString(R.string.flexplace_mensaje_carga_cancelados),
                        getString(R.string.flexplace_forapproved_emptyview_canceled_title),
                        getString(R.string.flexplace_forapproved_emptyview_canceled_message),
                        recargar);
                break;
        }
    }

    private void cargarListaHistorial(Spinner spinner, final int tipoLista, final String status,
                                      List<SolicitudFlex> solicitudesFlex, final String mensajeCarga,
                                      final String titleEmpty, final String messageEmpty,
                                      boolean recargar) {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        viewMessage.setVisibility(View.GONE);
        if (solicitudesFlex == null || recargar) {
            flexPlaceRequestAdapter.setHistorialFlexPlace(new ArrayList<SolicitudFlex>());
            switch (tipoLista) {
                case AWAITING:
                    if (spAwaitingYear.getOnItemSelectedListener() == null)
                        spAwaitingYear.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position, long id) {
                                        if (++year1 > 1) {
                                            getListaFlexPlaceFromServices(
                                                    Integer.parseInt(
                                                            spAwaitingYear.getSelectedItem().toString()),
                                                    tipoLista, status, mensajeCarga, titleEmpty,
                                                    messageEmpty);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                    break;
                case APPROVED:
                    if (spApprovedYear.getOnItemSelectedListener() == null)
                        spApprovedYear.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position, long id) {
                                        if (++year2 > 1) {
                                            getListaFlexPlaceFromServices(
                                                    Integer.parseInt(
                                                            spApprovedYear.getSelectedItem().toString()),
                                                    tipoLista, status, mensajeCarga, titleEmpty,
                                                    messageEmpty);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                    break;
                case REJECTED:
                    if (spRejectedYear.getOnItemSelectedListener() == null)
                        spRejectedYear.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position, long id) {
                                        if (++year3 > 1) {
                                            getListaFlexPlaceFromServices(
                                                    Integer.parseInt(
                                                            spRejectedYear.getSelectedItem().toString()),
                                                    tipoLista, status, mensajeCarga, titleEmpty,
                                                    messageEmpty);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                    break;
                case CANCELED:
                    if (spCanceledYear.getOnItemSelectedListener() == null)
                        spCanceledYear.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position, long id) {
                                        if (++year4 > 1) {
                                            getListaFlexPlaceFromServices(
                                                    Integer.parseInt(
                                                            spCanceledYear.getSelectedItem().toString()),
                                                    tipoLista, status, mensajeCarga, titleEmpty,
                                                    messageEmpty);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                    break;
            }
            String[] aniosFlex = getResources().getStringArray(R.array.anios_flex);
            List<String> anios = Arrays.asList(aniosFlex);
            int index = 0;
            index = anios.indexOf(String.valueOf(currentYear));
            spinner.setSelection(index);
            getListaFlexPlaceFromServices(currentYear, tipoLista, status, mensajeCarga,
                    titleEmpty, messageEmpty);
        } else {
            if (solicitudesFlex.isEmpty()) displayEmptyView(titleEmpty, messageEmpty);
            else flexPlaceRequestAdapter.setHistorialFlexPlace(solicitudesFlex);
        }
    }

    private void getListaFlexPlaceFromServices(int anio, final int tipoLista, String status,
                                               String mensajeCarga, final String titleEmpty,
                                               final String mensajeEmpty) {
        flexPlaceRequestAdapter.setHistorialFlexPlace(new ArrayList<SolicitudFlex>());
        viewMessage.setVisibility(View.GONE);
        tvMensajeViewLoading.setText(mensajeCarga);
        viewLoading.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        Call<ResponseSolicitudesFlexPlace> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceforApproveApi.class)
                .getSolicitudesFlexPlace(status, anio);
        call.enqueue(new Callback<ResponseSolicitudesFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseSolicitudesFlexPlace> call, Response<ResponseSolicitudesFlexPlace> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.code() == 200) {
                    List<SolicitudFlex> flexPlaces = response.body().getList();
                    cargarLista(tipoLista, flexPlaces,titleEmpty, mensajeEmpty);
                } else {
                    displayMessage500(tipoLista);
                }
                viewLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseSolicitudesFlexPlace> call, Throwable t) {
                displayMessage500(tipoLista);
                viewLoading.setVisibility(View.GONE);
            }
        });
    }

    private void cargarLista(int tipoLista, List<SolicitudFlex> solicitudFlexes,
                             String titleEmpty, String messageEmpty) {
        switch (tipoLista) {
            case APPROVED:
                approveds = solicitudFlexes;
                break;
            case AWAITING:
                awaitings = solicitudFlexes;
                break;
            case REJECTED:
                rejecteds = solicitudFlexes;
                break;
            case CANCELED:
                canceleds = solicitudFlexes;
                break;
        }
        if (solicitudFlexes.size() == 0)
            displayEmptyView(titleEmpty, messageEmpty);
        else {
            rvFlexRequests.setVisibility(View.VISIBLE);
            flexPlaceRequestAdapter.setHistorialFlexPlace(solicitudFlexes);
        }

    }

    private void setUpRecyclerView() {
        rvFlexRequests.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvFlexRequests.setLayoutManager(layoutManager);
        flexPlaceRequestAdapter = new FlexPlaceRequestAdapter(this,
                new FlexPlaceRequestAdapter.SolicitudOnClick() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent = new Intent(ForApproveFlexPlaceActivity.this,
                                ForApproveFlexRequestDetailActivity.class);
                        SolicitudFlex currentSolicitudFlex = null;
                        switch (tabLayout.getSelectedTabPosition()) {
                            case APPROVED:
                                if (approveds != null && approveds.size() > 0)
                                    currentSolicitudFlex = approveds.get(position);
                                break;
                            case REJECTED:
                                if (rejecteds != null && rejecteds.size() > 0)
                                    currentSolicitudFlex = rejecteds.get(position);
                                break;
                            case AWAITING:
                                if (awaitings != null && awaitings.size() > 0) {
                                    currentSolicitudFlex = awaitings.get(position);
                                    intent = new Intent(ForApproveFlexPlaceActivity.this,
                                            ForApproveFlexAwaitingDetailActivity.class);
                                }
                                break;
                            case CANCELED:
                                if (canceleds != null && canceleds.size() > 0)
                                    currentSolicitudFlex = canceleds.get(position);
                                break;
                        }
                        if (currentSolicitudFlex != null) {
                            int requestCode = (int) currentSolicitudFlex.getId();
                            intent.putExtra("requestCode", requestCode);
                            startActivity(intent);
                        }
                    }
                });
        rvFlexRequests.setAdapter(flexPlaceRequestAdapter);
    }

    private void displayMessage500(final int tipoLista) {
        ivMessageImagen.setImageResource(R.drawable.img_error_servidor);
        tvMessageTitle.setText(getString(R.string.flexplace_error500_title));
        tvMessageMensaje.setText(getString(R.string.flexplace_error500_message));
        tvMessageBoton.setText(getString(R.string.flexplace_error500_textButton));
        tvMessageBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHistoryList(tipoLista, true);
            }
        });
        tvMessageBoton.setVisibility(View.VISIBLE);
        viewMessage.setVisibility(View.VISIBLE);
    }

    private void displayEmptyView(String title, String mensaje) {
        ivMessageImagen.setImageResource(R.drawable.ic_empty_view_lista_vacaciones);
        tvMessageTitle.setText(title);
        tvMessageMensaje.setText(mensaje);
        swipeRefreshLayout.setVisibility(View.GONE);
        tvMessageBoton.setVisibility(View.GONE);
        viewMessage.setVisibility(View.VISIBLE);
    }


    private void bindViews() {
        //tabs de solicitudes
        tabLayout = findViewById(R.id.solicitudes_flex_tabLayout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        //spinners
        spAwaitingYear = findViewById(R.id.solicitudes_flex_spFiltroAnio1);
        spApprovedYear = findViewById(R.id.solicitudes_flex_spFiltroAnio2);
        spRejectedYear = findViewById(R.id.solicitudes_flex_spFiltroAnio3);
        spCanceledYear = findViewById(R.id.solicitudes_flex_spFiltroAnio4);

        //recycler para lista de solicitudes
        rvFlexRequests = findViewById(R.id.solicitudes_flex_rvListaSolicitudes);

        //view para carga
        viewLoading = findViewById(R.id.flexplace_forapprove_loading_view);
        tvMensajeViewLoading = findViewById(R.id.flexplace_view_loading_tvMessage);

        //view para respuesta server
        viewMessage = findViewById(R.id.flexplace_forapprove_viewMessage);
        tvMessageTitle = findViewById(R.id.flexplace_view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.flexplace_view_message_tvMessage);
        ivMessageImagen = findViewById(R.id.flexplace_view_message_ivImage);
        tvMessageBoton = findViewById(R.id.flexplace_view_message_tvButton);
    }


    @Override
    public void onBackPressed() {
        goToParentActivity();
    }


    public void clickNull(View view) {
    }

    public void configurarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.flexplace_module_forapprove_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goToParentActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        startActivity(upIntent);
        finish();
    }
}
