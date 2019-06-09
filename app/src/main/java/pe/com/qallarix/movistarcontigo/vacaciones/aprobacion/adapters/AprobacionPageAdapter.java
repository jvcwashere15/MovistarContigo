package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.fragments.AprobadasFragment;
import pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.fragments.PendientesFragment;

public class AprobacionPageAdapter extends FragmentPagerAdapter {

    Context context;

    public AprobacionPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return PendientesFragment.newInstance();
            default: return AprobadasFragment.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Pendientes";
        else return "Aprobadas";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
