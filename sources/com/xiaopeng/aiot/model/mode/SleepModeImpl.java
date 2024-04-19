package com.xiaopeng.aiot.model.mode;

import com.xiaopeng.aiot.device.fragrance.api.FragranceApi;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.model.sp.IFragranceSleepSpaceSave;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class SleepModeImpl implements IMode {
    private boolean isInSleepModel = false;

    @Override // com.xiaopeng.aiot.model.mode.IMode
    public void enter() {
        if (this.isInSleepModel) {
            LogUtils.i(LogConfig.TAG_MODE, "Already in sleep model,do nothing!");
            return;
        }
        this.isInSleepModel = true;
        FragranceDevice loadData = FragranceApi.getApi().loadData();
        int channel = loadData.getChannel();
        int sleepSpaceChannelId = getSleepSpaceChannelId();
        saveNormalModeChannelId(channel);
        if (sleepSpaceChannelId != channel) {
            recoverChannel(sleepSpaceChannelId);
        }
        int density = loadData.getDensity();
        int sleepSpaceDensity = getSleepSpaceDensity();
        saveNormalModeDensity(density);
        if (density != sleepSpaceDensity) {
            recoverDensity(sleepSpaceDensity);
        }
        LogUtils.i(LogConfig.TAG_MODE, String.format("Enter sleep model.currentChannel: %s currentDensity: %s, channelFromStore: %s densityFromStore: %s", Integer.valueOf(channel), Integer.valueOf(density), Integer.valueOf(sleepSpaceChannelId), Integer.valueOf(sleepSpaceDensity)));
    }

    @Override // com.xiaopeng.aiot.model.mode.IMode
    public void exit() {
        if (!this.isInSleepModel) {
            LogUtils.i(LogConfig.TAG_MODE, "Already out sleep model,do nothing!");
            return;
        }
        this.isInSleepModel = false;
        FragranceDevice loadData = FragranceApi.getApi().loadData();
        int channel = loadData.getChannel();
        int normalModelChannelId = getNormalModelChannelId();
        saveSleepSpaceChannelId(channel);
        if (normalModelChannelId != channel) {
            recoverChannel(normalModelChannelId);
        }
        int density = loadData.getDensity();
        int normalModelDensity = getNormalModelDensity();
        saveSleepSpaceDensity(density);
        if (density != normalModelDensity) {
            recoverDensity(normalModelDensity);
        }
        LogUtils.i(LogConfig.TAG_MODE, String.format("Leave sleep model.currentChannel: %s currentDensity: %s, channelFromStore: %s densityFromStore: %s", Integer.valueOf(channel), Integer.valueOf(density), Integer.valueOf(normalModelChannelId), Integer.valueOf(normalModelDensity)));
    }

    private void recoverChannel(int i) {
        LogUtils.i(LogConfig.TAG_MODE, "Recover channel: " + i);
        if (i == -1) {
            FragranceApi.getApi().setEnable(false);
        } else {
            FragranceApi.getApi().setChoiceChannel(i, false);
        }
    }

    private void recoverDensity(int i) {
        LogUtils.i(LogConfig.TAG_MODE, "Recover density: " + i);
        if (i == -1) {
            FragranceApi.getApi().setEnable(false);
        } else {
            FragranceApi.getApi().setDensity(i, false);
        }
    }

    private void saveNormalModeChannelId(int i) {
        SaveManager.get().getFragranceOutSleepSpaceSave().saveChannelId(i);
    }

    private int getNormalModelChannelId() {
        IFragranceSleepSpaceSave fragranceOutSleepSpaceSave = SaveManager.get().getFragranceOutSleepSpaceSave();
        if (fragranceOutSleepSpaceSave != null) {
            return fragranceOutSleepSpaceSave.getChannelId();
        }
        return -1;
    }

    private void saveNormalModeDensity(int i) {
        SaveManager.get().getFragranceOutSleepSpaceSave().saveDensity(i);
    }

    private int getNormalModelDensity() {
        IFragranceSleepSpaceSave fragranceOutSleepSpaceSave = SaveManager.get().getFragranceOutSleepSpaceSave();
        if (fragranceOutSleepSpaceSave != null) {
            return fragranceOutSleepSpaceSave.getDensity();
        }
        return -1;
    }

    private void saveSleepSpaceChannelId(int i) {
        SaveManager.get().getFragranceSleepSpaceSave().saveChannelId(i);
    }

    private int getSleepSpaceChannelId() {
        IFragranceSleepSpaceSave fragranceSleepSpaceSave = SaveManager.get().getFragranceSleepSpaceSave();
        if (fragranceSleepSpaceSave != null) {
            return fragranceSleepSpaceSave.getChannelId();
        }
        return -1;
    }

    private void saveSleepSpaceDensity(int i) {
        SaveManager.get().getFragranceSleepSpaceSave().saveDensity(i);
    }

    private int getSleepSpaceDensity() {
        IFragranceSleepSpaceSave fragranceSleepSpaceSave = SaveManager.get().getFragranceSleepSpaceSave();
        if (fragranceSleepSpaceSave != null) {
            return fragranceSleepSpaceSave.getDensity();
        }
        return -1;
    }
}
