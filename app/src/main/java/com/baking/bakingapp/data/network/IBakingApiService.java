package com.baking.bakingapp.data.network;

import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.util.Constant;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface IBakingApiService {
    @GET(Constant.BAKING_END_POINT)
    Single<List<BakingWS>> getBakingRecipes();
}