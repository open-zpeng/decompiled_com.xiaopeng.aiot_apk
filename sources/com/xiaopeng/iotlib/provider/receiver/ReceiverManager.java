package com.xiaopeng.iotlib.provider.receiver;

import android.util.ArraySet;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.Iterator;
/* loaded from: classes.dex */
public class ReceiverManager implements ReceiverListener {
    private static final String TAG = "ReceiverManager";
    private ArraySet<ReceiverListener> mListeners;

    private ReceiverManager() {
        this.mListeners = new ArraySet<>();
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final ReceiverManager INSTANCE = new ReceiverManager();

        private SingletonHolder() {
        }
    }

    public static ReceiverManager get() {
        return SingletonHolder.INSTANCE;
    }

    public void addListener(ReceiverListener receiverListener) {
        if (receiverListener == null) {
            return;
        }
        LogUtils.d(TAG, "addListener " + receiverListener);
        this.mListeners.add(receiverListener);
    }

    public void removeListener(ReceiverListener receiverListener) {
        if (receiverListener == null) {
            return;
        }
        LogUtils.d(TAG, "removeListener " + receiverListener);
        this.mListeners.remove(receiverListener);
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onChildSeatUninstall() {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onChildSeatUninstall();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onFragranceInsert() {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onFragranceInsert();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onEnterSleepMode() {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onEnterSleepMode();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onExitSleepMode() {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onExitSleepMode();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onEnterMeditationMode() {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onEnterMeditationMode();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onExitMeditationMode() {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onExitMeditationMode();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onFridgeDoorOpen() {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onFridgeDoorOpen();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onCoHigh() {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCoHigh();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onCinemaMode(boolean z) {
        Iterator<ReceiverListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCinemaMode(z);
        }
    }
}
