package com.baking.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.baking.bakingapp.data.BakingRepositoryImp;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.data.models.IngredientWS;
import com.baking.bakingapp.ui.baking_detail.BakingRecipeDetailFragment;
import com.baking.bakingapp.util.PrefManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CollectionWidgetRemoteViewFactory implements RemoteViewsFactory {

    List<IngredientWS> mCollection = new ArrayList<>();
    BakingWS bakingWS = null;
    Context mContext = null;
    Intent mIntent;
    @NonNull
    private final BakingRepositoryImp bakingRepositoryImp = BakingRepositoryImp.getInstance();
    private PrefManager prefManager;

    public CollectionWidgetRemoteViewFactory(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        fetchBakingList(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection == null ? 0 : mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        if (mCollection != null && mCollection.size() > 0) {
            view.setTextViewText(android.R.id.text1, mCollection.get(position).ingredient);
        }
        Bundle extras = new Bundle();
        extras.putParcelable(BakingRecipeDetailFragment.ARG_ITEM_ID, bakingWS);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        // Make it possible to distinguish the individual on-click
        // action of a given item
        view.setOnClickFillInIntent(android.R.id.text1, fillInIntent);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void fetchBakingList(Context context) {
        prefManager = new PrefManager(context);
        String recipeId = mIntent.getData().getSchemeSpecificPart();

        bakingRepositoryImp.getBakingRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BakingWS>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BakingWS> bakingWSList) {
                        bakingWS = null;

                        for (int i = 0; i < bakingWSList.size(); i++) {
                            if (bakingWSList.get(i).id.toString().equals(recipeId)) {
                                bakingWS = bakingWSList.get(i);
                            }
                        }

                        if (bakingWS == null || recipeId.equals("")) {
                            bakingWS = bakingWSList.get(0);
                        }

                        mCollection.clear();
                        mCollection = bakingWS.ingredients;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}