package com.xiaopeng.aiot.device.freezer.api;

import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.xiaopeng.aiot.device.freezer.api.FreezerApi;
import com.xiaopeng.aiot.device.freezer.data.FreezerDevice;
import com.xiaopeng.iotlib.model.api.IotApi;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.model.sp.IFreezerModeSave;
import com.xiaopeng.iotlib.model.sp.IFreezerTemperSave;
import com.xiaopeng.iotlib.model.sp.IFreezerTimeSave;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.provider.iot.device.Base;
import com.xiaopeng.iotlib.provider.iot.device.Freezer;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import java.util.Iterator;
/* loaded from: classes.dex */
public class FreezerApiXuiImpl extends IotApi<FreezerDevice> implements FreezerApi, FreezerApi.Callback {
    private static final String TAG = "FreezerApi";
    private ArraySet<Callback> mCallbacks;
    private Runnable mLoadRunnable;

    /* loaded from: classes.dex */
    public interface Callback {
        void onPowerSwitchChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public String logTag() {
        return "FreezerApi";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FreezerApiXuiImpl() {
        super(Freezer.DEVICE_TYPE, new String[]{Freezer.PROP_WORK_MODE, "temperature"});
        this.mLoadRunnable = new Runnable() { // from class: com.xiaopeng.aiot.device.freezer.api.-$$Lambda$FreezerApiXuiImpl$umorLsiJm4Qb6ws5GZmURrIExqo
            @Override // java.lang.Runnable
            public final void run() {
                FreezerApiXuiImpl.this.lambda$new$0$FreezerApiXuiImpl();
            }
        };
        this.mCallbacks = new ArraySet<>();
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void addATemper() {
        LogUtils.d(logTag(), " add a degree ");
        setDeviceProperties("temperature", Freezer.VAL_INC);
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void reduceATemper() {
        LogUtils.d(logTag(), " reduce a degree ");
        setDeviceProperties("temperature", Freezer.VAL_DEC);
    }

    public /* synthetic */ void lambda$new$0$FreezerApiXuiImpl() {
        notifyChanged(loadData());
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
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

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void setLockHold(int i) {
        String logTag = logTag();
        LogUtils.d(logTag, " setLockHold: " + i);
        if (i == 0) {
            setDeviceProperties(Freezer.PROP_HEAT_PRESERVATION, Freezer.VAL_OFF);
        } else {
            setDeviceProperties(Freezer.PROP_HEAT_PRESERVATION, Freezer.VAL_ON);
        }
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void setChildLock(int i) {
        String logTag = logTag();
        LogUtils.d(logTag, " setChildLock: " + i);
        if (i == 1) {
            setDeviceProperties(Freezer.PROP_CHILD_LOCK, Freezer.VAL_ON);
        } else {
            setDeviceProperties(Freezer.PROP_CHILD_LOCK, Freezer.VAL_OFF);
        }
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public boolean isChildLock() {
        if (Freezer.VAL_OFF.equals(getRawValue(Freezer.PROP_CHILD_LOCK))) {
            LogUtils.d(logTag(), "childLock close ");
            return false;
        }
        LogUtils.d(logTag(), "childLock open ");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IotApi
    public synchronized FreezerDevice createData() {
        FreezerDevice freezerDevice;
        String logTag = logTag();
        LogUtils.d(logTag, "======createFreezer " + rawDataInfo() + "---" + Thread.currentThread());
        freezerDevice = new FreezerDevice();
        String rawValue = getRawValue(Freezer.PROP_WORK_MODE);
        if (Freezer.VAL_COLD.equals(rawValue)) {
            freezerDevice.setWorkMode(1);
        } else if (Freezer.VAL_HEAT.equals(rawValue)) {
            freezerDevice.setWorkMode(2);
        } else {
            freezerDevice.setWorkMode(0);
        }
        String rawValue2 = getRawValue("temperature");
        if (Base.VAL_INVALID_STR.equals(rawValue2) || TextUtils.isEmpty(rawValue2)) {
            freezerDevice.setTemperature(null);
        } else {
            try {
                int parseInt = Integer.parseInt(rawValue2);
                if (parseInt >= 0 && parseInt <= 50 && (parseInt >= 30 || parseInt <= 20)) {
                    Log.d("FreezerApi", "setTemper successful: " + rawValue2);
                    freezerDevice.setTemperature(rawValue2);
                }
                Log.d("FreezerApi", "temperature is invalid: " + parseInt);
                freezerDevice.setTemperature(null);
            } catch (Exception e) {
                e.printStackTrace();
                freezerDevice.setTemperature(null);
            }
        }
        String rawValue3 = getRawValue(Freezer.PROP_HOLDING_TIME);
        if (TextUtils.isEmpty(rawValue3)) {
            freezerDevice.setHoldingTime(0);
        } else {
            int parseInt2 = Integer.parseInt(rawValue3);
            if (parseInt2 >= 0 && parseInt2 <= 24) {
                Log.d("FreezerApi", "holdTimeV: " + parseInt2);
                freezerDevice.setHoldingTime(parseInt2);
            } else {
                freezerDevice.setHoldingTime(0);
            }
        }
        return freezerDevice;
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void setWorkMode(int i) {
        String logTag = logTag();
        LogUtils.d(logTag, "api setWorkMode " + i);
        setWorkMode(i, true);
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void setHoldTime(int i) {
        String logTag = logTag();
        LogUtils.d(logTag, "api setHoldTime " + i);
        setHoldTime(i, true);
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void setPowerOC(int i) {
        String logTag = logTag();
        LogUtils.d(logTag, "api setPowerOC " + i);
        String str = i == 1 ? Freezer.VAL_ON : i == 0 ? Freezer.VAL_OFF : null;
        if (str != null) {
            setDeviceProperties(Freezer.PROP_POWER_CTRL, str);
        }
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void setDoorOpen(int i) {
        String logTag = logTag();
        LogUtils.d(logTag, "api setDoorOpen " + i);
        if (i == 1) {
            setDeviceProperties(Freezer.PROP_DOOR_CTRL, Freezer.VAL_ON);
        }
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public void setTemper(int i) {
        String logTag = logTag();
        LogUtils.d(logTag, "api setTemper " + i);
        setTemper(i, true);
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public boolean isDoorOpen() {
        if (Freezer.VAL_OFF.equals(getRawValue(Freezer.PROP_DOOR_CTRL))) {
            LogUtils.d(logTag(), "door close ");
            return false;
        }
        LogUtils.d(logTag(), "door open ");
        return true;
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public boolean isPowerOpen() {
        if (Freezer.VAL_ON.equals(getRawValue(Freezer.PROP_POWER_CTRL))) {
            LogUtils.d(logTag(), "power on ");
            return true;
        }
        LogUtils.d(logTag(), "power off ");
        return false;
    }

    public void setTemper(final int i, boolean z) {
        boolean z2;
        LogUtils.d("FreezerApi", "setTemper" + i);
        if ((i < 0 || i > 20) && (i < 30 || i > 50)) {
            z2 = false;
        } else {
            z2 = true;
            setDeviceProperties("temperature", String.valueOf(i));
        }
        if (z2) {
            if (z) {
                ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.aiot.device.freezer.api.-$$Lambda$FreezerApiXuiImpl$KOGhdzXXJ_byqa1SFnx6ircBJNw
                    @Override // java.lang.Runnable
                    public final void run() {
                        FreezerApiXuiImpl.lambda$setTemper$1(i);
                    }
                });
                return;
            }
            LogUtils.e("FreezerApi", "setTemper error" + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setTemper$1(int i) {
        IFreezerTemperSave freezerTemperSave = SaveManager.get().getFreezerTemperSave();
        if (freezerTemperSave != null) {
            freezerTemperSave.savedTemperVal(i);
        }
    }

    public void setHoldTime(final int i, boolean z) {
        LogUtils.d("FreezerApi", "setHoldTime" + i);
        boolean z2 = true;
        if (i < 1 || i > 24) {
            z2 = false;
        } else {
            setDeviceProperties(Freezer.PROP_HOLDING_TIME, String.valueOf(i));
        }
        if (z2) {
            if (z) {
                ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.aiot.device.freezer.api.-$$Lambda$FreezerApiXuiImpl$TFF_wvNqbxU7zM64VULcSOSDWV0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FreezerApiXuiImpl.lambda$setHoldTime$2(i);
                    }
                });
                return;
            }
            LogUtils.e("FreezerApi", "setHoldTime error" + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setHoldTime$2(int i) {
        IFreezerTimeSave freezerTimeSave = SaveManager.get().getFreezerTimeSave();
        if (freezerTimeSave != null) {
            freezerTimeSave.savedTimeValue(i);
        }
    }

    public void setWorkMode(final int i, boolean z) {
        LogUtils.d(logTag(), " setWorkMode " + i);
        boolean z2 = true;
        String str = i == 1 ? Freezer.VAL_COLD : i == 2 ? Freezer.VAL_HEAT : i == 0 ? Freezer.VAL_OFF : null;
        if (str != null) {
            setDeviceProperties(Freezer.PROP_WORK_MODE, str);
        } else {
            z2 = false;
        }
        if (z2) {
            if (z) {
                ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.aiot.device.freezer.api.-$$Lambda$FreezerApiXuiImpl$rTAWJcfkh2qLUIwR-jYXGY2N97k
                    @Override // java.lang.Runnable
                    public final void run() {
                        FreezerApiXuiImpl.lambda$setWorkMode$3(i);
                    }
                });
                return;
            }
            LogUtils.e("FreezerApi", "setWorkMode error" + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setWorkMode$3(int i) {
        IFreezerModeSave freezerModeSave = SaveManager.get().getFreezerModeSave();
        if (freezerModeSave != null) {
            freezerModeSave.savedWorkModeVal(i);
        }
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public synchronized void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi
    public synchronized void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    @Override // com.xiaopeng.aiot.device.freezer.api.FreezerApi.Callback
    public void onMPowerChanged() {
        Iterator<Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onPowerSwitchChanged();
        }
    }
}
