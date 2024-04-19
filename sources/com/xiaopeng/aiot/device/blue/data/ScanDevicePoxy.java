package com.xiaopeng.aiot.device.blue.data;

import com.xiaopeng.iotlib.data.DeviceInfo;
import java.util.List;
/* loaded from: classes.dex */
public class ScanDevicePoxy extends DeviceInfo {
    private List<ScanDevice> list;

    private ScanDevicePoxy(List<ScanDevice> list) {
        this.list = list;
    }

    public static ScanDevicePoxy create(List<ScanDevice> list) {
        return new ScanDevicePoxy(list);
    }

    public List<ScanDevice> getList() {
        return this.list;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String toString() {
        return "ScanDevicePoxy{list=" + this.list + '}';
    }
}
