package pe.com.qallarix.movistarcontigo.embajador.movil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DetalleDescuentoActivity;
import pe.com.qallarix.movistarcontigo.embajador.ServiceAmbassadorApi;
import pe.com.qallarix.movistarcontigo.embajador.movil.fragments.EmbajadorMovilPaquetesFragment;
import pe.com.qallarix.movistarcontigo.embajador.movil.fragments.EmbajadorMovilQueHacerFragment;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.Benefit;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.EmbajadorMovil;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmbajadorMovilActivity extends TranquiParentActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Benefit benefit;
    private int currentTab = 0;
    private ProgressDialog progressDialog;
    private String mDni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embajador_movil);
        mDni = getDocumentNumber();
        configurarToolbar();
        configurarTabs();
        if (existConnectionInternet()){
            final Call<EmbajadorMovil> call = WebService.getInstance(mDni)
                    .createService(ServiceAmbassadorApi.class)
                    .getDataEmbajadorMovil();
            progressDialog = ProgressDialog.show(EmbajadorMovilActivity.this, "Embajador Movil",
                    "Cargando Canal Embajador Movil...", true, true, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            call.cancel();
                            finish();
                        }
                    });
            progressDialog.setCanceledOnTouchOutside(false);
            call.enqueue(new Callback<EmbajadorMovil>() {
                @Override
                public void onResponse(Call<EmbajadorMovil> call, Response<EmbajadorMovil> response) {
                    progressDialog.dismiss();
                    if (response.code() == 200){
                        benefit = response.body().getBenefit();
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment_movil");
                        if (fragment == null) setearFragment();
                    }else{
                        Toast.makeText(EmbajadorMovilActivity.this, "No se puedo cargar el Canal Embajador Móvil", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<EmbajadorMovil> call, Throwable t) {
                    Toast.makeText(EmbajadorMovilActivity.this, "No se puedo cargar el Canal Embajador Móvil", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }else{
            Toast.makeText(this, "Se perdió la conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void configurarTabs() {
        tabLayout = findViewById(R.id.embajador_movil_tabLayout);
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
            fragment = EmbajadorMovilPaquetesFragment.newInstance(benefit.getOfferList());
        }else if(currentTab == 1){
            fragment = EmbajadorMovilQueHacerFragment.newInstance(benefit.getSelfhelpList());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_just_account, menu);
        View view = menu.findItem(R.id.action_account).getActionView();
        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
        tvIniciales.setText(obtenerIniciales());
        tvIniciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmbajadorMovilActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_account:
                Intent intent = new Intent(EmbajadorMovilActivity.this,AccountActivity.class);
                startActivity(intent);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }


    public String getDocumentNumber(){
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        return sharedPreferences.getString("documentNumber","");
    }
}
