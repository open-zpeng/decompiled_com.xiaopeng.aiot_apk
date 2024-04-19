package com.xiaopeng.iotlib.model.sp;
/* loaded from: classes.dex */
public interface IFreezerModeSave extends ISave {
    public static final String KEY_FREEZER_MODE = "freezer_mode";

    int getWorkModeValue();

    boolean savedWorkModeVal(int i);
}
