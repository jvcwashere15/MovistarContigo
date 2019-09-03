package pe.com.qallarix.movistarcontigo.autentication.verification.interfaces;

import android.content.Intent;
import android.content.SharedPreferences;

import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;

public interface VerificationInteractor {
    void validateToken(String documentType, String documentNumber,
                       String tokenAccess, String tokenNotification);
    void resubmitToken(String documentType, String documentNumber);
    void saveDataEmployeeToPreferences(Employee employee, SharedPreferences sharedPreferences);
    String getDocumentNumberFromIntent(Intent intent);
}
