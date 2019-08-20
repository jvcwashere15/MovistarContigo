package pe.com.qallarix.movistarcontigo.ambassador.home.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.ambassador.home.fragments.HomePackFragment;
import pe.com.qallarix.movistarcontigo.pojos.PaqueteHogar;

public class HomePackAdapter extends FragmentPagerAdapter {

    private List<PaqueteHogar> paquetesHogares;

    public HomePackAdapter(FragmentManager fm, List<PaqueteHogar> paquetesHogares) {
        super(fm);
        this.paquetesHogares = paquetesHogares;
    }

    @Override
    public Fragment getItem(int position) {
        return HomePackFragment.newInstance(paquetesHogares.get(position));
    }

    @Override
    public int getCount() {
        return paquetesHogares.size();
    }
}
