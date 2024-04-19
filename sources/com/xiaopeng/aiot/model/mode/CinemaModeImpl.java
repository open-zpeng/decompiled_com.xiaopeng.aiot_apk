package com.xiaopeng.aiot.model.mode;

import com.xiaopeng.aiot.device.fragrance.api.FragranceApi;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
class CinemaModeImpl implements IMode, IDeviceApi.OnDataChangeListener<FragranceDevice> {
    private int mChannelId;
    private int mDensity;
    private boolean mEnter;
    private boolean mHelpChangeDensity;
    private boolean mOpen;

    @Override // com.xiaopeng.aiot.model.mode.IMode
    public void enter() {
        FragranceApi api = FragranceApi.getApi();
        FragranceDevice loadData = api.loadData();
        this.mOpen = FragranceDevice.isOpen(loadData);
        this.mChannelId = loadData.getChannel();
        if (this.mChannelId == -1) {
            this.mChannelId = SaveManager.get().getFragranceSave().getChannelId();
        }
        this.mDensity = loadData.getDensity();
        log(String.format("enter : open:%s, channelId:%s, density:%s , data:%s", Boolean.valueOf(this.mOpen), Integer.valueOf(this.mChannelId), Integer.valueOf(this.mDensity), loadData));
        if (this.mOpen) {
            FragranceApi.getApi().setDensity(101);
        }
        this.mHelpChangeDensity = false;
        api.setEnable(false);
        api.addOnDataChangeListener(this);
        this.mEnter = true;
    }

    @Override // com.xiaopeng.aiot.model.mode.IMode
    public synchronized void exit() {
        if (this.mEnter) {
            FragranceApi api = FragranceApi.getApi();
            api.removeOnDataChangeListener(this);
            FragranceDevice loadData = api.loadData();
            log(String.format("exit : open:%s, channelId:%s, density:%s , data:%s", Boolean.valueOf(this.mOpen), Integer.valueOf(this.mChannelId), Integer.valueOf(this.mDensity), loadData));
            if (loadData == null) {
                return;
            }
            if (FragranceDevice.isOpen(loadData) != this.mOpen) {
                if (this.mOpen) {
                    boolean recoveryChannel = this.mChannelId != -1 ? recoveryChannel(this.mChannelId, loadData) : false;
                    if (!recoveryChannel) {
                        recoveryChannel = open(loadData);
                    }
                    if (recoveryChannel && this.mDensity != loadData.getDensity()) {
                        api.setDensity(this.mDensity);
                    }
                } else {
                    if (this.mChannelId != -1 && this.mChannelId != loadData.getChannel()) {
                        recoveryChannel(this.mChannelId, loadData);
                    }
                    if (this.mDensity != loadData.getDensity()) {
                        api.setDensity(this.mDensity);
                    }
                    api.setEnable(false);
                }
            } else if (this.mOpen) {
                if (this.mChannelId != -1 && this.mChannelId != loadData.getChannel()) {
                    recoveryChannel(this.mChannelId, loadData);
                }
                if (this.mDensity != loadData.getChannel()) {
                    api.setDensity(this.mDensity);
                }
            } else {
                boolean recoveryChannel2 = this.mChannelId != -1 ? recoveryChannel(this.mChannelId, loadData) : false;
                if (!recoveryChannel2) {
                    recoveryChannel2 = open(loadData);
                }
                if (recoveryChannel2 && this.mDensity != loadData.getDensity()) {
                    api.setDensity(this.mDensity);
                }
                api.setEnable(false);
            }
            this.mEnter = false;
        }
    }

    private boolean recoveryChannel(int i, FragranceDevice fragranceDevice) {
        if (i == -1) {
            return false;
        }
        int channelIndex = FragranceDevice.channelIndex(i);
        int[] channelType = fragranceDevice.getChannelType();
        if (channelType != null && channelIndex < channelType.length && FragranceDevice.validType(channelType[channelIndex])) {
            FragranceApi.getApi().setChoiceChannel(i);
            return true;
        }
        log("will open but no bottle in channelId : " + i);
        return false;
    }

    private boolean open(FragranceDevice fragranceDevice) {
        int findInsertChannel = findInsertChannel(fragranceDevice.getChannelType());
        if (findInsertChannel != -1) {
            FragranceApi.getApi().setChoiceChannel(findInsertChannel);
            return true;
        }
        return false;
    }

    private int findInsertChannel(int[] iArr) {
        if (iArr == null) {
            return -1;
        }
        for (int i = 0; i < iArr.length; i++) {
            if (FragranceDevice.validType(iArr[i])) {
                return FragranceDevice.channelId(i);
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onDataChanged(FragranceDevice fragranceDevice) {
        if (!this.mHelpChangeDensity && !this.mOpen && this.mDensity == 100 && fragranceDevice.getDensity() == 100 && FragranceDevice.isOpen(fragranceDevice)) {
            log("help aqy changed density to high");
            FragranceApi.getApi().setDensity(101);
            this.mHelpChangeDensity = true;
        }
    }

    private void log(String str) {
        LogUtils.i("CinemaMode", str, 1);
    }
}
