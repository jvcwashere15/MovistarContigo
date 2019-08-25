package pe.com.qallarix.movistarcontigo.flexplace;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.BasePermissionListener;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.health.PdfActivity;
import pe.com.qallarix.movistarcontigo.util.PermissionUtils;

public class AboutFlexPlaceActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    TextView tvBotonPreguntasFrecuentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexplace_about);
        tvBotonPreguntasFrecuentes = findViewById(R.id.acerca_de_flex_tvPreguntasFrecuentes);
        configurarToolbar();
        configurarBotonOpenPdf(tvBotonPreguntasFrecuentes,
                "Preguntas Frecuentes FlexPlace",
                "https://storageqallarix.blob.core.windows.net/wpqallarixblob/Salud/flexplace_colaboradores.pdf");
    }

    public void configurarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Acerca de tu FlexPlace");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_navigation);
    }

    private void configurarBotonOpenPdf(TextView textView, final String title, final String file) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AboutFlexPlaceActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (PermissionUtils.neverAskAgainSelected(AboutFlexPlaceActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            displayNeverAskAgainDialog();
                        } else {
                            mostrarDialogNecesitamosPermisos(title,file);
                        }
                    }
                }else{
                    Intent intent = new Intent(AboutFlexPlaceActivity.this, PdfActivity.class);
                    intent.putExtra(PdfActivity.KEY_PDF_TITLE_ACTIVITY,title);
                    intent.putExtra(PdfActivity.KEY_URI_PDF,file);
                    startActivity(intent);
                }

            }
        });
    }

    private void displayNeverAskAgainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para visualizar el documento pdf sobre el instructivo del SGV, permite que podamos acceder al almacenamiento"+
                "\nToca Ajustes > Permisos, y activa Almacenamiento");
        builder.setCancelable(false);
        builder.setPositiveButton("AJUSTES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("AHORA NO", null);
        builder.show();
    }

    public void mostrarDialogNecesitamosPermisos(final String title, final String file){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para visualizar el documento pdf sobre el instructivo del SGV, permite que Movistar Contigo pueda acceder al almacenamiento");
        builder.setCancelable(false);
        builder.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                pedirPermisos(title,file);
            }
        });
        builder.setNegativeButton("AHORA NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void pedirPermisos(final String title, final String file) {
        if (alertDialog != null) alertDialog.dismiss();
        Dexter.withActivity(this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BasePermissionListener(){
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(AboutFlexPlaceActivity.this,PdfActivity.class);
                        intent.putExtra(PdfActivity.KEY_PDF_TITLE_ACTIVITY,title);
                        intent.putExtra(PdfActivity.KEY_URI_PDF,file);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        PermissionUtils.setShouldShowStatus(AboutFlexPlaceActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }).check();
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
        startActivity(upIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToParentActivity();
    }
}
