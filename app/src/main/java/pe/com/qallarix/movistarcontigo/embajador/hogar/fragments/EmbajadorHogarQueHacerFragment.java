package pe.com.qallarix.movistarcontigo.embajador.hogar.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.embajador.FragmentParentEmbajador;
import pe.com.qallarix.movistarcontigo.embajador.hogar.adapters.HogarPasosAdapter;
import pe.com.qallarix.movistarcontigo.embajador.hogar.adapters.ImportanteAdapter;
import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.SelfhelpList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorHogarQueHacerFragment extends FragmentParentEmbajador {

    private List<SelfhelpList> selfhelpLists1;
    private List<SelfhelpList> selfhelpLists2;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private ImageView ivAtras,ivAdelante;
    private static final String ARGUMENT_SELFHELPLIST_1 = "selfhelpLists1";
    private static final String ARGUMENT_SELFHELPLIST_2 = "selfhelpLists2";


    public EmbajadorHogarQueHacerFragment() {
        // Required empty public constructor
    }

    public static EmbajadorHogarQueHacerFragment newInstance(List<SelfhelpList> selfhelpLists1,
                                                             List<SelfhelpList> selfhelpLists2) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_SELFHELPLIST_1, (Serializable) selfhelpLists1);
        args.putSerializable(ARGUMENT_SELFHELPLIST_2, (Serializable) selfhelpLists2);
        EmbajadorHogarQueHacerFragment fragment = new EmbajadorHogarQueHacerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selfhelpLists1 = (List<SelfhelpList>) getArguments().getSerializable(ARGUMENT_SELFHELPLIST_1);
        selfhelpLists2 = (List<SelfhelpList>) getArguments().getSerializable(ARGUMENT_SELFHELPLIST_2);
        View rootView = inflater.inflate(R.layout.fragment_embajador_hogar_que_hacer, container, false);
        viewPager = rootView.findViewById(R.id.hogar_vpPasos);
        ivAtras = rootView.findViewById(R.id.hogar_que_hacer_ivAtras);
        ivAdelante = rootView.findViewById(R.id.hogar_que_hacer_ivAdelante);
        recyclerView = rootView.findViewById(R.id.hogar_rvImportante);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configurarFlechasViewPager(viewPager,ivAtras,ivAdelante,selfhelpLists1.size()-1);
        HogarPasosAdapter hogarPasosAdapter = new HogarPasosAdapter(getChildFragmentManager(),selfhelpLists1);
        viewPager.setAdapter(hogarPasosAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ImportanteAdapter importanteAdapter = new ImportanteAdapter(selfhelpLists2,getActivity());
        recyclerView.setAdapter(importanteAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

}
