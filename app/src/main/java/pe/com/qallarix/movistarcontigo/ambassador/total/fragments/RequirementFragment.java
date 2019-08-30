package pe.com.qallarix.movistarcontigo.ambassador.total.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.qallarix.movistarcontigo.R;
import pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1.ItemList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequirementFragment extends Fragment {

    private TextView tvDescription;
    private TextView tvOrder;
    private ItemList requirement;
    private static final String ARGUMENT_REQUIREMENT = "requisito";

    public RequirementFragment() {
        // Required empty public constructor
    }

    public static RequirementFragment newInstance(ItemList requirement) {

        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_REQUIREMENT,requirement);
        RequirementFragment fragment = new RequirementFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requirement = (ItemList) getArguments().getSerializable(ARGUMENT_REQUIREMENT);
        View rootView = inflater.inflate(R.layout.fragment_requisito, container, false);
        tvOrder = rootView.findViewById(R.id.requisito_tvNumero);
        tvDescription = rootView.findViewById(R.id.requisito_tvDescripcion);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvOrder.setText(requirement.getOrder()+"");
        tvDescription.setText(requirement.getDescription());
    }
}
