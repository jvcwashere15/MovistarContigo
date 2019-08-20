package pe.com.qallarix.movistarcontigo.especiales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.especiales.adapters.BeneficioEspecialAdapter;
import pe.com.qallarix.movistarcontigo.especiales.pojos.BeneficiosEspeciales;
import pe.com.qallarix.movistarcontigo.especiales.pojos.SpecialBenefit;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EspecialesActivity extends TranquiParentActivity {
    public static String KEY_DETALLE_BENEFICIO_ID = "id";
    public static String KEY_DETALLE_BENEFICIO_TITLE = "title";

    private Toolbar toolbar;
    private RecyclerView rvPlanes;
    private List<SpecialBenefit> specialBenefits;
    private String mDni;
    private boolean isLoading = true;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficios_especiales);
        mDni = getDocumentNumber();
        configurarToolbar();
        mShimmerViewContainer = findViewById(R.id.beneficios_especiales_shimerFrameLayout);
        cargarBeneficiosEspeciales();
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

    /**
     * metodo que configura el aspecto visual y funcionalidad navigation del toolbar
     */
    public void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Especiales");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }

    private void cargarBeneficiosEspeciales() {
        configurarRecyclerView();
        rvPlanes.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        Call<BeneficiosEspeciales> call = WebService1.getInstance(mDni)
                .createService(ServiceEspecialesApi.class)
                .getBeneficiosEspeciales();
        call.enqueue(new Callback<BeneficiosEspeciales>() {
            @Override
            public void onResponse(Call<BeneficiosEspeciales> call, Response<BeneficiosEspeciales> response) {

                if (response.code() == 200){
                    specialBenefits = response.body().getSpecialBenefits();
                    cargarMenuBeneficiosEspeciales();
                    rvPlanes.setVisibility(View.VISIBLE);
                }else{
                    mostrarMensaje("Se produjo un error al cargar los beneficios especiales");
                    finish();
                }
                isLoading = false;
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmer();
            }

            @Override
            public void onFailure(Call<BeneficiosEspeciales> call, Throwable t) {
                mostrarMensaje("Se produjo un error al cargar los beneficios especiales");
                finish();
            }
        });

    }

    private void cargarMenuBeneficiosEspeciales() {
        BeneficioEspecialAdapter beneficioEspecialAdapter = new BeneficioEspecialAdapter(
                EspecialesActivity.this,
                specialBenefits,
                new BeneficioEspecialAdapter.ClickBeneficioEspecial() {
                    @Override
                    public void onClick(int position, View view) {
                        SpecialBenefit currentSpecialBenefit = specialBenefits.get(position);
                        Intent intent = new Intent(EspecialesActivity.this, DetalleEspecialActivity.class);
                        intent.putExtra("id",currentSpecialBenefit.getID());
                        intent.putExtra("title",currentSpecialBenefit.getTitle());
                        startActivity(intent);
                    }
                });
        rvPlanes.setAdapter(beneficioEspecialAdapter);
    }

    private void configurarRecyclerView() {
        rvPlanes = findViewById(R.id.beneficios_especiales_recycler);
        rvPlanes.setHasFixedSize(true);
        rvPlanes.setLayoutManager(new LinearLayoutManager(this));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_just_account, menu);
//        View view = menu.findItem(R.id.action_account).getActionView();
//        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
//        tvIniciales.setText(obtenerIniciales());
//        tvIniciales.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EspecialesActivity.this,AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_account:
//                Intent intent = new Intent(EspecialesActivity.this,AccountActivity.class);
//                startActivity(intent);
//                return true;
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
