package com.xiaopeng.aiot.device.childseat.data;

import com.xiaopeng.iotlib.data.DeviceInfo;
/* loaded from: classes.dex */
public class ChildSeatDevice extends DeviceInfo {
    public static final int STATUS_BLE_CLOSED = 1;
    public static final int STATUS_BLE_CONNECTING = 3;
    public static final int STATUS_BLE_TURN_ON = 2;
    public static final int STATUS_INSTALLED = 5;
    public static final int STATUS_INVALID = 6;
    public static final int STATUS_UNBINDING = -1;
    public static final int STATUS_UNINSTALLED = 4;
    private int status = -1;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String toString() {
        return "ChildSeatDevice{status=" + this.status + '}';
    }
}
