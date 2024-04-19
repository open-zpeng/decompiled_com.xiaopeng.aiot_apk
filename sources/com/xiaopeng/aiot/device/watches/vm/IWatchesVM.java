package com.xiaopeng.aiot.device.watches.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
/* loaded from: classes.dex */
public interface IWatchesVM {
    void callPhone();

    MutableLiveData<Boolean> getMarkEnable();

    MutableLiveData<Integer> getResult();

    MutableLiveData<Boolean> getState();

    MutableLiveData<WatchesDevice> getWatches();

    void questLocation();

    void setMarkEnable(boolean z);
}
