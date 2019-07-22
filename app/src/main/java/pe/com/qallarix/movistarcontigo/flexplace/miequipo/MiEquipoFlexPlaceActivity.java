package pe.com.qallarix.movistarcontigo.flexplace.miequipo;

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
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.PermissionUtils;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiEquipoFlexPlaceActivity extends TranquiParentActivity {
    private Spinner spMeses, spAnios;
    private RecyclerView rvLista;
    private FlexPlaceEquipoAdapter flexPlaceEquipoAdapter;
    private Team currentTeam;
    private int checkMeses = 0,checkAnios = 0;
    private RadioButton rbLunes,rbMartes,rbMiercoles,rbJueves,rbViernes;
    private TextView tvBotonGenerarReporte;
    private int dia,mes,anio;
    AlertDialog alertDialog;

    //view message
    private View viewMessage;
    private TextView tvMessageTitle, tvMessageMensaje, tvMessageBoton;
    private ImageView ivMessageImagen;

    //ViewLoader
    private TextView tvMensajeViewLoader;
    private View viewLoader;


    public final int LUNES = 1;
    public final int MARTES = 2;
    public final int MIERCOLES = 3;
    public final int JUEVES = 4;
    public final int VIERNES = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_equipo_flex_place);
        binderVistas();
        configurarToolbar();
        configurarSpinnerMeses();
        configurarSpinnerAnios();
        configurarRbDia(rbLunes,1,rbMartes,rbMiercoles,rbJueves,rbViernes);
        configurarRbDia(rbMartes,2,rbLunes,rbMiercoles,rbJueves,rbViernes);
        configurarRbDia(rbMiercoles,3,rbMartes,rbLunes,rbJueves,rbViernes);
        configurarRbDia(rbJueves,4,rbMartes,rbMiercoles,rbLunes,rbViernes);
        configurarRbDia(rbViernes,5,rbMartes,rbMiercoles,rbJueves,rbLunes);
        configurarRecycler();
        configurarFechaActual();
        cargarListaFlexPlaceEquipo();
        configurarBotonGenerarReporte();
    }

    private void configurarBotonGenerarReporte() {

        tvBotonGenerarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        MiEquipoFlexPlaceActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (PermissionUtils.neverAskAgainSelected(MiEquipoFlexPlaceActivity.this,
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
        Call<ResponseReporteFlexMiEquipo> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceMiEquipoApi.class)
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
                Toast.makeText(MiEquipoFlexPlaceActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarFechaActual() {
        Calendar calendar = Calendar.getInstance();
        anio = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH) + 1;
        dia = 1;
        spMeses.setSelection(mes-1);
        String[] aniosFlex = getResources().getStringArray(R.array.anios_flex);
        List<String> anios = Arrays.asList(aniosFlex);
        int index = 0;
        index = anios.indexOf(String.valueOf(anio));
        spAnios.setSelection(index);
        switch (dia){
            case LUNES: rbLunes.setChecked(true);break;
            case MARTES: rbLunes.setChecked(true);break;
            case MIERCOLES: rbLunes.setChecked(true);break;
            case JUEVES: rbLunes.setChecked(true);break;
            case VIERNES: rbLunes.setChecked(true);break;
        }
    }

    private void configurarRecycler() {
        rvLista.setHasFixedSize(true);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        flexPlaceEquipoAdapter = new FlexPlaceEquipoAdapter(this, new FlexPlaceEquipoAdapter.FlexEquipoOnClick() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        rvLista.setAdapter(flexPlaceEquipoAdapter);
    }

    private void binderVistas() {
        spMeses = findViewById(R.id.flexplace_equipo_spMeses);
        spAnios = findViewById(R.id.flexplace_equipo_spAnios);
        rbLunes = findViewById(R.id.flexplace_equipo_rbLunes);
        rbMartes = findViewById(R.id.flexplace_equipo_rbMartes);
        rbMiercoles = findViewById(R.id.flexplace_equipo_rbMiercoles);
        rbJueves = findViewById(R.id.flexplace_equipo_rbJueves);
        rbViernes = findViewById(R.id.flexplace_equipo_rbViernes);
        rvLista = findViewById(R.id.flexplace_equipo_rvListaFlexEquipo);
        tvBotonGenerarReporte = findViewById(R.id.flexplace_equipo_tvBotonGenerarReporte);

        tvMensajeViewLoader = findViewById(R.id.viewLoad_tvMensaje);
        viewLoader = findViewById(R.id.lista_solicitudes_viewProgress);
        viewMessage = findViewById(R.id.view_message);
        tvMessageTitle = findViewById(R.id.view_message_tvTitle);
        tvMessageMensaje = findViewById(R.id.view_message_tvMensaje);
        ivMessageImagen = findViewById(R.id.view_message_ivImagen);
        tvMessageBoton = findViewById(R.id.view_message_tvBoton);
    }

    private void cargarListaFlexPlaceEquipo() {
        viewMessage.setVisibility(View.GONE);
        viewLoader.setVisibility(View.VISIBLE);
        Call<ResponseFlexPlaceEquipo> call = WebService3
                .getInstance(getDocumentNumber())
                .createService(ServiceFlexplaceMiEquipoApi.class)
                .getFlexPlaceMiEquipo(mes,anio);

        call.enqueue(new Callback<ResponseFlexPlaceEquipo>() {
            @Override
            public void onResponse(Call<ResponseFlexPlaceEquipo> call, Response<ResponseFlexPlaceEquipo> response) {
                if (response.code() == 200){
                    currentTeam = response.body().getTeam();
                    cargarLista();
                }else {
                    mostrarMensaje500();
                }
                viewLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseFlexPlaceEquipo> call, Throwable t) {
                mostrarMensaje500();
            }
        });
    }

    private void cargarLista() {
        List<FlexEquipo> flexEquipo = new ArrayList<>();
        switch (dia){
            case LUNES: flexEquipo = currentTeam.getUno();break;
            case MARTES: flexEquipo = currentTeam.getDos();break;
            case MIERCOLES: flexEquipo = currentTeam.getTres();break;
            case JUEVES: flexEquipo = currentTeam.getCuatro();break;
            case VIERNES: flexEquipo = currentTeam.getCinco();break;
        }
        flexPlaceEquipoAdapter.setFlexPlaceMiEquipo(flexEquipo);
        if (flexEquipo.isEmpty())
            mostrarEmptyView("","No tienes solicitudes FlexPlace para este periodo");
    }

    private void configurarSpinnerAnios() {
        spAnios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (++checkAnios > 1){
                    anio = Integer.parseInt(getResources().getStringArray(R.array.anios_flex)[spAnios.getSelectedItemPosition()]);
                    cargarListaFlexPlaceEquipo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void configurarSpinnerMeses() {
        spMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (++checkMeses > 1){
                    mes = spMeses.getSelectedItemPosition() + 1;
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
                    buttonView.setTextAppearance (MiEquipoFlexPlaceActivity.this,
                            R.style.dia_selected);
                    rbAux1.setChecked(false);rbAux2.setChecked(false);
                    rbAux3.setChecked(false);rbAux4.setChecked(false);
                    tvBotonGenerarReporte.setVisibility(View.VISIBLE);
                    cargarListaFlexPlaceEquipo();
                }else{
                    buttonView.setBackgroundResource(R.drawable.borde_seleccion_calendario);
                    buttonView.setTextAppearance (MiEquipoFlexPlaceActivity.this,
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
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(upIntent);
        finish();
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

    private void mostrarEmptyView(String title, String mensaje) {
        viewMessage.setVisibility(View.VISIBLE);
        ivMessageImagen.setImageResource(R.drawable.ic_empty_view_lista_vacaciones);
        tvMessageTitle.setText(title);
        tvMessageMensaje.setText(mensaje);
        tvMessageBoton.setVisibility(View.GONE);
    }

    public void clickNull(View view) {
    }

    public void mostrarDialogNecesitamosPermisos(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MiEquipoFlexPlaceActivity.this);
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
        Dexter.withActivity(MiEquipoFlexPlaceActivity.this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener(){
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        getReporteFromService();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        PermissionUtils.setShouldShowStatus(MiEquipoFlexPlaceActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
            Toast.makeText(MiEquipoFlexPlaceActivity.this, "Documento " +
                            nameFile + " guardado en el dispositivo",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayNeverAskAgainDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MiEquipoFlexPlaceActivity.this);
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
