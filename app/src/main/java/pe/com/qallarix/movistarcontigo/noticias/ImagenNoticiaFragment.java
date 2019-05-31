package pe.com.qallarix.movistarcontigo.noticias;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pe.com.qallarix.movistarcontigo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagenNoticiaFragment extends Fragment {
    private String urlImage;
    private ImageView ivImagen;

    private static final String NOTICIA_IMAGEN = "imagen";

    public ImagenNoticiaFragment() {
        // Required empty public constructor
    }

    public static ImagenNoticiaFragment newInstance(String urlImage) {

        Bundle args = new Bundle();
        args.putString(NOTICIA_IMAGEN,urlImage);
        ImagenNoticiaFragment fragment = new ImagenNoticiaFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        urlImage = getArguments().getString(NOTICIA_IMAGEN);
        View rootView = inflater.inflate(R.layout.fragment_imagen_noticia, container, false);
        ivImagen = rootView.findViewById(R.id.detalle_noticia_ivImagen);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Picasso.with(getActivity()).load(urlImage).placeholder(R.drawable.placeholder_imagen).into(ivImagen);
    }
}
