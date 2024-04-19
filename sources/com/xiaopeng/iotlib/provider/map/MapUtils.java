package com.xiaopeng.iotlib.provider.map;

import android.net.Uri;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class MapUtils {
    public static final String ACTION = "com.xiaopeng.montecarlo.AMAP_RESTART";
    private static final String TAG = "map";
    public static final int TYPE = 2;

    private static String getPkgName() {
        return "com.xiaopeng.napa";
    }

    public static void drawMark(String str) {
        LogUtils.i(TAG, "drawMark  " + str);
        Uri.Builder builder = new Uri.Builder();
        route(builder.authority(getPkgName() + ".MapMarkService").path("drawMarks").appendQueryParameter("type", String.valueOf(2)).appendQueryParameter("marks", str).build());
    }

    public static void clearMark() {
        LogUtils.i(TAG, "clearMark : ");
        Uri.Builder builder = new Uri.Builder();
        route(builder.authority(getPkgName() + ".MapMarkService").path("clearMark").appendQueryParameter("type", String.valueOf(2)).build());
    }

    public static void navigation(double d, double d2) {
        LogUtils.i(TAG, "navigation lat : " + d + " , lon : " + d2);
        double[] bg09_to_gcj02 = bg09_to_gcj02(d2, d);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", "android.intent.action.VIEW");
            jSONObject.put("uri", "xpengmap://map/navi?data={\"dest\":{\"lat\":" + bg09_to_gcj02[1] + ",\"lon\":" + bg09_to_gcj02[0] + "}}&src=com.xiaopeng.aiot");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jSONObject2 = jSONObject.toString();
        Uri.Builder builder = new Uri.Builder();
        route(builder.authority(getPkgName() + ".GuideInfoService").path("onNewIntent").appendQueryParameter("intent", jSONObject2).build());
    }

    public static double[] bg09_to_gcj02(double d, double d2) {
        double d3 = d - 0.0065d;
        double d4 = d2 - 0.006d;
        double sqrt = Math.sqrt((d3 * d3) + (d4 * d4)) - (Math.sin(d4 * 52.35987755982988d) * 2.0E-5d);
        double atan2 = Math.atan2(d4, d3) - (Math.cos(d3 * 52.35987755982988d) * 3.0E-6d);
        return new double[]{Math.cos(atan2) * sqrt, sqrt * Math.sin(atan2)};
    }

    private static void route(Uri uri) {
        try {
            LogUtils.d(TAG, "targetUrl : " + uri);
            ApiRouter.route(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
