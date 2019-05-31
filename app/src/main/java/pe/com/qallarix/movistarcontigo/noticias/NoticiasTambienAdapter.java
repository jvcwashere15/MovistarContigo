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

public class NoticiasTambienAdapter extends RecyclerView.Adapter<NoticiasTambienAdapter.NoticiaComunHolder> {
    private List<News> news;
    private Context context;
    private NotiTambienClick notiTambienClick;

    public NoticiasTambienAdapter(List<News> news, Context context, NotiTambienClick notiTambienClick) {
        this.news = news;
        this.context = context;
        this.notiTambienClick = notiTambienClick;
    }

    public interface NotiTambienClick{
        public void clickNoticiaTambien(int position);
    }


    @NonNull
    @Override
    public NoticiaComunHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_noticia_comun,viewGroup,false);
        return new NoticiaComunHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiaComunHolder viewHolder, final int itemPosition) {
        News currentNews = news.get(itemPosition);
        viewHolder.setTitulo(currentNews.getTitle());
        viewHolder.setFecha(currentNews.getDate());
        viewHolder.setImagen(currentNews.getImageUrl(),context);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notiTambienClick.clickNoticiaTambien(itemPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return news.size();
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
