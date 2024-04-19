package com.xiaopeng.iotlib.provider.voice.command;

import androidx.core.app.NotificationCompat;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
class SpeechResult {
    private String event;
    private Object result;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SpeechResult(String str, Object obj) {
        this.event = str;
        this.result = obj;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NotificationCompat.CATEGORY_EVENT, this.event);
            jSONObject.put("result", this.result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
