package com.xiaopeng.iotlib.provider.blue;
/* loaded from: classes.dex */
public interface BluetoothCallBack {
    public static final int STATE_OFF = 10;
    public static final int STATE_ON = 12;
    public static final int STATE_TURNING_OFF = 13;
    public static final int STATE_TURNING_ON = 11;

    void onBleStateChanged(int i);
}
