package pe.com.qallarix.movistarcontigo.flexplace.approve;

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
import pe.com.qallarix.movistarcontigo.flexplace.approve.pojos.SolicitudFlex;

public class SolicitudesFlexPlaceAdapter extends RecyclerView.Adapter<SolicitudesFlexPlaceAdapter.SolicitudFlexPlaceHolder> {
    Context context;
    List<SolicitudFlex> solicitudFlexes;
    SolicitudOnClick solicitudOnClick;

    public SolicitudesFlexPlaceAdapter(Context context, SolicitudOnClick solicitudOnClick) {
        this.context = context;
        this.solicitudFlexes = new ArrayList<>();
        this.solicitudOnClick = solicitudOnClick;
    }

    public interface SolicitudOnClick{
        void onClick(View v, int position);
    }

    public void setHistorialFlexPlace(List<SolicitudFlex> solicitudFlexes) {
        this.solicitudFlexes = solicitudFlexes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SolicitudFlexPlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_solicitud_flexplace,viewGroup,false);
        return new SolicitudFlexPlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudFlexPlaceHolder solicitudFlexPlaceHolder,
                                 final int pos) {
        SolicitudFlex currentSolicitudFlex = solicitudFlexes.get(pos);
        solicitudFlexPlaceHolder.setNombre(currentSolicitudFlex.getEmployee());
        solicitudFlexPlaceHolder.setDescripcion(currentSolicitudFlex.getDateMessage());
        solicitudFlexPlaceHolder.vItemSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitudOnClick.onClick(v,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return solicitudFlexes.size();
    }

    public static class SolicitudFlexPlaceHolder extends RecyclerView.ViewHolder{
        TextView tvFlexNombre, tvFlexDescripcion;
        View vItemSolicitud;
        public SolicitudFlexPlaceHolder(@NonNull View itemView) {
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
