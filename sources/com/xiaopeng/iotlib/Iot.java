package com.xiaopeng.iotlib;

import android.app.Application;
/* loaded from: classes.dex */
public class Iot {
    private static boolean sForUnity;
    private static Application sInstance;

    public static Application getApp() {
        return sInstance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setApp(Application application) {
        sInstance = application;
    }

    public static boolean isForUnity() {
        return sForUnity;
    }

    public static void setForUnity(boolean z) {
        sForUnity = z;
    }
}
