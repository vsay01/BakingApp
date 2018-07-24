package com.baking.bakingapp.ui.baking_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baking.bakingapp.R;

/**
 * An activity representing a single BakingWS detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link com.baking.bakingapp.ui.baking_landing.BakingListActivity}.
 */
public class BakingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_recipe_detail);

        if (savedInstanceState == null && getIntent() != null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(BakingRecipeDetailFragment.ARG_ITEM_ID,
                    getIntent().getParcelableExtra(BakingRecipeDetailFragment.ARG_ITEM_ID));
            BakingRecipeDetailFragment fragment = new BakingRecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_baking_recipe_detail_container, fragment)
                    .commit();
        }
    }
}