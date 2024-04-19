package com.xiaopeng.iotlib.model.product;
/* loaded from: classes.dex */
public class ProductConfig implements IProductConfig {
    private IProductConfig mConfig;

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final ProductConfig INSTANCE = new ProductConfig();

        private SingletonHolder() {
        }
    }

    public static ProductConfig get() {
        return SingletonHolder.INSTANCE;
    }

    private ProductConfig() {
        this.mConfig = new ProductConfigImpl();
    }

    @Override // com.xiaopeng.iotlib.model.product.IProductConfig
    public boolean supportFragrance() {
        return this.mConfig.supportFragrance();
    }

    @Override // com.xiaopeng.iotlib.model.product.IProductConfig
    public boolean supportFridge() {
        return this.mConfig.supportFridge();
    }

    @Override // com.xiaopeng.iotlib.model.product.IProductConfig
    public boolean supportWatches() {
        return this.mConfig.supportWatches();
    }

    @Override // com.xiaopeng.iotlib.model.product.IProductConfig
    public boolean supportFragranceAutoOpen() {
        return this.mConfig.supportFragranceAutoOpen();
    }
}
