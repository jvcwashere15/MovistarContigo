package pe.com.qallarix.movistarcontigo.autentication.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autentication.UtilAuthentication;
import pe.com.qallarix.movistarcontigo.autentication.interfaces.login.LoginPresenter;
import pe.com.qallarix.movistarcontigo.autentication.interfaces.login.LoginView;
import pe.com.qallarix.movistarcontigo.autentication.presenters.LoginPresenterImpl;
import pe.com.qallarix.movistarcontigo.util.NumericKeyBoardTransformationMethod;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import pe.com.qallarix.movistarcontigo.util.WebServiceAmbassador;
import pe.com.qallarix.movistarcontigo.util.WebServiceVacations;
import pe.com.qallarix.movistarcontigo.util.WebServiceFlexPlace;
import pe.com.qallarix.movistarcontigo.util.WebServiceNotification;


public class LoginActivityView extends AppCompatActivity implements LoginView {

    private EditText editTextDni;
    private static final String DOCUMENT_TYPE = "1";
    private CheckBox cbTerminos;
    private View viewLoading;
    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        clearWebServices();
        bindViews();
        setUpInputDni();
        loginPresenter = new LoginPresenterImpl(this);
    }

    private void clearWebServices() {
        WebService1.setInstance(null);
        WebServiceAmbassador.setInstance(null);
        WebServiceVacations.setInstance(null);
        WebServiceFlexPlace.setInstance(null);
        WebServiceNotification.setInstance(null);
    }

    private void bindViews() {
        viewLoading = findViewById(R.id.login_view_loading);
        editTextDni = findViewById(R.id.login_etDni);
        cbTerminos = findViewById(R.id.login_cbTerminos);
    }

    private void setUpInputDni() {
        editTextDni.setTransformationMethod(new NumericKeyBoardTransformationMethod());
    }

    public void clickNull(View view) {}

    @Override
    public void showMessageEmptyDNI() {
        Toast.makeText(LoginActivityView.this, "Debe ingresar su DNI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageNoInternet() {
        Toast.makeText(LoginActivityView.this, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        viewLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        viewLoading.setVisibility(View.GONE);
    }

    @Override
    public void accessSucesfull(String codeToken) {
        Intent intent = new Intent(LoginActivityView.this, VerificationActivity.class);
        intent.putExtra("token",codeToken);
        intent.putExtra("documentType",DOCUMENT_TYPE);
        intent.putExtra("documentNumber",editTextDni.getText().toString());
        startActivity(intent);
    }

    @Override
    public void accessDenied(String messageDenied) {
        UtilAuthentication.mostrarMensaje(messageDenied,this);
    }

    @Override
    public void showMessageYouMustAcceptTerms() {
        UtilAuthentication.mostrarMensaje("Debe aceptar los términos",this);
    }

    @Override
    public void showMessageDocumentInvalid() {
        UtilAuthentication.mostrarMensaje("El documento ingresado no es válido",this);
    }

    @Override
    public boolean internetConnectionExists() {
        return UtilAuthentication.internetConnectionExists(this);
    }

    @Override
    public void hideSoftKeyboard() {
        UtilAuthentication.hideSoftKeyboard(this);
    }

    public void initLogin(View view) {
        loginPresenter.attemptLogin(DOCUMENT_TYPE,editTextDni.getText().toString(),cbTerminos.isChecked());
    }

    public void viewTermsAndConditions(View view) {
        Intent intent = new Intent(LoginActivityView.this, TermsActivityView.class);
        startActivity(intent);
    }


}
