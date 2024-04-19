package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class FragranceMeditationOutSave extends BaseSave implements IFragranceMeditationSave {
    private String mSpName;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FragranceMeditationOutSave(Context context) {
        super(context);
        this.mSpName = "fragrance_meditation";
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceMeditationSave
    public int getChannelId() {
        int i = getPreferences().getInt(IFragranceMeditationSave.KEY_CHANNEL_MEDITATION_OUT, -1);
        LogUtils.i(LogConfig.TAG_SAVE, "Get out meditation Channel Index:" + i);
        return i;
    }

    @Override // com.xiaopeng.iotlib.model.sp.IFragranceMeditationSave
    public boolean saveChannelId(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean commit = getPreferences().putInt(IFragranceMeditationSave.KEY_CHANNEL_MEDITATION_OUT, i).commit();
        LogUtils.i(LogConfig.TAG_SAVE, String.format("Save out meditation channel index value %s, result %s,time %s  t %s", Integer.valueOf(i), Boolean.valueOf(commit), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Thread.currentThread()));
        return commit;
    }

    @Override // com.xiaopeng.iotlib.model.sp.BaseSave
    String getPreferenceName() {
        return this.mSpName;
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISave
    public void clear() {
        getPreferences().clear().commit();
    }
}
