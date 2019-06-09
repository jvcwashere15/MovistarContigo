package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.fragments;


import android.app.Activity;
import android.content.Context;
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
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.adapters.PendientesAdapter;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.Pendiente;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendientesFragment extends Fragment {
    private RecyclerView rvPendientes;
    private List<Pendiente> pendientes;

    public PendientesFragment() { }

    public static PendientesFragment newInstance() {
        Bundle args = new Bundle();
        PendientesFragment fragment = new PendientesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pendientes, container, false);
        rvPendientes = rootView.findViewById(R.id.aprobacion_vacaciones_rvPendientes);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cargarData();
        rvPendientes.setHasFixedSize(true);
        rvPendientes.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPendientes.setAdapter(new PendientesAdapter(pendientes, getActivity(), new PendientesAdapter.OnClickPendiente() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DetalleAprobacionVacacionesActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        }));
    }

    private void cargarData() {
        pendientes = new ArrayList<>();
        pendientes.add(new Pendiente("Denis Ricardo Morales Retamozo","15/02/2019"));
        pendientes.add(new Pendiente("Gustavo Juan Perez Fernandez","10/02/2019"));
        pendientes.add(new Pendiente("Fernando Hugo Supo Cardenas","08/01/2019"));
        pendientes.add(new Pendiente("Lionel Alonso Gonzales Letto","07/01/2019"));
    }
}
