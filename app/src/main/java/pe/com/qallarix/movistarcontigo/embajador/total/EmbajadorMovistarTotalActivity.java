package pe.com.qallarix.movistarcontigo.embajador.total;

import android.content.Intent;
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

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DetalleDescuentoActivity;
import pe.com.qallarix.movistarcontigo.embajador.total.fragments.EmbajadorTotalBeneficiosFragment;
import pe.com.qallarix.movistarcontigo.embajador.total.fragments.EmbajadorTotalComoSoyFragment;
import pe.com.qallarix.movistarcontigo.embajador.total.fragments.EmbajadorTotalQueEsFragment;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;

public class EmbajadorMovistarTotalActivity extends TranquiParentActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embajador_movistar_total);
        configurarToolbar();
        configurarTabs();
        setearFragment(new EmbajadorTotalQueEsFragment());
    }

    private void configurarToolbar(){
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

    private void configurarTabs() {
        tabLayout = findViewById(R.id.embajador_total_tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                if(index == 0){
                    setearFragment(new EmbajadorTotalQueEsFragment());
                }else if(index == 1){
                    setearFragment(new EmbajadorTotalComoSoyFragment());
                }else if(index == 2){
                    setearFragment(new EmbajadorTotalBeneficiosFragment());
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
                Intent intent = new Intent(EmbajadorMovistarTotalActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_account:
                Intent intent = new Intent(EmbajadorMovistarTotalActivity.this,AccountActivity.class);
                startActivity(intent);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
