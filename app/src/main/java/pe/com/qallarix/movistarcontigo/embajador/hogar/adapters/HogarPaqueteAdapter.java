package pe.com.qallarix.movistarcontigo.embajador.hogar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.embajador.hogar.fragments.HogarPaqueteFragment;
import pe.com.qallarix.movistarcontigo.pojos.PaqueteHogar;

public class HogarPaqueteAdapter extends FragmentPagerAdapter {

    private List<PaqueteHogar> paquetesHogares;

    public HogarPaqueteAdapter(FragmentManager fm, List<PaqueteHogar> paquetesHogares) {
        super(fm);
        this.paquetesHogares = paquetesHogares;
    }

    @Override
    public Fragment getItem(int position) {
        return HogarPaqueteFragment.newInstance(paquetesHogares.get(position));
    }

    @Override
    public int getCount() {
        return paquetesHogares.size();
    }
}
