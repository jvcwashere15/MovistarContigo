package pe.com.qallarix.movistarcontigo.autenticacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.CerrarSesionToken;
import pe.com.qallarix.movistarcontigo.principal.MainActivity;
import pe.com.qallarix.movistarcontigo.util.TopicosNotificacion;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                    cerrarSesion();
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

    private void cerrarSesion() {
        if (existenPreferenciasSesionActiva()){
            ejecutarCierreSesion();
        }

    }

    private boolean existenPreferenciasSesionActiva() {
        SharedPreferences sharedPreferences = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains("tokenAccess")
                && sharedPreferences.contains("documentType")
                && sharedPreferences.contains("documentNumber")){
            mToken = sharedPreferences.getString("tokenAccess","");
            mDocumentType = sharedPreferences.getString("documentType","");
            mDni = sharedPreferences.getString("documentNumber","");
            return true;
        }else{
            mostrarMensaje("No se tiene sesion activa. Vuelva a ingresar.");
            return false;
        }
    }

    private void ejecutarCierreSesion() {
        Call<CerrarSesionToken> call = WebService1
                .getInstance(getDocumentNumber())
                .createService(ServiceEmployeeApi.class)
                .cerrarSesion(mDocumentType,mDni,mToken);
        lytProgress.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<CerrarSesionToken>() {
            @Override
            public void onResponse(Call<CerrarSesionToken> call, Response<CerrarSesionToken> response) {
                lytProgress.setVisibility(View.INVISIBLE);
                if (response.code() == 200){
                    cleanPreferencias();
                    desuscribirEnvioNotificaciones();
                    irAInicioLogin();
                }else{
                    mostrarMensaje("Se produjo un error al intentar cerrar sesion");
                }
            }

            @Override
            public void onFailure(Call<CerrarSesionToken> call, Throwable t) {
                mostrarMensaje("Se produjo un error al intentar cerrar sesion. Intente nuevamente");
            }
        });
    }

    private void irAInicioLogin() {
        Intent mainIntent = new Intent(AccountActivity.this, LoginActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void desuscribirEnvioNotificaciones() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TopicosNotificacion.TOPIC_NOTIFICATIONS);
    }


    private void cleanPreferencias() {
        SharedPreferences sharedPref = getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }


}
