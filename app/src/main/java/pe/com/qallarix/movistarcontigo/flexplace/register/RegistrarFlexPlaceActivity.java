package pe.com.qallarix.movistarcontigo.flexplace.register;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.register.pojos.ResponseValidarFlexPlace;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarFlexPlaceActivity extends TranquiParentActivity {

    //Seleccion dia
    private View vDaySelection;
    private TextView tvStartDate;
    private TextView tvDayError;

    //seleccion mes y dia resultante
    private Spinner spPeriod;
    private TextView tvDay;

    //muestra informacion lider y boton registrar
    private TextView tvLeaderApprove;
    private TextView tvButtonRegister;

    //View de progreso
    private View vValidateDates;

    //View dia elegido
    private View vDaySelected;

    //variables para almacenar fecha inicio ,fecha fin, periodo y dia elegido
    private String startDate, dayFlexplace;
    private int daySelected, monthTaked;

    //variables para la data del extra
    private String leader = "";


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
        setContentView(R.layout.activity_flexplace_register);
        getDataFromExtras();
        setUpToolbar();
        bindViews();
        setUpButtonDaySelection();
        setUpSpinnerPeriod();
        setUpRegisterFlexplaceButton();
    }

    private void getDataFromExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null && bundle.containsKey("leader")){
            leader = bundle.getString("leader","Jóse Eduardo Mendoza");
        }
    }

    private void setUpSpinnerPeriod() {
        spPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void bindViews() {
        tvStartDate = findViewById(R.id.registro_flex_tvFechaInicio);
        vDaySelection = findViewById(R.id.registro_flex_vSeleccionDia);
        tvDayError = findViewById(R.id.registro_flex_tvErrorDia);
        spPeriod = findViewById(R.id.registro_flex_spPeriodo);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow =
                    (android.widget.ListPopupWindow) popup.get(spPeriod);
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (48 * scale + 0.5f);
            // Set popupWindow height to 500px
            popupWindow.setHeight(pixels * 5);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        tvDay = findViewById(R.id.registro_flex_tvDia);
        tvLeaderApprove = findViewById(R.id.registro_flex_tvDescripcionLider);
        tvButtonRegister = findViewById(R.id.registro_flex_tvButtonRegistrar);
        vValidateDates = findViewById(R.id.validar_fechas_viewProgress);
        vDaySelected = findViewById(R.id.registro_flex_viewDiaElegido);
    }

    private Date obtenerFechaActual(){
        return Calendar.getInstance().getTime();
    }

    private void setUpButtonDaySelection() {

        vDaySelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateSetter = tvStartDate.getText().toString();
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
                                R.style.DatePickerDialogTheme,
                                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //obtenemos la fecha elegida en el calendario
                        final int mesActual = month + 1;
                        String diaFormateado = (dayOfMonth < 10)? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        String mesFormateado = (mesActual < 10)? "0" + mesActual : String.valueOf(mesActual);
                        startDate = diaFormateado + "/" + mesFormateado + "/" + year;
                        //Setemos la fecha obtenida del calendario en el TextView FechaInicio
                        tvStartDate.setText(startDate);
                        try {
                            //Obtenemos la fecha del calendar
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateInicial = dateFormat.parse(startDate);
                            //Obtenemos el numero de día
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(dateInicial);
                            daySelected = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                            //mostramos día elegido
                            displayDiaElegido();
                            //El número de día no debe ser sabado(7) ni domingo(1)
                            if (diaSeleccionadoCorrecto()){
                                ocultarMensajeErrorSeleccionFecha();
                                //Si ya se seleccionó el periodo, se habilita el boton registrar
                                if (spPeriod.getSelectedItemPosition() > 0){
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
        return daySelected != SABADO && daySelected != DOMINGO;
    }

    private void habilitarBotonRegistrar() {
        tvButtonRegister.setEnabled(true);
        tvButtonRegister.setBackgroundResource(R.drawable.boton_vacaciones_verde);
    }

    private void inhabilitarBotonRegistrar() {
        tvButtonRegister.setEnabled(false);
        tvButtonRegister.setBackgroundResource(R.drawable.boton_verde_disabled);
    }

    private void ocultarMensajeErrorSeleccionFecha() {
        tvDayError.setVisibility(View.INVISIBLE);
    }

    private void mostrarMensajeErrorSeleccionFecha() {
        tvDayError.setVisibility(View.VISIBLE);
    }

    private void displayInformacionAprobacion() {
        tvLeaderApprove.setVisibility(View.VISIBLE);
        CharSequence infoApprove = TranquiParentActivity.normal(
                "Tus "+ tvDay.getText().toString() +
                        " Flex serán aprobados por " , TranquiParentActivity.bold(leader) , " y finalizan el " +
                        obtenerFechaFin(startDate,monthTaked)+ "."
        );
        tvLeaderApprove.setText(infoApprove);
    }

    private void ocultarInformacionAprobacion(){
        tvLeaderApprove.setVisibility(View.INVISIBLE);
    }

    private void displayDiaElegido() {
        vDaySelected.setVisibility(View.VISIBLE);
        switch (daySelected){
            case LUNES: dayFlexplace = "Lunes";break;
            case MARTES: dayFlexplace = "Martes";break;
            case MIERCOLES: dayFlexplace = "Miércoles";break;
            case JUEVES: dayFlexplace = "Jueves";break;
            case VIERNES: dayFlexplace = "Viernes";break;
            case SABADO: dayFlexplace = "Sábado";break;
            case DOMINGO: dayFlexplace = "Domingo";break;
        }
        tvDay.setText(dayFlexplace);
    }


    private void setUpRegisterFlexplaceButton() {
        tvButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseValidarFlexPlace> call = WebServiceFlexPlace
                        .getInstance(getDocumentNumber())
                        .createService(ServiceFlexplaceRegistrarApi.class)
                        .validarFlexPlace(startDate,monthTaked);
                vValidateDates.setVisibility(View.VISIBLE);
                call.enqueue(new Callback<ResponseValidarFlexPlace>() {
                    @Override
                    public void onResponse(Call<ResponseValidarFlexPlace> call,
                                           Response<ResponseValidarFlexPlace> response) {
                        vValidateDates.setVisibility(View.GONE);
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
                intent.putExtra("startDate", startDate);
                intent.putExtra("monthTaked", monthTaked);
                intent.putExtra("dayFlexplace", dayFlexplace);
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

    public void setUpToolbar(){
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
