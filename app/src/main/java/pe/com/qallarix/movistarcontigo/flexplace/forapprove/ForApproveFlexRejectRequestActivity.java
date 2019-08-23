package pe.com.qallarix.movistarcontigo.flexplace.forapprove;

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

import pe.com.qallarix.movistarcontigo.R;

public class ForApproveFlexRejectRequestActivity extends AppCompatActivity {

    private String fechaInicio, fechaFin, dia, fechaSolicitud, nombreEmpleado;
    private long idRequest;
    private TextView tvFechaSolicitud, tvFechaInicio, tvFechaFin, tvDia, tvBotonRechazar,
            tvDescripcionLider, tvLider;
    private EditText etDescripcion;
    private String mTextoBoton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechazo_solicitud_flex);
        getDataFromIntent();
        configurarToolbar();
        bindearVistas();
        configurarBotonRechazar();
        displayDetalleFlexPlaceCancelar();
    }

    private void configurarBotonRechazar() {
        tvBotonRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etDescripcion.getText().toString()))
                    mostrarDialogError();
                else
                    mostrarDialogRechazarFlexPlace();
            }
        });
    }

    private void mostrarDialogError() {
        final AlertDialog.Builder builder = new AlertDialog
                .Builder(ForApproveFlexRejectRequestActivity.this);
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
        tvFechaSolicitud.setText(fechaSolicitud);
    }

    private void bindearVistas() {
        tvFechaSolicitud = findViewById(R.id.rechazar_flexplace_tvFechaSolicitud);
        tvFechaInicio = findViewById(R.id.rechazar_flexplace_tvFechaInicio);
        tvFechaFin = findViewById(R.id.rechazar_flexplace_tvFechaFin);
        tvDia = findViewById(R.id.rechazar_flexplace_tvDiasSolicitados);
        tvLider = findViewById(R.id.rechazar_flexplace_tvLider);
        tvDescripcionLider = findViewById(R.id.rechazar_flexplace_tvDescLider);
        etDescripcion = findViewById(R.id.rechazar_flexplace_etDescripcion);
        tvBotonRechazar = findViewById(R.id.rechazar_flexplace_tvBotonRechazar);
    }

    private void getDataFromIntent() {
        nombreEmpleado = getIntent().getExtras().getString("nombreEmpleado");
        fechaSolicitud = getIntent().getExtras().getString("fechaSolicitud");
        fechaInicio = getIntent().getExtras().getString("fechaInicio");
        fechaFin = getIntent().getExtras().getString("fechaFin");
        dia = getIntent().getExtras().getString("dia");
        idRequest = getIntent().getExtras().getLong("idRequest");
        mTextoBoton = getIntent().getExtras().getString("texto_boton");
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de FlexPlace");
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

    public void mostrarDialogRechazarFlexPlace(){
        final AlertDialog.Builder builder = new AlertDialog
                .Builder(ForApproveFlexRejectRequestActivity.this);
        builder.setTitle("¿Deseas continuar?");
        builder.setMessage("Vas a rechazar la solicitud de FlexPlace de " + nombreEmpleado + ".");
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ForApproveFlexRejectRequestActivity.this,
                        ForApproveFlexFinishProcessActivity.class);
                intent.putExtra("nombreEmpleado",nombreEmpleado);
                intent.putExtra("idRequest",idRequest);
                intent.putExtra("dia",dia);
                intent.putExtra("aprobar",false);
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
}
