package pe.com.qallarix.movistarcontigo.ambassador.home.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.ambassador.home.fragments.HomeStepsFragment;
import pe.com.qallarix.movistarcontigo.ambassador.home.pojos.SelfhelpList;

public class HogarPasosAdapter extends FragmentPagerAdapter {

    private List<SelfhelpList> selfhelpLists;

    public HogarPasosAdapter(FragmentManager fm, List<SelfhelpList> selfhelpLists) {
        super(fm);
        this.selfhelpLists = selfhelpLists;
    }

    @Override
    public Fragment getItem(int position) {
        return HomeStepsFragment.newInstance(selfhelpLists.get(position),position + 1);
    }

    @Override
    public int getCount() {
        return selfhelpLists.size();
    }
}
