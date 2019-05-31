package pe.com.qallarix.movistarcontigo.embajador.total.fragments;


import android.annotation.SuppressLint;
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
import pe.com.qallarix.movistarcontigo.pojos.TipoFacturacion;


/**
 * A simple {@link Fragment} subclass.
 */
public class TipoFacturacionFragment extends Fragment {

    private TipoFacturacion tipoFacturacion;
    private TextView tvDescripcion;
    private ImageView ivImagen;
    private static final String ARGUMENT_TIPO_FACTURACION = "tipoFacturacion";

    public TipoFacturacionFragment() {
        // Required empty public constructor
    }


    public static TipoFacturacionFragment newInstance(TipoFacturacion tipoFacturacion) {

        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_TIPO_FACTURACION,tipoFacturacion);
        TipoFacturacionFragment fragment = new TipoFacturacionFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tipoFacturacion = (TipoFacturacion) getArguments().getSerializable(ARGUMENT_TIPO_FACTURACION);
        View rootView = inflater.inflate(R.layout.fragment_tipo_facturacion, container, false);
        tvDescripcion = rootView.findViewById(R.id.tipo_facturacion_tvDescripcion);
        ivImagen = rootView.findViewById(R.id.tipo_facturacion_ivImagen);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDescripcion.setText(tipoFacturacion.getDescripcion());
        ivImagen.setImageResource(tipoFacturacion.getImagen());
    }
}
