package pe.com.qallarix.movistarcontigo.flexplace.registrar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.pojos.ResponseValidarFlexPlace;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarFlexPlaceActivity extends TranquiParentActivity {

    //Seleccion dia
    private View vSeleccionDia;
    private TextView tvFechaInicio;
    private TextView tvErrorDia;

    //seleccion mes y dia resultante
    private Spinner spPeriodo;
    private TextView tvDia;

    //muestra informacion lider y boton registrar
    private TextView tvLiderAprobacion;
    private TextView tvButtonRegistrar;

    //View de progreso
    private View vValidarFechas;

    //View dia elegido
    private View vDiaElegido;

    //variables para almacenar fecha inicio ,fecha fin, periodo y dia elegido
    private String fechaInicio;
    private int diaElegido, monthTaked;

    //variables para la data del extra
    private String leadership;


    private final int LUNES = 1;
    private final int MARTES = 2;
    private final int MIERCOLES = 3;
    private final int JUEVES = 4;
    private final int VIERNES = 5;
    private final int SABADO = 6;
    private final int DOMINGO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_flex_place);
        getDataFromExtras();
        configurarToolbar();
        bindearVistas();
        configurarBotonSeleccionFecha();
        configurarSpinnerPeriodo();
        configurarBotonRegistrar();
    }

    private void getDataFromExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null && bundle.containsKey("leadership")){
            leadership = bundle.getString("leadership","Jóse Eduardo Mendoza");
        }
    }

    private void configurarSpinnerPeriodo() {
        spPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //si el dia seleccionado es correcto y se selecciono periodo, habilita boton registrar
                if (position > 0){
                    monthTaked = position + 2;
                    if (diaSeleccionadoCorrecto()){
                        displayInformacionAprobacion();
                        habilitarBotonRegistrar();
                    }else{
                        inhabilitarBotonRegistrar();
                        ocultarInformacionAprobacion();
                    }
                }else{
                    inhabilitarBotonRegistrar();
                    ocultarInformacionAprobacion();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void bindearVistas() {
        tvFechaInicio = findViewById(R.id.registro_flex_tvFechaInicio);
        vSeleccionDia = findViewById(R.id.registro_flex_vSeleccionDia);
        tvErrorDia = findViewById(R.id.registro_flex_tvErrorDia);
        spPeriodo = findViewById(R.id.registro_flex_spPeriodo);
        tvDia = findViewById(R.id.registro_flex_tvDia);
        tvLiderAprobacion = findViewById(R.id.registro_flex_tvDescripcionLider);
        tvButtonRegistrar = findViewById(R.id.registro_flex_tvButtonRegistrar);
        vValidarFechas = findViewById(R.id.validar_fechas_viewProgress);
        vDiaElegido = findViewById(R.id.registro_flex_viewDiaElegido);
    }

    private Date obtenerFechaActual(){
        return Calendar.getInstance().getTime();
    }

    private void configurarBotonSeleccionFecha() {

        vSeleccionDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateSetter = tvFechaInicio.getText().toString();
                //obtenemos un date inciial para empezar el calendar
                Date dateInit = obtenerFechaActual();
                //Si se tiene fecha a setear, se transforma y setea sino se mantiene el date actual
                if (!dateSetter.equals("dd/mm/aaaa")){
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try{
                        dateInit = dateFormat.parse(dateSetter);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                int year = dateInit.getYear() + 1900;
                int mes = dateInit.getMonth();
                int dia = dateInit.getDate();

                //mostramos calendario para elegir el día flex
                DatePickerDialog recogerFecha =
                        new DatePickerDialog(RegistrarFlexPlaceActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //obtenemos la fecha elegida en el calendario
                        final int mesActual = month + 1;
                        String diaFormateado = (dayOfMonth < 10)? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        String mesFormateado = (mesActual < 10)? "0" + mesActual : String.valueOf(mesActual);
                        fechaInicio = diaFormateado + "/" + mesFormateado + "/" + year;
                        //Setemos la fecha obtenida del calendario en el TextView FechaInicio
                        tvFechaInicio.setText(fechaInicio);
                        try {
                            //Obtenemos la fecha del calendar
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateInicial = dateFormat.parse(fechaInicio);
                            //Obtenemos el numero de día
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(dateInicial);
                            diaElegido = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                            //mostramos día elegido
                            displayDiaElegido();
                            //El número de día no debe ser sabado(7) ni domingo(1)
                            if (diaSeleccionadoCorrecto()){
                                ocultarMensajeErrorSeleccionFecha();
                                //Si ya se seleccionó el periodo, se habilita el boton registrar
                                if (spPeriodo.getSelectedItemPosition() > 0){
                                    //mostramos la información de lider y fecha máxima de aprobación
                                    displayInformacionAprobacion();
                                    //habilitamos boton registrar
                                    habilitarBotonRegistrar();
                                }
                            }else{
                                ocultarInformacionAprobacion();
                                inhabilitarBotonRegistrar();
                                mostrarMensajeErrorSeleccionFecha();
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, year, mes, dia);
                //Obtenemos fecha actual
                Date currentDate = obtenerFechaActual();
                //La fecha mínima en calendario, debe ser la fecha mostrada en el textview
                //o en su defecto el día actual
                recogerFecha.getDatePicker().setMinDate(currentDate.getTime());
                //La fecha máxima en calendario, debe ser un año mas de la fecha inicial
                recogerFecha.getDatePicker().setMaxDate(sumarAnioAFecha(currentDate).getTime());
                recogerFecha.show();
            }
        });
    }

    private String obtenerFechaFin(String fechaInicial,int mesesATomar) {
        try{
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateInicial = dateFormat.parse(fechaInicial);
            Date dateFin = sumarMesesAFecha(dateInicial,mesesATomar);
            return dateFormat.format(dateFin);
        }catch (Exception e){
            return "";
        }
    }

    private boolean diaSeleccionadoCorrecto() {
        return diaElegido != SABADO && diaElegido != DOMINGO;
    }

    private void habilitarBotonRegistrar() {
        tvButtonRegistrar.setEnabled(true);
        tvButtonRegistrar.setBackgroundResource(R.drawable.boton_vacaciones_verde);
    }

    private void inhabilitarBotonRegistrar() {
        tvButtonRegistrar.setEnabled(false);
        tvButtonRegistrar.setBackgroundResource(R.drawable.boton_verde_disabled);
    }

    private void ocultarMensajeErrorSeleccionFecha() {
        tvErrorDia.setVisibility(View.INVISIBLE);
    }

    private void mostrarMensajeErrorSeleccionFecha() {
        tvErrorDia.setVisibility(View.VISIBLE);
    }

    private void displayInformacionAprobacion() {
        tvLiderAprobacion.setVisibility(View.VISIBLE);
        tvLiderAprobacion.setText("Tus " + tvDia.getText().toString() +
                " Flex serán aprobados por " + leadership + " y finalizan el " +
                obtenerFechaFin(fechaInicio,monthTaked)+ ".");
    }

    private void ocultarInformacionAprobacion(){
        tvLiderAprobacion.setVisibility(View.INVISIBLE);
    }

    private void displayDiaElegido() {
        vDiaElegido.setVisibility(View.VISIBLE);
        switch (diaElegido){
            case LUNES: tvDia.setText("Lunes");break;
            case MARTES: tvDia.setText("Martes");break;
            case MIERCOLES: tvDia.setText("Miércoles");break;
            case JUEVES: tvDia.setText("Jueves");break;
            case VIERNES: tvDia.setText("Viernes");break;
            case SABADO: tvDia.setText("Sábado");break;
            case DOMINGO: tvDia.setText("Domingo");break;
        }
    }


    private void configurarBotonRegistrar() {
        tvButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseValidarFlexPlace> call = WebServiceFlexPlace
                        .getInstance(getDocumentNumber())
                        .createService(ServiceFlexplaceRegistrarApi.class)
                        .validarFlexPlace(fechaInicio,monthTaked);
                vValidarFechas.setVisibility(View.VISIBLE);
                call.enqueue(new Callback<ResponseValidarFlexPlace>() {
                    @Override
                    public void onResponse(Call<ResponseValidarFlexPlace> call,
                                           Response<ResponseValidarFlexPlace> response) {
                        vValidarFechas.setVisibility(View.GONE);
                        if (response.code()==200){
                            mostrarDialogAprobacionFechas(response.body().getTitle(),
                                    response.body().getDetail());
                        }else if (response.code() == 400){
                            mostrarDialog400(response);
                        }else mostrarDialogError();

                    }

                    @Override
                    public void onFailure(Call<ResponseValidarFlexPlace> call, Throwable t) {
                        Toast.makeText(RegistrarFlexPlaceActivity.this,
                                "El servidor no esta disponible en estos momentos",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void mostrarDialog400(Response<ResponseValidarFlexPlace> response) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarFlexPlaceActivity.this,R.style.DialogMensajeStyle);
        builder.setTitle("¡Ups!");
        builder.setMessage("Hubo un problema con el servidor. Estamos trabajando para solucionarlo.");
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("exception");
            String m = jsonObject1.getString("exceptionMessage");
            builder.setMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setMessage("Hubo un problema con el servidor. Estamos trabajando para solucionarlo.");
        }
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.dismiss(); }
        });
        final AlertDialog alertDialog = builder.create();
        if (!isFinishing()) alertDialog.show();
    }

    private void mostrarDialogError() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarFlexPlaceActivity.this,R.style.DialogMensajeStyle);
        builder.setTitle("¡Ups!");
        builder.setMessage("Hubo un problema con el servidor. Estamos trabajando para solucionarlo.");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.dismiss(); }
        });
        final AlertDialog alertDialog = builder.create();
        if (!isFinishing()) alertDialog.show();
    }



    public void mostrarDialogAprobacionFechas(String title, String mensaje){
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarFlexPlaceActivity.this,R.style.DialogMensajeStyle);
        builder.setTitle(title);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(RegistrarFlexPlaceActivity.this, FinalizarRegistroFlexActivity.class);
                intent.putExtra("fechaInicio",fechaInicio);
                intent.putExtra("monthTaked",monthTaked);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Ahora no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        if (!isFinishing()) alertDialog.show();
    }


    public static Date sumarAnioAFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, 12);
        return calendar.getTime();
    }

    public static Date sumarMesesAFecha(Date fecha,int meses){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, meses);
        return calendar.getTime();
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro de FlexPlace");
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

    public void clickNull(View view) {
    }

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }
}
