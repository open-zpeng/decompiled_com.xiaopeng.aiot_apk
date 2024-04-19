package com.xiaopeng.aiot.model.buriedpoint;
/* loaded from: classes.dex */
public class DataLogManager {
    private IFragranceDL mIFragranceDL;

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final DataLogManager INSTANCE = new DataLogManager();

        private SingletonHolder() {
        }
    }

    private DataLogManager() {
        this.mIFragranceDL = new FragranceDLImpl();
    }

    public static DataLogManager get() {
        return SingletonHolder.INSTANCE;
    }

    public IFragranceDL getIFragranceDL() {
        return this.mIFragranceDL;
    }
}
