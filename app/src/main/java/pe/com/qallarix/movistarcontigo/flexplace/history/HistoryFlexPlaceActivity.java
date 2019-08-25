package pe.com.qallarix.movistarcontigo.flexplace.history;

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

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.FlexPlace;
import pe.com.qallarix.movistarcontigo.flexplace.history.pojos.ResponseHistorialFlexPlace;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFlexPlaceActivity extends TranquiParentActivity {

    RecyclerView rvVacaciones;
    List<FlexPlace> approveds;
    List<FlexPlace> awaitings;
    List<FlexPlace> rejecteds;
    List<FlexPlace> canceleds;
    HistoryFlexPlaceAdapter historialFlexPlaceAdapter;
    private TextView tvMensajeViewLoader;
    private View viewLoader;
    private TabLayout tabLayout;
    public static final int AWAITING = 0;
    public static final int APPROVED = 1;
    public static final int REJECTED = 2;
    public static final int CANCELED = 3;

    private final String STATUS_AWAITING = "02";
    private final String STATUS_APPROVED = "03";
    private final String STATUS_REJECTED = "04";
    private final String STATUS_CANCELED = "01";

    private int currentTab = AWAITING;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_history);
        setUpToolbar();
        bindViews();
        setupRecyclerView();
        configurarTabs();
        getDataFromIntent();
        TabLayout.Tab tab = tabLayout.getTabAt(currentTab);
        tab.select();
        displayHistoryList(currentTab);
    }

    private void getDataFromIntent() {
        Bundle bundleExtras = getIntent().getExtras();
        if (bundleExtras != null && bundleExtras.containsKey("initTab"))
            currentTab = bundleExtras.getInt("initTab",AWAITING);
    }

    private void configurarTabs() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                displayHistoryList(index);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void displayHistoryList(int listType) {
        switch (listType){
            case APPROVED:
                loadHistoryList(listType, STATUS_APPROVED, approveds,
                        getString(R.string.flexplace_mensaje_carga_aprobadas),
                        getString(R.string.flexplace_emptyview_approved_title),
                        getString(R.string.flexplace_emptyview_approved_message)); break;
            case AWAITING:
                loadHistoryList(listType, STATUS_AWAITING, awaitings,
                        getString(R.string.flexplace_mensaje_carga_pendientes),
                        getString(R.string.flexplace_emptyview_awaiting_title),
                        getString(R.string.flexplace_emptyview_awaiting_message)); break;
            case REJECTED:
                loadHistoryList(listType, STATUS_REJECTED, rejecteds,
                        getString(R.string.flexplace_mensaje_carga_rechazadas),
                        getString(R.string.flexplace_emptyview_rejected_title),
                        getString(R.string.flexplace_emptyview_rejected_message)); break;
            case CANCELED:
                loadHistoryList(listType, STATUS_CANCELED, canceleds,
                        getString(R.string.flexplace_mensaje_carga_cancelados),
                        getString(R.string.flexplace_emptyview_canceled_title),
                        getString(R.string.flexplace_emptyview_canceled_message)); break;
        }
    }

    private void loadHistoryList(int tipoLista, String status, List<FlexPlace> flexPlaces,
                                 String mensajeCarga, String titleEmpty, String messageEmpty){
        viewMessage.setVisibility(View.GONE);
        if (flexPlaces == null){
            historialFlexPlaceAdapter.setHistorialFlexPlace(new ArrayList<FlexPlace>());
            getFlexPlaceListFromServices(tipoLista,status,mensajeCarga,titleEmpty,messageEmpty);
        }else{
            if (flexPlaces.isEmpty()) displayEmptyView(titleEmpty, messageEmpty);
            else historialFlexPlaceAdapter.setHistorialFlexPlace(flexPlaces);
        }
    }

    private void getFlexPlaceListFromServices(final int tipoLista, String status,
                                              String mensajeCarga, final String titleEmpty,
                                              final String messageEmpty) {
        tvMensajeViewLoader.setText(mensajeCarga);
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseHistorialFlexPlace> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexPlaceHistoryApi.class)
                .getHistorialFlexPlace(status,2019);
        call.enqueue(new Callback<ResponseHistorialFlexPlace>() {
            @Override
            public void onResponse(Call<ResponseHistorialFlexPlace> call, Response<ResponseHistorialFlexPlace> response) {
                if (response.code() == 200){
                    List<FlexPlace> flexPlaces = response.body().getList();
                    loadListRequests(tipoLista,flexPlaces,titleEmpty,messageEmpty);
                }else {
                    displayMessage500(tipoLista);
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseHistorialFlexPlace> call, Throwable t) {
                displayMessage500(tipoLista);
            }
        });
    }

    private void loadListRequests(int tipoLista, List<FlexPlace> flexPlaces, String titleEmpty,
                                  String messageEmpty) {
        switch (tipoLista){
            case APPROVED: approveds = flexPlaces; break;
            case AWAITING: awaitings = flexPlaces; break;
            case REJECTED: rejecteds = flexPlaces; break;
            case CANCELED: canceleds = flexPlaces; break;
        }
        if (flexPlaces.size() == 0) displayEmptyView(titleEmpty, messageEmpty);
        else historialFlexPlaceAdapter.setHistorialFlexPlace(flexPlaces);
    }

    private void bindViews() {
        //tabs
        tabLayout = findViewById(R.id.flexplace_requests_tabLayout);
        //List of requests
        rvVacaciones = findViewById(R.id.flexplace_rvListRequests);
        //Loading view
        viewLoader = findViewById(R.id.flexplace_loading_view);
        tvMensajeViewLoader = findViewById(R.id.flexplace_view_loading_tvMessage);
        //Empty View
        viewMessage = findViewById(R.id.flexplace_empty_view);
        ivMessageImagen = findViewById(R.id.flexplace_view_message_ivImage);
        tvMessageTitle = findViewById(R.id.flexplace_view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.flexplace_view_message_tvMessage);
        tvMessageBoton = findViewById(R.id.flexplace_view_message_tvButton);
    }

    private void setupRecyclerView() {
        rvVacaciones.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVacaciones.setLayoutManager(layoutManager);
        historialFlexPlaceAdapter = new HistoryFlexPlaceAdapter(this,
                new HistoryFlexPlaceAdapter.EstadoOnClick() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent = new Intent(HistoryFlexPlaceActivity.this,
                                HistoryFlexDetailActivity.class);
                        FlexPlace currentFlexPlace = null;
                        switch (tabLayout.getSelectedTabPosition()){
                            case APPROVED:
                                if (approveds != null && approveds.size()> 0)
                                    currentFlexPlace = approveds.get(position);
                                break;
                            case REJECTED:
                                if (rejecteds != null && rejecteds.size() > 0)
                                    currentFlexPlace = rejecteds.get(position);
                                break;
                            case AWAITING:
                                if (awaitings != null && awaitings.size()> 0)
                                    currentFlexPlace = awaitings.get(position);
                                break;
                            case CANCELED:
                                if (canceleds != null && canceleds.size()> 0)
                                    currentFlexPlace = canceleds.get(position);
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

    private void displayMessage500(final int tipoLista) {
        ivMessageImagen.setImageResource(R.drawable.img_error_servidor);
        tvMessageTitle.setText(getString(R.string.flexplace_error500_title));
        tvMessageMensaje.setText(getString(R.string.flexplace_error500_message));
        tvMessageBoton.setText(getString(R.string.flexplace_error500_textButton));
        tvMessageBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { displayHistoryList(tipoLista); }
        });
        tvMessageBoton.setVisibility(View.VISIBLE);
        viewMessage.setVisibility(View.VISIBLE);
    }

    private void displayEmptyView(String title, String mensaje) {
        ivMessageImagen.setImageResource(R.drawable.ic_empty_view_lista_vacaciones);
        tvMessageTitle.setText(title);
        tvMessageMensaje.setText(mensaje);
        tvMessageBoton.setVisibility(View.GONE);
        viewMessage.setVisibility(View.VISIBLE);
    }

    public void clickNull(View view) {}

    public void setUpToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.flexplace_module_history_name));
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
        startActivity(upIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }

}
