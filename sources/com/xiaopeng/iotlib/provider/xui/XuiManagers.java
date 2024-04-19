package com.xiaopeng.iotlib.provider.xui;

import android.app.Application;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import com.xiaopeng.iotlib.provider.iot.IotControl;
import com.xiaopeng.iotlib.provider.iot.IotManagers;
import com.xiaopeng.iotlib.provider.xui.XuiManagers;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.xuimanager.XUIManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
/* loaded from: classes.dex */
public class XuiManagers {
    private static final String TAG = "XuiManager";
    private static volatile XuiManagers sInstance;
    private XUIManager mClient;
    private HandlerThread mWorkThread;
    private final CopyOnWriteArraySet<IXuiManagerCallBack> mCallBacks = new CopyOnWriteArraySet<>();
    private final ServiceConnection mCarConnectionCb = new AnonymousClass1();
    private List<IXuiControl> mXuiControls = new ArrayList();
    private IotControl mIotControl = IotManagers.getIotControl();

    public static XuiManagers get() {
        if (sInstance == null) {
            synchronized (XuiManagers.class) {
                if (sInstance == null) {
                    sInstance = new XuiManagers();
                }
            }
        }
        return sInstance;
    }

    private XuiManagers() {
        this.mXuiControls.add(this.mIotControl);
    }

    public void init(Application application) {
        LogUtils.i("XuiManager", "init: ");
        this.mWorkThread = new HandlerThread("xpiot-car");
        this.mWorkThread.start();
        this.mClient = XUIManager.createXUIManager(application, this.mCarConnectionCb, new Handler(this.mWorkThread.getLooper()));
        connectCar();
    }

    private void connectCar() {
        try {
            if (this.mClient != null) {
                this.mClient.connect();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.iotlib.provider.xui.XuiManagers$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements ServiceConnection {
        AnonymousClass1() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.w("XuiManager", "onServiceConnected: " + iBinder);
            ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.xui.-$$Lambda$XuiManagers$1$qXkYv7eEElD4lK0LggS4I8Eh97M
                @Override // java.lang.Runnable
                public final void run() {
                    XuiManagers.AnonymousClass1.this.lambda$onServiceConnected$0$XuiManagers$1();
                }
            });
        }

        public /* synthetic */ void lambda$onServiceConnected$0$XuiManagers$1() {
            XuiManagers.this.initManager();
            Iterator it = XuiManagers.this.mCallBacks.iterator();
            while (it.hasNext()) {
                ((IXuiManagerCallBack) it.next()).onServiceConnected();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.e("XuiManager", "onServiceDisconnected: ");
            Iterator it = XuiManagers.this.mCallBacks.iterator();
            while (it.hasNext()) {
                ((IXuiManagerCallBack) it.next()).onServiceDisconnected();
            }
        }
    }

    public void addCarManagerCallBack(IXuiManagerCallBack iXuiManagerCallBack) {
        this.mCallBacks.add(iXuiManagerCallBack);
    }

    public void removeCarManagerCallBack(IXuiManagerCallBack iXuiManagerCallBack) {
        this.mCallBacks.remove(iXuiManagerCallBack);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initManager() {
        long currentTimeMillis = System.currentTimeMillis();
        for (IXuiControl iXuiControl : this.mXuiControls) {
            iXuiControl.init(this.mClient);
        }
        LogUtils.d("XuiManager", "initManager " + (System.currentTimeMillis() - currentTimeMillis));
    }

    public void release() {
        for (IXuiControl iXuiControl : this.mXuiControls) {
            iXuiControl.release();
        }
        XUIManager xUIManager = this.mClient;
        if (xUIManager != null) {
            xUIManager.disconnect();
        }
        HandlerThread handlerThread = this.mWorkThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        this.mCallBacks.clear();
    }

    public IotControl getIotControl() {
        return this.mIotControl;
    }
}
