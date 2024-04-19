package com.xiaopeng.iotlib.provider.power;

import android.content.Intent;
import android.util.ArraySet;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.provider.car.CarManager;
import com.xiaopeng.iotlib.provider.car.ICarMcuControl;
import com.xiaopeng.iotlib.provider.receiver.IotReceiver;
import com.xiaopeng.iotlib.provider.router.RouterHelper;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.xui.app.XToast;
import java.util.Iterator;
import java.util.Random;
/* loaded from: classes.dex */
public class PowerCenterManager implements ICarMcuControl.Callback {
    private static final boolean DEBUG = ApiConfig.POWER_DEBUG;
    private static final String OPEN_TIME_SET_DIALOG_ACTON = "android.intent.action.showTrunkPowerDelayOffTimeDialog";
    private static final String POWER_CENTER_PACKAGE = "com.xiaopeng.chargecontrol";
    private ArraySet<Callback> mCallbacks;
    private final ICarMcuControl mcu;

    /* loaded from: classes.dex */
    public interface Callback {
        void onPowerCenterSwitchChanged();

        void onPowerCenterTimeChanged();
    }

    private PowerCenterManager() {
        this.mCallbacks = new ArraySet<>();
        this.mcu = CarManager.get().getCarMcuControl();
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarMcuControl.Callback
    public void onMcuTimeChanged() {
        Iterator<Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onPowerCenterTimeChanged();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarMcuControl.Callback
    public void onMcuSwitchChanged() {
        Iterator<Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onPowerCenterSwitchChanged();
        }
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final PowerCenterManager INSTANCE = new PowerCenterManager();

        private SingletonHolder() {
        }
    }

    public static PowerCenterManager get() {
        return SingletonHolder.INSTANCE;
    }

    public String getTimeSet() {
        String trunkPowerDelayOffTime = RouterHelper.getTrunkPowerDelayOffTime();
        LogUtils.i("power", "getTimeSet  time : " + trunkPowerDelayOffTime);
        return trunkPowerDelayOffTime;
    }

    public boolean getPowerSwitch() {
        boolean trunkPowerStatus = this.mcu.getTrunkPowerStatus();
        LogUtils.i("power", "getPowerSwitch  state : " + trunkPowerStatus);
        return trunkPowerStatus;
    }

    public boolean setFridgeSwitch(boolean z) {
        if (DEBUG) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int nextInt = new Random().nextInt(6);
            if (z && nextInt == 2) {
                ThreadUtils.UI.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.power.-$$Lambda$PowerCenterManager$XzS1g_gueTqNxogo6fcan-VLICY
                    @Override // java.lang.Runnable
                    public final void run() {
                        XToast.show("供电故障，无法使用（随机模拟故障）");
                    }
                });
                return false;
            }
            this.mcu.setTrunkPowerSw(z);
            return z;
        }
        boolean fridgeSwitchStatus = RouterHelper.setFridgeSwitchStatus(z);
        LogUtils.i("power", "setFridgeSwitch  state : " + fridgeSwitchStatus);
        return fridgeSwitchStatus;
    }

    public void openTimeSetDialog() {
        Intent intent = new Intent(OPEN_TIME_SET_DIALOG_ACTON);
        intent.putExtra(IotReceiver.KEY_FROM, " fridge");
        intent.addFlags(16777216);
        Iot.getApp().sendBroadcast(intent);
    }

    public synchronized void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
        this.mcu.addCallback(this);
    }

    public synchronized void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
        if (this.mCallbacks.size() == 0) {
            this.mcu.removeCallback(this);
        }
    }
}
