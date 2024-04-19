package com.xiaopeng.aiot.device.fridge.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.fridge.api.FridgeApi;
import com.xiaopeng.aiot.device.fridge.data.FridgeDevice;
import com.xiaopeng.iotlib.base.BaseViewModel;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.provider.blue.BluetoothCallBack;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.utils.ThreadUtils;
@Deprecated
/* loaded from: classes.dex */
public class FridgeVM extends BaseViewModel implements IFridgeVM, IDeviceApi.OnDataChangeListener<FridgeDevice>, BluetoothCallBack {
    private MutableLiveData<Integer> mBlueState;
    private MutableLiveData<FridgeDevice> mFridge;

    public FridgeVM() {
        logD("onCreate ");
        getApi().addOnDataChangeListener(this);
        BluetoothHelper.get().addCallBack(this);
    }

    private FridgeApi getApi() {
        return FridgeApi.getApi();
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm.IFridgeVM
    public MutableLiveData<FridgeDevice> getFridge() {
        if (this.mFridge == null) {
            this.mFridge = new MutableLiveData<>();
            logD("getFridge: ");
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aiot.device.fridge.vm.-$$Lambda$FridgeVM$rVGKTJFxZLv53LwU12KfWCUlkBA
                @Override // java.lang.Runnable
                public final void run() {
                    FridgeVM.this.lambda$getFridge$0$FridgeVM();
                }
            });
        }
        return this.mFridge;
    }

    public /* synthetic */ void lambda$getFridge$0$FridgeVM() {
        this.mFridge.postValue(getApi().loadData());
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm.IFridgeVM
    public MutableLiveData<Integer> getBlueState() {
        if (this.mBlueState == null) {
            this.mBlueState = new MutableLiveData<>();
            int state = BluetoothHelper.get().getState();
            logD("getBlueState: " + state);
            this.mBlueState.setValue(Integer.valueOf(state));
        }
        return this.mBlueState;
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm.IFridgeVM
    public void setTemperature(int i) {
        logD("setTemperature: " + i);
        getApi().setTemperature(i);
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm.IFridgeVM
    public void openBluetooth() {
        logD("openBluetooth: ");
        BluetoothHelper.get().open();
    }

    @Override // com.xiaopeng.aiot.device.fridge.vm.IFridgeVM
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
}
