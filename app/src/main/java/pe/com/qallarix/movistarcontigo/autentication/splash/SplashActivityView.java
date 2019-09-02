package pe.com.qallarix.movistarcontigo.autentication.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.BuildConfig;
import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autentication.UtilAuthentication;
import pe.com.qallarix.movistarcontigo.autentication.splash.interfaces.SplashPresenter;
import pe.com.qallarix.movistarcontigo.autentication.splash.interfaces.SplashView;
import pe.com.qallarix.movistarcontigo.autentication.login.LoginActivityView;
import pe.com.qallarix.movistarcontigo.main.MainActivity;

public class SplashActivityView extends AppCompatActivity implements SplashView {
    private TextView tvVersionName;
    private SplashPresenter splashPresenter;
    private View vNoConnectionInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindViews();
        setVersionName();
        splashPresenter = new SplashPresenterImpl(this);

            if (UtilAuthentication.internetConnectionExists(this)) {
                splashPresenter.validateSession();
            } else {
                showViewNoConnectionInternet();
            }


    }


    @Override
    public void showViewNoConnectionInternet() {
        vNoConnectionInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorServer() {
        UtilAuthentication.mostrarMensaje(
                "Se produjo un problema al obtener los datos de sesión, reintentar.",
                this);
    }

    @Override
    public void onSuccesfullActiveSession() {
        goToMainDashBoardScreen();
    }


    @Override
    public void onSuccesfullNoActiveSession() {
        goToLoginScreen();
    }


    @Override
    public boolean internetConnectionExists() {
        return UtilAuthentication.internetConnectionExists(this);
    }

    @Override
    public SharedPreferences getPreferences() {
        return getSharedPreferences("quallarix.movistar.pe.com.quallarix",Context.MODE_PRIVATE);
    }

    @Override
    public Context getContextView() {
        return SplashActivityView.this;
    }

    public void bindViews() {
        tvVersionName = findViewById(R.id.splash_tvVersionName);
        vNoConnectionInternet = findViewById(R.id.splash_vSinConexion);
    }


    private void goToMainDashBoardScreen() {
        Intent loginIntent = new Intent(SplashActivityView.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void goToLoginScreen() {
        Intent loginIntent = new Intent(SplashActivityView.this, LoginActivityView.class);
        startActivity(loginIntent);
        finish();
    }

    public void setVersionName() {
        tvVersionName.setText(BuildConfig.VERSION_NAME);
    }

}
