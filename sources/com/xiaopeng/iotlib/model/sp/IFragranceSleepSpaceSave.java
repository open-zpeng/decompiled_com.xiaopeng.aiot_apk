package com.xiaopeng.iotlib.model.sp;
/* loaded from: classes.dex */
public interface IFragranceSleepSpaceSave extends ISave {
    public static final String KEY_CHANNEL_SLEEP_SPACE = "channel_sleep_space";
    public static final String KEY_CHANNEL_SLEEP_SPACE_OUT = "channel_sleep_space_out";
    public static final String KEY_DENSITY_SLEEP_SPACE = "density_sleep_space";
    public static final String KEY_DENSITY_SLEEP_SPACE_OUT = "density_sleep_space_out";

    int getChannelId();

    int getDensity();

    boolean saveChannelId(int i);

    boolean saveDensity(int i);
}
