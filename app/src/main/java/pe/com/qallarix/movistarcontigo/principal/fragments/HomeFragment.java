package pe.com.qallarix.movistarcontigo.principal.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.io.Serializable;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.BeneficiosEspecialesActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DescuentosActivity;
import pe.com.qallarix.movistarcontigo.embajador.CanalEmbajadorActivity;
import pe.com.qallarix.movistarcontigo.noticias.DataNoticias;
import pe.com.qallarix.movistarcontigo.noticias.News;
import pe.com.qallarix.movistarcontigo.noticias.ServiceNewsApi;
import pe.com.qallarix.movistarcontigo.openlearning.OpenLearningActivity;
import pe.com.qallarix.movistarcontigo.principal.MainActivity;
import pe.com.qallarix.movistarcontigo.principal.NoticiasDashboardAdapter;
import pe.com.qallarix.movistarcontigo.salud.SaludActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TextView tvHolaUsuario;
    private CardView cvDescuentos, cvSalud, cvCanalEmbajador,
            cvEstudios, cvBeneficiosEspeciales;

    private ImageView ivAtras,ivAdelante;
    private ViewPager vpNoticias;
    private NoticiasDashboardAdapter noticiasDashboardAdapter;

    private List<News> noticias;
    private DotIndicator dotIndicator;
    private boolean isLoading = true;
    private ShimmerFrameLayout mShimmerViewContainer;
    private View viewSinConexion,viewDashBoard;
    private String mDni;
    private MainActivity parentActivity;

    private static final String TITLE_DISCOUNT = "DISCOUNT",
            TITLE_HEALTH = "HEALTH",
            TITLE_AMBASSADOR = "AMBASSADOR",
            TITLE_STUDY = "STUDY",
            TITLE_SPECIALS = "SPECIALS";

    private static final String ARGUMENT_NOTICIAS = "noticias";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(List<News> noticias) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_NOTICIAS,(Serializable) noticias);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        noticias = (List<News>) getArguments().getSerializable(ARGUMENT_NOTICIAS);
        mDni = ((TranquiParentActivity)getActivity()).getDocumentNumber();
        parentActivity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mShimmerViewContainer = rootView.findViewById(R.id.dashboard_shimerFrameLayout);
        viewSinConexion = rootView.findViewById(R.id.view_sin_conexion);
        viewDashBoard = rootView.findViewById(R.id.view_dashboard);
        ivAtras = rootView.findViewById(R.id.btnImagenAtras);
        ivAdelante = rootView.findViewById(R.id.btnImagenAdelante);
        tvHolaUsuario = rootView.findViewById(R.id.home_tvHolaUsuario);
        vpNoticias = rootView.findViewById(R.id.home_vpNoticias);
        dotIndicator = rootView.findViewById(R.id.dotIndicator);
        bindearVistas(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoading)
            mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (noticias != null) {
            cargarDashBoardFromCache();
            mostrarViewDashboard();
        }else{
            mostrarViewLoading();
            cargarDashBoardFromNetWork();
        }
    }

    private void mostrarViewDashboard() {
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        viewSinConexion.setVisibility(View.GONE);
        viewDashBoard.setVisibility(View.VISIBLE);
        isLoading = false;
    }

    private void mostrarViewLoading() {
        if (!mShimmerViewContainer.isShimmerStarted()) mShimmerViewContainer.startShimmer();
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        viewSinConexion.setVisibility(View.GONE);
        viewDashBoard.setVisibility(View.GONE);
        isLoading = true;
    }

    private void mostrarViewSinConexion() {
        if (mShimmerViewContainer.isShimmerStarted())mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        viewSinConexion.setVisibility(View.VISIBLE);
        viewDashBoard.setVisibility(View.GONE);
        isLoading = false;
    }


    private void cargarDashBoardFromNetWork() {
        if (parentActivity.existConnectionInternet())
            loadNewsFromNetWork();
        else {
            mostrarViewSinConexion();
        }
    }

    private void loadNewsFromNetWork() {
       Call<DataNoticias> call = WebService.getInstance(mDni)
                .createService(ServiceNewsApi.class)
                .getNoticias();
        call.enqueue(new Callback<DataNoticias>() {
            @Override
            public void onResponse(Call<DataNoticias> call, Response<DataNoticias> response) {
                if (response.code() == 200){
                    noticias = response.body().getNews();
                    parentActivity.setNoticias(noticias);
                    cargarDashBoardFromCache();
                    mostrarViewDashboard();
                }else{
                    mostrarViewSinConexion();
                }
            }
            @Override
            public void onFailure(Call<DataNoticias> call, Throwable t) {
                mostrarViewSinConexion();
            }
        });
    }

    private void cargarDashBoardFromCache() {
        configurarViewPagerNoticiasDestacadas();
        mostrarDatosUsuario();
        configurarBotonesBeneficios();
    }

    private void configurarViewPagerNoticiasDestacadas() {
        int numberOfImages = 0;
        if (noticias.size() < 3) numberOfImages = noticias.size();
        else numberOfImages = 3;

        dotIndicator.setNumberOfItems(numberOfImages);

        vpNoticias.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotIndicator.setSelectedItem(position,true);
//                if (position == 0) ivAtras.setVisibility(View.GONE);
//                else if (position == 2) ivAdelante.setVisibility(View.GONE);
//                else{
//                    ivAtras.setVisibility(View.VISIBLE);
//                    ivAdelante.setVisibility(View.VISIBLE);
//                }
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });
        if (!isAdded()) return;
        noticiasDashboardAdapter = new NoticiasDashboardAdapter(getChildFragmentManager(),noticias,numberOfImages);
        vpNoticias.setAdapter(noticiasDashboardAdapter);
    }

    private void mostrarDatosUsuario() {
        SharedPreferences sharedPreferences = parentActivity.getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains("firstName")){
            String firstName = sharedPreferences.getString("firstName","");
            if (!firstName.equals("")){
                tvHolaUsuario.setText("¡Hola "+firstName+"!");
            }
        }
    }
    private void configurarBotonesBeneficios() {
        setearMetodoClick(cvDescuentos,DescuentosActivity.class);
        setearMetodoClick(cvEstudios, OpenLearningActivity.class);
        setearMetodoClick(cvSalud,SaludActivity.class);
        setearMetodoClick(cvCanalEmbajador,CanalEmbajadorActivity.class);
        setearMetodoClick(cvBeneficiosEspeciales,BeneficiosEspecialesActivity.class);
    }


    /**
     * metodo para dar al funcionalidad click al cardview del beneficio
     * @param cardView vista cardview clickeable del beneficio
     * @param activityClass activity.class a donde se dirigira al dar click
     */
    private void setearMetodoClick(final CardView cardView, final Class<?> activityClass) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView.equals(cvBeneficiosEspeciales)) Analitycs.logEventoClickDashboard(getActivity(),TITLE_SPECIALS);
                else if (cardView.equals(cvCanalEmbajador)) Analitycs.logEventoClickDashboard(getActivity(),TITLE_AMBASSADOR);
                else if (cardView.equals(cvDescuentos)) Analitycs.logEventoClickDashboard(getActivity(),TITLE_DISCOUNT);
                else if (cardView.equals(cvEstudios)) Analitycs.logEventoClickDashboard(getActivity(),TITLE_STUDY);
                else if (cardView.equals(cvSalud)) Analitycs.logEventoClickDashboard(getActivity(),TITLE_HEALTH);
                irAlBeneficio(activityClass);
            }
        });
    }


    /**
     * metodo para ir a la actividad de un beneficio específico
     * @param activityClass la activity.class que se abrirá
     */
    public void irAlBeneficio(Class<?> activityClass){
        Intent intent =  new Intent(getActivity(),activityClass);
        startActivity(intent);
    }

    /**
     * metodo para conectar las vistas con sus referencias
     * @param vistaRaiz referencia a la vista inflada para el fragment
     */
    public void bindearVistas(View vistaRaiz){
        cvDescuentos = vistaRaiz.findViewById(R.id.dash_cvDescuentos);
        cvSalud = vistaRaiz.findViewById(R.id.dash_cvSalud);
        cvEstudios = vistaRaiz.findViewById(R.id.dash_cvOpenLearning);
        cvCanalEmbajador = vistaRaiz.findViewById(R.id.dash_cvCanalEmbajador);
        cvBeneficiosEspeciales = vistaRaiz.findViewById(R.id.dash_cvBeneficiosEspeciales);
    }

}
