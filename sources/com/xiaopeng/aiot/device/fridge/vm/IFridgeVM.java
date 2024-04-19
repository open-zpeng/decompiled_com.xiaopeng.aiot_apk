package com.xiaopeng.aiot.device.fridge.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.fridge.data.FridgeDevice;
@Deprecated
/* loaded from: classes.dex */
public interface IFridgeVM {
    MutableLiveData<Integer> getBlueState();

    MutableLiveData<FridgeDevice> getFridge();

    void openBluetooth();

    void setTemperature(int i);

    boolean unBundling();
}
