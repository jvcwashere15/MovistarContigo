package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;

public class ForApproveDetailActivity extends TranquiParentActivity {
    private String requestCode,requestDay,employeeName, employeeCode, bossCode, bossName,
            requestDateStart,requestDateEnd;

    private long requestDaysDifference;
    private TextView
            tvEmpleado,
            tvFechaSolicitud,
            tvFechaInicio,
            tvFechaFin,
            tvDiasSolicitados,
            tvDescripcion;
    private TextView
            tvButtonAprobar,
            tvButtonRechazar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_aprobacion_vacaciones);
        bindearVistas();
        configurarToolbar();
        getExtrasFromIntent();
        displayDetalleSolicitud();
        configurarBotonAprobar(employeeName);
        configurarBotonRechazar(employeeName);
    }

    private void getExtrasFromIntent() {
        requestDay = getIntent().getExtras().getString("requestDay","");
        requestCode = getIntent().getExtras().getString("requestCode","");
        employeeName = getIntent().getExtras().getString("employeeName","");
        requestDateStart = getIntent().getExtras().getString("requestDateStart","");
        requestDateEnd = getIntent().getExtras().getString("requestDateEnd","");
        employeeCode = getIntent().getExtras().getString("employeeCode","");
        bossCode = getIntent().getExtras().getString("bossCode","");
        bossName = getIntent().getExtras().getString("bossName","");

        requestDaysDifference = getIntent().getExtras().getLong("getRequestDaysDifference",0);
    }

    private void bindearVistas() {
        tvEmpleado = findViewById(R.id.detalle_aprobacion_tvEmpleado);
        tvDescripcion = findViewById(R.id.detalle_aprobacion_tvDescripcion);
        tvDiasSolicitados = findViewById(R.id.detalle_aprobacion_tvDiasSolicitados);
        tvFechaSolicitud = findViewById(R.id.detalle_aprobacion_tvFechaSolicitud);
        tvFechaFin = findViewById(R.id.detalle_aprobacion_tvFechaFin);
        tvFechaInicio = findViewById(R.id.detalle_aprobacion_tvFechaInicio);
        tvButtonAprobar = findViewById(R.id.detalle_aprobacion_tvButtonAprobar);
        tvButtonRechazar = findViewById(R.id.detalle_aprobacion_tvButtonRechazar);
    }

    private void displayDetalleSolicitud() {
        tvEmpleado.setText(employeeName);
        tvFechaSolicitud.setText(requestDay);
        tvFechaInicio.setText(requestDateStart);
        tvFechaFin.setText(requestDateEnd);
        tvDiasSolicitados.setText(requestDaysDifference + " días");
        tvDescripcion.setText("Tienes un plazo de 3 días hábiles para responder a esta solicitud, " +
                "de lo contrario se aprobarán automáticamente.");
    }


    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de solicitud");
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

    private void configurarBotonAprobar(final String colaborador) {
        tvButtonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogAprobacionRechazo("aprobar",colaborador,true);
            }
        });
    }

    private void configurarBotonRechazar(final String colaborador) {
        tvButtonRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogAprobacionRechazo("rechazar",colaborador,false);
            }
        });
    }

    private void mostrarDialogAprobacionRechazo(String mensaje, final String nombre, final boolean approver) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                ForApproveDetailActivity.this,R.style.DialogMensajeStyle);
        builder.setTitle("¿Deseas continuar?");
        builder.setMessage("Vas a " + mensaje + " la solicitud de vacaciones de " + nombre);
        builder.setCancelable(false);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ForApproveDetailActivity.this, ForApproveFinishActivity.class);
                //Datos para aprobar o rechazar vacaciones
                intent.putExtra("employeeCode",getCIP());
                intent.putExtra("requestCode",requestCode);
                intent.putExtra("approver",approver);
                intent.putExtra("requestDateStart",requestDateStart);
                intent.putExtra("requestDateEnd",requestDateEnd);
                intent.putExtra("employeeCode",employeeCode);
                intent.putExtra("bossCode",bossCode);
                intent.putExtra("bossName",bossName);

                //Datos para mostrar
                intent.putExtra("colaborador",nombre);
                intent.putExtra("dias",requestDaysDifference);
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
