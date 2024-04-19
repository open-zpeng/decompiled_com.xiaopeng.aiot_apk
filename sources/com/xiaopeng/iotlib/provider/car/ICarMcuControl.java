package com.xiaopeng.iotlib.provider.car;
/* loaded from: classes.dex */
public interface ICarMcuControl extends ICarEcuControl {

    /* loaded from: classes.dex */
    public interface Callback {
        void onMcuSwitchChanged();

        void onMcuTimeChanged();
    }

    void addCallback(Callback callback);

    boolean getTrunkPowerStatus();

    void removeCallback(Callback callback);

    void setTrunkPowerSw(boolean z);
}
