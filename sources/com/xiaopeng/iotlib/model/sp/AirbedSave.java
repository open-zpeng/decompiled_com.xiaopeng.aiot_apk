package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class AirbedSave extends BaseSave implements IAirbedSave {
    @Override // com.xiaopeng.iotlib.model.sp.BaseSave
    String getPreferenceName() {
        return "airbed";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AirbedSave(Context context) {
        super(context);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IAirbedSave
    public int getBedPressure() {
        int i = getPreferences().getInt(IAirbedSave.KEY_ATMOSPHERE, 1);
        LogUtils.i(LogConfig.TAG_SAVE, "getBedPressure " + i);
        return i;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IAirbedSave
    public boolean saveBedPressure(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean commit = getPreferences().putInt(IAirbedSave.KEY_ATMOSPHERE, i).commit();
        LogUtils.i(LogConfig.TAG_SAVE, String.format("savePressure value %s, result %s,time %s  t %s", Integer.valueOf(i), Boolean.valueOf(commit), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Thread.currentThread()));
        return commit;
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISave
    public void clear() {
        getPreferences().clear().commit();
    }
}
