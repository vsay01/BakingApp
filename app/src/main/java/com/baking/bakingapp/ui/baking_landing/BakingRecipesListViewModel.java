package com.baking.bakingapp.ui.baking_landing;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.baking.bakingapp.data.BakingRepositoryImp;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.data.models.ResponseBakingList;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BakingRecipesListViewModel extends AndroidViewModel {

    private static final String TAG = BakingRecipesListViewModel.class.getSimpleName();
    private MutableLiveData<ResponseBakingList> listBakingWSMutableLiveData;

    @NonNull
    private final BakingRepositoryImp bakingRepositoryImp = BakingRepositoryImp.getInstance();

    public BakingRecipesListViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchBakingRecipes() {
        bakingRepositoryImp.getMockBakingRecipe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BakingWS>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BakingWS> bakingWSList) {
                        getListBakingWSMutableLiveData().setValue(new ResponseBakingList(bakingWSList));
                    }

                    @Override
                    public void onError(Throwable e) {
                        getListBakingWSMutableLiveData().setValue(new ResponseBakingList(e));
                    }
                });
    }

    public MutableLiveData<ResponseBakingList> getListBakingWSMutableLiveData() {
        if (listBakingWSMutableLiveData == null) {
            listBakingWSMutableLiveData = new MutableLiveData<>();
        }
        return listBakingWSMutableLiveData;
    }
}