package com.xiaopeng.aiot.model.scene;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.utils.AlarmHelper;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class TimeWatchMarkReceiver extends BroadcastReceiver {
    private static final String ACTION = "com.xiaopeng.intent.WATCH_MARK";
    private static final String TAG = "TimeWatchMark";
    private static final long TIME = 300000;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            boolean isMarkEnable = WatchesScene.get().isMarkEnable();
            LogUtils.i(TAG, "onReceive isMark " + isMarkEnable);
            if (isMarkEnable) {
                WatchesScene.get().questLocation();
                timing();
            }
        }
    }

    private static Intent createPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.setComponent(new ComponentName(context, TimeWatchMarkReceiver.class));
        return intent;
    }

    public static void timing() {
        Application app = Iot.getApp();
        Intent createPendingIntent = createPendingIntent(app);
        AlarmHelper.cancelBroadcastIntent(app, createPendingIntent, 0);
        boolean isExistBroadcastIntent = AlarmHelper.isExistBroadcastIntent(app, createPendingIntent, 0);
        LogUtils.i(TAG, "timing , isExist : " + isExistBroadcastIntent);
        AlarmHelper.createOrUpdateBroadcastIntent(app, System.currentTimeMillis() + TIME, createPendingIntent, 0);
    }

    public static void cancel() {
        Application app = Iot.getApp();
        LogUtils.i(TAG, "cancel ");
        AlarmHelper.cancelBroadcastIntent(app, createPendingIntent(app), 0);
    }
}
