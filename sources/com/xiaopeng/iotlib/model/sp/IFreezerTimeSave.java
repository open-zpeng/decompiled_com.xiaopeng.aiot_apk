package com.xiaopeng.iotlib.model.sp;
/* loaded from: classes.dex */
public interface IFreezerTimeSave extends ISave {
    public static final String KEY_FREEZER_TIME = "freezer_time";

    int getTimeValue();

    boolean savedTimeValue(int i);
}
