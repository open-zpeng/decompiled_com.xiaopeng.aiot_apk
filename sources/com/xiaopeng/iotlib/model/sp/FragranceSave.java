package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class FragranceSave extends BaseSave implements IFragranceSave {
    @Override // com.xiaopeng.iotlib.model.sp.BaseSave
    protected String getPreferenceName() {
        return "fragrance";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FragranceSave(Context context) {
        super(context);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceSave
    public int getChannelId() {
        int i = getPreferences().getInt(IFragranceSave.KEY_CHANNEL, -1);
        LogUtils.i(LogConfig.TAG_SAVE, "getChannelIndex " + i);
        return i;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceSave
    public boolean saveChannelId(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean commit = getPreferences().putInt(IFragranceSave.KEY_CHANNEL, i).commit();
        LogUtils.i(LogConfig.TAG_SAVE, String.format("saveChannelIndex value %s, result %s,time %s  t %s", Integer.valueOf(i), Boolean.valueOf(commit), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Thread.currentThread()));
        return commit;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceSave
    public int getDensity() {
        return getPreferences().getInt(IFragranceSave.KEY_DENSITY, -1);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceSave
    public boolean saveDensity(int i) {
        return getPreferences().putInt(IFragranceSave.KEY_DENSITY, i).commit();
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISave
    public void clear() {
        getPreferences().clear().commit();
    }
}
