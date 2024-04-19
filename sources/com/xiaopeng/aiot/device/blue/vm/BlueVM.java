package com.xiaopeng.aiot.device.blue.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.blue.api.BlueApi;
import com.xiaopeng.aiot.device.blue.data.ScanDevice;
import com.xiaopeng.aiot.device.blue.data.ScanDevicePoxy;
import com.xiaopeng.iotlib.base.BaseViewModel;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.provider.blue.BluetoothCallBack;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import java.util.List;
import java.util.Set;
/* loaded from: classes.dex */
public class BlueVM extends BaseViewModel implements IBlueVM, IDeviceApi.OnDataChangeListener<ScanDevicePoxy>, BluetoothCallBack {
    private MutableLiveData<List<ScanDevice>> mBlue = new MutableLiveData<>();
    private MutableLiveData<Boolean> mBlueScan = new MutableLiveData<>();
    private MutableLiveData<Integer> mBlueState;
    private DemoScan mDemoScan;

    public BlueVM() {
        logD("onCreate ");
        getApi().addOnDataChangeListener(this);
        BluetoothHelper.get().addCallBack(this);
        if (ApiConfig.API_DEBUG) {
            this.mDemoScan = new DemoScan(this);
        }
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public void setFilterName(String str) {
        getApi().setFilterName(str);
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public void setMultiFilterName(Set<String> set) {
        logD("Blue VM setMultipleNames");
        getApi().setMultipleFilterNames(set);
    }

    private BlueApi getApi() {
        return BlueApi.getApi();
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public MutableLiveData<List<ScanDevice>> getBlue() {
        return this.mBlue;
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public MutableLiveData<Integer> getBlueState() {
        if (this.mBlueState == null) {
            this.mBlueState = new MutableLiveData<>();
            this.mBlueState.setValue(Integer.valueOf(BluetoothHelper.get().getState()));
        }
        return this.mBlueState;
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public MutableLiveData<Boolean> getBlueScan() {
        return this.mBlueScan;
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public void setBlueEnable(boolean z) {
        if (z) {
            BluetoothHelper.get().open();
        } else {
            BluetoothHelper.get().close();
        }
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public void start() {
        logD("start ");
        getApi().start();
        this.mBlueScan.setValue(true);
        DemoScan demoScan = this.mDemoScan;
        if (demoScan != null) {
            demoScan.start();
        }
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public void stop() {
        logD("stop ");
        getApi().stop();
        this.mBlueScan.setValue(false);
        DemoScan demoScan = this.mDemoScan;
        if (demoScan != null) {
            demoScan.stop();
        }
    }

    @Override // com.xiaopeng.aiot.device.blue.vm.IBlueVM
    public boolean bind(ScanDevice scanDevice, String str) {
        logD("bind " + scanDevice + " , deviceType " + scanDevice);
        return getApi().bind(scanDevice, str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        logI("onCleared ");
        this.mBlue = null;
        getApi().removeOnDataChangeListener(this);
        BluetoothHelper.get().removeCallBack(this);
        DemoScan demoScan = this.mDemoScan;
        if (demoScan != null) {
            demoScan.onCleared();
        }
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onDataChanged(ScanDevicePoxy scanDevicePoxy) {
        this.mBlue.postValue(scanDevicePoxy.getList());
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
