package com.xiaopeng.aiot.model.buriedpoint;

import com.xiaopeng.iotlib.provider.buriedpoint.BuriedPointUtils;
/* loaded from: classes.dex */
public class BaseDataLog {
    public static final String KEY_SOURCE = "source";
    public static final String KEY_TYPE = "type";

    public void sendDataLog(String str, String str2, String str3, int i) {
        BuriedPointUtils.sendButtonDataLog(str, str2, str3, i);
    }
}
