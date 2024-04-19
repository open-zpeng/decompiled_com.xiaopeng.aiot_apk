package com.xiaopeng.iotlib.utils;

import android.content.Context;
import android.content.res.Configuration;
/* loaded from: classes.dex */
public class ScreenUtils {
    public static void changeScreenPort(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.orientation = 1;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
