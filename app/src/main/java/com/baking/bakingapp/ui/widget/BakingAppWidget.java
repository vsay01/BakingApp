package com.baking.bakingapp.ui.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.BakingRepositoryImp;
import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.data.models.IngredientWS;
import com.baking.bakingapp.ui.baking_detail.BakingDetailActivity;
import com.baking.bakingapp.util.ParcelableUtil;
import com.baking.bakingapp.util.PrefManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.baking.bakingapp.util.Constant.KEY_FAVORITE_RECIPE_ID;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @NonNull
    private final BakingRepositoryImp bakingRepositoryImp = BakingRepositoryImp.getInstance();
    private PrefManager prefManager;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, BakingWS bakingWS) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.recipe_name, bakingWS.name);

        Intent intent = new Intent(context, WidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CollectionWidgetRemoteViewFactory.BYTE_ARRAY_LIST, getAllByteArrayList(bakingWS.ingredients));
        bundle.putSerializable(CollectionWidgetRemoteViewFactory.BYTE_BAKING_OBJECT, getByteObject(bakingWS));
        intent.putExtras(bundle);
        // Set up the collection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(intent, views);
        } else {
            setRemoteAdapterV11(intent, views);
        }
        // This section makes it possible for items to have individualized behavior.
        // It does this by setting up a pending intent template. Individuals items of a collection
        // cannot set up their own pending intents. Instead, the collection as a whole sets
        // up a pending intent template, and the individual items set a fillInIntent
        // to create unique behavior on an item-by-item basis.
        Intent activityIntent = new Intent(context, BakingDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list, pendingIntent);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private ArrayList<byte[]> getAllByteArrayList(List<IngredientWS> ingredientWSList) {
        ArrayList<byte[]> byteArrayList = new ArrayList<>();
        for (IngredientWS ingredientWS : ingredientWSList) {
            byteArrayList.add(ParcelableUtil.marshall(ingredientWS));
        }
        return byteArrayList;
    }

    private byte[] getByteObject(BakingWS bakingWS) {
        return ParcelableUtil.marshall(bakingWS);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        fetchBakingList(context, appWidgetIds, appWidgetManager);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent != null && intent.getAction() != null && intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), BakingAppWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            fetchBakingList(context, appWidgetIds, appWidgetManager);
        }
    }


    private void fetchBakingList(Context context, int[] appWidgetIds, AppWidgetManager appWidgetManager) {
        prefManager = new PrefManager(context);

        bakingRepositoryImp.getMockBakingRecipe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BakingWS>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BakingWS> bakingWSList) {
                        // There may be multiple widgets active, so update all of them
                        String favoriteBakingRecipeId = prefManager.getString(PreferenceManager.getDefaultSharedPreferences(context), KEY_FAVORITE_RECIPE_ID, "");

                        BakingWS bakingWS = null;
                        for (int i = 0; i < bakingWSList.size(); i++) {
                            if (bakingWSList.get(i).id.toString().equals(favoriteBakingRecipeId)) {
                                bakingWS = bakingWSList.get(i);
                            }
                        }

                        if (bakingWS == null) {
                            bakingWS = bakingWSList.get(0);
                        }

                        for (int appWidgetId : appWidgetIds) {
                            updateAppWidget(context, appWidgetManager, appWidgetId, bakingWS);
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

