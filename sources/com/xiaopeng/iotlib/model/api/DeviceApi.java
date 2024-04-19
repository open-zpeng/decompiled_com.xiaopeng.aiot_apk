package com.xiaopeng.iotlib.model.api;

import com.xiaopeng.iotlib.data.DeviceInfo;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
/* loaded from: classes.dex */
public abstract class DeviceApi<T extends DeviceInfo> implements IDeviceApi<T> {
    private volatile boolean mListenerSignal;
    private CopyOnWriteArrayList<WeakReference<IDeviceApi.OnDataChangeListener>> mOnDataChangeListeners = new CopyOnWriteArrayList<>();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract String logTag();

    protected abstract void startListenerSignal();

    protected abstract void stopListenerSignal();

    protected void serviceConnected() {
        ThreadUtils.API_SINGLE.post(new Runnable() { // from class: com.xiaopeng.iotlib.model.api.-$$Lambda$DeviceApi$UaFUbMszPLxXeUx2rjTm-j7UObM
            @Override // java.lang.Runnable
            public final void run() {
                DeviceApi.this.lambda$serviceConnected$0$DeviceApi();
            }
        });
    }

    public /* synthetic */ void lambda$serviceConnected$0$DeviceApi() {
        LogUtils.i(logTag(), " onServiceConnected ");
        notifyChanged(loadData());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void notifyChanged(T t) {
        Iterator<WeakReference<IDeviceApi.OnDataChangeListener>> it = this.mOnDataChangeListeners.iterator();
        while (it.hasNext()) {
            IDeviceApi.OnDataChangeListener onDataChangeListener = it.next().get();
            if (onDataChangeListener != null) {
                onDataChangeListener.onDataChanged(t);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void notifyEvent(String str, String str2) {
        Iterator<WeakReference<IDeviceApi.OnDataChangeListener>> it = this.mOnDataChangeListeners.iterator();
        while (it.hasNext()) {
            IDeviceApi.OnDataChangeListener onDataChangeListener = it.next().get();
            if (onDataChangeListener != null) {
                onDataChangeListener.onEvent(str, str2);
            }
        }
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi
    public synchronized void addOnDataChangeListener(IDeviceApi.OnDataChangeListener onDataChangeListener) {
        String logTag = logTag();
        LogUtils.d(logTag, "addOnDataChangeListener: " + onDataChangeListener);
        Iterator<WeakReference<IDeviceApi.OnDataChangeListener>> it = this.mOnDataChangeListeners.iterator();
        while (it.hasNext()) {
            if (it.next().get() == onDataChangeListener) {
                return;
            }
        }
        if (this.mOnDataChangeListeners.size() == 0) {
            startListenerSignal();
            this.mListenerSignal = true;
        }
        this.mOnDataChangeListeners.add(new WeakReference<>(onDataChangeListener));
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi
    public synchronized void removeOnDataChangeListener(IDeviceApi.OnDataChangeListener onDataChangeListener) {
        Iterator<WeakReference<IDeviceApi.OnDataChangeListener>> it = this.mOnDataChangeListeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WeakReference<IDeviceApi.OnDataChangeListener> next = it.next();
            if (next.get() == onDataChangeListener) {
                this.mOnDataChangeListeners.remove(next);
                break;
            }
        }
        String logTag = logTag();
        LogUtils.d(logTag, "removeOnDataChangeListener: " + onDataChangeListener + " , size " + this.mOnDataChangeListeners.size());
        if (this.mOnDataChangeListeners.size() == 0) {
            stopListenerSignal();
            this.mListenerSignal = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isListenerSignal() {
        return this.mListenerSignal;
    }
}
