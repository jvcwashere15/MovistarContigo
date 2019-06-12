package pe.com.qallarix.movistarcontigo.salud.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.BasePermissionListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.AccountActivity;
import pe.com.qallarix.movistarcontigo.salud.PdfActivity;
import pe.com.qallarix.movistarcontigo.salud.VinetaAdapter;
import pe.com.qallarix.movistarcontigo.salud.pojos.Detail;
import pe.com.qallarix.movistarcontigo.salud.pojos.HealthPlan;
import pe.com.qallarix.movistarcontigo.util.PermissionUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleSaludFragment extends Fragment {
    private CardView cvLineamientos;
    private CardView cvBeneficios;
    private CardView cvAportes;
    private CardView cvAfiliaciones;


    private ImageView ivLineamientos;
    private ImageView ivBeneficios;
    private ImageView ivAportes;
    private ImageView ivAfiliaciones;


    private View vDescripcionLineamientos;
    private View vDescripcionBeneficios;
    private View vDescripcionAportes;
    private View vDescripcionAfiliaciones;

    private TextView
            tvPdfEpsFichaAfiliacion,
            tvPdfEpsCartaPlan,
            tvPdfEpsRequisitos,
            tvPdfEpsActualizacion,
            tvPdfEpsListaGestores,
            tvPdfEpsCartaDesafiliacion;

    private TextView
            tvPdfOncologicoFichaAfiliacion,
            tvPdfOncologicoCartaPlan,
            tvPdfOncologicoRequisitos,
            tvPdfOncologicoActualizacion,
            tvPdfOncologicoListaGestores,
            tvPdfOncologicoCartaDesafiliacion;

    private TextView
            tvPdfLgbtiqFichaAfiliacion,
            tvPdfLgbtiqCartaPlan,
            tvPdfLgbtiqDeclaracionJurada,
            tvPdfLgbtiqCartaDesafiliacion;


    AlertDialog alertDialog;

    private final String KEY_PDF_TITLE_ACTIVITY = "titulo_pdf_activity";
    private final String KEY_URI_PDF = "uri_pdf";

    private final String TABLE_EPS = "TABLE_EPS";
    private final String TABLE_NONE = "TABLE_NONE";
    private final String TABLE_LGTB = "TABLE_LGTB";

    private TextView tvDescripcionAportes;

    private HealthPlan healthPlan;
    private Detail mDetail;

    private TextView tvDescripcionLineamientos;
    private TextView tvPdfLink;

    private RecyclerView rvBeneficios;

    private TextView tvInformacionAdicional;

    private View tableEPS;
    private View tableLGTB;

    private View vAfiliacionEPS,vAfiliacionOncologico, vAfiliacionLGBTIQ;

    private static final String TAG = "TEST";
    private static final String ARGUMENT_HEALTHPLAN = "healthplan";


    public DetalleSaludFragment() {
        // Required empty public constructor
    }

    public static DetalleSaludFragment newInstance(HealthPlan healthPlan) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_HEALTHPLAN, healthPlan);
        DetalleSaludFragment fragment = new DetalleSaludFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        healthPlan = (HealthPlan) getArguments().getSerializable(ARGUMENT_HEALTHPLAN);
        View rootView = inflater.inflate(R.layout.fragment_detalle_salud, container, false);
        cvLineamientos = rootView.findViewById(R.id.detalle_salud_cvLineamientos);
        cvBeneficios = rootView.findViewById(R.id.detalle_salud_cvBeneficios);
        cvAportes = rootView.findViewById(R.id.detalle_salud_cvAportes);
        cvAfiliaciones = rootView.findViewById(R.id.detalle_salud_cvAfiliaciones);

        ivLineamientos = rootView.findViewById(R.id.salud_eps_iv1);
        ivBeneficios = rootView.findViewById(R.id.salud_eps_iv2);
        ivAportes = rootView.findViewById(R.id.salud_eps_iv3);
        ivAfiliaciones = rootView.findViewById(R.id.salud_eps_iv4);


        vDescripcionLineamientos = rootView.findViewById(R.id.detalle_salud_lineamientos_descripcion);
        vDescripcionBeneficios = rootView.findViewById(R.id.detalle_salud_beneficios_descripcion);
        vDescripcionAportes = rootView.findViewById(R.id.detalle_salud_aportes_descripcion);
        vDescripcionAfiliaciones = rootView.findViewById(R.id.detalle_salud_afiliaciones_descripcion);


        rvBeneficios = rootView.findViewById(R.id.detalle_salud_rvBeneficios);

        tvInformacionAdicional = rootView.findViewById(R.id.detalle_salud_tvInformacionAdicional);

        tvDescripcionLineamientos = rootView.findViewById(R.id.detalle_salud_lineamientos_tvDescripcion);
        tvPdfLink = rootView.findViewById(R.id.detalle_salud_lineamientos_tvLinkPdf);

        tvDescripcionAportes = rootView.findViewById(R.id.detalle_salud_tvDescripcionAportes);
        tableEPS = rootView.findViewById(R.id.table_eps);
        tableLGTB = rootView.findViewById(R.id.table_lgbtq);

        vAfiliacionEPS = rootView.findViewById(R.id.afiliaciones_eps);
        vAfiliacionOncologico = rootView.findViewById(R.id.afiliaciones_oncologico);
        vAfiliacionLGBTIQ = rootView.findViewById(R.id.afiliaciones_lgbtiq);

        tvPdfEpsFichaAfiliacion = rootView.findViewById(R.id.afiliacion_eps_tvFichaAfiliacion);
        tvPdfEpsActualizacion = rootView.findViewById(R.id.afiliacion_eps_tvActualizacion);
        tvPdfEpsCartaDesafiliacion = rootView.findViewById(R.id.afiliacion_eps_tvCartaDesafiliacion);
        tvPdfEpsCartaPlan = rootView.findViewById(R.id.afiliacion_eps_tvCartaPlan);
        tvPdfEpsListaGestores = rootView.findViewById(R.id.afiliacion_eps_tvListaGestores);
        tvPdfEpsRequisitos = rootView.findViewById(R.id.afiliacion_eps_tvRequisitos);

        tvPdfOncologicoFichaAfiliacion = rootView.findViewById(R.id.afiliaciones_oncologico_tvFichaAfiliacion);
        tvPdfOncologicoActualizacion = rootView.findViewById(R.id.afiliaciones_oncologico_tvActualizacion);
        tvPdfOncologicoCartaDesafiliacion = rootView.findViewById(R.id.afiliaciones_oncologico_tvCartaDesafiliacion);
        tvPdfOncologicoCartaPlan = rootView.findViewById(R.id.afiliaciones_oncologico_tvCartaPlan);
        tvPdfOncologicoListaGestores = rootView.findViewById(R.id.afiliaciones_oncologico_tvListaGestores);
        tvPdfOncologicoRequisitos = rootView.findViewById(R.id.afiliaciones_oncologico_tvRequisitos);

        tvPdfLgbtiqFichaAfiliacion = rootView.findViewById(R.id.afiliaciones_lgbtiq_tvFichaAfiliacion);
        tvPdfLgbtiqCartaDesafiliacion = rootView.findViewById(R.id.afiliaciones_lgbtiq_tvCartaDesafiliacion);
        tvPdfLgbtiqCartaPlan = rootView.findViewById(R.id.afiliaciones_lgbtiq_tvCartaPlan);
        tvPdfLgbtiqDeclaracionJurada = rootView.findViewById(R.id.afiliaciones_lgbtiq_tvDeclaracionJurada);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mDetail = healthPlan.getDetail();

        configurarCVLineamientos();
        configurarCVBeneficios();
        configurarCVAportes();
        configurarCVAfiliaciones();

        String cadena  = healthPlan.getAdditionalInformation();


        Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(cadena);
        List<String> emails = new ArrayList<>();
        while(matcher.find()) {
            emails.add(matcher.group());
        }

        Pattern p1 = Pattern.compile("[0-9]{9}", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = p1.matcher(cadena);
        List<String> phones = new ArrayList<>();
        while(matcher1.find()) {
            phones.add(matcher1.group());
        }

        SpannableString tagSpan = new SpannableString(cadena);

        for (final String sEmail : emails){
            int posStart = cadena.indexOf(sEmail);
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    textView.invalidate();
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:" + sEmail));
                    startActivity(emailIntent);
                }
                @Override
                public void updateDrawState(TextPaint paint) {
                    super.updateDrawState(paint);
                    paint.setUnderlineText(true); // set underline if you want to underline
                    paint.setColor(getActivity().getResources().getColor(R.color.colorCanalEmbajador)); // set the color to blue

                }
            };
            tagSpan.setSpan(clickSpan, posStart, posStart + sEmail.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for (final String sPhone : phones){
            int posStart = cadena.indexOf(sPhone);
            ClickableSpan clickSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    textView.invalidate();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + sPhone));
                    startActivity(intent);
                }
                @Override
                public void updateDrawState(TextPaint paint) {
                    super.updateDrawState(paint);
                    paint.setUnderlineText(true); // set underline if you want to underline
                    paint.setColor(getActivity().getResources().getColor(R.color.colorCanalEmbajador)); // set the color to blue

                }
            };
            tagSpan.setSpan(clickSpan, posStart, posStart + sPhone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvInformacionAdicional.setBackgroundColor(Color.TRANSPARENT);
        tvInformacionAdicional.setText(tagSpan);
        tvInformacionAdicional.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void configurarCVAfiliaciones() {
        if (mDetail.getContributionTable().equals(TABLE_NONE)) vAfiliacionOncologico.setVisibility(View.VISIBLE);
        else if (mDetail.getContributionTable().equals(TABLE_LGTB)) vAfiliacionLGBTIQ.setVisibility(View.VISIBLE);
        else if (mDetail.getContributionTable().equals(TABLE_EPS)) vAfiliacionEPS.setVisibility(View.VISIBLE);
        cvAfiliaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vDescripcionLineamientos.getVisibility() == View.VISIBLE)cvLineamientos.callOnClick();
                if (vDescripcionAportes.getVisibility() == View.VISIBLE)cvAportes.callOnClick();
                if (vDescripcionBeneficios.getVisibility() == View.VISIBLE)cvBeneficios.callOnClick();

                if (vDescripcionAfiliaciones.getVisibility() == View.VISIBLE){
                    vDescripcionAfiliaciones.setVisibility(View.GONE);
                    ivAfiliaciones.setRotation(90);
                    return;
                }
                vDescripcionAfiliaciones.setVisibility(View.VISIBLE);
                ivAfiliaciones.setRotation(270);
            }
        });

        configurarBotonOpenPdf(tvPdfEpsFichaAfiliacion,"Ficha de afiliación EPS",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/1-Ficha%20de%20afiliaci%C3%B3n%20EPS.pdf");
        configurarBotonOpenPdf(tvPdfEpsCartaPlan,"Carta del Plan de Salud EPS",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/2-Carta%20de%20afiliaci%C3%B3n%20a%20Plan%20de%20Salud.pdf");
        configurarBotonOpenPdf(tvPdfEpsRequisitos,"Requisitos según afiliado",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/3-Requisitos%20seg%C3%BAn%20afiliado.pdf");
        configurarBotonOpenPdf(tvPdfEpsActualizacion,"Actualización de dependientes",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/4-Gu%C3%ADa%20de%20actualizaci%C3%B3n%20de%20dependientes.pdf");
        configurarBotonOpenPdf(tvPdfEpsListaGestores,"Lista de gestores zonales",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/5-Lista%20de%20Gestores%20Zonales.pdf");
        configurarBotonOpenPdf(tvPdfEpsCartaDesafiliacion,"Carta de desafiliación",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/6-Carta%20de%20desafiliaci%C3%B3n%20EPS.pdf");


        configurarBotonOpenPdf(tvPdfOncologicoFichaAfiliacion,"Ficha de afiliación EPS",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/1-Ficha%20de%20afiliaci%C3%B3n%20EPS.pdf");
        configurarBotonOpenPdf(tvPdfOncologicoCartaPlan,"Carta del Plan de Salud EPS",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/2-Carta%20de%20afiliaci%C3%B3n%20a%20Plan%20de%20Salud.pdf");
        configurarBotonOpenPdf(tvPdfOncologicoRequisitos,"Requisitos según afiliado",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/3-Requisitos%20seg%C3%BAn%20afiliado.pdf");
        configurarBotonOpenPdf(tvPdfOncologicoActualizacion,"Actualización de dependientes",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/4-Gu%C3%ADa%20de%20actualizaci%C3%B3n%20de%20dependientes.pdf");
        configurarBotonOpenPdf(tvPdfOncologicoListaGestores,"Lista de gestores zonales",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/5-Lista%20de%20Gestores%20Zonales.pdf");
        configurarBotonOpenPdf(tvPdfOncologicoCartaDesafiliacion,"Carta de desafiliación",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/6-Carta%20de%20desafiliaci%C3%B3n%20EPS.pdf");

        configurarBotonOpenPdf(tvPdfLgbtiqFichaAfiliacion,"Ficha de afiliación LGBTIQ+",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/LGBTIQ/1-Ficha%20de%20afiliaci%C3%B3n%20LGTBIQ.pdf");
        configurarBotonOpenPdf(tvPdfLgbtiqCartaPlan,"Carta de Plan de Salud LGBTIQ+",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/LGBTIQ/2-Carta%20de%20afilaci%C3%B3n%20a%20Plan%20de%20Salud.pdf");
        configurarBotonOpenPdf(tvPdfLgbtiqDeclaracionJurada,"Declaración jurada de domicilio",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/LGBTIQ/3-Declaraci%C3%B3n%20jurada%20de%20domicilio.pdf");
        configurarBotonOpenPdf(tvPdfLgbtiqCartaDesafiliacion,"Carta de desafiliación",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/LGBTIQ/4-Carta%20de%20desafiliaci%C3%B3n%20LGTBIQ.pdf");
    }

    private void configurarCVLineamientos() {
        cvLineamientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vDescripcionAfiliaciones.getVisibility() == View.VISIBLE)cvAfiliaciones.callOnClick();
                if (vDescripcionAportes.getVisibility() == View.VISIBLE)cvAportes.callOnClick();
                if (vDescripcionBeneficios.getVisibility() == View.VISIBLE)cvBeneficios.callOnClick();

                if (vDescripcionLineamientos.getVisibility() == View.VISIBLE){
                    vDescripcionLineamientos.setVisibility(View.GONE);
                    ivLineamientos.setRotation(90);
                    return;
                }
                vDescripcionLineamientos.setVisibility(View.VISIBLE);
                ivLineamientos.setRotation(270);
            }
        });
        tvDescripcionLineamientos.setText(mDetail.getPlanAlignment());
        tvPdfLink.setText(mDetail.getPlanAlignmentNameFile());
        configurarBotonOpenPdf(tvPdfLink,healthPlan.getTitle(),mDetail.getPlanAlignmentFile());
    }

    private void configurarBotonOpenPdf(TextView textView, final String title, final String file) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (PermissionUtils.neverAskAgainSelected(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            displayNeverAskAgainDialog();
                        } else {
                            mostrarDialogNecesitamosPermisos();
                        }
                    }
                }else{
                    Intent intent = new Intent(getActivity(),PdfActivity.class);
                    intent.putExtra(KEY_PDF_TITLE_ACTIVITY,title);
                    intent.putExtra(KEY_URI_PDF,file);
                    startActivity(intent);
                }

            }
        });
    }

    private void configurarCVBeneficios() {
        List<String> benefitLists = mDetail.getBenefitsList();
        if (benefitLists.size()>0){
            cvBeneficios.setVisibility(View.VISIBLE);
            cvBeneficios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vDescripcionLineamientos.getVisibility() == View.VISIBLE)cvLineamientos.callOnClick();
                    if (vDescripcionAportes.getVisibility() == View.VISIBLE)cvAportes.callOnClick();
                    if (vDescripcionAfiliaciones.getVisibility() == View.VISIBLE)cvAfiliaciones.callOnClick();

                    if (vDescripcionBeneficios.getVisibility() == View.VISIBLE){
                        vDescripcionBeneficios.setVisibility(View.GONE);
                        ivBeneficios.setRotation(90);
                        return;
                    }
                    vDescripcionBeneficios.setVisibility(View.VISIBLE);
                    ivBeneficios.setRotation(270);
                }
            });
            rvBeneficios.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvBeneficios.setHasFixedSize(true);
            rvBeneficios.setAdapter(new VinetaAdapter(benefitLists));
        }
    }

    private void configurarCVAportes() {
        tvDescripcionAportes.setText(mDetail.getContributionDescription());
        if (mDetail.getContributionTable().equals(TABLE_LGTB)) tableLGTB.setVisibility(View.VISIBLE);
        else if (mDetail.getContributionTable().equals(TABLE_EPS)) tableEPS.setVisibility(View.VISIBLE);
        cvAportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vDescripcionLineamientos.getVisibility() == View.VISIBLE)cvLineamientos.callOnClick();
                if (vDescripcionAfiliaciones.getVisibility() == View.VISIBLE)cvAfiliaciones.callOnClick();
                if (vDescripcionBeneficios.getVisibility() == View.VISIBLE)cvBeneficios.callOnClick();

                if (vDescripcionAportes.getVisibility() == View.VISIBLE){
                    vDescripcionAportes.setVisibility(View.GONE);
                    ivAportes.setRotation(90);
                    return;
                }
                vDescripcionAportes.setVisibility(View.VISIBLE);
                ivAportes.setRotation(270);
            }
        });
    }


    public void mostrarDialogNecesitamosPermisos(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Para visualizar el archivo de los lineamientos del plan, permite que Movistar Contigo pueda acceder al almacenamiento");
        builder.setCancelable(false);
        builder.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                pedirPermisos();
            }
        });
        builder.setNegativeButton("AHORA NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void pedirPermisos() {
        if (alertDialog != null) alertDialog.dismiss();
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener(){
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(getActivity(),PdfActivity.class);
                        intent.putExtra(KEY_PDF_TITLE_ACTIVITY,healthPlan.getTitle());
                        intent.putExtra(KEY_URI_PDF,mDetail.getPlanAlignmentFile());
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        PermissionUtils.setShouldShowStatus(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }).check();
    }

    private void displayNeverAskAgainDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Para visualizar el documento pdf sobre los lineamientos del plan, permite que podamos acceder al almacenamiento"+
                "\nToca Ajustes > Permisos, y activa Almacenamiento");
        builder.setCancelable(false);
        builder.setPositiveButton("AJUSTES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("AHORA NO", null);
        builder.show();
    }
}
