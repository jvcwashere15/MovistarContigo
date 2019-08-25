package pe.com.qallarix.movistarcontigo.health;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.health.fragments.HealthDetailFragment;
import pe.com.qallarix.movistarcontigo.health.pojos.DataPlan;
import pe.com.qallarix.movistarcontigo.health.pojos.HealthPlan;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthDetailActivity extends TranquiParentActivity {

    private FragmentManager fragmentManager;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private long mId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_salud);
        recuperarDatosBasicosDelPlan();
        configurarToolbar();
        obtenerDatosDelPlan();

    }

    private void obtenerDatosDelPlan() {
        Call<DataPlan> call = WebService1.getInstance(getDocumentNumber())
                .createService(ServiceHealthApi.class)
                .getPlanSalud(mId);
        progressDialog = ProgressDialog.show(this, "Salud", "Cargando detalles del plan...", true);
        call.enqueue(new Callback<DataPlan>() {
            @Override
            public void onResponse(Call<DataPlan> call, Response<DataPlan> response) {
                if (response.code() == 200){
                    HealthPlan currentHealthPlan = response.body().getHealthPlan();
                    getSupportActionBar().setTitle(currentHealthPlan.getTitle());
                    displayFragmentDetalle(currentHealthPlan);
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                    mostrarMensaje("Se produjo un error al intentar cargar los detalles del plan ");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DataPlan> call, Throwable t) {
                progressDialog.dismiss();
                mostrarMensaje("Se produjo un error al intentar cargar los detalles del plan ");
                finish();
            }
        });
    }

    /**
     * metodo para desplegar el fragment de la informacion detallada del beneficio salud
     * @param healthPlan es el objeto que contiene los detalles del plan
     */
    private void displayFragmentDetalle(HealthPlan healthPlan) {
        fragmentManager = getSupportFragmentManager();
        layoutManager = new LinearLayoutManager(this);
        fragmentManager.beginTransaction()
                .replace(R.id.detalle_beneficio_salud_fragment, HealthDetailFragment.newInstance(healthPlan))
                .commitAllowingStateLoss();
    }


    public void recuperarDatosBasicosDelPlan(){
        Bundle bundle = getIntent().getExtras();
        mId = bundle.getLong("id",0);
    }


    /**
     * metodo que configura el aspecto visual y funcionalidad navigation del toolbar
     */
    public void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
//                Intent intent = new Intent(HealthDetailActivity.this,AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_account:
//                Intent intent = new Intent(HealthDetailActivity.this,AccountActivity.class);
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
