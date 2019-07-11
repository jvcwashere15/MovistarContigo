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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.FinalizarRegistroActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.RegistroVacacionesActivity;

public class RegistrarFlexPlaceActivity extends AppCompatActivity {
    private TextView tvFechaInicio, tvFechaFin;
    private View vCalendarioInicio, vCalendarioFin;
    private TextView tvButtonRegistrar, tvLiderAprobacion, tvAvisoMeses;
    private String fechaInicio , fechaFin;
    private View viewSeleccionDias;
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
                mostrarDialogAprobacionFechas("¿Deseas continuar?",
                        "La fecha máxima para que se apruebe tu solicitud es hasta el 30/06/2019");
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

    private void obtenerFecha(final int flag, final Date date){
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
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
                        configurarBotonCalendarioFin(minimumDate);
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
        recogerFecha.getDatePicker().setMinDate(date.getTime());
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

    private void bindearVistas() {
        vCalendarioInicio = findViewById(R.id.flexplace_vCalendarioInicio);
        vCalendarioFin = findViewById(R.id.flexplace_vCalendarioFin);
        tvFechaInicio = findViewById(R.id.registro_flex_tvFechaInicio);
        tvFechaFin = findViewById(R.id.registro_flex_tvFechaFin);
        tvAvisoMeses = findViewById(R.id.registrar_flex_tvAvisoMesesMinimo);
        tvLiderAprobacion = findViewById(R.id.registrar_flex_tvLider);
        tvButtonRegistrar = findViewById(R.id.registrar_flex_tvButtonRegistrar);
        viewSeleccionDias = findViewById(R.id.view_seleccion_dias);
        rb1 = findViewById(R.id.registrar_flex_dia1);
        rb2 = findViewById(R.id.registrar_flex_dia2);
        rb3 = findViewById(R.id.registrar_flex_dia3);
        rb4 = findViewById(R.id.registrar_flex_dia4);
        rb5 = findViewById(R.id.registrar_flex_dia5);
    }

    private void configurarBotonCalendarioFin(final Date date) {
        vCalendarioFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(FIN,date);
            }
        });
    }

    private void configurarBotonCalendarioInicio(final Date date) {
        vCalendarioInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(INICIO, date);
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
