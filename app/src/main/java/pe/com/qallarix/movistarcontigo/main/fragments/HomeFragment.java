package pe.com.qallarix.movistarcontigo.main.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.io.Serializable;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.AmbassadorChannelActivity;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.health.HealthActivity;
import pe.com.qallarix.movistarcontigo.specials.SpecialsActivity;
import pe.com.qallarix.movistarcontigo.discounts.DescuentosActivity;
import pe.com.qallarix.movistarcontigo.flexplace.FlexplaceActivity;
import pe.com.qallarix.movistarcontigo.news.pojos.DataNoticias;
import pe.com.qallarix.movistarcontigo.news.pojos.News;
import pe.com.qallarix.movistarcontigo.news.ServiceNewsApi;
import pe.com.qallarix.movistarcontigo.openlearning.OpenLearningActivity;
import pe.com.qallarix.movistarcontigo.main.MainActivity;
import pe.com.qallarix.movistarcontigo.main.NewsDashboardAdapter;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import pe.com.qallarix.movistarcontigo.vacations.VacationsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TextView tvHolaUsuario;
    private CardView cvDash1, cvDash2, cvDash3,
            cvDash4, cvDash5, cvDash6;

    private TextView tvDash1,tvDash2,tvDash3,
            tvDash4,tvDash5,tvDash6;

    private ImageView ivDash1,ivDash2,ivDash3,
            ivDash4,ivDash5,ivDash6;

    private ViewPager vpNoticias;
    private NewsDashboardAdapter noticiasDashboardAdapter;

    private List<News> noticias;
    private boolean isFlexPlace;
    private DotIndicator dotIndicator;
    private boolean isLoading = true;
    private ShimmerFrameLayout mShimmerViewContainer;
    private View viewSinConexion,viewDashBoard;
    private String mDni;
    private MainActivity parentActivity;

    private static final String TITLE_DISCOUNT = "DISCOUNT",
            TITLE_HEALTH = "HEALTH",
            TITLE_AMBASSADOR = "AMBASSADOR",
            TITLE_FLEXPLACE = "FLEXPLACE",
            TITLE_SPECIALS = "SPECIALS",
            TITLE_VACACIONES = "VACATION",
            TITLE_STUDY = "STUDY";


    private static final String ARGUMENT_NOTICIAS = "noticias";
    private static final String ARGUMENT_IS_FLEXPLACE = "isFlexPlace";


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(List<News> noticias, boolean isFlexPlace) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_NOTICIAS,(Serializable) noticias);
        args.putBoolean(ARGUMENT_IS_FLEXPLACE,isFlexPlace);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        noticias = (List<News>) getArguments().getSerializable(ARGUMENT_NOTICIAS);
        isFlexPlace = getArguments().getBoolean(ARGUMENT_IS_FLEXPLACE,false);
        mDni = ((TranquiParentActivity)getActivity()).getDocumentNumber();
        parentActivity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mShimmerViewContainer = rootView.findViewById(R.id.dashboard_shimerFrameLayout);
        viewSinConexion = rootView.findViewById(R.id.view_sin_conexion);
        viewDashBoard = rootView.findViewById(R.id.view_dashboard);
        tvHolaUsuario = rootView.findViewById(R.id.home_tvHolaUsuario);
        vpNoticias = rootView.findViewById(R.id.home_vpNoticias);
        dotIndicator = rootView.findViewById(R.id.dotIndicator);
        bindearVistas(rootView);
        return rootView;
    }

    private void settingsMenuWithoutFlexPlace() {
        settingsImagesOfButtons(
                R.drawable.ic_vacaciones,
                R.drawable.ic_embajador,
                R.drawable.ic_descuentos,
                R.drawable.ic_salud,
                R.drawable.ic_especiales,
                R.drawable.ic_open_learning);
        settingsStringsOfButtons(
                getString(R.string.dash_title_vacaciones),
                getString(R.string.dash_title_embajador),
                getString(R.string.dash_title_descuentos),
                getString(R.string.dash_title_salud),
                getString(R.string.dash_title_beneficios_especiales),
                getString(R.string.dash_title_open_learning));
        settingsClickOfButtons(isFlexPlace);
    }

    private void settingsMenuWithFlexPlace() {
        settingsImagesOfButtons(R.drawable.ic_vacaciones,
                R.drawable.ic_flexplace,
                R.drawable.ic_embajador,
                R.drawable.ic_descuentos,
                R.drawable.ic_salud,
                R.drawable.ic_especiales);
        settingsStringsOfButtons(getString(R.string.dash_title_vacaciones),
                getString(R.string.dash_title_flexplace),
                getString(R.string.dash_title_embajador),
                getString(R.string.dash_title_descuentos),
                getString(R.string.dash_title_salud),
                getString(R.string.dash_title_beneficios_especiales));
        settingsClickOfButtons(isFlexPlace);
    }

    private void settingsImagesOfButtons(int resourceImage1,int resourceImage2,int resourceImage3,
                                         int resourceImage4,int resourceImage5,int resourceImage6) {
        ivDash1.setImageResource(resourceImage1);
        ivDash2.setImageResource(resourceImage2);
        ivDash3.setImageResource(resourceImage3);
        ivDash4.setImageResource(resourceImage4);
        ivDash5.setImageResource(resourceImage5);
        ivDash6.setImageResource(resourceImage6);
    }

    private void settingsStringsOfButtons(String s1,String s2,String s3,
                                          String s4,String s5,String s6) {
        tvDash1.setText(s1);
        tvDash2.setText(s2);
        tvDash3.setText(s3);
        tvDash4.setText(s4);
        tvDash5.setText(s5);
        tvDash6.setText(s6);
    }

    private void settingsClickOfButtons(boolean habilitarFlexPlace) {
        if (habilitarFlexPlace){
            setearMetodoClick(cvDash1, VacationsActivity.class,TITLE_VACACIONES);
            setearMetodoClick(cvDash2, FlexplaceActivity.class,TITLE_FLEXPLACE);
            setearMetodoClick(cvDash3, AmbassadorChannelActivity.class,TITLE_AMBASSADOR);
            setearMetodoClick(cvDash4,DescuentosActivity.class,TITLE_DISCOUNT);
            setearMetodoClick(cvDash5, HealthActivity.class,TITLE_HEALTH);
            setearMetodoClick(cvDash6, SpecialsActivity.class,TITLE_SPECIALS);
        }else{
            setearMetodoClick(cvDash1, VacationsActivity.class,TITLE_VACACIONES);
            setearMetodoClick(cvDash2, AmbassadorChannelActivity.class,TITLE_AMBASSADOR);
            setearMetodoClick(cvDash3,DescuentosActivity.class,TITLE_DISCOUNT);
            setearMetodoClick(cvDash4, HealthActivity.class,TITLE_HEALTH);
            setearMetodoClick(cvDash5, SpecialsActivity.class,TITLE_SPECIALS);
            setearMetodoClick(cvDash6,OpenLearningActivity.class,TITLE_STUDY);
        }
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
        if (parentActivity.internetConectionExists())
            loadNewsFromNetWork();
        else {
            mostrarViewSinConexion();
        }
    }

    private void loadNewsFromNetWork() {
        Call<DataNoticias> call = WebService1.getInstance(mDni)
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
        if (isFlexPlace) settingsMenuWithFlexPlace();
        else settingsMenuWithoutFlexPlace();
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
        noticiasDashboardAdapter = new NewsDashboardAdapter(getChildFragmentManager(),noticias,numberOfImages);
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


    /**
     * metodo para dar al funcionalidad click al cardview del beneficio
     * @param cardView vista cardview clickeable del beneficio
     * @param activityClass activity.class a donde se dirigira al dar click
     */
    private void setearMetodoClick(final CardView cardView, final Class<?> activityClass,
                                   final String titleAnalitycs) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Analitycs.logEventoClickDashboard(getActivity(),titleAnalitycs);
//                if (titleAnalitycs.equals(TITLE_FLEXPLACE))
//                    mostrarMensaje("¡Muy pronto podrás registrar tu FlexPlace desde el app!");
//                else
                    irAlBeneficio(activityClass);
            }
        });
    }

    public void mostrarMensaje(String m){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(m);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        if (!getActivity().isFinishing()) alertDialog.show();
        else Toast.makeText(getActivity(), m, Toast.LENGTH_SHORT).show();
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
        cvDash1 = vistaRaiz.findViewById(R.id.dash_cv1);
        cvDash2 = vistaRaiz.findViewById(R.id.dash_cv2);
        cvDash3 = vistaRaiz.findViewById(R.id.dash_cv3);
        cvDash4 = vistaRaiz.findViewById(R.id.dash_cv4);
        cvDash5 = vistaRaiz.findViewById(R.id.dash_cv5);
        cvDash6 = vistaRaiz.findViewById(R.id.dash_cv6);

        tvDash1 = vistaRaiz.findViewById(R.id.dash_tv1);
        tvDash2 = vistaRaiz.findViewById(R.id.dash_tv2);
        tvDash3 = vistaRaiz.findViewById(R.id.dash_tv3);
        tvDash4 = vistaRaiz.findViewById(R.id.dash_tv4);
        tvDash5 = vistaRaiz.findViewById(R.id.dash_tv5);
        tvDash6 = vistaRaiz.findViewById(R.id.dash_tv6);

        ivDash1 = vistaRaiz.findViewById(R.id.dash_iv1);
        ivDash2 = vistaRaiz.findViewById(R.id.dash_iv2);
        ivDash3 = vistaRaiz.findViewById(R.id.dash_iv3);
        ivDash4 = vistaRaiz.findViewById(R.id.dash_iv4);
        ivDash5 = vistaRaiz.findViewById(R.id.dash_iv5);
        ivDash6 = vistaRaiz.findViewById(R.id.dash_iv6);
    }
}