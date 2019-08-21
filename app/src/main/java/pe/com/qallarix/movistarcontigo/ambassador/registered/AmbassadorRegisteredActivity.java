package pe.com.qallarix.movistarcontigo.ambassador.registered;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.ServiceAmbassadorApi;
import pe.com.qallarix.movistarcontigo.ambassador.registered.adapter.RegisteredAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.registered.pojos.BreakRegistered;
import pe.com.qallarix.movistarcontigo.ambassador.registered.pojos.ResponseTraceability;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceAmbassador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbassadorRegisteredActivity extends TranquiParentActivity {
    Toolbar toolbar;
    RecyclerView rvListaRegistrados;
    List<BreakRegistered> breaksList = new ArrayList<>();
    RegisteredAdapter registeredAdapter;
    ShimmerFrameLayout shimmerFramePlaceholder;
    boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embajador_registrados);
        setUpShimmer();
        setUpToolbar();
        setUpRecycler();
        loadBreaksList();
    }

    private void setUpShimmer() {
        shimmerFramePlaceholder = findViewById(R.id.ambassador_registered_placeholder);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoading)
            shimmerFramePlaceholder.startShimmer();
    }

    @Override
    protected void onPause() {
        shimmerFramePlaceholder.stopShimmer();
        super.onPause();
    }

    private void loadBreaksList() {
        Call<ResponseTraceability> call = WebServiceAmbassador
                .getInstance(getDocumentNumber())
                .createService(ServiceAmbassadorApi.class)
                .getTraceability();
        shimmerFramePlaceholder.setVisibility(View.VISIBLE);
        shimmerFramePlaceholder.stopShimmer();
        call.enqueue(new Callback<ResponseTraceability>() {
            @Override
            public void onResponse(Call<ResponseTraceability> call, Response<ResponseTraceability> response) {
                if (response.code() == 200){
                    breaksList = response.body().getList();
                    registeredAdapter.setBreaks(breaksList);
                    if (breaksList.isEmpty()) displayEmptyView();
                }else{
                    displayMessageErrorServer();
                }
                isLoading = false;
                shimmerFramePlaceholder.setVisibility(View.GONE);
                shimmerFramePlaceholder.stopShimmer();
            }

            @Override
            public void onFailure(Call<ResponseTraceability> call, Throwable t) {
                Toast.makeText(AmbassadorRegisteredActivity.this, "Problemas con el servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayEmptyView() {
        findViewById(R.id.ambassador_registered_emptyView).setVisibility(View.VISIBLE);
    }

    private void displayMessageErrorServer() {
        findViewById(R.id.ambassador_registered_errorView).setVisibility(View.VISIBLE);
    }


    private void setUpRecycler() {
        rvListaRegistrados = findViewById(R.id.ambassador_registered_rvLista);
        rvListaRegistrados.setHasFixedSize(true);
        rvListaRegistrados.setLayoutManager(new LinearLayoutManager(this));
        registeredAdapter = new RegisteredAdapter(this);
        rvListaRegistrados.setAdapter(registeredAdapter);
    }

    private void setUpToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("Quiebres registrados");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
