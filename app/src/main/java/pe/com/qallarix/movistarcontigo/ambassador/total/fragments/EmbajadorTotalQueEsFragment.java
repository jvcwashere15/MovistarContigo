package pe.com.qallarix.movistarcontigo.ambassador.total.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.total.adapters.RequisitosAdapter;
import pe.com.qallarix.movistarcontigo.pojos.Requisito;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorTotalQueEsFragment extends Fragment {


    private List<Requisito> requisitos;
    private ViewPager viewPager;
    private DotIndicator dotIndicator;


    public EmbajadorTotalQueEsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_embajador_total_que_es, container, false);
        viewPager = rootView.findViewById(R.id.movistar_total_vpRequisitos);
        dotIndicator = rootView.findViewById(R.id.movistar_total_requisitos_dotIndicator);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargaData();

        if (requisitos.size() > 1)
            dotIndicator.setNumberOfItems(requisitos.size());
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

        RequisitosAdapter requisitosAdapter = new RequisitosAdapter(getChildFragmentManager(),requisitos);
        viewPager.setAdapter(requisitosAdapter);
    }

    private void cargaData() {
        requisitos = new ArrayList<>();
        requisitos.add(new Requisito("1","Ser trabajador activo de Telefónica del Perú S.A.A."));
        requisitos.add(new Requisito("2","Tus líneas (Fijo y Móvil) deben de estar a tu nombre."));
        requisitos.add(new Requisito("3","Aplicar a la oferta publicada en el canal embajador."));
    }

}
