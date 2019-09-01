package pe.com.qallarix.movistarcontigo.autentication.interfaces.login;

public interface LoginView {

    void showMessageEmptyDNI();
    void showMessageNoInternet();

    void showLoading();
    void hideLoading();

    void accessSucesfull(String codeToken);
    void accessDenied(String messageDenied);

    void showMessageYouMustAcceptTerms();
    void showMessageDocumentInvalid();

    boolean internetConnectionExists();

    void hideSoftKeyboard();
}
