package pe.com.qallarix.movistarcontigo.embajador.hogar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.adapters.ComboPaqueteAdapter;
import pe.com.qallarix.movistarcontigo.adapters.ComplementoPaqueteAdapter;
import pe.com.qallarix.movistarcontigo.embajador.FragmentParentEmbajador;
import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.ComplementList;
import pe.com.qallarix.movistarcontigo.pojos.Combo;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorHogarAdicionalesFragment extends FragmentParentEmbajador {

    private ViewPager vpComplementos;
    private ViewPager vpCombos;
    private List<Combo> combos;
    private List<ComplementList> complementLists;
    private static final String ARGUMENT_COMPLEMENT_LISTS = "complementLists";
    private ImageView ivAdicionalAtras, ivAdicionalAdelante,
            ivCombosAtras,ivCombosAdelante;


    public EmbajadorHogarAdicionalesFragment() {
        // Required empty public constructor
    }

    public static EmbajadorHogarAdicionalesFragment newInstance(List<ComplementList> complementLists) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_COMPLEMENT_LISTS, (Serializable) complementLists);
        EmbajadorHogarAdicionalesFragment fragment = new EmbajadorHogarAdicionalesFragment();
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
        ivAdicionalAtras = rootView.findViewById(R.id.hogar_adicionales_ivAtras);
        ivAdicionalAdelante = rootView.findViewById(R.id.hogar_adicionales_ivAdelante);
        ivCombosAtras = rootView.findViewById(R.id.hogar_combos_ivAtras);
        ivCombosAdelante = rootView.findViewById(R.id.hogar_combos_ivAdelante);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargaDataCombos();
        configurarFlechasViewPager(vpComplementos,ivAdicionalAtras,ivAdicionalAdelante,complementLists.size()-1);
        ComplementoPaqueteAdapter complementoPaqueteAdapter = new ComplementoPaqueteAdapter(getChildFragmentManager(),complementLists);
        vpComplementos.setAdapter(complementoPaqueteAdapter);
        configurarFlechasViewPager(vpCombos,ivCombosAtras,ivCombosAdelante,combos.size()-1);
        ComboPaqueteAdapter comboPaqueteAdapter = new ComboPaqueteAdapter(getChildFragmentManager(),combos);
        vpCombos.setAdapter(comboPaqueteAdapter);
    }

    private void cargaDataCombos() {
        combos = new ArrayList<>();
        combos.add(new Combo(R.color.colorCombo1,
                "Si hoy tienes lo siguiente:",
                "60 Mbps",
                "S/ 238.70",
                "Precio regular: S/ 338.70"));
        combos.add(new Combo(R.color.colorCombo2,
                "Y decides migrar a una velocidad mayor *",
                "120 Mbps",
                "S/ 268.70",
                "Precio regular: S/ 338.70"));
        combos.add(new Combo(R.color.colorCombo3,
                "O decides reducir la velocidad de tu plan *",
                "40 Mbps",
                "S/ 198.70",
                "Precio regular: S/ 268.70"));
    }
}
