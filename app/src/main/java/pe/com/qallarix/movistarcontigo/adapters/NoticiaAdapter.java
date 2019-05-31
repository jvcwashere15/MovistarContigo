package pe.com.qallarix.movistarcontigo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.pojos.Noticia;
public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.NoticiaHolder>{

    List<Noticia> noticias;
    Context context;

    public NoticiaAdapter(List<Noticia> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
    }

    @Override
    public NoticiaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_noticia_comun,viewGroup,false);
        return new NoticiaHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticiaHolder noticiaHolder, int position) {
        Noticia currentNoticia = noticias.get(position);
        noticiaHolder.bindTitulo(currentNoticia.getTitulo());
        noticiaHolder.bindFecha(currentNoticia.getFecha());
        noticiaHolder.bindImagen(currentNoticia.getFoto(),context);
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }


    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
        notifyDataSetChanged();
    }

    public static class NoticiaHolder extends RecyclerView.ViewHolder{

        TextView tvTitulo;
        TextView tvFecha;
        ImageView ivImagen;


        public NoticiaHolder(View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.noticia_comun_tvTitulo);
            tvFecha = itemView.findViewById(R.id.noticia_comun_tvFecha);
            ivImagen = itemView.findViewById(R.id.noticia_comun_ivImagen);
        }

        public void bindTitulo(String titulo){
            tvTitulo.setText(titulo);
        }

        public void bindFecha(String fecha){
            tvFecha.setText(fecha);
        }

        public void bindImagen(int recurso,Context context){
            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(8)
                    .build();

            Picasso.with(context).load(recurso)
                    .placeholder(R.drawable.placeholder_imagen)
                    .transform(transformation)
                    .fit()
                    .into(ivImagen);
        }



    }
}
