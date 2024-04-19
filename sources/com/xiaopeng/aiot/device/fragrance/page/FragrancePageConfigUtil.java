package com.xiaopeng.aiot.device.fragrance.page;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.xiaopeng.aiot.BuildConfig;
import com.xiaopeng.aiot.model.page.PageConfigDetail2;
import com.xiaopeng.app.ActivityManagerFactory;
import com.xiaopeng.view.WindowManagerFactory;
import java.util.HashMap;
/* loaded from: classes.dex */
public class FragrancePageConfigUtil {
    public static final String BROADCAST_ACTION = "com.xiaopeng.intent.action.CLOSE_FRAGRANCE";
    public static final String TAG = "FragrancePage Speech";
    private static final Uri uri = new Uri.Builder().scheme(BuildConfig.SCHEME).authority(BuildConfig.AUTH).path(PageConfigDetail2.PATH).appendQueryParameter("type", "fragrance").build();
    private static final HashMap<String, String> pageMap = new HashMap<>();

    private static void init() {
        pageMap.put("XPPlugin_FragranceApp://FragranceMainView?from=speech", "/device/detail/fragrance");
    }

    public static Uri getUri() {
        return uri;
    }

    public static void showFragrancePage(Application application, int i) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268468224);
        intent.setData(uri);
        ActivityManagerFactory.create(application).startActivity(application, intent, i);
    }

    public static boolean isSpeechLocation(int i, String str, Application application) {
        int screenId = WindowManagerFactory.create(application).getScreenId(application.getPackageName());
        Log.d(TAG, "the window get pageId is: " + screenId + " sharedId is: " + i + " the application packageName is: " + application.getPackageName());
        if (hasShow(str) && screenId == i) {
            Log.d(TAG, "isSpeechLocation : true.");
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean hasShow(java.lang.String r3) {
        /*
            init()
            com.xiaopeng.iotlib.data.PageId r0 = new com.xiaopeng.iotlib.data.PageId
            java.util.HashMap<java.lang.String, java.lang.String> r1 = com.xiaopeng.aiot.device.fragrance.page.FragrancePageConfigUtil.pageMap
            java.lang.Object r3 = r1.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            r0.<init>(r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r1 = "hasShow: "
            r3.append(r1)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            java.lang.String r1 = "FragrancePage Speech"
            android.util.Log.d(r1, r3)
            com.xiaopeng.iotlib.model.activity.ActivityState r3 = com.xiaopeng.iotlib.model.activity.ActivityState.getInstance()
            java.util.HashMap r3 = r3.getActivityState(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "states: "
            r0.append(r2)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
            if (r3 == 0) goto L6f
            java.util.Collection r3 = r3.values()
            java.util.Iterator r3 = r3.iterator()
        L4c:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L6f
            java.lang.Object r0 = r3.next()
            com.xiaopeng.iotlib.model.activity.ActivityState$State r0 = (com.xiaopeng.iotlib.model.activity.ActivityState.State) r0
            com.xiaopeng.iotlib.model.activity.ActivityState$State r2 = com.xiaopeng.iotlib.model.activity.ActivityState.State.CREATE
            if (r0 == r2) goto L68
            com.xiaopeng.iotlib.model.activity.ActivityState$State r2 = com.xiaopeng.iotlib.model.activity.ActivityState.State.START
            if (r0 == r2) goto L68
            com.xiaopeng.iotlib.model.activity.ActivityState$State r2 = com.xiaopeng.iotlib.model.activity.ActivityState.State.RESUME
            if (r0 == r2) goto L68
            com.xiaopeng.iotlib.model.activity.ActivityState$State r2 = com.xiaopeng.iotlib.model.activity.ActivityState.State.PAUSE
            if (r0 != r2) goto L4c
        L68:
            java.lang.String r3 = "hasShow is : true"
            android.util.Log.d(r1, r3)
            r3 = 1
            return r3
        L6f:
            java.lang.String r3 = "hasShow is : false"
            android.util.Log.d(r1, r3)
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.aiot.device.fragrance.page.FragrancePageConfigUtil.hasShow(java.lang.String):boolean");
    }

    public static void closeFragrancePage(Application application) {
        application.getApplicationContext().sendBroadcast(new Intent(BROADCAST_ACTION));
    }
}
