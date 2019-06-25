
package pe.com.qallarix.movistarcontigo.embajador.hogar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.embajador.ServiceAmbassadorApi;
import pe.com.qallarix.movistarcontigo.embajador.hogar.fragments.EmbajadorHogarAdicionalesFragment;
import pe.com.qallarix.movistarcontigo.embajador.hogar.fragments.EmbajadorHogarFacturacionFragment;
import pe.com.qallarix.movistarcontigo.embajador.hogar.fragments.EmbajadorHogarPaquetesFragment;
import pe.com.qallarix.movistarcontigo.embajador.hogar.fragments.EmbajadorHogarQueHacerFragment;
import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.Benefit;
import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.EmbajadorHogar;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmbajadorHogarActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Benefit benefit;
    private int currentTab = 0;
    private ProgressDialog progressDialog;
    private String mDni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embajador_hogar);
        mDni = getDocumentNumber();
        configurarToolbar();
        configurarTabs();
        if (existConnectionInternet()){
            final Call<EmbajadorHogar> call = WebService1.getInstance(mDni)
                    .createService(ServiceAmbassadorApi.class)
                    .getDataEmbajadorHogar();
            progressDialog = ProgressDialog.show(EmbajadorHogarActivity.this, "Embajador Hogar",
                    "Cargando Canal Embajador Hogar...", true, true, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            call.cancel();
                            finish();
                        }
                    });
            call.enqueue(new Callback<EmbajadorHogar>() {
                @Override
                public void onResponse(Call<EmbajadorHogar> call, Response<EmbajadorHogar> response) {
                    if (response.code() == 200){
                        progressDialog.dismiss();
                        benefit = response.body().getBenefit();
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment_hogar");
                        if (fragment == null) setearFragment();
                    }else{
                        Toast.makeText(EmbajadorHogarActivity.this, "No se puedo cargar el Canal Embajador Hogar", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<EmbajadorHogar> call, Throwable t) {
                    Toast.makeText(EmbajadorHogarActivity.this, "No se puedo cargar el Canal Embajador Hogar", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }else{
            Toast.makeText(this, "Se perdió la conexión a internet", Toast.LENGTH_SHORT).show();
        }


    }

    private void configurarTabs() {
        tabLayout = findViewById(R.id.embajador_hogar_tabLayout);
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
            fragment = EmbajadorHogarPaquetesFragment.newInstance(benefit.getOfferList());
        }else if(currentTab == 1){
            fragment = EmbajadorHogarAdicionalesFragment.newInstance(benefit.getComplementList());
        }else if(currentTab == 2){
            fragment = new EmbajadorHogarFacturacionFragment();
        }else if(currentTab == 3){
            fragment = EmbajadorHogarQueHacerFragment.newInstance(benefit.getSelfhelpList(),benefit.getSelfhelpList2());
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.embajador_hogar_content,fragment,"fragment_hogar")
                .commitAllowingStateLoss();
    }

    private void configurarToolbar(){
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
//                Intent intent = new Intent(EmbajadorHogarActivity.this,AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_account:
//                Intent intent = new Intent(EmbajadorHogarActivity.this,AccountActivity.class);
//                startActivity(intent);
//                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }



    public String getDocumentNumber(){
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        return sharedPreferences.getString("documentNumber","");
    }
}
