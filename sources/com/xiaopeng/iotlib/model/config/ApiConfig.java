package com.xiaopeng.iotlib.model.config;

import android.content.Context;
import android.provider.Settings;
import com.xiaopeng.iotlib.utils.Utils;
/* loaded from: classes.dex */
public class ApiConfig {
    private static final int ACCOUNT = 8;
    public static boolean ACCOUNT_DEBUG = false;
    private static final int API = 1;
    public static boolean API_DEBUG = false;
    private static final boolean DEBUG = false;
    private static final int OLD = 2;
    private static final int POWER = 4;
    public static boolean POWER_DEBUG = false;
    public static boolean USE_CACHE = false;
    public static boolean USE_OLD_PAGE = false;

    public static void checkTest(Context context) {
        if (Utils.isUserRelease()) {
            API_DEBUG = false;
            USE_CACHE = false;
            USE_OLD_PAGE = false;
            POWER_DEBUG = false;
            ACCOUNT_DEBUG = false;
            return;
        }
        try {
            int i = Settings.System.getInt(context.getContentResolver(), "aiot_api_debug", 0);
            if ((i & 1) == 1) {
                API_DEBUG = true;
                USE_CACHE = true;
            }
            if ((i & 2) == 2) {
                USE_OLD_PAGE = true;
            }
            if ((i & 4) == 4) {
                POWER_DEBUG = true;
            }
            if ((i & 8) == 8) {
                ACCOUNT_DEBUG = true;
            }
        } catch (Exception unused) {
        }
    }
}
