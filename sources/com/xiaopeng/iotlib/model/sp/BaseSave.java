package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public abstract class BaseSave {
    protected final String FRAGRANCE = "fragrance";
    protected final String FRAGRANCE_SLEEP_SPACE = "fragrance_sleep_space";
    private ISharePreferences mISharePreferences;

    abstract String getPreferenceName();

    private BaseSave() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseSave(Context context) {
        this.mISharePreferences = new SharePreferenceImpl(context, getPreferenceName());
        LogUtils.i(LogConfig.TAG_SAVE, "save create  " + getPreferenceName());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ISharePreferences getPreferences() {
        return this.mISharePreferences;
    }

    public void commit() {
        getPreferences().commit();
    }

    public void remove(String str) {
        getPreferences().remove(str);
    }
}
