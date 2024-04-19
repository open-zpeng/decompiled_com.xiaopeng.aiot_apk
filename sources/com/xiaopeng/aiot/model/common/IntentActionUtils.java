package com.xiaopeng.aiot.model.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class IntentActionUtils {
    public static final String BLE_CLOSE_TEST = "com.xiaopeng.aiot.BLE_CLOSE_TEST";
    public static final String BLE_OPEN_TEST = "com.xiaopeng.aiot.BLE_OPEN_TEST";
    public static final String ELEMENT_DIRECT_TEST = "com.xiaopeng.aiot.ELEMENT_DIRECT_TEST";
    public static final String FRIDGE_FILTER_MAC = "com.xiaopeng.aiot.FRIDGE_FILTER_MAC";
    public static final String FRIDGE_FILTER_NAME = "com.xiaopeng.aiot.FRIDGE_FILTER_NAME";
    public static final String LOGIN_TEST = "com.xiaopeng.aiot.LOGIN_TEST";
    public static final String LOG_LEVEL = "com.xiaopeng.aiot.LOG_LEVEL";
    public static final String LOG_TAG_CLEAR = "com.xiaopeng.aiot.LOG_TAG_CLEAR";
    public static final String LOG_TAG_LEVEL = "com.xiaopeng.aiot.LOG_TAG_LEVEL";
    public static final String MARK_TEST = "com.xiaopeng.aiot.MARK_TEST";
    public static final String SAVE_CLEAR = "com.xiaopeng.aiot.SAVE_CLEAR";
    public static final String SPEECH_EVENT = "com.xiaopeng.aiot.SPEECH_EVENT";
    public static final String SPEECH_QUERY = "com.xiaopeng.aiot.SPEECH_QUERY";
    public static final String START_FRAGRANCE_INSERT_TEST = "com.xiaopeng.aiot.START_FRAGRANCE_INSERT_TEST";
    public static final String START_SERVICE = "com.xiaopeng.aiot.START_SERVICE";
    public static final String STOP_SERVICE = "com.xiaopeng.aiot.STOP_SERVICE";

    public static void callOrDial(Context context, String str) {
        if (call(context, str)) {
            LogUtils.d(NotificationCompat.CATEGORY_CALL, "call success");
        } else {
            dial(context, str);
        }
    }

    public static boolean call(Context context, String str) {
        if (context == null || str == null) {
            LogUtils.w(NotificationCompat.CATEGORY_CALL, "call context or phoneNum is null");
            return false;
        }
        try {
            Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + str.trim()));
            intent.addFlags(268435456);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean dial(Context context, String str) {
        if (context == null || str == null) {
            LogUtils.w(NotificationCompat.CATEGORY_CALL, "dial context or phoneNum is null");
            return false;
        }
        try {
            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + str.trim()));
            intent.addFlags(268435456);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
