package com.xiaopeng.aiot.device.airbed.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.airbed.api.AirBedApi;
import com.xiaopeng.aiot.device.airbed.data.AirBedDevice;
import com.xiaopeng.iotlib.base.BaseViewModel;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.utils.ThreadUtils;
/* loaded from: classes.dex */
public class AirBedVM extends BaseViewModel implements IAirBedVM, IDeviceApi.OnDataChangeListener<AirBedDevice> {
    private MutableLiveData<AirBedDevice> mAirBedLiveData;

    public AirBedVM() {
        logD("onCreate ");
        getApi().addOnDataChangeListener(this);
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onDataChanged(AirBedDevice airBedDevice) {
        logI("onDataChanged: " + airBedDevice);
        if (this.mAirBedLiveData == null) {
            this.mAirBedLiveData = new MutableLiveData<>();
        }
        this.mAirBedLiveData.postValue(airBedDevice);
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public MutableLiveData<AirBedDevice> getAirBed() {
        if (this.mAirBedLiveData == null) {
            this.mAirBedLiveData = new MutableLiveData<>();
            logD("getChildSeat: ");
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aiot.device.airbed.vm.-$$Lambda$AirBedVM$2wCbpzf8dBWgVqbqfQUWl9N-Mtg
                @Override // java.lang.Runnable
                public final void run() {
                    AirBedVM.this.lambda$getAirBed$0$AirBedVM();
                }
            });
        }
        return this.mAirBedLiveData;
    }

    public /* synthetic */ void lambda$getAirBed$0$AirBedVM() {
        AirBedDevice loadData = getApi().loadData();
        MutableLiveData<AirBedDevice> mutableLiveData = this.mAirBedLiveData;
        if (mutableLiveData != null) {
            mutableLiveData.postValue(loadData);
        } else {
            logI("getAirBed:mAirBedLiveData is null ");
        }
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public boolean unBundling() {
        logD("unBundling: ");
        return getApi().unBundling();
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public void openBluetooth() {
        logD("openBluetooth: ");
        BluetoothHelper.get().open();
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public void inflateBed() {
        getApi().inflateBed();
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public void deflateBed() {
        getApi().deflateBed();
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public void interruptBump() {
        getApi().interruptBedBump();
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public void hardnessUp(String str) {
        getApi().hardnessUp(str);
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public void hardnessDown(String str) {
        getApi().hardnessDown(str);
    }

    @Override // com.xiaopeng.aiot.device.airbed.vm.IAirBedVM
    public void hardnessSet(String str) {
        getApi().hardnessSet(str);
    }

    private AirBedApi getApi() {
        return AirBedApi.getApi();
    }
}
