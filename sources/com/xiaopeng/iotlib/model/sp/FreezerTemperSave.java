package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class FreezerTemperSave extends BaseSave implements IFreezerTemperSave {
    @Override // com.xiaopeng.iotlib.model.sp.BaseSave
    String getPreferenceName() {
        return "freezer";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FreezerTemperSave(Context context) {
        super(context);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFreezerTemperSave
    public int getTemperValue() {
        int i = getPreferences().getInt(IFreezerTemperSave.KEY_FREEZER_TEMPER, 0);
        LogUtils.i(LogConfig.TAG_SAVE, "getTemperValue " + i);
        return i;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFreezerTemperSave
    public boolean savedTemperVal(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean commit = getPreferences().putInt(IFreezerTemperSave.KEY_FREEZER_TEMPER, i).commit();
        LogUtils.i(LogConfig.TAG_SAVE, String.format("saveTemper value %s, result %s,time %s  t %s", Integer.valueOf(i), Boolean.valueOf(commit), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Thread.currentThread()));
        return commit;
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISave
    public void clear() {
        getPreferences().clear().commit();
    }
}
