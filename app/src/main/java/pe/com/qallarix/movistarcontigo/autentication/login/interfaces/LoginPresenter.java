package pe.com.qallarix.movistarcontigo.autentication.login.interfaces;

public interface LoginPresenter {
    //for view
    void attemptLogin(String documentType,String documentNumber);
    //for interactor
    void onSuccessLogin(String codeToken);
    void onErrorLogin(String messageError);
}
