package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.SolicitudAprobacion;

public class ForApproveRequestsAdapter extends RecyclerView.Adapter<ForApproveRequestsAdapter.PendienteHolder> {

    private List<SolicitudAprobacion> solicitudes;
    private OnClickPendiente onClickPendiente;
    private Context context;

    public ForApproveRequestsAdapter(Context context, OnClickPendiente onClickPendiente) {
        this.solicitudes = new ArrayList<SolicitudAprobacion>();
        this.onClickPendiente = onClickPendiente;
        this.context = context;
    }

    public interface OnClickPendiente{
        void onClick(int position);
    }

    public void setSolicitudes(List<SolicitudAprobacion> solicitudes) {
        this.solicitudes = solicitudes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PendienteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vacaciones_pendientes,viewGroup,false);
        return new PendienteHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendienteHolder pendienteHolder, final int position) {
        SolicitudAprobacion currentPendiente = solicitudes.get(position);
        pendienteHolder.setColaborador(currentPendiente.getEmployeeName());
        pendienteHolder.setFecha("Solicitó sus vacaciones el " + currentPendiente.getRequestDay());
        pendienteHolder.vItemPendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPendiente.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return solicitudes.size();
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
