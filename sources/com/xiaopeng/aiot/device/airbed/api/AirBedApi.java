package com.xiaopeng.aiot.device.airbed.api;

import com.xiaopeng.aiot.device.airbed.data.AirBedDevice;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public interface AirBedApi extends IDeviceApi<AirBedDevice> {
    void deflateBed();

    void hardnessDown(String str);

    void hardnessSet(String str);

    void hardnessUp(String str);

    void inflateBed();

    void interruptBedBump();

    void setHardnessLevel(int i);

    static AirBedApi getApi() {
        return SingletonHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final AirBedApi INSTANCE = create();

        private SingletonHolder() {
        }

        private static AirBedApi create() {
            LogUtils.d(LogConfig.TAG_DEVICE_API_FRIDGE, "create api");
            return new AirBedApiXuiImpl();
        }
    }
}
