package pe.com.qallarix.movistarcontigo.embajador.hogar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.embajador.hogar.fragments.ComplementoFragment;
import pe.com.qallarix.movistarcontigo.embajador.hogar.pojos.ComplementList;

public class ComplementoPaqueteAdapter extends FragmentPagerAdapter {

    private List<ComplementList> complementLists;

    public ComplementoPaqueteAdapter(FragmentManager fm, List<ComplementList> complementLists) {
        super(fm);
        this.complementLists = complementLists;
    }

    @Override
    public Fragment getItem(int position) {
        return ComplementoFragment.newInstance(complementLists.get(position));
    }

    @Override
    public int getCount() {
        return complementLists.size();
    }
}
