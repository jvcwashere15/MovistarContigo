package pe.com.qallarix.movistarcontigo.autentication.splash;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.firebase.messaging.FirebaseMessaging;

import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.autentication.ServiceEmployeeApi;
import pe.com.qallarix.movistarcontigo.autentication.splash.interfaces.SplashInteractor;
import pe.com.qallarix.movistarcontigo.autentication.splash.interfaces.SplashPresenter;
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

    @Override
    public boolean tokenExist(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null && sharedPreferences.contains("tokenAccess"))
            return true;
        return false;
    }

    @Override
    public String getTokenNotification(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null && sharedPreferences.contains("tokenNotification"))
            return sharedPreferences.getString("tokenNotification","");
        return "";
    }

    @Override
    public String getDocumentNumber(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null && sharedPreferences.contains("documentNumber"))
            return sharedPreferences.getString("documentNumber","");
        return "";
    }

    @Override
    public String getToken(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null && sharedPreferences.contains("tokenAccess"))
            return sharedPreferences.getString("tokenAccess","");
        return "";
    }

    @Override
    public void saveNewPreferences(Employee employee, SharedPreferences sharedPreferences,
                                   Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("category", employee.getCategory());
        editor.putString("clase", employee.getClase());
        editor.putString("initials", employee.getInitials());
        editor.putString("documentNumber", employee.getDocumentNumber());
        editor.putString("email", employee.getEmail());
        editor.putString("firstName", employee.getFirstName());
        editor.putString("fullName", employee.getFullName());
        editor.putString("sex", employee.getSex());
        editor.putString("shortName", employee.getShortName());
        editor.putString("cip", employee.getCip());
        editor.putString("vicePresidency", employee.getVicePresidency());
        editor.putString("management", employee.getManagement());
        editor.putString("direction", employee.getDirection());
        editor.putBoolean("isFlexPlace", employee.isFlexPlace());
        editor.commit();
        Analitycs.setUserProperties(context,
                employee.getClase(),employee.getCategory(),
                employee.getVicePresidency(),employee.getManagement(),
                employee.getCip(),employee.getDirection());
    }
}
