package pe.com.qallarix.movistarcontigo.openlearning;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autentication.account.AccountActivityView;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;

public class DetalleEstudioActivity extends TranquiParentActivity {

    Toolbar toolbar;
    long mId;
    String mTitle;
    String mDescription;
    String mInstitution;
    String mInstitutionImage;
    String mDiscount;
    String mDiscountDetail;
    String mDiscountImage;

    private ImageView ivImagen;
    private ImageView ivLogo;
    private TextView tvTitulo;
    private TextView tvDescripcion;
    private TextView tvDescuento;
    private TextView tvDetalleDescuento;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estudio);
        configurarToolbar();

        Bundle extras = getIntent().getExtras();
        mId = extras.getLong("id");
        mTitle = extras.getString("title");
        mDescription = extras.getString("description");
        mInstitution = extras.getString("institution");
        mInstitutionImage = extras.getString("institutionImage");
        mDiscount = extras.getString("discount");
        mDiscountDetail = extras.getString("discountDetail");
        mDiscountImage = extras.getString("discountImage");

        bindearVistas();
        cargarBeneficio();

    }

    private void bindearVistas() {
        ivImagen = findViewById(R.id.detalle_estudio_ivImagen);
        ivLogo = findViewById(R.id.detalle_estudio_ivLogo);
        tvDescripcion = findViewById(R.id.detalle_estudio_tvDescripcion);
        tvDescuento = findViewById(R.id.detalle_estudio_tvDescuento);
        tvDetalleDescuento = findViewById(R.id.detalle_estudio_tvDescuentoDetalle);
        tvTitulo = findViewById(R.id.detalle_estudio_tvTitulo);
    }

    private void cargarBeneficio() {
        tvTitulo.setText(mTitle);
        tvDescripcion.setText(mDescription);
        tvDescuento.setText(mDiscount);
        tvDetalleDescuento.setText(mDiscountDetail);
        Picasso.with(this).load(mDiscountImage).placeholder(R.drawable.placeholder_imagen).into(ivImagen);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .oval(true)
                .build();
        Picasso.with(this).load(mInstitutionImage).transform(transformation).into(ivLogo);
    }

    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Detalle descuento");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void usarDescuento(View view){
        findViewById(R.id.estudio_detalle_lytDetalle).setVisibility(View.GONE);
        findViewById(R.id.estudio_detalle_lytUsar).setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(DetalleEstudioActivity.this, AccountActivityView.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_account:
                Intent intent = new Intent(DetalleEstudioActivity.this, AccountActivityView.class);
                startActivity(intent);
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

