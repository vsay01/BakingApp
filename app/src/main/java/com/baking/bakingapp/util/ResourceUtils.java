package com.baking.bakingapp.util;

import android.content.Context;

import com.baking.bakingapp.R;

public class ResourceUtils {
    public static boolean isTablet(Context context) {
        if (context == null) return false;
        return context.getResources().getBoolean(R.bool.isTablet);
    }
}
