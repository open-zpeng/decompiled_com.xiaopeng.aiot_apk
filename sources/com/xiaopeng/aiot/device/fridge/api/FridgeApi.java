package com.xiaopeng.aiot.device.fridge.api;

import com.xiaopeng.aiot.device.fridge.data.FridgeDevice;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public interface FridgeApi extends IDeviceApi<FridgeDevice> {
    void powerState(boolean z);

    void setTemperature(int i);

    static FridgeApi getApi() {
        return SingletonHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final FridgeApi INSTANCE = create();

        private SingletonHolder() {
        }

        private static FridgeApi create() {
            LogUtils.d(LogConfig.TAG_DEVICE_API_FRIDGE, "create api");
            return new FridgeApiXuiImpl();
        }
    }
}
