package com.xiaopeng.iotlib.provider.power;

import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
/* loaded from: classes.dex */
public class PowerCenterObserver implements IServicePublisher {
    private static final String TAG = "SpeechObserver";

    public void onEvent(String str, String str2) {
        LogUtils.i(TAG, "onEvent event== " + str + " ,data: " + str2);
    }
}
