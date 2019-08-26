package pe.com.qallarix.movistarcontigo.ambassador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.breaks.AmbassadorBreaksActivity;
import pe.com.qallarix.movistarcontigo.ambassador.home.AmbassadorHomeActivity;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.AmbassadorMobileActivity;
import pe.com.qallarix.movistarcontigo.ambassador.questions.AmbassadorQuestionsActivity;
import pe.com.qallarix.movistarcontigo.ambassador.registered.AmbassadorRegisteredActivity;
import pe.com.qallarix.movistarcontigo.ambassador.total.AmbassadorTotalActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;


public class AmbassadorChannelActivity extends TranquiParentActivity {

    private Toolbar toolbar;
    private CardView cvMovistarTotal, cvHogar,cvMovil,cvQuiebres,cvQuiebresRegistrados,cvPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambassador_channel);

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
        cvQuiebresRegistrados = findViewById(R.id.canal_embajador_cvQuiebresRegistrados);
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
        configurarClickCvCanalEmbajador(cvMovistarTotal, AmbassadorTotalActivity.class);
        configurarClickCvCanalEmbajador(cvHogar, AmbassadorHomeActivity.class);
        configurarClickCvCanalEmbajador(cvMovil, AmbassadorMobileActivity.class);
        configurarClickCvCanalEmbajador(cvQuiebres, AmbassadorBreaksActivity.class);
        configurarClickCvCanalEmbajador(cvQuiebresRegistrados, AmbassadorRegisteredActivity.class);
        configurarClickCvCanalEmbajador(cvPreguntas, AmbassadorQuestionsActivity.class);
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
                Intent intent = new Intent(AmbassadorChannelActivity.this,activityDestino);
                startActivity(intent);
            }
        });
    }
}
