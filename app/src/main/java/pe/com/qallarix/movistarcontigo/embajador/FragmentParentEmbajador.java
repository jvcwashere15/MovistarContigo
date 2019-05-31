package pe.com.qallarix.movistarcontigo.embajador;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class FragmentParentEmbajador extends Fragment {
    public void configurarFlechasViewPager(ViewPager viewPager, final ImageView ivAtras, final ImageView ivAdelante, final int maxPage) {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    ivAtras.setVisibility(View.GONE);
                    ivAdelante.setVisibility(View.VISIBLE);
                }
                else if (position == maxPage) {
                    ivAtras.setVisibility(View.VISIBLE);
                    ivAdelante.setVisibility(View.GONE);
                }
                else{
                    ivAtras.setVisibility(View.VISIBLE);
                    ivAdelante.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });
    }
}
