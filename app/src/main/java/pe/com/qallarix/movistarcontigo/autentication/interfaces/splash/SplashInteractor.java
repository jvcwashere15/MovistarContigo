package pe.com.qallarix.movistarcontigo.autentication.interfaces.splash;

public interface SplashInteractor {
    void validateSession(String documentType, String documentNumber, String tokenAccess,
                         String tokenNotification, String mobileType);
}
