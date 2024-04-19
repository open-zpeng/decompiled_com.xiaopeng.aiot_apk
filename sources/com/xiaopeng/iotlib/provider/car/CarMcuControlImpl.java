package com.xiaopeng.iotlib.provider.car;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.mcu.CarMcuManager;
import android.util.ArraySet;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.provider.car.ICarMcuControl;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class CarMcuControlImpl implements ICarMcuControl {
    private static final boolean DEBUG = ApiConfig.POWER_DEBUG;
    private static final String TAG = "mcu";
    private CarMcuManager.CarMcuEventCallback mCarMcuEventCallback;
    private CarMcuManager mCarMcuManager;
    private Collection<Integer> mPropertyIds;
    private ArraySet<ICarMcuControl.Callback> mCallbacks = new ArraySet<>();
    private final ConcurrentHashMap<Integer, Object> mCache = new ConcurrentHashMap<>();

    @Override // com.xiaopeng.iotlib.provider.car.ICarEcuControl
    public void init(Car car) {
        LogUtils.i(TAG, "init: ");
        try {
            this.mCarMcuManager = (CarMcuManager) car.getCarManager("xp_mcu");
        } catch (Exception e) {
            LogUtils.e(TAG, "getCarManager error");
            e.printStackTrace();
        }
        this.mCarMcuEventCallback = new CarMcuManager.CarMcuEventCallback() { // from class: com.xiaopeng.iotlib.provider.car.CarMcuControlImpl.1
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue == null) {
                    return;
                }
                if (557847655 == carPropertyValue.getPropertyId()) {
                    LogUtils.i(CarMcuControlImpl.TAG, "onChangeEvent: " + carPropertyValue.toString());
                    if (CarMcuControlImpl.DEBUG) {
                        return;
                    }
                    CarMcuControlImpl.this.onChangeEvent_ID_MCU_REQ_TRKP_MODE(carPropertyValue.getValue());
                } else if (557847656 == carPropertyValue.getPropertyId()) {
                    LogUtils.i(CarMcuControlImpl.TAG, "onChangeEvent: " + carPropertyValue.toString());
                    CarMcuControlImpl.this.onChangeEvent_ID_MCU_REQ_TRKP_TIMEON(carPropertyValue.getValue());
                }
            }

            public void onErrorEvent(int i, int i2) {
                LogUtils.e(CarMcuControlImpl.TAG, "onErrorEvent i: " + i + " ,i1: " + i2);
            }
        };
        if (this.mPropertyIds != null) {
            callBack();
        }
        if (this.mCallbacks.size() > 0) {
            this.mPropertyIds = null;
            callBack();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChangeEvent_ID_MCU_REQ_TRKP_MODE(final Object obj) {
        ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarMcuControlImpl$9UBLHoAU8h7UlBciPptjgcYlp74
            @Override // java.lang.Runnable
            public final void run() {
                CarMcuControlImpl.this.lambda$onChangeEvent_ID_MCU_REQ_TRKP_MODE$0$CarMcuControlImpl(obj);
            }
        });
    }

    public /* synthetic */ void lambda$onChangeEvent_ID_MCU_REQ_TRKP_MODE$0$CarMcuControlImpl(Object obj) {
        if (obj instanceof Integer) {
            int intValue = ((Integer) obj).intValue();
            ConcurrentHashMap<Integer, Object> concurrentHashMap = this.mCache;
            boolean z = true;
            if (intValue != 1 && intValue != 2) {
                z = false;
            }
            concurrentHashMap.put(557847655, Boolean.valueOf(z));
        }
        Iterator<ICarMcuControl.Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onMcuSwitchChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChangeEvent_ID_MCU_REQ_TRKP_TIMEON(final Object obj) {
        ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarMcuControlImpl$D43ptLQlphy9g-UEDgoLv9nXmak
            @Override // java.lang.Runnable
            public final void run() {
                CarMcuControlImpl.this.lambda$onChangeEvent_ID_MCU_REQ_TRKP_TIMEON$1$CarMcuControlImpl(obj);
            }
        });
    }

    public /* synthetic */ void lambda$onChangeEvent_ID_MCU_REQ_TRKP_TIMEON$1$CarMcuControlImpl(Object obj) {
        this.mCache.put(557847656, obj);
        Iterator<ICarMcuControl.Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onMcuTimeChanged();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarEcuControl
    public void release() {
        unCallback();
    }

    private synchronized void unCallback() {
        if (this.mCarMcuManager == null) {
            LogUtils.e(TAG, "unCallback  mCarMcuManager is null");
            return;
        }
        if (this.mCarMcuEventCallback != null && this.mPropertyIds != null) {
            this.mCarMcuManager.unregisterPropCallback(this.mPropertyIds, this.mCarMcuEventCallback);
            this.mPropertyIds = null;
            LogUtils.i(TAG, "unregisterPropCallback  over");
        }
    }

    private synchronized void callBack() {
        if (this.mCarMcuManager == null) {
            LogUtils.e(TAG, "callBack  mCarMcuManager is null");
        } else if (this.mPropertyIds == null) {
            this.mPropertyIds = new ArrayList();
            this.mPropertyIds.add(557847655);
            this.mPropertyIds.add(557847656);
            try {
                this.mCarMcuManager.registerPropCallback(this.mPropertyIds, this.mCarMcuEventCallback);
                LogUtils.i(TAG, "registerPropCallback  over");
            } catch (CarNotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarMcuControl
    public void addCallback(ICarMcuControl.Callback callback) {
        this.mCallbacks.add(callback);
        callBack();
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarMcuControl
    public void removeCallback(ICarMcuControl.Callback callback) {
        this.mCallbacks.remove(callback);
        if (this.mCallbacks.size() == 0) {
            unCallback();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarMcuControl
    public boolean getTrunkPowerStatus() {
        if (this.mCarMcuManager == null) {
            LogUtils.e(TAG, "setTrunkPowerSw mCarMcuManager is null ");
            return false;
        }
        try {
            if (DEBUG) {
                Object obj = this.mCache.get(557847655);
                if (obj instanceof Boolean) {
                    LogUtils.i(TAG, "getTrunkPowerStatus  debug state : " + obj);
                    return ((Boolean) obj).booleanValue();
                }
            }
            int trunkPowerStatus = this.mCarMcuManager.getTrunkPowerStatus();
            LogUtils.i(TAG, "getTrunkPowerStatus  state : " + trunkPowerStatus);
            return trunkPowerStatus == 1 || trunkPowerStatus == 2;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarMcuControl
    public void setTrunkPowerSw(boolean z) {
        if (this.mCarMcuManager == null) {
            LogUtils.e(TAG, "setTrunkPowerSw mCarMcuManager is null ");
            return;
        }
        int i = z ? 1 : 0;
        try {
            if (DEBUG) {
                onChangeEvent_ID_MCU_REQ_TRKP_MODE(Integer.valueOf(i));
            } else {
                this.mCarMcuManager.setTrunkPowerSw(i);
            }
            LogUtils.i(TAG, "setTrunkPowerSw  state : " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
