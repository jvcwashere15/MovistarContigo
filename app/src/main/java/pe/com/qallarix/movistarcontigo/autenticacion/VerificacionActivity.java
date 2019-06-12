package pe.com.qallarix.movistarcontigo.autenticacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.messaging.FirebaseMessaging;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.Employee;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.ResponseToken;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.ValidacionToken;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerificacionActivity extends TranquiParentActivity {

    private View
            vProgressVerificacion,
            vProgressVolverAEnviar;
    private TextView
            tvDni,
            tvVolverEnviarCodigo;
    private Button btValidar;
    private String
            documentNumber,
            tokenAccess,
            documentType;
    private Pinview mPinview;
    private ImageView btAtras;
    private final String
            TOPIC_PRODUCCION = "produccion_android",
            TOPIC_DESARROLLO = "develop_android",
            TOPIC_NOTIFICATIONS = TOPIC_DESARROLLO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        getDataIntent();
        bindearVistas();


        btAtras = findViewById(R.id.verificacion_btAtras);
        btAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        configurarPinview();
        setearDni();
        configurarBotonValidarToken();
        configurarBotonVolverEnviarcodigo(documentType,documentNumber);

    }

    private void bindearVistas() {
        vProgressVerificacion = findViewById(R.id.verificacion_lytValidarCodigo);
        vProgressVolverAEnviar = findViewById(R.id.verificacion_lytEnviarCodigo);
        mPinview = findViewById(R.id.verificacion_pinview);
        tvVolverEnviarCodigo = findViewById(R.id.verificacion_tvVolverEnviarCodigo);
        tvDni = findViewById(R.id.verificacion_tvDni);
        btValidar = findViewById(R.id.verificacion_btAceptar);
    }

    private void getDataIntent() {
        documentNumber = getIntent().getExtras().getString("documentNumber","");
        documentType = getIntent().getExtras().getString("documentType","");
    }

    private void configurarPinview() {
        mPinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                hideSoftKeyboard(VerificacionActivity.this);
                tokenAccess = mPinview.getValue();
                validarTokenConServicio(documentType,documentNumber,tokenAccess);
            }
        });
    }

    private void configurarBotonValidarToken(){
        btValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(VerificacionActivity.this);
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
        Call<ResponseToken> call = WebService.getInstance(documentNumber)
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
        Call<ValidacionToken> call = WebService.getInstance(documentNumber)
                .createService(ServiceEmployeeApi.class)
                .validarToken(tipoDoc,numeroDoc,tokenIngresado);
        vProgressVerificacion.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ValidacionToken>() {
            @Override
            public void onResponse(Call<ValidacionToken> call, Response<ValidacionToken> response) {
                vProgressVerificacion.setVisibility(View.GONE);
                if (response.code() == 200){
                    Employee employee = response.body().getEmployee();
                    guardarEmpleadoSharedPreferences(employee);
                    FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_NOTIFICATIONS);
                    Analitycs.logEventoLogin(VerificacionActivity.this);
                    Analitycs.setUserPropertyClaseYCategoria(VerificacionActivity.this,employee.getClase(),employee.getCategory());
                    irAPantallaBienvenida();
                }else{
                    mostrarMensaje("El código ingresado es incorrecto.");
                }
            }

            @Override
            public void onFailure(Call<ValidacionToken> call, Throwable t) {
                Toast.makeText(VerificacionActivity.this, "Se produjo un error al intentar validar el token", Toast.LENGTH_SHORT).show();
                vProgressVerificacion.setVisibility(View.GONE);
            }
        });
    }

    private void irAPantallaBienvenida() {
        Intent intent = new Intent(VerificacionActivity.this, BienvenidoActivity.class);
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
        editor.commit();
    }


    private void configurarFocusEditTextCodigo(final EditText et1, final EditText et2) {
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et1.getText().toString().trim().length() == 1){
                    et1.clearFocus();
                    et2.requestFocus();
                }
            }
        });
    }

    private void setearDni() {

        if (documentNumber.length() == 8){
            tvDni.setText("Número de DNI: " + documentNumber);
        }else{
            tvDni.setText("Número de C.E.: " + documentNumber);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void clickNull(View view) {
        Toast.makeText(this, "Espere...", Toast.LENGTH_SHORT).show();
    }
}
