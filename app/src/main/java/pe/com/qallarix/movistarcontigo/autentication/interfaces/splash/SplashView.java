package pe.com.qallarix.movistarcontigo.autentication.interfaces.splash;

import android.content.Context;
import android.content.SharedPreferences;

public interface SplashView {
    void onErrorServer();
    void onSuccesfullActiveSession();
    void onSuccesfullNoActiveSession();
    boolean internetConnectionExists();
    void showViewNoConnectionInternet();
    SharedPreferences getPreferences();
    Context getContextView();
}