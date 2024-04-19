package com.xiaopeng.iotlib;

import android.app.Application;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.provider.car.CarManager;
import com.xiaopeng.iotlib.provider.iot.IotManagers;
import com.xiaopeng.iotlib.utils.TimeLogs;
import com.xiaopeng.lib.bughunter.BugHunter;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.xui.Xui;
/* loaded from: classes.dex */
public class MainBase {
    private Application mApp;

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAsyncInit(Application application) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onInit(Application application) {
    }

    public void init(Application application) {
        StartPerfUtils.appOnCreateBegin();
        Iot.setApp(application);
        this.mApp = application;
        TimeLogs create = TimeLogs.create();
        create.start("MainBase init time ");
        init();
        create.end();
        new Thread(new Runnable() { // from class: com.xiaopeng.iotlib.-$$Lambda$MainBase$qFBmXbr56_FBB-cWLv4adjQC1pw
            @Override // java.lang.Runnable
            public final void run() {
                MainBase.this.lambda$init$0$MainBase();
            }
        }).start();
        StartPerfUtils.appOnCreateEnd();
    }

    public /* synthetic */ void lambda$init$0$MainBase() {
        TimeLogs create = TimeLogs.create();
        create.start("MainBase init thread ");
        asyncInit();
        create.end();
    }

    private void init() {
        ApiConfig.checkTest(this.mApp);
        IotManagers.init();
        Xui.init(this.mApp);
        Xui.setVuiEnable(true);
        Xui.setFontScaleDynamicChangeEnable(true);
        Xui.setDialogFullScreen(true);
        Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(this.mApp));
        SaveManager.get().init(this.mApp);
        LogConfig.init();
        onInit(this.mApp);
    }

    private void asyncInit() {
        CarManager.get().init(this.mApp);
        BugHunter.init(this.mApp);
        onAsyncInit(this.mApp);
    }
}
