package pe.com.qallarix.movistarcontigo.ambassador.total;

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
import pe.com.qallarix.movistarcontigo.ambassador.home.AmbassadorHomeActivity;
import pe.com.qallarix.movistarcontigo.ambassador.registered.pojos.ResponseTraceability;
import pe.com.qallarix.movistarcontigo.ambassador.total.fragments.AmbassadorTotalBenefitsFragment;
import pe.com.qallarix.movistarcontigo.ambassador.total.fragments.AmbassadorTotalHowIAmFragment;
import pe.com.qallarix.movistarcontigo.ambassador.total.fragments.AmbassadorTotalWhatIsFragment;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.ResponseMovistarTotal;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1.Tab1;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab2.Tab2;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab3.Tab3;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceAmbassador;
import pe.com.qallarix.movistarcontigo.util.WebServiceAmbassadorMT;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbassadorTotalActivity extends TranquiParentActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Tab1 tab1;
    private Tab2 tab2;
    private Tab3 tab3;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambassador_total);
        setUpToolbar();
        setUpTabs();
        displayFragmentsMovistarTotal();
    }

    private void displayFragmentsMovistarTotal() {
        final Call<ResponseMovistarTotal> call = WebServiceAmbassadorMT
                .getInstance(getDocumentNumber())
                .createService(ServiceAmbassadorApi.class)
                .getMovistarTotal();
        progressDialog = ProgressDialog.show(AmbassadorTotalActivity.this, "Embajador Movistar Total",
                "Cargando Canal Embajador Movistar Total...", true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        call.cancel();
                        finish();
                    }
                });
        call.enqueue(new Callback<ResponseMovistarTotal>() {
            @Override
            public void onResponse(Call<ResponseMovistarTotal> call, Response<ResponseMovistarTotal> response) {
                if (response.code() == 200){
                    progressDialog.dismiss();
                    ResponseMovistarTotal responseMovistarTotal = response.body();
                    saveDataForTabs(responseMovistarTotal);
                    setearFragment(AmbassadorTotalWhatIsFragment.newInstance(tab1));
                }
            }

            @Override
            public void onFailure(Call<ResponseMovistarTotal> call, Throwable t) {
                Toast.makeText(AmbassadorTotalActivity.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveDataForTabs(ResponseMovistarTotal responseMovistarTotal) {
        tab1 = responseMovistarTotal.getBenefit().getTab1();
        tab2 = responseMovistarTotal.getBenefit().getTab2();
//        tab3 = responseMovistarTotal.getBenefit().getTab3();
    }

    private void setUpToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("Movistar Total");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpTabs() {
        tabLayout = findViewById(R.id.embajador_total_tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                if(index == 0){
                    setearFragment(AmbassadorTotalWhatIsFragment.newInstance(tab1));
                }else if(index == 1){
                    setearFragment(AmbassadorTotalHowIAmFragment.newInstance(tab2));
                }else if(index == 2){
                    setearFragment(new AmbassadorTotalBenefitsFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

//    private void configurarPestania(final Fragment fragment, final ToggleButton tbEnabled,
//                                    final ToggleButton tbDisabled1, final ToggleButton tbDisabled2){
//        tbEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    tbEnabled.setTextColor(Color.WHITE);
//                    tbEnabled.setEnabled(false);
//                    tbDisabled1.setChecked(false);
//                    tbDisabled1.setEnabled(true);
//                    tbDisabled2.setChecked(false);
//                    tbDisabled2.setEnabled(true);
//                    setearFragment(fragment);
//                }
//                else tbEnabled.setTextColor(getResources().getColor(R.color.colorCanalEmbajador));
//            }
//        });
//    }

    private void setearFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.embajador_movistar_total_content,fragment).commit();
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
//                Intent intent = new Intent(AmbassadorTotalActivity.this,AccountActivityView.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_account:
//                Intent intent = new Intent(AmbassadorTotalActivity.this,AccountActivityView.class);
//                startActivity(intent);
//                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
