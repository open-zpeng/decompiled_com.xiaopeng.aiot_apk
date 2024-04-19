package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class WatchesSave extends BaseSave implements IWatchesSave {
    @Override // com.xiaopeng.iotlib.model.sp.BaseSave
    String getPreferenceName() {
        return "aiot_watches";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WatchesSave(Context context) {
        super(context);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IWatchesSave
    public boolean isMarkEnable() {
        boolean z = getPreferences().getBoolean(IWatchesSave.KEY_MARK, false);
        LogUtils.i(LogConfig.TAG_SAVE, "isMarkEnable " + z);
        return z;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IWatchesSave
    public boolean saveMarkEnable(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean commit = getPreferences().putBoolean(IWatchesSave.KEY_MARK, z).commit();
        LogUtils.i(LogConfig.TAG_SAVE, String.format("saveMarkEnable value %s, result %s,time %s  t %s", Boolean.valueOf(z), Boolean.valueOf(commit), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Thread.currentThread()));
        return commit;
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISave
    public void clear() {
        getPreferences().clear().commit();
    }
}
