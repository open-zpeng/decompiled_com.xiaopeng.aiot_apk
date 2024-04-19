package com.xiaopeng.aiot.model.speech;

import com.xiaopeng.aiot.device.fragrance.api.FragranceApi;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.aiot.model.buriedpoint.DataLogManager;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.model.sp.IFragranceSave;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.Arrays;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class FragranceEventImpl implements FragranceEvent {
    private FragranceDevice loadData() {
        return FragranceApi.getApi().loadData();
    }

    private int[] channelTypesProcess(FragranceDevice fragranceDevice) {
        int[] channelType = fragranceDevice.getChannelType();
        if (channelType == null) {
            int[] iArr = new int[3];
            for (int i = 0; i < iArr.length; i++) {
                iArr[i] = -1;
            }
            LogUtils.i(LogConfig.TAG_SPEECH, "loadData typesNew  " + Arrays.toString(iArr));
            return iArr;
        } else if (channelType.length < 3) {
            int[] iArr2 = new int[3];
            for (int i2 = 0; i2 < iArr2.length; i2++) {
                iArr2[i2] = -1;
            }
            System.arraycopy(channelType, 0, iArr2, 0, channelType.length);
            LogUtils.i(LogConfig.TAG_SPEECH, "loadData typesNew  " + Arrays.toString(iArr2) + ", types" + Arrays.toString(channelType));
            return iArr2;
        } else {
            return channelType;
        }
    }

    private int getRecordChannelId() {
        IFragranceSave fragranceSave = SaveManager.get().getFragranceSave();
        if (fragranceSave != null) {
            return fragranceSave.getChannelId();
        }
        return -1;
    }

    @Override // com.xiaopeng.aiot.model.speech.FragranceEvent
    public int openFuzzy() {
        FragranceDevice loadData = loadData();
        if (loadData == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "FragranceDevice is null");
            return 0;
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "openFuzzy " + loadData.toString());
        if (isOpen(loadData)) {
            return 100;
        }
        int recordChannelId = getRecordChannelId();
        LogUtils.i(LogConfig.TAG_SPEECH, "openFuzzy recordChannelId " + recordChannelId);
        int[] channelTypesProcess = channelTypesProcess(loadData);
        if (recordChannelId == -1) {
            return openFuzzyNoRecord(channelTypesProcess);
        }
        return openFuzzyInRecord(channelTypesProcess, recordChannelId);
    }

    private int openFuzzyNoRecord(int[] iArr) {
        LogUtils.i(LogConfig.TAG_SPEECH, "openFuzzyNoRecord channels  " + Arrays.toString(iArr));
        int findInsertChannel = findInsertChannel(iArr);
        LogUtils.i(LogConfig.TAG_SPEECH, "openFuzzyNoRecord channelId  " + findInsertChannel);
        if (findInsertChannel != -1) {
            FragranceApi.getApi().setChoiceChannel(findInsertChannel);
            buriedPointOpen(iArr[FragranceDevice.channelIndex(findInsertChannel)], 2);
            return 101;
        }
        return 102;
    }

    private int openFuzzyInRecord(int[] iArr, int i) {
        int channelIndex = FragranceDevice.channelIndex(i);
        LogUtils.i(LogConfig.TAG_SPEECH, "openFuzzyInRecord channels  " + Arrays.toString(iArr) + ", index " + channelIndex);
        if (channelIndex >= 0 && channelIndex < iArr.length) {
            if (FragranceDevice.validType(iArr[channelIndex])) {
                FragranceApi.getApi().setChoiceChannel(i);
                buriedPointOpen(iArr[channelIndex], 2);
                return 101;
            }
            return openFuzzyNoRecord(iArr);
        }
        return openFuzzyNoRecord(iArr);
    }

    @Override // com.xiaopeng.aiot.model.speech.FragranceEvent
    public int openToType(int i) {
        FragranceDevice loadData = loadData();
        if (loadData == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "openToType device is null");
            return 0;
        }
        int[] channelTypesProcess = channelTypesProcess(loadData);
        LogUtils.i(LogConfig.TAG_SPEECH, "openToName " + loadData.toString() + " ,type " + i);
        if (isOpen(loadData)) {
            return openToTypeInOpened(loadData, channelTypesProcess, i);
        }
        return openToTypeInClosed(channelTypesProcess, i);
    }

    private int openToTypeInOpened(FragranceDevice fragranceDevice, int[] iArr, int i) {
        if (FragranceDevice.activeType(fragranceDevice) == i) {
            return 202;
        }
        int findTypeChannel = findTypeChannel(iArr, i);
        if (findTypeChannel == -1) {
            return 102;
        }
        FragranceApi.getApi().setChoiceChannel(findTypeChannel);
        buriedPointOpen(i, 2);
        return 201;
    }

    private int openToTypeInClosed(int[] iArr, int i) {
        int findTypeChannel = findTypeChannel(iArr, i);
        if (findTypeChannel == -1) {
            return 102;
        }
        FragranceApi.getApi().setChoiceChannel(findTypeChannel);
        buriedPointOpen(i, 2);
        return 101;
    }

    private int findInsertChannel(int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            if (FragranceDevice.validType(iArr[i])) {
                return FragranceDevice.channelId(i);
            }
        }
        return -1;
    }

    private int findTypeChannel(int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] == i) {
                return FragranceDevice.channelId(i2);
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.aiot.model.speech.FragranceEvent
    public int openToPosition(int i) {
        FragranceDevice loadData = loadData();
        if (loadData == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "FragranceDevice is null");
            return 0;
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "openToPosition " + loadData.toString() + " , position " + i);
        int[] channelTypesProcess = channelTypesProcess(loadData);
        if (i < 0 || i >= channelTypesProcess.length) {
            return 0;
        }
        int i2 = channelTypesProcess[i];
        int channelId = FragranceDevice.channelId(i);
        int channel = loadData.getChannel();
        LogUtils.i(LogConfig.TAG_SPEECH, "openToPosition targetChannelId : " + channelId + " , curChannelId " + channel + " ,targetType  " + i2);
        if (FragranceDevice.validType(i2)) {
            if (channel == channelId) {
                return 100;
            }
            FragranceApi.getApi().setChoiceChannel(channelId);
            buriedPointOpen(i2, 2);
            return isOpen(loadData) ? 201 : 101;
        }
        return 102;
    }

    @Override // com.xiaopeng.aiot.model.speech.FragranceEvent
    public int next() {
        FragranceDevice loadData = loadData();
        if (loadData == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "FragranceDevice is null");
            return 0;
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "next " + loadData.toString());
        if (!isOpen(loadData)) {
            return 204;
        }
        int[] channelTypesProcess = channelTypesProcess(loadData);
        int channel = loadData.getChannel();
        int channelIndex = FragranceDevice.channelIndex(channel);
        LogUtils.i(LogConfig.TAG_SPEECH, "next curChannelId " + channel + " ,curIndex " + channelIndex + ",types " + Arrays.toString(channelTypesProcess));
        if (channelIndex < 0 || channelIndex >= channelTypesProcess.length) {
            return 0;
        }
        int i = channelTypesProcess[channelIndex];
        int i2 = channelIndex;
        while (true) {
            i2++;
            if (i2 >= channelTypesProcess.length) {
                i2 = 0;
            }
            LogUtils.i(LogConfig.TAG_SPEECH, "next nextIndex " + i2);
            if (i2 == channelIndex) {
                return 203;
            }
            int i3 = channelTypesProcess[i2];
            LogUtils.i(LogConfig.TAG_SPEECH, "next nextType " + i3 + " ,curType " + i);
            if (FragranceDevice.validType(i3) && i3 != i) {
                FragranceApi.getApi().setChoiceChannel(FragranceDevice.channelId(i2));
                buriedPointOpen(i3, 2);
                return 201;
            }
        }
    }

    @Override // com.xiaopeng.aiot.model.speech.FragranceEvent
    public int changeConHigh() {
        FragranceDevice loadData = loadData();
        if (loadData == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "FragranceDevice is null");
            return 0;
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "changeConHigh " + loadData.toString());
        if (FragranceDevice.hasBottle(loadData)) {
            if (isOpen(loadData)) {
                if (loadData.getDensity() == 101) {
                    return 102;
                }
                FragranceApi.getApi().setDensity(101);
                return 100;
            }
            return 104;
        }
        return 105;
    }

    @Override // com.xiaopeng.aiot.model.speech.FragranceEvent
    public int changeConLow() {
        FragranceDevice loadData = loadData();
        if (loadData == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "FragranceDevice is null");
            return 0;
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "changeConLow " + loadData.toString());
        if (FragranceDevice.hasBottle(loadData)) {
            if (isOpen(loadData)) {
                if (loadData.getDensity() == 100) {
                    return 103;
                }
                FragranceApi.getApi().setDensity(100);
                return 101;
            }
            return 104;
        }
        return 105;
    }

    @Override // com.xiaopeng.aiot.model.speech.FragranceEvent
    public int close() {
        FragranceDevice loadData = loadData();
        if (loadData == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "FragranceDevice is null");
            return 0;
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "close " + loadData.toString());
        if (FragranceDevice.hasBottle(loadData)) {
            if (isOpen(loadData)) {
                FragranceApi.getApi().setEnable(false);
                buriedPointClose(FragranceDevice.activeType(loadData), 2);
                return 100;
            }
            return 101;
        }
        return 103;
    }

    private boolean isOpen(FragranceDevice fragranceDevice) {
        return FragranceDevice.isOpen(fragranceDevice);
    }

    private void buriedPointOpen(int i, int i2) {
        DataLogManager.get().getIFragranceDL().open(FragranceDevice.typeIndex(i) + 1, i2);
    }

    private void buriedPointClose(int i, int i2) {
        if (FragranceDevice.validType(i)) {
            DataLogManager.get().getIFragranceDL().close(FragranceDevice.typeIndex(i) + 1, i2);
        }
    }
}
