package com.xiaopeng.aiot.device.blue.api;

import android.util.Log;
import com.xiaopeng.aiot.device.airbed.api.AirBedApi;
import com.xiaopeng.aiot.device.blue.data.ScanDevice;
import com.xiaopeng.aiot.device.blue.data.ScanDevicePoxy;
import com.xiaopeng.aiot.device.childseat.api.ChildSeatApi;
import com.xiaopeng.aiot.device.fridge.api.FridgeApi;
import com.xiaopeng.iotlib.model.api.DeviceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.iot.IotControl;
import com.xiaopeng.iotlib.provider.iot.IotManagers;
import com.xiaopeng.iotlib.provider.iot.device.AirBed;
import com.xiaopeng.iotlib.provider.iot.device.Base;
import com.xiaopeng.iotlib.provider.iot.device.ChildSafetySeat;
import com.xiaopeng.iotlib.provider.iot.device.Device;
import com.xiaopeng.iotlib.provider.iot.device.Fridge;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class BlueApiImpl extends DeviceApi<ScanDevicePoxy> implements BlueApi, IotControl.IDeviceListenerListener {
    private String mFilterName;
    private boolean mStarting;
    private Set<String> mFilterNameSet = new HashSet();
    private final SortByRSSI mSortByRSSI = new SortByRSSI();
    private final CopyOnWriteArrayList<ScanDevice> mScanDevices = new CopyOnWriteArrayList<>();
    private final Set<String> mFilterMacs = Collections.synchronizedSet(new HashSet());

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi
    public ScanDevicePoxy loadData() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public String logTag() {
        return LogConfig.TAG_DEVICE_API_BLUE;
    }

    private static IotControl getIotControl() {
        return IotManagers.getIotControl();
    }

    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    protected void startListenerSignal() {
        getIotControl().addListener(this);
    }

    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    protected void stopListenerSignal() {
        getIotControl().removeListener(this);
    }

    @Override // com.xiaopeng.aiot.device.blue.api.BlueApi
    public void start() {
        if (this.mStarting) {
            stop();
        }
        String logTag = logTag();
        LogUtils.d(logTag, " start " + this.mStarting);
        getIotControl().sendCommand(null, Base.CMD_SCAN_DEVICE_START, Base.SCAN_TYPE_BLE);
        this.mStarting = true;
    }

    @Override // com.xiaopeng.aiot.device.blue.api.BlueApi
    public void stop() {
        LogUtils.d(logTag(), " stop ");
        this.mScanDevices.clear();
        getIotControl().sendCommand(null, Base.CMD_SCAN_DEVICE_STOP, Base.SCAN_TYPE_BLE);
        notifyChanged(ScanDevicePoxy.create(new ArrayList(this.mScanDevices)));
        this.mStarting = false;
    }

    @Override // com.xiaopeng.aiot.device.blue.api.BlueApi
    public boolean bind(ScanDevice scanDevice, String str) {
        String logTag = logTag();
        LogUtils.d(logTag, " bind " + scanDevice + " , deviceType " + str);
        return getIotControl().sendCommand(scanDevice.getDevice(), Base.CMD_ADD_DEVICE, str);
    }

    @Override // com.xiaopeng.aiot.device.blue.api.BlueApi
    public void setFilterMac(Set<String> set) {
        String logTag = logTag();
        LogUtils.i(logTag, " setFilterMac " + set);
        this.mFilterMacs.clear();
        this.mFilterMacs.addAll(set);
    }

    @Override // com.xiaopeng.aiot.device.blue.api.BlueApi
    public void setFilterName(String str) {
        String logTag = logTag();
        LogUtils.i(logTag, " setFilterName " + str);
        if (str != null && !str.equals(this.mFilterName)) {
            this.mScanDevices.clear();
        }
        this.mFilterName = str;
    }

    @Override // com.xiaopeng.aiot.device.blue.api.BlueApi
    public void setMultipleFilterNames(Set<String> set) {
        String logTag = logTag();
        LogUtils.i(logTag, " setMultipleFilterNames " + set);
        if (set != null && !set.equals(this.mFilterNameSet)) {
            this.mScanDevices.clear();
        }
        this.mFilterNameSet.clear();
        this.mFilterNameSet.addAll(set);
    }

    @Override // com.xiaopeng.aiot.device.blue.api.BlueApi
    public boolean binding(String str, String str2) {
        ScanDevice scanDevice;
        String logTag = logTag();
        LogUtils.i(logTag, " binding " + str + " , " + str2);
        Iterator<ScanDevice> it = this.mScanDevices.iterator();
        while (true) {
            if (!it.hasNext()) {
                scanDevice = null;
                break;
            }
            scanDevice = it.next();
            if (scanDevice.getDeviceId().equals(str)) {
                break;
            }
        }
        if (scanDevice != null) {
            bind(scanDevice, str2);
            return true;
        }
        String logTag2 = logTag();
        LogUtils.i(logTag2, " binding error----" + str + " , " + str2);
        return false;
    }

    @Override // com.xiaopeng.aiot.device.blue.api.BlueApi
    public void unbind(String str) {
        char c;
        LogUtils.i(logTag(), " unbind " + str);
        int hashCode = str.hashCode();
        if (hashCode == 108955582) {
            if (str.equals(AirBed.DEVICE_TYPE)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 249514054) {
            if (hashCode == 2112549413 && str.equals(Fridge.DEVICE_TYPE)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(ChildSafetySeat.DEVICE_TYPE)) {
                c = 0;
            }
            c = 65535;
        }
        if (c == 0) {
            ChildSeatApi.getApi().unBundling();
        } else if (c == 1) {
            FridgeApi.getApi().unBundling();
        } else if (c != 2) {
        } else {
            AirBedApi.getApi().unBundling();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl.IDeviceListenerListener
    public void onDeviceAdd(List<Device> list) {
        ScanDevice create;
        if (list == null) {
            LogUtils.i(logTag(), "onDeviceAdd list is null ");
            return;
        }
        if (list.size() < 50) {
            String logTag = logTag();
            LogUtils.d(logTag, "onDeviceAdd list size : " + list.size() + "==" + list);
        } else {
            String logTag2 = logTag();
            LogUtils.d(logTag2, "onDeviceAdd list size : " + list.size());
        }
        boolean z = false;
        for (Device device : list) {
            if (checkDevice(device) && (create = ScanDevice.create(device)) != null && !this.mScanDevices.contains(create)) {
                this.mScanDevices.add(create);
                z = true;
            }
        }
        if (z) {
            this.mScanDevices.sort(this.mSortByRSSI);
            notifyChanged(ScanDevicePoxy.create(new ArrayList(this.mScanDevices)));
        }
    }

    private boolean checkDevice(Device device) {
        if (device == null || device.getDeviceId() == null || device.getDeviceName() == null) {
            return false;
        }
        Set<String> set = this.mFilterNameSet;
        if (set == null || set.contains(device.getDeviceName())) {
            return this.mFilterMacs.size() == 0 || this.mFilterMacs.contains(device.getDeviceId());
        }
        Log.d("mFilterNameSet", "its value: " + this.mFilterNameSet + "deviceName:" + device.getDeviceName());
        return false;
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl.IDeviceListenerListener
    public void onOperationResult(String str, String str2, String str3) {
        LogUtils.d(logTag(), String.format(" onOperationResult deviceId %s ,opCmd %s ,reason %s", str, str2, str3));
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl.IDeviceListenerListener
    public void onPropertiesUpdated(String str, Map<String, String> map) {
        LogUtils.d(logTag(), String.format(" onPropertiesUpdated deviceId %s s", str));
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl.IDeviceListenerListener
    public void onDeviceBindChanged(String str, String str2) {
        LogUtils.d(logTag(), String.format(" onDeviceBindChanged deviceId %s , cmd: %s ", str, str2));
    }
}
