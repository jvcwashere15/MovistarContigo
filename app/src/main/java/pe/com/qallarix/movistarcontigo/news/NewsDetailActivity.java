package pe.com.qallarix.movistarcontigo.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;
import java.util.List;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.news.adapters.NewsAlsoAdapter;
import pe.com.qallarix.movistarcontigo.news.adapters.NewsImageAdapter;
import pe.com.qallarix.movistarcontigo.news.pojos.DataNews;
import pe.com.qallarix.movistarcontigo.news.pojos.DataNoticias;
import pe.com.qallarix.movistarcontigo.news.pojos.News;
import pe.com.qallarix.movistarcontigo.news.pojos.NewsDetail;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends TranquiParentActivity {

    private int mId;
    private boolean mPantallaAnterior = false;
    private Toolbar toolbar;
    private TextView tvTitulo, tvFecha, tvDescripcion,
            tvTambien, tvPdf, tvLink;
    private ViewPager vpImagen;
    private RecyclerView rvNoticias;
    private ProgressBar progressBar;
    private NewsDetail news;
    private List<String> imagenesNoticias;
    private DotIndicator dotIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticia);
        getDataFromExtras();
        bindearViews();
        configurarToolbar();
        configurarRecycler();
        cargarNoticia();
        cargarOtrasNoticias();
    }


    private void cargarNoticia() {
        Call<DataNews> call = WebService1.getInstance(getDocumentNumber())
                .createService(ServiceNewsApi.class)
                .getNoticia(mId);
        call.enqueue(new Callback<DataNews>() {
            @Override
            public void onResponse(Call<DataNews> call, Response<DataNews> response) {
                if (response.code() == 200){
                    DataNews dataNews = response.body();
                    if (dataNews != null) {
                        news = dataNews.getNews();
                        displayNoticia();
                    }
                }else{
                    Toast.makeText(NewsDetailActivity.this, "Error al obtener la noticia", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DataNews> call, Throwable t) {
                Toast.makeText(NewsDetailActivity.this, "Error al obtener la noticia", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void cargarOtrasNoticias() {
        Call<DataNoticias> call = WebService1.getInstance(getDocumentNumber())
                .createService(ServiceNewsApi.class)
                .getNoticiasExcepto(mId);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<DataNoticias>() {
            @Override
            public void onResponse(Call<DataNoticias> call, final Response<DataNoticias> response) {
                tvTambien.setVisibility(View.VISIBLE);
                if (response.code() == 200){
                    progressBar.setVisibility(View.INVISIBLE);
                    cargarlistaOtrasNoticias(response.body().getNews());
                }
            }

            @Override
            public void onFailure(Call<DataNoticias> call, Throwable t) {
                cargarlistaOtrasNoticias(null);
            }
        });
    }

    private void cargarlistaOtrasNoticias(final List<News> news) {
        if (news!=null && news.size()>0){
            NewsAlsoAdapter noticiasTambienAdapter =
                    new NewsAlsoAdapter(news, NewsDetailActivity.this,
                            new NewsAlsoAdapter.NotiTambienClick() {
                                @Override
                                public void clickNoticiaTambien(int position) {
                                    Intent intent = new Intent(NewsDetailActivity.this, NewsDetailActivity.class);
                                    News currentNews = news.get(position);
                                    intent.putExtra("id",String.valueOf(currentNews.getId()));
                                    startActivity(intent);
                                    finish();
                                }
                            });
            rvNoticias.setAdapter(noticiasTambienAdapter);
        }
    }

    private void getDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        mId = Integer.parseInt(extras.getString("id"));
        mPantallaAnterior = extras.getBoolean("lista_notificaciones",false);
    }

    private void bindearViews() {
        tvTitulo = findViewById(R.id.detalle_noticia_tvTitulo);
        tvFecha = findViewById(R.id.detalle_noticia_tvFecha);
        tvDescripcion = findViewById(R.id.detalle_noticia_tvDescripcion);
        vpImagen = findViewById(R.id.detalle_noticia_vpImagen);
        tvTambien = findViewById(R.id.detalle_noticia_tvTambien);
        tvLink = findViewById(R.id.detalle_noticia_tvLink);
        tvPdf = findViewById(R.id.detalle_noticia_tvPdf);
        progressBar = findViewById(R.id.detalle_noticia_progressBar);
        dotIndicator = findViewById(R.id.dotIndicator);
        rvNoticias = findViewById(R.id.detalle_noticia_rvNoticias);
    }

    private void configurarRecycler() {
        rvNoticias.setLayoutManager(new LinearLayoutManager(this));
        rvNoticias.setHasFixedSize(true);
        rvNoticias.setNestedScrollingEnabled(false);
    }



    private void displayNoticia() {
        if (news != null){
            displayNoticiaImagenes();
            displayNoticiaInformacion();
            displayNoticiaLinkWeb();
            displayNoticiaPdf();
        }

    }

    private void displayNoticiaPdf() {
        String nameFile = news.getAttachedFileName();
        if (nameFile != null && !nameFile.equals("")){
            final String file = news.getAttachedFile();
            tvPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(file));
                    Intent chooser = Intent.createChooser(intent,"Abrir PDF");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        try{
                            startActivity(chooser);
                        }catch (Exception e){
                            Toast.makeText(NewsDetailActivity.this,
                                    "El dipositivo no cuenta con una aplicación que permita visualizar PDF",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            tvPdf.setVisibility(View.VISIBLE);
        }
    }

    private void displayNoticiaLinkWeb() {
        String nameUrl = news.getAttachedUrlName();
        if (nameUrl != null && !nameUrl.equals("")){
            final String url = news.getAttachedUrl();
            tvLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
            tvLink.setVisibility(View.VISIBLE);
        }
    }

    private void displayNoticiaInformacion() {
        tvTitulo.setText(news.getTitle());
        tvFecha.setText(news.getDate());
        tvDescripcion.setText(news.getDescription());
    }

    private void displayNoticiaImagenes() {
        imagenesNoticias = news.getImageList();
        if (imagenesNoticias.size() > 1)
            dotIndicator.setNumberOfItems(imagenesNoticias.size());
        else
            dotIndicator.setVisibility(View.GONE);

        vpImagen.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotIndicator.setSelectedItem(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        NewsImageAdapter imagenNoticiasAdapter = new NewsImageAdapter(getSupportFragmentManager(),imagenesNoticias);
        vpImagen.setAdapter(imagenNoticiasAdapter);
    }

    public void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Noticias");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
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
//                Intent intent = new Intent(NewsDetailActivity.this, AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
