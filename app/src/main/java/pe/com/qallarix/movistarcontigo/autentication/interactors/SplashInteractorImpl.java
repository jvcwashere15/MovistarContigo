package pe.com.qallarix.movistarcontigo.autentication.interactors;

import com.google.firebase.messaging.FirebaseMessaging;

import pe.com.qallarix.movistarcontigo.autentication.interfaces.ServiceEmployeeApi;
import pe.com.qallarix.movistarcontigo.autentication.interfaces.splash.SplashInteractor;
import pe.com.qallarix.movistarcontigo.autentication.interfaces.splash.SplashPresenter;
import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ValidacionToken;
import pe.com.qallarix.movistarcontigo.util.TopicsNotification;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashInteractorImpl implements SplashInteractor {
    private SplashPresenter splashPresenter;

    public SplashInteractorImpl(SplashPresenter splashPresenter) {
        this.splashPresenter = splashPresenter;
    }

    @Override
    public void validateSession(String documentType, String documentNumber, String tokenAccess,
                                String tokenNotification, String mobileType) {
        Call<ValidacionToken> call = WebService1.getInstance(documentNumber)
            .createService(ServiceEmployeeApi.class)
            .validarSesion(documentType,documentNumber,tokenAccess,tokenNotification,mobileType);

        call.enqueue(new Callback<ValidacionToken>() {
            @Override
            public void onResponse(Call<ValidacionToken> call, Response<ValidacionToken> response) {
                if (response.code() == 200){
                    splashPresenter.onSuccesfullActiveSession(response.body().getEmployee());
                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(TopicsNotification.TOPIC_NOTIFICATIONS);
                    splashPresenter.onSuccesfullNoActiveSession();
                }
            }

            @Override
            public void onFailure(Call<ValidacionToken> call, Throwable t) {
                splashPresenter.onErrorServer();
            }
        });
    }
}
