package com.xiaopeng.iotlib.model.sound;

import java.util.ArrayList;
/* loaded from: classes.dex */
public class AssetsSoundSource {
    public static final String FRIDGE_POWER_OFF = "fridge/power_off.wav";
    public static final String FRIDGE_POWER_ON = "fridge/power_on.wav";
    public static final String FRIDGE_TEMPERATURE_SIX = "fridge/temperature_six.wav";
    public static final String FRIDGE_TEMPERATURE_SUBZERO_SIX = "fridge/temperature_subzero_six.wav";
    public static final String FRIDGE_TEMPERATURE_ZERO = "fridge/temperature_zero.wav";
    private static final ArrayList<String> mSourceCollections = new ArrayList<>();
    public static final String FRAGRANCE_QILI = "fragrance/qili.ogg";
    public static final String FRAGRANCE_KUNLUN = "fragrance/kunlun.ogg";
    public static final String FRAGRANCE_DIANLAN = "fragrance/dianlan.ogg";
    public static final String FRAGRANCE_XINGYUN = "fragrance/xingyun.ogg";
    public static final String FRAGRANCE_QIANXING = "fragrance/qianxing.ogg";
    public static final String FRAGRANCE_XINGJI = "fragrance/xingji.ogg";
    public static final String FRAGRANCE_SHIGUANG = "fragrance/shiguang.ogg";
    public static final String FRAGRANCE_HUPO = "fragrance/hupo.ogg";
    public static final String FRAGRANCE_XIANGSHAN = "fragrance/xiangshan.ogg";
    public static final String FRAGRANCE_ZIYOU = "fragrance/ziyou.ogg";
    public static final String FRAGRANCE_BAISE = "fragrance/baise.ogg";
    public static final String FRAGRANCE_XIAOPENG = "fragrance/xiaopeng.ogg";
    public static final String[] FRAGRANCE_SOUND = {FRAGRANCE_QILI, FRAGRANCE_KUNLUN, FRAGRANCE_DIANLAN, FRAGRANCE_XINGYUN, FRAGRANCE_QIANXING, FRAGRANCE_XINGJI, FRAGRANCE_SHIGUANG, FRAGRANCE_HUPO, FRAGRANCE_XIANGSHAN, FRAGRANCE_ZIYOU, FRAGRANCE_BAISE, FRAGRANCE_XIAOPENG, FRAGRANCE_XIAOPENG, FRAGRANCE_XIAOPENG, FRAGRANCE_XIAOPENG, FRAGRANCE_XIAOPENG};

    public static ArrayList<String> getAssetsSoundRes() {
        if (mSourceCollections.isEmpty()) {
            mSourceCollections.add(FRIDGE_POWER_OFF);
            mSourceCollections.add(FRIDGE_POWER_ON);
            mSourceCollections.add(FRIDGE_TEMPERATURE_SIX);
            mSourceCollections.add(FRIDGE_TEMPERATURE_ZERO);
            mSourceCollections.add(FRIDGE_TEMPERATURE_SUBZERO_SIX);
            mSourceCollections.add(FRAGRANCE_DIANLAN);
            mSourceCollections.add(FRAGRANCE_KUNLUN);
            mSourceCollections.add(FRAGRANCE_QILI);
            mSourceCollections.add(FRAGRANCE_QIANXING);
            mSourceCollections.add(FRAGRANCE_SHIGUANG);
            mSourceCollections.add(FRAGRANCE_XINGJI);
            mSourceCollections.add(FRAGRANCE_XINGYUN);
        }
        return mSourceCollections;
    }
}
