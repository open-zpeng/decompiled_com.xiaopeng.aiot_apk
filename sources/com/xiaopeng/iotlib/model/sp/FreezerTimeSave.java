package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class FreezerTimeSave extends BaseSave implements IFreezerTimeSave {
    @Override // com.xiaopeng.iotlib.model.sp.BaseSave
    String getPreferenceName() {
        return "freezer";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FreezerTimeSave(Context context) {
        super(context);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFreezerTimeSave
    public int getTimeValue() {
        int i = getPreferences().getInt(IFreezerTimeSave.KEY_FREEZER_TIME, 0);
        LogUtils.i(LogConfig.TAG_SAVE, "getTimeValue " + i);
        return i;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFreezerTimeSave
    public boolean savedTimeValue(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean commit = getPreferences().putInt(IFreezerTimeSave.KEY_FREEZER_TIME, i).commit();
        LogUtils.i(LogConfig.TAG_SAVE, String.format("saveTime value %s, result %s,time %s  t %s", Integer.valueOf(i), Boolean.valueOf(commit), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Thread.currentThread()));
        return commit;
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISave
    public void clear() {
        getPreferences().clear().commit();
    }
}
