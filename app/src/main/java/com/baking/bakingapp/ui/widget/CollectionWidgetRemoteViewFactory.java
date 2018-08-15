package com.baking.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.data.models.IngredientWS;
import com.baking.bakingapp.ui.baking_detail.BakingRecipeDetailFragment;
import com.baking.bakingapp.util.ParcelableUtil;

import java.util.ArrayList;
import java.util.List;

public class CollectionWidgetRemoteViewFactory implements RemoteViewsFactory {
    private static final String TAG = "WidgetDataProvider";
    public static final String BYTE_ARRAY_LIST = "BYTE_ARRAY_LIST";
    public static final String BYTE_BAKING_OBJECT = "BYTE_BAKING_OBJECT";

    List<IngredientWS> mCollection = new ArrayList<>();
    BakingWS bakingWS = null;
    Context mContext = null;
    Intent mIntent;

    public CollectionWidgetRemoteViewFactory(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mCollection.clear();
        if (mIntent != null && mIntent.getExtras() != null) {
            List<byte[]> byteArrayList = ((List<byte[]>) mIntent.getExtras().getSerializable(BYTE_ARRAY_LIST));
            for (byte[] byteArray : byteArrayList) {
                mCollection.add(new IngredientWS(ParcelableUtil.unmarshall(byteArray)));
            }

            Parcel parcel = ParcelableUtil.unmarshall((byte[]) mIntent.getExtras().getSerializable(BYTE_BAKING_OBJECT));
            bakingWS = new BakingWS(parcel);
        }
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
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        if (mCollection != null) {
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
}