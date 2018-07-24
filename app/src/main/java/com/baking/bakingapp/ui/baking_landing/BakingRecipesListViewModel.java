package com.baking.bakingapp.ui.baking_landing;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.baking.bakingapp.data.BakingRepositoryImp;
import com.baking.bakingapp.data.models.BakingWS;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BakingRecipesListViewModel extends AndroidViewModel {

    private static final String TAG = BakingRecipesListViewModel.class.getSimpleName();
    private MutableLiveData<List<BakingWS>> listBakingWSMutableLiveData;

    @NonNull
    private final BakingRepositoryImp bakingRepositoryImp = BakingRepositoryImp.getInstance();

    public BakingRecipesListViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchBakingRecipes() {
        bakingRepositoryImp.getBakingRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BakingWS>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BakingWS> bakingWSList) {
                        getListBakingWSMutableLiveData().setValue(bakingWSList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<BakingWS>> getListBakingWSMutableLiveData() {
        if (listBakingWSMutableLiveData == null) {
            listBakingWSMutableLiveData = new MutableLiveData<>();
        }
        return listBakingWSMutableLiveData;
    }
}