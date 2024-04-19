package com.xiaopeng.aiot.device.watches.api;

import android.text.TextUtils;
import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.iotlib.model.api.IotApi;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.iot.device.Watches;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.Utils;
import org.json.JSONObject;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class WatchesApiImpl extends IotApi<WatchesDevice> implements WatchesApi {
    private static final int INTERVAL = 30000;
    private long mQuestTimeForBind;
    private long mQuestTimeForLocation;
    private long mQuestTimeForLocationUpdate;
    private long mQuestTimeForStatus;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public String logTag() {
        return LogConfig.TAG_DEVICE_API_WATCHES;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WatchesApiImpl() {
        super(Watches.DEVICE_TYPE, new String[]{Watches.PROP_BIND_STATUS, Watches.PROP_HEAD_PICTURE, Watches.PROP_NICKNAME, Watches.PROP_PHONE_NUMBER, "power", Watches.PROP_NETWORK_STATUS, "position"}, new String[]{Watches.PROP_GET_RESULT, Watches.PROP_BIND_STATUS});
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IotApi
    public WatchesDevice createData() {
        boolean z;
        LogUtils.d(logTag(), "======createData " + rawDataInfo(Watches.PROP_HEAD_PICTURE));
        if (getIotDevice() == null) {
            return null;
        }
        WatchesDevice watchesDevice = new WatchesDevice();
        String rawValue = getRawValue(Watches.PROP_BIND_STATUS);
        if (rawValue != null) {
            switch (rawValue.hashCode()) {
                case 48:
                    if (rawValue.equals("0")) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 49:
                    if (rawValue.equals("1")) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 50:
                    if (rawValue.equals("2")) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 1444:
                    if (rawValue.equals("-1")) {
                        z = false;
                        break;
                    }
                    z = true;
                    break;
                case 1389220:
                    if (rawValue.equals(Watches.VAL_BIND_STATUS_QUERY_FAIL)) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 1389221:
                    if (rawValue.equals(Watches.VAL_BIND_STATUS_ACCOUNT_LOG_OUT)) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                default:
                    z = true;
                    break;
            }
            if (!z) {
                watchesDevice.setState(-1);
            } else if (z) {
                watchesDevice.setState(0);
            } else if (z || z) {
                watchesDevice.setState(1);
            } else if (z) {
                watchesDevice.setState(WatchesDevice.STATUS_LOGOUT);
            } else if (z) {
                watchesDevice.setState(-100);
            }
        }
        watchesDevice.setPhoto(getRawValue(Watches.PROP_HEAD_PICTURE));
        watchesDevice.setName(getRawValue(Watches.PROP_NICKNAME));
        watchesDevice.setPhone(getRawValue(Watches.PROP_PHONE_NUMBER));
        String rawValue2 = getRawValue("power");
        if (!TextUtils.isEmpty(rawValue2)) {
            try {
                JSONObject jSONObject = new JSONObject(rawValue2);
                String string = jSONObject.getString("power");
                String string2 = jSONObject.getString("timestamp");
                watchesDevice.setPower(Utils.parse(string));
                watchesDevice.setPowerTime(string2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        watchesDevice.setNetworkState(Utils.parse(getRawValue(Watches.PROP_NETWORK_STATUS)));
        String rawValue3 = getRawValue("position");
        if (TextUtils.isEmpty(rawValue3)) {
            watchesDevice.setLatitude(0.0d);
            watchesDevice.setLongitude(0.0d);
            watchesDevice.setLocationTime(null);
            watchesDevice.setLocationDesc(null);
        } else if (!TextUtils.isEmpty(rawValue3)) {
            try {
                JSONObject jSONObject2 = new JSONObject(rawValue3);
                String string3 = jSONObject2.getString(Watches.KEY_POS_LATITUDE);
                String string4 = jSONObject2.getString(Watches.KEY_POS_LONGITUDE);
                String string5 = jSONObject2.getString("timestamp");
                String string6 = jSONObject2.getString(Watches.KEY_POS_DESCRIPTION);
                watchesDevice.setLatitude(Utils.parseDouble(string3));
                watchesDevice.setLongitude(Utils.parseDouble(string4));
                watchesDevice.setLocationTime(string5);
                watchesDevice.setLocationDesc(string6);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return watchesDevice;
    }

    @Override // com.xiaopeng.aiot.device.watches.api.WatchesApi
    public void questBind() {
        if (System.currentTimeMillis() - this.mQuestTimeForBind > 30000) {
            this.mQuestTimeForBind = System.currentTimeMillis();
            setDeviceProperties(Watches.PROP_BIND_STATUS, "1");
            return;
        }
        LogUtils.i(logTag(), "questBind failed Time is too short");
    }

    @Override // com.xiaopeng.aiot.device.watches.api.WatchesApi
    public void questStatus() {
        if (System.currentTimeMillis() - this.mQuestTimeForStatus > 30000) {
            this.mQuestTimeForStatus = System.currentTimeMillis();
            setDeviceProperties(Watches.PROP_ALL_STATUS, "1");
            return;
        }
        LogUtils.i(logTag(), "questStatus failed Time is too short");
    }

    @Override // com.xiaopeng.aiot.device.watches.api.WatchesApi
    public void questLocation() {
        if (System.currentTimeMillis() - this.mQuestTimeForLocation > 30000) {
            this.mQuestTimeForLocation = System.currentTimeMillis();
            setDeviceProperties("position", "1");
            return;
        }
        LogUtils.i(logTag(), "questLocation failed Time is too short");
    }

    @Override // com.xiaopeng.aiot.device.watches.api.WatchesApi
    public void questLocationUpdate() {
        if (System.currentTimeMillis() - this.mQuestTimeForLocationUpdate > 30000) {
            this.mQuestTimeForLocationUpdate = System.currentTimeMillis();
            setDeviceProperties("position", "2");
            return;
        }
        LogUtils.i(logTag(), "questLocationUpdate failed Time is too short");
    }

    @Override // com.xiaopeng.aiot.device.watches.api.WatchesApi
    public void questUserInfo() {
        LogUtils.d(logTag(), "query for user information of nickname, picture, phone number");
        setDeviceProperties(Watches.PROP_BIND_STATUS, "1");
    }
}
