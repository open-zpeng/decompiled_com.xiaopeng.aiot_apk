package com.xiaopeng.iotlib.model.sp;
/* loaded from: classes.dex */
public interface IFragranceSave extends ISave {
    public static final String KEY_CHANNEL = "channel";
    public static final String KEY_DENSITY = "density";

    int getChannelId();

    int getDensity();

    boolean saveChannelId(int i);

    boolean saveDensity(int i);
}
