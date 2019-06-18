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

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.pojos.Combo;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComboFragment extends Fragment {

    private Combo combo;
    private TextView tvDescripcion;
    private TextView tvMegas;
    private TextView tvPrecioEmbajador;
    private TextView tvPrecioRegular;
    private static final String ARGUMENT_COMBO = "combo";

    public ComboFragment() {
        // Required empty public constructor
    }

    public static ComboFragment newInstance(Combo combo) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_COMBO,combo);
        ComboFragment fragment = new ComboFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        combo = (Combo) getArguments().getSerializable(ARGUMENT_COMBO);
        View rootView = inflater.inflate(R.layout.fragment_combo, container, false);
        tvDescripcion = rootView.findViewById(R.id.combo_tvDescripcion);
        tvMegas = rootView.findViewById(R.id.combo_tvMegas);
        tvPrecioEmbajador = rootView.findViewById(R.id.combo_tvPrecioEmbajador);
        tvPrecioRegular = rootView.findViewById(R.id.combo_tvPrecioRegular);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDescripcion.setText(combo.getDescripcion());
        tvDescripcion.setBackgroundResource(combo.getHeaderColor());
        tvMegas.setText(combo.getMegas());
        tvPrecioEmbajador.setText(combo.getPrecio_embajador());
        tvPrecioEmbajador.setTextColor(getResources().getColor(combo.getColor()));
        tvPrecioRegular.setText(combo.getPrecio());
    }
}
