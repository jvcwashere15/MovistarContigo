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
import pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos.EstadoVacaciones;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos.Aprobada;

public class AprobadasAdapter extends RecyclerView.Adapter<AprobadasAdapter.AprobadaHolder> {

    private Context context;
    private List<Aprobada> aprobadas;
    private OnClickAprobada onClickAprobada;

    public AprobadasAdapter(Context context, List<Aprobada> aprobadas, OnClickAprobada onClickAprobada) {
        this.context = context;
        this.aprobadas = aprobadas;
        this.onClickAprobada = onClickAprobada;
    }

    @NonNull
    @Override
    public AprobadaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_vacaciones_aprobadas,viewGroup,false);
        return new AprobadaHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AprobadaHolder aprobadaHolder, final int position) {
        Aprobada currentAprobada = aprobadas.get(position);
        aprobadaHolder.setColaborador(currentAprobada.getColaborador());
        aprobadaHolder.setFecha(currentAprobada.getFecha());
        aprobadaHolder.setEstado(currentAprobada.getEstado());
        aprobadaHolder.vItemAprobada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAprobada.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return aprobadas.size();
    }

    public interface OnClickAprobada{
        void onClick(int position);
    }



    public static class AprobadaHolder extends RecyclerView.ViewHolder{

        View vItemAprobada;
        TextView tvColaborador, tvFecha, tvEstado;

        public AprobadaHolder(@NonNull View itemView) {
            super(itemView);
            vItemAprobada = itemView.findViewById(R.id.item_aprobada_vAprobada);
            tvColaborador = itemView.findViewById(R.id.item_aprobada_tvColaborador);
            tvFecha = itemView.findViewById(R.id.item_aprobada_tvFecha);
            tvEstado = itemView.findViewById(R.id.item_aprobada_tvEstado);

        }

        public void setColaborador(String colaborador){
            tvColaborador.setText(colaborador);
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
