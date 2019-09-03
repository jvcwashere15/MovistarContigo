package pe.com.qallarix.movistarcontigo.autentication.verification.interfaces;

import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;

public interface VerificationView {
    void showLoadingView(String message);
    void hideLoadingView();
    void showMessageInvalidToken();
    void hideSoftKeyboard();
    void goToWelcomeScreen();
    void displayDNI();
    void saveDataFromIntentToCache(String documentNumber);
    void onSuccessValidateToken(Employee employee);

    void showMessageErrorValidatingToken();

    void showMessageDeniedToken();
}
