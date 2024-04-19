package com.xiaopeng.aiot.device.fridge.api;

import android.text.TextUtils;
import com.xiaopeng.aiot.device.fridge.data.FridgeDevice;
import com.xiaopeng.iotlib.model.api.IotApi;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.iot.device.Base;
import com.xiaopeng.iotlib.provider.iot.device.Fridge;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.iotlib.utils.Utils;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class FridgeApiXuiImpl extends IotApi<FridgeDevice> implements FridgeApi {
    private static final int[] sErrors = {1, 2, 4, 8, 16, 32, 64};
    private Runnable mLoadRunnable;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public String logTag() {
        return LogConfig.TAG_DEVICE_API_FRIDGE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FridgeApiXuiImpl() {
        super(Fridge.DEVICE_TYPE, new String[]{Base.PROP_CONNECT_STATE, Fridge.PROP_TARGET_TEMPERATURE, "temperature", Fridge.PROP_ERROR_CODE});
        this.mLoadRunnable = new Runnable() { // from class: com.xiaopeng.aiot.device.fridge.api.-$$Lambda$FridgeApiXuiImpl$oJAP0U7DrtzCz3dXJIqj2w7fZdo
            @Override // java.lang.Runnable
            public final void run() {
                FridgeApiXuiImpl.this.lambda$new$0$FridgeApiXuiImpl();
            }
        };
    }

    @Override // com.xiaopeng.aiot.device.fridge.api.FridgeApi
    public void setTemperature(int i) {
        String str;
        String logTag = logTag();
        LogUtils.d(logTag, " setTemperature " + i);
        switch (i) {
            case 100:
                str = Fridge.VAL_TEMPERATURE_TARGET_LOW;
                break;
            case 101:
                str = Fridge.VAL_TEMPERATURE_TARGET_MIDDLE;
                break;
            case 102:
                str = Fridge.VAL_TEMPERATURE_TARGET_HIGH;
                break;
            default:
                str = null;
                break;
        }
        if (str != null) {
            setDeviceProperties(Fridge.PROP_TARGET_TEMPERATURE, str);
        }
    }

    public /* synthetic */ void lambda$new$0$FridgeApiXuiImpl() {
        notifyChanged(loadData());
    }

    @Override // com.xiaopeng.aiot.device.fridge.api.FridgeApi
    public void powerState(boolean z) {
        String logTag = logTag();
        LogUtils.i(logTag, " powerState " + z);
        if (z) {
            if (ApiConfig.API_DEBUG) {
                ThreadUtils.UI.postDelay(this.mLoadRunnable, 3000L);
                return;
            }
            return;
        }
        if (ApiConfig.API_DEBUG) {
            ThreadUtils.UI.removeCallbacks(this.mLoadRunnable);
        }
        clearData();
        notifyChanged(createData());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IotApi
    public synchronized FridgeDevice createData() {
        String logTag = logTag();
        LogUtils.d(logTag, "======createFridge " + rawDataInfo() + "---" + Thread.currentThread());
        FridgeDevice fridgeDevice = new FridgeDevice();
        if (getIotDevice() == null) {
            fridgeDevice.setHasBinding(false);
            return fridgeDevice;
        }
        fridgeDevice.setHasBinding(true);
        String rawValue = getRawValue(Base.PROP_CONNECT_STATE);
        if (rawValue != null) {
            if ("100".equals(rawValue)) {
                fridgeDevice.setConnect(100);
            } else {
                fridgeDevice.setConnect(101);
            }
        } else {
            fridgeDevice.setConnect(-1);
        }
        String rawValue2 = getRawValue(Fridge.PROP_TARGET_TEMPERATURE);
        if (Fridge.VAL_TEMPERATURE_TARGET_HIGH.equals(rawValue2)) {
            fridgeDevice.setTemperatureSet(102);
        } else if (Fridge.VAL_TEMPERATURE_TARGET_MIDDLE.equals(rawValue2)) {
            fridgeDevice.setTemperatureSet(101);
        } else if (Fridge.VAL_TEMPERATURE_TARGET_LOW.equals(rawValue2)) {
            fridgeDevice.setTemperatureSet(100);
        } else {
            fridgeDevice.setTemperatureSet(-1);
        }
        String rawValue3 = getRawValue("temperature");
        if (Base.VAL_INVALID_STR.equals(rawValue3) || TextUtils.isEmpty(rawValue3)) {
            fridgeDevice.setTemperature(null);
        } else {
            try {
                int parseInt = Integer.parseInt(rawValue3);
                if (parseInt == -99) {
                    fridgeDevice.setTemperature(null);
                } else {
                    fridgeDevice.setTemperature(String.valueOf(parseInt));
                }
            } catch (Exception e) {
                e.printStackTrace();
                fridgeDevice.setTemperature(null);
            }
        }
        String rawValue4 = getRawValue(Fridge.PROP_ERROR_CODE);
        int parse16 = Utils.parse16(rawValue4);
        StringBuilder sb = new StringBuilder();
        if (parse16 != -1) {
            for (int i = 0; i < sErrors.length; i++) {
                if ((sErrors[i] & parse16) == sErrors[i]) {
                    sb.append(i + 1);
                    sb.append(",");
                }
            }
        }
        fridgeDevice.setErrorCode(sb.toString());
        String logTag2 = logTag();
        LogUtils.d(logTag2, "value " + rawValue4 + " ,errorInt " + parse16 + " , error " + fridgeDevice.getErrorCode());
        return fridgeDevice;
    }
}
