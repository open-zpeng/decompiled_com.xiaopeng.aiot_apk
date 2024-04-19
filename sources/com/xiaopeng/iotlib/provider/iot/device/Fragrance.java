package com.xiaopeng.iotlib.provider.iot.device;

import com.xiaopeng.xuimanager.iot.devices.FragranceDevice;
/* loaded from: classes.dex */
public class Fragrance extends Base {
    public static final String CMD_SET_CHANNEL = "setActiveChannel";
    public static final String CMD_SET_CONCENTRATION = "setConcentration";
    public static final String CMD_SET_SWITCH = "setSwitch";
    public static final String DEVICE_TYPE = "Fragrance";
    public static final String PROP_ACTIVE_CHANNEL = "active";
    public static final String PROP_CHANNEL_TYPES = "channel_types";
    public static final String PROP_CONCENTRATION = "concentration";
    public static final String PROP_CO_CONCENTRATION = "co_concentration";
    public static final String PROP_SWITCH_STATE = "switch_state";
    public static final String VAL_CHANNEL_1 = "0";
    public static final String VAL_CHANNEL_2 = "1";
    public static final String VAL_CHANNEL_3 = "2";
    public static final String VAL_CONCENTRATION_HIGH = "2";
    public static final String VAL_CONCENTRATION_LOW = "1";
    public static final String VAL_CO_CONCENTRATION_HIGH = "2";
    public static final String VAL_CO_CONCENTRATION_LOW = "1";
    public static final String VAL_CO_CONCENTRATION_NONE = "0";
    public static final String VAL_OFF = "0";
    public static final String VAL_ON = "1";
    public static final int VAL_TYPE_COUNT = 15;
    public static final int VAL_TYPE_FIRST = 1;
    public static final int VAL_TYPE_LAST = 15;
    public static final int VAL_TYPE_UNINIT = -1;
    public static final int VAL_TYPE_UNPLUG = 0;
    public static final String VAL_UNINIT = "-1";

    public static int[] readChannelTypes(String str) {
        return FragranceDevice.readChannelTypes(str);
    }
}
