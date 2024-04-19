package com.xiaopeng.aiot.model.scene;

import com.xiaopeng.aiot.model.common.AiCardUtils;
import com.xiaopeng.aiot.model.mode.ModeManager;
import com.xiaopeng.iotlib.provider.receiver.ReceiverListener;
import com.xiaopeng.iotlib.provider.receiver.ReceiverManager;
/* loaded from: classes.dex */
public class ReceiverScene implements ReceiverListener {
    private ReceiverScene() {
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final ReceiverScene INSTANCE = new ReceiverScene();

        private SingletonHolder() {
        }
    }

    public static ReceiverScene get() {
        return SingletonHolder.INSTANCE;
    }

    public synchronized void init() {
        ReceiverManager.get().addListener(this);
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onEnterSleepMode() {
        ModeManager.get().enterSleep();
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onExitSleepMode() {
        ModeManager.get().exitSleep();
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onEnterMeditationMode() {
        ModeManager.get().enterMeditation();
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onExitMeditationMode() {
        ModeManager.get().exitMeditation();
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onFridgeDoorOpen() {
        AiCardUtils.sendFridgeDoorCard();
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onCoHigh() {
        AiCardUtils.sendCoCard();
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onCinemaMode(boolean z) {
        if (z) {
            ModeManager.get().enterCinema();
        } else {
            ModeManager.get().exitCinema();
        }
    }
}
