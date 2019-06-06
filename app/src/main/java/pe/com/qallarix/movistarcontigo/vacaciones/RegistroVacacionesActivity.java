package pe.com.qallarix.movistarcontigo.vacaciones;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import pe.com.qallarix.movistarcontigo.R;

public class RegistroVacacionesActivity extends AppCompatActivity {

    private View
            vCalendarioInicio,
            vCalendarioFin;
    private TextView
            tvFechaInicio,
            tvFechaFin;
    private final int INICIO = 1;
    private final int FIN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_vacaciones);
        configurarToolbar();
        bindearVistas();
        configurarBotonCalendarioInicio();
        configurarBotonCalendarioFin();
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
        DatePickerDialog recogerFecha = new DatePickerDialog(this,R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                if (flag == INICIO)
                    tvFechaInicio.setText(diaFormateado + "/" + mesFormateado + "/" + year);
                else
                    tvFechaFin.setText(diaFormateado + "/" + mesFormateado + "/" + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, 2019, 6, 6);
        //Muestro el widget
        recogerFecha.show();

    }

    private void bindearVistas() {
        vCalendarioInicio = findViewById(R.id.vacaciones_vCalendarioInicio);
        vCalendarioFin = findViewById(R.id.vacaciones_vCalendarioFin);
        tvFechaInicio= findViewById(R.id.registro_vacaciones_tvFechaInicio);
        tvFechaFin = findViewById(R.id.registro_vacaciones_tvFechaFin);

    }
}
