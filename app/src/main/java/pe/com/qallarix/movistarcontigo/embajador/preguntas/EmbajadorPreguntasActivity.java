package pe.com.qallarix.movistarcontigo.embajador.preguntas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DetalleDescuentoActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;


public class EmbajadorPreguntasActivity extends TranquiParentActivity {

    private Toolbar toolbar;

    private LinearLayout lytHogar1,lytHogar2,lytHogar3,lytHogar4,lytHogar5,lytHogar6;
    private LinearLayout lytMovil1,lytMovil2,lytMovil3,lytMovil4,lytMovil5,
            lytMovil6, lytMovil7, lytMovil8, lytMovil9;

    private TextView tvPreguntaH1,tvPreguntaH2,tvPreguntaH3,tvPreguntaH4,
            tvPreguntaH5,tvPreguntaH6;
    private TextView tvPreguntaM1,tvPreguntaM2,tvPreguntaM3,tvPreguntaM4,
            tvPreguntaM5,tvPreguntaM6,tvPreguntaM7,tvPreguntaM8,tvPreguntaM9;

    private ImageView ivHogar1,ivHogar2,ivHogar3,ivHogar4,ivHogar5,ivHogar6;
    private ImageView ivMovil1,ivMovil2,ivMovil3,ivMovil4,ivMovil5,
            ivMovil6,ivMovil7,ivMovil8,ivMovil9;

