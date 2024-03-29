package pe.com.qallarix.movistarcontigo.specials.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.specials.adapters.SpecialBenefitTypeAdapter;
import pe.com.qallarix.movistarcontigo.specials.pojos.ItemList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BenefitFragment extends Fragment {

    private static final String KEY_DESCRIPCION = "description";
    private static final String KEY_ITEM_LIST = "itemList";

    private String mDescription;
    private List<ItemList> mItemList;
    private TextView tvDescription;
    private RecyclerView rvListItems;

    public BenefitFragment() {
        // Required empty public constructor
    }

    public static BenefitFragment newInstance(String description, List<ItemList> itemList) {

        Bundle args = new Bundle();
        args.putString(KEY_DESCRIPCION,description);
        args.putSerializable(KEY_ITEM_LIST,(Serializable) itemList);
        BenefitFragment fragment = new BenefitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDescription = getArguments().getString(KEY_DESCRIPCION);
        mItemList = (List<ItemList>) getArguments().get(KEY_ITEM_LIST);
        View rootView = inflater.inflate(R.layout.fragment_beneficio, container, false);
        tvDescription = rootView.findViewById(R.id.detalle_beneficio_especial_tvDescripcion);
        rvListItems = rootView.findViewById(R.id.detalle_beneficio_especial_recycler);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvDescription.setText(mDescription);
        rvListItems.setHasFixedSize(true);
        rvListItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpecialBenefitTypeAdapter tipoBeneficioAdapter = new SpecialBenefitTypeAdapter(getActivity(), mItemList);
        rvListItems.setAdapter(tipoBeneficioAdapter);
        rvListItems.setNestedScrollingEnabled(false);
    }
}
