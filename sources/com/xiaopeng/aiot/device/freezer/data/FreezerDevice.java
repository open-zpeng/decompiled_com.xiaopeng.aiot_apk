package com.xiaopeng.aiot.device.freezer.data;

import com.xiaopeng.iotlib.data.DeviceInfo;
/* loaded from: classes.dex */
public class FreezerDevice extends DeviceInfo {
    public static final int HOLDING_TIME = 0;
    public static final int MODE_COLD = 1;
    public static final int MODE_HEAT = 2;
    public static final int MODE_OFF = 0;
    public static final int TEMPERATURE_COLD_MAIN = 0;
    public static final int TEMPERATURE_COLD_MAX = 20;
    public static final int TEMPERATURE_HOT_MAIN = 30;
    public static final int TEMPERATURE_HOT_MAX = 50;
    private int holdingTime;
    private String temperature;
    private int workMode = 0;

    public void setTemperature(String str) {
        this.temperature = str;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setWorkMode(int i) {
        this.workMode = i;
    }

    public int getWorkMode() {
        return this.workMode;
    }

    public void setHoldingTime(int i) {
        this.holdingTime = i;
    }

    public int getHoldingTime() {
        return this.holdingTime;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String toString() {
        return "FreezerDevice{workMode=" + this.workMode + ", temperature='" + this.temperature + "', holdingTime=" + this.holdingTime + '}';
    }
}
