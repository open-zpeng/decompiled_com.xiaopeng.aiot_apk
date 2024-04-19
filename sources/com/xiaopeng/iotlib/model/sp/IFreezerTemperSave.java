package com.xiaopeng.iotlib.model.sp;
/* loaded from: classes.dex */
public interface IFreezerTemperSave extends ISave {
    public static final String KEY_FREEZER_TEMPER = "freezer_temper";

    int getTemperValue();

    boolean savedTemperVal(int i);
}
