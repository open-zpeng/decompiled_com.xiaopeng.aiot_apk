package com.xiaopeng.aiot.device.fragrance.data;

import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.iotlib.data.DeviceInfo;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.Arrays;
/* loaded from: classes.dex */
public class FragranceDevice extends DeviceInfo {
    public static final int CHANNEL_1 = 100;
    public static final int CHANNEL_2 = 101;
    public static final int CHANNEL_3 = 102;
    public static final int CHANNEL_COUNT = 3;
    public static final int CHANNEL_FIRST = 100;
    public static final int CHANNEL_LAST = 102;
    public static final int CHANNEL_NONE = -1;
    public static final int DENSITY_CLOSE = -1;
    public static final int DENSITY_HIGH = 101;
    public static final int DENSITY_LOW = 100;
    public static final int TYPE_COUNT = 15;
    public static final int TYPE_FIRST = 101;
    public static final int TYPE_LAST = 115;
    public static final int TYPE_NULL = -1;
    private int[] channelType;
    public static final int[] Clement_Gavarry = {104};
    public static final int[] Hamid_Merati_Kashani = {105};
    public static final int[] Florian_Gallo = {106, 107};
    public static final int[] Barbara_Zoebelein = {108, 109, 110, 111};
    public static final int[] TYPE_P0 = {102, 105};
    public static final int[] TYPE_SUPER_01 = {104, 105, 106};
    public static final int[] TYPE_SUPER_02 = {104, 105, 107};
    private int density = 100;
    private int channel = -1;

    public static int channelId(int i) {
        return i + 100;
    }

    public static int channelIndex(int i) {
        return i - 100;
    }

    public static int typeIndex(int i) {
        return i + WatchesDevice.STATUS_LOGOUT;
    }

    public static boolean validType(int i) {
        return i >= 101 && i <= 115;
    }

    public int getDensity() {
        return this.density;
    }

    public void setDensity(int i) {
        this.density = i;
    }

    public int[] getChannelType() {
        return this.channelType;
    }

    public void setChannelType(int[] iArr) {
        this.channelType = iArr;
    }

    public int getChannel() {
        return this.channel;
    }

    public void setChannel(int i) {
        this.channel = i;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String toString() {
        return "FragranceDevice{density=" + this.density + ", channel=" + this.channel + ", channelType=" + Arrays.toString(this.channelType) + '}';
    }

    public void reset() {
        this.density = -1;
        this.channel = -1;
        this.channelType = null;
    }

    public static boolean isClementGavarry(int i) {
        for (int i2 : Clement_Gavarry) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHamidMeratiKashani(int i) {
        for (int i2 : Hamid_Merati_Kashani) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFlorianGallo(int i) {
        for (int i2 : Florian_Gallo) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBarbaraZoebelein(int i) {
        for (int i2 : Barbara_Zoebelein) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    public static int activeType(FragranceDevice fragranceDevice) {
        int[] channelType = fragranceDevice.getChannelType();
        int channelIndex = channelIndex(fragranceDevice.getChannel());
        if (channelType == null || channelIndex < 0 || channelIndex >= channelType.length) {
            return -1;
        }
        return channelType[channelIndex];
    }

    public static boolean isOpen(FragranceDevice fragranceDevice) {
        int channel = fragranceDevice.getChannel();
        return channel == 100 || channel == 101 || channel == 102;
    }

    public static boolean hasBottle(FragranceDevice fragranceDevice) {
        int[] channelType;
        if (fragranceDevice == null || (channelType = fragranceDevice.getChannelType()) == null) {
            return false;
        }
        for (int i : channelType) {
            if (validType(i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSuper(int i) {
        return isClementGavarry(i) || isHamidMeratiKashani(i) || isFlorianGallo(i) || isBarbaraZoebelein(i);
    }

    public static void channelTypesProcess(int[] iArr, int[] iArr2) {
        for (int i = 0; i < iArr2.length; i++) {
            iArr2[i] = -1;
        }
        if (iArr == null) {
            LogUtils.i("data", "channelTypesProcess typesNew  " + Arrays.toString(iArr2));
            return;
        }
        for (int i2 = 0; i2 < iArr.length && i2 < iArr2.length; i2++) {
            iArr2[i2] = iArr[i2];
        }
        LogUtils.i("data", "channelTypesProcess inTypes: " + Arrays.toString(iArr) + ", outTypes: " + Arrays.toString(iArr2));
    }
}
