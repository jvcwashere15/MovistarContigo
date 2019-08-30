package pe.com.qallarix.movistarcontigo.ambassador.total.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.ambassador.total.fragments.FacturationTypeFragment;
import pe.com.qallarix.movistarcontigo.pojos.TipoFacturacion;


public class TipoFacturacionAdapter extends FragmentPagerAdapter {

    private List<TipoFacturacion> tipoFacturacions;

    public TipoFacturacionAdapter(FragmentManager fm, List<TipoFacturacion> tipoFacturacions) {
        super(fm);
        this.tipoFacturacions = tipoFacturacions;
    }

    @Override
    public Fragment getItem(int position) {
        return FacturationTypeFragment.newInstance(tipoFacturacions.get(position));
    }

    @Override
    public int getCount() {
        return tipoFacturacions.size();
    }
}
