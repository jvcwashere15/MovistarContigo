package pe.com.qallarix.movistarcontigo.embajador.movil.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.io.Serializable;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.embajador.movil.adapters.MovilPaqueteAdapter;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.OfferList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorMovilPaquetesFragment extends Fragment {

    private List<OfferList> paquetesMoviles;
    private ViewPager viewPager;
    private static final String ARGUMENT_PAQUETES_MOVILES = "paquetesMoviles";
    private DotIndicator dotIndicator;

    public EmbajadorMovilPaquetesFragment() {
        // Required empty public constructor
    }

    public static EmbajadorMovilPaquetesFragment newInstance(List<OfferList> paquetesMoviles) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_PAQUETES_MOVILES,(Serializable) paquetesMoviles);
        EmbajadorMovilPaquetesFragment fragment = new EmbajadorMovilPaquetesFragment();
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
    }

}
