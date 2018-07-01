package com.baking.bakingapp.ui.baking_landing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.BakingWS;

import java.util.List;

import butterknife.ButterKnife;

public class BakingRecipeRecyclerViewAdapter
        extends RecyclerView.Adapter<BakingRecipeRecyclerViewAdapter.ViewHolder> {

    private final BakingListActivity mParentActivity;
    private final List<BakingWS> mBakingWSList;
    private final BakingItemClickListener mBakingRecipeClickListener;

    BakingRecipeRecyclerViewAdapter(BakingListActivity parent,
                                    List<BakingWS> items,
                                    BakingItemClickListener bakingRecipeClickListener) {
        mBakingWSList = items;
        mParentActivity = parent;
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

    }

    @Override
    public int getItemCount() {
        return mBakingWSList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        int mColorPalette;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}