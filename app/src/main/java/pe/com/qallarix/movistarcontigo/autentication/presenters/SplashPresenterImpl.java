package pe.com.qallarix.movistarcontigo.autentication.presenters;

import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import pe.com.qallarix.movistarcontigo.autentication.interactors.SplashInteractorImpl;
import pe.com.qallarix.movistarcontigo.autentication.interfaces.splash.SplashInteractor;
import pe.com.qallarix.movistarcontigo.autentication.interfaces.splash.SplashPresenter;
import pe.com.qallarix.movistarcontigo.autentication.interfaces.splash.SplashView;
import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;

public class SplashPresenterImpl implements SplashPresenter {
    private SplashView splashView;
    private SplashInteractor splashInteractor;
    private final String DOCUMENT_TYPE = "1";
    private final String MOBILE_TYPE = "1";


    public SplashPresenterImpl(SplashView splashView) {
        this.splashView = splashView;
        this.splashInteractor = new SplashInteractorImpl(this);
    }

    @Override
    public void validateSession() {
        if (splashView != null) {
            if (splashView.internetConnectionExists()) {
                SharedPreferences sharedPreferences = splashView.getPreferences();
                if (splashInteractor.tokenExist(sharedPreferences)) {
                    splashInteractor.validateSession(
                            DOCUMENT_TYPE,
                            splashInteractor.getDocumentNumber(sharedPreferences),
                            splashInteractor.getToken(sharedPreferences),
                            splashInteractor.getTokenNotification(sharedPreferences),
                            MOBILE_TYPE);
                } else
                    waitAndReportSessionNotActive();
            } else {
                splashView.showViewNoConnectionInternet();
            }
        }
    }

    @Override
    public void onErrorServer() {
        if (splashView != null)
            splashView.onErrorServer();
    }

    @Override
    public void onSuccesfullActiveSession(Employee employee) {
        if (splashView != null)
            splashInteractor.saveNewPreferences(employee,
                    splashView.getPreferences(),
                    splashView.getContextView());
            splashView.onSuccesfullActiveSession();
    }

    @Override
    public void onSuccesfullNoActiveSession() {
        if (splashView != null) {
            waitAndReportSessionNotActive();
        }

    }

    private void waitAndReportSessionNotActive() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                splashView.onSuccesfullNoActiveSession();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000);
    }
}
