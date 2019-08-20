package pe.com.qallarix.movistarcontigo.ambassador.mobile.fragments;

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
import pe.com.qallarix.movistarcontigo.ambassador.mobile.adapters.MovilPasosAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos.SelfhelpList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmbassadorMobileWhatToDoFragment extends Fragment {
    private List<SelfhelpList> pasos;
    private ViewPager viewPager;
    private static final String ARGUMENT_PASOS = "pasos";
    private DotIndicator dotIndicator;

    public AmbassadorMobileWhatToDoFragment() {
        // Required empty public constructor
    }

    public static AmbassadorMobileWhatToDoFragment newInstance(List<SelfhelpList> pasos) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_PASOS,(Serializable) pasos);
        AmbassadorMobileWhatToDoFragment fragment = new AmbassadorMobileWhatToDoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pasos = (List<SelfhelpList>) getArguments().getSerializable(ARGUMENT_PASOS);
        View rootView = inflater.inflate(R.layout.fragment_embajador_movil_que_hacer, container, false);
        viewPager = rootView.findViewById(R.id.movil_vpPasos);
        dotIndicator = rootView.findViewById(R.id.movil_pasos_dotIndicator);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (pasos.size() > 1)
            dotIndicator.setNumberOfItems(pasos.size());
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
        MovilPasosAdapter movilPasosAdapter = new MovilPasosAdapter(getChildFragmentManager(),pasos);
        viewPager.setAdapter(movilPasosAdapter);
    }

}
