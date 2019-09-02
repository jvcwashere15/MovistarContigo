package pe.com.qallarix.movistarcontigo.autentication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import pe.com.qallarix.movistarcontigo.BuildConfig;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ValidacionToken;
import pe.com.qallarix.movistarcontigo.main.MainActivity;
import pe.com.qallarix.movistarcontigo.util.TopicsNotification;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends TranquiParentActivity {
    private String mDni;
    private String mToken;
    private String mDocumentType;
    private boolean conectado = true;
    private TextView tvVersionName;
    private View vSplashSinConexion;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindViews();
        setVersionName();

        if (internetConectionExists()){
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
                    .setMinimumFetchIntervalInSeconds(0)
                    .build();
            mFirebaseRemoteConfig.setConfigSettings(configSettings);
            HashMap<String,Object> defaults = new HashMap<>();
            defaults.put("version_code",BuildConfig.VERSION_CODE);


            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(@NonNull Task<Boolean> task) {
                    if (isOldVersion()){
                        mostrarMensajeOldVersion();
                    }else{
                        if(existeToken())
                            validarSesion(mDocumentType,mDni,mToken);
                        else
                            mostrarLogin();
                    }
                }
            });
        }else{
            conectado = false;
            vSplashSinConexion.setVisibility(View.VISIBLE);
        }

    }

    private void goToUpdateAppMovistarContigo() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private boolean isOldVersion() {
        int versionCode = (int) mFirebaseRemoteConfig.getLong("version_code");
        if (BuildConfig.VERSION_CODE < versionCode){
            return true;
        }
        return false;
    }



    private void setVersionName() {
        tvVersionName.setText(BuildConfig.VERSION_NAME);
    }

    private void bindViews() {
        tvVersionName = findViewById(R.id.splash_tvVersionName);
        vSplashSinConexion = findViewById(R.id.splash_vSinConexion);
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
        Call<ValidacionToken> call = WebService1.getInstance(mDni)
            .createService(ServiceEmployeeApi.class)
            .validarSesion(tipoDoc,numeroDoc,tokenIngresado,getTokenNotification(),"1");

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
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(TopicsNotification.TOPIC_NOTIFICATIONS);
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("documentType", mDocumentType);
        editor.putString("tokenAccess", mToken);
        editor.putString("category", employee.getCategory());
        editor.putString("clase", employee.getClase());
        editor.putString("initials", employee.getInitials());
        editor.putString("documentNumber", employee.getDocumentNumber());
        editor.putString("email", employee.getEmail());
        editor.putString("firstName", employee.getFirstName());
        editor.putString("fullName", employee.getFullName());
        editor.putString("sex", employee.getSex());
        editor.putString("shortName", employee.getShortName());
        editor.putString("cip", employee.getCip());
        editor.putString("vicePresidency", employee.getVicePresidency());
        editor.putString("management", employee.getManagement());
        editor.putString("direction", employee.getDirection());
        editor.putBoolean("isFlexPlace", employee.isFlexPlace());
        editor.commit();
        Analitycs.setUserProperties(SplashActivity.this,
                employee.getClase(),employee.getCategory(),
                employee.getVicePresidency(),employee.getManagement(),
                employee.getCip(),employee.getDirection());
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

    public void mostrarMensajeOldVersion(){
        Toast mToast = Toast
                .makeText(this, "Hay una nueva versión disponible de Movistar Contigo. " +
                                "¡Actualiza y sigue disfrutando de nuestra app!",
                Toast.LENGTH_SHORT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this
                ,R.style.DialogMensajeStyle);
        builder.setTitle("NUEVA VERSIÓN DISPONIBLE");
        builder.setMessage("Hay una nueva versión disponible de Movistar Contigo. " +
                "¡Actualiza y sigue disfrutando de nuestra app!");
        builder.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                goToUpdateAppMovistarContigo();
                finishAffinity();
            }
        });
        builder.setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        builder.setCancelable(false);
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
