package com.xiaopeng.aiot.model.speech;

import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.aiot.model.scene.WatchesScene;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.iot.device.Watches;
import com.xiaopeng.iotlib.provider.map.MapUtils;
import com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent;
import com.xiaopeng.iotlib.provider.voice.command.SpeechManager;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.lib.apirouter.ClientConstants;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
class WatchSpeech implements ISpeechEvent {
    static final String BIND = "is.bind.childwatch";
    static final String INFO = "get.watch.phone.info";
    static final String LOCATION = "get.childwatch.location";
    static final String MARK = "is.watch.location.opened";

    /* JADX INFO: Access modifiers changed from: package-private */
    public WatchSpeech() {
        WatchesScene.get().init();
    }

    @Override // com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent
    public void onEvent(String str, String str2) {
        LogUtils.i(LogConfig.TAG_SPEECH, "WatchSpeech onEvent not work " + str);
    }

    @Override // com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent
    public void onQuery(String str, String str2, String str3) {
        Object obj;
        LogUtils.i(LogConfig.TAG_SPEECH, "WatchSpeech onQuery event " + str + " , data " + str2 + " ,callback: " + str3);
        if (BIND.equals(str)) {
            obj = Boolean.valueOf(bind());
        } else if (LOCATION.equals(str)) {
            obj = location();
        } else if (MARK.equals(str)) {
            obj = Boolean.valueOf(mark());
        } else if (INFO.equals(str)) {
            obj = info();
        } else {
            LogUtils.w(LogConfig.TAG_SPEECH, "WatchSpeech onQuery illegal event " + str);
            obj = null;
        }
        LogUtils.i(LogConfig.TAG_SPEECH, str + " : " + obj);
        SpeechManager.get().postQueryResult(str, str3, obj);
    }

    private String info() {
        WatchesDevice watchesDevice = WatchesScene.get().getWatchesDevice();
        if (watchesDevice == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Watches.PROP_PHONE_NUMBER, watchesDevice.getPhone());
            jSONObject.put(ClientConstants.ALIAS.P_NAME, watchesDevice.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private boolean mark() {
        return WatchesScene.get().isMarkEnable();
    }

    private String location() {
        WatchesDevice watchesDevice = WatchesScene.get().getWatchesDevice();
        if (watchesDevice == null || watchesDevice.getLocationTime() == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            double[] bg09_to_gcj02 = MapUtils.bg09_to_gcj02(watchesDevice.getLongitude(), watchesDevice.getLatitude());
            jSONObject.put(Watches.KEY_POS_LATITUDE, String.valueOf(bg09_to_gcj02[1]));
            jSONObject.put("lon", String.valueOf(bg09_to_gcj02[0]));
            jSONObject.put("time", watchesDevice.getLocationTime());
            jSONObject.put(Watches.KEY_POS_DESCRIPTION, watchesDevice.getLocationDesc());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private boolean bind() {
        WatchesDevice watchesDevice = WatchesScene.get().getWatchesDevice();
        return watchesDevice != null && watchesDevice.getState() == 1;
    }
}
