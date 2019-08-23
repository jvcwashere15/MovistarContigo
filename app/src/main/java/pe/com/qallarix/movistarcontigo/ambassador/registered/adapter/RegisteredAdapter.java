package pe.com.qallarix.movistarcontigo.ambassador.registered.adapter;

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
import pe.com.qallarix.movistarcontigo.ambassador.registered.pojos.BreakRegistered;

public class RegisteredAdapter extends RecyclerView.Adapter<RegisteredAdapter.RegisteredHolder> {
    List<BreakRegistered> breaks;
    Context context;

    public RegisteredAdapter(Context context) {
        this.breaks = new ArrayList<>();
        this.context = context;
    }

    public void setBreaks(List<BreakRegistered> breaks) {
        this.breaks = breaks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RegisteredHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_quiebre_registrado,viewGroup,false);
        return new RegisteredHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredHolder registeredHolder, int i) {
        BreakRegistered currentBreakRegistered = breaks.get(i);
        registeredHolder.setName(currentBreakRegistered.getEmployee());
        registeredHolder.setDni(currentBreakRegistered.getDni());
        registeredHolder.setPhone(currentBreakRegistered.getMobile());
        registeredHolder.setDate(currentBreakRegistered.getDate());
        registeredHolder.setState(currentBreakRegistered.getStatus());
        registeredHolder.setTypeBreak(currentBreakRegistered.getBreakType());
        registeredHolder.setTypeProduct(currentBreakRegistered.getBreakProduct());
    }

    @Override
    public int getItemCount() {
        return breaks.size();
    }

    public static class RegisteredHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvDni, tvPhone, tvDate, tvTypeBreak, tvTypeProduct, tvState;

        public RegisteredHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_break_registered_tvName);
            tvDni = itemView.findViewById(R.id.item_break_registered_tvDni);
            tvPhone = itemView.findViewById(R.id.item_break_registered_tvPhone);
            tvDate = itemView.findViewById(R.id.item_break_registered_tvDate);
            tvTypeBreak = itemView.findViewById(R.id.item_break_registered_tvTypeBreak);
            tvTypeProduct = itemView.findViewById(R.id.item_break_registered_tvTypeProduct);
            tvState = itemView.findViewById(R.id.item_break_registered_tvState);
        }

        public void setName(String name){
            tvName.setText(name);
        }

        public void setDni(String dni){
            tvDni.setText(dni);
        }

        public void setPhone(String phone){
            tvPhone.setText(phone);
        }
        public void setDate(String date){
            tvDate.setText(date);
        }
        public void setTypeBreak(String typeBreak){
            tvTypeBreak.setText(typeBreak);
        }
        public void setTypeProduct(String typeProduct){
            tvTypeProduct.setText(typeProduct);
        }
        public void setState(String state){
//            String strEstado = "";
//            int colorEstado = 0;
//            if (state.equals(ServiceFlexPlaceHistoryApi.APROBADO)){
//                strEstado = "APROBADO";colorEstado = R.drawable.etiqueta_verde;
//            }else if (state.equals(ServiceFlexPlaceHistoryApi.PENDIENTE)){
//                strEstado = "PENDIENTE";colorEstado = R.drawable.etiqueta_amarilla;
//            }else if (state.equals(ServiceFlexPlaceHistoryApi.RECHAZADO)){
//                strEstado = "RECHAZADO";colorEstado = R.drawable.etiqueta_roja;
//            }else if (state.equals(ServiceFlexPlaceHistoryApi.CANCELADO)){
//                strEstado = "CANCELADO";colorEstado = R.drawable.etiqueta_morada;
//            }
//            tvName.setText(strEstado);
//            tvName.setBackgroundResource(colorEstado);
            tvState.setText(state);
        }
    }
}
