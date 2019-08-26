package pe.com.qallarix.movistarcontigo.ambassador.total.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.total.adapters.PlanMovistarTotalAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.total.adapters.TipoFacturacionAdapter;
import pe.com.qallarix.movistarcontigo.pojos.PlanMovistarTotal;
import pe.com.qallarix.movistarcontigo.pojos.TipoFacturacion;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmbassadorTotalHowIAmFragment extends Fragment {

    LinearLayout lytCicloFacturacion1,lytCicloFacturacion2,
            lytCicloFacturacion3;

    TextView tvCicloFacturacion1,tvCicloFacturacion2,
            tvCicloFacturacion3;

    LinearLayout lytDescripcion1,lytDescripcion2,
            lytDescripcion3;

    ImageView iv1, iv2, iv3;

    List<TipoFacturacion> tipoFacturacions;
    private ViewPager vpTipoFacturacion;

    List<PlanMovistarTotal> planMovistarTotals;
    private ViewPager vpPlanMovistarTotal;

    private DotIndicator dotPlanes;
    private DotIndicator dotFacturacion;


    public AmbassadorTotalHowIAmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_embajador_total_como_soy, container, false);
        lytCicloFacturacion1 = rootView.findViewById(R.id.embajador_total_facturacion_lyt1);
        lytCicloFacturacion2 = rootView.findViewById(R.id.embajador_total_facturacion_lyt2);
        lytCicloFacturacion3 = rootView.findViewById(R.id.embajador_total_facturacion_lyt3);

        tvCicloFacturacion1 = rootView.findViewById(R.id.embajador_total_facturacion_tv1);
        tvCicloFacturacion2 = rootView.findViewById(R.id.embajador_total_facturacion_tv2);
        tvCicloFacturacion3 = rootView.findViewById(R.id.embajador_total_facturacion_tv3);


        lytDescripcion1 = rootView.findViewById(R.id.embajador_total_facturacion_descripcion1);
        lytDescripcion2 = rootView.findViewById(R.id.embajador_total_facturacion_descripcion2);
        lytDescripcion3 = rootView.findViewById(R.id.embajador_total_facturacion_descripcion3);

        iv1 = rootView.findViewById(R.id.embajador_total_facturacion_iv1);
        iv2 = rootView.findViewById(R.id.embajador_total_facturacion_iv2);
        iv3 = rootView.findViewById(R.id.embajador_total_facturacion_iv3);

        dotFacturacion = rootView.findViewById(R.id.movistar_total_facturacion_dotIndicator);
        dotPlanes = rootView.findViewById(R.id.movistar_total_planes_dotIndicator);

        vpTipoFacturacion = rootView.findViewById(R.id.embajador_movistar_total_vpTipoFacturacion);
        vpPlanMovistarTotal = rootView.findViewById(R.id.embajador_movistar_total_vpPlanes);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cargaDataPlanesMovistarTotal();
        cargaDataTipoFacturacion();

        configurarBotonDetalleImportante(lytCicloFacturacion1,lytDescripcion1,tvCicloFacturacion1,iv1);
        configurarBotonDetalleImportante(lytCicloFacturacion2,lytDescripcion2,tvCicloFacturacion2,iv2);
        configurarBotonDetalleImportante(lytCicloFacturacion3,lytDescripcion3,tvCicloFacturacion3,iv3);

        if (planMovistarTotals.size() > 1)
            dotPlanes.setNumberOfItems(planMovistarTotals.size());
        else
            dotPlanes.setVisibility(View.GONE);

        vpPlanMovistarTotal.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotPlanes.setSelectedItem(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        PlanMovistarTotalAdapter planMovistarTotalAdapter = new PlanMovistarTotalAdapter(getChildFragmentManager(),planMovistarTotals);
        vpPlanMovistarTotal.setAdapter(planMovistarTotalAdapter);

        if (tipoFacturacions.size() > 1)
            dotFacturacion.setNumberOfItems(tipoFacturacions.size());
        else
            dotFacturacion.setVisibility(View.GONE);

        vpTipoFacturacion.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotFacturacion.setSelectedItem(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        TipoFacturacionAdapter tipoFacturacionAdapter = new TipoFacturacionAdapter(getChildFragmentManager(),tipoFacturacions);
        vpTipoFacturacion.setAdapter(tipoFacturacionAdapter);
    }

    private void cargaDataPlanesMovistarTotal() {
        planMovistarTotals = new ArrayList<>();
        planMovistarTotals.add(new PlanMovistarTotal(
                "40 Mbps",
                "1 módem Wifi",
                "107 canales SD + 61 canales HD",
                "Incluye 2 decos HD",
                "Llamadas ilimitadas a fijos Movistar + 100 min. a otros operadores fijos",
                "2 líneas móviles",
                "8 GB libres cada uno",
                R.drawable.apps_ilimitadas_total,
                "S/ 199",
                "Precio regular: S/ 269"));
        planMovistarTotals.add(new PlanMovistarTotal(
                "60 Mbps",
                "1 módem Smart Wifi",
                "107 canales SD + 61 canales HD",
                "Incluye 2 decos HD",
                "Llamadas ilimitadas a fijos Movistar + 100 min. a otros operadores fijos",
                "2 líneas móviles",
                "10 GB libres cada uno",
                R.drawable.apps_ilimitadas_total,
                "S/ 229",
                "Precio regular: S/ 299"));
        planMovistarTotals.add(new PlanMovistarTotal(
                "120 Mbps",
                "1 deco smart + 1 módem Smart Wifi",
                "107 canales SD + 61 canales HD",
                "Incluye 1 Deco Smart y 1 Deco HD",
                "Llamadas ilimitadas a fijos Movistar + 100 min. a otros operadores fijos",
                "2 líneas móviles",
                "15 GB libres cada uno",
                R.drawable.apps_ilimitadas_total,
                "S/ 289",
                "Precio regular: S/ 359"));
        planMovistarTotals.add(new PlanMovistarTotal(
                "200 Mbps",
                "1 deco smart  + 1 módem Smart Wifi",
                "107 canales + 61 canales HD",
                "Incluye 1 Deco Smart y 1 Deco HD",
                "Llamadas ilimitadas a fijos Movistar + 100 min. a otros operadores fijos",
                "2 líneas móviles",
                "25 GB libres cada uno",
                R.drawable.apps_ilimitadas_total,
                "S/ 389",
                "Precio regular: S/ 459"));
    }

    private void cargaDataTipoFacturacion() {
        tipoFacturacions = new ArrayList<>();
        tipoFacturacions.add(new TipoFacturacion("Los números telefónicos (Fijo y móviles)",R.drawable.tipo_facturacion_1));
        tipoFacturacions.add(new TipoFacturacion("Los montos a pagar por cada servicio (Fijo y móvil) y fecha de vencimiento",R.drawable.tipo_facturacion_2));
        tipoFacturacions.add(new TipoFacturacion("Fecha de vencimiento",R.drawable.tipo_facturacion_3));

    }

    private void configurarBotonDetalleImportante(final LinearLayout lytFacturacion, final LinearLayout lytDescripcion, final TextView tvCiclo, final ImageView iv){
        lytFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lytDescripcion.getVisibility() == View.VISIBLE){
                    lytDescripcion.setVisibility(View.GONE);
                    iv.setRotation(90);
                    tvCiclo.setTextColor(getResources().getColor(R.color.colorTextoCanalEmbajador));
                    return;
                }
                lytDescripcion.setVisibility(View.VISIBLE);
                iv.setRotation(270);
                tvCiclo.setTextColor(getResources().getColor(R.color.colorCanalEmbajador));
            }
        });
    }


}
