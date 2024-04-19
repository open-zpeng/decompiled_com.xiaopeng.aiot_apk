package com.xiaopeng.iotlib.provider.iot.device;

import com.xiaopeng.xuimanager.iot.BaseDevice;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/* loaded from: classes.dex */
public class Device {
    private BaseDevice device;

    private Device(BaseDevice baseDevice) {
        this.device = baseDevice;
    }

    public static List<Device> create(List<BaseDevice> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (BaseDevice baseDevice : list) {
            if (baseDevice != null) {
                arrayList.add(new Device(baseDevice));
            }
        }
        return arrayList;
    }

    public static Device create(BaseDevice baseDevice) {
        if (baseDevice != null) {
            return new Device(baseDevice);
        }
        return null;
    }

    public BaseDevice getDevice() {
        return this.device;
    }

    public String getDeviceName() {
        return this.device.getDeviceName();
    }

    public void setDeviceId(String str) {
        this.device.setDeviceId(str);
    }

    public String getDeviceId() {
        return this.device.getDeviceId();
    }

    public String getDeviceType() {
        return this.device.getDeviceType();
    }

    public Map<String, String> getPropertyMap() {
        return this.device.getPropertyMap();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.device, ((Device) obj).device);
    }

    public int hashCode() {
        return Objects.hash(this.device);
    }

    public String toString() {
        BaseDevice baseDevice = this.device;
        if (baseDevice != null) {
            if (Scan.DEVICE_TYPE.equals(baseDevice.getDeviceType())) {
                if (this.device.getPropertyMap() != null) {
                    return this.device.getDeviceId() + " name: " + this.device.getDeviceName() + " rssi:" + ((String) this.device.getPropertyMap().get(Scan.PROP_BLUETOOTH_RSSI)) + " type:" + ((String) this.device.getPropertyMap().get(Scan.PROP_REAL_TYPE));
                }
                return this.device.getDeviceId() + " name: " + this.device.getDeviceName();
            }
            return this.device.toString();
        }
        return "";
    }
}
