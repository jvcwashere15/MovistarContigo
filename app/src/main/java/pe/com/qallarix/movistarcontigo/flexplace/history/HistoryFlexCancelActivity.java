package pe.com.qallarix.movistarcontigo.flexplace.history;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import pe.com.qallarix.movistarcontigo.R;


public class HistoryFlexCancelActivity extends AppCompatActivity {

    private String fechaInicio, fechaFin, dia, statusId;
    private long idRequest;
    private TextView tvEstado, tvFechaInicio, tvFechaFin, tvDia, tvBotonContinuar,
            tvDescripcionLider, tvLider;
    private EditText etDescripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_history_cancel);
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
                if (statusId.equals(ServiceFlexPlaceHistoryApi.APROBADO)){
                    if(TextUtils.isEmpty(etDescripcion.getText().toString()))
                        mostrarDialogError();
                    else
                        mostrarDialogCancelacionFlexPlace("¿Deseas continuar?",
                                "Vas a cancelar tu FlexPlace en curso del " +
                                        fechaInicio + " al " + fechaFin + ".");
                }else
                    mostrarDialogCancelacionFlexPlace("¿Deseas continuar?",
                            "Vas a cancelar la solicitud de tus días Flex del " +
                                    fechaInicio + " al " + fechaFin + ".");
            }
        });
    }

    private void mostrarDialogError() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HistoryFlexCancelActivity.this);
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
        if (statusId.equals(ServiceFlexPlaceHistoryApi.APROBADO)){
            strEstado = "APROBADO";colorEstado = R.drawable.etiqueta_verde;
            etDescripcion.setVisibility(View.VISIBLE);
        }else if (statusId.equals(ServiceFlexPlaceHistoryApi.PENDIENTE)){
            strEstado = "PENDIENTE";colorEstado = R.drawable.etiqueta_amarilla;
            tvLider.setText("FlexPlace por aprobar");
            tvDescripcionLider.setText("Cancelarás la solicitud de tus días Flex:");
        }
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
        if (getIntent().getExtras()!= null){
            fechaInicio = getIntent().getExtras().getString("fechaInicio");
            fechaFin = getIntent().getExtras().getString("fechaFin");
            dia = getIntent().getExtras().getString("dia");
            statusId = getIntent().getExtras().getString("statusId");
            idRequest = getIntent().getExtras().getLong("idRequest");
        }

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

    public void mostrarDialogCancelacionFlexPlace(String title, String mensaje){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                HistoryFlexCancelActivity.this,R.style.DialogMensajeStyle);
        builder.setTitle(title);
        builder.setMessage(mensaje);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(HistoryFlexCancelActivity.this,
                        HistoryFlexFinishCancelActivity.class);
                intent.putExtra("fecha_inicio",fechaInicio);
                intent.putExtra("fecha_fin",fechaFin);
                intent.putExtra("idRequest",idRequest);
                intent.putExtra("statusId",statusId);
                intent.putExtra("observacion",etDescripcion.getText().toString());
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

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }
}
