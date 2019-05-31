package pe.com.qallarix.movistarcontigo.embajador.movil.fragments;


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

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.CharacteristicGroupList;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.CharacteristicList;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.OfferList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovilPaqueteFragment extends Fragment {

    private OfferList paqueteMovil;

    private TextView tvInternet1;
    private TextView tvInternet2;
    private TextView tvInternet3;

    private TextView tvAplicaciones1;
    private ImageView ivAplicaciones2;
    private TextView tvAplicaciones3;

    private TextView tvVozSms1;
    private TextView tvVozSms2;

    private TextView tvMegasRoaming1;
    private TextView tvMegasRoaming2;

    private TextView tvPrecios1;
    private TextView tvPrecios2;
    private TextView tvPrecios3;

    private static final String ARGUMENT_PAQUETE_MOVIL = "paqueteMovil";

    public MovilPaqueteFragment() {
        // Required empty public constructor
    }

    public static MovilPaqueteFragment newInstance(OfferList paqueteMovil) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_PAQUETE_MOVIL,(Serializable) paqueteMovil);
        MovilPaqueteFragment fragment = new MovilPaqueteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paqueteMovil = (OfferList) getArguments().getSerializable(ARGUMENT_PAQUETE_MOVIL);
        View rootView = inflater.inflate(R.layout.fragment_movil_paquete, container, false);

        tvInternet1 = rootView.findViewById(R.id.paquete_movil_tvInternet1);
        tvInternet2 = rootView.findViewById(R.id.paquete_movil_tvInternet2);
        tvInternet3 = rootView.findViewById(R.id.paquete_movil_tvInternet3);

        tvAplicaciones1 = rootView.findViewById(R.id.paquete_movil_tvAplicaciones1);
        ivAplicaciones2 = rootView.findViewById(R.id.paquete_movil_ivAplicaciones2);
        tvAplicaciones3 = rootView.findViewById(R.id.paquete_movil_tvAplicaciones3);

        tvVozSms1 = rootView.findViewById(R.id.paquete_movil_tvVozSMS1);
        tvVozSms2 = rootView.findViewById(R.id.paquete_movil_tvVozSMS2);

        tvMegasRoaming1= rootView.findViewById(R.id.paquete_movil_tvMegasRoaming1);
        tvMegasRoaming2= rootView.findViewById(R.id.paquete_movil_tvMegasRoaming2);

        tvPrecios1 = rootView.findViewById(R.id.paquete_movil_tvPrecio1);
        tvPrecios2 = rootView.findViewById(R.id.paquete_movil_tvPrecio2);
        tvPrecios3 = rootView.findViewById(R.id.paquete_movil_tvPrecio3);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (CharacteristicGroupList characteristicGroupList : paqueteMovil.getCharacteristicGroupList()){
            if(characteristicGroupList.getCharacteristicName().equals("Internet")){
                for (CharacteristicList characteristicList : characteristicGroupList.getCharacteristicList()){
                    if (characteristicList.getCharacteristicName().equals("Cantidad")){
                        tvInternet1.setText(characteristicList.getCharacteristicValue());
                    }else if (characteristicList.getCharacteristicName().equals("Tipo")){
                        tvInternet2.setText(characteristicList.getCharacteristicValue());
                    }else if (characteristicList.getCharacteristicName().equals("Compartir")){
                        tvInternet3.setText(characteristicList.getCharacteristicValue());
                    }
                }
            }else if (characteristicGroupList.getCharacteristicName().equals("Aplicaciones")){
                for (CharacteristicList characteristicList : characteristicGroupList.getCharacteristicList()){
                    if (characteristicList.getCharacteristicName().equals("Cantidad")){
                        tvAplicaciones1.setText(characteristicList.getCharacteristicValue());
                    }else if (characteristicList.getCharacteristicName().equals("Imagen")){
                        Picasso.with(getActivity()).load(characteristicList.getCharacteristicValue()).into(ivAplicaciones2);
                    }else if (characteristicList.getCharacteristicName().equals("Descripcion")){
                        tvAplicaciones3.setText(characteristicList.getCharacteristicValue());
                    }
                }
            }else if (characteristicGroupList.getCharacteristicName().equals("Voz y SMS")){
                for (CharacteristicList characteristicList : characteristicGroupList.getCharacteristicList()){
                    if (characteristicList.getCharacteristicName().equals("Nombre")){
                        tvVozSms1.setText(characteristicList.getCharacteristicValue());
                    }else if (characteristicList.getCharacteristicName().equals("Cantidad")){
                        tvVozSms2.setText(characteristicList.getCharacteristicValue());
                    }
                }
            }else if (characteristicGroupList.getCharacteristicName().equals("Megas Roaming")){
                for (CharacteristicList characteristicList : characteristicGroupList.getCharacteristicList()){
                    if (characteristicList.getCharacteristicName().equals("Nombre")){
                        tvMegasRoaming1.setText(characteristicList.getCharacteristicValue());
                    }else if (characteristicList.getCharacteristicName().equals("Cantidad")){
                        tvMegasRoaming2.setText(characteristicList.getCharacteristicValue());
                    }
                }
            }else if (characteristicGroupList.getCharacteristicName().equals("Precios")){
                for (CharacteristicList characteristicList : characteristicGroupList.getCharacteristicList()){
                    if (characteristicList.getCharacteristicName().equals("Nombre")){
                        tvPrecios1.setText(characteristicList.getCharacteristicValue());
                    }else if (characteristicList.getCharacteristicName().equals("Tienda")){
                        tvPrecios2.setText(characteristicList.getCharacteristicValue());
                    }else if (characteristicList.getCharacteristicName().equals("Embajador")){
                        tvPrecios3.setText(characteristicList.getCharacteristicValue());
                    }
                }
            }
        }
    }
}
