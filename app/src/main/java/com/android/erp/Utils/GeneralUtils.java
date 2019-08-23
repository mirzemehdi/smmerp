package com.android.erp.Utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

public class GeneralUtils {
    public static int convertDpToPixel(int dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return Math.round(px);
    }
    public static void largeLog(String tag, String content) {
        if (content.length() > 4000) {
            Log.d(tag, content.substring(0, 4000));
            largeLog(tag, content.substring(4000));
        } else {
            Log.d(tag, content);
        }
    }
}
