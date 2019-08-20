package pe.com.qallarix.movistarcontigo.ambassador.breaks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos.Question;
import pe.com.qallarix.movistarcontigo.util.BulletSpanWithRadius;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.PreguntaHolder> {
    private List<Question> questions;
    private Context context;

    public QuestionAdapter(Context context) {
        this.context = context;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PreguntaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_importante,viewGroup,false);
        PreguntaHolder preguntaHolder = new PreguntaHolder(view);
        return preguntaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreguntaHolder preguntaHolder, int i) {
        Question currentQuestion = questions.get(i);
        preguntaHolder.setTvImportante(currentQuestion.getTitle());
        preguntaHolder.setTvDescripcion(context,currentQuestion.getContent());
        preguntaHolder.configurarOnClick();
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class PreguntaHolder extends RecyclerView.ViewHolder{

        ConstraintLayout lytImportante;
        TextView tvImportante;
        TextView tvDescripcion;
        ImageView ivImagen;

        public PreguntaHolder(@NonNull View itemView) {
            super(itemView);
            lytImportante = itemView.findViewById(R.id.embajador_hogar_importante_lyt1);
            tvImportante = itemView.findViewById(R.id.embajador_hogar_importante_tv1);
            tvDescripcion = itemView.findViewById(R.id.embajador_hogar_importante_descripcion_1);
            ivImagen = itemView.findViewById(R.id.embajador_hogar_preguntas_iv1);
        }

        public void setTvImportante(String strImportante){
            tvImportante.setText(strImportante);
        }

        public void setTvDescripcion(Context context,String strDescripcion) {
            crearVinetas(context, tvDescripcion,
                    strDescripcion,"\r\n",
                    16,3,
                    context.getResources().getColor(R.color.colorCanalEmbajador));
        }

        public void configurarOnClick(){
            lytImportante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvDescripcion.getVisibility() == View.VISIBLE){
                        tvDescripcion.setVisibility(View.GONE);
                        ivImagen.setRotation(90);
                        tvImportante.setTextColor(0xFF86888C);
                        return;
                    }
                    tvDescripcion.setVisibility(View.VISIBLE);
                    ivImagen.setRotation(270);
                    tvImportante.setTextColor(0xFF00A9E0);
                }
            });
        }

        public void crearVinetas(Context context, TextView textView, String texto, String regex, int gap, int radius,int color){
            String arr[] = texto.split(regex);

            int bulletGap = (int) (context.getResources().getDisplayMetrics().density * gap);
            int bulletRadius = (int) (context.getResources().getDisplayMetrics().density * radius);

            SpannableStringBuilder ssb = new SpannableStringBuilder();
            for (int i = 0; i < arr.length; i++) {
                String line = arr[i];
                SpannableString ss = new SpannableString(line);
                ss.setSpan(new BulletSpanWithRadius(bulletRadius,bulletGap,color), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.append(ss);

                //avoid last "\n"
                if(i+1<arr.length)
                    ssb.append("\n");

            }
            textView.setText(ssb);
        }

    }
}
