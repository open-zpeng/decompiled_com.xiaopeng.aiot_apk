package com.xiaopeng.aiot.demo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.aiot.device.blue.api.BlueApi;
import com.xiaopeng.aiot.device.fragrance.model.FragranceWork;
import com.xiaopeng.aiot.model.common.IntentActionUtils;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.model.direct.DirectManager;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.provider.account.AccountHelper;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.provider.iot.device.Watches;
import com.xiaopeng.iotlib.provider.map.MapUtils;
import com.xiaopeng.iotlib.provider.voice.command.SpeechManager;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.lib.apirouter.ClientConstants;
import com.xiaopeng.xui.app.XToast;
import java.util.Arrays;
import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class DemoService extends Service {
    private static final String TAG = "test";
    private Context mContext;
    private FragranceWork mFragranceWork;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.aiot.demo.DemoService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.i("test", action);
            if (IntentActionUtils.START_FRAGRANCE_INSERT_TEST.equals(action)) {
                if (DemoService.this.mFragranceWork == null) {
                    DemoService.this.mFragranceWork = new FragranceWork();
                    DemoService.this.mFragranceWork.onCreate(DemoService.this.mContext);
                }
            } else if (IntentActionUtils.ELEMENT_DIRECT_TEST.equals(action)) {
                String stringExtra = intent.getStringExtra("url");
                LogUtils.i("test", "Direct ELEMENT_DIRECT_TEST url " + stringExtra);
                if (DirectManager.get().go(stringExtra) || !ApiConfig.API_DEBUG) {
                    return;
                }
                XToast.show(" not support");
            } else if (IntentActionUtils.BLE_OPEN_TEST.equals(action)) {
                BluetoothHelper.get().open();
            } else if (IntentActionUtils.BLE_CLOSE_TEST.equals(action)) {
                BluetoothHelper.get().close();
            } else if (IntentActionUtils.LOG_LEVEL.equals(action)) {
                LogUtils.setLogLevel(intent.getIntExtra("level", 3));
            } else if (IntentActionUtils.LOG_TAG_LEVEL.equals(action)) {
                String stringExtra2 = intent.getStringExtra("tag");
                int intExtra = intent.getIntExtra("level", 3);
                if (stringExtra2 != null) {
                    LogUtils.setLogTagLevel(stringExtra2, Integer.valueOf(intExtra));
                }
            } else if (IntentActionUtils.LOG_TAG_CLEAR.equals(action)) {
                LogUtils.clearTagLevel();
            } else if (IntentActionUtils.FRIDGE_FILTER_MAC.equals(action)) {
                String stringExtra3 = intent.getStringExtra("mac");
                if (TextUtils.isEmpty(stringExtra3)) {
                    return;
                }
                BlueApi.getApi().setFilterMac(new HashSet(Arrays.asList(stringExtra3.split(";"))));
            } else if (IntentActionUtils.FRIDGE_FILTER_NAME.equals(action)) {
                String stringExtra4 = intent.getStringExtra(ClientConstants.ALIAS.P_NAME);
                HashSet hashSet = new HashSet();
                hashSet.clear();
                hashSet.add(stringExtra4);
                BlueApi.getApi().setMultipleFilterNames(hashSet);
            } else if (IntentActionUtils.SPEECH_EVENT.equals(action)) {
                SpeechManager.get().dispatchEvent(intent.getStringExtra(NotificationCompat.CATEGORY_EVENT), intent.getStringExtra("data"));
            } else if (IntentActionUtils.SPEECH_QUERY.equals(action)) {
                SpeechManager.get().dispatchQuery(intent.getStringExtra(NotificationCompat.CATEGORY_EVENT), intent.getStringExtra("data"), intent.getStringExtra("callback"));
            } else if (IntentActionUtils.SAVE_CLEAR.equals(action)) {
                SaveManager.get().getFragranceSave().clear();
            } else if (IntentActionUtils.LOGIN_TEST.equals(action)) {
                AccountHelper.get().setLoginTest(intent.getIntExtra("data", 0));
            } else if (IntentActionUtils.MARK_TEST.equals(action)) {
                String stringExtra5 = intent.getStringExtra(Watches.KEY_POS_LATITUDE);
                String stringExtra6 = intent.getStringExtra("lon");
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("mType", 2);
                    jSONObject.put("mId", 0);
                    jSONObject.put("mLat", Double.parseDouble(stringExtra5));
                    jSONObject.put("mLon", Double.parseDouble(stringExtra6));
                    jSONObject.put("mZ", 1.0d);
                    jSONObject.put("mCoordinateType", "gcj02");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MapUtils.drawMark(jSONObject.toString());
            } else {
                LogUtils.i("test", "else");
            }
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentActionUtils.START_FRAGRANCE_INSERT_TEST);
        intentFilter.addAction(IntentActionUtils.ELEMENT_DIRECT_TEST);
        intentFilter.addAction(IntentActionUtils.BLE_OPEN_TEST);
        intentFilter.addAction(IntentActionUtils.BLE_CLOSE_TEST);
        intentFilter.addAction(IntentActionUtils.LOG_LEVEL);
        intentFilter.addAction(IntentActionUtils.LOG_TAG_LEVEL);
        intentFilter.addAction(IntentActionUtils.LOG_TAG_CLEAR);
        intentFilter.addAction(IntentActionUtils.FRIDGE_FILTER_MAC);
        intentFilter.addAction(IntentActionUtils.FRIDGE_FILTER_NAME);
        intentFilter.addAction(IntentActionUtils.SPEECH_EVENT);
        intentFilter.addAction(IntentActionUtils.SPEECH_QUERY);
        intentFilter.addAction(IntentActionUtils.SAVE_CLEAR);
        intentFilter.addAction(IntentActionUtils.LOGIN_TEST);
        intentFilter.addAction(IntentActionUtils.MARK_TEST);
        registerReceiver(this.mReceiver, intentFilter);
        LogUtils.i("test", "onCreate");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        LogUtils.d("test", "onStartCommand " + intent.getAction());
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mReceiver);
        FragranceWork fragranceWork = this.mFragranceWork;
        if (fragranceWork != null) {
            fragranceWork.onDestroy(this);
            this.mFragranceWork = null;
        }
        LogUtils.i("test", "onDestroy");
    }
}
