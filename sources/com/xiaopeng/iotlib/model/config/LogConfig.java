package com.xiaopeng.iotlib.model.config;

import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.Utils;
/* loaded from: classes.dex */
public class LogConfig {
    public static final String TAG_ACTIVITY = "ui-act";
    public static final String TAG_APPVIEW = "ui-appview";
    public static final String TAG_BLUE = "blue";
    public static final String TAG_CAR = "CarManager";
    public static final String TAG_CAR_HVAC = "CarHvac";
    public static final String TAG_DEVICE_API_BED = "AirBedApi";
    public static final String TAG_DEVICE_API_BLUE = "BlueApi";
    public static final String TAG_DEVICE_API_FRAGRANCE = "FragranceApi";
    public static final String TAG_DEVICE_API_FREEZER = "FreezerApi";
    public static final String TAG_DEVICE_API_FRIDGE = "FridgeApi";
    public static final String TAG_DEVICE_API_SEAT = "SeatApi";
    public static final String TAG_DEVICE_API_WATCHES = "WatchesApi";
    public static final String TAG_DIRECT = "Direct";
    public static final String TAG_DIRECT_HELPER = "DirectHelper";
    public static final String TAG_DIRECT_MANAGER = "DirectManager";
    public static final String TAG_FRAGMENT = "ui-fragment";
    public static final String TAG_IOT = "IotManager";
    public static final String TAG_MODE = "mode";
    public static final String TAG_PAGE = "ui-page";
    public static final String TAG_PAGE_CONFIG = "pageConfig";
    public static final String TAG_SAVE = "save";
    public static final String TAG_SERVICE = "service";
    public static final String TAG_SPEECH = "speech";
    public static final String TAG_TEST = "test";
    public static final String TAG_VM = "model";
    public static final String TAG_VUI_OBSERVER = "vuiObserver";
    public static final String TAG_VUI_SCENE = "vuiScene";
    public static final String TAG_XUI = "XuiManager";
    public static final String TAG_XUI_IOT = "XuiIot";

    public static void init() {
        if (Utils.isUserRelease()) {
            LogUtils.setLogLevel(4);
        } else {
            LogUtils.setLogLevel(3);
        }
    }
}
