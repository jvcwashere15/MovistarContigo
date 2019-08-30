package pe.com.qallarix.movistarcontigo.ambassador.total.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.ambassador.total.fragments.RequirementFragment;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1.ItemList;

public class RequisitosAdapter extends FragmentPagerAdapter {

    private List<ItemList> requisitos;

    public RequisitosAdapter(FragmentManager fm, List<ItemList> requisitos) {
        super(fm);
        this.requisitos = requisitos;
    }

    @Override
    public Fragment getItem(int position) {
        return RequirementFragment.newInstance(requisitos.get(position));
    }

    public void setRequisitos(List<ItemList> requisitos) {
        this.requisitos = requisitos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return requisitos.size();
    }
}
