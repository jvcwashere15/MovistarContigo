package pe.com.qallarix.movistarcontigo.main.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pe.com.qallarix.movistarcontigo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExternalFragment extends Fragment {

    private CardView cvMovistarHogar;
    private CardView cvMiMovistar;

    private View vDescMovistarHogar;
    private View vDescMiMovistar;

    private ImageView ivFlechaMovistarHogar;
    private ImageView ivFlechaMiMovistar;


    public ExternalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_externas, container, false);
        cvMovistarHogar = rootView.findViewById(R.id.herramientas_cvMovistarHogar);
        cvMiMovistar = rootView.findViewById(R.id.herramientas_cvMiMovistar);

        vDescMovistarHogar = rootView.findViewById(R.id.herramientas_view_description_movistar_hogar);
        vDescMiMovistar = rootView.findViewById(R.id.herramientas_view_description_mi_movistar);

        ivFlechaMovistarHogar = rootView.findViewById(R.id.herramientas_ivFlechaMovistarHogar);
        ivFlechaMiMovistar = rootView.findViewById(R.id.herramientas_ivFlechaMiMovistar);

        configurarBotonIrALink(R.id.herramientas_mi_movistar_btIrHerramienta,rootView,"https://play.google.com/store/apps/details?id=tdp.app.col");
        configurarBotonIrALink(R.id.herramientas_movistar_hogar_btIrHerramienta,rootView,"https://play.google.com/store/apps/details?id=com.movistarapphogar");

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configurarBotonCVHerramienta(cvMovistarHogar,vDescMovistarHogar,ivFlechaMovistarHogar);
        configurarBotonCVHerramienta(cvMiMovistar,vDescMiMovistar,ivFlechaMiMovistar);
    }

    private void configurarBotonCVHerramienta(final CardView cardView, final View view, final ImageView iv){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getVisibility() == View.VISIBLE){
                    view.setVisibility(View.GONE);
                    iv.setRotation(90);
                    return;
                }
                view.setVisibility(View.VISIBLE);
                iv.setRotation(270);
            }
        });
    }

    private void configurarBotonIrALink(final int idResource, View rootView, final String link){
        rootView.findViewById(idResource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

}
