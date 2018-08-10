package com.baking.bakingapp.ui.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.BakingRepositoryImp;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.util.ParcelableUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @NonNull
    private final BakingRepositoryImp bakingRepositoryImp = BakingRepositoryImp.getInstance();

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, List<BakingWS> bakingWSList) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        Intent intent = new Intent(context, WidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CollectionWidgetRemoteViewFactory.BYTE_ARRAY_LIST, getAllByteArrayList(bakingWSList));
        intent.putExtras(bundle);
        // Set up the collection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(intent, views);
        } else {
            setRemoteAdapterV11(intent, views);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private ArrayList<byte[]> getAllByteArrayList(List<BakingWS> bakingWSList) {
        ArrayList<byte[]> byteArrayList = new ArrayList<>();
        for (BakingWS bakingWS : bakingWSList) {
            byteArrayList.add(ParcelableUtil.marshall(bakingWS));
        }
        return byteArrayList;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        bakingRepositoryImp.getBakingRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BakingWS>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BakingWS> bakingWSList) {
                        // There may be multiple widgets active, so update all of them
                        for (int appWidgetId : appWidgetIds) {
                            updateAppWidget(context, appWidgetManager, appWidgetId, bakingWSList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Intent intent, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list, intent);
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Intent intent, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list, intent);
    }
}

