package pe.com.qallarix.movistarcontigo.autentication.verification.interfaces;

import android.content.Intent;
import android.content.SharedPreferences;

import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;

public interface VerificationPresenter {
    //methods for view
    void getDocumentNumberFromIntent(Intent intent);
    void validateToken(String documentType, String documentNumber,
                       String tokenAccess, String tokenNotification);
    void resubmitToken(String documentType, String documentNumber);
    void saveDataEmployeeToPreferences(Employee employee, SharedPreferences sharedPreferences);
    void saveTokenNotificationOnPreferences(String tokenNotification, SharedPreferences sharedPreferences);

    //methods for interactor
    void onSuccessDataFromIntent();
    void onErrorDataFromIntent();

    void onSuccessValidateToken(Employee employee);
    void onDeniedAccessToken();
    void onErrorValidateToken();

    void getTokenNotification();
}
