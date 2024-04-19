package com.xiaopeng.aiot.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.ArraySet;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.Iterator;
@Deprecated
/* loaded from: classes.dex */
public class IotService extends Service {
    private static final String TAG = "service";
    private ArraySet<IWorkService> mWorkServices;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogUtils.i("service", "onCreate");
        this.mWorkServices = new ArraySet<>();
        Iterator<IWorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            it.next().onCreate(this);
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        LogUtils.i("service", "onStartCommand " + intent.getAction());
        Iterator<IWorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            try {
                it.next().onStartCommand(this, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("service", "onDestroy");
        Iterator<IWorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            it.next().onDestroy(this);
        }
        this.mWorkServices.clear();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        LogUtils.i("service", "onLowMemory");
        Iterator<IWorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            it.next().onLowMemory();
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LogUtils.i("service", "onConfigurationChanged");
        Iterator<IWorkService> it = this.mWorkServices.iterator();
        while (it.hasNext()) {
            it.next().onConfigurationChanged(configuration);
        }
    }
}
