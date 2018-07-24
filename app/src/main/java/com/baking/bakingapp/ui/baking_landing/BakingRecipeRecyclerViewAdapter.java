package com.baking.bakingapp.ui.baking_landing;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.base.GlideApp;
import com.baking.bakingapp.data.models.BakingWS;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingRecipeRecyclerViewAdapter
        extends RecyclerView.Adapter<BakingRecipeRecyclerViewAdapter.ViewHolder> {

    private BakingListActivity bakingListActivity;
    private final List<BakingWS> mBakingWSList;
    private final BakingItemClickListener mBakingRecipeClickListener;

    BakingRecipeRecyclerViewAdapter(BakingListActivity parent,
                                    List<BakingWS> items,
                                    BakingItemClickListener bakingRecipeClickListener) {
        this.bakingListActivity = parent;
        mBakingWSList = items;
        mBakingRecipeClickListener = bakingRecipeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_baking_recipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        BakingWS bakingWS = mBakingWSList.get(position);

        if (bakingWS != null) {
            GlideApp.with(bakingListActivity)
                    .load(bakingWS.image)
                    .placeholder(R.mipmap.ic_launcher)
                    .fitCenter()
                    .into(holder.imvBakingRecipe);

            holder.bakingRecipeName.setText(bakingWS.name);

            mBakingRecipeClickListener.onRecipeClicked(bakingWS);
        }
    }

    @Override
    public int getItemCount() {
        return mBakingWSList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imv_baking_recipe)
        AppCompatImageView imvBakingRecipe;

        @BindView(R.id.tv_baking_recipe_name)
        AppCompatTextView bakingRecipeName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}