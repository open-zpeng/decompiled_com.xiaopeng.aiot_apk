package com.xiaopeng.aiot.model.mode;
/* loaded from: classes.dex */
public class ModeManager {
    private final IMode mCinemaMode;
    private final IMode mMeditationMode;
    private final IMode mSleepMode;

    public static ModeManager get() {
        return SingletonHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final ModeManager INSTANCE = new ModeManager();

        private SingletonHolder() {
        }
    }

    private ModeManager() {
        this.mSleepMode = new SleepModeImpl();
        this.mMeditationMode = new MeditationModeImpl();
        this.mCinemaMode = new CinemaModeImpl();
    }

    public void enterMeditation() {
        this.mMeditationMode.enter();
    }

    public void exitMeditation() {
        this.mMeditationMode.exit();
    }

    public void enterSleep() {
        this.mSleepMode.enter();
    }

    public void exitSleep() {
        this.mSleepMode.exit();
    }

    public void enterCinema() {
        this.mCinemaMode.enter();
    }

    public void exitCinema() {
        this.mCinemaMode.exit();
    }
}
