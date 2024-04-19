package com.xiaopeng.iotlib.provider.iot;

import com.xiaopeng.xuimanager.iot.BaseDevice;
import com.xiaopeng.xuimanager.iot.IoTManager;
/* loaded from: classes.dex */
public class IotManagers {
    private static final String TAG = "IotManager";
    private static volatile IotControlImpl mIotControl;

    public static void init() {
        getIotControlImpl().setIoTManager(IoTManager.getInstance());
        BaseDevice.setMaxPropertyLogLength(64);
    }

    private static IotControlImpl getIotControlImpl() {
        if (mIotControl == null) {
            synchronized (IotManagers.class) {
                if (mIotControl == null) {
                    mIotControl = new IotControlImpl();
                }
            }
        }
        return mIotControl;
    }

    public static IotControl getIotControl() {
        return getIotControlImpl();
    }
}
