package com.xiaopeng.aiot.device.childseat.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.childseat.data.ChildSeatDevice;
/* loaded from: classes.dex */
public interface IChildSeatVM {
    MutableLiveData<ChildSeatDevice> getChildSeat();

    void openBluetooth();

    boolean unBundling();
}
