package com.xiaopeng.iotlib.provider.iot.device;
/* loaded from: classes.dex */
public abstract class Base {
    public static final String CMD_ADD_DEVICE = "cmd_add_device";
    public static final String CMD_ONOFF = "cmd_base_onoff";
    public static final String CMD_REMOVE_DEVICE = "cmd_remove_device";
    public static final String CMD_SCAN_DEVICE_START = "cmd_scan_device_start";
    public static final String CMD_SCAN_DEVICE_STOP = "cmd_scan_device_stop";
    public static final String GET_BY_DEVICE_CLASS = "by_dev_class";
    public static final String GET_BY_DEVICE_TYPE = "by_dev_type";
    public static final String PROP_CONNECT_STATE = "connect_state";
    public static final String PROP_SWITCH_STATE = "switch_state";
    public static final String SCAN_TYPE_BLE = "scan_type_ble";
    public static final String VAL_CONNECTED = "1";
    public static final String VAL_DISCONNECTED = "0";
    public static final int VAL_INVALID_INT = Integer.MIN_VALUE;
    public static final String VAL_INVALID_STR = "-2147483648";
    public static final String VAL_OFF = "0";
    public static final String VAL_ON = "1";
    public static final String VAL_SERVICE_AVAILABLE = "100";
    public static final String VAL_UNINIT = "-1";
    public static final int VAL_UNINIT_INT = -1;
    public static final String VAL_UNKNOWN_DEVICE_ID = "unknown_device_id";
    public static final String VAL_UNKNOWN_DEVICE_NAME = "unknown_device_name";
}
