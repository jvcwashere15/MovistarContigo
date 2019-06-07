package pe.com.qallarix.movistarcontigo.vacaciones;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.AprobacionVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.estado.EstadoVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.RegistroVacacionesActivity;

public class VacacionesActivity extends AppCompatActivity {
    private View
            vRegistro,
            vEstado,
            vAprobacion,
            vCalendarioInicio,
            vCalendarioFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacaciones);
        configurarToolbar();
        bindearVistas();
        configurarBotonRegistroVacaciones();
        configurarBotonEstadoVacaciones();
        configurarBotonAprobacionVacaciones();

    }



    private void configurarBotonRegistroVacaciones() {
        vRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, RegistroVacacionesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configurarBotonEstadoVacaciones() {
        vEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, EstadoVacacionesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void configurarBotonAprobacionVacaciones() {
        vAprobacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(VacacionesActivity.this, AprobacionVacacionesActivity.class);
                startActivity(intent);
            }
        });
    }



    private void bindearVistas() {
        vRegistro = findViewById(R.id.vacaciones_vRegistro);
        vEstado = findViewById(R.id.vacaciones_vEstado);
        vAprobacion = findViewById(R.id.vacaciones_vAprobacion);
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gestión de vacaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vacaciones, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_informacion:
                //visualizar informacion
                return true;
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
}
