package pe.com.qallarix.movistarcontigo.ambassador.home.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.qallarix.movistarcontigo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmbassadorHomeBillingFragment extends Fragment {


    public AmbassadorHomeBillingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_embajador_hogar_facturacion, container, false);
    }

}
