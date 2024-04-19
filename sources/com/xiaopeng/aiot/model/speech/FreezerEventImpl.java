package com.xiaopeng.aiot.model.speech;

import com.xiaopeng.aiot.device.freezer.api.FreezerApi;
import com.xiaopeng.aiot.device.freezer.data.FreezerDevice;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class FreezerEventImpl implements FreezerEvent {
    private FreezerDevice loadData() {
        return FreezerApi.getApi().loadData();
    }

    private boolean hasDevice() {
        if (loadData() == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "FreezerDevice is null");
            return false;
        }
        return true;
    }

    @Override // com.xiaopeng.aiot.model.speech.FreezerEvent
    public int isSupportFreezer() {
        if (hasDevice()) {
            LogUtils.d(LogConfig.TAG_SPEECH, "FreezerDevice is supported");
            return 1;
        }
        return 0;
    }

    @Override // com.xiaopeng.aiot.model.speech.FreezerEvent
    public int getPower() {
        if (!hasDevice()) {
            LogUtils.i(LogConfig.TAG_SPEECH, "getPower: no device");
            return 0;
        }
        boolean isPowerOpen = FreezerApi.getApi().isPowerOpen();
        LogUtils.d(LogConfig.TAG_SPEECH, "getPower: isOn? " + isPowerOpen);
        return isPowerOpen ? 1 : 0;
    }

    @Override // com.xiaopeng.aiot.model.speech.FreezerEvent
    public int getDoor() {
        if (!hasDevice()) {
            LogUtils.i(LogConfig.TAG_SPEECH, "getDoor: no device");
            return 0;
        }
        boolean isDoorOpen = FreezerApi.getApi().isDoorOpen();
        LogUtils.d(LogConfig.TAG_SPEECH, "getDoor: isOpen? " + isDoorOpen);
        return isDoorOpen ? 1 : 0;
    }

    @Override // com.xiaopeng.aiot.model.speech.FreezerEvent
    public int getLock() {
        if (!hasDevice()) {
            LogUtils.i(LogConfig.TAG_SPEECH, "getLock: no device");
            return 0;
        }
        boolean isChildLock = FreezerApi.getApi().isChildLock();
        LogUtils.d(LogConfig.TAG_SPEECH, "getLock: isOpen? " + isChildLock);
        return isChildLock ? 1 : 0;
    }
}
