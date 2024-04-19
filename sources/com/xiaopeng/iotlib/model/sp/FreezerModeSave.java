package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class FreezerModeSave extends BaseSave implements IFreezerModeSave {
    @Override // com.xiaopeng.iotlib.model.sp.BaseSave
    String getPreferenceName() {
        return "freezer";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FreezerModeSave(Context context) {
        super(context);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFreezerModeSave
    public int getWorkModeValue() {
        int i = getPreferences().getInt(IFreezerModeSave.KEY_FREEZER_MODE, 0);
        LogUtils.i(LogConfig.TAG_SAVE, "getModeValue " + i);
        return i;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFreezerModeSave
    public boolean savedWorkModeVal(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean commit = getPreferences().putInt(IFreezerModeSave.KEY_FREEZER_MODE, i).commit();
        LogUtils.i(LogConfig.TAG_SAVE, String.format("saveMode value %s, result %s,time %s  t %s", Integer.valueOf(i), Boolean.valueOf(commit), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Thread.currentThread()));
        return commit;
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISave
    public void clear() {
        getPreferences().clear().commit();
    }
}
