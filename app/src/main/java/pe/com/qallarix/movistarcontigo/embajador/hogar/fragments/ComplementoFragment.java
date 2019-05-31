package pe.com.qallarix.movistarcontigo.embajador.hogar.fragments;


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

import com.squareup.picasso.Picasso;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.ComplementList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComplementoFragment extends Fragment {

    private ComplementList complementList;
    private ImageView ivImagen;
    private TextView tvNombre;
    private TextView tvPrecio;
    private TextView tvDescripcion;
    private static final String ARGUMENT_COMPLEMENT_LIST = "complement_list";


    public ComplementoFragment() {
        // Required empty public constructor
    }

    public static ComplementoFragment newInstance(ComplementList complementList) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_COMPLEMENT_LIST,complementList);
        ComplementoFragment fragment = new ComplementoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        complementList = (ComplementList) getArguments().getSerializable(ARGUMENT_COMPLEMENT_LIST);
        View rootView = inflater.inflate(R.layout.fragment_complemento, container, false);
        ivImagen = rootView.findViewById(R.id.complemento_ivImagen);
        tvNombre = rootView.findViewById(R.id.complemento_tvNombre);
        tvPrecio = rootView.findViewById(R.id.complemento_tvPrecio);
        tvDescripcion = rootView.findViewById(R.id.complemento_tvDescripcion);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getActivity()).load(complementList.getComplementImageUrl()).placeholder(R.drawable.placeholder_imagen).into(ivImagen);
        tvNombre.setText(complementList.getComplementName());
        tvPrecio.setText(complementList.getComplementPriceValue());
        if (TextUtils.isEmpty(complementList.getComplementPriceDescription())) tvDescripcion.setText(complementList.getComplementAdditionalDetail());
        else tvDescripcion.setText(complementList.getComplementPriceDescription());
    }
}
