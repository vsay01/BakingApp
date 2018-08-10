package com.baking.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.baking.bakingapp.data.models.BakingWS;
import com.baking.bakingapp.util.ParcelableUtil;

import java.util.ArrayList;
import java.util.List;

public class CollectionWidgetRemoteViewFactory implements RemoteViewsFactory {
    private static final String TAG = "WidgetDataProvider";
    public static final String BYTE_ARRAY_LIST = "BYTE_ARRAY_LIST";

    List<BakingWS> mCollection = new ArrayList<>();
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
        if (mIntent != null) {
            List<byte[]> byteArrayList = ((List<byte[]>) mIntent.getExtras().getSerializable(BYTE_ARRAY_LIST));
            for (byte[] byteArray : byteArrayList) {
                mCollection.add(new BakingWS(ParcelableUtil.unmarshall(byteArray)));
            }
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
            view.setTextViewText(android.R.id.text1, mCollection.get(position).name);
        }
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