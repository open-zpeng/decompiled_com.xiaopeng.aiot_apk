package com.xiaopeng.aiot.device.fridge.data;

import com.xiaopeng.iotlib.data.DeviceInfo;
/* loaded from: classes.dex */
public class FridgeDevice extends DeviceInfo {
    public static final int CON_STATUS_CONNECTED = 100;
    public static final int CON_STATUS_DISCONNECT = 101;
    public static final int CON_STATUS_UNIT = -1;
    public static final int TEMPERATURE_HIGH = 102;
    public static final int TEMPERATURE_LOW = 100;
    public static final int TEMPERATURE_MIDDLE = 101;
    public static final int TEMPERATURE_UNKNOWN = -1;
    private String errorCode;
    private boolean hasBinding;
    private String temperature;
    private int connect = -1;
    private int temperatureSet = -1;

    public boolean isHasBinding() {
        return this.hasBinding;
    }

    public void setHasBinding(boolean z) {
        this.hasBinding = z;
    }

    public int getTemperatureSet() {
        return this.temperatureSet;
    }

    public void setTemperatureSet(int i) {
        this.temperatureSet = i;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String str) {
        this.temperature = str;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String str) {
        this.errorCode = str;
    }

    public int getConnect() {
        return this.connect;
    }

    public void setConnect(int i) {
        this.connect = i;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String toString() {
        return "FridgeDevice{hasBinding=" + this.hasBinding + ", connect=" + this.connect + ", temperature='" + this.temperature + "', temperatureSet=" + this.temperatureSet + ", errorCode='" + this.errorCode + "'}";
    }
}
