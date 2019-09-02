package pe.com.qallarix.movistarcontigo.autentication.verification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autentication.ServiceEmployeeApi;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationPresenter;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationView;
import pe.com.qallarix.movistarcontigo.autentication.login.LoginActivityView;
import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ResponseToken;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ValidacionToken;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.autentication.welcome.WelcomeActivityView;
import pe.com.qallarix.movistarcontigo.util.TopicsNotification;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerificationActivityView extends TranquiParentActivity implements VerificationView {

    private View
            vProgressVerificacion,
            vProgressVolverAEnviar;
    private TextView
            tvDni,
            tvVolverEnviarCodigo;
    private TextView btValidar;
    private String
            documentNumber,
            tokenAccess,
            documentType;
    private Pinview mPinview;

    private VerificationPresenter verificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        bindViews();
        verificationPresenter = new VerificationPresenterImpl(this);

        getDataFromIntent();
        configurarPinview();
        setearDni();

        configurarBotonValidarToken();
        configurarBotonVolverEnviarcodigo(documentType,documentNumber);

    }

    private void bindViews() {
        vProgressVerificacion = findViewById(R.id.verificacion_lytValidarCodigo);
        vProgressVolverAEnviar = findViewById(R.id.verificacion_lytEnviarCodigo);
        mPinview = findViewById(R.id.verificacion_pinview);
        tvVolverEnviarCodigo = findViewById(R.id.verificacion_tvVolverEnviarCodigo);
        tvDni = findViewById(R.id.verificacion_tvDni);
        btValidar = findViewById(R.id.verificacion_btAceptar);
    }

    private void getDataFromIntent() {
        documentNumber = getIntent().getExtras().getString("documentNumber","");
        documentType = getIntent().getExtras().getString("documentType","");
    }

    private void configurarPinview() {
        mPinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                hideSoftKeyboard(VerificationActivityView.this);
                tokenAccess = mPinview.getValue();
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(
                                new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("Firebase Token Id/", "getInstanceId failed", task.getException());
                                    return;
                                }
                                // Get new Instance ID token
                                String token = task.getResult().getToken();
                                Log.d("Firebase Token Id/","FIREBASE TOKEN ID: " + token);
                                SharedPreferences sharedPref = getSharedPreferences("quallarix.movistar.pe.com.quallarix", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("tokenNotification", token);
                                editor.commit();
                                validarTokenConServicio(documentType,documentNumber,tokenAccess);
                            }
                        });
            }
        });
    }

    private void configurarBotonValidarToken(){
        btValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(VerificationActivityView.this);
                String codigoVerificacion = mPinview.getValue();
                if (codigoVerificacion != null && !TextUtils.isEmpty(codigoVerificacion) && codigoVerificacion.length()==5) {
                    tokenAccess = codigoVerificacion;
                    validarTokenConServicio(documentType, documentNumber, tokenAccess);
                } else {
                    mostrarMensaje("Debe ingresar el código de verificación de 5 dígitos");
                }
            }
        });
    }

    private void configurarBotonVolverEnviarcodigo(final String tipoDoc, final String numeroDoc) {
        tvVolverEnviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reenviarCodigoAutorizacion(documentType,documentNumber);
            }
        });
    }

    private void reenviarCodigoAutorizacion(String documentType, String documentNumber) {
        Call<ResponseToken> call = WebService1.getInstance(documentNumber)
                .createService(ServiceEmployeeApi.class)
                .getGenerateToken(documentType,documentNumber);
        vProgressVolverAEnviar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseToken>() {
            @Override
            public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                vProgressVolverAEnviar.setVisibility(View.GONE);
                if (response.code() == 200){
                    mostrarMensaje("Se envío un nuevo código a tu correo corporativo");
                }else if (response.code() == 401){
                    mostrarMensaje("El número de documento no esta autorizado para iniciar sesión");
                    finish();
                }else{
                    mostrarMensaje("Se produjo un error al intentar enviar un nuevo código");
                }
            }

            @Override
            public void onFailure(Call<ResponseToken> call, Throwable t) {
                vProgressVolverAEnviar.setVisibility(View.GONE);
                mostrarMensaje("Se produjo un error al intentar enviar un nuevo código");
            }
        });
    }


    private void validarTokenConServicio(String tipoDoc, String numeroDoc, String tokenIngresado) {
        Call<ValidacionToken> call = WebService1.getInstance(documentNumber)
                .createService(ServiceEmployeeApi.class)
                .validarToken(tipoDoc,numeroDoc,tokenIngresado,getTokenNotification(),"1");
        vProgressVerificacion.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ValidacionToken>() {
            @Override
            public void onResponse(Call<ValidacionToken> call, Response<ValidacionToken> response) {
                vProgressVerificacion.setVisibility(View.GONE);
                if (response.code() == 200){
                    Employee employee = response.body().getEmployee();
                    guardarEmpleadoSharedPreferences(employee);
                    FirebaseMessaging.getInstance().subscribeToTopic(TopicsNotification.TOPIC_NOTIFICATIONS);
                    Analitycs.logEventoLogin(VerificationActivityView.this);
                    Analitycs.setUserProperties(VerificationActivityView.this,
                            employee.getClase(),employee.getCategory(),
                            employee.getVicePresidency(),employee.getManagement(),
                            employee.getCip(),employee.getDirection());
                    irAPantallaBienvenida();
                }else{
                    mostrarMensaje("El código ingresado es incorrecto.");
                }
            }

            @Override
            public void onFailure(Call<ValidacionToken> call, Throwable t) {
                Toast.makeText(VerificationActivityView.this, "Se produjo un error al intentar validar el token", Toast.LENGTH_SHORT).show();
                vProgressVerificacion.setVisibility(View.GONE);
            }
        });
    }

    private void irAPantallaBienvenida() {
        Intent intent = new Intent(VerificationActivityView.this, WelcomeActivityView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void guardarEmpleadoSharedPreferences(Employee employee){
        SharedPreferences sharedPref = getSharedPreferences("quallarix.movistar.pe.com.quallarix", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("documentType", documentType);
        editor.putString("tokenAccess", tokenAccess);
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
    }

    private void setearDni() {

        if (documentNumber.length() == 8){
            tvDni.setText("Número de DNI: " + documentNumber);
        }else{
            tvDni.setText("Número de C.E.: " + documentNumber);
        }
    }

    public void clickNull(View view) {
        Toast.makeText(this, "Espere...", Toast.LENGTH_SHORT).show();
    }

    public void backToLogin(View view) {
        startActivity(new Intent(this, LoginActivityView.class));
        finish();
    }

    public void validateToken(View view) {

    }
}
