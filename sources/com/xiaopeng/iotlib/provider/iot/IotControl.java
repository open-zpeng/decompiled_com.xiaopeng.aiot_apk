package com.xiaopeng.iotlib.provider.iot;

import com.xiaopeng.iotlib.provider.iot.device.Device;
import com.xiaopeng.iotlib.provider.xui.IXuiControl;
import java.util.List;
import java.util.Map;
/* loaded from: classes.dex */
public interface IotControl extends IXuiControl {

    /* loaded from: classes.dex */
    public interface IDeviceListenerListener {
        void onDeviceAdd(List<Device> list);

        void onDeviceBindChanged(String str, String str2);

        void onOperationResult(String str, String str2, String str3);

        void onPropertiesUpdated(String str, Map<String, String> map);
    }

    void addListener(IDeviceListenerListener iDeviceListenerListener);

    List<Device> getDevice(String str);

    Map<String, String> getDeviceProperties(Device device);

    void removeListener(IDeviceListenerListener iDeviceListenerListener);

    boolean sendCommand(Device device, String str, String str2);

    boolean setDeviceProperties(Device device, Map<String, String> map);

    void subscribeNotifications(Device device);

    void unSubscribeNotifications(Device device);
}
