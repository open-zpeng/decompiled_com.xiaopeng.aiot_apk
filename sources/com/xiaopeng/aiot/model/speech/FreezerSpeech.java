package com.xiaopeng.aiot.model.speech;

import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent;
import com.xiaopeng.iotlib.provider.voice.command.SpeechManager;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class FreezerSpeech implements ISpeechEvent {
    static final String DOOR_STATUS = "control.refrigerator.door.status";
    static final String LOCK_STATUS = "control.refrigerator.lock.status";
    static final String POWER_STATUS = "control.refrigerator.power.status";
    static final String SUPPORT_FREEZER = "control.refrigerator.support";

    @Override // com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent
    public void onEvent(String str, String str2) {
        LogUtils.i(LogConfig.TAG_SPEECH, "Freezer onEvent not work " + str + " , data " + str2);
    }

    @Override // com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent
    public void onQuery(String str, String str2, String str3) {
        int i;
        LogUtils.i(LogConfig.TAG_SPEECH, "Freezer onQuery event " + str + " , data " + str2 + " ,callback: " + str3);
        if (SUPPORT_FREEZER.equals(str)) {
            i = createEvent().isSupportFreezer();
            LogUtils.i(LogConfig.TAG_SPEECH, "support freezer result: " + i);
        } else if (POWER_STATUS.equals(str)) {
            i = createEvent().getPower();
            LogUtils.i(LogConfig.TAG_SPEECH, "power status result: " + i);
        } else if (DOOR_STATUS.equals(str)) {
            i = createEvent().getDoor();
            LogUtils.i(LogConfig.TAG_SPEECH, "door status result: " + i);
        } else if (LOCK_STATUS.equals(str)) {
            i = createEvent().getLock();
            LogUtils.i(LogConfig.TAG_SPEECH, "child lock status result: " + i);
        } else {
            LogUtils.i(LogConfig.TAG_SPEECH, "onQuery illegal event " + str);
            i = -1;
        }
        SpeechManager.get().postQueryResult(str, str3, Integer.valueOf(i));
    }

    private FreezerEvent createEvent() {
        return new FreezerEventImpl();
    }
}
