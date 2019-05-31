package pe.com.qallarix.movistarcontigo.openlearning;

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
import pe.com.qallarix.movistarcontigo.openlearning.pojos.Category;


public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriaHolder> {
    private List<Category> categories;
    private Context context;
    private OnClickCategoria onClickCategoria;

    public CategoriasAdapter(List<Category> categories, Context context, OnClickCategoria onClickCategoria) {
        this.categories = categories;
        this.context = context;
        this.onClickCategoria = onClickCategoria;
    }

    public interface OnClickCategoria{
        void onClick(View view, int position);
    }

    @NonNull
    @Override
    public CategoriaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_categoria,viewGroup,false);
        return new CategoriaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaHolder categoriaHolder, final int position) {
        Category currentCategory = categories.get(position);
        categoriaHolder.setTvNombre(currentCategory.getDescription());
        categoriaHolder.setImagen(currentCategory.getImageUrl(),context);
        categoriaHolder.cvCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCategoria.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public static class CategoriaHolder extends RecyclerView.ViewHolder{
        CardView cvCategoria;
        TextView tvNombre;
        ImageView ivImagen;

        public CategoriaHolder(@NonNull View itemView) {
            super(itemView);
            cvCategoria = itemView.findViewById(R.id.categoria_cvCategoria);
            tvNombre = itemView.findViewById(R.id.categoria_tvNombre);
            ivImagen = itemView.findViewById(R.id.categoria_ivImagen);
        }

        public void setTvNombre(String nombre){
            tvNombre.setText(nombre);
        }

        public void setImagen(String url, Context context){
            Picasso.with(context).load(url).placeholder(R.drawable.ic_image).into(ivImagen);
        }
    }
}
