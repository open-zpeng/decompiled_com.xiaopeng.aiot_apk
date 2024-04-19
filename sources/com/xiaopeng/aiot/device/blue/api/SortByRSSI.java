package com.xiaopeng.aiot.device.blue.api;

import com.xiaopeng.aiot.device.blue.data.ScanDevice;
import java.util.Comparator;
/* loaded from: classes.dex */
class SortByRSSI implements Comparator<ScanDevice> {
    @Override // java.util.Comparator
    public int compare(ScanDevice scanDevice, ScanDevice scanDevice2) {
        return scanDevice2.getRssi() - scanDevice.getRssi();
    }
}
