package pe.com.qallarix.movistarcontigo.descuentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;

import pe.com.qallarix.movistarcontigo.descuentos.pojos.Descuentos;
import pe.com.qallarix.movistarcontigo.descuentos.pojos.Discount;
import pe.com.qallarix.movistarcontigo.openlearning.CategoriasActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescuentosActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private DescuentoAdapter descuentoAdapter;
    private List<Discount> discounts;
    private RecyclerView rvDescuentos;
    private long currentFiltro = -1;
    private static final int NO_FILTRAR = 0;
    private final int REQUEST_FILTRO = 1;
    private final int REQUEST_MAPA = 2;
    private final int TIPO_DESCUENTO = 1;
    private String mDni;
    private View emptyView, viewRecycler;

    private ShimmerFrameLayout mShimmerViewContainer;
    private int sizePage;
    private int currentPagina = 0;
    private long totalItems;
    private boolean isLoading = true,isLoadingMoreDiscounts = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descuentos);
        mDni = getDocumentNumber();
        emptyView = findViewById(R.id.descuentos_empty_view);
        viewRecycler = findViewById(R.id.view_recycler);
        mShimmerViewContainer = findViewById(R.id.descuentos_shimerFrameLayout);
        configurarToolbar();
        configurarRecyclerView();
        if (existConnectionInternet()){
            cargarInicialRecyclerView(NO_FILTRAR);
        }else {
            mostrarMensaje("No hay conexion a internet");
            finish();
        }
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                cargarInicialRecyclerView(currentFiltro);
//            }
//        });
    }

    private void configurarRecyclerView() {
        rvDescuentos = findViewById(R.id.descuentos_rvDescuentos);
        rvDescuentos.setHasFixedSize(true);
        rvDescuentos.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_FILTRO){
            if (resultCode == RESULT_OK){
                long result = data.getLongExtra("result",NO_FILTRAR);
                String urlImage = data.getStringExtra("icon");
                String title = data.getStringExtra("title");
                cargarChipFiltro(urlImage,title);
                cargarInicialRecyclerView(result);
            }
        }
    }

    private void cargarChipFiltro(String urlImage, String title) {
        final View viewContent = findViewById(R.id.filtro_view_content);
        RoundedImageView roundedImageView = findViewById(R.id.chip_filtro_ivFiltro);
        TextView tvTitle = findViewById(R.id.chip_filtro_tvTitulo);
        ImageView imageView = findViewById(R.id.chip_filtro_ivClose);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewContent.setVisibility(View.GONE);
                cargarInicialRecyclerView(NO_FILTRAR);
            }
        });
        tvTitle.setText(title);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .oval(true)
                .build();

        Picasso.with(this).load(urlImage).transform(transformation).into(roundedImageView);
        viewContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoading)
            mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    private void cargarInicialRecyclerView(final long categoria) {
        if (categoria != currentFiltro){
            configurarRecyclerView();
            viewRecycler.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
            final int PAGINA_INICIAL = 1;
            final Call<Descuentos> callDescuentos = construirRequest(categoria,PAGINA_INICIAL);
            callDescuentos.enqueue(new Callback<Descuentos>() {
                @Override
                public void onResponse(Call<Descuentos> call, Response<Descuentos> response) {
                    if (response.code() == 200){
                        totalItems = response.body().getTotalItems();
                        discounts = response.body().getDiscounts();
                        cargarDescuentos();
                        currentPagina = 1;
                        currentFiltro = categoria;
                        viewRecycler.setVisibility(View.VISIBLE);
                    }else{
                        Log.d("Descuentos:",response.code()+"");
                        Toast.makeText(DescuentosActivity.this, "No se pudieron cargar los descuentos", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    isLoading = false;
                    mShimmerViewContainer.setVisibility(View.GONE);
                    mShimmerViewContainer.stopShimmer();
                }

                @Override
                public void onFailure(Call<Descuentos> call, Throwable t) {
                    Toast.makeText(DescuentosActivity.this, "No se pudieron cargar los descuentos", Toast.LENGTH_SHORT).show();
                    finish();
                }

            });
        }
    }

    private void onLoadMoreDescuentos(final long categoria) {
        final int numeroPagina = currentPagina + 1;
        discounts.add(null);
        descuentoAdapter.notifyItemInserted(discounts.size()-1);
        final Call<Descuentos> callDescuentos = construirRequest(categoria,numeroPagina);
        callDescuentos.enqueue(new Callback<Descuentos>() {
            @Override
            public void onResponse(Call<Descuentos> call, Response<Descuentos> response) {
                if (response.code() == 200){
                    discounts.remove(discounts.size()-1);
                    descuentoAdapter.notifyItemRemoved(discounts.size()-1);
                    List<Discount> nuevosDescuentos = response.body().getDiscounts();
                    if (!nuevosDescuentos.isEmpty())discounts.addAll(nuevosDescuentos);
                    currentFiltro = categoria;
                    currentPagina = numeroPagina;
                }else{
                    Toast.makeText(DescuentosActivity.this, "No se pudieron cargar mas descuentos", Toast.LENGTH_SHORT).show();
                    discounts.remove(discounts.size()-1);
                    descuentoAdapter.notifyItemRemoved(discounts.size()-1);
                }
                descuentoAdapter.notifyDataSetChanged();
                descuentoAdapter.setLoaded();
                isLoadingMoreDiscounts = false;
            }

            @Override
            public void onFailure(Call<Descuentos> call, Throwable t) {
                Toast.makeText(DescuentosActivity.this, "No se pudieron cargar los descuentos", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }

    private void cargarDescuentos() {
        if (discounts.isEmpty()) {
            rvDescuentos.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            sizePage = discounts.size();
            rvDescuentos.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            descuentoAdapter = new DescuentoAdapter(rvDescuentos, DescuentosActivity.this, discounts,
                    DescuentosActivity.this, sizePage, new DescuentoAdapter.ClickDescuento() {
                @Override
                public void onClick(View view, int position) {
                    configurarClickDescuento(position);
                }
            });
            descuentoAdapter.setLoadMore(new LoadMore() {
                @Override
                public void onLoadMore() {
                    if (!isLoadingMoreDiscounts && discounts.size() < totalItems){
                        isLoadingMoreDiscounts = true;
                        onLoadMoreDescuentos(currentFiltro);
                    }
                }
            });
            rvDescuentos.setAdapter(descuentoAdapter);

        }
    }

    private void configurarClickDescuento(int position) {
        Discount currentDiscount = discounts.get(position);
        Intent intent = new Intent(DescuentosActivity.this,DetalleDescuentoActivity.class);
        intent.putExtra("id", currentDiscount.getId());
        intent.putExtra("origin", currentDiscount.getOrigin());
        Analitycs.logEventoClickItemDescuento(DescuentosActivity.this,currentDiscount);
        startActivity(intent);
    }

    public Call<Descuentos> construirRequest(long filtro,int numeroPagina){
        if (filtro == NO_FILTRAR) return WebService1.getInstance(mDni)
                .createService(ServiceDescuentoApi.class)
                .getListaDescuentos(numeroPagina);
        return WebService1.getInstance(mDni)
                .createService(ServiceDescuentoApi.class)
                .getListaDescuentos(numeroPagina,filtro);
    }

    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Descuentos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    private void irAFiltrarPorCategoria(){
        Intent mIntent = new Intent(this, CategoriasActivity.class);
        mIntent.putExtra("tipo_descuento",TIPO_DESCUENTO);
        startActivityForResult(mIntent, REQUEST_FILTRO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_descuentos, menu);
//        View view = menu.findItem(R.id.action_account).getActionView();
//        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
//        tvIniciales.setText(obtenerIniciales());
//        tvIniciales.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DescuentosActivity.this,AccountActivity.class);
//                startActivity(intent);
//            }
//        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_account:
//                Intent intent = new Intent(DescuentosActivity.this,AccountActivity.class);
//                startActivity(intent);
//                return true;
            case R.id.action_search:
                irAFiltrarPorCategoria();
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

    public String getDocumentNumber(){
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        return sharedPreferences.getString("documentNumber","");
    }


}
