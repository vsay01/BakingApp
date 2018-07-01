package com.baking.bakingapp.ui.baking_detail;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.baking.bakingapp.R;
import com.baking.bakingapp.util.ColorUtils;

/**
 * An activity representing a single BakingWS detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link com.baking.bakingapp.ui.baking_landing.BakingListActivity}.
 */
public class BakingDetailActivity extends AppCompatActivity {

    private int mPaletteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_recipe_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);
        }

        if (getIntent() != null) {
            mPaletteColor = getIntent().getIntExtra(BakingRecipeDetailFragment.ARG_ITEM_COLOR_PALETTE, ContextCompat.getColor(this, R.color.colorAccent));
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //set color action bar
            actionBar.setBackgroundDrawable(new ColorDrawable(ColorUtils.manipulateColor(mPaletteColor, 0.62f)));

            //set color status bar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ColorUtils.manipulateColor(mPaletteColor, 0.32f));
            }
        }

        if (savedInstanceState == null && getIntent() != null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(BakingRecipeDetailFragment.ARG_ITEM_ID,
                    getIntent().getParcelableExtra(BakingRecipeDetailFragment.ARG_ITEM_ID));
            arguments.putString(BakingRecipeDetailFragment.ARG_ITEM_TRANSITION_ID,
                    getIntent().getStringExtra(BakingRecipeDetailFragment.ARG_ITEM_TRANSITION_ID));
            arguments.putInt(BakingRecipeDetailFragment.ARG_ITEM_COLOR_PALETTE, mPaletteColor);
            BakingRecipeDetailFragment fragment = new BakingRecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_baking_recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}