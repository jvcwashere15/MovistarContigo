package pe.com.qallarix.movistarcontigo.autentication.splash.interfaces;

import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;

public interface SplashPresenter {
    void validateSession();
    void onErrorServer();
    void onSuccesfullActiveSession(Employee employee);
    void onSuccesfullNoActiveSession();
}
