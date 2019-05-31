package pe.com.qallarix.movistarcontigo.autenticacion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.loaders.CerrarSesionLoader;
import pe.com.qallarix.movistarcontigo.principal.MainActivity;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;


public class AccountActivity extends TranquiParentActivity {
    private TextView tvNombreUsuario;
    private TextView tvHolaUsuario;
    private TextView tvCargo;
    private ImageView ivAtras;

    private Button btCerrarSesion, btIrAHome;

    private String mDni;
    private String mToken;
    private String mDocumentType;
    private String mCategoria;
    private String mFullName;
    private String firstName;

    private TextView tvInicialesUsuario;

    private LinearLayout lytProgress;
    public final String BASE_URL_QA = "https://qallarix-ws-qa.azurewebsites.net/";
    public final String BASE_URL_PRODUCCION = "https://tcqallarix.azurewebsites.net/";
    public final String CERRAR_BASIC_REQUEST_URL = BASE_URL_PRODUCCION +"employee/closeSession/close";
    public static int SESION_LOADER_ID = 3;

    private LoaderManager.LoaderCallbacks<Integer> cerrarSesionLoaderCallbacks = new LoaderManager.LoaderCallbacks<Integer>() {
        @NonNull
        @Override
        public Loader<Integer> onCreateLoader(int i, @Nullable Bundle bundle) {
            lytProgress.setVisibility(View.VISIBLE);
            Uri baseUri = Uri.parse(CERRAR_BASIC_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter("documentType", bundle.getString("documentType"));
            uriBuilder.appendQueryParameter("documentNumber", bundle.getString("documentNumber"));
            uriBuilder.appendQueryParameter("tokenAccess", bundle.getString("tokenAccess"));
            return new CerrarSesionLoader(AccountActivity.this, uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Integer> loader, Integer sesionActivaCerrada) {
            lytProgress.setVisibility(View.INVISIBLE);
            if (sesionActivaCerrada != null){
                if (sesionActivaCerrada == 1){
                    SharedPreferences sharedPref = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove("documentType");
                    editor.remove("documentNumber");
                    editor.remove("tokenAccess");
                    editor.commit();
                    Intent mainIntent = new Intent(AccountActivity.this, LoginActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }else{
                    mostrarMensaje("Se produjo un error al intentar cerrar sesion");
                }
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Integer> loader) { }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvNombreUsuario = findViewById(R.id.account_tvNombreUsuario);
        tvHolaUsuario = findViewById(R.id.account_tvHolaUsuario);
        tvCargo = findViewById(R.id.account_tvCargo);
        ivAtras = findViewById(R.id.account_btAtras);

        tvInicialesUsuario = findViewById(R.id.account_tvInicialesUsuario);

        lytProgress = findViewById(R.id.login_lytProgress);

        btCerrarSesion = findViewById(R.id.account_btCerrarSesion);
        btIrAHome = findViewById(R.id.account_btHome);

        SharedPreferences sharedPref = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        mDni = sharedPref.getString("documentNumber", "");
        mDocumentType = sharedPref.getString("documentType", "");
        mToken = sharedPref.getString("tokenAccess", "");
        mFullName = sharedPref.getString("fullName", "");
        mCategoria = sharedPref.getString("category", "");
        firstName = sharedPref.getString("firstName","");

        tvInicialesUsuario.setText(obtenerIniciales());
        ivAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (!mFullName.equals("")){
            tvNombreUsuario.setText(mFullName);
            tvHolaUsuario.setText("¡Hola " + firstName + "!");
            tvCargo.setText(mCategoria);
        }



        btCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (existConnectionInternet()){
                    SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
                    if (sharedPreferences != null && sharedPreferences.contains("tokenAccess")
                            && sharedPreferences.contains("documentType")
                            && sharedPreferences.contains("documentNumber")){
                        mToken = sharedPreferences.getString("tokenAccess","");
                        mDocumentType = sharedPreferences.getString("documentType","");
                        mDni = sharedPreferences.getString("documentNumber","");
                        LoaderManager loaderManager = getSupportLoaderManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("documentType",mDocumentType);
                        bundle.putString("documentNumber",mDni);
                        bundle.putString("tokenAccess",mToken);
                        loaderManager.initLoader(SESION_LOADER_ID, bundle, cerrarSesionLoaderCallbacks);
                    }else{
                        mostrarMensaje("No se tiene sesion activa. Vuelva a ingresar.");
                    }
                }else{
                    Toast.makeText(AccountActivity.this, "Problemas con la red, revisa tu conexion a internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btIrAHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(AccountActivity.this, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    public void mostrarMensaje(String m){
        final AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setMessage(m);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
