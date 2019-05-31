package pe.com.qallarix.movistarcontigo.noticias;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;

public class NoticiasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<News> news;
    private Context context;
    private OnClickNoticia onClickNoticia;


    public NoticiasAdapter(List<News> news, Context context, OnClickNoticia onClickNoticia) {
        this.news = news;
        this.context = context;
        this.onClickNoticia = onClickNoticia;
    }

    public interface OnClickNoticia{
        void notiClick(int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0) return 1;
        else return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        switch (itemViewType){
            case 0:
                return new NoticiaDestacadaHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_noticia_destacado,viewGroup,false));
            default:
                return new NoticiaComunHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_noticia_comun,viewGroup,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int itemPosition) {
        News currentNews = news.get(itemPosition);
        switch (itemPosition){
            case 0:
                NoticiaDestacadaHolder noticiaDestacadaHolder = (NoticiaDestacadaHolder) viewHolder;
                noticiaDestacadaHolder.setTitulo(currentNews.getTitle());
                noticiaDestacadaHolder.setFecha(currentNews.getDate());
                noticiaDestacadaHolder.setImagen(currentNews.getImageUrl(),context);
                noticiaDestacadaHolder.ivImagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickNoticia.notiClick(itemPosition);
                    }
                });
                break;
            default:
                NoticiaComunHolder noticiaComunHolder = (NoticiaComunHolder) viewHolder;
                noticiaComunHolder.setTitulo(currentNews.getTitle());
                noticiaComunHolder.setFecha(currentNews.getDate());
                noticiaComunHolder.setImagen(currentNews.getImageUrl(),context);
                noticiaComunHolder.cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickNoticia.notiClick(itemPosition);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class NoticiaDestacadaHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        TextView tvFecha;
        ImageView ivImagen;

        public NoticiaDestacadaHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.noticia_dash_tvTitulo);
            tvFecha = itemView.findViewById(R.id.noticia_destacada_tvFecha);
            ivImagen = itemView.findViewById(R.id.noticia_dash_ivImagen);
        }

        public void setTitulo(String titulo){
            tvTitulo.setText(titulo);
        }

        public void setFecha(String fecha){
            tvFecha.setText(fecha);
        }

        public void setImagen(String ruta, Context context){
            Picasso.with(context).load(ruta).placeholder(R.drawable.placeholder_imagen).into(ivImagen);
        }
    }

    public static class NoticiaComunHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        TextView tvFecha;
        ImageView ivImagen;
        CardView cv;
        public NoticiaComunHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.noticia_comun_tvTitulo);
            tvFecha = itemView.findViewById(R.id.noticia_comun_tvFecha);
            ivImagen = itemView.findViewById(R.id.noticia_comun_ivImagen);
            cv = itemView.findViewById(R.id.noticia_comun_cv);
        }

        public void setTitulo(String titulo){
            tvTitulo.setText(titulo);
        }

        public void setFecha(String fecha){
            tvFecha.setText(fecha);
        }

        public void setImagen(String ruta, Context context){
            Picasso.with(context).load(ruta).placeholder(R.drawable.placeholder_imagen).into(ivImagen);
        }

    }
}
