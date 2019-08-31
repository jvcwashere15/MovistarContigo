package pe.com.qallarix.movistarcontigo.autentication.login.interfaces;

public interface LoginPresenter {
    void attemptLogin(String documentType, String documentNumber, boolean isChecked);
    void onSuccessLogin(String codeToken);
    void onErrorLogin(String messageError);
}
