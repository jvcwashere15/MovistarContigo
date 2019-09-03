package pe.com.qallarix.movistarcontigo.autentication.login.interfaces;

public interface LoginView {
    void showMessageEmptyDNI();
    void showLoading();
    void hideLoading();
    void accessGranted(String codeToken);
    void accessDenied(String messageDenied);
    void showMessageYouMustAcceptTerms();
    void showMessageDocumentInvalid();
    void hideSoftKeyboard();
}
