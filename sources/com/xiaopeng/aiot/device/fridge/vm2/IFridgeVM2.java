package com.xiaopeng.aiot.device.fridge.vm2;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.fridge.data.FridgeDevice;
/* loaded from: classes.dex */
public interface IFridgeVM2 {
    void callMaintainService(String str);

    MutableLiveData<Integer> getBlueState();

    MutableLiveData<FridgeDevice> getFridge();

    MutableLiveData<Boolean> getPowerSwitch();

    MutableLiveData<String> getTimeSet();

    void openBluetooth();

    void openTimeSetDialog();

    void setPowerSwitch(boolean z);

    void setTemperature(int i);

    boolean unBundling();
}
