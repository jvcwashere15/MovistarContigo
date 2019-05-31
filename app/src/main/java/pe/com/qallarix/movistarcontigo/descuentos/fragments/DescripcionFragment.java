package pe.com.qallarix.movistarcontigo.descuentos.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescripcionFragment extends Fragment {
    private TextView tvTitulo, tvDescripcion, tvDescuento, tvDetalle, tvZonal;
    private static final String TITULO_KEY = "mTitle";
    private static final String DESCRIPCION_KEY = "mDescription";
    private static final String DESCUENTO_KEY = "mDiscount";
    private static final String DETALLE_KEY = "mDiscountDetail";
    private static final String ZONAL_KEY = "mZonal";

    private String mTitle, mDescription, mDiscount, mDiscountDetail, mZonal;

    public DescripcionFragment() {
        // Required empty public constructor
    }

    public static DescripcionFragment newInstance(String mTitle, String mDescription,
                                                  String mDiscount, String mDiscountDetail,
                                                  String mZonal) {
        Bundle args = new Bundle();
        args.putString(TITULO_KEY,mTitle);
        args.putString(DESCRIPCION_KEY,mDescription);
        args.putString(DESCUENTO_KEY,mDiscount);
        args.putString(DETALLE_KEY,mDiscountDetail);
        args.putString(ZONAL_KEY,mZonal);

        DescripcionFragment fragment = new DescripcionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTitle = getArguments().getString(TITULO_KEY);
        mDescription = getArguments().getString(DESCRIPCION_KEY);
        mDiscount = getArguments().getString(DESCUENTO_KEY);
        mDiscountDetail = getArguments().getString(DETALLE_KEY);
        mZonal = getArguments().getString(ZONAL_KEY);
        View rootView = inflater.inflate(R.layout.fragment_descripcion, container, false);
        tvTitulo = rootView.findViewById(R.id.detalle_movistar_tvTitulo);
        tvDescripcion = rootView.findViewById(R.id.detalle_movistar_tvDescripcion);
        tvDetalle = rootView.findViewById(R.id.detalle_movistar_tvDescuentoDetalle);
        tvDescuento = rootView.findViewById(R.id.detalle_movistar_tvDescuento);
        tvZonal = rootView.findViewById(R.id.detalle_movistar_tvZona);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitulo.setText(mTitle);
        tvDescripcion.setText(mDescription);
        tvDescuento.setText(mDiscount);
        tvDetalle.setText(mDiscountDetail);
        tvZonal.setText(mZonal);
    }
}
