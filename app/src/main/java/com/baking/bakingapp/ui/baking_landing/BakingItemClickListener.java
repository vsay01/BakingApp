package com.baking.bakingapp.ui.baking_landing;

import android.support.v7.widget.AppCompatImageView;

import com.baking.bakingapp.data.models.BakingWS;

public interface BakingItemClickListener {
    void onRecipeClicked(BakingWS bakingWS, int colorPalette, AppCompatImageView bakingRecipeImage);
}