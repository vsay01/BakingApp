package com.baking.bakingapp.data;

import android.support.annotation.Nullable;

import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.data.network.IBakingApiService;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class BakingRepositoryImp implements IBakingRepository {

    @Nullable
    private static BakingRepositoryImp INSTANCE = null;

    private IBakingApiService mApiService;

    /**
     * Returns the single instance of this class, creating it if necessary.
     */
    public static BakingRepositoryImp getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BakingRepositoryImp();
        }
        return INSTANCE;
    }

    public BakingRepositoryImp() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
        mApiService = retrofit.create(IBakingApiService.class);
    }

    /**
     * Used to force {@link #getInstance()}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<BakingWS>> getBakingRecipes() {
        return null;
    }
}