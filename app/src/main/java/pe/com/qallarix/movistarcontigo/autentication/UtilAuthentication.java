package pe.com.qallarix.movistarcontigo.autentication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import pe.com.qallarix.movistarcontigo.R;

public class UtilAuthentication {
    public static boolean documentoEsValido(String documentNumber) {
        if (documentNumber.length() == 8 || documentNumber.length() == 9)
            return true;
        return false;
    }

    public static boolean acceptedsTerms(boolean isChecked) {
        if (isChecked)
            return true;
        return false;
    }

    public static boolean internetConnectionExists(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void mostrarMensaje(String m, Activity activity){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogMensajeStyle);
        builder.setMessage(m);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        if (!activity.isFinishing()) alertDialog.show();
        else Toast.makeText(activity, m, Toast.LENGTH_SHORT).show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View mViewWithFocus = activity.getCurrentFocus();
        if (mViewWithFocus != null){
            inputMethodManager.hideSoftInputFromWindow(mViewWithFocus.getWindowToken(), 0);
        }
    }

}
