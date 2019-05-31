package pe.com.qallarix.movistarcontigo.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import pe.com.qallarix.movistarcontigo.util.QueryUtils;

public class CerrarSesionLoader extends AsyncTaskLoader<Integer> {

    private String mUrl;

    public CerrarSesionLoader(@NonNull Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        if (mUrl == null) return null;
        int sesionActiva = QueryUtils.fetchCerrarSesionData(mUrl);
        return sesionActiva;
    }
}
