package com.xiaopeng.aiot.device.blue.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.blue.data.ScanDevice;
import java.util.List;
import java.util.Set;
/* loaded from: classes.dex */
public interface IBlueVM {
    boolean bind(ScanDevice scanDevice, String str);

    MutableLiveData<List<ScanDevice>> getBlue();

    MutableLiveData<Boolean> getBlueScan();

    MutableLiveData<Integer> getBlueState();

    void setBlueEnable(boolean z);

    void setFilterName(String str);

    void setMultiFilterName(Set<String> set);

    void start();

    void stop();
}
