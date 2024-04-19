package com.xiaopeng.iotlib.data;

import java.util.Objects;
/* loaded from: classes.dex */
public class DeviceInfo {
    protected String deviceId;
    protected String deviceType;
    protected String name;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String str) {
        this.deviceType = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.deviceId, ((DeviceInfo) obj).deviceId);
    }

    public int hashCode() {
        return Objects.hash(this.deviceId);
    }

    public String toString() {
        return "DeviceInfo{name='" + this.name + "', deviceId='" + this.deviceId + "', deviceType='" + this.deviceType + "'}";
    }
}
