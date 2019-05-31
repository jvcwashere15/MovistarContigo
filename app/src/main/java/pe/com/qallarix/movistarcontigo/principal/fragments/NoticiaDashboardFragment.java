package pe.com.qallarix.movistarcontigo.principal.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.noticias.DetalleNoticiaActivity;
import pe.com.qallarix.movistarcontigo.noticias.News;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiaDashboardFragment extends Fragment {

    private TextView tvTitulo;
    private ImageView ivImagen;
    private News noticia;
    private Button btVermas;
    private static final String ARGUMENT_NOTICIA = "noticia";

    public NoticiaDashboardFragment() {
        // Required empty public constructor
    }

    public static NoticiaDashboardFragment newInstance(News noticia) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_NOTICIA,noticia);
        NoticiaDashboardFragment fragment = new NoticiaDashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        noticia = (News) getArguments().getSerializable(ARGUMENT_NOTICIA);
        View rootView = inflater.inflate(R.layout.fragment_noticia_dashboard, container, false);
        tvTitulo = rootView.findViewById(R.id.noticia_dash_tvTitulo);
        ivImagen = rootView.findViewById(R.id.noticia_dash_ivImagen);
        btVermas = rootView.findViewById(R.id.noticia_dash_btVerMas);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitulo.setText(noticia.getTitle());
        Picasso.with(getActivity()).load(noticia.getImageUrl()).placeholder(R.drawable.placeholder_imagen).into(ivImagen);
        btVermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DetalleNoticiaActivity.class);
//                intent.putExtra("titulo",noticia.getTitle());
//                intent.putExtra("descripcion",noticia.getDescription());
//                intent.putExtra("urlImagen",noticia.getImageUrl());
//                intent.putExtra("fecha",noticia.getDate());
                intent.putExtra("id",String.valueOf(noticia.getId()));
                Analitycs.logEventoClickDashboardVerMasNoticia(getActivity());
                startActivity(intent);
            }
        });
    }
}
