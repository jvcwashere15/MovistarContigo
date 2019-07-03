package pe.com.qallarix.movistarcontigo.embajador.total.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.embajador.total.fragments.VPBeneficioFragment;
import pe.com.qallarix.movistarcontigo.pojos.Beneficio;

public class BeneficioAdapter extends FragmentPagerAdapter {

    private List<Beneficio> beneficios;
    private int color;

    public BeneficioAdapter(FragmentManager fm, List<Beneficio> beneficios, int color) {
        super(fm);
        this.beneficios = beneficios;
        this.color = color;
    }

    @Override
    public Fragment getItem(int position) {
        return VPBeneficioFragment.newInstance(beneficios.get(position), color);
    }

    @Override
    public int getCount() {
        return beneficios.size();
    }
}
