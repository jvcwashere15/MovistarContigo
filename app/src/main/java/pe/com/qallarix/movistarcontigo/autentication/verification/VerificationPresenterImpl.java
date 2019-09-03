package pe.com.qallarix.movistarcontigo.autentication.verification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationInteractor;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationPresenter;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationView;

public class VerificationPresenterImpl implements VerificationPresenter {
    private VerificationView verificationView;
    private VerificationInteractor verificationInteractor;

    public VerificationPresenterImpl(VerificationView verificationView) {
        this.verificationView = verificationView;
        this.verificationInteractor = new VerificationInteractorImpl(this);
    }

    @Override
    public void getDocumentNumberFromIntent(Intent intent) {
        verificationInteractor.getDocumentNumberFromIntent(intent);
    }

    @Override
    public void validateToken(String documentType, String documentNumber,
                              String tokenAccess, String tokenNotification) {
        if (tokenAccess != null && !TextUtils.isEmpty(tokenAccess) && tokenAccess.length()==5) {
            verificationView.hideSoftKeyboard();
            verificationView.showLoadingView("Validando código");
            verificationInteractor.validateToken(documentType, documentNumber,
                    tokenAccess, tokenNotification);
        } else {
            verificationView.showMessageInvalidToken();
        }
    }

    @Override
    public void resubmitToken(String documentType, String documentNumber) {

    }


    @Override
    public void saveDataEmployeeToPreferences(Employee employee,
                                              SharedPreferences sharedPreferences) {

    }

    @Override
    public void saveTokenNotificationOnPreferences(String tokenNotification,
                                                   SharedPreferences sharedPreferences) {

    }

    @Override
    public void onSuccessDataFromIntent() {

    }

    @Override
    public void onErrorDataFromIntent() {

    }

    @Override
    public void onSuccessValidateToken(Employee employee) {
        verificationView.onSuccessValidateToken(employee);
    }

    @Override
    public void onDeniedAccessToken() {
        verificationView.showMessageDeniedToken();
    }



    @Override
    public void onErrorValidateToken() {
        verificationView.hideLoadingView();
        verificationView.showMessageErrorValidatingToken();
    }

    @Override
    public void getTokenNotification() {

    }
}
