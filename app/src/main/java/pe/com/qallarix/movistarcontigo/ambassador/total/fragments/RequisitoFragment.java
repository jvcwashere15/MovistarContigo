package pe.com.qallarix.movistarcontigo.ambassador.total.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.pojos.Requisito;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequisitoFragment extends Fragment {

    private TextView tvDescripcion;
    private TextView tvNumero;
    private Requisito requisito;
    private static final String ARGUMENT_REQUISITO = "requisito";

    public RequisitoFragment() {
        // Required empty public constructor
    }

    public static RequisitoFragment newInstance(Requisito requisito) {

        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_REQUISITO,requisito);
        RequisitoFragment fragment = new RequisitoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requisito = (Requisito) getArguments().getSerializable(ARGUMENT_REQUISITO);
        View rootView = inflater.inflate(R.layout.fragment_requisito, container, false);
        tvNumero = rootView.findViewById(R.id.requisito_tvNumero);
        tvDescripcion = rootView.findViewById(R.id.requisito_tvDescripcion);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNumero.setText(requisito.getNumero());
        tvDescripcion.setText(requisito.getDescripcion());
    }
}
