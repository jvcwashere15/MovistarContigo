package pe.com.qallarix.movistarcontigo.ambassador.total.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.pojos.PlanMovistarTotal;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovistarTotalPlanFragment extends Fragment {

    private PlanMovistarTotal planMovistarTotal;
    private TextView tvInternetMegas;
    private TextView tvInternetIncluye;
    private TextView tvTVCanales;
    private TextView tvTVIncluye;
    private TextView tvFijaDescripcion;
    private TextView tvMovilNLineas;
    private TextView tvMovilGigas;
    private ImageView ivApps;
    private TextView tvPrecioRegular;
    private TextView tvPrecioEmbajador;

    private static final String ARGUMENT_PLAN_MOVISTAR_TOTAL = "planMovistarTotal";

    public MovistarTotalPlanFragment() {
        // Required empty public constructor
    }


    public static MovistarTotalPlanFragment newInstance(PlanMovistarTotal planMovistarTotal) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_PLAN_MOVISTAR_TOTAL,planMovistarTotal);
        MovistarTotalPlanFragment fragment = new MovistarTotalPlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        planMovistarTotal = (PlanMovistarTotal) getArguments().getSerializable(ARGUMENT_PLAN_MOVISTAR_TOTAL);
        View rootView = inflater.inflate(R.layout.fragment_plan_movistar_total, container, false);
        tvInternetMegas = rootView.findViewById(R.id.paquete_movistar_total_tvInternetMegas);
        tvInternetIncluye = rootView.findViewById(R.id.paquete_movistar_total_tvInternetIncluye);
        tvTVCanales = rootView.findViewById(R.id.paquete_movistar_total_tvTVDescripcion);
        tvTVIncluye = rootView.findViewById(R.id.paquete_movistar_total_tvTVIncluye);
        tvFijaDescripcion = rootView.findViewById(R.id.paquete_movistar_total_tvFijaDescripcion);
        tvMovilNLineas = rootView.findViewById(R.id.paquete_movistar_total_tvMovilNLineas);
        tvMovilGigas= rootView.findViewById(R.id.paquete_movistar_total_tvMovilGigas);
        ivApps = rootView.findViewById(R.id.paquete_movistar_total_ivApps);
        tvPrecioRegular = rootView.findViewById(R.id.paquete_movistar_total_tvPrecioRegular);
        tvPrecioEmbajador = rootView.findViewById(R.id.paquete_movistar_total_tvPrecioEmbajador);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvInternetMegas.setText(planMovistarTotal.getInternetMegas());
        tvInternetIncluye.setText(planMovistarTotal.getInternetIncluye());
        tvTVCanales.setText(planMovistarTotal.getTvCanales());
        tvTVIncluye.setText(planMovistarTotal.getTvIncluye());
        tvFijaDescripcion.setText(planMovistarTotal.getFijaDescripcion());
        tvMovilNLineas.setText(planMovistarTotal.getMovilNLineas());
        tvMovilGigas.setText(planMovistarTotal.getMovilGigas());
        ivApps.setImageResource(planMovistarTotal.getApps_ilimitadas());
        tvPrecioRegular.setText(planMovistarTotal.getPrecioRegular());
        tvPrecioEmbajador.setText(planMovistarTotal.getPrecioEmbajador());
    }
}
