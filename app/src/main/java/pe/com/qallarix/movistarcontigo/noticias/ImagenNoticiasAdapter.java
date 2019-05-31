package pe.com.qallarix.movistarcontigo.noticias;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import java.util.List;

public class ImagenNoticiasAdapter extends FragmentPagerAdapter {
    private List<String> noticias;

    public ImagenNoticiasAdapter(FragmentManager fm, List<String> noticias) {
        super(fm);
        this.noticias = noticias;
    }

    @Override
    public Fragment getItem(int position) {
        if (noticias == null) return new ImagenNoticiaFragment();
        return ImagenNoticiaFragment.newInstance(noticias.get(position));
    }

    @Override
    public int getCount() {
        return noticias.size();
    }
}
