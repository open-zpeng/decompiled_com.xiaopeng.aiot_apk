package com.xiaopeng.iotlib.provider.iot;

import com.xiaopeng.iotlib.provider.iot.device.Scan;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.xuimanager.iot.IDeviceListener;
import com.xiaopeng.xuimanager.iot.devices.ScanDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/* loaded from: classes.dex */
class IotControlDebugScan implements Runnable {
    private static final String TAG = "XuiIot";
    private int index;
    private IDeviceListener mIDeviceListener;
    private String[] mac = {"24:35:CC:13:C5:1D", "E6:5A:30:B0:A5:70", "44:5A:30:B0:A5:70", "55:5A:30:B0:A5:70", "66:5A:30:B0:A5:70", "77:5A:30:B0:A5:70", "88:5A:30:B0:A5:70", "11:22:33:44:55:66", "23:22:33:44:55:66", "34:22:33:44:55:66", "45:22:33:44:55:66"};
    private String[] name = {"小鹏车载冰箱", "XPeng-AB0", "IroboSeat", "小鹏车载冰箱", "儿童座椅", "蓝牙床垫", "小鹏车载", "ABCD", "小鹏车载冰箱", "Baby Smart Seat", "小鹏儿童座椅", "小鹏车载冰箱", "小鹏车载冰箱", "小鹏蓝牙床垫", "蓝牙床垫"};

    /* JADX INFO: Access modifiers changed from: package-private */
    public IotControlDebugScan(IDeviceListener iDeviceListener) {
        this.mIDeviceListener = iDeviceListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        ArrayList arrayList = new ArrayList();
        int nextInt = new Random().nextInt(3) + 1;
        for (int i = 0; i < nextInt; i++) {
            arrayList.add(create());
        }
        IDeviceListener iDeviceListener = this.mIDeviceListener;
        if (iDeviceListener != null) {
            iDeviceListener.onDeviceAdd(arrayList);
        }
        ThreadUtils.UI.postDelay(this, 1000L);
    }

    private ScanDevice create() {
        this.index++;
        HashMap hashMap = new HashMap();
        hashMap.put(Scan.PROP_BLUETOOTH_RSSI, String.valueOf(-new Random().nextInt(100)));
        hashMap.put(Scan.PROP_REAL_TYPE, Scan.VAL_TYPE_BLUETOOTH_BLE);
        String[] strArr = this.name;
        String str = strArr[this.index % strArr.length];
        StringBuilder sb = new StringBuilder();
        String[] strArr2 = this.mac;
        sb.append(strArr2[this.index % strArr2.length]);
        sb.append(":");
        sb.append(this.index);
        ScanDevice scanDevice = new ScanDevice(str, sb.toString(), Scan.DEVICE_TYPE);
        scanDevice.setPropertyMap(hashMap);
        return scanDevice;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void start() {
        LogUtils.d("XuiIot", "IotControlDebugScan start");
        stop();
        ThreadUtils.UI.postDelay(this, 500L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stop() {
        LogUtils.d("XuiIot", "IotControlDebugScan stop");
        ThreadUtils.UI.removeCallbacks(this);
        this.index = 0;
    }
}
