package com.xiaopeng.aiot.device.blue.vm;

import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class DemoScan {
    private static final int SCAN_TIME_OUT = 6000;
    private IBlueVM mIBlueVM;
    private Runnable mRunnable = new Runnable() { // from class: com.xiaopeng.aiot.device.blue.vm.-$$Lambda$DemoScan$fPaTkqQ9QKFwveWWu1UaA7du6is
        @Override // java.lang.Runnable
        public final void run() {
            DemoScan.this.lambda$new$0$DemoScan();
        }
    };

    public /* synthetic */ void lambda$new$0$DemoScan() {
        this.mIBlueVM.stop();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DemoScan(IBlueVM iBlueVM) {
        this.mIBlueVM = iBlueVM;
        LogUtils.i("DemoScanVm", "IotControlDebugScan");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void start() {
        ThreadUtils.UI.removeCallbacks(this.mRunnable);
        ThreadUtils.UI.postDelay(this.mRunnable, 6000L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stop() {
        ThreadUtils.UI.removeCallbacks(this.mRunnable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onCleared() {
        ThreadUtils.UI.removeCallbacks(this.mRunnable);
    }
}
