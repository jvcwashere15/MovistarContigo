package pe.com.qallarix.movistarcontigo.autentication.interfaces.login;

public interface LoginPresenter {
    void attemptLogin(String documentType, String documentNumber, boolean isChecked);
    void onSuccessLogin(String codeToken);
    void onErrorLogin(String messageError);
}
