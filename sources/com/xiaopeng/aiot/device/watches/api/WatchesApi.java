package com.xiaopeng.aiot.device.watches.api;

import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public interface WatchesApi extends IDeviceApi<WatchesDevice> {
    void questBind();

    void questLocation();

    void questLocationUpdate();

    void questStatus();

    void questUserInfo();

    static WatchesApi getApi() {
        return SingletonHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final WatchesApi INSTANCE = create();

        private SingletonHolder() {
        }

        private static WatchesApi create() {
            LogUtils.d(LogConfig.TAG_DEVICE_API_WATCHES, "create api");
            return new WatchesApiImpl();
        }
    }
}
