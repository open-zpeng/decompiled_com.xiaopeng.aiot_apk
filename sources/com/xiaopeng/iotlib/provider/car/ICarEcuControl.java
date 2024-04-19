package com.xiaopeng.iotlib.provider.car;

import android.car.Car;
/* loaded from: classes.dex */
interface ICarEcuControl {
    void init(Car car);

    void release();

    static ICarHvacControl createHvacManager() {
        return new CarHvacControlImpl();
    }

    static ICarMcuControl createMcuController() {
        return new CarMcuControlImpl();
    }
}
