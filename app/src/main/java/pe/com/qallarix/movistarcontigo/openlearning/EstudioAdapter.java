package pe.com.qallarix.movistarcontigo.openlearning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.openlearning.pojos.Benefit;

public class EstudioAdapter extends RecyclerView.Adapter<EstudioAdapter.EstudioHolder> {

    List<Benefit> estudios;
    Context context;
    ClickEstudio clickEstudio;


    public interface ClickEstudio{
        public void onClick(View view, int position);
    }

    public EstudioAdapter(List<Benefit> estudios, Context context, ClickEstudio clickEstudio) {
        this.estudios = estudios;
        this.context = context;
        this.clickEstudio = clickEstudio;
    }

    @NonNull
    @Override
    public EstudioHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_estudio,viewGroup,false);
        return new EstudioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstudioHolder estudioHolder, final int position) {
        Benefit currentEstudio = estudios.get(position);
        estudioHolder.bindTitulo(currentEstudio.getInstitution());
        estudioHolder.bindCantidad(currentEstudio.getDiscount());
        estudioHolder.bindDescripcion1(currentEstudio.getTitle());
        estudioHolder.bindDescripcion2(currentEstudio.getDiscountDetail());
        estudioHolder.bindImagen(currentEstudio.getDiscountImage(),context);
        estudioHolder.flItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEstudio.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return estudios.size();
    }


    public String cortarCadena(String cadena, int tamanio){
        if (cadena.length() > tamanio){
            return cadena.substring(0,tamanio-1);
        }
        return cadena;
    }

    static class EstudioHolder extends RecyclerView.ViewHolder{
        ImageView ivImagen;
        TextView tvTitulo;
        TextView tvCantidad;
        TextView tvDescripcion1;
        TextView tvDescripcion2;
        FrameLayout flItemView;

        public EstudioHolder(View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.item_descuento_ivImagen);
            tvTitulo = itemView.findViewById(R.id.item_descuento_tvTitulo);
            tvCantidad = itemView.findViewById(R.id.item_descuento_tvCantidad);
            tvDescripcion1 = itemView.findViewById(R.id.item_descuento_tvDescripcion_1);
            tvDescripcion2 = itemView.findViewById(R.id.item_descuento_tvDescripcion_2);
            flItemView = itemView.findViewById(R.id.flItemView);
        }

        public void bindImagen(String urlImagen, Context context){
            Picasso.with(context).load(urlImagen).placeholder(R.drawable.placeholder_imagen).into(ivImagen);
        }

        public void bindTitulo(String titulo){
            tvTitulo.setText(titulo);
        }

        public void bindCantidad(String cantidad){
            tvCantidad.setText(cantidad);
        }

        public void bindDescripcion1(String descripcion){
            tvDescripcion1.setText(descripcion);
        }

        public void bindDescripcion2(String descripcion){
            tvDescripcion2.setText(descripcion);
        }
    }
}
