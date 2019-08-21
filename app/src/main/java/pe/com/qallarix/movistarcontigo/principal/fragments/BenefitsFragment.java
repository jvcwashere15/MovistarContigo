package pe.com.qallarix.movistarcontigo.principal.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.AmbassadorChannelActivity;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.especiales.EspecialesActivity;
import pe.com.qallarix.movistarcontigo.descuentos.DescuentosActivity;
import pe.com.qallarix.movistarcontigo.openlearning.OpenLearningActivity;
import pe.com.qallarix.movistarcontigo.salud.SaludActivity;


public class BenefitsFragment extends Fragment {

    CardView cvDescuentos;
    CardView cvSalud;
    CardView cvEstudios;
    CardView cvCanalEmbajador;
    CardView cvBeneficiosEspeciales;

    private static final String TITLE_DISCOUNT = "DISCOUNT",
            TITLE_HEALTH = "HEALTH",
            TITLE_AMBASSADOR = "AMBASSADOR",
            TITLE_STUDY = "STUDY",
            TITLE_SPECIALS = "SPECIALS";


    public BenefitsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beneficios, container, false);
        bindearVistas(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setearMetodoClick(cvDescuentos,DescuentosActivity.class);
        setearMetodoClick(cvSalud,SaludActivity.class);
        setearMetodoClick(cvEstudios, OpenLearningActivity.class);
//        cvEstudios.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://www.movistaropenlearning.com";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//            }
//        });
        setearMetodoClick(cvCanalEmbajador, AmbassadorChannelActivity.class);
        setearMetodoClick(cvBeneficiosEspeciales, EspecialesActivity.class);
    }

    /**
     * metodo para dar al funcionalidad click al cardview del beneficio
     * @param cardView vista cardview clickeable del beneficio
     * @param activityClass activity.class a donde se dirigira al dar click
     */
    private void setearMetodoClick(final CardView cardView, final Class<?> activityClass) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView.equals(cvSalud)) Analitycs.logEventoClickBenefits(getActivity(),TITLE_HEALTH);
                else if (cardView.equals(cvEstudios)) Analitycs.logEventoClickBenefits(getActivity(),TITLE_STUDY);
                else if (cardView.equals(cvDescuentos)) Analitycs.logEventoClickBenefits(getActivity(),TITLE_DISCOUNT);
                else if (cardView.equals(cvCanalEmbajador)) Analitycs.logEventoClickBenefits(getActivity(),TITLE_AMBASSADOR);
                else if (cardView.equals(cvBeneficiosEspeciales)) Analitycs.logEventoClickBenefits(getActivity(),TITLE_SPECIALS);
                irAlBeneficio(activityClass);
            }
        });
    }


    /**
     * metodo para ir a la actividad de un beneficio específico
     * @param activityClass la activity.class que se abrirá
     */
    public void irAlBeneficio(Class<?> activityClass){
        Intent intent =  new Intent(getActivity(),activityClass);
        startActivity(intent);
    }

    /**
     * metodo para conectar las vistas con sus referencias
     * @param vistaRaiz referencia a la vista inflada para el fragment
     */
    public void bindearVistas(View vistaRaiz){
        cvDescuentos = vistaRaiz.findViewById(R.id.cvDescuentos);
        cvSalud = vistaRaiz.findViewById(R.id.cvSalud);
        cvEstudios = vistaRaiz.findViewById(R.id.cvEstudios);
        cvCanalEmbajador = vistaRaiz.findViewById(R.id.cvCanalEmbajador);
        cvBeneficiosEspeciales = vistaRaiz.findViewById(R.id.cvBeneficiosEspeciales);
    }
}
