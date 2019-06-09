package pe.com.qallarix.movistarcontigo.vacaciones.estado;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;

public class EstadoVacacionesAdapter extends RecyclerView.Adapter<EstadoVacacionesAdapter.EstadoVacacionesHolder> {

    Context context;
    List<EstadoVacaciones> estadoVacaciones;
    EstadoOnClick estadoOnClick;

    public EstadoVacacionesAdapter(Context context, List<EstadoVacaciones> estadoVacaciones, EstadoOnClick estadoOnClick) {
        this.context = context;
        this.estadoVacaciones = estadoVacaciones;
        this.estadoOnClick = estadoOnClick;
    }

    public interface EstadoOnClick{
        void onClick(View v, int position);
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
        estadoVacacionesHolder.setEstado(currentEstadoVacaciones.getEstado());
        estadoVacacionesHolder.setFecha(currentEstadoVacaciones.getFecha());
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
        TextView tvFecha, tvEstado;
        View vEstadoVacaciones;
        public EstadoVacacionesHolder(@NonNull View itemView) {
            super(itemView);
            vEstadoVacaciones = itemView.findViewById(R.id.item_estado_vacaciones_vEstadoVacaciones);
            tvFecha = itemView.findViewById(R.id.item_estado_vacaciones_tvFecha);
            tvEstado = itemView.findViewById(R.id.item_estado_vacaciones_tvEstado);
        }

        public void setFecha(String fecha){
            tvFecha.setText(fecha);
        }

        public void setEstado(int estado){
            String strEstado = "";
            int colorEstado = 0;
            switch (estado){
                case EstadoVacaciones.ESTADO_PENDIENTES: strEstado = "PENDIENTES";colorEstado = R.drawable.etiqueta_amarilla;break;
                case EstadoVacaciones.ESTADO_APROBADAS: strEstado = "APROBADAS";colorEstado = R.drawable.etiqueta_verde;break;
                case EstadoVacaciones.ESTADO_GOZADAS: strEstado = "GOZADAS";colorEstado = R.drawable.etiqueta_gris;break;
                case EstadoVacaciones.ESTADO_RECHAZADAS: strEstado = "RECHAZADAS";colorEstado = R.drawable.etiqueta_roja;break;
            }
            tvEstado.setText(strEstado);
            tvEstado.setBackgroundResource(colorEstado);
        }
    }
}
