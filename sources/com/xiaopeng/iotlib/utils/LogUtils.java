package com.xiaopeng.iotlib.utils;

import android.util.Log;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import java.util.HashMap;
/* loaded from: classes.dex */
public class LogUtils {
    public static final int LOG_D_LEVEL = 3;
    public static final int LOG_E_LEVEL = 6;
    public static final int LOG_I_LEVEL = 4;
    public static final int LOG_W_LEVEL = 5;
    private static final String TAG = "aiot=";
    private static final boolean DEBUG = ApiConfig.API_DEBUG;
    private static int LOG_LEVEL = 3;
    private static HashMap<String, Integer> sHashMap = new HashMap<>();

    public static void setLogTagLevel(String str, Integer num) {
        sHashMap.put(str, num);
    }

    public static void clearTagLevel() {
        sHashMap.clear();
    }

    public static void setLogLevel(int i) {
        LOG_LEVEL = i;
    }

    private static boolean isLogTagEnabled(String str, int i) {
        Integer num = sHashMap.get(str);
        return num == null || i >= num.intValue();
    }

    private static boolean isLogLevelEnabled(int i) {
        return LOG_LEVEL <= i;
    }

    public static void d(String str, String str2) {
        if (isLogTagEnabled(str, 3) && isLogLevelEnabled(3)) {
            StringBuilder sb = new StringBuilder();
            sb.append(TAG);
            if (str == null) {
                str = "";
            }
            sb.append(str);
            Log.d(sb.toString(), stackTraceLog(str2));
        }
    }

    public static void d(String str, String str2, int i) {
        if (isLogTagEnabled(str, 3) && isLogLevelEnabled(3)) {
            StringBuilder sb = new StringBuilder();
            sb.append(TAG);
            if (str == null) {
                str = "";
            }
            sb.append(str);
            Log.d(sb.toString(), stackTraceLog(str2, i));
        }
    }

    public static void i(String str, String str2) {
        if (isLogTagEnabled(str, 4) && isLogLevelEnabled(4)) {
            StringBuilder sb = new StringBuilder();
            sb.append(TAG);
            if (str == null) {
                str = "";
            }
            sb.append(str);
            Log.i(sb.toString(), stackTraceLog(str2));
        }
    }

    public static void i(String str, String str2, int i) {
        if (isLogTagEnabled(str, 4) && isLogLevelEnabled(4)) {
            StringBuilder sb = new StringBuilder();
            sb.append(TAG);
            if (str == null) {
                str = "";
            }
            sb.append(str);
            Log.i(sb.toString(), stackTraceLog(str2, i));
        }
    }

    public static void w(String str, String str2) {
        if (isLogTagEnabled(str, 5) && isLogLevelEnabled(5)) {
            StringBuilder sb = new StringBuilder();
            sb.append(TAG);
            if (str == null) {
                str = "";
            }
            sb.append(str);
            Log.w(sb.toString(), stackTraceLog(str2));
        }
    }

    public static void e(String str, String str2) {
        if (isLogTagEnabled(str, 6) && isLogLevelEnabled(6)) {
            StringBuilder sb = new StringBuilder();
            sb.append(TAG);
            if (str == null) {
                str = "";
            }
            sb.append(str);
            Log.e(sb.toString(), stackTraceLog(str2));
        }
    }

    private static String stackTraceLog(String str) {
        if (DEBUG) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            return "(" + stackTrace[2].getFileName() + ":" + stackTrace[2].getLineNumber() + ") " + str + "--" + Thread.currentThread().getName();
        }
        return str;
    }

    private static String stackTraceLog(String str, int i) {
        if (DEBUG) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            int i2 = i + 2;
            sb.append(stackTrace[i2].getFileName());
            sb.append(":");
            sb.append(stackTrace[i2].getLineNumber());
            sb.append(") ");
            sb.append(str);
            return sb.toString();
        }
        return str;
    }
}
