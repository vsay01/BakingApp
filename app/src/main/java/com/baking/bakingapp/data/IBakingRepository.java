package com.baking.bakingapp.data;

import com.baking.bakingapp.data.models.BakingWS;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public interface IBakingRepository {
    @NonNull
    Single<List<BakingWS>> getBakingRecipes();
}