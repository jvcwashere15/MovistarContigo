package pe.com.qallarix.movistarcontigo.autentication.verification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import pe.com.qallarix.movistarcontigo.autentication.ServiceEmployeeApi;
import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ResponseToken;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ValidacionToken;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationInteractor;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationPresenter;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationInteractorImpl implements VerificationInteractor {
    private VerificationPresenter verificationPresenter;

    public VerificationInteractorImpl(VerificationPresenter verificationPresenter) {
        this.verificationPresenter = verificationPresenter;
    }

    @Override
    public void validateToken(String documentType, String documentNumber,
                              String tokenAccess, String tokenNotification) {
        Call<ValidacionToken> call = WebService1.getInstance(documentNumber)
                .createService(ServiceEmployeeApi.class)
                .validarToken(documentType,documentNumber,tokenAccess,tokenNotification,"1");
        call.enqueue(new Callback<ValidacionToken>() {
            @Override
            public void onResponse(Call<ValidacionToken> call, Response<ValidacionToken> response) {
                if (response.code() == 200){
                    Employee employee = response.body().getEmployee();
                    verificationPresenter.onSuccessValidateToken(employee);

                }else{
                    verificationPresenter.onDeniedAccessToken();
                }
            }

            @Override
            public void onFailure(Call<ValidacionToken> call, Throwable t) {
                verificationPresenter.onErrorValidateToken();
            }
        });
    }

    @Override
    public void resubmitToken(String documentType, String documentNumber) {
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

    @Override
    public void saveDataEmployeeToPreferences(Employee employee, SharedPreferences sharedPreferences) {

    }

    @Override
    public String getDocumentNumberFromIntent(Intent intent) {
        if (intent != null){
            if (intent.getExtras() != null && intent.getExtras().containsKey("documentNumber")){
                return intent.getExtras().getString("documentNumber",null);
            }
        }
        return null;
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
}
