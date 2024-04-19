package com.xiaopeng.aiot.device.watches.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.aiot.model.common.IntentActionUtils;
import com.xiaopeng.aiot.model.scene.WatchesScene;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.base.BaseViewModel;
/* loaded from: classes.dex */
public class WatchesVM extends BaseViewModel implements IWatchesVM, WatchesScene.CallBack {
    private MutableLiveData<Boolean> mMarkEnable;
    private MutableLiveData<Integer> mResult;
    private MutableLiveData<Boolean> mState;
    private MutableLiveData<WatchesDevice> mWatches;

    @Override // com.xiaopeng.aiot.model.scene.WatchesScene.CallBack
    public void onAccountSwitch() {
    }

    public WatchesVM() {
        logD("onCreate ");
        WatchesScene.get().addCallBack(this);
    }

    @Override // com.xiaopeng.aiot.device.watches.vm.IWatchesVM
    public MutableLiveData<WatchesDevice> getWatches() {
        logD("getWatches: ");
        if (this.mWatches == null) {
            this.mWatches = new MutableLiveData<>();
        }
        this.mWatches.postValue(WatchesScene.get().loadWatchesDevice());
        return this.mWatches;
    }

    @Override // com.xiaopeng.aiot.device.watches.vm.IWatchesVM
    public MutableLiveData<Boolean> getState() {
        logD("getState: ");
        if (this.mState == null) {
            this.mState = new MutableLiveData<>();
        }
        this.mState.postValue(Boolean.valueOf(WatchesScene.get().isLogin()));
        return this.mState;
    }

    @Override // com.xiaopeng.aiot.device.watches.vm.IWatchesVM
    public MutableLiveData<Boolean> getMarkEnable() {
        logD("getState: ");
        if (this.mMarkEnable == null) {
            this.mMarkEnable = new MutableLiveData<>();
        }
        this.mMarkEnable.postValue(Boolean.valueOf(WatchesScene.get().isMarkEnable()));
        return this.mMarkEnable;
    }

    @Override // com.xiaopeng.aiot.device.watches.vm.IWatchesVM
    public MutableLiveData<Integer> getResult() {
        logD("getState: ");
        if (this.mResult == null) {
            this.mResult = new MutableLiveData<>();
        }
        return this.mResult;
    }

    @Override // com.xiaopeng.aiot.device.watches.vm.IWatchesVM
    public void callPhone() {
        WatchesDevice value;
        logI("callPhone: ");
        MutableLiveData<WatchesDevice> mutableLiveData = this.mWatches;
        if (mutableLiveData == null || (value = mutableLiveData.getValue()) == null || value.getPhone() == null) {
            return;
        }
        IntentActionUtils.callOrDial(Iot.getApp(), value.getPhone());
    }

    @Override // com.xiaopeng.aiot.device.watches.vm.IWatchesVM
    public void setMarkEnable(boolean z) {
        logI("setMarkEnable: " + z);
        WatchesScene.get().setMarkEnable(z);
    }

    @Override // com.xiaopeng.aiot.device.watches.vm.IWatchesVM
    public void questLocation() {
        WatchesScene.get().questLocation();
    }

    @Override // com.xiaopeng.aiot.model.scene.WatchesScene.CallBack
    public void onLoginChanged(boolean z) {
        logI("onStateChanged: " + z);
        if (this.mState == null) {
            this.mState = new MutableLiveData<>();
        }
        this.mState.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.aiot.model.scene.WatchesScene.CallBack
    public void onDeviceChanged(WatchesDevice watchesDevice) {
        logI("onDeviceChanged: " + watchesDevice);
        if (this.mWatches == null) {
            this.mWatches = new MutableLiveData<>();
        }
        this.mWatches.postValue(watchesDevice);
    }

    @Override // com.xiaopeng.aiot.model.scene.WatchesScene.CallBack
    public void onResult(int i) {
        logI("onResult: " + i);
        if (this.mResult == null) {
            this.mResult = new MutableLiveData<>();
        }
        this.mResult.postValue(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        logI("onCleared ");
        WatchesScene.get().removeCallBack(this);
    }
}
