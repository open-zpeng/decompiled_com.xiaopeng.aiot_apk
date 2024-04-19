package com.xiaopeng.iotlib.base;

import androidx.lifecycle.ViewModel;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class BaseViewModel extends ViewModel {
    private static int sCount;

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseViewModel() {
        sCount++;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void logD(String str) {
        LogUtils.d(LogConfig.TAG_VM, getClass().getSimpleName() + " " + hashCode() + "-" + str, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void logI(String str) {
        LogUtils.i(LogConfig.TAG_VM, getClass().getSimpleName() + " " + hashCode() + "-" + str, 1);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        sCount--;
        logD("ViewModel finalize " + sCount);
    }
}
