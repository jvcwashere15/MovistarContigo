package pe.com.qallarix.movistarcontigo.vacaciones.registro;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.ResponseValidarFechas;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.ServiceRegistrarVacacionesApi;
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
            tvMensaje;
    private TextView tvButtonRegistar;

    private String fechaInicio = "", fechaFin = "";


    private final int INICIO = 1;
    private final int FIN = 2;

    private long mDia = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_vacaciones);
        configurarToolbar();
        bindearVistas();
        configurarBotonCalendarioInicio();
        configurarBotonCalendarioFin();
        configurarBotonRegistrar();
    }

    private void configurarBotonRegistrar() {
        tvButtonRegistar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Call<ResponseValidarFechas> call = WebService2
                        .getInstance(getDocumentNumber())
                        .createService(ServiceRegistrarVacacionesApi.class)
                        .validarEntreFechas(getCIP(),fechaInicio,fechaFin);
                call.enqueue(new Callback<ResponseValidarFechas>() {
                    @Override
                    public void onResponse(Call<ResponseValidarFechas> call, Response<ResponseValidarFechas> response) {
                        if (response.code() == 200){
                            Validation validation = response.body().getValidation();
                            mostrarDialogAprobacionFechas(validation.getObservation(),"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseValidarFechas> call, Throwable t) {
                        Toast.makeText(RegistroVacacionesActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void mostrarDialogAprobacionFechas(String title,String fechaMaxAprobacion){
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistroVacacionesActivity.this);
        builder.setTitle(title);
        builder.setMessage("La fecha máxima para que se apruebe tu solicitud es hasta el " + fechaMaxAprobacion);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(RegistroVacacionesActivity.this,FinalizarRegistroActivity.class);
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

    private void obtenerFecha(final int flag){
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
        }, 2019, 6, 6);
        //Muestro el widget
        recogerFecha.show();
    }

    private void mostrarMensajeFechas() {
        if (mDia > 0){
            tvButtonRegistar.setVisibility(View.VISIBLE);
            tvMensaje.setTextColor(Color.BLACK);
            tvMensaje.setText("Días solicitados:");
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
    }


}
