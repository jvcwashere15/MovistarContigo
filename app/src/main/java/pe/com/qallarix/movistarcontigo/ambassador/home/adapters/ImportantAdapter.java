package pe.com.qallarix.movistarcontigo.ambassador.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.home.pojos.SelfhelpList;

public class ImportantAdapter extends RecyclerView.Adapter<ImportantAdapter.ImportanteHolder> {

    private List<SelfhelpList> importantes;
    private Context context;

    public ImportantAdapter(List<SelfhelpList> importantes, Context context) {
        this.importantes = importantes;
        this.context = context;
    }

    @NonNull
    @Override
    public ImportanteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_importante,viewGroup,false);
        ImportanteHolder importanteHolder = new ImportanteHolder(view);
        return importanteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImportanteHolder importanteHolder, int i) {
        SelfhelpList currentSelfhelpList = importantes.get(i);
        importanteHolder.setTvImportante(currentSelfhelpList.getSelfHelpName());
        importanteHolder.setTvDescripcion(
                context,
                currentSelfhelpList.getSelfHelpDescription() ,
                currentSelfhelpList.getSelfHelpLinkText(),
                currentSelfhelpList.getSelfHelpLink()
        );
        importanteHolder.configurarOnClick();
    }

    @Override
    public int getItemCount() {
        return importantes.size();
    }

    static class ImportanteHolder extends RecyclerView.ViewHolder{

        ConstraintLayout lytImportante;
        TextView tvImportante;
        TextView tvDescripcion;
        ImageView ivImagen;

        public ImportanteHolder(@NonNull View itemView) {
            super(itemView);
            lytImportante = itemView.findViewById(R.id.embajador_hogar_importante_lyt1);
            tvImportante = itemView.findViewById(R.id.embajador_hogar_importante_tv1);
            tvDescripcion = itemView.findViewById(R.id.embajador_hogar_importante_descripcion_1);
            ivImagen = itemView.findViewById(R.id.embajador_hogar_preguntas_iv1);
        }

        public void setTvImportante(String strImportante){
            tvImportante.setText(strImportante);
        }

        public void setTvDescripcion(final Context context, String strDescripcion,String linkText, final String link) {
            if (link != null || TextUtils.isEmpty(link)){
                String cadena = strDescripcion + " " + linkText + " ";

                Pattern p1 = Pattern.compile(linkText, Pattern.CASE_INSENSITIVE);
                Matcher matcher1 = p1.matcher(cadena);
                List<String> aquis = new ArrayList<>();
                while(matcher1.find()) {
                    aquis.add(matcher1.group());
                }

                SpannableString tagSpan = new SpannableString(cadena);

                for (final String sAqui : aquis){
                    int posStart = cadena.indexOf(sAqui);
                    ClickableSpan clickSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            textView.invalidate();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(link));
                            context.startActivity(intent);
                        }
                        @Override
                        public void updateDrawState(TextPaint paint) {
                            super.updateDrawState(paint);
                            paint.setUnderlineText(true); // set underline if you want to underline
                            paint.setColor(context.getResources().getColor(R.color.colorCanalEmbajador)); // set the color to blue

                        }
                    };
                    tagSpan.setSpan(clickSpan, posStart, posStart + sAqui.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tvDescripcion.setBackgroundColor(Color.TRANSPARENT);
                tvDescripcion.setText(tagSpan);
                tvDescripcion.setMovementMethod(LinkMovementMethod.getInstance());
            }else{
                tvDescripcion.setText(strDescripcion);
            }
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


    }



}
