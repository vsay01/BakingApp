package com.baking.bakingapp.ui.baking_landing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.ui.baking_detail.BakingDetailActivity;
import com.baking.bakingapp.ui.baking_detail.BakingRecipeDetailFragment;
import com.baking.bakingapp.util.NetworkUtils;

import java.util.List;

import butterknife.ButterKnife;

import static com.baking.bakingapp.ui.baking_detail.BakingRecipeDetailFragment.ARG_ITEM_COLOR_PALETTE;
import static com.baking.bakingapp.ui.baking_detail.BakingRecipeDetailFragment.ARG_ITEM_TRANSITION_ID;

/**
 * An activity representing a list of BakingRecipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BakingListActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BakingListActivity extends AppCompatActivity {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private BakingRecipesListViewModel mViewModel;
    private int mSelectedItem = 0;
    private static final String SELECTED_ITEM_POSITION = "ItemPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_recipes_list);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);
        }

        supportPostponeEnterTransition();
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM_POSITION);
        }

        if (findViewById(R.id.item_baking_recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    private void loadBakingRecipes() {
        if (NetworkUtils.isOnline(this)) {

        } else {

        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, @NonNull List<BakingWS> bakingWS) {
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setAdapter(new BakingRecipeRecyclerViewAdapter(this, bakingWS, new BakingItemClickListener() {
            @Override
            public void onRecipeClicked(BakingWS bakingWS, int colorPalette, AppCompatImageView bakingRecipeImage) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(BakingRecipeDetailFragment.ARG_ITEM_ID, bakingWS);
                    arguments.putString(ARG_ITEM_TRANSITION_ID, ViewCompat.getTransitionName(bakingRecipeImage));
                    arguments.putInt(ARG_ITEM_COLOR_PALETTE, colorPalette);

                    BakingRecipeDetailFragment fragment = new BakingRecipeDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_baking_recipe_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(BakingListActivity.this, BakingDetailActivity.class);
                    intent.putExtra(BakingRecipeDetailFragment.ARG_ITEM_ID, bakingWS);
                    intent.putExtra(ARG_ITEM_TRANSITION_ID, ViewCompat.getTransitionName(bakingRecipeImage));
                    intent.putExtra(ARG_ITEM_COLOR_PALETTE, colorPalette);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            BakingListActivity.this,
                            bakingRecipeImage,
                            ViewCompat.getTransitionName(bakingRecipeImage));

                    startActivity(intent, options.toBundle());
                }
            }
        }));
    }
}