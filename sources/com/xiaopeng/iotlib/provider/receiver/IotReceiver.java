package com.xiaopeng.iotlib.provider.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.iotlib.provider.iot.device.Fragrance;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class IotReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.xiaopeng.intent.action.AIOT_NOTIFICATION";
    public static final String EXTRA_DEVICE_PROPERTY_NAME = "extra_device_property_name";
    public static final String EXTRA_DEVICE_PROPERTY_VALUE = "extra_device_property_value";
    public static final String EXTRA_DEVICE_TYPE = "extra_device_type";
    public static final String KEY_EVENT_TYPE = "bd_event_type";
    public static final String KEY_FROM = "from";
    public static final String KEY_NOTIFY = "key_event_stat_notify";
    public static final String VAL_EVENT_TYPE_NOTIFY = "type_event_stat_notify";
    public static final String VAL_EVENT_TYPE_RAW = "type_event_prop_raw";
    public static final String VAL_NOTIFY_CHILD_SEAT_UNINSTALL = "notify_child_seat_uninstall";
    public static final String VAL_NOTIFY_FRAGRANCE_ENTER_CINEMA = "notify_enter_cinema";
    public static final String VAL_NOTIFY_FRAGRANCE_EXIT_CINEMA = "notify_exit_cinema";
    public static final String VAL_NOTIFY_FRAGRANCE_INSERT = "notify_fragrance_insert";
    public static final String VAL_NOTIFY_FRAGRANCE_INTO_MEDITATION = "notify_fragrance_into_meditation";
    public static final String VAL_NOTIFY_FRAGRANCE_INTO_SLEEP = "notify_fragrance_into_sleep";
    public static final String VAL_NOTIFY_FRAGRANCE_OUT_MEDITATION = "notify_fragrance_out_meditation";
    public static final String VAL_NOTIFY_FRAGRANCE_OUT_SLEEP = "notify_fragrance_out_sleep";
    public static final String VAL_NOTIFY_FRIDGE_OPEN_DOOR = "notify_fridge_door_open";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra(KEY_FROM);
            LogUtils.i("IotReceiver ", "" + stringExtra);
            String stringExtra2 = intent.getStringExtra(KEY_EVENT_TYPE);
            if (VAL_EVENT_TYPE_NOTIFY.equals(stringExtra2)) {
                notify(context, intent.getStringExtra(KEY_NOTIFY));
            } else if (VAL_EVENT_TYPE_RAW.equals(stringExtra2)) {
                String stringExtra3 = intent.getStringExtra(EXTRA_DEVICE_TYPE);
                String stringExtra4 = intent.getStringExtra(EXTRA_DEVICE_PROPERTY_NAME);
                String stringExtra5 = intent.getStringExtra(EXTRA_DEVICE_PROPERTY_VALUE);
                if (Fragrance.DEVICE_TYPE.equals(stringExtra3)) {
                    rawData_Fragrance(stringExtra4, stringExtra5);
                    return;
                }
                LogUtils.i("IotReceiver ", "raw deviceType not valid  : " + stringExtra3);
            } else {
                LogUtils.i("IotReceiver ", "type not valid : " + stringExtra2);
            }
        }
    }

    private void notify(Context context, String str) {
        if (VAL_NOTIFY_FRAGRANCE_INSERT.equals(str)) {
            LogUtils.i("IotReceiver ", " VAL_NOTIFY_FRAGRANCE_INSERT ");
            ReceiverManager.get().onFragranceInsert();
        } else if (VAL_NOTIFY_FRAGRANCE_INTO_SLEEP.equals(str)) {
            LogUtils.i("IotReceiver ", "VAL_NOTIFY_FRAGRANCE_INTO_SLEEP ");
            ReceiverManager.get().onEnterSleepMode();
        } else if (VAL_NOTIFY_FRAGRANCE_OUT_SLEEP.equals(str)) {
            LogUtils.i("IotReceiver ", "VAL_NOTIFY_FRAGRANCE_OUT_SLEEP ");
            ReceiverManager.get().onExitSleepMode();
        } else if (VAL_NOTIFY_FRAGRANCE_INTO_MEDITATION.equals(str)) {
            LogUtils.i("IotReceiver ", "VAL_NOTIFY_FRAGRANCE_INTO_MEDITATION ");
            ReceiverManager.get().onEnterMeditationMode();
        } else if (VAL_NOTIFY_FRAGRANCE_OUT_MEDITATION.equals(str)) {
            LogUtils.i("IotReceiver ", "VAL_NOTIFY_FRAGRANCE_OUT_MEDITATION ");
            ReceiverManager.get().onExitMeditationMode();
        } else if (VAL_NOTIFY_FRIDGE_OPEN_DOOR.equals(str)) {
            LogUtils.i("IotReceiver ", "VAL_NOTIFY_FRIDGE_OPEN_DOOR ");
            ReceiverManager.get().onFridgeDoorOpen();
        } else if (VAL_NOTIFY_CHILD_SEAT_UNINSTALL.equals(str)) {
            LogUtils.i("IotReceiver ", "VAL_NOTIFY_CHILD_SEAT_UNINSTALL ");
            ReceiverManager.get().onChildSeatUninstall();
        } else if (VAL_NOTIFY_FRAGRANCE_ENTER_CINEMA.equals(str)) {
            LogUtils.i("IotReceiver ", "VAL_NOTIFY_FRAGRANCE_ENTER_CINEMA ");
            ReceiverManager.get().onCinemaMode(true);
        } else if (VAL_NOTIFY_FRAGRANCE_EXIT_CINEMA.equals(str)) {
            LogUtils.i("IotReceiver ", "VAL_NOTIFY_FRAGRANCE_EXIT_CINEMA ");
            ReceiverManager.get().onCinemaMode(false);
        } else {
            LogUtils.i("IotReceiver ", "notify not valid : " + str);
        }
    }

    private void rawData_Fragrance(String str, String str2) {
        if (Fragrance.PROP_CO_CONCENTRATION.equals(str)) {
            if ("2".equals(str2)) {
                LogUtils.i("IotReceiver ", " VAL_CO_CONCENTRATION_HIGH ");
                ReceiverManager.get().onCoHigh();
                return;
            }
            LogUtils.i("IotReceiver ", "rawData_Fragrance value not valid : " + str2);
            return;
        }
        LogUtils.i("IotReceiver ", "rawData_Fragrance name not valid : " + str);
    }
}
