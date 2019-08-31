package pe.com.qallarix.movistarcontigo.autentication.login.presenters;

import android.text.TextUtils;

import pe.com.qallarix.movistarcontigo.autentication.login.UtilLogin;
import pe.com.qallarix.movistarcontigo.autentication.login.interactors.LoginInteractorImpl;
import pe.com.qallarix.movistarcontigo.autentication.login.interfaces.LoginInteractor;
import pe.com.qallarix.movistarcontigo.autentication.login.interfaces.LoginPresenter;
import pe.com.qallarix.movistarcontigo.autentication.login.interfaces.LoginView;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;

public class LoginPresenterImpl extends TranquiParentActivity implements LoginPresenter {
    LoginView loginView;
    LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl(this);
    }


    @Override
    public void attemptLogin(String documentType, String documentNumber, boolean isChecked) {
        if (loginView != null){

            if (documentNumber != null && !TextUtils.isEmpty(documentNumber)) {
                if (UtilLogin.documentoEsValido(documentNumber)){
                    if (UtilLogin.acceptedsTerms(isChecked)){
                        if (loginView.internetConnectionExists()){
                            loginView.showLoading();
                            loginInteractor.getTokenFromService(documentType,documentNumber);
                        }
                        else
                            loginView.showMessageNoInternet();
                    }else
                        loginView.showMessageYouMustAcceptTerms();
                }else
                    loginView.showMessageDocumentInvalid();
            } else
                loginView.showMessageEmptyDNI();

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
