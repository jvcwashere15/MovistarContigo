package pe.com.qallarix.movistarcontigo.flexplace.registrar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.pojos.ResponseValidarFlexPlace;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService3;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.FinalizarRegistroActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.RegistroVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.ResponseValidarFechas;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarFlexPlaceActivity extends TranquiParentActivity {
    private TextView tvFechaInicio, tvFechaFin;
    private View vCalendarioInicio, vCalendarioFin;
    private TextView tvButtonRegistrar, tvLiderAprobacion, tvAvisoMeses;
    private String fechaInicio , fechaFin;
    private View viewSeleccionDias,viewValidarFechas;
    private final int INICIO = 0,FIN = 1;
    private int dia;
    private RadioButton rb1,rb2,rb3,rb4,rb5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_flex_place);
        configurarToolbar();
        bindearVistas();
        cargarFechaInicioFromService();
        configurarRbDia(rb1,1,rb2,rb3,rb4,rb5);
        configurarRbDia(rb2,2,rb1,rb3,rb4,rb5);
        configurarRbDia(rb3,3,rb2,rb1,rb4,rb5);
        configurarRbDia(rb4,4,rb2,rb3,rb1,rb5);
        configurarRbDia(rb5,5,rb2,rb3,rb4,rb1);
        configurarBotonRegistrar();
    }

    private void configurarBotonRegistrar() {
        tvButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseValidarFlexPlace> call = WebService3
                        .getInstance(getDocumentNumber())
                        .createService(ServiceFlexplaceRegistrarApi.class)
                        .validarFlexPlace(fechaInicio,fechaFin,dia);
                viewValidarFechas.setVisibility(View.VISIBLE);
                call.enqueue(new Callback<ResponseValidarFlexPlace>() {
                    @Override
                    public void onResponse(Call<ResponseValidarFlexPlace> call,
                                           Response<ResponseValidarFlexPlace> response) {
                        viewValidarFechas.setVisibility(View.GONE);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarFlexPlaceActivity.this);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarFlexPlaceActivity.this);
        builder.setTitle("¡Ups!");
        builder.setMessage("Hubo un problema con el servidor. Estamos trabajando para solucionarlo.");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.dismiss(); }
        });
        final AlertDialog alertDialog = builder.create();
        if (!isFinishing()) alertDialog.show();
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
                    buttonView.setTextAppearance (RegistrarFlexPlaceActivity.this, R.style.dia_selected);
                    rbAux1.setChecked(false);rbAux2.setChecked(false);
                    rbAux3.setChecked(false);rbAux4.setChecked(false);
                    tvLiderAprobacion.setVisibility(View.VISIBLE);
                    tvButtonRegistrar.setVisibility(View.VISIBLE);
                }else{
                    buttonView.setBackgroundResource(R.drawable.borde_seleccion_calendario);
                    buttonView.setTextAppearance (RegistrarFlexPlaceActivity.this, R.style.dia_unselected);

                }
            }
        });
    }



    private void cargarFechaInicioFromService() {
        Date dateInicial = Calendar.getInstance().getTime();
        configurarBotonCalendarioInicio(dateInicial);
    }

    public void mostrarDialogAprobacionFechas(String title, String mensaje){
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarFlexPlaceActivity.this);
        builder.setTitle(title);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(RegistrarFlexPlaceActivity.this, FinalizarRegistroFlexActivity.class);
                intent.putExtra("fecha_inicio",fechaInicio);
                intent.putExtra("fecha_fin",fechaFin);
                intent.putExtra("dia",dia);
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

    private void obtenerFecha(final String fechaActual, final int flag, final Date dateStart, final Date dateEnd){
        Date currentDate;
        Calendar cal = Calendar.getInstance();
        currentDate = cal.getTime();

        if (!fechaActual.equals("dd/mm/aaaa")){
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try{
                Date dateInicial = dateFormat.parse(fechaActual);
                currentDate = dateInicial;
            }catch (Exception e){ }
        }

        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                if (flag == INICIO){
                    fechaInicio = diaFormateado + "/" + mesFormateado + "/" + year;
                    tvFechaInicio.setText(fechaInicio);
                    try {
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date dateInicial = dateFormat.parse(fechaInicio);
                        Date minimumDate = sumarMesesAFecha(dateInicial);
                        Date maximumDate = sumarAnioAFecha(dateInicial);
                        configurarBotonCalendarioFin(minimumDate,maximumDate);
                        String strDateFin = dateFormat.format(minimumDate);
                        tvFechaFin.setText(strDateFin);
                        fechaFin = strDateFin;
                        tvAvisoMeses.setVisibility(View.VISIBLE);
                        cleanSeleccionDias();
                        tvLiderAprobacion.setVisibility(View.INVISIBLE);
                        tvButtonRegistrar.setVisibility(View.INVISIBLE);
                        viewSeleccionDias.setVisibility(View.VISIBLE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else{
                    fechaFin = diaFormateado + "/" + mesFormateado + "/" + year;
                    tvFechaFin.setText(fechaFin);
                    tvAvisoMeses.setVisibility(View.INVISIBLE);
                    tvLiderAprobacion.setVisibility(View.INVISIBLE);
                    tvButtonRegistrar.setVisibility(View.INVISIBLE);
                    cleanSeleccionDias();
                }
            }
        }, currentDate.getYear(), currentDate.getMonth(), currentDate.getDay());
        recogerFecha.getDatePicker().setMinDate(dateStart.getTime());
        if (dateEnd != null) recogerFecha.getDatePicker().setMaxDate(dateEnd.getTime());
        recogerFecha.show();
    }

    private void cleanSeleccionDias() {
        dia = 0;
        rb1.setChecked(false);
        rb2.setChecked(false);
        rb3.setChecked(false);
        rb4.setChecked(false);
        rb5.setChecked(false);
    }

    public static Date sumarMesesAFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, 3);
        return calendar.getTime();
    }

    public static Date sumarAnioAFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, 12);
        return calendar.getTime();
    }

    private void bindearVistas() {
        vCalendarioInicio = findViewById(R.id.flexplace_vCalendarioInicio);
        vCalendarioFin = findViewById(R.id.flexplace_vCalendarioFin);
        tvFechaInicio = findViewById(R.id.registro_flex_tvFechaInicio);
        tvFechaFin = findViewById(R.id.registro_flex_tvFechaFin);
        tvAvisoMeses = findViewById(R.id.registrar_flex_tvAvisoMesesMinimo);
        tvLiderAprobacion = findViewById(R.id.registrar_flex_tvLider);
        tvButtonRegistrar = findViewById(R.id.registrar_flex_tvButtonRegistrar);
        viewSeleccionDias = findViewById(R.id.view_seleccion_dias);
        viewValidarFechas = findViewById(R.id.validar_fechas_viewProgress);
        rb1 = findViewById(R.id.registrar_flex_dia1);
        rb2 = findViewById(R.id.registrar_flex_dia2);
        rb3 = findViewById(R.id.registrar_flex_dia3);
        rb4 = findViewById(R.id.registrar_flex_dia4);
        rb5 = findViewById(R.id.registrar_flex_dia5);
    }



    private void configurarBotonCalendarioInicio(final Date dateStart) {
        vCalendarioInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(tvFechaInicio.getText().toString(),INICIO, dateStart,null);
            }
        });
    }

    private void configurarBotonCalendarioFin(final Date dateStart,final Date dateEnd) {
        vCalendarioFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(tvFechaFin.getText().toString(),FIN,dateStart,dateEnd);
            }
        });
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

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }
}
