package com.baking.bakingapp.ui.baking_detail;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.ui.baking_detail.step_detail.StepDetailFragment;
import com.baking.bakingapp.ui.baking_detail.step_detail.StepDetailPagerFragment;
import com.baking.bakingapp.ui.baking_detail.steps.StepFragment;
import com.baking.bakingapp.ui.widget.BakingAppWidget;
import com.baking.bakingapp.util.PrefManager;

import static com.baking.bakingapp.util.Constant.KEY_FAVORITE_RECIPE_ID;

/**
 * An activity representing a single BakingWS detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link com.baking.bakingapp.ui.baking_landing.BakingListActivity}.
 */
public class BakingDetailActivity extends AppCompatActivity implements StepFragment.OnListFragmentInteractionListener {

    private BakingWS bakingWS;
    private BakingRecipeDetailFragment bakingRecipeDetailFragment;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_recipe_detail);

        bakingWS = getIntent().getParcelableExtra(BakingRecipeDetailFragment.ARG_ITEM_ID);
        prefManager = new PrefManager(this);

        if (savedInstanceState == null && getIntent() != null && bakingWS != null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            bakingRecipeDetailFragment = BakingRecipeDetailFragment.newInstance(bakingWS);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_baking_recipe_detail_container, bakingRecipeDetailFragment)
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem favoriteItem = menu.findItem(R.id.favorite);
        if (prefManager.getString(PreferenceManager.getDefaultSharedPreferences(this), KEY_FAVORITE_RECIPE_ID, "").equals(bakingWS.id.toString())) {
            favoriteItem.setIcon(getResources().getDrawable(R.drawable.ic_selected_favorite_black_24dp));
        } else {
            favoriteItem.setIcon(getResources().getDrawable(R.drawable.ic_unselected_favorite_border_black_24dp));
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.favorite:
                if (prefManager.getString(PreferenceManager.getDefaultSharedPreferences(this), KEY_FAVORITE_RECIPE_ID, "").equals(bakingWS.id.toString())) {
                    prefManager.setString(PreferenceManager.getDefaultSharedPreferences(this), KEY_FAVORITE_RECIPE_ID, "");
                } else {
                    prefManager.setString(PreferenceManager.getDefaultSharedPreferences(this), KEY_FAVORITE_RECIPE_ID, bakingWS.id.toString());
                }
                updateWidgetFavoriteRecipe();
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateWidgetFavoriteRecipe() {
        Intent intent = new Intent(this, BakingAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), BakingAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }
}