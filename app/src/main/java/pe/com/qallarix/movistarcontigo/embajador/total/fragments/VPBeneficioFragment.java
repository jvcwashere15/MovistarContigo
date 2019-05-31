package pe.com.qallarix.movistarcontigo.embajador.total.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.noticias.News;
import pe.com.qallarix.movistarcontigo.pojos.Beneficio;


/**
 * A simple {@link Fragment} subclass.
 */
public class VPBeneficioFragment extends Fragment {

    private Beneficio beneficio;
    private int color;

    private ImageView ivImagen;
    private TextView tvNombre;
    private TextView tvDescripcion;
    private static final String ARGUMENT_BENEFICIO = "beneficio";
    private static final String ARGUMENT_COLOR = "color";


    public VPBeneficioFragment() {
        // Required empty public constructor
    }

    public static VPBeneficioFragment newInstance(Beneficio beneficio, int color) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_BENEFICIO,beneficio);
        args.putInt(ARGUMENT_COLOR,color);
        VPBeneficioFragment fragment = new VPBeneficioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        beneficio = (Beneficio) getArguments().getSerializable(ARGUMENT_BENEFICIO);
        color = getArguments().getInt(ARGUMENT_COLOR);
        View rootView = inflater.inflate(R.layout.fragment_vpbeneficio, container, false);
        ivImagen = rootView.findViewById(R.id.vpBeneficio_ivImagen);
        tvNombre = rootView.findViewById(R.id.vpBeneficio_tvNombre);
        tvDescripcion = rootView.findViewById(R.id.vpBeneficio_tvDescription);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivImagen.setImageResource(beneficio.getImagen());
        if (TextUtils.isEmpty(beneficio.getNombre()))tvNombre.setVisibility(View.GONE);
        else{
            tvNombre.setText(beneficio.getNombre());
            tvNombre.setTextColor(color);
        }
        tvDescripcion.setText(beneficio.getDescripcion());
        tvDescripcion.setTextColor(color);
    }


}
