package pe.com.qallarix.movistarcontigo.embajador.total.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.embajador.total.fragments.RequisitoFragment;
import pe.com.qallarix.movistarcontigo.pojos.Requisito;

public class RequisitosAdapter extends FragmentPagerAdapter {

    private List<Requisito> requisitos;

    public RequisitosAdapter(FragmentManager fm, List<Requisito> requisitos) {
        super(fm);
        this.requisitos = requisitos;
    }

    @Override
    public Fragment getItem(int position) {
        return RequisitoFragment.newInstance(requisitos.get(position));
    }

    @Override
    public int getCount() {
        return requisitos.size();
    }
}
