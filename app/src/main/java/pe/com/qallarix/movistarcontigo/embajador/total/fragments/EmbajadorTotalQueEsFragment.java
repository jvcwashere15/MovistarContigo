package pe.com.qallarix.movistarcontigo.embajador.total.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.adapters.RequisitosAdapter;
import pe.com.qallarix.movistarcontigo.embajador.FragmentParentEmbajador;
import pe.com.qallarix.movistarcontigo.pojos.Requisito;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorTotalQueEsFragment extends FragmentParentEmbajador {


    private List<Requisito> requisitos;
    private ViewPager viewPager;
    private ImageView ivAtras;
    private ImageView ivAdelante;

    public EmbajadorTotalQueEsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_embajador_total_que_es, container, false);
        viewPager = rootView.findViewById(R.id.movistar_total_vpRequisitos);
        ivAtras = rootView.findViewById(R.id.embajador_ivAtras);
        ivAdelante = rootView.findViewById(R.id.embajador_ivAdelante);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargaData();
        RequisitosAdapter requisitosAdapter = new RequisitosAdapter(getChildFragmentManager(),requisitos);
        configurarFlechasViewPager(viewPager,ivAtras,ivAdelante,requisitos.size()-1);
        viewPager.setAdapter(requisitosAdapter);
    }

    private void cargaData() {
        requisitos = new ArrayList<>();
        requisitos.add(new Requisito("1","Ser trabajador activo de Telefónica del Perú S.A.A."));
        requisitos.add(new Requisito("2","Tus líneas (Fijo y Móvil) deben de estar a tu nombre."));
        requisitos.add(new Requisito("3","Aplicar a la oferta publicada en el canal embajador."));
    }

}
