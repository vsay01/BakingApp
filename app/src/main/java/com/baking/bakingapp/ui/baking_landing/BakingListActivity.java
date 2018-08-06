package com.baking.bakingapp.ui.baking_landing;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.ui.baking_detail.BakingDetailActivity;
import com.baking.bakingapp.ui.baking_detail.BakingRecipeDetailFragment;
import com.baking.bakingapp.util.ResourceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of BakingRecipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BakingListActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BakingListActivity extends AppCompatActivity {
    private BakingRecipesListViewModel mViewModel;

    @BindView(R.id.item_list)
    RecyclerView recyclerViewRecipe;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_recipes_list);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);
        }

        toolbar.setTitle(R.string.app_name);
        // Get the ViewModel.
        mViewModel = ViewModelProviders.of(this).get(BakingRecipesListViewModel.class);
        // Create the observer which updates the UI.
        final Observer<List<BakingWS>> listObserver = new Observer<List<BakingWS>>() {
            @Override
            public void onChanged(@NonNull List<BakingWS> bakingWSList) {
                setupRecyclerView(recyclerViewRecipe, bakingWSList);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getListBakingWSMutableLiveData().observe(this, listObserver);
        mViewModel.fetchBakingRecipes();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, @NonNull List<BakingWS> bakingWSList) {
        int numberOfColumns = 1;
        if (ResourceUtils.isTablet(this)) {
            numberOfColumns = 3;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setAdapter(new BakingRecipeRecyclerViewAdapter(this, bakingWSList, bakingWS -> {
            Intent intent = new Intent(BakingListActivity.this, BakingDetailActivity.class);
            intent.putExtra(BakingRecipeDetailFragment.ARG_ITEM_ID, bakingWS);
            startActivity(intent);
        }));
    }
}