package com.xiaopeng.iotlib.provider.voice.vui;

import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.speech.vui.VuiEngine;
/* loaded from: classes.dex */
public class VuiObserver implements IServicePublisher {
    private static final String TAG = "vuiObserver";

    public void onEvent(String str, String str2) {
        LogUtils.i("vuiObserver", " VuiObserver onEvent event== " + str + ", data==" + str);
        VuiEngine.getInstance(Iot.getApp()).dispatchVuiEvent(str, str2);
    }

    public String getElementState(String str, String str2) {
        String elementState = VuiEngine.getInstance(Iot.getApp()).getElementState(str, str2);
        LogUtils.i("vuiObserver", " VuiObserver getElementState sceneId== " + str + ", elementId==" + str2 + ", result==" + elementState);
        return elementState;
    }
}
