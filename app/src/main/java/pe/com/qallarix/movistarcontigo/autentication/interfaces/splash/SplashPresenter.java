package pe.com.qallarix.movistarcontigo.autentication.interfaces.splash;

import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;

public interface SplashPresenter {
    void validateSession();
    void onErrorServer();
    void onSuccesfullActiveSession(Employee employee);
    void onSuccesfullNoActiveSession();
}
