package pe.com.qallarix.movistarcontigo.vacaciones.registro;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebServiceVacaciones;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.ResponseValidarFechas;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.Validation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroVacacionesActivity extends TranquiParentActivity {

    private View
            vCalendarioInicio,
            vCalendarioFin;
    private TextView
            tvFechaInicio,
            tvFechaFin,
            tvNumeroDias,
            tvLiderAprobacion,
            tvMensaje;
    private TextView tvButtonRegistar;
    private View viewValidarFechas;
    private String fechaInicio = "", fechaFin = "", leaderName = "";

    private final int INICIO = 1;
    private final int FIN = 2;

    private long mDia = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_vacaciones);
        configurarToolbar();
        getDataFromExtras();
        bindearVistas();
        configurarBotonCalendarioInicio();
        configurarBotonCalendarioFin();
        configurarBotonRegistrar();
    }

    private void getDataFromExtras() {
        leaderName = getIntent().getExtras().getString("leadershipName","");
    }

    private void configurarBotonRegistrar() {
        tvButtonRegistar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Call<ResponseValidarFechas> call = WebServiceVacaciones
                        .getInstance(getDocumentNumber())
                        .createService(ServiceRegistrarVacacionesApi.class)
                        .validarEntreFechas(getCIP(),fechaInicio,fechaFin);
                viewValidarFechas.setVisibility(View.VISIBLE);
                call.enqueue(new Callback<ResponseValidarFechas>() {
                    @Override
                    public void onResponse(Call<ResponseValidarFechas> call, Response<ResponseValidarFechas> response) {
                        viewValidarFechas.setVisibility(View.GONE);
                        int responseCode = response.code();
                        if ( responseCode == 200){
                            Validation validation = response.body().getValidation();
                            mostrarDialogAprobacionFechas(validation.getObservation());
                        }else if (responseCode == 500 || responseCode == 404){
                            mostrarDialogError();
                        }else mostrarDialog400(response);
                    }

                    @Override
                    public void onFailure(Call<ResponseValidarFechas> call, Throwable t) {
                        viewValidarFechas.setVisibility(View.GONE);
                        Toast.makeText(RegistroVacacionesActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void mostrarDialog400(Response<ResponseValidarFechas> response) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistroVacacionesActivity.this);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistroVacacionesActivity.this);
        builder.setTitle("¡Ups!");
        builder.setMessage("Hubo un problema con el servidor. Estamos trabajando para solucionarlo.");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.dismiss(); }
        });
        final AlertDialog alertDialog = builder.create();
        if (!isFinishing()) alertDialog.show();
    }

    public void mostrarDialogAprobacionFechas(String title){
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistroVacacionesActivity.this);
        builder.setTitle(title);
        builder.setMessage("Tu solicitud será aprobada en un plazo no mayor a 3 días hábiles.");
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(RegistroVacacionesActivity.this,FinalizarRegistroActivity.class);
                intent.putExtra("fecha_inicio",fechaInicio);
                intent.putExtra("fecha_fin",fechaFin);
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


    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro de vacaciones");
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

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }

    private void configurarBotonCalendarioFin() {
        vCalendarioFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(FIN);
            }
        });
    }

    private void configurarBotonCalendarioInicio() {
        vCalendarioInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(INICIO);
            }
        });
    }

    private Date obtenerFechaActual(){
        return Calendar.getInstance().getTime();
    }

    private void obtenerFecha(final int flag){
        String dateSetter = "";
        if (flag == INICIO) dateSetter = tvFechaInicio.getText().toString();
        else dateSetter = tvFechaFin.getText().toString();

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




        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? "0" +
                        String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? "0" +
                        String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                if (flag == INICIO){
                    fechaInicio = diaFormateado + "/" + mesFormateado + "/" + year;
                    tvFechaInicio.setText(fechaInicio);
                    if (!TextUtils.isEmpty(fechaFin)){
                        mDia = obtenerNumeroDeDias();
                        mostrarMensajeFechas();
                    }
                } else{
                    fechaFin = diaFormateado + "/" + mesFormateado + "/" + year;
                    tvFechaFin.setText(fechaFin);
                    if (!TextUtils.isEmpty(fechaInicio)){
                        mDia = obtenerNumeroDeDias();
                        mostrarMensajeFechas();
                    }
                }
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, year, mes, dia);
        //Muestro el widget
        Date currentDate = obtenerFechaActual();
        recogerFecha.getDatePicker().setMinDate(currentDate.getTime());
        recogerFecha.show();
    }

    private void mostrarMensajeFechas() {
        if (mDia > 0){
            tvButtonRegistar.setVisibility(View.VISIBLE);
            tvMensaje.setTextColor(Color.BLACK);
            tvMensaje.setText("Días solicitados:");
            tvLiderAprobacion.setText("Tus vacaciones serán aprobadas por " + leaderName);
            tvLiderAprobacion.setVisibility(View.VISIBLE);
            tvNumeroDias.setVisibility(View.VISIBLE);
            if (mDia > 1) tvNumeroDias.setText(String.valueOf(mDia) + " días");
            else tvNumeroDias.setText(String.valueOf(mDia) + " día");
            tvButtonRegistar.setVisibility(View.VISIBLE);
        }else{
            tvButtonRegistar.setVisibility(View.INVISIBLE);
            tvNumeroDias.setVisibility(View.GONE);
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.setTextColor(Color.RED);
            tvMensaje.setText("El rango de fechas seleccionado es incorrecto");
        }
    }


    public long obtenerNumeroDeDias(){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = myFormat.parse(fechaInicio);
            Date date2 = myFormat.parse(fechaFin);
            long diff = date2.getTime() - date1.getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void bindearVistas() {
        vCalendarioInicio = findViewById(R.id.vacaciones_vCalendarioInicio);
        vCalendarioFin = findViewById(R.id.vacaciones_vCalendarioFin);
        tvFechaInicio= findViewById(R.id.registro_vacaciones_tvFechaInicio);
        tvFechaFin = findViewById(R.id.registro_vacaciones_tvFechaFin);
        tvNumeroDias = findViewById(R.id.registrar_vacaciones_tvDiasSolicitados);
        tvMensaje = findViewById(R.id.registrar_vacaciones_tvMensaje);
        tvButtonRegistar = findViewById(R.id.registrar_vacaciones_btRegistrar);
        viewValidarFechas = findViewById(R.id.validar_fechas_viewProgress);
        tvLiderAprobacion = findViewById(R.id.registrar_vacaciones_tvLiderAprobacion);
    }


}
