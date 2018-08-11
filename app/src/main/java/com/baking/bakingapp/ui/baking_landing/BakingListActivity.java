package com.baking.bakingapp.ui.baking_landing;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.data.models.ResponseBakingList;
import com.baking.bakingapp.ui.baking_detail.BakingDetailActivity;
import com.baking.bakingapp.ui.baking_detail.BakingRecipeDetailFragment;
import com.baking.bakingapp.util.NetworkUtils;
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

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.cardview_message)
    CardView cardViewMessage;

    @BindView(R.id.tv_message)
    AppCompatTextView tvMessage;

    @BindView(R.id.tv_try_again)
    AppCompatTextView tvTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_recipes_list);
        ButterKnife.bind(this);

        // Get the ViewModel.
        mViewModel = ViewModelProviders.of(this).get(BakingRecipesListViewModel.class);

        // Create the observer which updates the UI.
        final Observer<ResponseBakingList> listObserver = responseBakingList -> {
            progressBar.setVisibility(View.GONE);
            if (responseBakingList.getThrowable() != null) {
                showCardMessage();
            } else {
                setupRecyclerView(recyclerViewRecipe, responseBakingList.getList());
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mViewModel.getListBakingWSMutableLiveData().observe(this, listObserver);
        
        if (!NetworkUtils.isOnline(this)) {
            showCardMessage();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);
        }

        progressBar.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.app_name);

        fetchBakingRecipe();
    }

    private void showCardMessage() {
        tvMessage.setText(R.string.string_error_message);
        tvTryAgain.setOnClickListener(v -> fetchBakingRecipe());
        cardViewMessage.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void fetchBakingRecipe() {
        if (mViewModel != null) {
            progressBar.setVisibility(View.VISIBLE);
            cardViewMessage.setVisibility(View.GONE);
            mViewModel.fetchBakingRecipes();
        }
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