package com.xiaopeng.aiot.device.freezer.api;

import com.xiaopeng.aiot.device.freezer.api.FreezerApiXuiImpl;
import com.xiaopeng.aiot.device.freezer.data.FreezerDevice;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public interface FreezerApi extends IDeviceApi<FreezerDevice> {

    /* loaded from: classes.dex */
    public interface Callback {
        void onMPowerChanged();
    }

    void addATemper();

    void addCallback(FreezerApiXuiImpl.Callback callback);

    boolean isChildLock();

    boolean isDoorOpen();

    boolean isPowerOpen();

    void powerState(boolean z);

    void reduceATemper();

    void removeCallback(FreezerApiXuiImpl.Callback callback);

    void setChildLock(int i);

    void setDoorOpen(int i);

    void setHoldTime(int i);

    void setLockHold(int i);

    void setPowerOC(int i);

    void setTemper(int i);

    void setWorkMode(int i);

    static FreezerApi getApi() {
        return SingletonHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final FreezerApi INSTANCE = create();

        private SingletonHolder() {
        }

        private static FreezerApi create() {
            LogUtils.d(LogConfig.TAG_DEVICE_API_FREEZER, "create api");
            return new FreezerApiXuiImpl();
        }
    }
}
