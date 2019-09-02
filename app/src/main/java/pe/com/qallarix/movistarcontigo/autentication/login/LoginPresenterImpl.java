package pe.com.qallarix.movistarcontigo.autentication.login;

import android.text.TextUtils;

import pe.com.qallarix.movistarcontigo.autentication.UtilAuthentication;
import pe.com.qallarix.movistarcontigo.autentication.login.interfaces.LoginInteractor;
import pe.com.qallarix.movistarcontigo.autentication.login.interfaces.LoginPresenter;
import pe.com.qallarix.movistarcontigo.autentication.login.interfaces.LoginView;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;

public class LoginPresenterImpl extends TranquiParentActivity implements LoginPresenter {
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl(this);
    }


    @Override
    public void attemptLogin(String documentType, String documentNumber) {
        if (loginView != null){
            loginView.hideSoftKeyboard();
            loginView.showLoading();
            loginInteractor.getTokenFromService(documentType,documentNumber);
        }
    }

    @Override
    public void onSuccessLogin(String codeToken) {
        if (loginView != null){
            loginView.hideLoading();
            loginView.accessSucesfull(codeToken);
        }
    }

    @Override
    public void onErrorLogin(String messageError) {
        if (loginView != null){
            loginView.hideLoading();
            loginView.accessDenied(messageError);
        }
    }
}
