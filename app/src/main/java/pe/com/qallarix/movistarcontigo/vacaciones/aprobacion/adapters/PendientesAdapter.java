package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.Pendiente;

public class PendientesAdapter extends RecyclerView.Adapter<PendientesAdapter.PendienteHolder> {

    private List<Pendiente> pendientes;
    private OnClickPendiente onClickPendiente;
    private Context context;

    public PendientesAdapter(List<Pendiente> pendientes, Context context, OnClickPendiente onClickPendiente) {
        this.pendientes = pendientes;
        this.onClickPendiente = onClickPendiente;
        this.context = context;
    }

    public interface OnClickPendiente{
        void onClick(int position);
    }

    @NonNull
    @Override
    public PendienteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vacaciones_pendientes,viewGroup,false);
        return new PendienteHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendienteHolder pendienteHolder, final int position) {
        Pendiente currentPendiente = pendientes.get(position);
        pendienteHolder.setColaborador(currentPendiente.getColaborador());
        pendienteHolder.setFecha(currentPendiente.getFecha());
        pendienteHolder.vItemPendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPendiente.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pendientes.size();
    }

    public static class PendienteHolder extends RecyclerView.ViewHolder{

        View vItemPendiente;
        TextView tvColaborador, tvFecha;

        public PendienteHolder(@NonNull View itemView) {
            super(itemView);
            vItemPendiente = itemView.findViewById(R.id.item_pendiente_vPendiente);
            tvColaborador = itemView.findViewById(R.id.item_pendiente_tvColaborador);
            tvFecha = itemView.findViewById(R.id.item_pendiente_tvFecha);
        }

        public void setColaborador(String colaborador){
            tvColaborador.setText(colaborador);
        }
        public void setFecha(String fecha){
            tvFecha.setText(fecha);
        }

    }
}
