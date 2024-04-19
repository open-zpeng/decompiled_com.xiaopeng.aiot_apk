package com.xiaopeng.aiot.service;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
@Deprecated
/* loaded from: classes.dex */
public interface IWorkService {
    default void onConfigurationChanged(Configuration configuration) {
    }

    void onCreate(Context context);

    void onDestroy(Context context);

    default void onLowMemory() {
    }

    default void onStartCommand(Context context, Intent intent) {
    }
}
