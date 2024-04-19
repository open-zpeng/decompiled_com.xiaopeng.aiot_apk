package com.xiaopeng.aiot.model.speech;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import com.xiaopeng.iotlib.provider.voice.command.SpeechManager;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.Utils;
/* loaded from: classes.dex */
public class SpeechConfig {
    public static boolean SPEECH_DEBUG = false;

    public static void init(Application application) {
        checkTest(application);
        FragranceSpeech fragranceSpeech = new FragranceSpeech(application);
        SpeechManager.get().add("ac.perfume.on", fragranceSpeech);
        SpeechManager.get().add("ac.perfume.off", fragranceSpeech);
        SpeechManager.get().add("ac.perfume.potency.set", fragranceSpeech);
        SpeechManager.get().add("gui.page.open.state.aiot", fragranceSpeech);
        SpeechManager.get().add("gui.page.open.aiot", fragranceSpeech);
        SpeechManager.get().add("gui.page.close.aiot", fragranceSpeech);
        WatchSpeech watchSpeech = new WatchSpeech();
        SpeechManager.get().add("is.bind.childwatch", watchSpeech);
        SpeechManager.get().add("get.childwatch.location", watchSpeech);
        SpeechManager.get().add("is.watch.location.opened", watchSpeech);
        SpeechManager.get().add("get.watch.phone.info", watchSpeech);
        SpeechManager.get().add("airbed.is.binded", new AirbedSpeech());
        FreezerSpeech freezerSpeech = new FreezerSpeech();
        SpeechManager.get().add("control.refrigerator.support", freezerSpeech);
        SpeechManager.get().add("control.refrigerator.power.status", freezerSpeech);
        SpeechManager.get().add("control.refrigerator.door.status", freezerSpeech);
        SpeechManager.get().add("control.refrigerator.lock.status", freezerSpeech);
    }

    private static void checkTest(Context context) {
        if (Utils.isUserRelease()) {
            return;
        }
        try {
            int i = Settings.System.getInt(context.getContentResolver(), "aiot_sp_debug", 0);
            if (i == 1) {
                LogUtils.i("checkTest", " ,aiot_sp_debug " + i);
                SPEECH_DEBUG = true;
            }
        } catch (Exception unused) {
        }
    }
}
