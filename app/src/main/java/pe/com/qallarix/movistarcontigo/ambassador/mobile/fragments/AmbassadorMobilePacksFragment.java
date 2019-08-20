package pe.com.qallarix.movistarcontigo.ambassador.mobile.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.io.Serializable;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.adapters.MovilPaqueteAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos.OfferList;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmbassadorMobilePacksFragment extends Fragment {

    private List<OfferList> paquetesMoviles;
    private ViewPager viewPager;
    private TextView tvFooter;
    private static final String ARGUMENT_PAQUETES_MOVILES = "paquetesMoviles";
    private DotIndicator dotIndicator;

    public AmbassadorMobilePacksFragment() {
        // Required empty public constructor
    }

    public static AmbassadorMobilePacksFragment newInstance(List<OfferList> paquetesMoviles) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_PAQUETES_MOVILES,(Serializable) paquetesMoviles);
        AmbassadorMobilePacksFragment fragment = new AmbassadorMobilePacksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paquetesMoviles = (List<OfferList>) getArguments().getSerializable(ARGUMENT_PAQUETES_MOVILES);
        View rootView = inflater.inflate(R.layout.fragment_embajador_movil_paquetes, container, false);
        viewPager = rootView.findViewById(R.id.movil_vpPaquetes);
        dotIndicator = rootView.findViewById(R.id.movil_paquetes_dotIndicator);
        tvFooter = rootView.findViewById(R.id.ambassador_mobile_tvFooter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (paquetesMoviles.size() > 1)
            dotIndicator.setNumberOfItems(paquetesMoviles.size());
        else
            dotIndicator.setVisibility(View.GONE);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotIndicator.setSelectedItem(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });
        MovilPaqueteAdapter movilPaqueteAdapter = new MovilPaqueteAdapter(getChildFragmentManager(),paquetesMoviles);
        viewPager.setAdapter(movilPaqueteAdapter);

        CharSequence textStyle = TranquiParentActivity.normal(
                "Adquiere tu plan en cualquiera de nuestros puntos de venta  con tu ",
                TranquiParentActivity.bold(TranquiParentActivity.color(
                        getActivity().getResources().getColor(R.color.loginBoton),"DNI"
                ))
        );
        tvFooter.setText(textStyle);

    }

}
