package com.xiaopeng.iotlib.model.api;

import com.xiaopeng.iotlib.data.DeviceInfo;
import com.xiaopeng.iotlib.provider.iot.IotControl;
import com.xiaopeng.iotlib.provider.iot.IotManagers;
import com.xiaopeng.iotlib.provider.iot.device.Base;
import com.xiaopeng.iotlib.provider.iot.device.Device;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public abstract class IotApi<T extends DeviceInfo> extends DeviceApi<T> implements IotControl.IDeviceListenerListener {
    private Device mDevice;
    private String[] mEventKeys;
    private final String[] mKeys;
    private ConcurrentHashMap<String, String> mRawData;
    private final String mType;

    protected abstract T createData();

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl.IDeviceListenerListener
    public void onDeviceAdd(List<Device> list) {
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl.IDeviceListenerListener
    public void onOperationResult(String str, String str2, String str3) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public IotApi(String str, String[] strArr) {
        this(str, strArr, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public IotApi(String str, String[] strArr, String[] strArr2) {
        this.mType = str;
        this.mKeys = strArr;
        this.mEventKeys = strArr2;
        this.mRawData = new ConcurrentHashMap<>();
    }

    private IotControl getIotControl() {
        return IotManagers.getIotControl();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized Device getIotDevice() {
        if (this.mDevice == null) {
            this.mDevice = createDevice();
        }
        return this.mDevice;
    }

    protected synchronized void clearDevice() {
        this.mDevice = null;
    }

    private synchronized Device createDevice() {
        Device device;
        device = null;
        List<Device> device2 = getIotControl().getDevice(this.mType);
        if (device2 != null && device2.size() > 0) {
            device = device2.get(0);
        }
        String logTag = logTag();
        LogUtils.d(logTag, " createDevice isListenerSignal: " + isListenerSignal());
        if (device != null && isListenerSignal()) {
            getIotControl().subscribeNotifications(device);
        }
        return device;
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi
    public T loadData() {
        LogUtils.d(logTag(), " loadData ");
        Map<String, String> deviceProperties = getIotControl().getDeviceProperties(getIotDevice());
        if (deviceProperties != null) {
            for (String str : this.mKeys) {
                dataConversion(deviceProperties, str);
            }
        }
        return createData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean setDeviceProperties(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, str2);
        return getIotControl().setDeviceProperties(getIotDevice(), hashMap);
    }

    protected boolean sendCommand(String str, String str2) {
        return getIotControl().sendCommand(getIotDevice(), str, str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getRawValue(String str) {
        if (str == null) {
            return null;
        }
        return this.mRawData.get(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public void startListenerSignal() {
        LogUtils.d(logTag(), " startListenerSignal ");
        getIotControl().addListener(this);
        startDeviceMonitor();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public void stopListenerSignal() {
        LogUtils.d(logTag(), " stopListenerSignal ");
        getIotControl().removeListener(this);
        stopDeviceMonitor();
    }

    protected void startDeviceMonitor() {
        LogUtils.d(logTag(), " startDeviceMonitor ");
        Device iotDevice = getIotDevice();
        if (iotDevice != null) {
            getIotControl().subscribeNotifications(iotDevice);
        } else {
            LogUtils.d(logTag(), " startDeviceMonitor device is null ");
        }
    }

    protected void stopDeviceMonitor() {
        LogUtils.d(logTag(), " stopDeviceMonitor ");
        Device iotDevice = getIotDevice();
        if (iotDevice != null) {
            getIotControl().unSubscribeNotifications(iotDevice);
        } else {
            LogUtils.d(logTag(), " stopDeviceMonitor device is null ");
        }
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi
    public boolean unBundling() {
        LogUtils.d(logTag(), " unBundling ");
        boolean sendCommand = sendCommand(Base.CMD_REMOVE_DEVICE, this.mType);
        if (sendCommand) {
            stopDeviceMonitor();
            clearData();
            clearDevice();
            notifyChanged(createData());
        }
        return sendCommand;
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl.IDeviceListenerListener
    public void onDeviceBindChanged(String str, String str2) {
        String logTag = logTag();
        LogUtils.i(logTag, "onDeviceBindChanged: " + str + " ,cmd " + str2);
        if (this.mType.equals(str) && Base.CMD_ADD_DEVICE.equals(str2)) {
            notifyChanged(loadData());
            startDeviceMonitor();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl.IDeviceListenerListener
    public void onPropertiesUpdated(String str, Map<String, String> map) {
        Device device = this.mDevice;
        String deviceId = device != null ? device.getDeviceId() : null;
        LogUtils.d(logTag(), " onPropertiesUpdated deviceId:" + str + " ,map:" + map + " ,curDeviceId ; " + deviceId);
        if (map == null || str == null || !str.equals(deviceId)) {
            return;
        }
        String[] strArr = this.mEventKeys;
        if (strArr != null) {
            for (String str2 : strArr) {
                String str3 = map.get(str2);
                if (str2 != null && str3 != null) {
                    notifyEvent(str2, str3);
                }
            }
        }
        boolean z = false;
        for (String str4 : this.mKeys) {
            if (dataConversion(map, str4)) {
                z = true;
            }
        }
        if (z) {
            notifyChanged(createData());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setRawData(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        this.mRawData.put(str, str2);
    }

    private boolean dataConversion(Map<String, String> map, String str) {
        String str2 = map.get(str);
        if (str == null || str2 == null) {
            return false;
        }
        this.mRawData.put(str, str2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String rawDataInfo() {
        return this.mRawData.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String rawDataInfo(String str) {
        HashMap hashMap = new HashMap(this.mRawData);
        hashMap.remove(str);
        return hashMap.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void clearData() {
        this.mRawData.clear();
    }
}
