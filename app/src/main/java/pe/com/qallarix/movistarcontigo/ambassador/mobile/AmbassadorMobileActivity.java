package pe.com.qallarix.movistarcontigo.ambassador.mobile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.ServiceAmbassadorApi;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.fragments.AmbassadorMobilePacksFragment;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.fragments.AmbassadorMobileWhatToDoFragment;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos.Benefit;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos.AmbassadorMobile;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceAmbassador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AmbassadorMobileActivity extends TranquiParentActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Benefit benefit;
    private int currentTab = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambassador_mobile);
        configurarToolbar();
        configurarTabs();
        loadAmbassadorMobileFragments();
    }

    private void loadAmbassadorMobileFragments() {
        if (internetConectionExists())
            loadDataMobileFromServiceAmbassador();
        else
            Toast.makeText(this, "Se perdió la conexión a internet", Toast.LENGTH_SHORT).show();
    }

    private void loadDataMobileFromServiceAmbassador() {
        final Call<AmbassadorMobile> call = WebServiceAmbassador.getInstance(getDocumentNumber())
                .createService(ServiceAmbassadorApi.class)
                .getDataAmbassadorMobile();
        progressDialog = ProgressDialog.show(AmbassadorMobileActivity.this, "Embajador Movil",
                "Cargando Canal Embajador Movil...", true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        call.cancel();
                        finish();
                    }
                });
        progressDialog.setCanceledOnTouchOutside(false);
        call.enqueue(new Callback<AmbassadorMobile>() {
            @Override
            public void onResponse(Call<AmbassadorMobile> call, Response<AmbassadorMobile> response) {
                progressDialog.dismiss();
                if (response.code() == 200){
                    benefit = response.body().getBenefit();
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment_movil");
                    if (fragment == null) setearFragment();
                }else{
                    Toast.makeText(AmbassadorMobileActivity.this, "No se puedo cargar el Canal Embajador Móvil", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<AmbassadorMobile> call, Throwable t) {
                Toast.makeText(AmbassadorMobileActivity.this, "No se puedo cargar el Canal Embajador Móvil", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void configurarTabs() {
        tabLayout = findViewById(R.id.ambassador_mobile_tabLayout);
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
            fragment = AmbassadorMobilePacksFragment.newInstance(benefit.getOfferList());
        }else if(currentTab == 1){
            fragment = AmbassadorMobileWhatToDoFragment.newInstance(benefit.getSelfhelpList());
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.embajador_movil_content,fragment,"fragment_movil")
                .commitAllowingStateLoss();
    }

    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("Móvil");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
