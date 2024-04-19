package com.xiaopeng.iotlib.provider.iot;

import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.provider.iot.IotControl;
import com.xiaopeng.iotlib.provider.iot.IotControlImpl;
import com.xiaopeng.iotlib.provider.iot.device.Base;
import com.xiaopeng.iotlib.provider.iot.device.Device;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.xuimanager.XUIManager;
import com.xiaopeng.xuimanager.iot.BaseDevice;
import com.xiaopeng.xuimanager.iot.IDeviceListener;
import com.xiaopeng.xuimanager.iot.IoTManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class IotControlImpl implements IotControl {
    private static final String TAG = "XuiIot";
    private IotControlDebug mDebug;
    private IoTManager mIoTManager;
    private final ConcurrentHashMap<String, Map<String, String>> mCache = new ConcurrentHashMap<>();
    private final CopyOnWriteArraySet<IotControl.IDeviceListenerListener> mCallBacks = new CopyOnWriteArraySet<>();
    private IDeviceListener mIDeviceListener = new AnonymousClass1();

    @Override // com.xiaopeng.iotlib.provider.xui.IXuiControl
    public synchronized void init(XUIManager xUIManager) {
        try {
            this.mIoTManager = (IoTManager) xUIManager.getXUIServiceManager("iotmanager");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setIoTManager(this.mIoTManager);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void setIoTManager(IoTManager ioTManager) {
        this.mIoTManager = ioTManager;
        LogUtils.i("XuiIot", String.format("init: API_DEBUG  %s, USE_CACHE %s", Boolean.valueOf(ApiConfig.API_DEBUG), Boolean.valueOf(ApiConfig.USE_CACHE)));
        init();
    }

    private synchronized void init() {
        IoTManager ioTManager = getIoTManager();
        if (ioTManager == null) {
            LogUtils.e("XuiIot", "init: IoTManager is null ");
            return;
        }
        if (ApiConfig.API_DEBUG) {
            this.mDebug = new IotControlDebug(this.mIDeviceListener);
            this.mDebug.init();
            setDebug(this.mDebug);
        }
        ioTManager.registerListener(this.mIDeviceListener);
        LogUtils.d("XuiIot", "registerListener ");
    }

    private synchronized IoTManager getIoTManager() {
        return this.mIoTManager;
    }

    private synchronized IotControlDebug getDebug() {
        return this.mDebug;
    }

    private synchronized void setDebug(IotControlDebug iotControlDebug) {
        this.mDebug = iotControlDebug;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.iotlib.provider.iot.IotControlImpl$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements IDeviceListener {
        AnonymousClass1() {
        }

        public void onDeviceAdd(final List<BaseDevice> list) {
            ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlImpl$1$qmX_RYrtfDmQ2D0TtAqm_L2puTE
                @Override // java.lang.Runnable
                public final void run() {
                    IotControlImpl.AnonymousClass1.this.lambda$onDeviceAdd$0$IotControlImpl$1(list);
                }
            });
        }

        public /* synthetic */ void lambda$onDeviceAdd$0$IotControlImpl$1(List list) {
            LogUtils.i("XuiIot", "onDeviceAdd " + list);
            List<Device> create = Device.create(list);
            Iterator it = IotControlImpl.this.mCallBacks.iterator();
            while (it.hasNext()) {
                ((IotControl.IDeviceListenerListener) it.next()).onDeviceAdd(create);
            }
        }

        public void onPropertiesUpdated(String str, Map<String, String> map) {
            IotControlImpl.this.process_onPropertiesUpdated(str, map);
        }

        public void onOperationResult(final String str, final String str2, final String str3) {
            LogUtils.i("XuiIot", String.format("onPropertiesUpdated,deviceId=%s, opCmd %s,reason %s", str, str2, str3));
            ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlImpl$1$MiNt5z0_HcINerSK3ZtNyB8F71w
                @Override // java.lang.Runnable
                public final void run() {
                    IotControlImpl.AnonymousClass1.this.lambda$onOperationResult$1$IotControlImpl$1(str, str2, str3);
                }
            });
        }

        public /* synthetic */ void lambda$onOperationResult$1$IotControlImpl$1(String str, String str2, String str3) {
            Iterator it = IotControlImpl.this.mCallBacks.iterator();
            while (it.hasNext()) {
                ((IotControl.IDeviceListenerListener) it.next()).onOperationResult(str, str2, str3);
            }
        }
    }

    @Override // com.xiaopeng.iotlib.provider.xui.IXuiControl
    public synchronized void release() {
        IotControlDebug debug;
        IoTManager ioTManager = getIoTManager();
        if (ioTManager != null) {
            ioTManager.unRegisterListener(this.mIDeviceListener);
        }
        if (ApiConfig.API_DEBUG && (debug = getDebug()) != null) {
            debug.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void process_onPropertiesUpdated(final String str, final Map<String, String> map) {
        if (map == null || str == null) {
            return;
        }
        LogUtils.i("XuiIot", "onPropertiesUpdated,id=" + str + " ," + map.toString());
        if (ApiConfig.USE_CACHE) {
            Map<String, String> map2 = this.mCache.get(str);
            if (map2 != null) {
                synchronized (this.mCache) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        map2.put(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                LogUtils.i("XuiIot", "onPropertiesUpdated  not cache ,id=" + str);
            }
        }
        ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlImpl$U-xbJk-P1Hb8PygDxH4_LKwPRLg
            @Override // java.lang.Runnable
            public final void run() {
                IotControlImpl.this.lambda$process_onPropertiesUpdated$0$IotControlImpl(str, map);
            }
        });
    }

    public /* synthetic */ void lambda$process_onPropertiesUpdated$0$IotControlImpl(String str, Map map) {
        Iterator<IotControl.IDeviceListenerListener> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onPropertiesUpdated(str, map);
        }
    }

    private void process_onDeviceBindChanged(final String str, final String str2) {
        ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.iot.-$$Lambda$IotControlImpl$lYYo_YptMyML4g0TZ_FFiUAT0Lo
            @Override // java.lang.Runnable
            public final void run() {
                IotControlImpl.this.lambda$process_onDeviceBindChanged$1$IotControlImpl(str, str2);
            }
        });
    }

    public /* synthetic */ void lambda$process_onDeviceBindChanged$1$IotControlImpl(String str, String str2) {
        Iterator<IotControl.IDeviceListenerListener> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onDeviceBindChanged(str, str2);
        }
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl
    public List<Device> getDevice(String str) {
        IoTManager ioTManager = getIoTManager();
        List<BaseDevice> list = null;
        if (ioTManager == null) {
            LogUtils.i("XuiIot", "getDevice IoTManager is null");
            return null;
        }
        try {
            if (ApiConfig.API_DEBUG) {
                IotControlDebug debug = getDebug();
                if (debug != null) {
                    list = debug.getDevice(str);
                }
            } else {
                list = ioTManager.getDevice(Base.GET_BY_DEVICE_TYPE, str);
                LogUtils.i("XuiIot", "getDevice " + list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Device.create(list);
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl
    public Map<String, String> getDeviceProperties(Device device) {
        Map<String, String> map;
        IoTManager ioTManager = getIoTManager();
        Map<String, String> map2 = null;
        if (checkDeviceAndIotNull("getDeviceProperties", device, ioTManager)) {
            return null;
        }
        if (ApiConfig.USE_CACHE && device.getDeviceId() != null && (map = this.mCache.get(device.getDeviceId())) != null) {
            LogUtils.i("XuiIot", "getDeviceProperties cache " + map);
            return map;
        }
        try {
            if (ApiConfig.API_DEBUG) {
                IotControlDebug debug = getDebug();
                if (debug != null) {
                    map2 = debug.getDeviceProperties(device);
                }
            } else {
                map2 = ioTManager.getDeviceProperties(device.getDevice());
                LogUtils.i("XuiIot", "getDeviceProperties: " + map2 + " ，device " + device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ApiConfig.USE_CACHE && device.getDeviceId() != null && map2 != null) {
            this.mCache.put(device.getDeviceId(), map2);
        }
        return map2;
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl
    public boolean setDeviceProperties(Device device, Map<String, String> map) {
        IoTManager ioTManager = getIoTManager();
        if (checkDeviceAndIotNull("setDeviceProperties", device, ioTManager)) {
            return false;
        }
        try {
            if (ApiConfig.API_DEBUG) {
                IotControlDebug debug = getDebug();
                return debug != null && debug.setDeviceProperties(device, map);
            }
            ioTManager.setDeviceProperties(device.getDevice(), map);
            LogUtils.i("XuiIot", "sendCommand propMap: " + map + " ,device: " + device);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl
    public boolean sendCommand(Device device, String str, String str2) {
        IoTManager ioTManager = getIoTManager();
        boolean z = true;
        if (ioTManager == null) {
            LogUtils.i("XuiIot", String.format("sendCommand IoTManager is null cmd : %s , params : %s ,device: %s ", str, str2, device));
            return false;
        }
        try {
            if (ApiConfig.API_DEBUG) {
                IotControlDebug debug = getDebug();
                if (debug == null || !debug.sendCommand(device, str, str2)) {
                    z = false;
                }
                if (Base.CMD_ADD_DEVICE.equals(str) || Base.CMD_REMOVE_DEVICE.equals(str)) {
                    process_onDeviceBindChanged(str2, str);
                }
                return z;
            }
            if (device != null) {
                LogUtils.i("XuiIot", String.format("sendCommand cmd : %s , params : %s ,device: %s ", str, str2, device.getDevice()));
                ioTManager.sendCommand(device.getDevice(), str, str2);
                if (Base.CMD_ADD_DEVICE.equals(str) || Base.CMD_REMOVE_DEVICE.equals(str)) {
                    process_onDeviceBindChanged(str2, str);
                }
            } else {
                ioTManager.sendCommand((BaseDevice) null, str, str2);
            }
            LogUtils.i("XuiIot", String.format("sendCommand cmd over : %s , params : %s ,device: %s ", str, str2, device));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl
    public void addListener(IotControl.IDeviceListenerListener iDeviceListenerListener) {
        LogUtils.d("XuiIot", "addListener: " + iDeviceListenerListener);
        this.mCallBacks.add(iDeviceListenerListener);
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl
    public void removeListener(IotControl.IDeviceListenerListener iDeviceListenerListener) {
        LogUtils.d("XuiIot", "removeListener: " + iDeviceListenerListener);
        this.mCallBacks.remove(iDeviceListenerListener);
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl
    public void subscribeNotifications(Device device) {
        IoTManager ioTManager = getIoTManager();
        if (checkDeviceAndIotNull("subscribeNotifications", device, ioTManager)) {
            return;
        }
        try {
            ioTManager.subscribeNotifications(device.getDevice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i("XuiIot", "subscribeNotifications " + device);
    }

    @Override // com.xiaopeng.iotlib.provider.iot.IotControl
    public void unSubscribeNotifications(Device device) {
        IoTManager ioTManager = getIoTManager();
        if (checkDeviceAndIotNull("unSubscribeNotifications", device, ioTManager)) {
            return;
        }
        try {
            ioTManager.unSubscribeNotifications(device.getDevice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i("XuiIot", "unSubscribeNotifications " + device);
    }

    private boolean checkDeviceAndIotNull(String str, Device device, IoTManager ioTManager) {
        if (device == null) {
            LogUtils.i("XuiIot", String.format("%s checkDeviceAndIot device is null ", str));
            return true;
        } else if (ioTManager == null) {
            LogUtils.i("XuiIot", String.format("%s checkDeviceAndIot IoTManager is null  device: %s", str, device));
            return true;
        } else {
            return false;
        }
    }
}
