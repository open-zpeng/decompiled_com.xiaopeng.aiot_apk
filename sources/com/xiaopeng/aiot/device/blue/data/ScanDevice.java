package com.xiaopeng.aiot.device.blue.data;

import com.xiaopeng.iotlib.data.DeviceInfo;
import com.xiaopeng.iotlib.provider.iot.device.Device;
import com.xiaopeng.iotlib.provider.iot.device.Scan;
import com.xiaopeng.iotlib.utils.Utils;
/* loaded from: classes.dex */
public class ScanDevice extends DeviceInfo {
    private Device device;
    private int rssi;

    private ScanDevice() {
    }

    public static ScanDevice create(Device device) {
        if (device == null || device.getDeviceId() == null || device.getPropertyMap() == null || !Scan.VAL_TYPE_BLUETOOTH_BLE.equals(device.getPropertyMap().get(Scan.PROP_REAL_TYPE))) {
            return null;
        }
        ScanDevice scanDevice = new ScanDevice();
        scanDevice.setName(device.getDeviceName());
        scanDevice.setDeviceId(device.getDeviceId());
        scanDevice.setDeviceType(device.getDeviceType());
        scanDevice.rssi = Utils.parse(device.getPropertyMap().get(Scan.PROP_BLUETOOTH_RSSI));
        scanDevice.device = device;
        return scanDevice;
    }

    public Device getDevice() {
        return this.device;
    }

    public int getRssi() {
        return this.rssi;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String toString() {
        return "ScanDevice{rssi=" + this.rssi + ", Id='" + this.deviceId + "'}";
    }
}
