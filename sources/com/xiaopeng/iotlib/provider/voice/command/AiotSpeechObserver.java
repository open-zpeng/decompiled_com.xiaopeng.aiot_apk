package com.xiaopeng.iotlib.provider.voice.command;

import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
/* loaded from: classes.dex */
public class AiotSpeechObserver implements IServicePublisher {
    private static final String TAG = "AiotSpeechObserver";

    public void onEvent(String str, String str2) {
        LogUtils.i(TAG, "onEvent event== " + str + " ,data: " + str2);
        SpeechManager.get().dispatchEvent(str, str2);
    }

    public void onQuery(String str, String str2, String str3) {
        LogUtils.i(TAG, "onQuery event== " + str + " ,data: " + str2 + " ,callback: " + str3);
        SpeechManager.get().dispatchQuery(str, str2, str3);
    }
}
