package com.xiaopeng.iotlib.model.sp;
/* loaded from: classes.dex */
public interface IAirbedSave extends ISave {
    public static final String KEY_ATMOSPHERE = "atmosphere";

    int getBedPressure();

    boolean saveBedPressure(int i);
}
