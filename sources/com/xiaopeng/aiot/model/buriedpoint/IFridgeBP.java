package com.xiaopeng.aiot.model.buriedpoint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes.dex */
public interface IFridgeBP {
    public static final int SOURCE_CAR_LIFE = 1;
    public static final int SOURCE_SPEECH = 2;
    public static final int TEMP_HIGH = 3;
    public static final int TEMP_LOW = 1;
    public static final int TEMP_MID = 2;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Source {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Temperature {
    }

    void enable(boolean z);

    void into(int i);

    void setTemperature(int i);
}
