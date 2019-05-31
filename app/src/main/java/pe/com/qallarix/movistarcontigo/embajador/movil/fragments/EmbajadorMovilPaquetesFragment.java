package pe.com.qallarix.movistarcontigo.embajador.movil.fragments;


import android.annotation.SuppressLint;
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
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.embajador.FragmentParentEmbajador;
import pe.com.qallarix.movistarcontigo.embajador.movil.adapters.MovilPaqueteAdapter;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.OfferList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorMovilPaquetesFragment extends FragmentParentEmbajador {

    private List<OfferList> paquetesMoviles;
    private ViewPager viewPager;
    private ImageView ivAtras, ivAdelante;
    private static final String ARGUMENT_PAQUETES_MOVILES = "paquetesMoviles";

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
        ivAtras = rootView.findViewById(R.id.embajador_paquete_movil_ivAtras);
        ivAdelante = rootView.findViewById(R.id.embajador_paquete_movil_ivAdelante);
        viewPager = rootView.findViewById(R.id.movil_vpPaquetes);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configurarFlechasViewPager(viewPager,ivAtras,ivAdelante,paquetesMoviles.size()-1);
        MovilPaqueteAdapter movilPaqueteAdapter = new MovilPaqueteAdapter(getChildFragmentManager(),paquetesMoviles);
        viewPager.setAdapter(movilPaqueteAdapter);
    }

}
