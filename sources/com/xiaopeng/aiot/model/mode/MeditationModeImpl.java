package com.xiaopeng.aiot.model.mode;

import com.xiaopeng.aiot.device.fragrance.api.FragranceApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.model.sp.IFragranceMeditationSave;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class MeditationModeImpl implements IMode {
    private boolean isInMeditationModel = false;

    @Override // com.xiaopeng.aiot.model.mode.IMode
    public void enter() {
        if (this.isInMeditationModel) {
            LogUtils.i(LogConfig.TAG_MODE, "Already in meditation model,do nothing!");
            return;
        }
        this.isInMeditationModel = true;
        int channel = FragranceApi.getApi().loadData().getChannel();
        int meditationChannelId = getMeditationChannelId();
        LogUtils.i(LogConfig.TAG_MODE, "Enter meditation model ,current channel: " + channel + " channel from store[Meditation model]: " + meditationChannelId);
        saveNormalModeChannelId(channel);
        if (meditationChannelId != channel) {
            recoverChannel(meditationChannelId);
        }
    }

    @Override // com.xiaopeng.aiot.model.mode.IMode
    public void exit() {
        if (!this.isInMeditationModel) {
            LogUtils.i(LogConfig.TAG_MODE, "Already out meditation model,do nothing!");
            return;
        }
        this.isInMeditationModel = false;
        int channel = FragranceApi.getApi().loadData().getChannel();
        int normalModelChannelId = getNormalModelChannelId();
        LogUtils.i(LogConfig.TAG_MODE, "Leave meditation model ,current channel: " + channel + " channel from store[Normal model]: " + normalModelChannelId);
        saveMeditationChannelId(channel);
        if (normalModelChannelId != channel) {
            recoverChannel(normalModelChannelId);
        }
    }

    private void recoverChannel(int i) {
        LogUtils.i(LogConfig.TAG_MODE, "Recover channel: " + i);
        if (i == -1) {
            FragranceApi.getApi().setEnable(false);
        } else {
            FragranceApi.getApi().setChoiceChannel(i, false);
        }
    }

    private void saveNormalModeChannelId(int i) {
        SaveManager.get().getFragranceOutMeditationSave().saveChannelId(i);
    }

    private void saveMeditationChannelId(int i) {
        SaveManager.get().getFragranceMeditationSave().saveChannelId(i);
    }

    private int getNormalModelChannelId() {
        IFragranceMeditationSave fragranceOutMeditationSave = SaveManager.get().getFragranceOutMeditationSave();
        if (fragranceOutMeditationSave != null) {
            return fragranceOutMeditationSave.getChannelId();
        }
        return -1;
    }

    private int getMeditationChannelId() {
        IFragranceMeditationSave fragranceMeditationSave = SaveManager.get().getFragranceMeditationSave();
        if (fragranceMeditationSave != null) {
            return fragranceMeditationSave.getChannelId();
        }
        return -1;
    }
}
