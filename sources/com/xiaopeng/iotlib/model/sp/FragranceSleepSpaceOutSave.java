package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class FragranceSleepSpaceOutSave extends BaseSave implements IFragranceSleepSpaceSave {
    @Override // com.xiaopeng.iotlib.model.sp.BaseSave
    String getPreferenceName() {
        return "fragrance_sleep_space";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FragranceSleepSpaceOutSave(Context context) {
        super(context);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceSleepSpaceSave
    public int getChannelId() {
        int i = getPreferences().getInt(IFragranceSleepSpaceSave.KEY_CHANNEL_SLEEP_SPACE_OUT, -1);
        LogUtils.i(LogConfig.TAG_SAVE, "Get out sleep space Channel Index:" + i);
        return i;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceSleepSpaceSave
    public boolean saveChannelId(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean commit = getPreferences().putInt(IFragranceSleepSpaceSave.KEY_CHANNEL_SLEEP_SPACE_OUT, i).commit();
        LogUtils.i(LogConfig.TAG_SAVE, String.format("Save sleep space channel index value %s, result %s,time %s  t %s", Integer.valueOf(i), Boolean.valueOf(commit), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Thread.currentThread()));
        return commit;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceSleepSpaceSave
    public int getDensity() {
        return getPreferences().getInt(IFragranceSleepSpaceSave.KEY_DENSITY_SLEEP_SPACE_OUT, -1);
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceSleepSpaceSave
    public boolean saveDensity(int i) {
        return getPreferences().putInt(IFragranceSleepSpaceSave.KEY_DENSITY_SLEEP_SPACE_OUT, i).commit();
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISave
    public void clear() {
        getPreferences().clear().commit();
    }
}
