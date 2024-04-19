package com.xiaopeng.iotlib.model.sp;

import android.app.Application;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class SaveManager {
    private Application mApplication;
    private volatile IFragranceMeditationSave mFragranceMeditationSave;
    private volatile IFragranceMeditationSave mFragranceOutMeditationSave;
    private volatile IFragranceSleepSpaceSave mFragranceOutSleepSpaceSave;
    private volatile IFragranceSleepSpaceSave mFragranceSleepSpaceSave;
    private volatile IFreezerModeSave mFreezerModeSave;
    private volatile IFreezerTemperSave mFreezerTemperSave;
    private volatile IFreezerTimeSave mFreezerTimeSave;
    private volatile IAirbedSave mIAirbedSave;
    private volatile IFragranceSave mIFragranceSave;
    private volatile IWatchesSave mIWatchesSave;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final SaveManager INSTANCE = new SaveManager();

        private SingletonHolder() {
        }
    }

    public static SaveManager get() {
        return SingletonHolder.INSTANCE;
    }

    public void init(Application application) {
        this.mApplication = application;
    }

    public IFragranceSave getFragranceSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "getFragranceSave mContext is null, must be init");
            return null;
        }
        if (this.mIFragranceSave == null) {
            synchronized (this) {
                if (this.mIFragranceSave == null) {
                    this.mIFragranceSave = new FragranceSave(this.mApplication);
                }
            }
        }
        return this.mIFragranceSave;
    }

    public IFragranceMeditationSave getFragranceMeditationSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "Get fragrance  mContext is null, must be init");
            return null;
        }
        if (this.mFragranceMeditationSave == null) {
            synchronized (this) {
                if (this.mFragranceMeditationSave == null) {
                    this.mFragranceMeditationSave = new FragranceMeditationSave(this.mApplication);
                }
            }
        }
        return this.mFragranceMeditationSave;
    }

    public IFragranceMeditationSave getFragranceOutMeditationSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "Get fragrance  mContext is null, must be init");
            return null;
        }
        if (this.mFragranceOutMeditationSave == null) {
            synchronized (this) {
                if (this.mFragranceOutMeditationSave == null) {
                    this.mFragranceOutMeditationSave = new FragranceMeditationOutSave(this.mApplication);
                }
            }
        }
        return this.mFragranceOutMeditationSave;
    }

    public IFragranceSleepSpaceSave getFragranceSleepSpaceSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "SleepSpace: Get fragrance  mContext is null, must be init");
            return null;
        }
        if (this.mFragranceSleepSpaceSave == null) {
            synchronized (this) {
                if (this.mFragranceSleepSpaceSave == null) {
                    this.mFragranceSleepSpaceSave = new FragranceSleepSpaceSave(this.mApplication);
                }
            }
        }
        return this.mFragranceSleepSpaceSave;
    }

    public IFragranceSleepSpaceSave getFragranceOutSleepSpaceSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "SleepSpace: Get fragrance  mContext is null, must be init");
            return null;
        }
        if (this.mFragranceOutSleepSpaceSave == null) {
            synchronized (this) {
                if (this.mFragranceOutSleepSpaceSave == null) {
                    this.mFragranceOutSleepSpaceSave = new FragranceSleepSpaceOutSave(this.mApplication);
                }
            }
        }
        return this.mFragranceOutSleepSpaceSave;
    }

    public IWatchesSave getWatchesSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "getWatchesSave mContext is null, must be init");
            return null;
        }
        if (this.mIWatchesSave == null) {
            synchronized (this) {
                if (this.mIWatchesSave == null) {
                    this.mIWatchesSave = new WatchesSave(this.mApplication);
                }
            }
        }
        return this.mIWatchesSave;
    }

    public IAirbedSave getAirbedSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "getAirbedSave mContext is null, must be init");
            return null;
        }
        if (this.mIAirbedSave == null) {
            synchronized (this) {
                if (this.mIAirbedSave == null) {
                    this.mIAirbedSave = new AirbedSave(this.mApplication);
                }
            }
        }
        return this.mIAirbedSave;
    }

    public IFreezerTimeSave getFreezerTimeSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "getFreezerTimeSave mContext is null, must be init");
            return null;
        }
        if (this.mFreezerTimeSave == null) {
            synchronized (this) {
                if (this.mFreezerTimeSave == null) {
                    this.mFreezerTimeSave = new FreezerTimeSave(this.mApplication);
                }
            }
        }
        return this.mFreezerTimeSave;
    }

    public IFreezerModeSave getFreezerModeSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "getFreezerModeSave mContext is null, must be init");
            return null;
        }
        if (this.mFreezerTemperSave == null) {
            synchronized (this) {
                if (this.mFreezerModeSave == null) {
                    this.mFreezerModeSave = new FreezerModeSave(this.mApplication);
                }
            }
        }
        return this.mFreezerModeSave;
    }

    public IFreezerTemperSave getFreezerTemperSave() {
        if (this.mApplication == null) {
            LogUtils.e(LogConfig.TAG_SAVE, "getFreezerTemperSave mContext is null, must be init");
            return null;
        }
        if (this.mFreezerTemperSave == null) {
            synchronized (this) {
                if (this.mFreezerTemperSave == null) {
                    this.mFreezerTemperSave = new FreezerTemperSave(this.mApplication);
                }
            }
        }
        return this.mFreezerTemperSave;
    }
}
