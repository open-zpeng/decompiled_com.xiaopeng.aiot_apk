package com.xiaopeng.aiot.model.buriedpoint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes.dex */
public interface IFragranceDL {
    public static final int DENSITY_HIGH = 2;
    public static final int DENSITY_LOW = 1;
    public static final int SOURCE_FRAGRANCE_AIR = 2;
    public static final int SOURCE_FRAGRANCE_CAR_LIFE = 1;
    public static final int SOURCE_FRAGRANCE_INSERT = 3;
    public static final int SOURCE_FRAGRANCE_SPEECH = 4;
    public static final int SOURCE_FRAGRANCE_UNWANTED = 0;
    public static final int SOURCE_OPEN_GUI = 1;
    public static final int SOURCE_OPEN_SLEEPSPACE = 4;
    public static final int SOURCE_OPEN_SPEECH = 2;
    public static final int SOURCE_OPEN_SYSTEM = 3;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Density {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Source {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface SourceOpen {
    }

    void close(int i, int i2);

    void density(int i);

    void greatMaster(int i);

    void into(int i);

    void more(int i);

    void open(int i, int i2);
}
