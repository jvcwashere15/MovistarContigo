package pe.com.qallarix.movistarcontigo.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import pe.com.qallarix.movistarcontigo.news.NewsImageFragment;

public class NewsImageAdapter extends FragmentPagerAdapter {
    private List<String> noticias;

    public NewsImageAdapter(FragmentManager fm, List<String> noticias) {
        super(fm);
        this.noticias = noticias;
    }

    @Override
    public Fragment getItem(int position) {
        if (noticias == null) return new NewsImageFragment();
        return NewsImageFragment.newInstance(noticias.get(position));
    }

    @Override
    public int getCount() {
        return noticias.size();
    }
}
