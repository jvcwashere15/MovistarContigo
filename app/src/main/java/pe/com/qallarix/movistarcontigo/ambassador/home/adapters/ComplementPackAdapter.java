package pe.com.qallarix.movistarcontigo.ambassador.home.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.ambassador.home.fragments.ComplementFragment;
import pe.com.qallarix.movistarcontigo.ambassador.home.pojos.ComplementList;

public class ComplementPackAdapter extends FragmentPagerAdapter {

    private List<ComplementList> complementLists;

    public ComplementPackAdapter(FragmentManager fm, List<ComplementList> complementLists) {
        super(fm);
        this.complementLists = complementLists;
    }

    @Override
    public Fragment getItem(int position) {
        return ComplementFragment.newInstance(complementLists.get(position));
    }

    @Override
    public int getCount() {
        return complementLists.size();
    }
}
