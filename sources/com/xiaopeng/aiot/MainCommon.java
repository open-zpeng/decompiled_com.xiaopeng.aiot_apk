package com.xiaopeng.aiot;

import android.app.Application;
import com.xiaopeng.aiot.model.scene.ReceiverScene;
import com.xiaopeng.aiot.model.scene.SpeechScene;
import com.xiaopeng.aiot.model.scene.WatchesScene;
import com.xiaopeng.iotlib.MainBase;
import com.xiaopeng.iotlib.model.product.ProductConfig;
/* loaded from: classes.dex */
public class MainCommon extends MainBase {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.MainBase
    public void onInit(Application application) {
        super.onInit(application);
        ReceiverScene.get().init();
        if (ProductConfig.get().supportWatches()) {
            WatchesScene.get().init();
        }
        SpeechScene.init(application);
    }
}
