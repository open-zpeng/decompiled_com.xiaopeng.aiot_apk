package com.xiaopeng.aiot.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.aiot.model.common.IntentActionUtils;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.iotlib.utils.Utils;
/* loaded from: classes.dex */
public class DemoManager {
    public static void init() {
        if (Utils.isUserRelease()) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentActionUtils.START_SERVICE);
        intentFilter.addAction(IntentActionUtils.STOP_SERVICE);
        Iot.getApp().registerReceiver(new BroadcastReceiver() { // from class: com.xiaopeng.aiot.demo.DemoManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (IntentActionUtils.START_SERVICE.equals(action)) {
                    DemoManager.startService();
                } else if (IntentActionUtils.STOP_SERVICE.equals(action)) {
                    DemoManager.stopService();
                }
            }
        }, intentFilter);
        if (ApiConfig.API_DEBUG) {
            ThreadUtils.UI.postDelay(new Runnable() { // from class: com.xiaopeng.aiot.demo.-$$Lambda$DemoManager$z1CMWf9BKlkuetz2FD6GlzN-5FA
                @Override // java.lang.Runnable
                public final void run() {
                    DemoManager.startService();
                }
            }, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startService() {
        try {
            Iot.getApp().startService(new Intent(Iot.getApp(), DemoService.class));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void stopService() {
        Iot.getApp().stopService(new Intent(Iot.getApp(), DemoService.class));
    }
}
