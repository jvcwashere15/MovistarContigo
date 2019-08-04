package pe.com.qallarix.movistarcontigo.autenticacion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autenticacion.pojos.ResponseToken;
import pe.com.qallarix.movistarcontigo.util.NumericKeyBoardTransformationMethod;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import pe.com.qallarix.movistarcontigo.util.WebService2;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import pe.com.qallarix.movistarcontigo.util.WebServiceNotification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends TranquiParentActivity {

    private EditText etDni;
    private TextView btIngresar;
    private String documentType = "1";
    private TextView tvTerminos;
    private CheckBox cbTerminos;
    private View viewProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        clearWebServices();
        bindearVistas();
        configurarInputDni();
        configurarBotonIniciarSesion();
        configurarTerminos();
    }

    private void clearWebServices() {
        WebService1.setInstance(null);
        WebService2.setInstance(null);
        WebServiceFlexPlace.setInstance(null);
        WebServiceNotification.setInstance(null);
    }

    private void bindearVistas() {
        viewProgress = findViewById(R.id.login_viewProgress);
        tvTerminos = findViewById(R.id.login_tvTerminosCondiciones);
        etDni = findViewById(R.id.login_etDni);
        btIngresar = findViewById(R.id.login_btIngresar);
        cbTerminos = findViewById(R.id.login_cbTerminos);
    }

    private void configurarTerminos() {
        tvTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,TerminosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configurarInputDni() {
        etDni.setTransformationMethod(new NumericKeyBoardTransformationMethod());
    }

    private void configurarBotonIniciarSesion(){
        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
    }

    private void iniciarSesion() {
        String documentNumber = etDni.getText().toString().trim();
        if (documentNumber != null && !TextUtils.isEmpty(documentNumber)) {
            if (documentoEsValido(documentNumber) && terminosAceptados()){
                if (existConnectionInternet()){
                    documentNumber = etDni.getText().toString();
                    pedirTokenAlServicio(documentType, documentNumber);
                }else{
                    Toast.makeText(LoginActivity.this, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(LoginActivity.this, "Debe ingresar su DNI", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean terminosAceptados() {
        if (cbTerminos.isChecked())
            return true;
        else
            mostrarMensaje("Debe aceptar los términos y condiciones");
        return false;
    }

    private boolean documentoEsValido(String documentNumber) {
        if (documentNumber.length() == 8 || documentNumber.length() == 9){
            return true;
        }else{
           mostrarMensaje("Debe ingresar un número de DNI (8 dígitos) o C.E. (9 dígitos) válido.");
        }
        return false;
    }



    private void pedirTokenAlServicio(final String tipoDoc, final String numeroDoc){
        hideSoftKeyboard(this);
        Call<ResponseToken> call = WebService1.getInstance(numeroDoc)
                .createService(ServiceEmployeeApi.class)
                .getGenerateToken(tipoDoc,numeroDoc);
        viewProgress.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseToken>() {
            @Override
            public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                viewProgress.setVisibility(View.GONE);
                if (response.code() == 200){
                    Intent intent = new Intent(LoginActivity.this,VerificacionActivity.class);
                    intent.putExtra("token",response.body().getToken().getCode());
                    intent.putExtra("documentType",tipoDoc);
                    intent.putExtra("documentNumber",numeroDoc);
                    etDni.setText("");
                    startActivity(intent);
                }else if (response.code() == 401){
                    mostrarMensaje("Número de DNI o C.E. incorrecto");
                }else{
                    mostrarMensaje("Se produjo un error al intentar iniciar sesión");
                }
            }

            @Override
            public void onFailure(Call<ResponseToken> call, Throwable t) {
                viewProgress.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Se produjo un error al intentar iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clickNull(View view) {
        Toast.makeText(this, "Espere...", Toast.LENGTH_SHORT).show();
    }
}
