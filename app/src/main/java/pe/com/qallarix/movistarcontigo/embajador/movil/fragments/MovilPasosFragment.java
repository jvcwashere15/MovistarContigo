package pe.com.qallarix.movistarcontigo.embajador.movil.fragments;


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
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.SelfhelpList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovilPasosFragment extends Fragment {

    private SelfhelpList selfhelpList;
    private int numero;

    private TextView tvNumero;
    private TextView tvDescripcion;

    private static final String ARGUMENT_SELFHELPLIST = "selfhelpList";
    private static final String ARGUMENT_NUMERO = "numero";

    public MovilPasosFragment() {
        // Required empty public constructor
    }

    public static MovilPasosFragment newInstance(SelfhelpList selfhelpList, int numero) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_SELFHELPLIST,selfhelpList);
        args.putInt(ARGUMENT_NUMERO,numero);
        MovilPasosFragment fragment = new MovilPasosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selfhelpList = (SelfhelpList) getArguments().getSerializable(ARGUMENT_SELFHELPLIST);
        numero = getArguments().getInt(ARGUMENT_NUMERO);
        View rootView = inflater.inflate(R.layout.fragment_embajador_movil_pasos, container, false);
        tvNumero = rootView.findViewById(R.id.embajador_movil_quehacer_tvNumero);
        tvDescripcion = rootView.findViewById(R.id.embajador_movil_quehacer_tvDescripcion);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNumero.setText(numero + ".");
        tvDescripcion.setText(selfhelpList.getSelfHelpDescription());
    }
}
