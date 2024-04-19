package com.xiaopeng.iotlib.model.sp;
/* loaded from: classes.dex */
public interface IWatchesSave extends ISave {
    public static final String KEY_MARK = "mark_enable";

    boolean isMarkEnable();

    boolean saveMarkEnable(boolean z);
}
