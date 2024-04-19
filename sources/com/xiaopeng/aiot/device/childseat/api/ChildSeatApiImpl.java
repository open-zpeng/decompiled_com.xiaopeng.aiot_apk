package com.xiaopeng.aiot.device.childseat.api;

import com.xiaopeng.aiot.device.childseat.data.ChildSeatDevice;
import com.xiaopeng.iotlib.model.api.IotApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.blue.BluetoothCallBack;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.provider.iot.device.Base;
import com.xiaopeng.iotlib.provider.iot.device.ChildSafetySeat;
import com.xiaopeng.iotlib.utils.LogUtils;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ChildSeatApiImpl extends IotApi<ChildSeatDevice> implements ChildSeatApi, BluetoothCallBack {
    private int mBleState;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public String logTag() {
        return LogConfig.TAG_DEVICE_API_SEAT;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ChildSeatApiImpl() {
        super(ChildSafetySeat.DEVICE_TYPE, new String[]{Base.PROP_CONNECT_STATE, ChildSafetySeat.PROP_ISOFIX_STATUS});
        this.mBleState = -1;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IotApi, com.xiaopeng.iotlib.model.api.IDeviceApi
    public ChildSeatDevice loadData() {
        this.mBleState = BluetoothHelper.get().getState();
        String logTag = logTag();
        LogUtils.d(logTag, " loadData  mBleState " + this.mBleState);
        return (ChildSeatDevice) super.loadData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.IotApi, com.xiaopeng.iotlib.model.api.DeviceApi
    public void startListenerSignal() {
        super.startListenerSignal();
        BluetoothHelper.get().addCallBack(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.IotApi, com.xiaopeng.iotlib.model.api.DeviceApi
    public void stopListenerSignal() {
        super.stopListenerSignal();
        BluetoothHelper.get().removeCallBack(this);
        this.mBleState = -1;
    }

    @Override // com.xiaopeng.iotlib.provider.blue.BluetoothCallBack
    public void onBleStateChanged(int i) {
        String logTag = logTag();
        LogUtils.i(logTag, "onBleStateChanged: " + i);
        this.mBleState = i;
        notifyChanged(createData());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IotApi
    public synchronized ChildSeatDevice createData() {
        String logTag = logTag();
        LogUtils.i(logTag, "======createData " + rawDataInfo() + ", mBleState" + this.mBleState);
        ChildSeatDevice childSeatDevice = new ChildSeatDevice();
        if (getIotDevice() == null) {
            childSeatDevice.setStatus(-1);
            return childSeatDevice;
        }
        if (this.mBleState == -1) {
            this.mBleState = BluetoothHelper.get().getState();
        }
        if (this.mBleState != 10 && this.mBleState != 13) {
            if (this.mBleState == 11) {
                childSeatDevice.setStatus(2);
                return childSeatDevice;
            } else if (!"100".equals(getRawValue(Base.PROP_CONNECT_STATE))) {
                childSeatDevice.setStatus(3);
                return childSeatDevice;
            } else {
                String rawValue = getRawValue(ChildSafetySeat.PROP_ISOFIX_STATUS);
                if ("1".equals(rawValue)) {
                    childSeatDevice.setStatus(5);
                } else if ("0".equals(rawValue)) {
                    childSeatDevice.setStatus(4);
                } else {
                    childSeatDevice.setStatus(6);
                }
                return childSeatDevice;
            }
        }
        childSeatDevice.setStatus(1);
        return childSeatDevice;
    }
}
