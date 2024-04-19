package com.xiaopeng.aiot.device.fragrance.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
/* loaded from: classes.dex */
public interface IFragranceVM {
    MutableLiveData<FragranceDevice> getFragrance();

    void setChoiceChannel(int i);

    void setDensity(int i);

    void setEnable(boolean z);
}
