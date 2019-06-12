package pe.com.qallarix.movistarcontigo.descuentos;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
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

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.descuentos.fragments.DescripcionFragment;
import pe.com.qallarix.movistarcontigo.descuentos.fragments.UsaloFragment;
import pe.com.qallarix.movistarcontigo.descuentos.pojos.DetalleDescuento;
import pe.com.qallarix.movistarcontigo.descuentos.pojos.Discount;
import pe.com.qallarix.movistarcontigo.openlearning.DetalleEstudioActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleDescuentoActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private String mId;
    private String mOrigin;


    int currentTab = 0;

    private ImageView ivImagen;
    private ImageView ivLogo;
    private TextView tvTitulo;
    private TextView tvDescripcion;
    private TextView tvDescuento;
    private TextView tvDetalleDescuento;
    private TextView tvHowToUse;
    private TextView tvZona;
    private ImageView ivTipoDescuento;
    private ImageView ivLogoPrix;
    private Button btUsaloAhora;
    private View vLine;
    private TabLayout tabLayout;
    private View vSingle;
    private View vMovistar;
    private boolean mPantallaAnterior = false;
    private Discount mDiscount;

    private static final String DESCUENTO_PRIX = "Prix";
    private static final String DESCUENTO_EXTERNO = "Externo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_descuento);
        obtenerDataExtras();
        bindearVistas();
        cargarBeneficio();
    }

    private void obtenerDataExtras() {
        Bundle extras = getIntent().getExtras();
        mId = extras.getString("id");
        mOrigin = extras.getString("origin");
        mPantallaAnterior = extras.getBoolean("lista_notificaciones",false);
    }

    private void bindearVistas() {
        vSingle = findViewById(R.id.detalle_descuento_layout_single);
        vMovistar = findViewById(R.id.detalle_descuento_layout_movistar);
        ivImagen = findViewById(R.id.detalle_descuento_ivImagen);
        ivLogo = findViewById(R.id.detalle_descuento_ivLogo);
        ivLogoPrix = findViewById(R.id.detalle_descuento_logo_prix);
        vLine = findViewById(R.id.detalle_descuento_vLine);
        ivTipoDescuento = findViewById(R.id.detalle_descuento_ivTipoDescuento);
        tvTitulo = findViewById(R.id.detalle_descuento_tvTitulo);
        tvDescripcion = findViewById(R.id.detalle_descuento_tvDescripcion);
        tvDescuento = findViewById(R.id.detalle_descuento_tvDescuento);
        tvDetalleDescuento = findViewById(R.id.detalle_descuento_tvDescuentoDetalle);
        tvZona = findViewById(R.id.detalle_descuento_tvZona);
        tvHowToUse = findViewById(R.id.detalle_descuento_tvHowToUse);
        btUsaloAhora = findViewById(R.id.detalle_descuento_btUsaloAhora);
    }

    private void cargarBeneficio() {
        Call<DetalleDescuento> call = WebService.getInstance(getDocumentNumber())
                .createService(ServiceDescuentoApi.class)
                .getDescuento(mId,mOrigin);
        call.enqueue(new Callback<DetalleDescuento>() {
            @Override
            public void onResponse(Call<DetalleDescuento> call, Response<DetalleDescuento> response) {
                if (response.code() == 200){
                    mDiscount = response.body().getDiscount();
                    configurarToolbar();
                    displayImagenEmpresa();
                    displaySolapaTipoDescuento();
                    displayLogoEmpresa();
                    displayDetalleDescuento();
                }
            }

            @Override
            public void onFailure(Call<DetalleDescuento> call, Throwable t) {
                Toast.makeText(DetalleDescuentoActivity.this, "Error al cargar descuento", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void displayDetalleDescuento() {
        if (mOrigin.equals(DESCUENTO_PRIX)){
            displayDescuentoPrix();
        }else if(mDiscount.getBehaivorDiscount() == 1)
            displayDescuentoInHouse();
        else
            displayDescuentoMovistar();
    }

    private void displayDescuentoInHouse() {
        vSingle.setVisibility(View.VISIBLE);
        vMovistar.setVisibility(View.GONE);

        tvTitulo.setText(mDiscount.getTitle());
        tvTitulo.setTextColor(getResources().getColor(R.color.colorCanalEmbajador));

        tvDescripcion.setText(mDiscount.getDescription());

        tvDescuento.setVisibility(View.VISIBLE);
        tvDescuento.setText(mDiscount.getDiscount());
        ivLogoPrix.setVisibility(View.GONE);

        vLine.setBackgroundResource(R.color.colorCanalEmbajador);

        tvDetalleDescuento.setText(mDiscount.getDiscountDetail());

        if (!TextUtils.isEmpty(mDiscount.getHowToUse())){
            tvHowToUse.setVisibility(View.VISIBLE);
            tvHowToUse.setText(mDiscount.getHowToUse());
        }else tvHowToUse.setVisibility(View.GONE);

        tvZona.setText(mDiscount.getZonal());
        tvZona.setVisibility(View.VISIBLE);

        btUsaloAhora.setVisibility(View.GONE);
    }

    private void displayDescuentoMovistar() {
        vSingle.setVisibility(View.GONE);
        vMovistar.setVisibility(View.VISIBLE);
        configurarTabs();
        setearFragment();
    }

    private void displayDescuentoPrix() {
        vSingle.setVisibility(View.VISIBLE);
        vMovistar.setVisibility(View.GONE);

        tvTitulo.setText(mDiscount.getTitle());
        tvDescripcion.setText(mDiscount.getDescription());

        tvDescuento.setVisibility(View.GONE);
        ivLogoPrix.setVisibility(View.VISIBLE);

        tvDetalleDescuento.setText(mDiscount.getDiscountDetail());

        tvZona.setVisibility(View.GONE);

        btUsaloAhora.setVisibility(View.VISIBLE);
        configurarBotonUsaloAhora();
    }

    private void configurarBotonUsaloAhora() {
        btUsaloAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(mDiscount.getDeepLink()));
//                startActivity(i);
                Analitycs.logEventoClickBotonPrix(DetalleDescuentoActivity.this);
                if (isPackageExisted("pe.beyond.movistar.prioritymoments")) {
                    Intent intent = new Intent (Intent.ACTION_VIEW);
//                    intent.setData (Uri.parse("pe.beyond.movistar.priority://prix?promo=a0t1r000008sV6fAAE"));
                    intent.setData(Uri.parse("https://movistarprix.page.link/Prix"));
                    try{
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(DetalleDescuentoActivity.this, "Se produjo un error al intentar a ingresar a prix", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=pe.beyond.movistar.prioritymoments")));
                }
            }
        });
    }

    public boolean isPackageExisted(String targetPackage){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    private void displayImagenEmpresa() {
        Picasso.with(this).load(mDiscount.getDiscountImage()).into(ivImagen);
    }
    private void displaySolapaTipoDescuento() {
        if (mOrigin.equals(DESCUENTO_PRIX))
            ivTipoDescuento.setImageResource(R.drawable.ic_solapa_prix);
    }
    private void displayLogoEmpresa() {
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .oval(true)
                .build();
        Picasso.with(this).load(mDiscount.getBrandImage()).transform(transformation).into(ivLogo);
    }

    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mDiscount.getBrand());
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
                Intent intent = new Intent(DetalleDescuentoActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_account:
                Intent intent = new Intent(DetalleDescuentoActivity.this,AccountActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    private void configurarTabs() {
        tabLayout = findViewById(R.id.detalle_descuento_tabLayout);
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
            fragment = DescripcionFragment
                    .newInstance(mDiscount.getTitle(),
                            mDiscount.getDescription(),
                            mDiscount.getDiscount(),
                            mDiscount.getDiscountDetail(),
                            mDiscount.getZonal());
        }else if(currentTab == 1){
            fragment = UsaloFragment
                    .newInstance(mDiscount.getHowToUse(),
                            mDiscount.getUrlFile(),mDiscount.getUrlWeb(),
                            mDiscount.getContact().getName(),mDiscount.getContact().getPhone(),
                            mDiscount.getContact().getPhoneAnexo(),mDiscount.getContact().getPhoneWhatsapp(),
                            mDiscount.getContact().getEmail());
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detalle_descuento_movistar_content,fragment,"fragment_descuento")
                .commitAllowingStateLoss();
    }

    private void goToParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(upIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mPantallaAnterior) super.onBackPressed();
        else goToParentActivity();
    }
}
