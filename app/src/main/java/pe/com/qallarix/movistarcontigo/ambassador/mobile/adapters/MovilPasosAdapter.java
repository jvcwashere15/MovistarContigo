package pe.com.qallarix.movistarcontigo.ambassador.mobile.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.ambassador.mobile.fragments.MobileStepsFragment;
import pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos.SelfhelpList;

public class MovilPasosAdapter extends FragmentPagerAdapter {

    private List<SelfhelpList> pasos;

    public MovilPasosAdapter(FragmentManager fm, List<SelfhelpList> pasos) {
        super(fm);
        this.pasos = pasos;
    }

    @Override
    public Fragment getItem(int position) {
        return MobileStepsFragment.newInstance(pasos.get(position),position + 1);
    }

    @Override
    public int getCount() {
        return pasos.size();
    }
}
