package com.xiaopeng.aiot.device.fridge.vm2;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.fridge.api.FridgeApi;
import com.xiaopeng.aiot.device.fridge.data.FridgeDevice;
import com.xiaopeng.aiot.model.common.IntentActionUtils;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.base.BaseViewModel;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.provider.blue.BluetoothCallBack;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.provider.power.PowerCenterManager;
import com.xiaopeng.iotlib.utils.ThreadUtils;
/* loaded from: classes.dex */
public class FridgeVM2 extends BaseViewModel implements IFridgeVM2, IDeviceApi.OnDataChangeListener<FridgeDevice>, BluetoothCallBack, PowerCenterManager.Callback {
    private MutableLiveData<Integer> mBlueState;
    private MutableLiveData<FridgeDevice> mFridge;
    private MutableLiveData<Boolean> mPowerSwitch;
    private MutableLiveData<String> mTimeSet;

    public FridgeVM2() {
        logI("onCreate ");
        getApi().addOnDataChangeListener(this);
        BluetoothHelper.get().addCallBack(this);
        PowerCenterManager.get().addCallback(this);
    }

    private FridgeApi getApi() {
        return FridgeApi.getApi();
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public MutableLiveData<FridgeDevice> getFridge() {
        if (this.mFridge == null) {
            this.mFridge = new MutableLiveData<>();
            logD("getFridge: ");
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aiot.device.fridge.vm2.-$$Lambda$FridgeVM2$9I5v5MTjwsUs2XOXH-h3d8VcMCA
                @Override // java.lang.Runnable
                public final void run() {
                    FridgeVM2.this.lambda$getFridge$0$FridgeVM2();
                }
            });
        }
        return this.mFridge;
    }

    public /* synthetic */ void lambda$getFridge$0$FridgeVM2() {
        if (this.mFridge == null) {
            logI("getFridge mFridge is null ");
            return;
        }
        FridgeDevice loadData = getApi().loadData();
        MutableLiveData<FridgeDevice> mutableLiveData = this.mFridge;
        if (mutableLiveData != null) {
            mutableLiveData.postValue(loadData);
        } else {
            logI("getFridge mFridge is null ");
        }
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public MutableLiveData<Integer> getBlueState() {
        if (this.mBlueState == null) {
            this.mBlueState = new MutableLiveData<>();
            int state = BluetoothHelper.get().getState();
            logD("getBlueState: " + state);
            this.mBlueState.setValue(Integer.valueOf(state));
        }
        return this.mBlueState;
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public MutableLiveData<String> getTimeSet() {
        if (this.mTimeSet == null) {
            this.mTimeSet = new MutableLiveData<>();
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aiot.device.fridge.vm2.-$$Lambda$FridgeVM2$BNtghu-oMR6cb55-NPfZnV3Bk1w
                @Override // java.lang.Runnable
                public final void run() {
                    FridgeVM2.this.lambda$getTimeSet$1$FridgeVM2();
                }
            });
        }
        return this.mTimeSet;
    }

    public /* synthetic */ void lambda$getTimeSet$1$FridgeVM2() {
        String timeSet = PowerCenterManager.get().getTimeSet();
        logD("getTimeSet: " + timeSet);
        MutableLiveData<String> mutableLiveData = this.mTimeSet;
        if (mutableLiveData != null) {
            mutableLiveData.postValue(timeSet);
        }
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public MutableLiveData<Boolean> getPowerSwitch() {
        if (this.mPowerSwitch == null) {
            this.mPowerSwitch = new MutableLiveData<>();
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aiot.device.fridge.vm2.-$$Lambda$FridgeVM2$DUwMvkEG5CLNM7aMx_gr2N0Q8CA
                @Override // java.lang.Runnable
                public final void run() {
                    FridgeVM2.this.lambda$getPowerSwitch$2$FridgeVM2();
                }
            });
        }
        return this.mPowerSwitch;
    }

    public /* synthetic */ void lambda$getPowerSwitch$2$FridgeVM2() {
        boolean powerSwitch = PowerCenterManager.get().getPowerSwitch();
        logD("getFridgeSwitch: " + powerSwitch);
        MutableLiveData<Boolean> mutableLiveData = this.mPowerSwitch;
        if (mutableLiveData != null) {
            mutableLiveData.postValue(Boolean.valueOf(powerSwitch));
        }
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public void setPowerSwitch(final boolean z) {
        ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.aiot.device.fridge.vm2.-$$Lambda$FridgeVM2$7ZVmSy8zFiEE8lMJ_EjetPZELmc
            @Override // java.lang.Runnable
            public final void run() {
                FridgeVM2.this.lambda$setPowerSwitch$3$FridgeVM2(z);
            }
        });
    }

    public /* synthetic */ void lambda$setPowerSwitch$3$FridgeVM2(boolean z) {
        boolean fridgeSwitch = PowerCenterManager.get().setFridgeSwitch(z);
        logI("setPowerSwitch: " + fridgeSwitch);
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public void setTemperature(int i) {
        logD("setTemperature: " + i);
        getApi().setTemperature(i);
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public void openBluetooth() {
        logD("openBluetooth: ");
        BluetoothHelper.get().open();
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public void openTimeSetDialog() {
        logI("openTimeSet ");
        PowerCenterManager.get().openTimeSetDialog();
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public void callMaintainService(String str) {
        logI("callMaintainService ");
        IntentActionUtils.callOrDial(Iot.getApp(), str);
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2
    public boolean unBundling() {
        logD("unBundling: ");
        return getApi().unBundling();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        logI("onCleared ");
        this.mFridge = null;
        getApi().removeOnDataChangeListener(this);
        BluetoothHelper.get().removeCallBack(this);
        PowerCenterManager.get().removeCallback(this);
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onDataChanged(FridgeDevice fridgeDevice) {
        logI("onDataChanged: " + fridgeDevice);
        if (this.mFridge == null) {
            this.mFridge = new MutableLiveData<>();
        }
        this.mFridge.postValue(fridgeDevice);
    }

    @Override // com.xiaopeng.iotlib.provider.blue.BluetoothCallBack
    public void onBleStateChanged(int i) {
        logI("onStateChanged: " + i);
        if (this.mBlueState == null) {
            this.mBlueState = new MutableLiveData<>();
        }
        this.mBlueState.postValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.iotlib.provider.power.PowerCenterManager.Callback
    public void onPowerCenterSwitchChanged() {
        boolean powerSwitch = PowerCenterManager.get().getPowerSwitch();
        logI("onPowerSwitchChanged: " + powerSwitch);
        if (this.mPowerSwitch == null) {
            this.mPowerSwitch = new MutableLiveData<>();
        }
        this.mPowerSwitch.postValue(Boolean.valueOf(powerSwitch));
        getApi().powerState(powerSwitch);
    }

    @Override // com.xiaopeng.iotlib.provider.power.PowerCenterManager.Callback
    public void onPowerCenterTimeChanged() {
        String timeSet = PowerCenterManager.get().getTimeSet();
        logI("onPowerTimeChanged: " + timeSet);
        if (this.mTimeSet == null) {
            this.mTimeSet = new MutableLiveData<>();
        }
        this.mTimeSet.postValue(timeSet);
    }
}
