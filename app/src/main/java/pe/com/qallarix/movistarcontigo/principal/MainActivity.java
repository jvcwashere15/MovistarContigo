package pe.com.qallarix.movistarcontigo.principal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.noticias.DataNoticias;
import pe.com.qallarix.movistarcontigo.noticias.News;
import pe.com.qallarix.movistarcontigo.noticias.ServiceNewsApi;
import pe.com.qallarix.movistarcontigo.notificacion.NotificacionesActivity;
import pe.com.qallarix.movistarcontigo.principal.fragments.BeneficiosFragment;
import pe.com.qallarix.movistarcontigo.principal.fragments.HerramientasFragment;
import pe.com.qallarix.movistarcontigo.principal.fragments.HomeFragment;
import pe.com.qallarix.movistarcontigo.principal.fragments.NoticiasFragment;
import pe.com.qallarix.movistarcontigo.util.TipoFragment;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends TranquiParentActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private int fragmentActual = TipoFragment.HOME;
    private  List<News> noticias;
    public static final String TITLE_HOME = "HOME",
                            TITLE_BENEFIT = "BENEFIT",
                            TITLE_TOOLS = "TOOLS",
                            TITLE_NEWS = "NEWS";
    private String mDni;

    public void setNoticias(List<News> noticias) {
        this.noticias = noticias;
    }

    public List<News> getNoticias() {
        return noticias;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        obtenerDniUsuarioActual();
        configurarToolbar();
        configurarBottomNavigation();
        if (getIntent().getExtras()!= null && getIntent().getExtras().containsKey("fragment")){
            String fragment = getIntent().getExtras().getString("fragment",TITLE_HOME);
            if (fragment.equals(TITLE_NEWS)) bottomNavigationView.setSelectedItemId(R.id.action_noticias);
        }else{
            bottomNavigationView.setSelectedItemId(R.id.action_home);
        }
    }

    private void obtenerDniUsuarioActual() {
        mDni = getDocumentNumber();
    }

    private void configurarBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int idItem = menuItem.getItemId();
                if (idItem == R.id.action_home){
                    setFragmentHome();
                    return true;
                }else if (idItem == R.id.action_beneficios){
                    setFragmentBeneficios();
                    return true;
                }else if (idItem == R.id.action_herramientas){
                    setFragmentHerramientas();
                    return true;
                }else if (idItem ==R.id.action_noticias){
                    setFragmentNoticias();
                    return true;
                }
                return false;
            }
        });
    }

    private void configurarToolbar() {
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Movistar Contigo");
    }


    public void setFragmentHome(){
        Analitycs.logEventoClickBottomNav(this,TITLE_HOME);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, HomeFragment.newInstance(noticias), "frag");
        fragmentTransaction.commit();
        fragmentActual = TipoFragment.HOME;
    }


    public void setFragmentBeneficios(){
        Analitycs.logEventoClickBottomNav(this,TITLE_BENEFIT);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment,new BeneficiosFragment(),"frag");
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("frag_imagen");
        if (fragment != null) fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
        fragmentActual = TipoFragment.BENEFICIOS;
    }

    public void setFragmentHerramientas(){
        Analitycs.logEventoClickBottomNav(this,TITLE_TOOLS);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment,new HerramientasFragment(),"frag");
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("frag_imagen");
        if (fragment != null) fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
        fragmentActual = TipoFragment.HERRAMIENTAS;
    }

    public void setFragmentNoticias(){
        Analitycs.logEventoClickBottomNav(this,TITLE_NEWS);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment,NoticiasFragment.newInstance(noticias,mDni),"frag");
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("frag_imagen");
        if (fragment != null) fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
        fragmentActual = TipoFragment.NOTICIAS;
    }


    @Override
    public void onBackPressed() {
        if (fragmentActual == TipoFragment.HOME)
            super.onBackPressed();
        else{
            if (fragmentActual == TipoFragment.BENEFICIOS ||
                    fragmentActual == TipoFragment.HERRAMIENTAS ||
                    fragmentActual == TipoFragment.NOTICIAS){
                bottomNavigationView.setSelectedItemId(R.id.action_home);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);

        View view = menu.findItem(R.id.action_account).getActionView();
        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
        tvIniciales.setText(obtenerIniciales());
        tvIniciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_notificaciones:
                Intent intent = new Intent(MainActivity.this, NotificacionesActivity.class);
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
