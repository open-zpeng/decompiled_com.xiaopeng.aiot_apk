package com.xiaopeng.aiot.device.airbed.data;

import com.xiaopeng.iotlib.data.DeviceInfo;
/* loaded from: classes.dex */
public class AirBedDevice extends DeviceInfo {
    public static final int STATUS_BLE_CLOSED = 1;
    public static final int STATUS_BLE_CONNECTING = 3;
    public static final int STATUS_BLE_TURN_ON = 4;
    public static final int STATUS_BLE_TURN_ONING = 2;
    public static final int STATUS_UNBINDING = -1;
    private String bedBumpStatus;
    private String bedHardnessLevel;
    private String bedHardwareStatus;
    private int status = -1;

    public String getBedHardwareStatus() {
        return this.bedHardwareStatus;
    }

    public void setBedHardwareStatus(String str) {
        this.bedHardwareStatus = str;
    }

    public String getBedBumpStatus() {
        return this.bedBumpStatus;
    }

    public void setBedBumpStatus(String str) {
        this.bedBumpStatus = str;
    }

    public String getBedHardnessLevel() {
        return this.bedHardnessLevel;
    }

    public void setBedHardnessLevel(String str) {
        this.bedHardnessLevel = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String toString() {
        return "AirBedDevice{status=" + this.status + ", bedHardwareStatus='" + this.bedHardwareStatus + "', bedBumpStatus='" + this.bedBumpStatus + "', bedHardnessLevel='" + this.bedHardnessLevel + "', name='" + this.name + "', deviceId='" + this.deviceId + "', deviceType='" + this.deviceType + "'}";
    }
}
