package com.xiaopeng.aiot.model.buriedpoint;
/* loaded from: classes.dex */
public class BuriedPointManager {
    private IFragranceBP mIFragranceBP;
    private IFridgeBP mIFridgeBP;

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final BuriedPointManager INSTANCE = new BuriedPointManager();

        private SingletonHolder() {
        }
    }

    private BuriedPointManager() {
        this.mIFragranceBP = new FragranceBPImpl();
        this.mIFridgeBP = new FridgeBPImpl();
    }

    public static BuriedPointManager get() {
        return SingletonHolder.INSTANCE;
    }

    public IFragranceBP getIFragranceBP() {
        return this.mIFragranceBP;
    }

    public IFridgeBP getIFridgeBP() {
        return this.mIFridgeBP;
    }
}
