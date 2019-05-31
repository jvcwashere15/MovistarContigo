package pe.com.qallarix.movistarcontigo.principal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.noticias.News;
import pe.com.qallarix.movistarcontigo.principal.fragments.NoticiaDashboardFragment;

public class NoticiasDashboardAdapter extends FragmentPagerAdapter {
    private List<News> noticias;
    private int numberOfItems;

    public NoticiasDashboardAdapter(FragmentManager fm, List<News> noticias,int numberOfItems) {
        super(fm);
        this.noticias = noticias;
        this.numberOfItems = numberOfItems;
    }

    @Override
    public Fragment getItem(int i) {
        if (noticias == null) return new NoticiaDashboardFragment();
        return NoticiaDashboardFragment.newInstance(noticias.get(i));
    }

    @Override
    public int getCount() {
        return numberOfItems;
    }
}
