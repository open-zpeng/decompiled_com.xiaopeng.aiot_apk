package com.xiaopeng.iotlib.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
/* loaded from: classes.dex */
public class AlarmHelper {
    private static final String TAG = AlarmHelper.class.getSimpleName();

    public static void setAlarmForPendingIntent(Context context, long j, PendingIntent pendingIntent) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setExact(0, j, pendingIntent);
    }

    public static void cancelAlarmForPendingIntent(Context context, PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            LogUtils.i(TAG, "cancel the pending intent ,called with a null pIntent");
            return;
        }
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static void createOrUpdateBroadcastIntent(Context context, long j, Intent intent, int i) {
        setAlarmForPendingIntent(context, j, PendingIntent.getBroadcast(context, i, intent, 134217728));
    }

    public static boolean isExistBroadcastIntent(Context context, Intent intent, int i) {
        return PendingIntent.getBroadcast(context, i, intent, 536870912) != null;
    }

    public static void cancelBroadcastIntent(Context context, Intent intent, int i) {
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i, intent, 536870912);
        if (broadcast != null) {
            cancelAlarmForPendingIntent(context, broadcast);
        }
    }

    public static void createOrUpdateActivityIntent(Context context, long j, Intent intent, int i) {
        setAlarmForPendingIntent(context, j, PendingIntent.getActivity(context, i, intent, 134217728));
    }

    public static boolean isExistActivityIntent(Context context, Intent intent, int i) {
        return PendingIntent.getActivity(context, i, intent, 536870912) != null;
    }

    public static void cancelActivityIntent(Context context, Intent intent, int i) {
        PendingIntent activity = PendingIntent.getActivity(context, i, intent, 536870912);
        if (activity != null) {
            cancelAlarmForPendingIntent(context, activity);
        }
    }

    public static void createOrUpdateServiceIntent(Context context, long j, Intent intent, int i) {
        setAlarmForPendingIntent(context, j, PendingIntent.getService(context, i, intent, 134217728));
    }

    public static boolean isExistServiceIntent(Context context, Intent intent, int i) {
        return PendingIntent.getService(context, i, intent, 536870912) != null;
    }

    public static void cancelServiceIntent(Context context, Intent intent, int i) {
        PendingIntent service = PendingIntent.getService(context, i, intent, 536870912);
        if (service != null) {
            cancelAlarmForPendingIntent(context, service);
        }
    }
}
