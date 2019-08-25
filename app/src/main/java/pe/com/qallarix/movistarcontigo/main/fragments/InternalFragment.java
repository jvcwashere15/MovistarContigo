package pe.com.qallarix.movistarcontigo.main.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternalFragment extends Fragment {

    private CardView cvSuccesFactor;
    private CardView cvRemedy;
    private CardView cvCae;
    private CardView cvWorkplace;
    private CardView cvDoit;
    private CardView cvEdi;
    private CardView cvSistemasClick;

    private View vDescSuccesFactor;
    private View vDescRemedy;
    private View vDescCae;
    private View vDescDoit;
    private View vDescEdi;
    private View vDescWorkplace;
    private View vDescSistemasClick;

    private ImageView ivFlechaSuccess;
    private ImageView ivFlechaCae;
    private ImageView ivFlechaRemedy;
    private ImageView ivFlechaDoit;
    private ImageView ivFlechaWorkplace;
    private ImageView ivFlechaSistemasClick;
    private ImageView ivFlechaEdi;

    private static final String TITLE_SFACTOR = "SFACTOR",
            TITLE_CAE = "CAE",
            TITLE_WORKPLACE = "WORKPLACE",
            TITLE_EDI = "EDI",
            TITLE_DOIT = "DOIT",
            TITLE_SISCLICK = "SISCLICK",
            TITLE_REMEDY = "REMEDY";


    public InternalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_internas, container, false);
        cvSuccesFactor = rootView.findViewById(R.id.herramientas_cvSuccesFactor);
        cvCae = rootView.findViewById(R.id.herramientas_cvCae);
        cvRemedy = rootView.findViewById(R.id.herramientas_cvRemedy);
        cvDoit = rootView.findViewById(R.id.herramientas_cvDoit);
        cvSistemasClick = rootView.findViewById(R.id.herramientas_cvSistemasClick);
        cvEdi = rootView.findViewById(R.id.herramientas_cvEdi);
        cvWorkplace = rootView.findViewById(R.id.herramientas_cvWorkplace);


        vDescSuccesFactor = rootView.findViewById(R.id.herramientas_view_description_success_factor);
        vDescRemedy = rootView.findViewById(R.id.herramientas_view_description_remedy);
        vDescCae = rootView.findViewById(R.id.herramientas_view_description_cae);
        vDescDoit = rootView.findViewById(R.id.herramientas_view_description_doit);
        vDescEdi = rootView.findViewById(R.id.herramientas_view_description_edi);
        vDescWorkplace = rootView.findViewById(R.id.herramientas_view_description_workplace);
        vDescSistemasClick = rootView.findViewById(R.id.herramientas_view_description_sistemas_click);

        ivFlechaSuccess = rootView.findViewById(R.id.herramientas_ivFlechaSuccess);
        ivFlechaCae = rootView.findViewById(R.id.herramientas_ivFlechaCae);
        ivFlechaRemedy = rootView.findViewById(R.id.herramientas_ivFlechaRemedy);
        ivFlechaDoit = rootView.findViewById(R.id.herramientas_ivFlechaDoit);
        ivFlechaWorkplace = rootView.findViewById(R.id.herramientas_ivFlechaWorkplace);
        ivFlechaSistemasClick = rootView.findViewById(R.id.herramientas_ivFlechaSistemasClick);
        ivFlechaEdi = rootView.findViewById(R.id.herramientas_ivFlechaEdi);



        configurarBotonIrALink(R.id.herramientas_cae_btIrHerramienta,rootView,"https://telefonicahelprh.zendesk.com");
        configurarBotonIrALink(R.id.herramientas_doit_btIrAHerramienta,rootView,"https://doit.movistar.com.pe");
        configurarBotonIrALink(R.id.herramientas_succesfactor_btIrHerramienta,rootView,"https://performancemanager5.successfactors.eu/login?company=Telefonica#/login");
        configurarBotonIrALink(R.id.herramientas_edi_btIrHerramienta,rootView,"https://www.sistemaedi.com.pe/EDI");
        configurarBotonIrALink(R.id.herramientas_workplace_btIrHerramienta,rootView,"https://telefonica.facebook.com/");



        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configurarBotonCVHerramienta(cvSuccesFactor,vDescSuccesFactor,ivFlechaSuccess);
        configurarBotonCVHerramienta(cvCae,vDescCae,ivFlechaCae);
        configurarBotonCVHerramienta(cvRemedy,vDescRemedy,ivFlechaRemedy);
        configurarBotonCVHerramienta(cvDoit,vDescDoit,ivFlechaDoit);
        configurarBotonCVHerramienta(cvSistemasClick,vDescSistemasClick,ivFlechaSistemasClick);
        configurarBotonCVHerramienta(cvEdi,vDescEdi,ivFlechaEdi);
        configurarBotonCVHerramienta(cvWorkplace,vDescWorkplace,ivFlechaWorkplace);

    }

    private void configurarBotonCVHerramienta(final CardView cardView, final View view, final ImageView iv){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getVisibility() == View.VISIBLE){
                    view.setVisibility(View.GONE);
                    iv.setRotation(90);
                    return;
                }
                if (cardView.equals(cvSuccesFactor)) Analitycs.logEventoClickHerramientas(getActivity(),TITLE_SFACTOR);
                else if (cardView.equals(cvCae)) Analitycs.logEventoClickHerramientas(getActivity(),TITLE_CAE);
                else if (cardView.equals(cvDoit)) Analitycs.logEventoClickHerramientas(getActivity(),TITLE_DOIT);
                else if (cardView.equals(cvEdi)) Analitycs.logEventoClickHerramientas(getActivity(),TITLE_EDI);
                else if (cardView.equals(cvRemedy)) Analitycs.logEventoClickHerramientas(getActivity(),TITLE_REMEDY);
                else if (cardView.equals(cvSistemasClick)) Analitycs.logEventoClickHerramientas(getActivity(),TITLE_SISCLICK);
                else if (cardView.equals(cvWorkplace)) Analitycs.logEventoClickHerramientas(getActivity(),TITLE_WORKPLACE);
                view.setVisibility(View.VISIBLE);
                iv.setRotation(270);
            }
        });
    }

    private void configurarBotonIrALink(final int idResource, View rootView, final String link){
        rootView.findViewById(idResource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    if (idResource == R.id.herramientas_doit_btIrAHerramienta) Analitycs.logEventoClickHerramientaLink(getActivity(),TITLE_DOIT);
                    else if (idResource == R.id.herramientas_cae_btIrHerramienta) Analitycs.logEventoClickHerramientaLink(getActivity(),TITLE_CAE);
                    else if (idResource == R.id.herramientas_edi_btIrHerramienta) Analitycs.logEventoClickHerramientaLink(getActivity(),TITLE_EDI);
                    else if (idResource == R.id.herramientas_succesfactor_btIrHerramienta) Analitycs.logEventoClickHerramientaLink(getActivity(),TITLE_SFACTOR);
                    startActivity(intent);
                }
            }
        });
    }

}
