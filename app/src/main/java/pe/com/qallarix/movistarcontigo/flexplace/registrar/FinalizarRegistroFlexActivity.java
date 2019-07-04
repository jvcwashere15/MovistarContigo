package pe.com.qallarix.movistarcontigo.flexplace.registrar;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import pe.com.qallarix.movistarcontigo.R;

public class FinalizarRegistroFlexActivity extends AppCompatActivity {

    private String fechaInicio, fechaFin;
    private int dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_registro_flex);
        configurarToolbar();
        bindearVistas();
        getDataFromExtras();
        registrarFlexPlace();

    }

    private void getDataFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            fechaInicio = extras.getString("fecha_inicio");
            fechaFin = extras.getString("fecha_fin");
            dia = extras.getInt("dia");
        }
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro de FlexPlace");
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

    private void bindearVistas() {
    }


    private void registrarFlexPlace() {
    }

    public void verNormativa(View view) {
    }

    public void verHistorialFlexPlace(View view) {
    }

    public void volverMenu(View view) {
    }

    public void clickNull(View view) {
    }
}
