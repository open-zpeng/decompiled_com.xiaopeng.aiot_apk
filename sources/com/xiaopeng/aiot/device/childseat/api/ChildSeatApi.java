package com.xiaopeng.aiot.device.childseat.api;

import com.xiaopeng.aiot.device.childseat.data.ChildSeatDevice;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public interface ChildSeatApi extends IDeviceApi<ChildSeatDevice> {
    static ChildSeatApi getApi() {
        return SingletonHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final ChildSeatApi INSTANCE = create();

        private SingletonHolder() {
        }

        private static ChildSeatApi create() {
            LogUtils.d(LogConfig.TAG_DEVICE_API_SEAT, "create api");
            return new ChildSeatApiImpl();
        }
    }
}
