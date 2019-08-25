package pe.com.qallarix.movistarcontigo.specials.adapters;

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
import pe.com.qallarix.movistarcontigo.specials.pojos.SpecialBenefit;

public class SpecialBenefitAdapter extends RecyclerView.Adapter<SpecialBenefitAdapter.BeneficioEspecialHolder> {

    private ClickBeneficioEspecial clickBeneficioEspecial;
    private Context context;
    private List<SpecialBenefit> specialBenefits;

    public SpecialBenefitAdapter(Context context, List<SpecialBenefit> specialBenefits, ClickBeneficioEspecial clickBeneficioEspecial) {
        this.clickBeneficioEspecial = clickBeneficioEspecial;
        this.context = context;
        this.specialBenefits = specialBenefits;
    }

    @NonNull
    @Override
    public BeneficioEspecialHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_beneficios_especiales,viewGroup,false);
        return new BeneficioEspecialHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeneficioEspecialHolder beneficioEspecialHolder, final int position) {
        SpecialBenefit currentSpecialBenefit = specialBenefits.get(position);
        beneficioEspecialHolder.setIcono(context,currentSpecialBenefit.getIcon());
        beneficioEspecialHolder.setNombre(currentSpecialBenefit.getTitle());
        beneficioEspecialHolder.cvBeneficio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBeneficioEspecial.onClick(position,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialBenefits.size();
    }


    public interface ClickBeneficioEspecial{
        void onClick(int position, View view);
    }

    public static class BeneficioEspecialHolder extends RecyclerView.ViewHolder{

        private ImageView ivIcono;
        private TextView tvNombre;
        private CardView cvBeneficio;

        public BeneficioEspecialHolder(@NonNull View itemView) {
            super(itemView);
            cvBeneficio = itemView.findViewById(R.id.item_beneficio_especial_cvBeneficio);
            ivIcono = itemView.findViewById(R.id.item_beneficio_especial_ivIcono);
            tvNombre = itemView.findViewById(R.id.item_beneficio_especial_tvNombre);
        }

        public void setIcono(Context context, String urlImagen){
            try{
                Picasso.with(context).load(urlImagen).into(ivIcono);
            }catch (Exception e){

            }

        }

        public void setNombre(String nombre){
            tvNombre.setText(nombre);
        }

    }
}
