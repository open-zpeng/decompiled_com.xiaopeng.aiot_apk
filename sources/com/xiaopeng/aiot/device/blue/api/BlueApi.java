package com.xiaopeng.aiot.device.blue.api;

import com.xiaopeng.aiot.device.blue.data.ScanDevice;
import com.xiaopeng.aiot.device.blue.data.ScanDevicePoxy;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.Set;
/* loaded from: classes.dex */
public interface BlueApi extends IDeviceApi<ScanDevicePoxy> {
    boolean bind(ScanDevice scanDevice, String str);

    boolean binding(String str, String str2);

    void setFilterMac(Set<String> set);

    void setFilterName(String str);

    void setMultipleFilterNames(Set<String> set);

    void start();

    void stop();

    void unbind(String str);

    static BlueApi getApi() {
        return SingletonHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final BlueApi INSTANCE = create();

        private SingletonHolder() {
        }

        private static BlueApi create() {
            LogUtils.d(LogConfig.TAG_DEVICE_API_BLUE, "create api");
            return new BlueApiImpl();
        }
    }
}
