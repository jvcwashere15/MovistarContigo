package pe.com.qallarix.movistarcontigo.autentication.login.interfaces;

public interface LoginInteractor {
    void getTokenFromService(String tipoDoc, String numeroDoc);
}
