package pe.com.qallarix.movistarcontigo.embajador.hogar.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.embajador.FragmentParentEmbajador;
import pe.com.qallarix.movistarcontigo.embajador.hogar.adapters.HogarPaqueteAdapter;
import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.CharacteristicGroupList;
import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.OfferList;
import pe.com.qallarix.movistarcontigo.pojos.PaqueteHogar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmbajadorHogarPaquetesFragment extends FragmentParentEmbajador {


    private List<OfferList> offerLists;
    private List<PaqueteHogar> paquetesHogares;
    private ImageView ivAtras,ivAdelante;

    private ViewPager viewPager;
    private static final String ARGUMENT_OFFERLISTS = "offerlists";

    public EmbajadorHogarPaquetesFragment() {
        // Required empty public constructor
    }


    public static EmbajadorHogarPaquetesFragment newInstance(List<OfferList> offerLists) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_OFFERLISTS, (Serializable) offerLists);
        EmbajadorHogarPaquetesFragment fragment = new EmbajadorHogarPaquetesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        offerLists = (List<OfferList>) getArguments().getSerializable(ARGUMENT_OFFERLISTS);
        View rootView = inflater.inflate(R.layout.fragment_embajador_hogar_paquetes, container, false);
        ivAtras = rootView.findViewById(R.id.hogar_paquetes_ivAtras);
        ivAdelante = rootView.findViewById(R.id.hogar_paquetes_ivAdelante);

        viewPager = rootView.findViewById(R.id.hogar_vpPaquetes);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargaData();
        configurarFlechasViewPager(viewPager,ivAtras,ivAdelante,paquetesHogares.size()-1);
        HogarPaqueteAdapter hogarPaqueteAdapter = new HogarPaqueteAdapter(getChildFragmentManager(), paquetesHogares);
        viewPager.setAdapter(hogarPaqueteAdapter);
    }

    private void cargaData() {
        paquetesHogares = new ArrayList<>();
        for (OfferList offerList : offerLists) {
            PaqueteHogar paqueteHogar = new PaqueteHogar();
            List<CharacteristicGroupList> characteristicGroupLists = offerList.getCharacteristicGroupList();
            for (CharacteristicGroupList characteristicGroupList : characteristicGroupLists) {
                int orderCharacteristic = (int) characteristicGroupList.getOrder();
                switch (orderCharacteristic) {
                    case 1:
                        paqueteHogar.setMegas1(characteristicGroupList.getCharacteristicList().get(0).getCharacteristicValue());
                        paqueteHogar.setMegas2(characteristicGroupList.getCharacteristicList().get(1).getCharacteristicValue());
                        break;
                    case 2:
                        paqueteHogar.setTrio(characteristicGroupList.getCharacteristicList().get(0).getCharacteristicValue());
                        break;
                    case 3:
                        paqueteHogar.setHd(characteristicGroupList.getCharacteristicList().get(0).getCharacteristicValue());
                        break;
                    case 4:
                        paqueteHogar.setModem(characteristicGroupList.getCharacteristicList().get(0).getCharacteristicValue());
                        break;
                    case 5:
                        paqueteHogar.setPrecio1(characteristicGroupList.getCharacteristicList().get(0).getCharacteristicValue());
                        paqueteHogar.setPrecio2(characteristicGroupList.getCharacteristicList().get(2).getCharacteristicValue());
                        paqueteHogar.setPrecio3(characteristicGroupList.getCharacteristicList().get(1).getCharacteristicValue());
                        break;
                }
            }
            paquetesHogares.add(paqueteHogar);
        }

    }

}
