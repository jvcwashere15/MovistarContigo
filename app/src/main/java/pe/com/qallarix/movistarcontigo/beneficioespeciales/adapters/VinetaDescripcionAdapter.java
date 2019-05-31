package pe.com.qallarix.movistarcontigo.beneficioespeciales.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;

public class VinetaDescripcionAdapter extends RecyclerView.Adapter<VinetaDescripcionAdapter.VinetaHolder> {
    List<String> vinetas;

    public VinetaDescripcionAdapter(List<String> vinetas) {
        this.vinetas = vinetas;
    }

    @NonNull
    @Override
    public VinetaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vineta_beneficio_especial,viewGroup,false);
        return new VinetaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VinetaHolder vinetaHolder, int i) {
        vinetaHolder.tvTexto.setText(vinetas.get(i));
    }

    @Override
    public int getItemCount() {
        return vinetas.size();
    }

    public static class VinetaHolder extends RecyclerView.ViewHolder{
        TextView tvTexto;
        public VinetaHolder(@NonNull View itemView) {
            super(itemView);
            tvTexto = itemView.findViewById(R.id.vineta_texto);
        }
    }
}
