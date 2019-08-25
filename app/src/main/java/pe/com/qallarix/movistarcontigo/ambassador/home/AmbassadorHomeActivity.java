
package pe.com.qallarix.movistarcontigo.ambassador.home;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.ServiceAmbassadorApi;
import pe.com.qallarix.movistarcontigo.ambassador.home.fragments.AmbassadorHomeAditionalsFragment;
import pe.com.qallarix.movistarcontigo.ambassador.home.fragments.AmbassadorHomeBillingFragment;
import pe.com.qallarix.movistarcontigo.ambassador.home.fragments.AmbassadorHomePacksFragment;
import pe.com.qallarix.movistarcontigo.ambassador.home.fragments.AmbassadorHomeWhatToDoFragment;
import pe.com.qallarix.movistarcontigo.ambassador.home.pojos.AmbassadorHome;
import pe.com.qallarix.movistarcontigo.ambassador.home.pojos.Benefit;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbassadorHomeActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Benefit benefit;
    private int currentTab = 0;
    private ProgressDialog progressDialog;
    private String mDni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambassador_home);
        settingToolbar();
        settingTabs();
        loadAmbassadorHomeFragments();
    }

    private void loadAmbassadorHomeFragments() {
        if (internetConectionExists())
            loadDataHomeFromServicesAmbassador();
        else
            //todo (ambassador) mostrar pantalla de falta conexion a internet
            Toast.makeText(this, "Se perdió la conexión a internet", Toast.LENGTH_SHORT).show();
    }

    private void loadDataHomeFromServicesAmbassador() {
        final Call<AmbassadorHome> call = WebService1.getInstance(getDocumentNumber())
                .createService(ServiceAmbassadorApi.class)
                .getDataAmbassadorHome();
        progressDialog = ProgressDialog.show(AmbassadorHomeActivity.this, "Embajador Hogar",
                "Cargando Canal Embajador Hogar...", true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        call.cancel();
                        finish();
                    }
                });
        call.enqueue(new Callback<AmbassadorHome>() {
            @Override
            public void onResponse(Call<AmbassadorHome> call, Response<AmbassadorHome> response) {
                if (response.code() == 200){
                    progressDialog.dismiss();
                    benefit = response.body().getBenefit();
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment_hogar");
                    if (fragment == null) setearFragment();
                }else{
                    Toast.makeText(AmbassadorHomeActivity.this, "No se pudo cargar el Canal Embajador Hogar", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AmbassadorHome> call, Throwable t) {
                Toast.makeText(AmbassadorHomeActivity.this, "Falló la conexion al servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void settingTabs() {
        tabLayout = findViewById(R.id.ambassador_home_tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                currentTab = index;
                setearFragment();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }


    private void setearFragment() {
        Fragment fragment = null;
        if(currentTab == 0){
            fragment = AmbassadorHomePacksFragment.newInstance(benefit.getOfferList());
        }else if(currentTab == 1){
            fragment = AmbassadorHomeAditionalsFragment.newInstance(benefit.getComplementList());
        }else if(currentTab == 2){
            fragment = new AmbassadorHomeBillingFragment();
        }else if(currentTab == 3){
            fragment = AmbassadorHomeWhatToDoFragment.newInstance(benefit.getSelfhelpList(),benefit.getSelfhelpList2());
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.embajador_hogar_content,fragment,"fragment_hogar")
                .commitAllowingStateLoss();
    }

    private void settingToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("Hogar");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            default:return super.onOptionsItemSelected(item);
        }
    }
}
