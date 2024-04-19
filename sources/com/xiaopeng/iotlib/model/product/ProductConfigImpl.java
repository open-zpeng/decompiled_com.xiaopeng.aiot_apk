package com.xiaopeng.iotlib.model.product;

import android.car.Car;
import android.os.SystemProperties;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import java.util.HashMap;
/* loaded from: classes.dex */
class ProductConfigImpl implements IProductConfig {
    private static final String FRAGRANCE = "persist.sys.xiaopeng.SFS";
    private static final String FRAGRANCE_AUTO_OPEN = "fragrance.auto.open";
    private static final String FRIDGE = "persist.sys.xiaopeng.refrigerator";
    private static final String PROPERTY_NOT_SUPPORT = "0";
    private static final String PROPERTY_SUPPORT = "1";
    private static final String WATCHES = "watches";
    private HashMap<String, Boolean> sFeatureSupport = new HashMap<>();

    private boolean hasFeature(String str) {
        Boolean bool = this.sFeatureSupport.get(str);
        if (bool == null) {
            bool = Boolean.valueOf("1".equals(SystemProperties.get(str, "0")));
            this.sFeatureSupport.put(str, bool);
            if (bool.booleanValue()) {
                LogUtils.d("ProductConfig", "support feature:" + str);
            } else {
                LogUtils.i("ProductConfig", "not support feature: " + str);
            }
        }
        return bool.booleanValue();
    }

    private boolean hasFeatureInCar(String str) {
        Boolean bool = this.sFeatureSupport.get(str);
        if (bool == null) {
            String xpCduType = Car.getXpCduType();
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -547156470) {
                if (hashCode == 1125964221 && str.equals(WATCHES)) {
                    c = 1;
                }
            } else if (str.equals(FRAGRANCE_AUTO_OPEN)) {
                c = 2;
            }
            boolean z = false;
            if (c == 1) {
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q7) || xpCduType.equals(VuiUtils.CAR_PLATFORM_Q1)) {
                    z = true;
                }
                bool = Boolean.valueOf(z);
            } else if (c != 2) {
                bool = false;
            } else {
                bool = Boolean.valueOf(xpCduType.equals(VuiUtils.CAR_PLATFORM_Q3));
            }
            this.sFeatureSupport.put(str, bool);
            if (bool.booleanValue()) {
                LogUtils.d("ProductConfig", "support feature:" + str);
            } else {
                LogUtils.i("ProductConfig", "not support feature: " + str);
            }
        }
        return bool.booleanValue();
    }

    @Override // com.xiaopeng.iotlib.model.product.IProductConfig
    public boolean supportFragrance() {
        return hasFeature(FRAGRANCE);
    }

    @Override // com.xiaopeng.iotlib.model.product.IProductConfig
    public boolean supportFridge() {
        return hasFeature(FRIDGE);
    }

    @Override // com.xiaopeng.iotlib.model.product.IProductConfig
    public boolean supportWatches() {
        return hasFeatureInCar(WATCHES);
    }

    @Override // com.xiaopeng.iotlib.model.product.IProductConfig
    public boolean supportFragranceAutoOpen() {
        return hasFeatureInCar(FRAGRANCE_AUTO_OPEN);
    }
}
