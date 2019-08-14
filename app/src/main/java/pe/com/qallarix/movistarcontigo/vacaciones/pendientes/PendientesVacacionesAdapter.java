package pe.com.qallarix.movistarcontigo.vacaciones.pendientes;

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
import pe.com.qallarix.movistarcontigo.vacaciones.pendientes.pojos.EstadoVacaciones;

public class PendientesVacacionesAdapter extends RecyclerView.Adapter<PendientesVacacionesAdapter.EstadoVacacionesHolder> {

    Context context;
    List<EstadoVacaciones> estadoVacaciones;
    EstadoOnClick estadoOnClick;

    public PendientesVacacionesAdapter(Context context, EstadoOnClick estadoOnClick) {
        this.context = context;
        this.estadoVacaciones = new ArrayList<>();
        this.estadoOnClick = estadoOnClick;
    }

    public interface EstadoOnClick{
        void onClick(View v, int position);
    }

    public void setEstadoVacaciones(List<EstadoVacaciones> estadoVacaciones) {
        this.estadoVacaciones = estadoVacaciones;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EstadoVacacionesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vacaciones_estado,viewGroup,false);
        return new EstadoVacacionesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstadoVacacionesHolder estadoVacacionesHolder, final int pos) {
        EstadoVacaciones currentEstadoVacaciones = estadoVacaciones.get(pos);
        estadoVacacionesHolder.setFecha(currentEstadoVacaciones.getRequestDateStart(),
                currentEstadoVacaciones.getRequestDateEnd());
        estadoVacacionesHolder.vEstadoVacaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoOnClick.onClick(v,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return estadoVacaciones.size();
    }

    public static class EstadoVacacionesHolder extends RecyclerView.ViewHolder{
        TextView tvFecha;
        View vEstadoVacaciones;
        public EstadoVacacionesHolder(@NonNull View itemView) {
            super(itemView);
            vEstadoVacaciones = itemView.findViewById(R.id.item_estado_vacaciones_vEstadoVacaciones);
            tvFecha = itemView.findViewById(R.id.item_estado_vacaciones_tvFecha);
        }

        public void setFecha(String fechaInicio,String fechaFin){
            tvFecha.setText(fechaInicio + " - " + fechaFin);
        }
    }
}
