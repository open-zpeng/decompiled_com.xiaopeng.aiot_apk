package com.xiaopeng.iotlib.provider.voice.command;
/* loaded from: classes.dex */
public interface ISpeechEvent {
    void onEvent(String str, String str2);

    void onQuery(String str, String str2, String str3);
}
