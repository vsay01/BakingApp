package com.baking.bakingapp.util;

import android.content.Context;

public class UnitUtil {
    public static int dpToPx(int dps, Context context) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}
