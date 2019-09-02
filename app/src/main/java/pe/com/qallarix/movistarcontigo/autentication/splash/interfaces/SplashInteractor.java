package pe.com.qallarix.movistarcontigo.autentication.splash.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;

public interface SplashInteractor {
    void validateSession(String documentType, String documentNumber, String tokenAccess,
                         String tokenNotification, String mobileType);
    boolean tokenExist(SharedPreferences sharedPreferences);
    String getTokenNotification(SharedPreferences sharedPreferences);
    String getDocumentNumber(SharedPreferences sharedPreferences);
    String getToken(SharedPreferences sharedPreferences);
    void saveNewPreferences(Employee employee, SharedPreferences sharedPreferences,
                            Context context);
}
