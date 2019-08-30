package pe.com.qallarix.movistarcontigo.ambassador.total.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.total.adapters.RequisitosAdapter;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1.Block;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1.ItemList;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1.Tab1;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmbassadorTotalWhatIsFragment extends Fragment {


    private List<ItemList> requirements;
    private List<ItemList> products;

    private ViewPager viewPager;
    private DotIndicator dotIndicator;
    private Tab1 tab1;
    private ImageView ivProduct1,ivProduct2,ivProduct3,ivProduct4;
    private TextView tvProduct1,tvProduct2,tvProduct3,tvProduct4;

    private final int WHAT_IS = 0;
    private final int REQUIREMENTS = 1;

    private RequisitosAdapter requisitosAdapter;

    private static final String ARGUMENT_AMBASSADOR_TOTAL_WHATIS = "ambassadorTotalWhatIs";


    public AmbassadorTotalWhatIsFragment() {
        // Required empty public constructor
    }

    public static AmbassadorTotalWhatIsFragment newInstance(Tab1 tab1) {
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_AMBASSADOR_TOTAL_WHATIS,(Serializable) tab1);
        AmbassadorTotalWhatIsFragment fragment = new AmbassadorTotalWhatIsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tab1 = (Tab1) getArguments().getSerializable(ARGUMENT_AMBASSADOR_TOTAL_WHATIS);
        requirements = tab1.getBlock().get(REQUIREMENTS).getList();
        products = tab1.getBlock().get(WHAT_IS).getList();
        View rootView = inflater.inflate(R.layout.fragment_ambassador_total_whatis, container, false);
        viewPager = rootView.findViewById(R.id.tab1_requirements_vpRequirements);
        dotIndicator = rootView.findViewById(R.id.tab1_requirements_dotIndicator);
        tvProduct1 = rootView.findViewById(R.id.tab1_whatis_tvProduct1);
        tvProduct2 = rootView.findViewById(R.id.tab1_whatis_tvProduct2);
        tvProduct3 = rootView.findViewById(R.id.tab1_whatis_tvProduct3);
        tvProduct4 = rootView.findViewById(R.id.tab1_whatis_tvProduct4);
        ivProduct1 = rootView.findViewById(R.id.tab1_whatis_ivProduct1);
        ivProduct2 = rootView.findViewById(R.id.tab1_whatis_ivProduct2);
        ivProduct3 = rootView.findViewById(R.id.tab1_whatis_ivProduct3);
        ivProduct4 = rootView.findViewById(R.id.tab1_whatis_ivProduct4);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewPagerRequirements();
        displayProductsWhatIs();
        displayRequirements();

    }

    private void displayProductsWhatIs() {
        try{
            if (products.get(0) != null){
                Picasso.with(getActivity()).load(products.get(0).getImage()).into(ivProduct1);
                tvProduct1.setText(products.get(0).getDescription());
            }
            if (products.get(1) != null){
                Picasso.with(getActivity()).load(products.get(1).getImage()).into(ivProduct2);
                tvProduct2.setText(products.get(1).getDescription());
            }
            if (products.get(2) != null){
                Picasso.with(getActivity()).load(products.get(2).getImage()).into(ivProduct3);
                tvProduct3.setText(products.get(2).getDescription());
            }
            if (products.get(3) != null){
                Picasso.with(getActivity()).load(products.get(3).getImage()).into(ivProduct4);
                tvProduct4.setText(products.get(3).getDescription());
            }
        }catch (Exception e){
            Log.e("product-whatis:","no existe producto en array");
        }
    }

    private void displayRequirements() {
        if (requirements.size() > 1)
            dotIndicator.setNumberOfItems(requirements.size());
        else
            dotIndicator.setVisibility(View.GONE);
        requisitosAdapter.setRequisitos(requirements);
    }

    private void setUpViewPagerRequirements() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                dotIndicator.setSelectedItem(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        requisitosAdapter = new RequisitosAdapter(getChildFragmentManager(), new ArrayList<ItemList>());
        viewPager.setAdapter(requisitosAdapter);
    }

}
