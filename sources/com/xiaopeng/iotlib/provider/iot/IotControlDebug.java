package com.xiaopeng.iotlib.provider.iot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.account.AccountCallBack;
import com.xiaopeng.iotlib.provider.account.AccountHelper;
import com.xiaopeng.iotlib.provider.blue.BluetoothCallBack;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.provider.iot.device.Base;
import com.xiaopeng.iotlib.provider.iot.device.ChildSafetySeat;
import com.xiaopeng.iotlib.provider.iot.device.Device;
import com.xiaopeng.iotlib.provider.iot.device.Fragrance;
import com.xiaopeng.iotlib.provider.iot.device.Fridge;
import com.xiaopeng.iotlib.provider.iot.device.Watches;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.xuimanager.iot.BaseDevice;
import com.xiaopeng.xuimanager.iot.IDeviceListener;
import com.xiaopeng.xuimanager.iot.devices.ChildSafetySeatDevice;
import com.xiaopeng.xuimanager.iot.devices.FragranceDevice;
import com.xiaopeng.xuimanager.iot.devices.FridgeDevice;
import com.xiaopeng.xuimanager.iot.devices.MituWatchDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class IotControlDebug implements BluetoothCallBack {
    private static final String TAG = "XuiIot-Debug";
    private IDeviceListener mIDeviceListener;
    private IotControlDebugScan mIotDemoScan;
    private BroadcastReceiver mReceiver;
    private boolean NoFridgeDevice = ApiConfig.API_DEBUG;
    private boolean NoChildSeatDevice = ApiConfig.API_DEBUG;

    /* JADX INFO: Access modifiers changed from: package-private */
    public IotControlDebug(IDeviceListener iDeviceListener) {
        this.mIDeviceListener = iDeviceListener;
        this.mIotDemoScan = new IotControlDebugScan(iDeviceListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void init() {
        LogUtils.d(TAG, "registerReceiver: ");
        if (this.mReceiver == null) {
            this.mReceiver = new DemoReceiver();
            Iot.getApp().registerReceiver(this.mReceiver, new IntentFilter("AIOT_API_DEMO"));
        }
        BluetoothHelper.get().addCallBack(this);
        AccountHelper.get().addCallBack(new AccountCallBack() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlDebug$L7Kya3tvn3OVgd0XTXxNwmBDaCw
            @Override // com.xiaopeng.iotlib.provider.account.AccountCallBack
            public final void onAccountChanged(boolean z) {
                IotControlDebug.this.lambda$init$0$IotControlDebug(z);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$IotControlDebug(boolean z) {
        if (z) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("state", "0");
        this.mIDeviceListener.onPropertiesUpdated("watches", hashMap);
    }

    @Override // com.xiaopeng.iotlib.provider.blue.BluetoothCallBack
    public void onBleStateChanged(int i) {
        LogUtils.i(TAG, "onBleStateChanged state: " + i);
        if (this.mIDeviceListener == null) {
            return;
        }
        if (i == 12) {
            final HashMap hashMap = new HashMap();
            hashMap.put(Base.PROP_CONNECT_STATE, "100");
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlDebug$a04oUbKzttqH3LYdFOVok8pUxgc
                @Override // java.lang.Runnable
                public final void run() {
                    IotControlDebug.this.lambda$onBleStateChanged$1$IotControlDebug(hashMap);
                }
            });
            ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlDebug$BKVK-HcLPe6k4azm2T54SvUYggI
                @Override // java.lang.Runnable
                public final void run() {
                    IotControlDebug.this.lambda$onBleStateChanged$2$IotControlDebug(hashMap);
                }
            });
        } else if (i == 10) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put(Base.PROP_CONNECT_STATE, "0");
            this.mIDeviceListener.onPropertiesUpdated("fridge", hashMap2);
            this.mIDeviceListener.onPropertiesUpdated("childseat", hashMap2);
            this.mIotDemoScan.stop();
        } else {
            LogUtils.i(TAG, "onBleStateChanged not do thing state: " + i);
        }
    }

    public /* synthetic */ void lambda$onBleStateChanged$1$IotControlDebug(HashMap hashMap) {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mIDeviceListener.onPropertiesUpdated("fridge", hashMap);
    }

    public /* synthetic */ void lambda$onBleStateChanged$2$IotControlDebug(HashMap hashMap) {
        try {
            Thread.sleep(8000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mIDeviceListener.onPropertiesUpdated("childseat", hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void release() {
        if (this.mReceiver != null) {
            Iot.getApp().unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
        BluetoothHelper.get().removeCallBack(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<BaseDevice> getDevice(String str) {
        LogUtils.i(TAG, String.format("getDevice params : %s ,NoFridgeDevice: %s , NoChildSeatDevice: %s ", str, Boolean.valueOf(this.NoFridgeDevice), Boolean.valueOf(this.NoChildSeatDevice)));
        ArrayList arrayList = new ArrayList();
        if (Fragrance.DEVICE_TYPE.equals(str)) {
            FragranceDevice fragranceDevice = new FragranceDevice("fragrance", "fragrance", Fragrance.DEVICE_TYPE);
            fragranceDevice.setPropertyMap(getDeviceProperties(Device.create((BaseDevice) fragranceDevice)));
            arrayList.add(fragranceDevice);
        } else if (Fridge.DEVICE_TYPE.equals(str)) {
            if (!this.NoFridgeDevice) {
                FridgeDevice fridgeDevice = new FridgeDevice("fridge", "fridge", Fridge.DEVICE_TYPE);
                fridgeDevice.setPropertyMap(getDeviceProperties(Device.create((BaseDevice) fridgeDevice)));
                arrayList.add(fridgeDevice);
            }
        } else if (ChildSafetySeat.DEVICE_TYPE.equals(str)) {
            if (!this.NoChildSeatDevice) {
                ChildSafetySeatDevice childSafetySeatDevice = new ChildSafetySeatDevice("childseat", "childseat", ChildSafetySeat.DEVICE_TYPE);
                childSafetySeatDevice.setPropertyMap(getDeviceProperties(Device.create((BaseDevice) childSafetySeatDevice)));
                arrayList.add(childSafetySeatDevice);
            }
        } else if (Watches.DEVICE_TYPE.equals(str)) {
            arrayList.add(new MituWatchDevice("watches", "watches", Watches.DEVICE_TYPE));
        } else {
            LogUtils.i(TAG, "getDevice debug not this device params: " + str);
        }
        LogUtils.i(TAG, "getDevice debug " + arrayList.toString());
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean sendCommand(Device device, String str, String str2) {
        LogUtils.i(TAG, String.format("sendCommand debug cmd : %s , params : %s ,device: %s ", str, str2, device));
        if (Base.CMD_SCAN_DEVICE_START.equals(str)) {
            this.mIotDemoScan.start();
        } else if (Base.CMD_SCAN_DEVICE_STOP.equals(str)) {
            this.mIotDemoScan.stop();
        } else if (Base.CMD_ADD_DEVICE.equals(str)) {
            if (Fridge.DEVICE_TYPE.equals(str2)) {
                this.NoFridgeDevice = false;
            } else if (ChildSafetySeat.DEVICE_TYPE.equals(str2)) {
                this.NoChildSeatDevice = false;
            }
        } else if (Base.CMD_REMOVE_DEVICE.equals(str)) {
            if (Fridge.DEVICE_TYPE.equals(str2)) {
                this.NoFridgeDevice = true;
            } else if (ChildSafetySeat.DEVICE_TYPE.equals(str2)) {
                this.NoChildSeatDevice = true;
            }
        } else {
            LogUtils.i(TAG, "sendCommand not cmd ");
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean setDeviceProperties(final Device device, final Map<String, String> map) {
        if (device != null && device.getDeviceType().equals(Watches.DEVICE_TYPE)) {
            LogUtils.i(TAG, String.format("setDeviceProperties watch  debug propMap: %s ,device: %s", map, device));
            if (map == null) {
                return true;
            }
            if (map.get(Watches.PROP_ALL_STATUS) != null) {
                ThreadUtils.UI.postDelay(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlDebug$ZcqVjtJuomWaOsqGoqB2mQBpwJQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        IotControlDebug.this.lambda$setDeviceProperties$3$IotControlDebug(device);
                    }
                }, UILooperObserver.ANR_TRIGGER_TIME);
            }
            if ("2".equals(map.get("position"))) {
                ThreadUtils.UI.postDelay(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlDebug$wrU4XyzIAY1yVL_q886lmsYUOw0
                    @Override // java.lang.Runnable
                    public final void run() {
                        IotControlDebug.this.lambda$setDeviceProperties$4$IotControlDebug(device);
                    }
                }, 10000L);
            }
            return true;
        }
        LogUtils.i(TAG, String.format("setDeviceProperties debug propMap: %s ,device: %s", map, device));
        ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlDebug$o7ZeEI44C8xzBaN_hYScI4WzpXM
            @Override // java.lang.Runnable
            public final void run() {
                IotControlDebug.this.lambda$setDeviceProperties$5$IotControlDebug(device, map);
            }
        });
        return true;
    }

    public /* synthetic */ void lambda$setDeviceProperties$3$IotControlDebug(Device device) {
        HashMap hashMap = new HashMap();
        hashMap.put(Watches.PROP_NETWORK_STATUS, String.valueOf(new Random().nextInt(3)));
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("power", String.valueOf(new Random().nextInt(100)));
            jSONObject.put("timestamp", "20220315133845555");
            hashMap.put("power", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mIDeviceListener.onPropertiesUpdated(device.getDeviceId(), hashMap);
    }

    public /* synthetic */ void lambda$setDeviceProperties$4$IotControlDebug(Device device) {
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(Watches.KEY_POS_DESCRIPTION, "上海市静安区江场三路 " + new Random().nextInt(100));
            jSONObject.put("timestamp", "20220315133845566");
            int nextInt = new Random().nextInt(50);
            float f = ((float) nextInt) / 100.0f;
            LogUtils.i(TAG, String.format("setDeviceProperties debug i: %s ,f =: %s", Integer.valueOf(nextInt), Float.valueOf(f)));
            double d = f;
            jSONObject.put(Watches.KEY_POS_LATITUDE, 23.173826d + d);
            jSONObject.put(Watches.KEY_POS_LONGITUDE, d + 113.402929d);
            hashMap.put("position", jSONObject.toString());
            this.mIDeviceListener.onPropertiesUpdated(device.getDeviceId(), hashMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ void lambda$setDeviceProperties$5$IotControlDebug(Device device, Map map) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mIDeviceListener.onPropertiesUpdated(device.getDeviceId(), map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<String, String> getDeviceProperties(Device device) {
        LogUtils.i(TAG, "getDeviceProperties " + device);
        HashMap hashMap = new HashMap();
        if (Fragrance.DEVICE_TYPE.equals(device.getDeviceType())) {
            hashMap.put("switch_state", "0");
            hashMap.put(Fragrance.PROP_CONCENTRATION, "2");
            hashMap.put(Fragrance.PROP_CHANNEL_TYPES, String.format("%s,%s,%s", 1, 4, -1));
        } else if (Fridge.DEVICE_TYPE.equals(device.getDeviceType())) {
            hashMap.put(Fridge.PROP_TARGET_TEMPERATURE, Fridge.VAL_TEMPERATURE_TARGET_MIDDLE);
            hashMap.put("temperature", "20");
            hashMap.put(Base.PROP_CONNECT_STATE, "100");
        } else if (ChildSafetySeat.DEVICE_TYPE.equals(device.getDeviceType())) {
            hashMap.put(ChildSafetySeat.PROP_ISOFIX_STATUS, "1");
            hashMap.put(Base.PROP_CONNECT_STATE, "100");
        } else if (Watches.DEVICE_TYPE.equals(device.getDeviceType())) {
            hashMap.put(Watches.PROP_BIND_STATUS, "1");
            hashMap.put(Watches.PROP_NICKNAME, "盘古");
            hashMap.put(Watches.PROP_PHONE_NUMBER, "13000000000");
        } else {
            LogUtils.i(TAG, "getDeviceProperties not type ");
        }
        LogUtils.i(TAG, "getDeviceProperties debug : " + hashMap + " ，device " + device);
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class DemoReceiver extends BroadcastReceiver {
        private DemoReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("AIOT_API_DEMO".equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra(VuiConstants.ELEMENT_ID);
                String stringExtra2 = intent.getStringExtra("k");
                String stringExtra3 = intent.getStringExtra("v");
                LogUtils.i(IotControlDebug.TAG, "AIOT_API_DEMO  id : " + stringExtra + ", key : " + stringExtra2 + ",value : " + stringExtra3);
                if ("account".equals(stringExtra)) {
                    if ("1".equals(stringExtra3)) {
                        AccountHelper.get().setLoginTest(1);
                    } else {
                        AccountHelper.get().setLoginTest(0);
                    }
                } else if (LogConfig.TAG_BLUE.equals(stringExtra)) {
                    if ("1".equals(stringExtra3)) {
                        BluetoothHelper.get().open();
                    } else {
                        BluetoothHelper.get().close();
                    }
                } else {
                    HashMap hashMap = new HashMap();
                    hashMap.put(stringExtra2, stringExtra3);
                    IotControlDebug.this.mIDeviceListener.onPropertiesUpdated(stringExtra, hashMap);
                }
            }
        }
    }
}
