package com.baking.bakingapp.data;

import android.support.annotation.Nullable;
import android.util.Log;

import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.data.network.IBakingApiService;
import com.baking.bakingapp.util.Constant;
import com.baking.bakingapp.util.FileUtils;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
                .client(new OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
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
    public Single<List<BakingWS>> getBakingRecipes() {
        return mApiService.getBakingRecipes();
    }

    @Override
    public Single<List<BakingWS>> getMockBakingRecipe() {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<List<BakingWS>> jsonAdapter = moshi.adapter(Types.newParameterizedType(List.class, BakingWS.class));
        try {
            String jsonResponseFilePath = "res/raw/baking.json";
            Observable<List<BakingWS>> bakingWSObservable = Observable.just(jsonAdapter.fromJson(FileUtils.readJSONFromRawFolder(jsonResponseFilePath)));
            return Single.fromObservable(bakingWSObservable);
        } catch (IOException e) {
            Log.e(BakingRepositoryImp.class.getName(), e.getMessage());
        }
        return null;
    }
}