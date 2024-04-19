package com.xiaopeng.aiot.device.fragrance.api;

import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public interface FragranceApi extends IDeviceApi<FragranceDevice> {
    default void playSoundEffect(int i) {
    }

    void setChoiceChannel(int i);

    void setChoiceChannel(int i, boolean z);

    void setDensity(int i);

    void setDensity(int i, boolean z);

    void setEnable(boolean z);

    static FragranceApi getApi() {
        return SingletonHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final FragranceApi INSTANCE = create();

        private SingletonHolder() {
        }

        private static FragranceApi create() {
            LogUtils.d(LogConfig.TAG_DEVICE_API_FRAGRANCE, "create api");
            return new FragranceApiXuiImpl();
        }
    }
}
