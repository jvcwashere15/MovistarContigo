package pe.com.qallarix.movistarcontigo.salud;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.salud.pojos.Plan;


public class SaludAdapter extends RecyclerView.Adapter<SaludAdapter.ItemSaludHolder> {
    private Context context;
    private List<Plan> planes;
    private SaludClick saludClick;

    public interface SaludClick{
        void onClick(View v, int position);
    }

    public SaludAdapter(Context context, List<Plan> planes, SaludClick saludClick) {
        this.context = context;
        this.planes = planes;
        this.saludClick = saludClick;
    }

    @NonNull
    @Override
    public ItemSaludHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu_salud,viewGroup,false);
        return new ItemSaludHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSaludHolder itemSaludHolder, final int position) {
        Plan currentPlan = planes.get(position);
        itemSaludHolder.tvNombre.setText(currentPlan.getTitle());
        Picasso.with(context).load(currentPlan.getImage()).into(itemSaludHolder.ivIcono);
        itemSaludHolder.cvItemSalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saludClick.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planes.size();
    }


    public static class ItemSaludHolder extends RecyclerView.ViewHolder{
        CardView cvItemSalud;
        TextView tvNombre;
        ImageView ivIcono;

        public ItemSaludHolder(@NonNull View itemView) {
            super(itemView);
            cvItemSalud = itemView.findViewById(R.id.item_menu_salud_cv);
            tvNombre = itemView.findViewById(R.id.item_menu_salud_tvNombre);
            ivIcono = itemView.findViewById(R.id.item_menu_salud_ivImagen);
        }


    }
}
