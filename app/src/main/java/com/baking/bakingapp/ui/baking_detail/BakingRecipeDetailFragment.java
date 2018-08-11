package com.baking.bakingapp.ui.baking_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.ui.baking_detail.ingredients.IngredientFragment;
import com.baking.bakingapp.ui.baking_detail.steps.StepFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private BakingWS bakingWS;
    private BakingRecipeDetailFragmentPagerAdapter bakingRecipeDetailFragmentPagerAdapter;
    private IngredientFragment ingredientFragment;
    private StepFragment stepFragment;

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    public static BakingRecipeDetailFragment newInstance(BakingWS bakingWS) {
        BakingRecipeDetailFragment bakingRecipeDetailFragment = new BakingRecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM_ID, bakingWS);
        bakingRecipeDetailFragment.setArguments(args);
        return bakingRecipeDetailFragment;
    }

    public BakingRecipeDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
            bakingWS = getArguments().getParcelable(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setupToolbar(toolbar, true);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ingredientFragment = IngredientFragment.newInstance(bakingWS.ingredients);
        stepFragment = StepFragment.newInstance(bakingWS.steps);
        bakingRecipeDetailFragmentPagerAdapter = new BakingRecipeDetailFragmentPagerAdapter(getChildFragmentManager());
        bakingRecipeDetailFragmentPagerAdapter.addFragment(ingredientFragment, "Ingredients", 0);
        bakingRecipeDetailFragmentPagerAdapter.addFragment(stepFragment, "Steps", 1);

        viewPager.setAdapter(bakingRecipeDetailFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupToolbar(Toolbar toolbar, boolean setDisplayHomeAsUpEnabled) {
        if (toolbar != null && bakingWS != null) {
            toolbar.setTitle(bakingWS.name);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(setDisplayHomeAsUpEnabled);
        }
    }
}