package com.xiaopeng.aiot;

import android.app.Application;
import android.content.Context;
import com.xiaopeng.aiot.demo.DemoManager;
import com.xiaopeng.aiot.model.page.PageConfigManager;
import com.xiaopeng.aiot.model.scene.FragranceScene;
import com.xiaopeng.iotlib.provider.voice.vui.VuiManager;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        new MainApp().init(this);
    }

    /* loaded from: classes.dex */
    private static class MainApp extends MainCommon {
        private MainApp() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.aiot.MainCommon, com.xiaopeng.iotlib.MainBase
        public void onInit(Application application) {
            super.onInit(application);
            VuiManager.init(application);
            PageConfigManager.init();
            FragranceScene.get().init(application);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.iotlib.MainBase
        public void onAsyncInit(Application application) {
            super.onAsyncInit(application);
            DemoManager.init();
            LogUtils.i(App.TAG, "BUILD_INFO: d55_1.6.0 c4c5f09 2023/07/17 12:07:38");
        }
    }
}
