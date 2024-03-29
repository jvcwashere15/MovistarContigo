package pe.com.qallarix.movistarcontigo.flexplace.myteam;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.flexplace.forapprove.ForApproveFlexRequestDetailActivity;
import pe.com.qallarix.movistarcontigo.flexplace.myteam.pojos.FlexEquipo;
import pe.com.qallarix.movistarcontigo.flexplace.myteam.pojos.ResponseFlexPlaceEquipo;
import pe.com.qallarix.movistarcontigo.flexplace.myteam.pojos.ResponseReporteFlexMiEquipo;
import pe.com.qallarix.movistarcontigo.flexplace.myteam.pojos.Team;
import pe.com.qallarix.movistarcontigo.util.PermissionUtils;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTeamFlexPlaceActivity extends TranquiParentActivity {
    private Spinner spMonths, spYears;
    private RecyclerView rvList;
    private MyTeamFlexPlaceAdapter myTeamFlexPlaceAdapter;
    private Team currentTeam;
    private int checkMonths = 0, checkYears = 0;
    private RadioButton rbMondays, rbThuesdays, rbWednesdays, rbThursdays, rbFridays;
    private TextView tvTeamMessage;
    private TextView btGenerateReports;
    private int dia,mes,anio;
    AlertDialog alertDialog;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;

    //ViewLoader
    private TextView tvMensajeViewLoader;
    private View viewLoader;

    //emptyview
    private View emptyView;
    private TextView tvEmptyView;

    public final int MONDAY = 1;
    public final int THUESDAY = 2;
    public final int WEDNESDAY = 3;
    public final int THURSDAY = 4;
    public final int FRIDAY = 5;

    public final int MODULE_MYTEAM = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_myteam);
        binderVistas();
        configurarToolbar();
        configurarSpinnerMeses();
        configurarSpinnerAnios();
        configurarRbDia(rbMondays,MONDAY, rbThuesdays, rbWednesdays, rbThursdays, rbFridays);
        configurarRbDia(rbThuesdays,THUESDAY, rbMondays, rbWednesdays, rbThursdays, rbFridays);
        configurarRbDia(rbWednesdays,WEDNESDAY, rbThuesdays, rbMondays, rbThursdays, rbFridays);
        configurarRbDia(rbThursdays,THURSDAY, rbThuesdays, rbWednesdays, rbMondays, rbFridays);
        configurarRbDia(rbFridays,FRIDAY, rbThuesdays, rbWednesdays, rbThursdays, rbMondays);
        configurarRecycler();
        configurarFechaActual();
        cargarListaFlexPlaceEquipo();
        configurarBotonGenerarReporte();
    }

    private void configurarBotonGenerarReporte() {

        btGenerateReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        MyTeamFlexPlaceActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (PermissionUtils.neverAskAgainSelected(MyTeamFlexPlaceActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            displayNeverAskAgainDialog();
                        } else {
                            mostrarDialogNecesitamosPermisos();
                        }
                    }
                }else {
                    getReporteFromService();
                }
            }
        });
    }

    private void getReporteFromService() {
        tvMensajeViewLoader.setText("Generando reporte...");
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseReporteFlexMiEquipo> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceMyTeamApi.class)
                .getReporteFlexMiEquipo(mes,anio);
        call.enqueue(new Callback<ResponseReporteFlexMiEquipo>() {
            @Override
            public void onResponse(Call<ResponseReporteFlexMiEquipo> call,
                                   Response<ResponseReporteFlexMiEquipo> response) {
                if (response.code() == 200){
                    generarGuardarReporte(response.body().getFile(),response.body().getNameFile());
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseReporteFlexMiEquipo> call, Throwable t) {
                Toast.makeText(MyTeamFlexPlaceActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarFechaActual() {
        Calendar calendar = Calendar.getInstance();
        anio = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH) + 1;
        dia = 1;
        spMonths.setSelection(mes-1);
        String[] aniosFlex = getResources().getStringArray(R.array.anios_flex);
        List<String> anios = Arrays.asList(aniosFlex);
        int index = 0;
        index = anios.indexOf(String.valueOf(anio));
        spYears.setSelection(index);
        switch (dia){
            case MONDAY: rbMondays.setChecked(true);break;
            case THUESDAY: rbThuesdays.setChecked(true);break;
            case WEDNESDAY: rbWednesdays.setChecked(true);break;
            case THURSDAY: rbThursdays.setChecked(true);break;
            case FRIDAY: rbFridays.setChecked(true);break;
        }
    }

    private void configurarRecycler() {
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        myTeamFlexPlaceAdapter = new MyTeamFlexPlaceAdapter(this, new MyTeamFlexPlaceAdapter.FlexEquipoOnClick() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MyTeamFlexPlaceActivity.this,
                        ForApproveFlexRequestDetailActivity.class);
                FlexEquipo currentSolicitudFlex = null;

                List<FlexEquipo> flexEquipo = new ArrayList<>();
                switch (dia){
                    case MONDAY: flexEquipo = currentTeam.getUno();break;
                    case THUESDAY: flexEquipo = currentTeam.getDos();break;
                    case WEDNESDAY: flexEquipo = currentTeam.getTres();break;
                    case THURSDAY: flexEquipo = currentTeam.getCuatro();break;
                    case FRIDAY: flexEquipo = currentTeam.getCinco();break;
                }
                currentSolicitudFlex = flexEquipo.get(position);

                if (currentSolicitudFlex != null) {
                    int requestCode = (int)currentSolicitudFlex.getId();
                    intent.putExtra("requestCode",requestCode);
                    intent.putExtra("flagModule",MODULE_MYTEAM);
                    startActivity(intent);
                }
            }
        });
        rvList.setAdapter(myTeamFlexPlaceAdapter);
    }

    private void binderVistas() {
        spMonths = findViewById(R.id.flexplace_equipo_spMeses);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spMonths);

            // Set popupWindow height to 500px
            popupWindow.setHeight(560);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        spYears = findViewById(R.id.flexplace_equipo_spAnios);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spYears);

            // Set popupWindow height to 500px
            popupWindow.setHeight(560);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        rbMondays = findViewById(R.id.flexplace_equipo_rbLunes);
        rbThuesdays = findViewById(R.id.flexplace_equipo_rbMartes);
        rbWednesdays = findViewById(R.id.flexplace_equipo_rbMiercoles);
        rbThursdays = findViewById(R.id.flexplace_equipo_rbJueves);
        rbFridays = findViewById(R.id.flexplace_equipo_rbViernes);
        rvList = findViewById(R.id.flexplace_equipo_rvListaFlexEquipo);
        btGenerateReports = findViewById(R.id.flexplace_equipo_tvBotonGenerarReporte);

        tvMensajeViewLoader = findViewById(R.id.viewLoad_tvMensaje);
        viewLoader = findViewById(R.id.lista_solicitudes_viewProgress);
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);

        tvTeamMessage = findViewById(R.id.flexplace_equipo_mensajeEquipo);

        emptyView = findViewById(R.id.solicitudes_emptyview_view);
        tvEmptyView = findViewById(R.id.solicitudes_emptyview_text);
    }

    private void cargarListaFlexPlaceEquipo() {
        emptyView.setVisibility(View.GONE);
        viewMessage.setVisibility(View.GONE);
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseFlexPlaceEquipo> call = WebServiceFlexPlace
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceMyTeamApi.class)
                .getFlexPlaceMiEquipo(mes,anio);

        call.enqueue(new Callback<ResponseFlexPlaceEquipo>() {
            @Override
            public void onResponse(Call<ResponseFlexPlaceEquipo> call, Response<ResponseFlexPlaceEquipo> response) {
                if (response.code() == 200){
                    currentTeam = response.body().getTeam();
                    cargarLista();
                    long miembros = response.body().getTotal();
                    String text = "";
                    if (miembros == 1)
                        text = String.format(getResources().getString(R.string.flexplace_equipo_texto_numero_singular), miembros+"");
                    else
                        text = String.format(getResources().getString(R.string.flexplace_equipo_texto_numeros_plural), miembros+"");
                    CharSequence styledText = Html.fromHtml(text);
                    tvTeamMessage.setText(styledText);
                }else {
                    mostrarMensaje500();

                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseFlexPlaceEquipo> call, Throwable t) {
                mostrarMensaje500();
                viewLoader.setVisibility(View.GONE);
            }
        });
    }

    private void cargarLista() {
        List<FlexEquipo> flexEquipo = new ArrayList<>();
        switch (dia){
            case MONDAY: flexEquipo = currentTeam.getUno();break;
            case THUESDAY: flexEquipo = currentTeam.getDos();break;
            case WEDNESDAY: flexEquipo = currentTeam.getTres();break;
            case THURSDAY: flexEquipo = currentTeam.getCuatro();break;
            case FRIDAY: flexEquipo = currentTeam.getCinco();break;
        }
        myTeamFlexPlaceAdapter.setFlexPlaceMiEquipo(flexEquipo);
        if (flexEquipo.isEmpty())
            mostrarEmptyView();
    }

    private void configurarSpinnerAnios() {
        spYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (++checkYears > 1){
                    anio = Integer.parseInt(getResources().getStringArray(R.array.anios_flex)[spYears.getSelectedItemPosition()]);
                    cargarListaFlexPlaceEquipo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void configurarSpinnerMeses() {
        spMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (++checkMonths > 1){
                    mes = spMonths.getSelectedItemPosition() + 1;
                    cargarListaFlexPlaceEquipo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void configurarRbDia(RadioButton rb, final int valor,
                                 final RadioButton rbAux1, final RadioButton rbAux2,
                                 final RadioButton rbAux3, final RadioButton rbAux4) {
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dia = valor;
                    buttonView.setBackgroundResource(R.drawable.boton_vacaciones_verde);
                    buttonView.setTextAppearance (MyTeamFlexPlaceActivity.this,
                            R.style.dia_selected);
                    rbAux1.setChecked(false);rbAux2.setChecked(false);
                    rbAux3.setChecked(false);rbAux4.setChecked(false);
                    btGenerateReports.setVisibility(View.VISIBLE);
                    cargarListaFlexPlaceEquipo();
                }else{
                    buttonView.setBackgroundResource(R.drawable.borde_seleccion_calendario);
                    buttonView.setTextAppearance (MyTeamFlexPlaceActivity.this,
                            R.style.dia_unselected);
                }
            }
        });
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FlexPlace de mi equipo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                goToParentActivity();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    private void goToParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        startActivity(upIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }

    private void mostrarMensaje500() {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.img_error_servidor);
        tvMessageTitle.setText("¡Ups!");
        tvMessageMensaje.setText("Hubo un problema con el servidor. " +
                "Estamos trabajando para solucionarlo.");
        tvMessageBoton.setVisibility(View.VISIBLE);
        tvMessageBoton.setText("Cargar nuevamente");
        tvMessageBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                displayListaHistorial(tipoLista);
            }
        });
    }

    private void mostrarEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
    }

    public void clickNull(View view) {
    }

    public void mostrarDialogNecesitamosPermisos(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MyTeamFlexPlaceActivity.this);
        builder.setMessage("Para visualizar el archivo PDF, permite que Movistar Contigo pueda acceder al almacenamiento");
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
        Dexter.withActivity(MyTeamFlexPlaceActivity.this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener(){
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        getReporteFromService();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        PermissionUtils.setShouldShowStatus(MyTeamFlexPlaceActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }).check();
    }

    private void generarGuardarReporte(String stringFile, String nameFile) {
        byte[] excelAsBytes = Base64.decode(stringFile, 0);

        File file = new File(Environment.getExternalStorageDirectory()
                + "/" + nameFile);
//                            File nuevaCarpeta = new File(Environment.getExternalStorageDirectory(), "CONTIGO2018");
//                            nuevaCarpeta.mkdirs();
//                            File file = new File(nuevaCarpeta, "REPORT_FLEXPLACE_OCTUBRE.xlsx");
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file, true);
            os.write(excelAsBytes);
            os.flush();
            os.close();
            Toast.makeText(MyTeamFlexPlaceActivity.this, "Documento " +
                            nameFile + " guardado en el dispositivo",
                    Toast.LENGTH_SHORT).show();
            Analitycs.logEventoClickBotonReporteFlexPlace(MyTeamFlexPlaceActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayNeverAskAgainDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyTeamFlexPlaceActivity.this);
        builder.setMessage("Para guardar el reporte en el dispositivo, permite que podamos acceder " +
                "al almacenamiento"+
                "\nToca Ajustes > Permisos, y activa Almacenamiento");
        builder.setCancelable(false);
        builder.setPositiveButton("AJUSTES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("AHORA NO", null);
        builder.show();
    }


}
