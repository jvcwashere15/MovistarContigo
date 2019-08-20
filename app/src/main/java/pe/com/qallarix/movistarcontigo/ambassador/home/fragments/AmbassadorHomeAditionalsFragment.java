package pe.com.qallarix.movistarcontigo.ambassador.home.fragments;

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
import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.home.adapters.ComboPackAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.home.adapters.ComplementPackAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.home.pojos.ComplementList;
import pe.com.qallarix.movistarcontigo.pojos.Combo;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmbassadorHomeAditionalsFragment extends Fragment {

    private ViewPager vpComplementos;
    private ViewPager vpCombos;
    private List<Combo> combos;
    private DotIndicator dotIndicatorCombos, dotIndicatorAdicionales;
    private List<ComplementList> complementLists;
    private static final String ARGUMENT_COMPLEMENT_LISTS = "complementLists";


    public AmbassadorHomeAditionalsFragment() {
        // Required empty public constructor
    }

    public static AmbassadorHomeAditionalsFragment newInstance(List<ComplementList> complementLists) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_COMPLEMENT_LISTS, (Serializable) complementLists);
        AmbassadorHomeAditionalsFragment fragment = new AmbassadorHomeAditionalsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        complementLists = (List<ComplementList>) getArguments().get(ARGUMENT_COMPLEMENT_LISTS);
        View rootView = inflater.inflate(R.layout.fragment_embajador_hogar_adicionales, container, false);
        vpComplementos = rootView.findViewById(R.id.hogar_vpComplementos);
        vpCombos = rootView.findViewById(R.id.hogar_vpCombos);
        dotIndicatorAdicionales = rootView.findViewById(R.id.hogar_complementos_dot);
        dotIndicatorCombos = rootView.findViewById(R.id.hogar_combos_dot);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargaDataCombos();

        if (complementLists.size() > 1)
            dotIndicatorAdicionales.setNumberOfItems(complementLists.size());
        else
            dotIndicatorAdicionales.setVisibility(View.GONE);

        vpComplementos.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotIndicatorAdicionales.setSelectedItem(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });
        ComplementPackAdapter complementPackAdapter =
                new ComplementPackAdapter(getChildFragmentManager(),complementLists);
        vpComplementos.setAdapter(complementPackAdapter);

        if (combos.size() > 1)
            dotIndicatorCombos.setNumberOfItems(combos.size());
        else
            dotIndicatorCombos.setVisibility(View.GONE);

        vpCombos.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotIndicatorCombos.setSelectedItem(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });
        ComboPackAdapter comboPackAdapter = new ComboPackAdapter(getChildFragmentManager(),combos);
        vpCombos.setAdapter(comboPackAdapter);
    }

    private void cargaDataCombos() {
        combos = new ArrayList<>();
        combos.add(new Combo(R.drawable.header_paquete_celeste,R.color.colorCombo1,
                "Si hoy tienes lo siguiente:",
                "60 Mbps",
                "S/ 238.70",
                "Precio regular: S/ 338.70"));
        combos.add(new Combo(R.drawable.header_paquete_verde,R.color.colorCombo2,
                "Y decides migrar a una velocidad mayor *",
                "120 Mbps",
                "S/ 268.70",
                "Precio regular: S/ 338.70"));
        combos.add(new Combo(R.drawable.header_paquete_morado,R.color.colorCombo3,
                "O decides reducir la velocidad de tu plan *",
                "40 Mbps",
                "S/ 198.70",
                "Precio regular: S/ 268.70"));
    }
}
