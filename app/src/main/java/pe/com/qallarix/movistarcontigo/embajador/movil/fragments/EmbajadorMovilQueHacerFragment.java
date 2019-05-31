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
import pe.com.qallarix.movistarcontigo.embajador.movil.adapters.MovilPasosAdapter;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.SelfhelpList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorMovilQueHacerFragment extends FragmentParentEmbajador {

    private List<SelfhelpList> pasos;
    private ViewPager viewPager;
    private static final String ARGUMENT_PASOS = "pasos";
    private ImageView ivAtras, ivAdelante;

    public EmbajadorMovilQueHacerFragment() {
        // Required empty public constructor
    }

    public static EmbajadorMovilQueHacerFragment newInstance(List<SelfhelpList> pasos) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_PASOS,(Serializable) pasos);
        EmbajadorMovilQueHacerFragment fragment = new EmbajadorMovilQueHacerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pasos = (List<SelfhelpList>) getArguments().getSerializable(ARGUMENT_PASOS);
        View rootView = inflater.inflate(R.layout.fragment_embajador_movil_que_hacer, container, false);
        viewPager = rootView.findViewById(R.id.movil_vpPasos);
        ivAtras = rootView.findViewById(R.id.movil_que_hacer_ivAtras);
        ivAdelante = rootView.findViewById(R.id.movil_que_hacer_ivAdelante);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configurarFlechasViewPager(viewPager,ivAtras,ivAdelante,pasos.size()-1);
        MovilPasosAdapter movilPasosAdapter = new MovilPasosAdapter(getChildFragmentManager(),pasos);
        viewPager.setAdapter(movilPasosAdapter);
    }

}
