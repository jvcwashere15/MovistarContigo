package pe.com.qallarix.movistarcontigo.ambassador.mobile.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.ambassador.mobile.fragments.MobilePacksFragment;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos.OfferList;

public class MovilPaqueteAdapter extends FragmentPagerAdapter {

    private List<OfferList> paquetesMoviles;

    public MovilPaqueteAdapter(FragmentManager fm, List<OfferList> paquetesMoviles) {
        super(fm);
        this.paquetesMoviles = paquetesMoviles;
    }

    @Override
    public Fragment getItem(int position) {
        return MobilePacksFragment.newInstance(paquetesMoviles.get(position));
    }

    @Override
    public int getCount() {
        return paquetesMoviles.size();
    }
}
