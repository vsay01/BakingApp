package com.baking.bakingapp.data.network;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IBakingApiService {
    @GET(Config.BAKING_URL)
    Observable<ApiResponse> getBakingRecipes();
}