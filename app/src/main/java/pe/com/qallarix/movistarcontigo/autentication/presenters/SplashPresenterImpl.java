package pe.com.qallarix.movistarcontigo.autentication.presenters;

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
                if (splashView.tokenExist()) {
                    splashView.setVersionName();
                    splashInteractor.validateSession(
                            DOCUMENT_TYPE,
                            splashView.getDocumentNumberFromPreferences(),
                            splashView.getTokenFromPreferences(),
                            splashView.getTokenNotification(),
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
            splashView.onSuccesfullActiveSession(employee, splashView.getTokenFromPreferences());
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
        timer.schedule(timerTask, 2500);
    }
}
