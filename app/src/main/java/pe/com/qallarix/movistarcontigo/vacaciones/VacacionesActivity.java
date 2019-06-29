package pe.com.qallarix.movistarcontigo.vacaciones;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.AprobacionVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.EstadoVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.pojos.FutureJoy;
import pe.com.qallarix.movistarcontigo.vacaciones.pojos.VacacionesDashboard;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.RegistroVacacionesActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacacionesActivity extends TranquiParentActivity {
    private View
            vRegistro,
            vEstado,
            vAprobacion;
    private TextView
            tvPendientes,
            tvAdelanto,
            tvFechaDerecho;

    private boolean isLoading = true;

    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacaciones);
        configurarToolbar();
        bindearVistas();
        configurarBotonRegistroVacaciones();
        configurarBotonEstadoVacaciones();
        configurarBotonAprobacionVacaciones();
        cargarDatosVacaciones();
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

    private void cargarDatosVacaciones() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        Call<VacacionesDashboard> call = WebService2
                .getInstance(getDocumentNumber())
                .createService(ServiceVacacionesApi.class)
                .getInfoDashboardVacaciones(getCIP());
        call.enqueue(new Callback<VacacionesDashboard>() {
            @Override
            public void onResponse(Call<VacacionesDashboard> call, Response<VacacionesDashboard> response) {
                if (response.code() == 200){
                    FutureJoy futureJoy = response.body().getFutureJoy();
                    tvPendientes.setText(String.valueOf(futureJoy.getPlannedDaysPending()));
                    tvAdelanto.setText(String.valueOf(futureJoy.getPlannedDaysTruncate()));
                    tvFechaDerecho.setText(futureJoy.getDateOfRight());
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<VacacionesDashboard> call, Throwable t) {
                Toast.makeText(VacacionesActivity.this, "error en el servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    private void configurarBotonRegistroVacaciones() {
        vRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, RegistroVacacionesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configurarBotonEstadoVacaciones() {
        vEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, EstadoVacacionesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configurarBotonAprobacionVacaciones() {
        vAprobacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, AprobacionVacacionesActivity.class);
                startActivity(intent);
            }
        });
    }



    private void bindearVistas() {
        vRegistro = findViewById(R.id.vacaciones_vRegistro);
        vEstado = findViewById(R.id.vacaciones_vEstado);
        vAprobacion = findViewById(R.id.vacaciones_vAprobacion);
        tvPendientes = findViewById(R.id.vacaciones_tvPendientes);
        tvAdelanto = findViewById(R.id.vacaciones_tvAdelanto);
        tvFechaDerecho = findViewById(R.id.vacaciones_tvFechaDerecho);
        mShimmerViewContainer = findViewById(R.id.dashboard_vacaciones_shimerFrameLayout);
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gestión de vacaciones");
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
                Intent intent = new Intent(this,AcercaActivity.class);
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
}
