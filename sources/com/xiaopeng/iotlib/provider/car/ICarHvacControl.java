package com.xiaopeng.iotlib.provider.car;
/* loaded from: classes.dex */
public interface ICarHvacControl extends ICarEcuControl {
    public static final int SFS_CHANNEL_1 = 0;
    public static final int SFS_CHANNEL_2 = 1;
    public static final int SFS_CHANNEL_3 = 2;
    public static final int SFS_DENSITY_CLOSED = 0;
    public static final int SFS_DENSITY_HIGH = 2;
    public static final int SFS_DENSITY_LOW = 1;
    public static final int SFS_STATUS_OFF = 0;
    public static final int SFS_STATUS_ON = 1;
    public static final int SFS_TYPE_FIRST = 1;
    public static final int SFS_TYPE_LAST = 10;
    public static final int SFS_TYPE_NULL = 0;

    /* loaded from: classes.dex */
    public interface OnDataChangeListener {
        default void onSfsChannelChanged(int i) {
        }

        default void onSfsDensityChanged(int i) {
        }

        default void onSfsSwitchChanged(int i) {
        }

        default void onSfsTypeChanged(int[] iArr) {
        }
    }

    void addOnDataChangeListener(OnDataChangeListener onDataChangeListener);

    int getSfsChannel();

    int getSfsDensity();

    int getSfsSwitchStatus();

    int[] getSfsTypeInChannels();

    void removeOnDataChangeListener(OnDataChangeListener onDataChangeListener);

    boolean setSfsChannel(int i);

    void setSfsDensity(int i);

    void setSfsSwitch(int i);
}
