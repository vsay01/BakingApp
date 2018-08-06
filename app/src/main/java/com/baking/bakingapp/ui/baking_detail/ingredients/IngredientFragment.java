package com.baking.bakingapp.ui.baking_detail.ingredients;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.IngredientWS;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class IngredientFragment extends Fragment {

    // TODO: Customize parameter argument names
    public static final String ARG_ITEM_ID = "item_id";
    private List<IngredientWS> ingredientWSList;

    @BindView(R.id.list)
    RecyclerView listIngredient;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static IngredientFragment newInstance(List<IngredientWS> ingredientWSList) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ITEM_ID, (ArrayList<IngredientWS>) ingredientWSList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ingredientWSList = getArguments().getParcelableArrayList(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        ButterKnife.bind(this, view);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            listIngredient.setLayoutManager(new LinearLayoutManager(context));
            listIngredient.setAdapter(new MyIngredientRecyclerViewAdapter(getActivity(), ingredientWSList));
        }
        return view;
    }
}
