package pe.com.qallarix.movistarcontigo.autentication.interfaces.splash;

import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;

public interface SplashView {
    void onErrorServer();
    void onSuccesfullActiveSession(Employee employe, String currentToken);
    void onSuccesfullNoActiveSession();

    boolean internetConnectionExists();
    void showViewNoConnectionInternet();
    void setVersionName();
    boolean tokenExist();

    String getTokenNotification();
    String getDocumentNumberFromPreferences();
    String getTokenFromPreferences();
}