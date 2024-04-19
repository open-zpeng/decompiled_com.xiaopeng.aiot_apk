package com.xiaopeng.aiot.device.airbed.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.airbed.data.AirBedDevice;
/* loaded from: classes.dex */
public interface IAirBedVM {
    void deflateBed();

    MutableLiveData<AirBedDevice> getAirBed();

    void hardnessDown(String str);

    void hardnessSet(String str);

    void hardnessUp(String str);

    void inflateBed();

    void interruptBump();

    void openBluetooth();

    boolean unBundling();
}