    private LinearLayout lytRespuestaH1,lytRespuestaH2,lytRespuestaH3,lytRespuestaH4,
            lytRespuestaH5,lytRespuestaH6;
    private LinearLayout lytRespuestaM1,lytRespuestaM2,lytRespuestaM3,lytRespuestaM4,
            lytRespuestaM5,lytRespuestaM6,lytRespuestaM7,lytRespuestaM8,lytRespuestaM9;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embajador_preguntas);
        configurarToolbar();
        bindearVistas();
        configurarBotonDetallePregunta(lytHogar1,lytRespuestaH1,tvPreguntaH1,ivHogar1);
        configurarBotonDetallePregunta(lytHogar2,lytRespuestaH2,tvPreguntaH2,ivHogar2);
        configurarBotonDetallePregunta(lytHogar3,lytRespuestaH3,tvPreguntaH3,ivHogar3);
        configurarBotonDetallePregunta(lytHogar4,lytRespuestaH4,tvPreguntaH4,ivHogar4);
        configurarBotonDetallePregunta(lytHogar5,lytRespuestaH5,tvPreguntaH5,ivHogar5);
        configurarBotonDetallePregunta(lytHogar6,lytRespuestaH6,tvPreguntaH6,ivHogar6);

        configurarBotonDetallePregunta(lytMovil1,lytRespuestaM1,tvPreguntaM1,ivMovil1);
        configurarBotonDetallePregunta(lytMovil2,lytRespuestaM2,tvPreguntaM2,ivMovil2);
        configurarBotonDetallePregunta(lytMovil3,lytRespuestaM3,tvPreguntaM3,ivMovil3);
        configurarBotonDetallePregunta(lytMovil4,lytRespuestaM4,tvPreguntaM4,ivMovil4);
        configurarBotonDetallePregunta(lytMovil5,lytRespuestaM5,tvPreguntaM5,ivMovil5);
        configurarBotonDetallePregunta(lytMovil6,lytRespuestaM6,tvPreguntaM6,ivMovil6);
        configurarBotonDetallePregunta(lytMovil7,lytRespuestaM7,tvPreguntaM7,ivMovil7);
        configurarBotonDetallePregunta(lytMovil8,lytRespuestaM8,tvPreguntaM8,ivMovil8);
        configurarBotonDetallePregunta(lytMovil9,lytRespuestaM9,tvPreguntaM9,ivMovil9);
    }

    private void bindearVistas() {
        lytHogar1 = findViewById(R.id.embajador_preguntas_hogar_lyt1);
        lytHogar2 = findViewById(R.id.embajador_preguntas_hogar_lyt2);
        lytHogar3 = findViewById(R.id.embajador_preguntas_hogar_lyt3);
        lytHogar4 = findViewById(R.id.embajador_preguntas_hogar_lyt4);
        lytHogar5 = findViewById(R.id.embajador_preguntas_hogar_lyt5);
        lytHogar6 = findViewById(R.id.embajador_preguntas_hogar_lyt6);

        lytMovil1 = findViewById(R.id.embajador_preguntas_movil_lyt1);
        lytMovil2 = findViewById(R.id.embajador_preguntas_movil_lyt2);
        lytMovil3 = findViewById(R.id.embajador_preguntas_movil_lyt3);
        lytMovil4 = findViewById(R.id.embajador_preguntas_movil_lyt4);
        lytMovil5 = findViewById(R.id.embajador_preguntas_movil_lyt5);
        lytMovil6 = findViewById(R.id.embajador_preguntas_movil_lyt6);
        lytMovil7 = findViewById(R.id.embajador_preguntas_movil_lyt7);
        lytMovil8 = findViewById(R.id.embajador_preguntas_movil_lyt8);
        lytMovil9 = findViewById(R.id.embajador_preguntas_movil_lyt9);


        tvPreguntaH1 = findViewById(R.id.embajador_preguntas_hogar_tv1);
        tvPreguntaH2 = findViewById(R.id.embajador_preguntas_hogar_tv2);
        tvPreguntaH3 = findViewById(R.id.embajador_preguntas_hogar_tv3);
        tvPreguntaH4 = findViewById(R.id.embajador_preguntas_hogar_tv4);
        tvPreguntaH5 = findViewById(R.id.embajador_preguntas_hogar_tv5);
        tvPreguntaH6 = findViewById(R.id.embajador_preguntas_hogar_tv6);

        tvPreguntaM1 = findViewById(R.id.embajador_preguntas_movil_tv1);
        tvPreguntaM2 = findViewById(R.id.embajador_preguntas_movil_tv2);
        tvPreguntaM3 = findViewById(R.id.embajador_preguntas_movil_tv3);
        tvPreguntaM4 = findViewById(R.id.embajador_preguntas_movil_tv4);
        tvPreguntaM5 = findViewById(R.id.embajador_preguntas_movil_tv5);
        tvPreguntaM6 = findViewById(R.id.embajador_preguntas_movil_tv6);
        tvPreguntaM7 = findViewById(R.id.embajador_preguntas_movil_tv7);
        tvPreguntaM8 = findViewById(R.id.embajador_preguntas_movil_tv8);
        tvPreguntaM9 = findViewById(R.id.embajador_preguntas_movil_tv9);


        ivHogar1 = findViewById(R.id.embajador_hogar_preguntas_iv1);
        ivHogar2 = findViewById(R.id.embajador_hogar_preguntas_iv2);
        ivHogar3 = findViewById(R.id.embajador_hogar_preguntas_iv3);
        ivHogar4 = findViewById(R.id.embajador_hogar_preguntas_iv4);
        ivHogar5 = findViewById(R.id.embajador_hogar_preguntas_iv5);
        ivHogar6 = findViewById(R.id.embajador_hogar_preguntas_iv6);

        ivMovil1 = findViewById(R.id.embajador_movil_preguntas_iv1);
        ivMovil2 = findViewById(R.id.embajador_movil_preguntas_iv2);
        ivMovil3 = findViewById(R.id.embajador_movil_preguntas_iv3);
        ivMovil4 = findViewById(R.id.embajador_movil_preguntas_iv4);
        ivMovil5 = findViewById(R.id.embajador_movil_preguntas_iv5);
        ivMovil6 = findViewById(R.id.embajador_movil_preguntas_iv6);
        ivMovil7 = findViewById(R.id.embajador_movil_preguntas_iv7);
        ivMovil8 = findViewById(R.id.embajador_movil_preguntas_iv8);
        ivMovil9 = findViewById(R.id.embajador_movil_preguntas_iv9);


        lytRespuestaH1 = findViewById(R.id.embajador_preguntas_hogar_descripcion_1);
        lytRespuestaH2 = findViewById(R.id.embajador_preguntas_hogar_descripcion_2);
        lytRespuestaH3 = findViewById(R.id.embajador_preguntas_hogar_descripcion_3);
        lytRespuestaH4 = findViewById(R.id.embajador_preguntas_hogar_descripcion_4);
        lytRespuestaH5 = findViewById(R.id.embajador_preguntas_hogar_descripcion_5);
        lytRespuestaH6 = findViewById(R.id.embajador_preguntas_hogar_descripcion_6);

        lytRespuestaM1 = findViewById(R.id.embajador_preguntas_movil_descripcion_1);
        lytRespuestaM2 = findViewById(R.id.embajador_preguntas_movil_descripcion_2);
        lytRespuestaM3 = findViewById(R.id.embajador_preguntas_movil_descripcion_3);
        lytRespuestaM4 = findViewById(R.id.embajador_preguntas_movil_descripcion_4);
        lytRespuestaM5 = findViewById(R.id.embajador_preguntas_movil_descripcion_5);
        lytRespuestaM6 = findViewById(R.id.embajador_preguntas_movil_descripcion_6);
        lytRespuestaM7 = findViewById(R.id.embajador_preguntas_movil_descripcion_7);
        lytRespuestaM8 = findViewById(R.id.embajador_preguntas_movil_descripcion_8);
        lytRespuestaM9 = findViewById(R.id.embajador_preguntas_movil_descripcion_9);
    }


    private void configurarToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("Preguntas frecuentes");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void configurarBotonDetallePregunta(LinearLayout lytPregunta, final LinearLayout lytRespuesta, final TextView txtPregunta, final ImageView iv){
        lytPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lytRespuesta.getVisibility() == View.VISIBLE){
                    lytRespuesta.setVisibility(View.GONE);
                    iv.setRotation(90);
                    txtPregunta.setTextColor(getResources().getColor(R.color.colorTextoCanalEmbajador));
                    return;
                }
                lytRespuesta.setVisibility(View.VISIBLE);
                iv.setRotation(270);
                txtPregunta.setTextColor(getResources().getColor(R.color.colorCanalEmbajador));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_just_account, menu);
        View view = menu.findItem(R.id.action_account).getActionView();
        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
        tvIniciales.setText(obtenerIniciales());
        tvIniciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmbajadorPreguntasActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_account:
                Intent intent = new Intent(EmbajadorPreguntasActivity.this,AccountActivity.class);
                startActivity(intent);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
