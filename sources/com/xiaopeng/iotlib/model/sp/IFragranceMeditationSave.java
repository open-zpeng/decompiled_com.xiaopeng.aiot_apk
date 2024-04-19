package com.xiaopeng.iotlib.model.sp;
/* loaded from: classes.dex */
public interface IFragranceMeditationSave extends ISave {
    public static final String KEY_CHANNEL_MEDITATION = "channel_meditation";
    public static final String KEY_CHANNEL_MEDITATION_OUT = "channel_meditation_out";

    int getChannelId();

    boolean saveChannelId(int i);
}
