package pe.com.qallarix.movistarcontigo.beneficioespeciales.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.beneficioespeciales.pojos.ItemList;
import pe.com.qallarix.movistarcontigo.salud.PdfActivity;
import pe.com.qallarix.movistarcontigo.util.PermissionUtils;

public class TipoBeneficioAdapter extends RecyclerView.Adapter<TipoBeneficioAdapter.TipoBeneficioHolder> {
    private Context context;
    private List<ItemList> itemLists;


    public TipoBeneficioAdapter(Context context, List<ItemList> itemLists) {
        this.context = context;
        this.itemLists = itemLists;
    }

    @Override
    public void onBindViewHolder(@NonNull final TipoBeneficioHolder tipoBeneficioHolder, final int position) {
        ItemList currentItemList = itemLists.get(position);
        tipoBeneficioHolder.setNombre(currentItemList.getTitle());
        tipoBeneficioHolder.setDescripcion(currentItemList.getDescription());
        tipoBeneficioHolder.cargarRecyclerDescripcion(currentItemList.getBenefits(),context);
        tipoBeneficioHolder.configurarCVBeneficio();
    }

    @NonNull
    @Override
    public TipoBeneficioHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tipo_beneficio_especial,viewGroup,false);
        return new TipoBeneficioHolder(view);
    }


    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public static class TipoBeneficioHolder extends RecyclerView.ViewHolder{
        private CardView cvTipoBeneficio;
        private TextView tvNombre;
        private View vDescripcion;
        private TextView tvDescripcion;
        private ImageView ivFlecha;
        private RecyclerView rvDescripcion;


        public TipoBeneficioHolder(@NonNull View itemView) {
            super(itemView);
            cvTipoBeneficio = itemView.findViewById(R.id.item_tipo_beneficio_especial_cv);
            tvNombre = itemView.findViewById(R.id.item_tipo_beneficio_especial_tvNombre);
            vDescripcion = itemView.findViewById(R.id.item_tipo_beneficio_especial_detalle);
            tvDescripcion = itemView.findViewById(R.id.item_tipo_beneficio_especial_tvDescripcion);
            rvDescripcion = itemView.findViewById(R.id.item_tipo_beneficio_especial_rvDescripcion);
            ivFlecha = itemView.findViewById(R.id.tipo_beneficio_especial_ivFlecha);
        }

        public void setNombre(String nombre){
            tvNombre.setText(nombre);
        }

        public void setDescripcion(String descripcion){
            tvDescripcion.setText(descripcion);
        }

        public void cargarRecyclerDescripcion(List<String> beneficios,Context context){
            rvDescripcion.setHasFixedSize(true);
            rvDescripcion.setLayoutManager(new LinearLayoutManager(context));
            VinetaDescripcionAdapter vinetaDescripcionAdapter = new VinetaDescripcionAdapter(beneficios);
            rvDescripcion.setAdapter(vinetaDescripcionAdapter);
        }

        private void configurarCVBeneficio() {
            cvTipoBeneficio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vDescripcion.getVisibility() == View.VISIBLE){
                        vDescripcion.setVisibility(View.GONE);
                        ivFlecha.setRotation(90);
                        return;
                    }
                    vDescripcion.setVisibility(View.VISIBLE);
                    ivFlecha.setRotation(270);
                }
            });
        }
    }
}
