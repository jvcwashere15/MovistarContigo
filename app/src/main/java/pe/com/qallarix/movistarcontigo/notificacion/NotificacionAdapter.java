package pe.com.qallarix.movistarcontigo.notificacion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotificacionHolder> {
    private Context context;
    private List<Notification> notifications;
    private NotificacionClick notificacionClick;

    public interface NotificacionClick{
        void onClick(View v,int position);
    }

    public NotificacionAdapter(Context context, List<Notification> notifications, NotificacionClick notificacionClick) {
        this.context = context;
        this.notifications = notifications;
        this.notificacionClick = notificacionClick;
    }

    @NonNull
    @Override
    public NotificacionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notificacion,viewGroup,false);
        return new NotificacionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionHolder notificacionHolder, final int position) {
        Notification currentNotificacion = notifications.get(position);
        notificacionHolder.tvTitulo.setText(currentNotificacion.getTitle());
        notificacionHolder.tvTimeline.setText(currentNotificacion.getDescription());
        notificacionHolder.tvTipo.setText(currentNotificacion.getModuleTitle().toUpperCase());
        notificacionHolder.vNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificacionClick.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificacionHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvTimeline, tvTipo;
        View vNotificacion;

        public NotificacionHolder(@NonNull View itemView) {
            super(itemView);
            vNotificacion = itemView.findViewById(R.id.item_notificacion_viewPrincipal);
            tvTitulo = itemView.findViewById(R.id.item_notificacion_tvTitulo);
            tvTimeline = itemView.findViewById(R.id.item_notificacion_tvTimeline);
            tvTipo = itemView.findViewById(R.id.item_notificacion_tvTipo);
        }
    }
}
