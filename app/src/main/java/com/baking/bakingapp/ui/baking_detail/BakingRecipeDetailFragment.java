package com.baking.bakingapp.ui.baking_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a single BakingWS detail screen.
 * This fragment is either contained in a {@link com.baking.bakingapp.ui.baking_landing.BakingListActivity}
 * in two-pane mode (on tablets) or a {@link BakingDetailActivity}
 * on handsets.
 */
public class BakingRecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_TRANSITION_ID = "item_transition_id";
    public static final String ARG_ITEM_COLOR_PALETTE = "color_palette";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BakingRecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }
}