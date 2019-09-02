package pe.com.qallarix.movistarcontigo.autentication.login;

import pe.com.qallarix.movistarcontigo.autentication.ServiceEmployeeApi;
import pe.com.qallarix.movistarcontigo.autentication.login.interfaces.LoginInteractor;
import pe.com.qallarix.movistarcontigo.autentication.login.interfaces.LoginPresenter;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ResponseToken;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter loginPresenter;

    public LoginInteractorImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void getTokenFromService(String tipoDoc, String numeroDoc) {
        if (loginPresenter != null){
            Call<ResponseToken> call = WebService1.getInstance(numeroDoc)
                    .createService(ServiceEmployeeApi.class)
                    .getGenerateToken(tipoDoc,numeroDoc);
            call.enqueue(new Callback<ResponseToken>() {
                @Override
                public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                    if (response.code() == 200){
                        loginPresenter.onSuccessLogin(response.body().getToken().getCode());
                    }else {
                        loginPresenter.onErrorLogin("problemas al iniciar login");
                    }
                }
                @Override
                public void onFailure(Call<ResponseToken> call, Throwable t) {
                    loginPresenter.onErrorLogin("Error en el servidor");
                }
            });
        }
    }
}
