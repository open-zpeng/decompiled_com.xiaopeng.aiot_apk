package com.xiaopeng.aiot.device.fragrance.api;

import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.iotlib.model.api.IotApi;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.model.product.ProductConfig;
import com.xiaopeng.iotlib.model.sound.AssetsSoundSource;
import com.xiaopeng.iotlib.model.sound.SoundManager;
import com.xiaopeng.iotlib.model.sp.IFragranceSave;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.provider.iot.device.Fragrance;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
/* loaded from: classes.dex */
class FragranceApiXuiImpl extends IotApi<FragranceDevice> implements FragranceApi {
    private static final String TAG = "FragranceApi";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public String logTag() {
        return "FragranceApi";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FragranceApiXuiImpl() {
        super(Fragrance.DEVICE_TYPE, new String[]{"switch_state", Fragrance.PROP_ACTIVE_CHANNEL, Fragrance.PROP_CHANNEL_TYPES, Fragrance.PROP_CONCENTRATION});
    }

    @Override // com.xiaopeng.aiot.device.fragrance.api.FragranceApi
    public void setEnable(boolean z) {
        LogUtils.d("FragranceApi", " setEnable " + z);
        setDeviceProperties("switch_state", z ? "1" : "0");
    }

    @Override // com.xiaopeng.aiot.device.fragrance.api.FragranceApi
    public void setChoiceChannel(int i) {
        setChoiceChannel(i, true);
    }

    @Override // com.xiaopeng.aiot.device.fragrance.api.FragranceApi
    public void setChoiceChannel(final int i, boolean z) {
        String str;
        LogUtils.d("FragranceApi", " setChoiceChannel " + i);
        switch (i) {
            case 100:
                str = "0";
                break;
            case 101:
                str = "1";
                break;
            case 102:
                str = "2";
                break;
            default:
                str = null;
                break;
        }
        if (str != null) {
            if (setDeviceProperties(Fragrance.PROP_ACTIVE_CHANNEL, str)) {
                if (ProductConfig.get().supportFragranceAutoOpen()) {
                    setRawData("switch_state", "1");
                    if (ApiConfig.API_DEBUG) {
                        setEnable(true);
                    }
                } else {
                    setEnable(true);
                }
                if (z) {
                    ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.aiot.device.fragrance.api.-$$Lambda$FragranceApiXuiImpl$nIDgpgHHr7AGkdMUBARe45khteg
                        @Override // java.lang.Runnable
                        public final void run() {
                            FragranceApiXuiImpl.lambda$setChoiceChannel$0(i);
                        }
                    });
                    return;
                }
                return;
            }
            LogUtils.e("FragranceApi", " setChoiceChannel error  " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setChoiceChannel$0(int i) {
        IFragranceSave fragranceSave = SaveManager.get().getFragranceSave();
        if (fragranceSave != null) {
            fragranceSave.saveChannelId(i);
        }
    }

    @Override // com.xiaopeng.aiot.device.fragrance.api.FragranceApi
    public void setDensity(int i) {
        setDensity(i, true);
    }

    @Override // com.xiaopeng.aiot.device.fragrance.api.FragranceApi
    public void setDensity(final int i, boolean z) {
        LogUtils.d("FragranceApi", " setDensity " + i);
        if (setDeviceProperties(Fragrance.PROP_CONCENTRATION, i == 101 ? "2" : "1") && z) {
            ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.aiot.device.fragrance.api.-$$Lambda$FragranceApiXuiImpl$m8J7L1u1uUw6Zf1rc1tI7VsT3EU
                @Override // java.lang.Runnable
                public final void run() {
                    FragranceApiXuiImpl.lambda$setDensity$1(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setDensity$1(int i) {
        IFragranceSave fragranceSave = SaveManager.get().getFragranceSave();
        if (fragranceSave != null) {
            fragranceSave.saveDensity(i);
        }
    }

    @Override // com.xiaopeng.aiot.device.fragrance.api.FragranceApi
    public void playSoundEffect(int i) {
        if (i >= 0 && i < AssetsSoundSource.FRAGRANCE_SOUND.length) {
            SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_SOUND[i], 1);
        } else {
            SoundManager.get().stopMedia();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IotApi
    public synchronized FragranceDevice createData() {
        LogUtils.d("FragranceApi", " createData " + rawDataInfo() + "---" + Thread.currentThread());
        FragranceDevice fragranceDevice = new FragranceDevice();
        String rawValue = getRawValue(Fragrance.PROP_CONCENTRATION);
        if (rawValue == null) {
            fragranceDevice.setDensity(-1);
        } else if ("2".equals(rawValue)) {
            fragranceDevice.setDensity(101);
        } else if ("1".equals(rawValue)) {
            fragranceDevice.setDensity(100);
        } else {
            fragranceDevice.setDensity(-1);
        }
        int[] readChannelTypes = Fragrance.readChannelTypes(getRawValue(Fragrance.PROP_CHANNEL_TYPES));
        if (readChannelTypes != null && readChannelTypes.length != 0) {
            int[] iArr = new int[readChannelTypes.length];
            for (int i = 0; i < readChannelTypes.length; i++) {
                if (readChannelTypes[i] >= 1 && readChannelTypes[i] <= 15) {
                    iArr[i] = (readChannelTypes[i] - 1) + 101;
                    if (iArr[i] > 115) {
                        LogUtils.d("FragranceApi", "createFragrance types : " + readChannelTypes[i] + " ,i : " + i + ",chType:  " + iArr[i]);
                        iArr[i] = -1;
                    }
                } else {
                    iArr[i] = -1;
                }
            }
            fragranceDevice.setChannelType(iArr);
            String rawValue2 = getRawValue(Fragrance.PROP_ACTIVE_CHANNEL);
            String rawValue3 = getRawValue("switch_state");
            if (rawValue2 != null && "1".equals(rawValue3)) {
                int channelIndex = getChannelIndex(rawValue2);
                if (channelIndex < 0 || channelIndex >= iArr.length || iArr[channelIndex] == -1) {
                    fragranceDevice.setChannel(-1);
                } else if (channelIndex == 0) {
                    fragranceDevice.setChannel(100);
                } else if (channelIndex == 1) {
                    fragranceDevice.setChannel(101);
                } else if (channelIndex == 2) {
                    fragranceDevice.setChannel(102);
                } else {
                    fragranceDevice.setChannel(-1);
                }
                return fragranceDevice;
            }
            fragranceDevice.setChannel(-1);
            return fragranceDevice;
        }
        LogUtils.w("FragranceApi", "createFragrance types is null ");
        fragranceDevice.setChannelType(null);
        return fragranceDevice;
    }

    private synchronized int getChannelIndex(String str) {
        boolean z;
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    z = false;
                    break;
                }
                z = true;
                break;
            case 49:
                if (str.equals("1")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 50:
                if (str.equals("2")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            default:
                z = true;
                break;
        }
        if (z) {
            if (!z) {
                return !z ? -1 : 2;
            }
            return 1;
        }
        return 0;
    }
}
