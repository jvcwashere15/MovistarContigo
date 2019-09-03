package pe.com.qallarix.movistarcontigo.autentication.verification;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.autentication.ServiceEmployeeApi;
import pe.com.qallarix.movistarcontigo.autentication.UtilAuthentication;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationPresenter;
import pe.com.qallarix.movistarcontigo.autentication.verification.interfaces.VerificationView;
import pe.com.qallarix.movistarcontigo.autentication.login.LoginActivityView;
import pe.com.qallarix.movistarcontigo.autentication.pojos.Employee;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ResponseToken;
import pe.com.qallarix.movistarcontigo.autentication.pojos.ValidacionToken;
import pe.com.qallarix.movistarcontigo.analitycs.Analitycs;
import pe.com.qallarix.movistarcontigo.autentication.welcome.WelcomeActivityView;
import pe.com.qallarix.movistarcontigo.util.TopicsNotification;
import pe.com.qallarix.movistarcontigo.util.TranquiParentActivity;
import pe.com.qallarix.movistarcontigo.util.WebService1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerificationActivityView extends AppCompatActivity implements VerificationView {

    //Fields for cache
    private String mDocumentNumber, mTokenAccess;
    private final String DOCUMENT_TYPE = "1";

    //Views to use
    private View vProgressScreen;
    private TextView tvDNI;
    private Pinview pinViewCode;

    //Presenter
    private VerificationPresenter verificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        bindViews();
        setUpPinViewCode();
        verificationPresenter.getDocumentNumberFromIntent(getIntent());
    }

    private void bindViews() {
        vProgressScreen = findViewById(R.id.verificacion_viewLoading);
        pinViewCode = findViewById(R.id.verification_pinviewCode);
        tvDNI = findViewById(R.id.verification_tvDNI);
    }

    private void setUpPinViewCode() {
        pinViewCode.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                mTokenAccess = pinViewCode.getValue();
                getTokenNotificationAndValidateTokenAccess();
            }
        });
    }

    private void getTokenNotificationAndValidateTokenAccess() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Firebase Token Id/", "getInstanceId failed",
                                    task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String tokenNotification = task.getResult().getToken();
                        Log.d("Firebase Token Id/",
                                "FIREBASE TOKEN ID: " + tokenNotification);
                        SharedPreferences sharedPref = getSharedPreferences(
                                "quallarix.movistar.pe.com.quallarix",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("tokenNotification", tokenNotification);
                        editor.commit();
                        verificationPresenter.validateToken(DOCUMENT_TYPE,mDocumentNumber,
                                mTokenAccess,tokenNotification);
                    }
                });
    }

    public void clickNull(View view) { }

    public void validateToken(View view) {
        mTokenAccess = pinViewCode.getValue();
        getTokenNotificationAndValidateTokenAccess();
    }

    public void resubmitCode(View view) {
        verificationPresenter.resubmitToken(DOCUMENT_TYPE, mDocumentNumber);
    }

    public void backToLoginScreen(View view) {
        startActivity(new Intent(this, LoginActivityView.class));
        finish();
    }

    @Override
    public void goToWelcomeScreen() {
        Intent intent = new Intent(VerificationActivityView.this, WelcomeActivityView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoadingView(String message) {
        vProgressScreen.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        vProgressScreen.setVisibility(View.GONE);
    }

    @Override
    public void showMessageInvalidToken() {
        showMessageDialog(getString(R.string.verification_dialog_token_invalid));

    }

    private void showMessageDialog(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.DialogMensajeStyle);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        if (!isFinishing()) alertDialog.show();
        else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveDataFromIntentToCache(String documentNumber) {
        this.mDocumentNumber = documentNumber;
    }

    @Override
    public void displayDNI() {
        if (mDocumentNumber.length() == 8){
            tvDNI.setText(getString(R.string.verification_display_dni) + mDocumentNumber);
        }else{
            tvDNI.setText(getString(R.string.verification_display_dni) + mDocumentNumber);
        }
    }

    @Override
    public void onSuccessValidateToken(Employee employee) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                "quallarix.movistar.pe.com.quallarix", Context.MODE_PRIVATE);
        verificationPresenter.saveDataEmployeeToPreferences(employee,sharedPreferences);

        FirebaseMessaging.getInstance().subscribeToTopic(TopicsNotification.TOPIC_NOTIFICATIONS);
        Analitycs.logEventoLogin(VerificationActivityView.this);
        Analitycs.setUserProperties(VerificationActivityView.this,
                employee.getClase(),employee.getCategory(),
                employee.getVicePresidency(),employee.getManagement(),
                employee.getCip(),employee.getDirection());
        goToWelcomeScreen();
    }

    @Override
    public void showMessageErrorValidatingToken() {
        Toast.makeText(VerificationActivityView.this,
                getString(R.string.verification_error_validating_token),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageDeniedToken() {
        showMessageDialog(getString(R.string.verification));
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View mViewWithFocus = getCurrentFocus();
        if (mViewWithFocus != null){
            inputMethodManager.hideSoftInputFromWindow(mViewWithFocus.getWindowToken(), 0);
        }
    }


}
