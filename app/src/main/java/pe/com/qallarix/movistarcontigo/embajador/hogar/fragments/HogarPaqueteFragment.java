package pe.com.qallarix.movistarcontigo.embajador.hogar.fragments;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.pojos.PaqueteHogar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HogarPaqueteFragment extends Fragment {

    private PaqueteHogar paqueteHogar;
    private TextView tvMegas1;
    private TextView tvMegas2;
    private TextView tvTrio;
    private TextView tvHd;
    private TextView tvModem;
    private TextView tvPrecio1;
    private TextView tvPrecio2;
    private TextView tvPrecio3;
    private static final String ARGUMENT_PAQUETE_HOGAR = "paqueteHogar";

    public HogarPaqueteFragment() {
        // Required empty public constructor
    }

    public static HogarPaqueteFragment newInstance(PaqueteHogar paqueteHogar) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_PAQUETE_HOGAR, (Serializable) paqueteHogar);
        HogarPaqueteFragment fragment = new HogarPaqueteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paqueteHogar = (PaqueteHogar) getArguments().getSerializable(ARGUMENT_PAQUETE_HOGAR);
        View rootView = inflater.inflate(R.layout.fragment_embajador_hogar_paquete, container, false);
        tvMegas1 = rootView.findViewById(R.id.paquete_hogar_tvMegas1);
        tvMegas2 = rootView.findViewById(R.id.paquete_hogar_tvMegas2);
        tvTrio = rootView.findViewById(R.id.paquete_hogar_tvTrio);
        tvHd = rootView.findViewById(R.id.paquete_hogar_tvHd);
        tvModem = rootView.findViewById(R.id.paquete_hogar_tvModem);
        tvPrecio1 = rootView.findViewById(R.id.paquete_hogar_tvPrecio1);
        tvPrecio2 = rootView.findViewById(R.id.paquete_hogar_tvPrecio2);
        tvPrecio3 = rootView.findViewById(R.id.paquete_hogar_tvPrecio3);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMegas1.setText(paqueteHogar.getMegas1());
        tvMegas2.setText(paqueteHogar.getMegas2());
        tvTrio.setText(paqueteHogar.getTrio());
        tvHd.setText(paqueteHogar.getHd());
        tvModem.setText(paqueteHogar.getModem());

        tvPrecio2.setText(paqueteHogar.getPrecio2());
        tvPrecio3.setText(paqueteHogar.getPrecio3());
    }
}
