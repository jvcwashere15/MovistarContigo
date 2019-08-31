package pe.com.qallarix.movistarcontigo.autentication.login;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UtilLogin {
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


}
