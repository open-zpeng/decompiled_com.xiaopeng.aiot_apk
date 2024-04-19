package com.xiaopeng.iotlib.provider.car;

import android.app.Application;
import android.car.Car;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import com.xiaopeng.iotlib.provider.car.CarManager;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
/* loaded from: classes.dex */
public class CarManager {
    private static final String TAG = "CarManager";
    private static volatile CarManager sInstance;
    private Car mCarApiClient;
    private Context mContext;
    private HandlerThread mWorkThread;
    private final CopyOnWriteArraySet<ICarManagerCallBack> mCallBacks = new CopyOnWriteArraySet<>();
    private final ServiceConnection mCarConnectionCb = new AnonymousClass1();
    private List<ICarEcuControl> mCarEcuManagers = new ArrayList();
    private ICarHvacControl mCarHvacControl = ICarEcuControl.createHvacManager();
    private ICarMcuControl mCarMcuControl = ICarEcuControl.createMcuController();

    public static CarManager get() {
        if (sInstance == null) {
            synchronized (CarManager.class) {
                if (sInstance == null) {
                    sInstance = new CarManager();
                }
            }
        }
        return sInstance;
    }

    private CarManager() {
        this.mCarEcuManagers.add(this.mCarHvacControl);
        this.mCarEcuManagers.add(this.mCarMcuControl);
    }

    public void init(Application application) {
        this.mContext = application.getApplicationContext();
        this.mWorkThread = new HandlerThread("xpiot-car");
        this.mWorkThread.start();
        this.mCarApiClient = Car.createCar(this.mContext, this.mCarConnectionCb, new Handler(this.mWorkThread.getLooper()));
        connectCar();
    }

    private void connectCar() {
        try {
            if (this.mCarApiClient != null) {
                this.mCarApiClient.connect();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.iotlib.provider.car.CarManager$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements ServiceConnection {
        AnonymousClass1() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.w("CarManager", "onServiceConnected: " + iBinder);
            ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.car.-$$Lambda$CarManager$1$_GZGGJzlnqx83D6wJL8qQJV8-Jk
                @Override // java.lang.Runnable
                public final void run() {
                    CarManager.AnonymousClass1.this.lambda$onServiceConnected$0$CarManager$1();
                }
            });
        }

        public /* synthetic */ void lambda$onServiceConnected$0$CarManager$1() {
            CarManager.this.initEcuManager();
            Iterator it = CarManager.this.mCallBacks.iterator();
            while (it.hasNext()) {
                ((ICarManagerCallBack) it.next()).onServiceConnected();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.e("CarManager", "onServiceDisconnected: ");
            Iterator it = CarManager.this.mCallBacks.iterator();
            while (it.hasNext()) {
                ((ICarManagerCallBack) it.next()).onServiceDisconnected();
            }
        }
    }

    public void addCarManagerCallBack(ICarManagerCallBack iCarManagerCallBack) {
        this.mCallBacks.add(iCarManagerCallBack);
    }

    public void removeCarManagerCallBack(ICarManagerCallBack iCarManagerCallBack) {
        this.mCallBacks.remove(iCarManagerCallBack);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEcuManager() {
        long currentTimeMillis = System.currentTimeMillis();
        for (ICarEcuControl iCarEcuControl : this.mCarEcuManagers) {
            iCarEcuControl.init(this.mCarApiClient);
        }
        LogUtils.d("CarManager", "initEcuManager " + (System.currentTimeMillis() - currentTimeMillis));
    }

    public void release() {
        for (ICarEcuControl iCarEcuControl : this.mCarEcuManagers) {
            iCarEcuControl.release();
        }
        Car car = this.mCarApiClient;
        if (car != null) {
            car.disconnect();
        }
        HandlerThread handlerThread = this.mWorkThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        this.mCallBacks.clear();
    }

    public ICarHvacControl getCarHvacControl() {
        return this.mCarHvacControl;
    }

    public ICarMcuControl getCarMcuControl() {
        return this.mCarMcuControl;
    }
}
