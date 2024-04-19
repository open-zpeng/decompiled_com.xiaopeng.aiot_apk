package com.xiaopeng.aiot.device.childseat.vm;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.aiot.device.childseat.api.ChildSeatApi;
import com.xiaopeng.aiot.device.childseat.data.ChildSeatDevice;
import com.xiaopeng.iotlib.base.BaseViewModel;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.utils.ThreadUtils;
/* loaded from: classes.dex */
public class ChildSeatVM extends BaseViewModel implements IChildSeatVM, IDeviceApi.OnDataChangeListener<ChildSeatDevice> {
    private MutableLiveData<ChildSeatDevice> mChildSeatDevice;

    public ChildSeatVM() {
        logD("onCreate ");
        getApi().addOnDataChangeListener(this);
    }

    private ChildSeatApi getApi() {
        return ChildSeatApi.getApi();
    }

    @Override // com.xiaopeng.aiot.device.childseat.vm.IChildSeatVM
    public MutableLiveData<ChildSeatDevice> getChildSeat() {
        if (this.mChildSeatDevice == null) {
            this.mChildSeatDevice = new MutableLiveData<>();
            logD("getChildSeat: ");
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.aiot.device.childseat.vm.-$$Lambda$ChildSeatVM$Nzg3A-wTtZPjob0nXy0Uuf7_KOk
                @Override // java.lang.Runnable
                public final void run() {
                    ChildSeatVM.this.lambda$getChildSeat$0$ChildSeatVM();
                }
            });
        }
        return this.mChildSeatDevice;
    }

    public /* synthetic */ void lambda$getChildSeat$0$ChildSeatVM() {
        ChildSeatDevice loadData = getApi().loadData();
        MutableLiveData<ChildSeatDevice> mutableLiveData = this.mChildSeatDevice;
        if (mutableLiveData != null) {
            mutableLiveData.postValue(loadData);
        } else {
            logI("getChildSeat:mChildSeatDevice is null ");
        }
    }

    @Override // com.xiaopeng.aiot.device.childseat.vm.IChildSeatVM
    public boolean unBundling() {
        logD("unBundling: ");
        return getApi().unBundling();
    }

    @Override // com.xiaopeng.aiot.device.childseat.vm.IChildSeatVM
    public void openBluetooth() {
        logD("openBluetooth: ");
        BluetoothHelper.get().open();
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onDataChanged(ChildSeatDevice childSeatDevice) {
        logI("onDataChanged: " + childSeatDevice);
        if (this.mChildSeatDevice == null) {
            this.mChildSeatDevice = new MutableLiveData<>();
        }
        this.mChildSeatDevice.postValue(childSeatDevice);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        logI("onCleared ");
        this.mChildSeatDevice = null;
        getApi().removeOnDataChangeListener(this);
    }
}
