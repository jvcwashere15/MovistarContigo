package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.DetalleAprobacionVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.DetalleSolicitudAprobadaActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.adapters.AprobadasAdapter;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.Aprobada;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;

/**
 * A simple {@link Fragment} subclass.
 */
public class AprobadasFragment extends Fragment {
    private RecyclerView rvAprobadas;
    private List<Aprobada> aprobadas;


    public AprobadasFragment() {}

    public static AprobadasFragment newInstance() {
        Bundle args = new Bundle();
        AprobadasFragment fragment = new AprobadasFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_aprobadas, container, false);
        rvAprobadas = rootView.findViewById(R.id.aprobacion_vacaciones_rvAprobadas);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cargaData();
        rvAprobadas.setHasFixedSize(true);
        rvAprobadas.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAprobadas.setAdapter(new AprobadasAdapter(getActivity(), aprobadas, new AprobadasAdapter.OnClickAprobada() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DetalleSolicitudAprobadaActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        }));
    }

    private void cargaData() {
        aprobadas = new ArrayList<>();
        aprobadas.add(new Aprobada("Diego Armando Pachioni Retamozo","20/05/2019", EstadoVacaciones.ESTADO_APROBADAS));
        aprobadas.add(new Aprobada("Maria Elena Retamozo Arrieta","10/05/2019", EstadoVacaciones.ESTADO_APROBADAS));
        aprobadas.add(new Aprobada("Carlos Franco Vilchez Roque","11/02/2019", EstadoVacaciones.ESTADO_GOZADAS));
        aprobadas.add(new Aprobada("Jorge Juan Benavides Gomez","20/01/2019", EstadoVacaciones.ESTADO_GOZADAS));
    }
}
