package com.xiaopeng.iotlib.provider.voice.command;

import android.net.Uri;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import java.util.HashMap;
/* loaded from: classes.dex */
public class SpeechManager {
    private static final String TAG = "SpeechManager";
    private HashMap<String, ISpeechEvent> mISpeechEventMap;

    private SpeechManager() {
        this.mISpeechEventMap = new HashMap<>();
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final SpeechManager INSTANCE = new SpeechManager();

        private SingletonHolder() {
        }
    }

    public static SpeechManager get() {
        return SingletonHolder.INSTANCE;
    }

    public void add(String str, ISpeechEvent iSpeechEvent) {
        this.mISpeechEventMap.put(str, iSpeechEvent);
    }

    public void postQueryResult(String str, String str2) {
        postQueryResult(str, str2, true);
    }

    public void postQueryResult(String str, String str2, Object obj) {
        try {
            LogUtils.i(TAG, String.format("postQueryResult event:%s , callback:%s, result:%s", str, str2, obj));
            ApiRouter.route(Uri.parse(str2).buildUpon().appendQueryParameter("result", new SpeechResult(str, obj).toString()).build());
        } catch (Exception e) {
            LogUtils.i(TAG, "postQueryResult exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void dispatchEvent(final String str, final String str2) {
        final ISpeechEvent iSpeechEvent = this.mISpeechEventMap.get(str);
        if (iSpeechEvent != null) {
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.voice.command.-$$Lambda$SpeechManager$eUbvzmXJTfEQZBbcx2xTd_b1i8o
                @Override // java.lang.Runnable
                public final void run() {
                    ISpeechEvent.this.onEvent(str, str2);
                }
            });
        }
    }

    public void dispatchQuery(final String str, final String str2, final String str3) {
        final ISpeechEvent iSpeechEvent = this.mISpeechEventMap.get(str);
        if (iSpeechEvent != null) {
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.voice.command.-$$Lambda$SpeechManager$AAGMYUjxCBWUFCgKhjnyl7rl4BU
                @Override // java.lang.Runnable
                public final void run() {
                    ISpeechEvent.this.onQuery(str, str2, str3);
                }
            });
        }
    }
}
