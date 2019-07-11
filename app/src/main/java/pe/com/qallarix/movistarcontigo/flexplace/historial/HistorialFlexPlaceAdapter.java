package pe.com.qallarix.movistarcontigo.flexplace.historial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.historial.pojos.FlexPlace;

public class HistorialFlexPlaceAdapter extends RecyclerView.Adapter<HistorialFlexPlaceAdapter.HistorialFlexPlaceholder> {

    Context context;
    List<FlexPlace> historialFlexPlace;
    EstadoOnClick estadoOnClick;

    public HistorialFlexPlaceAdapter(Context context, EstadoOnClick estadoOnClick) {
        this.context = context;
        this.historialFlexPlace = new ArrayList<>();
        this.estadoOnClick = estadoOnClick;
    }

    public interface EstadoOnClick{
        void onClick(View v, int position);
    }

    public void setHistorialFlexPlace(List<FlexPlace> historialFlexPlace) {
        this.historialFlexPlace = historialFlexPlace;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistorialFlexPlaceholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_historial_flexplace,viewGroup,false);
        return new HistorialFlexPlaceholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialFlexPlaceholder historialFlexPlaceholder, final int pos) {
        FlexPlace currentFlexPlace = historialFlexPlace.get(pos);
        historialFlexPlaceholder.setDia(currentFlexPlace.getDayWeek());
        historialFlexPlaceholder.setFecha(currentFlexPlace.getDateStart(),
                currentFlexPlace.getDateEnd());
        historialFlexPlaceholder.vItemHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoOnClick.onClick(v,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historialFlexPlace.size();
    }

    public static class HistorialFlexPlaceholder extends RecyclerView.ViewHolder{
        TextView tvFlexDia, tvFlexRangoFechas;
        ImageView ivEstado;
        View vItemHistorial;
        public HistorialFlexPlaceholder(@NonNull View itemView) {
            super(itemView);
            vItemHistorial = itemView.findViewById(R.id.item_historial_flexplace_view);
            tvFlexDia = itemView.findViewById(R.id.item_historial_flexplace_tvDia);
            tvFlexRangoFechas = itemView.findViewById(R.id.item_historial_flexplace_tvRangoFecha);
        }

        public void setFecha(String fechaInicio,String fechaFin){
            tvFlexRangoFechas.setText("Del " + fechaInicio + " al " + fechaFin);
        }

        public void setDia(String dia){
            tvFlexDia.setText(dia);
        }


    }
}
