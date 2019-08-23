package pe.com.qallarix.movistarcontigo.flexplace.myteam;

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
import pe.com.qallarix.movistarcontigo.flexplace.myteam.pojos.FlexEquipo;

public class TeamFlexPlaceAdapter extends RecyclerView.Adapter<TeamFlexPlaceAdapter.FlexPlaceEquipoHolder> {
    Context context;
    List<FlexEquipo> solicitudFlexes;
    FlexEquipoOnClick flexEquipoOnClick;

    public TeamFlexPlaceAdapter(Context context, FlexEquipoOnClick flexEquipoOnClick) {
        this.context = context;
        this.solicitudFlexes = new ArrayList<>();
        this.flexEquipoOnClick = flexEquipoOnClick;
    }

    public interface FlexEquipoOnClick {
        void onClick(View v, int position);
    }

    public void setFlexPlaceMiEquipo(List<FlexEquipo> solicitudFlexes) {
        this.solicitudFlexes = solicitudFlexes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlexPlaceEquipoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_solicitud_flexplace,viewGroup,false);
        return new FlexPlaceEquipoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlexPlaceEquipoHolder flexPlaceEquipoHolder,
                                 final int pos) {
        FlexEquipo currentFlexEquipo = solicitudFlexes.get(pos);
        flexPlaceEquipoHolder.setNombre(currentFlexEquipo.getEmployee());
        flexPlaceEquipoHolder.setDescripcion("Del " + currentFlexEquipo.getDateStart() +
                " al " + currentFlexEquipo.getDateEnd());
        flexPlaceEquipoHolder.vItemSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flexEquipoOnClick.onClick(v,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return solicitudFlexes.size();
    }

    public static class FlexPlaceEquipoHolder extends RecyclerView.ViewHolder{
        TextView tvFlexNombre, tvFlexDescripcion;
        View vItemSolicitud;
        public FlexPlaceEquipoHolder(@NonNull View itemView) {
            super(itemView);
            vItemSolicitud = itemView.findViewById(R.id.item_solicitud_flexplace_view);
            tvFlexNombre = itemView.findViewById(R.id.item_solicitud_flexplace_tvNombre);
            tvFlexDescripcion = itemView.findViewById(R.id.item_solicitud_flexplace_tvDescripcion);
        }

        public void setNombre(String nombre){
            tvFlexNombre.setText(nombre);
        }

        public void setDescripcion(String mensajeDate){
            tvFlexDescripcion.setText(mensajeDate);
        }


    }
}
