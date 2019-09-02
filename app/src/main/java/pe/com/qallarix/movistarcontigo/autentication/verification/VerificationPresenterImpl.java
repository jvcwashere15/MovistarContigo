package pe.com.qallarix.movistarcontigo.autentication.verification;

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
}
