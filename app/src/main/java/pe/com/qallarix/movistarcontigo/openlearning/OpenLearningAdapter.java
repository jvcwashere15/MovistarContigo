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

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.openlearning.pojos.OpenLearningItem;

public class OpenLearningAdapter extends RecyclerView.Adapter<OpenLearningAdapter.TipoBeneficioHolder> {
    private Context context;
    private List<OpenLearningItem> openLearningItems;


    public OpenLearningAdapter(Context context, List<OpenLearningItem> openLearningItems) {
        this.context = context;
        this.openLearningItems = openLearningItems;
    }

    @Override
    public void onBindViewHolder(@NonNull final TipoBeneficioHolder tipoBeneficioHolder, final int position) {
        OpenLearningItem currentOpenLearningItem = openLearningItems.get(position);
        tipoBeneficioHolder.setNombre(currentOpenLearningItem.getTitulo());
        tipoBeneficioHolder.setDescripcion(currentOpenLearningItem.getDescripcion());
        tipoBeneficioHolder.configurarCVBeneficio();
    }

    @NonNull
    @Override
    public TipoBeneficioHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_open_learning,viewGroup,false);
        return new TipoBeneficioHolder(view);
    }


    @Override
    public int getItemCount() {
        return openLearningItems.size();
    }

    public static class TipoBeneficioHolder extends RecyclerView.ViewHolder{
        private CardView cvTipoBeneficio;
        private TextView tvNombre;
        private TextView tvDescripcion;
        private ImageView ivOpenLearning;



        public TipoBeneficioHolder(@NonNull View itemView) {
            super(itemView);
            cvTipoBeneficio = itemView.findViewById(R.id.item_open_learning_cv);
            tvNombre = itemView.findViewById(R.id.item_open_learning_tvNombre);
            tvDescripcion = itemView.findViewById(R.id.item_open_learning_tvDescripcion);
            ivOpenLearning = itemView.findViewById(R.id.open_learning_ivFlecha);
        }

        public void setNombre(String nombre){
            tvNombre.setText(nombre);
        }

        public void setDescripcion(String descripcion){
            tvDescripcion.setText(descripcion);
        }

        private void configurarCVBeneficio() {
            cvTipoBeneficio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvDescripcion.getVisibility() == View.VISIBLE){
                        tvDescripcion.setVisibility(View.GONE);
                        ivOpenLearning.setRotation(90);
                        return;
                    }
                    tvDescripcion.setVisibility(View.VISIBLE);
                    ivOpenLearning.setRotation(270);
                }
            });
        }
    }
}
