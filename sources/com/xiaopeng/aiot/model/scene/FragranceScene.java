package com.xiaopeng.aiot.model.scene;

import android.app.Application;
import com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.iotlib.provider.receiver.ReceiverListener;
import com.xiaopeng.iotlib.provider.receiver.ReceiverManager;
/* loaded from: classes.dex */
public class FragranceScene implements ReceiverListener {
    private Application mApp;

    private FragranceScene() {
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final FragranceScene INSTANCE = new FragranceScene();

        private SingletonHolder() {
        }
    }

    public static FragranceScene get() {
        return SingletonHolder.INSTANCE;
    }

    public synchronized void init(Application application) {
        this.mApp = application;
        ReceiverManager.get().addListener(this);
    }

    @Override // com.xiaopeng.iotlib.provider.receiver.ReceiverListener
    public void onFragranceInsert() {
        if (PageConfigFactory.FRAGRANCE2.go(this.mApp)) {
            BuriedPointManager.get().getIFragranceBP().into(3);
        }
    }
}
