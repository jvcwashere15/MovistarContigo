package pe.com.qallarix.movistarcontigo.embajador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DetalleDescuentoActivity;
import pe.com.qallarix.movistarcontigo.embajador.hogar.EmbajadorHogarActivity;
import pe.com.qallarix.movistarcontigo.embajador.movil.EmbajadorMovilActivity;
import pe.com.qallarix.movistarcontigo.embajador.preguntas.EmbajadorPreguntasActivity;
import pe.com.qallarix.movistarcontigo.embajador.quiebres.EmbajadorQuiebresActivity;
import pe.com.qallarix.movistarcontigo.embajador.total.EmbajadorMovistarTotalActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;


public class CanalEmbajadorActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private CardView cvMovistarTotal;
    private CardView cvHogar;
    private CardView cvMovil;
    private CardView cvQuiebres;
    private CardView cvPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canal_embajador);

        bindearVistas();
        configurarToolbar();
        configurarCardViewsCanalEmbajador();
    }

    /**
     * metodo para referenciar las vistas de la UI
     */
    public void bindearVistas(){
        toolbar = findViewById(R.id.toolbar);
        cvMovistarTotal = findViewById(R.id.canal_embajador_cvMovistarTotal);
        cvHogar = findViewById(R.id.canal_embajador_cvHogar);
        cvMovil = findViewById(R.id.canal_embajador_cvMovil);
        cvQuiebres = findViewById(R.id.canal_embajador_cvQuiebresReclamos);
        cvPreguntas = findViewById(R.id.canal_embajador_cvPreguntasFrecuentes);
    }

    /**
     * metodo que configura el aspecto visual y funcionalidad navigation del toolbar
     */
    public void configurarToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        toolbar.setTitle("Canal embajador");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    /**
     * metodo para configurar los cardviews beneficios especiales
     */
    private void configurarCardViewsCanalEmbajador() {
        configurarClickCvCanalEmbajador(cvMovistarTotal,EmbajadorMovistarTotalActivity.class);
        configurarClickCvCanalEmbajador(cvHogar,EmbajadorHogarActivity.class);
        configurarClickCvCanalEmbajador(cvMovil,EmbajadorMovilActivity.class);
        configurarClickCvCanalEmbajador(cvQuiebres,EmbajadorQuiebresActivity.class);
        configurarClickCvCanalEmbajador(cvPreguntas,EmbajadorPreguntasActivity.class);
    }

    /**
     * metodo para configurar el listener onclick del cardview beneficio especial indicado
     * @param cardView referencia del cardview al que se configurara el onclick
     * @param activityDestino indica la actividad que se abrira al dar click
     */
    public void configurarClickCvCanalEmbajador(CardView cardView, final Class<?> activityDestino){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CanalEmbajadorActivity.this,activityDestino);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_just_account, menu);
//        View view = menu.findItem(R.id.action_account).getActionView();
//        TextView tvIniciales = view.findViewById(R.id.menu_tvIniciales);
//        tvIniciales.setText(obtenerIniciales());
//        tvIniciales.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CanalEmbajadorActivity.this,AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_account:
//                Intent intent = new Intent(CanalEmbajadorActivity.this,AccountActivity.class);
//                startActivity(intent);
//                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
