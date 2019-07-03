package pe.com.qallarix.movistarcontigo.embajador.total.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.embajador.total.fragments.PlanMovistarTotalFragment;
import pe.com.qallarix.movistarcontigo.pojos.PlanMovistarTotal;


public class PlanMovistarTotalAdapter extends FragmentPagerAdapter {

    private List<PlanMovistarTotal> planMovistarTotals;

    public PlanMovistarTotalAdapter(FragmentManager fm, List<PlanMovistarTotal> planMovistarTotals) {
        super(fm);
        this.planMovistarTotals = planMovistarTotals;
    }

    @Override
    public Fragment getItem(int position) {
        return PlanMovistarTotalFragment.newInstance(planMovistarTotals.get(position));
    }

    @Override
    public int getCount() {
        return planMovistarTotals.size();
    }
}
