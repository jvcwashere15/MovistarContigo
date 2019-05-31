package pe.com.qallarix.movistarcontigo.embajador.movil.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.embajador.movil.fragments.MovilPaqueteFragment;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.OfferList;

public class MovilPaqueteAdapter extends FragmentPagerAdapter {

    private List<OfferList> paquetesMoviles;

    public MovilPaqueteAdapter(FragmentManager fm, List<OfferList> paquetesMoviles) {
        super(fm);
        this.paquetesMoviles = paquetesMoviles;
    }

    @Override
    public Fragment getItem(int position) {
        return MovilPaqueteFragment.newInstance(paquetesMoviles.get(position));
    }

    @Override
    public int getCount() {
        return paquetesMoviles.size();
    }
}
