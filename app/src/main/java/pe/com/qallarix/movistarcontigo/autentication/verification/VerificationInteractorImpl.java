package pe.com.qallarix.movistarcontigo.autentication.verification;

import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationInteractor;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationPresenter;

public class VerificationInteractorImpl implements VerificationInteractor {
    private VerificationPresenter verificationPresenter;

    public VerificationInteractorImpl(VerificationPresenter verificationPresenter) {
        this.verificationPresenter = verificationPresenter;
    }
}
