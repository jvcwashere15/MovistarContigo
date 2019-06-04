package pe.com.qallarix.movistarcontigo.beneficioespeciales;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.List;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.adapters.TipoBeneficioAdapter;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.fragments.BeneficioFragment;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.fragments.ContactoFragment;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.pojos.BenefitDetail;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.pojos.ItemList;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.pojos.SpecialBenefits;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleBeneficioEspecialActivity extends TranquiParentActivity {
    private Toolbar toolbar;
    private ImageView ivImagen;
    private RecyclerView rvTiposBeneficios;
    private TextView tvDescripcion;
    private TextView tvFooter;
    private Button btGestionar;
    private TabLayout tabLayout;
    int currentTab = 0;

    private View vSingle, vDual;

    private long idBeneficio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_beneficio_especial);
        getDataIntent();
        configurarToolbar();
        bindearVistas();
        cargarBeneficioEspecial();
    }

    private void bindearVistas() {
        vSingle = findViewById(R.id.detalle_beneficio_especial_vSingle);
        vDual = findViewById(R.id.detalle_beneficio_especial_vDual);
        tabLayout = findViewById(R.id.detalle_beneficio_especial_tabLayout);
    }

    private void cargarBeneficioEspecial() {
        Call<BenefitDetail> call = WebService.getInstance(getDocumentNumber())
                .createService(ServiceBeneficiosEspecialesApi.class)
                .getBeneficioEspecial(idBeneficio);
        call.enqueue(new Callback<BenefitDetail>() {
            @Override
            public void onResponse(Call<BenefitDetail> call, Response<BenefitDetail> response) {
                if (response.code() == 200){
                    SpecialBenefits currentSpecialBenefits = response.body().getSpecialBenefits();
                    getSupportActionBar().setTitle(currentSpecialBenefits.getTitle());
                    if (existeContacto(currentSpecialBenefits)){
                        displayDualView(currentSpecialBenefits);
                    }else displaySingleView(currentSpecialBenefits);
                }else{
                    Toast.makeText(DetalleBeneficioEspecialActivity.this, "Error al cargar el detalle del beneficio", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BenefitDetail> call, Throwable t) {
                Toast.makeText(DetalleBeneficioEspecialActivity.this, "Error al cargar el detalle del beneficio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displaySingleView(SpecialBenefits currentSpecialBenefits) {
        vSingle.setVisibility(View.VISIBLE);
        vDual.setVisibility(View.GONE);
        displayImagenBeneficioEspecial(currentSpecialBenefits.getImage());
        displayDescripcionBeneficio(currentSpecialBenefits.getDescription());
        displayOpcionesBeneficio(currentSpecialBenefits.getItemList());
        displayFooter(currentSpecialBenefits.getAdditionalInformation());
        configurarBotonGestionar(currentSpecialBenefits.getHiperlink());
    }

    private void displayDualView(SpecialBenefits currentSpecialBenefits) {
        vSingle.setVisibility(View.GONE);
        vDual.setVisibility(View.VISIBLE);
        displayImagenBeneficioEspecial(currentSpecialBenefits.getImage());
        configurarTabs(currentSpecialBenefits);
        setearFragment(currentSpecialBenefits);
    }

    private void configurarTabs(final SpecialBenefits currentSpecialBenefits) {
        tabLayout = findViewById(R.id.detalle_beneficio_especial_tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                currentTab = index;
                setearFragment(currentSpecialBenefits);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setearFragment(SpecialBenefits currentSpecialBenefits) {
        Fragment fragment = null;
        if(currentTab == 0){
            fragment = BeneficioFragment.newInstance(currentSpecialBenefits.getDescription(),
                    currentSpecialBenefits.getItemList());
        }else if(currentTab == 1){
            fragment = ContactoFragment.newInstance(currentSpecialBenefits.getHowToUse(),currentSpecialBenefits.getContactPhone(),
                    currentSpecialBenefits.getContactPhoneAnexo(),currentSpecialBenefits.getContactMobile(),currentSpecialBenefits.getContactEmail());
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detalle_beneficio_especial_content,fragment,"fragment_beneficio_especial")
                .commitAllowingStateLoss();
    }

    private boolean existeContacto(SpecialBenefits currentSpecialBenefits) {
        return !TextUtils.isEmpty(currentSpecialBenefits.getHowToUse()) || !TextUtils.isEmpty(currentSpecialBenefits.getContactPhone()) ||
                !TextUtils.isEmpty(currentSpecialBenefits.getContactEmail()) || !TextUtils.isEmpty(currentSpecialBenefits.getContactMobile());
    }

    private void displayImagenBeneficioEspecial(String urlImage){
        if (TextUtils.isEmpty(urlImage)) return;
        ivImagen = findViewById(R.id.detalle_beneficio_especial_ivImagen);
        try{
            Picasso.with(DetalleBeneficioEspecialActivity.this)
                    .load(urlImage)
                    .placeholder(R.drawable.placeholder_imagen)
                    .into(ivImagen);
        }catch (Exception e){

        }

    }

    private void displayFooter(String informacionAdicional) {
        tvFooter = findViewById(R.id.detalle_beneficio_especial_tvFooter);
        if (!TextUtils.isEmpty(informacionAdicional))
            tvFooter.setText(informacionAdicional);
        else
            tvFooter.setVisibility(View.GONE);
    }

    private void configurarBotonGestionar(final String mHyperLink) {
        btGestionar = findViewById(R.id.detalle_beneficio_especial_btGestionarAqui);
        if (!TextUtils.isEmpty(mHyperLink)){
            btGestionar.setVisibility(View.VISIBLE);
            btGestionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mHyperLink));
                    startActivity(intent);
                }
            });
        }else btGestionar.setVisibility(View.GONE);

    }

    private void displayOpcionesBeneficio(List<ItemList> itemLists) {
        rvTiposBeneficios = findViewById(R.id.detalle_beneficio_especial_recycler);
        if (!itemLists.isEmpty()){
            rvTiposBeneficios.setHasFixedSize(true);
            rvTiposBeneficios.setLayoutManager(new LinearLayoutManager(this));
            TipoBeneficioAdapter tipoBeneficioAdapter = new TipoBeneficioAdapter(this, itemLists);
            rvTiposBeneficios.setAdapter(tipoBeneficioAdapter);
            rvTiposBeneficios.setNestedScrollingEnabled(false);
        }
    }

    private void displayDescripcionBeneficio(String descripcion) {
        tvDescripcion = findViewById(R.id.detalle_beneficio_especial_tvDescripcion);
        if (!TextUtils.isEmpty(descripcion)){
            tvDescripcion.setText(descripcion);
            tvDescripcion.setVisibility(View.VISIBLE);
        }else
            tvDescripcion.setVisibility(View.GONE);


    }

    /**
     * Recupera la data del intent
     */
    public void getDataIntent(){
        Bundle bundle = getIntent().getExtras();
        idBeneficio = bundle.getLong(BeneficiosEspecialesActivity.KEY_DETALLE_BENEFICIO_ID, 0);
    }


    /**
     * metodo que configura el aspecto visual y funcionalidad navigation del toolbar
     */
    public void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
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
                Intent intent = new Intent(DetalleBeneficioEspecialActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_account:
                Intent intent = new Intent(DetalleBeneficioEspecialActivity.this,AccountActivity.class);
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

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }
}
