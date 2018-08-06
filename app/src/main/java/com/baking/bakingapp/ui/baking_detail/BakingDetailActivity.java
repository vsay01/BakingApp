package com.baking.bakingapp.ui.baking_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.ui.baking_detail.step_detail.StepDetailFragment;
import com.baking.bakingapp.ui.baking_detail.step_detail.StepDetailPagerFragment;
import com.baking.bakingapp.ui.baking_detail.steps.StepFragment;

/**
 * An activity representing a single BakingWS detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link com.baking.bakingapp.ui.baking_landing.BakingListActivity}.
 */
public class BakingDetailActivity extends AppCompatActivity implements StepFragment.OnListFragmentInteractionListener {

    private BakingWS bakingWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_recipe_detail);

        bakingWS = getIntent().getParcelableExtra(BakingRecipeDetailFragment.ARG_ITEM_ID);

        if (savedInstanceState == null && getIntent() != null && bakingWS != null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            BakingRecipeDetailFragment fragment = BakingRecipeDetailFragment.newInstance(bakingWS);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_baking_recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onStepClicked(boolean twoPane, int position) {
        if (bakingWS == null) {
            return;
        }
        if (twoPane) {
            StepDetailPagerFragment fragment = StepDetailPagerFragment.newInstance(bakingWS.steps.get(position));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_step_detail_container, fragment)
                    .commit();
        } else {
            StepDetailFragment fragment = StepDetailFragment.newInstance(bakingWS.steps, position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_baking_recipe_detail_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}