package pe.com.qallarix.movistarcontigo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.embajador.hogar.fragments.ComboFragment;
import pe.com.qallarix.movistarcontigo.pojos.Combo;

public class ComboPaqueteAdapter extends FragmentPagerAdapter {

    private List<Combo> combos;

    public ComboPaqueteAdapter(FragmentManager fm, List<Combo> combos) {
        super(fm);
        this.combos = combos;
    }

    @Override
    public Fragment getItem(int position) {
        return ComboFragment.newInstance(combos.get(position));
    }

    @Override
    public int getCount() {
        return combos.size();
    }
}
