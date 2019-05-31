package pe.com.qallarix.movistarcontigo.embajador.movil.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.embajador.movil.fragments.MovilPasosFragment;
import pe.com.qallarix.movistarcontigo.embajador.movil.pojos.SelfhelpList;

public class MovilPasosAdapter extends FragmentPagerAdapter {

    private List<SelfhelpList> pasos;

    public MovilPasosAdapter(FragmentManager fm, List<SelfhelpList> pasos) {
        super(fm);
        this.pasos = pasos;
    }

    @Override
    public Fragment getItem(int position) {
        return MovilPasosFragment.newInstance(pasos.get(position),position + 1);
    }

    @Override
    public int getCount() {
        return pasos.size();
    }
}
