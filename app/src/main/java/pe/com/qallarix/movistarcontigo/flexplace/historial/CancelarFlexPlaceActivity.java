package pe.com.qallarix.movistarcontigo.flexplace.historial;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.FinalizarRegistroFlexActivity;
import pe.com.qallarix.movistarcontigo.flexplace.registrar.RegistrarFlexPlaceActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.RegistroVacacionesActivity;
import pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos.ResponseValidarFechas;
import retrofit2.Response;

public class CancelarFlexPlaceActivity extends AppCompatActivity {

    private String fechaInicio, fechaFin, dia, idState;
    private long idRequest;
    private TextView tvEstado, tvFechaInicio, tvFechaFin, tvDia, tvBotonContinuar,
            tvDescripcionLider, tvLider;
    private EditText etDescripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_flex_place);
        getDataFromIntent();
        configurarToolbar();
        bindearVistas();
        configurarBotoncontinuar();
        displayDetalleFlexPlaceCancelar();
    }

    private void configurarBotoncontinuar() {
        tvBotonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDescripcion.getVisibility() == View.VISIBLE){
                    if(TextUtils.isEmpty(etDescripcion.getText().toString()))
                        mostrarDialogError();
                    else
                        mostrarDialogCancelacionFlexPlace("¿Deseas continuar?",
                                "Vas a cancelar tu FlexPlace en curso del " +
                                        fechaInicio + " al " + fechaFin + ".");
                }else
                    mostrarDialogCancelacionFlexPlace("¿Deseas continuar?",
                            "Vas a cancelar tu FlexPlace en curso del " +
                                    fechaInicio + " al " + fechaFin + ".");
            }
        });
    }

    private void mostrarDialogError() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CancelarFlexPlaceActivity.this);
        builder.setTitle("¡Ups!");
        builder.setMessage("Debes indicar un motivo de cancelación");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.dismiss(); }
        });
        final AlertDialog alertDialog = builder.create();
        if (!isFinishing()) alertDialog.show();
    }

    private void displayDetalleFlexPlaceCancelar() {
        tvFechaInicio.setText(fechaInicio);
        tvFechaFin.setText(fechaFin);
        tvDia.setText(dia);

        String strEstado = "";
        int colorEstado = 0;
        if (idState.equals(ServiceFlexplaceHistorialApi.APROBADO)){
            strEstado = "APROBADO";colorEstado = R.drawable.etiqueta_verde;
            etDescripcion.setVisibility(View.VISIBLE);
        }else if (idState.equals(ServiceFlexplaceHistorialApi.PENDIENTE)){
            strEstado = "PENDIENTE";colorEstado = R.drawable.etiqueta_amarilla;
            tvLider.setText("FlexPlace por aprobar");
            tvDescripcionLider.setText("Cancelarás la solicitud de tus días Flex:");
        }
//        else if (idState.equals(ServiceFlexplaceHistorialApi.RECHAZADO)){
//            strEstado = "RECHAZADO";colorEstado = R.drawable.etiqueta_roja;
//        }else if (idState.equals(ServiceFlexplaceHistorialApi.CANCELADO)){
//            strEstado = "CANCELADO";colorEstado = R.drawable.etiqueta_morada;
//        }
        tvEstado.setText(strEstado);
        tvEstado.setBackgroundResource(colorEstado);
    }

    private void bindearVistas() {
        tvEstado = findViewById(R.id.cancelar_flexplace_tvEstado);
        tvFechaInicio = findViewById(R.id.cancelar_flexplace_tvFechaInicio);
        tvFechaFin = findViewById(R.id.cancelar_flexplace_tvFechaFin);
        tvDia = findViewById(R.id.cancelar_flexplace_tvDiasSolicitados);
        tvLider = findViewById(R.id.cancelar_flexplace_tvLider);
        tvDescripcionLider = findViewById(R.id.cancelar_flexplace_tvDescLider);
        etDescripcion = findViewById(R.id.cancelar_flexplace_etDescripcion);
        tvBotonContinuar = findViewById(R.id.cancelar_flexplace_tvBotonContinuar);
    }

    private void getDataFromIntent() {
        fechaInicio = getIntent().getExtras().getString("fechaInicio");
        fechaFin = getIntent().getExtras().getString("fechaFin");
        dia = getIntent().getExtras().getString("dia");
        idRequest = getIntent().getExtras().getLong("idRequest");
        idState = getIntent().getExtras().getString("idState");
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cancelar FlexPlace");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    public void mostrarDialogCancelacionFlexPlace(String title, String mensaje){
        final AlertDialog.Builder builder = new AlertDialog.Builder(CancelarFlexPlaceActivity.this);
        builder.setTitle(title);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(CancelarFlexPlaceActivity.this,
                        FinalizarCancelacionFlexActivity.class);
                intent.putExtra("fecha_inicio",fechaInicio);
                intent.putExtra("fecha_fin",fechaFin);
                intent.putExtra("idRequest",idRequest);
                intent.putExtra("observacion",etDescripcion.getText());
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
}
