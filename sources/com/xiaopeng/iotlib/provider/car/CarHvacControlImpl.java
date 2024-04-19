package com.xiaopeng.iotlib.provider.car;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.hvac.CarHvacManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.provider.car.ICarHvacControl;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class CarHvacControlImpl implements ICarHvacControl {
    private static final String TAG = "CarHvac";
    private CarHvacManager.CarHvacEventCallback mCarHvacEventCallback;
    private CarHvacManager mCarHvacManager;
    private List<Integer> mHvacPropertyIds;
    private BroadcastReceiver receiver;
    private final CopyOnWriteArraySet<ICarHvacControl.OnDataChangeListener> mCallBacks = new CopyOnWriteArraySet<>();
    private final ConcurrentHashMap<Integer, Object> mCache = new ConcurrentHashMap<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public CarHvacControlImpl() {
        LogUtils.d("CarHvac", "API_DEBUG  " + ApiConfig.API_DEBUG + ", USE_CACHE " + ApiConfig.USE_CACHE);
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarEcuControl
    public void init(Car car) {
        LogUtils.i("CarHvac", "init: ");
        try {
            this.mCarHvacManager = (CarHvacManager) car.getCarManager("hvac");
        } catch (Exception e) {
            LogUtils.e("CarHvac", "getCarManager error");
            e.printStackTrace();
        }
        this.mCarHvacEventCallback = new CarHvacManager.CarHvacEventCallback() { // from class: com.xiaopeng.iotlib.provider.car.CarHvacControlImpl.1
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                if (carPropertyValue != null) {
                    LogUtils.i("CarHvac", "onChangeEvent: " + carPropertyValue);
                    CarHvacControlImpl.this.processEvent(carPropertyValue);
                }
            }

            public void onErrorEvent(int i, int i2) {
                LogUtils.e("CarHvac", "onErrorEvent i: " + i + " i1 : " + i2);
            }
        };
        if (this.mCallBacks.size() > 0) {
            this.mHvacPropertyIds = null;
            registerPropCallback(false);
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarEcuControl
    public void release() {
        unRegisterPropCallback();
    }

    private synchronized void registerPropCallback(boolean z) {
        if (this.mCarHvacManager == null) {
            LogUtils.i("CarHvac", "registerPropCallback mCarHvacManager is null");
            return;
        }
        if (this.mHvacPropertyIds == null) {
            this.mHvacPropertyIds = new ArrayList();
            this.mHvacPropertyIds.add(557849143);
            this.mHvacPropertyIds.add(557914680);
            this.mHvacPropertyIds.add(557849145);
            this.mHvacPropertyIds.add(557849150);
            LogUtils.i("CarHvac", "registerPropCallback");
            try {
                if (z) {
                    this.mCarHvacManager.registerPropCallback(this.mHvacPropertyIds, this.mCarHvacEventCallback);
                } else {
                    this.mCarHvacManager.registerPropCallbackWithFlag(this.mHvacPropertyIds, this.mCarHvacEventCallback, 1);
                }
                LogUtils.i("CarHvac", "registerPropCallback: " + this.mHvacPropertyIds.toString());
            } catch (NoSuchMethodError | CarNotConnectedException e) {
                LogUtils.e("CarHvac", "registerPropCallback error");
                e.printStackTrace();
            }
        }
        if (ApiConfig.API_DEBUG) {
            LogUtils.i("CarHvac", "registerReceiver: ");
            if (this.receiver == null) {
                this.receiver = new DemoReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("ID_HVAC_SFS_SW_ST");
                intentFilter.addAction("ID_HVAC_SFS_CH_ALL");
                intentFilter.addAction("ID_HVAC_SFS_TASTE_SET");
                intentFilter.addAction("ID_HVAC_SFS_CON_ST");
                Iot.getApp().registerReceiver(this.receiver, intentFilter);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void unRegisterPropCallback() {
        if (this.mCarHvacManager == null) {
            return;
        }
        if (this.mCarHvacEventCallback != null && this.mHvacPropertyIds != null) {
            this.mCarHvacManager.unregisterPropCallback(this.mHvacPropertyIds, this.mCarHvacEventCallback);
            this.mHvacPropertyIds = null;
        }
        if (ApiConfig.API_DEBUG && this.receiver != null) {
            Iot.getApp().unregisterReceiver(this.receiver);
            this.receiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processEvent(CarPropertyValue carPropertyValue) {
        Integer[] numArr;
        switch (carPropertyValue.getPropertyId()) {
            case 557849143:
                if (carPropertyValue.getValue() instanceof Integer) {
                    process_ID_HVAC_SFS_SW_ST(((Integer) carPropertyValue.getValue()).intValue());
                    return;
                }
                return;
            case 557849145:
                if (carPropertyValue.getValue() instanceof Integer) {
                    process_ID_HVAC_SFS_TASTE_SET(((Integer) carPropertyValue.getValue()).intValue());
                    return;
                }
                return;
            case 557849150:
                if (carPropertyValue.getValue() instanceof Integer) {
                    process_ID_HVAC_SFS_CON_ST(((Integer) carPropertyValue.getValue()).intValue());
                    return;
                }
                return;
            case 557914680:
                if (!(carPropertyValue.getValue() instanceof Integer[]) || (numArr = (Integer[]) carPropertyValue.getValue()) == null) {
                    return;
                }
                int[] iArr = new int[numArr.length];
                for (int i = 0; i < numArr.length; i++) {
                    iArr[i] = numArr[i].intValue();
                }
                process_ID_HVAC_SFS_CH_ALL(iArr);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void process_ID_HVAC_SFS_SW_ST(final int i) {
        if (ApiConfig.USE_CACHE) {
            this.mCache.put(557849143, Integer.valueOf(i));
        }
        ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarHvacControlImpl$AtWO1lQlrxTFoEfQNyd4Y3pGH_M
            @Override // java.lang.Runnable
            public final void run() {
                CarHvacControlImpl.this.lambda$process_ID_HVAC_SFS_SW_ST$0$CarHvacControlImpl(i);
            }
        });
    }

    public /* synthetic */ void lambda$process_ID_HVAC_SFS_SW_ST$0$CarHvacControlImpl(int i) {
        Iterator<ICarHvacControl.OnDataChangeListener> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onSfsSwitchChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void process_ID_HVAC_SFS_CH_ALL(final int[] iArr) {
        if (iArr == null) {
            return;
        }
        if (ApiConfig.USE_CACHE) {
            this.mCache.put(557914680, iArr);
        }
        ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarHvacControlImpl$zBqzmut9KKvryQGP1EPgalW3_H4
            @Override // java.lang.Runnable
            public final void run() {
                CarHvacControlImpl.this.lambda$process_ID_HVAC_SFS_CH_ALL$1$CarHvacControlImpl(iArr);
            }
        });
    }

    public /* synthetic */ void lambda$process_ID_HVAC_SFS_CH_ALL$1$CarHvacControlImpl(int[] iArr) {
        Iterator<ICarHvacControl.OnDataChangeListener> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onSfsTypeChanged(iArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void process_ID_HVAC_SFS_TASTE_SET(final int i) {
        if (ApiConfig.USE_CACHE) {
            this.mCache.put(557849145, Integer.valueOf(i));
        }
        ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarHvacControlImpl$lTMZbMeUbj8YeIXDINxoDqTd2sw
            @Override // java.lang.Runnable
            public final void run() {
                CarHvacControlImpl.this.lambda$process_ID_HVAC_SFS_TASTE_SET$2$CarHvacControlImpl(i);
            }
        });
    }

    public /* synthetic */ void lambda$process_ID_HVAC_SFS_TASTE_SET$2$CarHvacControlImpl(int i) {
        Iterator<ICarHvacControl.OnDataChangeListener> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onSfsChannelChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void process_ID_HVAC_SFS_CON_ST(final int i) {
        if (ApiConfig.USE_CACHE) {
            this.mCache.put(557849150, Integer.valueOf(i));
        }
        ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarHvacControlImpl$ctoWZPHhWSe7fKpMPQwHCZwsfQI
            @Override // java.lang.Runnable
            public final void run() {
                CarHvacControlImpl.this.lambda$process_ID_HVAC_SFS_CON_ST$3$CarHvacControlImpl(i);
            }
        });
    }

    public /* synthetic */ void lambda$process_ID_HVAC_SFS_CON_ST$3$CarHvacControlImpl(int i) {
        Iterator<ICarHvacControl.OnDataChangeListener> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onSfsDensityChanged(i);
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public void addOnDataChangeListener(ICarHvacControl.OnDataChangeListener onDataChangeListener) {
        LogUtils.i("CarHvac", "addOnDataChangeListener: " + onDataChangeListener);
        this.mCallBacks.add(onDataChangeListener);
        ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarHvacControlImpl$n3dXDqlH5xaBR-PO3PkPbC3a5g0
            @Override // java.lang.Runnable
            public final void run() {
                CarHvacControlImpl.this.lambda$addOnDataChangeListener$4$CarHvacControlImpl();
            }
        });
    }

    public /* synthetic */ void lambda$addOnDataChangeListener$4$CarHvacControlImpl() {
        registerPropCallback(false);
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public void removeOnDataChangeListener(ICarHvacControl.OnDataChangeListener onDataChangeListener) {
        LogUtils.i("CarHvac", "removeOnDataChangeListener: " + onDataChangeListener);
        this.mCallBacks.remove(onDataChangeListener);
        if (this.mCallBacks.size() == 0) {
            ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarHvacControlImpl$Yh-Dpy1bkvKN6bC0RpF4v3SrcVs
                @Override // java.lang.Runnable
                public final void run() {
                    CarHvacControlImpl.this.unRegisterPropCallback();
                }
            });
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public void setSfsSwitch(int i) {
        LogUtils.i("CarHvac", "setSfsSwitch: " + i);
        if (this.mCarHvacManager == null) {
            LogUtils.i("CarHvac", "setSfsSwitch mCarHvacManager is null");
            return;
        }
        try {
            if (ApiConfig.API_DEBUG) {
                process_ID_HVAC_SFS_SW_ST(i);
            } else {
                this.mCarHvacManager.setSfsSwitch(i);
                LogUtils.i("CarHvac", "setSfsSwitch end: " + i);
            }
        } catch (Exception | NoSuchMethodError e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public boolean setSfsChannel(int i) {
        LogUtils.i("CarHvac", "setSfsChannel: " + i);
        if (this.mCarHvacManager == null) {
            LogUtils.i("CarHvac", "setSfsChannel mCarHvacManager is null");
            return false;
        }
        try {
            if (ApiConfig.API_DEBUG) {
                process_ID_HVAC_SFS_TASTE_SET(i);
                return true;
            }
            this.mCarHvacManager.setSfsChannel(i);
            LogUtils.i("CarHvac", "setSfsChannel end: " + i);
            return true;
        } catch (Exception | NoSuchMethodError e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public void setSfsDensity(int i) {
        LogUtils.i("CarHvac", "setSfsDensity: " + i);
        if (this.mCarHvacManager == null) {
            LogUtils.i("CarHvac", "setSfsDensity mCarHvacManager is null");
            return;
        }
        try {
            if (ApiConfig.API_DEBUG) {
                process_ID_HVAC_SFS_CON_ST(i);
            } else {
                this.mCarHvacManager.setSfsConcentration(i);
                LogUtils.i("CarHvac", "setSfsDensity end: " + i);
            }
        } catch (Exception | NoSuchMethodError e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public int getSfsSwitchStatus() {
        LogUtils.i("CarHvac", "getSfsSwitchStatus");
        if (ApiConfig.USE_CACHE) {
            Object obj = this.mCache.get(557849143);
            LogUtils.i("CarHvac", "getSfsSwitchStatus cache " + obj);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
        }
        if (this.mCarHvacManager == null) {
            LogUtils.i("CarHvac", "getSfsSwitchStatus mCarHvacManager is null");
            return 0;
        }
        try {
            int sfsSwitchStatus = ApiConfig.API_DEBUG ? 0 : this.mCarHvacManager.getSfsSwitchStatus();
            if (ApiConfig.USE_CACHE) {
                this.mCache.put(557849143, Integer.valueOf(sfsSwitchStatus));
            }
            LogUtils.i("CarHvac", "getSfsSwitchStatus: " + sfsSwitchStatus);
            return sfsSwitchStatus;
        } catch (Exception | NoSuchMethodError e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public int[] getSfsTypeInChannels() {
        LogUtils.i("CarHvac", "getSfsTypeInChannels: ");
        if (ApiConfig.USE_CACHE) {
            Object obj = this.mCache.get(557914680);
            if (obj instanceof int[]) {
                StringBuilder sb = new StringBuilder();
                sb.append("getTypeInChannels cache ");
                int[] iArr = (int[]) obj;
                sb.append(Arrays.toString(iArr));
                LogUtils.i("CarHvac", sb.toString());
                return iArr;
            }
        }
        if (this.mCarHvacManager == null) {
            LogUtils.i("CarHvac", "getSfsTypeInChannels mCarHvacManager is null");
            return null;
        }
        try {
            int[] sfsTypeInChannels = ApiConfig.API_DEBUG ? new int[]{1, 0, 2} : this.mCarHvacManager.getSfsTypeInChannels();
            if (ApiConfig.USE_CACHE && sfsTypeInChannels != null) {
                this.mCache.put(557914680, sfsTypeInChannels);
            }
            LogUtils.i("CarHvac", "getSfsTypeInChannels: " + Arrays.toString(sfsTypeInChannels));
            return sfsTypeInChannels;
        } catch (Exception | NoSuchMethodError e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public int getSfsChannel() {
        LogUtils.i("CarHvac", "getSfsChannel");
        if (ApiConfig.USE_CACHE) {
            Object obj = this.mCache.get(557849145);
            LogUtils.i("CarHvac", "getSfsChannel cache " + obj);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
        }
        if (this.mCarHvacManager == null) {
            LogUtils.i("CarHvac", "getSfsChannel mCarHvacManager is null");
            return -1;
        }
        try {
            int sfsChannel = ApiConfig.API_DEBUG ? 0 : this.mCarHvacManager.getSfsChannel();
            if (ApiConfig.USE_CACHE) {
                this.mCache.put(557849145, Integer.valueOf(sfsChannel));
            }
            LogUtils.i("CarHvac", "getSfsChannel: " + sfsChannel);
            return sfsChannel;
        } catch (Exception | NoSuchMethodError e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaopeng.iotlib.provider.car.ICarHvacControl
    public int getSfsDensity() {
        LogUtils.i("CarHvac", "getSfsDensity");
        if (ApiConfig.USE_CACHE) {
            Object obj = this.mCache.get(557849150);
            LogUtils.i("CarHvac", "getSfsDensity cache " + obj);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
        }
        if (this.mCarHvacManager == null) {
            LogUtils.i("CarHvac", "getSfsDensity mCarHvacManager is null");
            return 0;
        }
        try {
            int sfsConcentrationStatus = ApiConfig.API_DEBUG ? 2 : this.mCarHvacManager.getSfsConcentrationStatus();
            if (ApiConfig.USE_CACHE) {
                this.mCache.put(557849150, Integer.valueOf(sfsConcentrationStatus));
            }
            LogUtils.i("CarHvac", "getSfsDensity: " + sfsConcentrationStatus);
            return sfsConcentrationStatus;
        } catch (Exception | NoSuchMethodError e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class DemoReceiver extends BroadcastReceiver {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private DemoReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("ID_HVAC_SFS_SW_ST".equals(action)) {
                CarHvacControlImpl.this.process_ID_HVAC_SFS_SW_ST(intent.getIntExtra(VuiConstants.ELEMENT_VALUE, 0));
            } else if ("ID_HVAC_SFS_CH_ALL".equals(action)) {
                String[] split = intent.getStringExtra(VuiConstants.ELEMENT_VALUE).split(",");
                CarHvacControlImpl.this.process_ID_HVAC_SFS_CH_ALL(new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])});
            } else if ("ID_HVAC_SFS_TASTE_SET".equals(action)) {
                CarHvacControlImpl.this.process_ID_HVAC_SFS_TASTE_SET(intent.getIntExtra(VuiConstants.ELEMENT_VALUE, 0));
            } else if ("ID_HVAC_SFS_CON_ST".equals(action)) {
                CarHvacControlImpl.this.process_ID_HVAC_SFS_CON_ST(intent.getIntExtra(VuiConstants.ELEMENT_VALUE, 0));
            }
        }
    }
}
