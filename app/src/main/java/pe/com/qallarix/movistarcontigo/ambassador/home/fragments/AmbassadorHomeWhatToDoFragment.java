package pe.com.qallarix.movistarcontigo.ambassador.home.fragments;


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

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.io.Serializable;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.home.adapters.HogarPasosAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.home.adapters.ImportantAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.home.pojos.SelfhelpList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmbassadorHomeWhatToDoFragment extends Fragment {

    private List<SelfhelpList> selfhelpLists1;
    private List<SelfhelpList> selfhelpLists2;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private DotIndicator dotIndicatorPasos;
    private static final String ARGUMENT_SELFHELPLIST_1 = "selfhelpLists1";
    private static final String ARGUMENT_SELFHELPLIST_2 = "selfhelpLists2";


    public AmbassadorHomeWhatToDoFragment() {
        // Required empty public constructor
    }

    public static AmbassadorHomeWhatToDoFragment newInstance(List<SelfhelpList> selfhelpLists1,
                                                             List<SelfhelpList> selfhelpLists2) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_SELFHELPLIST_1, (Serializable) selfhelpLists1);
        args.putSerializable(ARGUMENT_SELFHELPLIST_2, (Serializable) selfhelpLists2);
        AmbassadorHomeWhatToDoFragment fragment = new AmbassadorHomeWhatToDoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selfhelpLists1 = (List<SelfhelpList>) getArguments().getSerializable(ARGUMENT_SELFHELPLIST_1);
        selfhelpLists2 = (List<SelfhelpList>) getArguments().getSerializable(ARGUMENT_SELFHELPLIST_2);
        View rootView = inflater.inflate(R.layout.fragment_embajador_hogar_que_hacer, container, false);
        dotIndicatorPasos = rootView.findViewById(R.id.hogar_pasos_dotIndicator);
        viewPager = rootView.findViewById(R.id.hogar_vpPasos);
        recyclerView = rootView.findViewById(R.id.hogar_rvImportante);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (selfhelpLists1.size() > 1)
            dotIndicatorPasos.setNumberOfItems(selfhelpLists1.size());
        else
            dotIndicatorPasos.setVisibility(View.GONE);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotIndicatorPasos.setSelectedItem(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        HogarPasosAdapter hogarPasosAdapter = new HogarPasosAdapter(getChildFragmentManager(),selfhelpLists1);
        viewPager.setAdapter(hogarPasosAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ImportantAdapter importantAdapter = new ImportantAdapter(selfhelpLists2,getActivity());
        recyclerView.setAdapter(importantAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

}
