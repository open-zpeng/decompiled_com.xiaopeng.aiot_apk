package com.xiaopeng.aiot.device.fragrance.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.fragrance.api.FragranceApi;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.iotlib.base.BaseViewModel;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.utils.ThreadUtils;
/* loaded from: classes.dex */
public class FragranceVM extends BaseViewModel implements IFragranceVM, IDeviceApi.OnDataChangeListener<FragranceDevice> {
    private MutableLiveData<FragranceDevice> mFragrance;

    public FragranceVM() {
        logI("onCreate ");
        getApi().addOnDataChangeListener(this);
    }

    private FragranceApi getApi() {
        return FragranceApi.getApi();
    }

    @Override // com.xiaopeng.aiot.device.fragrance.vm.IFragranceVM
    public MutableLiveData<FragranceDevice> getFragrance() {
        if (this.mFragrance == null) {
            this.mFragrance = new MutableLiveData<>();
            loadFragrance();
        }
        return this.mFragrance;
    }

    @Override // com.xiaopeng.aiot.device.fragrance.vm.IFragranceVM
    public void setEnable(boolean z) {
        logD("setEnable: " + z);
        getApi().setEnable(z);
    }

    private void loadFragrance() {
        logD("loadFragrance:");
        ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aiot.device.fragrance.vm.-$$Lambda$FragranceVM$DiudIlNYCc1iywzM1QQq7Sj-A-o
            @Override // java.lang.Runnable
            public final void run() {
                FragranceVM.this.lambda$loadFragrance$0$FragranceVM();
            }
        });
    }

    public /* synthetic */ void lambda$loadFragrance$0$FragranceVM() {
        if (this.mFragrance == null) {
            logI("loadFragrance: mFragrance is null ");
            return;
        }
        FragranceDevice loadData = getApi().loadData();
        MutableLiveData<FragranceDevice> mutableLiveData = this.mFragrance;
        if (mutableLiveData != null) {
            mutableLiveData.postValue(loadData);
        } else {
            logI("loadFragrance: mFragrance is null ");
        }
    }

    @Override // com.xiaopeng.aiot.device.fragrance.vm.IFragranceVM
    public void setChoiceChannel(int i) {
        logD("setChoiceChannel: " + i);
        getApi().setChoiceChannel(i);
    }

    @Override // com.xiaopeng.aiot.device.fragrance.vm.IFragranceVM
    public void setDensity(int i) {
        logD("setDensity: " + i);
        getApi().setDensity(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        logI("onCleared ");
        this.mFragrance = null;
        getApi().removeOnDataChangeListener(this);
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onDataChanged(FragranceDevice fragranceDevice) {
        logI("onDataChanged: " + fragranceDevice);
        if (this.mFragrance == null) {
            this.mFragrance = new MutableLiveData<>();
        }
        this.mFragrance.postValue(fragranceDevice);
    }
}
