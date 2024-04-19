package com.xiaopeng.aiot.model.scene;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.ArraySet;
import com.xiaopeng.aiot.device.watches.api.WatchesApi;
import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.provider.account.AccountCallBack;
import com.xiaopeng.iotlib.provider.account.AccountHelper;
import com.xiaopeng.iotlib.provider.iot.device.Watches;
import com.xiaopeng.iotlib.provider.map.MapUtils;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.Utils;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class WatchesScene implements IDeviceApi.OnDataChangeListener<WatchesDevice>, AccountCallBack {
    private static final int INTERVAL = 300000;
    private static final String TAG = "watches_scene";
    private ArraySet<CallBack> mCallBacks;
    private boolean mIsLogin;
    private BroadcastReceiver mMapReceiver;
    private boolean mMarkEnable;
    private long mQuestTimeForLocation;
    private WatchesDevice mWatchesDevice;

    /* loaded from: classes.dex */
    public interface CallBack {
        void onAccountSwitch();

        void onDeviceChanged(WatchesDevice watchesDevice);

        void onLoginChanged(boolean z);

        void onResult(int i);
    }

    private WatchesScene() {
        this.mCallBacks = new ArraySet<>();
        this.mMapReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.aiot.model.scene.WatchesScene.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                LogUtils.i(WatchesScene.TAG, "map restart check mark " + WatchesScene.this.mMarkEnable);
                WatchesScene.this.markToMap();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final WatchesScene INSTANCE = new WatchesScene();

        private SingletonHolder() {
        }
    }

    public static WatchesScene get() {
        return SingletonHolder.INSTANCE;
    }

    public synchronized void init() {
        LogUtils.d(TAG, "init ");
        AccountHelper.get().addCallBack(this);
        WatchesApi.getApi().addOnDataChangeListener(this);
        this.mIsLogin = AccountHelper.get().isLogin();
        setWatchesDevice(loadWatchesDevice());
        this.mMarkEnable = SaveManager.get().getWatchesSave().isMarkEnable();
        LogUtils.d(TAG, String.format("init mIsLogin:%s  mMarkEnable:%s  mMarkEnable:%s", Boolean.valueOf(this.mIsLogin), Boolean.valueOf(this.mMarkEnable), this.mWatchesDevice));
        if (this.mMarkEnable) {
            markToMap();
            TimeWatchMarkReceiver.timing();
        } else {
            TimeWatchMarkReceiver.cancel();
        }
        Iot.getApp().registerReceiver(this.mMapReceiver, new IntentFilter(MapUtils.ACTION));
    }

    public synchronized boolean isMarkEnable() {
        return this.mMarkEnable;
    }

    public synchronized void setMarkEnable(boolean z) {
        LogUtils.i(TAG, " ,mMarkEnable : " + this.mMarkEnable + ", enable : " + z);
        if (z == this.mMarkEnable) {
            return;
        }
        SaveManager.get().getWatchesSave().saveMarkEnable(z);
        this.mMarkEnable = z;
        if (z) {
            markToMap();
            TimeWatchMarkReceiver.timing();
        } else {
            clearMarkToMap();
            TimeWatchMarkReceiver.cancel();
        }
    }

    public boolean isLogin() {
        return this.mIsLogin;
    }

    public WatchesDevice loadWatchesDevice() {
        LogUtils.i(TAG, "loadWatchesDevice");
        WatchesDevice loadData = WatchesApi.getApi().loadData();
        if (hasDevice(loadData)) {
            WatchesApi.getApi().questStatus();
            WatchesApi.getApi().questLocation();
            WatchesApi.getApi().questLocationUpdate();
            WatchesApi.getApi().questUserInfo();
        } else {
            WatchesApi.getApi().questBind();
        }
        return loadData;
    }

    private boolean hasDevice(WatchesDevice watchesDevice) {
        return isLogin() && watchesDevice != null && watchesDevice.getState() == 1;
    }

    public void questLocation() {
        if (System.currentTimeMillis() - this.mQuestTimeForLocation > 300000) {
            this.mQuestTimeForLocation = System.currentTimeMillis();
            if (hasDevice(getWatchesDevice())) {
                WatchesApi.getApi().questLocationUpdate();
                return;
            }
            return;
        }
        LogUtils.i(TAG, "questLocation failed Time is too short");
    }

    public void navigation() {
        WatchesDevice watchesDevice = getWatchesDevice();
        if (watchesDevice == null || watchesDevice.getState() != 1 || watchesDevice.getLocationTime() == null) {
            return;
        }
        MapUtils.navigation(watchesDevice.getLatitude(), watchesDevice.getLongitude());
    }

    public void addCallBack(CallBack callBack) {
        if (callBack != null) {
            LogUtils.d(TAG, "addCallBack " + callBack);
            this.mCallBacks.add(callBack);
        }
    }

    public void removeCallBack(CallBack callBack) {
        if (callBack != null) {
            LogUtils.d(TAG, "removeCallBack " + callBack);
            this.mCallBacks.remove(callBack);
        }
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onDataChanged(WatchesDevice watchesDevice) {
        LogUtils.i(TAG, "onDataChanged  ");
        if (watchesDevice != null && watchesDevice.getState() != 1) {
            setMarkEnable(false);
        }
        setWatchesDevice(watchesDevice);
        markToMap();
        Iterator<CallBack> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onDeviceChanged(watchesDevice);
        }
    }

    @Override // com.xiaopeng.iotlib.provider.account.AccountCallBack
    public void onAccountChanged(boolean z) {
        LogUtils.i(TAG, "onAccountChanged isLogin " + z + " , mIsLogin " + this.mIsLogin);
        if (this.mIsLogin == z) {
            return;
        }
        this.mIsLogin = z;
        if (!z) {
            setMarkEnable(false);
            setWatchesDevice(WatchesDevice.reset(getWatchesDevice()));
        }
        Iterator<CallBack> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onLoginChanged(z);
        }
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onEvent(String str, String str2) {
        if (Watches.PROP_GET_RESULT.equals(str)) {
            int parse = Utils.parse(str2);
            LogUtils.i(TAG, "onEvent result " + parse);
            Iterator<CallBack> it = this.mCallBacks.iterator();
            while (it.hasNext()) {
                it.next().onResult(parse);
            }
        } else if (Watches.PROP_BIND_STATUS.equals(str) && "100".equals(str2)) {
            LogUtils.i(TAG, "onEvent result " + str2);
            WatchesDevice loadData = WatchesApi.getApi().loadData();
            Iterator<CallBack> it2 = this.mCallBacks.iterator();
            while (it2.hasNext()) {
                CallBack next = it2.next();
                next.onDeviceChanged(loadData);
                next.onAccountSwitch();
            }
        }
    }

    public synchronized WatchesDevice getWatchesDevice() {
        return this.mWatchesDevice;
    }

    private synchronized void setWatchesDevice(WatchesDevice watchesDevice) {
        this.mWatchesDevice = watchesDevice;
    }

    private void clearMarkToMap() {
        MapUtils.clearMark();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void markToMap() {
        WatchesDevice watchesDevice = getWatchesDevice();
        boolean z = this.mMarkEnable;
        boolean z2 = this.mIsLogin;
        LogUtils.i(TAG, "markToMap isLogin " + z2 + " , device " + watchesDevice);
        if (z && z2 && watchesDevice != null && watchesDevice.getState() == 1 && watchesDevice.getLocationTime() != null) {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            try {
                double[] bg09_to_gcj02 = MapUtils.bg09_to_gcj02(watchesDevice.getLongitude(), watchesDevice.getLatitude());
                jSONObject.put("mType", 2);
                jSONObject.put("mId", 0);
                jSONObject.put("mLon", bg09_to_gcj02[0]);
                jSONObject.put("mLat", bg09_to_gcj02[1]);
                jSONObject.put("mZ", 1.0d);
                jSONObject.put("mCoordinateType", "gcj02");
                jSONObject.put("mName", watchesDevice.getName());
                jSONObject.put("mAddress", watchesDevice.getLocationDesc());
                jSONObject.put("mUpdateTime", watchesDevice.getLocationTime());
                jSONObject.put("mPhoneNumber", watchesDevice.getPhone());
                jSONObject.put("mPicture", watchesDevice.getPhoto());
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MapUtils.drawMark(jSONArray.toString());
        }
    }
}
