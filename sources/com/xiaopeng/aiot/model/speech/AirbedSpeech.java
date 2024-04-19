package com.xiaopeng.aiot.model.speech;

import com.xiaopeng.aiot.device.airbed.api.AirBedApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent;
import com.xiaopeng.iotlib.provider.voice.command.SpeechManager;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class AirbedSpeech implements ISpeechEvent {
    static final String BIND = "airbed.is.binded";

    @Override // com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent
    public void onEvent(String str, String str2) {
    }

    @Override // com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent
    public void onQuery(String str, String str2, String str3) {
        LogUtils.i(LogConfig.TAG_SPEECH, "Airbed onQuery event " + str + " , data " + str2 + " ,callback: " + str3);
        if (BIND.equals(str)) {
            SpeechManager.get().postQueryResult(str, str3, Boolean.valueOf(AirBedApi.getApi().loadData().getStatus() == 4));
        }
    }
}
