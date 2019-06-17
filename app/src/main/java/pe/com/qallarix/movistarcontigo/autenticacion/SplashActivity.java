package pe.com.qallarix.movistarcontigo.autenticacion;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import java.util.Timer;
import java.util.TimerTask;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.Employee;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.ValidacionToken;
import pe.com.qallarix.movistarcontigo.descuentos.DescuentosActivity;
import pe.com.qallarix.movistarcontigo.noticias.DetalleNoticiaActivity;
import pe.com.qallarix.movistarcontigo.principal.MainActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private String mDni;
    private String mToken;
    private String mDocumentType;
    private boolean conectado = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (existConnectionInternet()){
            setContentView(R.layout.activity_splash);
            if(existeToken())
                validarSesion(mDocumentType,mDni,mToken);
            else
                mostrarLogin();
        }else{
            conectado = false;
            setContentView(R.layout.sin_conexion_internet);
        }
    }

    @Override
    protected void onStop() {
        if (!conectado) finish();
        super.onStop();
    }

    private boolean existeToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains("tokenAccess")
                && sharedPreferences.contains("documentType")
                && sharedPreferences.contains("documentNumber")){
            mToken = sharedPreferences.getString("tokenAccess","");
            mDocumentType = sharedPreferences.getString("documentType","");
            mDni = sharedPreferences.getString("documentNumber","");
            return true;
        }
        return false;
    }

    private void mostrarLogin() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 2500);
    }

    private void validarSesion(String tipoDoc, String numeroDoc, String tokenIngresado) {
        Call<ValidacionToken> call = WebService.getInstance(mDni)
            .createService(ServiceEmployeeApi.class)
            .validarSesion(tipoDoc,numeroDoc,tokenIngresado);

        call.enqueue(new Callback<ValidacionToken>() {
            @Override
            public void onResponse(Call<ValidacionToken> call, Response<ValidacionToken> response) {
                if (response.code() == 200){
                    Employee currentEmployee = response.body().getEmployee();

                    guardarNuevasPreferencias(currentEmployee);
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Intent intentLogin = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ValidacionToken> call, Throwable t) {
                mostrarMensaje("Se produjo un problema al obtener los datos de sesión, reintentar.");
            }
        });
    }

    private void guardarNuevasPreferencias(Employee employee) {
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        if (sharedPreferences != null && !sharedPreferences.contains("cip")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cip", employee.getCip());
            editor.putString("vicePresidency", employee.getVicePresidency());
            editor.putString("management", employee.getManagement());
            editor.commit();
            Analitycs.setUserProperties(SplashActivity.this,
                    employee.getClase(),employee.getCategory(),
                    employee.getVicePresidency(),employee.getManagement(),
                    employee.getCip());
        }
    }

    private boolean existConnectionInternet(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void mostrarMensaje(String m){
        Toast mToast = Toast.makeText(this, m, Toast.LENGTH_SHORT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setMessage(m);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        final AlertDialog alertDialog = builder.create();
        if(!isFinishing())
        {
            alertDialog.show();
        }else{
            mToast.show();
            finishAffinity();
        }
    }



}
