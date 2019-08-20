package pe.com.qallarix.movistarcontigo.principal.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pe.com.qallarix.movistarcontigo.R;


public class HerramientasFragment extends Fragment {

    private TabLayout tabLayout;
    private int currentTab = 0;

    public HerramientasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_herramientas, container, false);
        tabLayout = rootView.findViewById(R.id.ambassador_mobile_tabLayout);
        configurarTabs();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setearFragment();
    }

    private void configurarTabs() {

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                currentTab = index;
                setearFragment();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setearFragment() {
        Fragment fragment = null;
        if(currentTab == 0){
            fragment = new InternasFragment();
        }else if(currentTab == 1){
            fragment = new ExternasFragment();
        }
        getChildFragmentManager().beginTransaction().replace(R.id.embajador_movil_content,fragment,"fragment_movil").commit();
    }
}
