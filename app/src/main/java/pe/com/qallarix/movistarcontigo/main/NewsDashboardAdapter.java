package pe.com.qallarix.movistarcontigo.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.news.pojos.News;
import pe.com.qallarix.movistarcontigo.main.fragments.NewsDashboardFragment;

public class NewsDashboardAdapter extends FragmentPagerAdapter {
    private List<News> noticias;
    private int numberOfItems;

    public NewsDashboardAdapter(FragmentManager fm, List<News> noticias, int numberOfItems) {
        super(fm);
        this.noticias = noticias;
        this.numberOfItems = numberOfItems;
    }

    @Override
    public Fragment getItem(int i) {
        if (noticias == null) return new NewsDashboardFragment();
        return NewsDashboardFragment.newInstance(noticias.get(i));
    }

    @Override
    public int getCount() {
        return numberOfItems;
    }
}
