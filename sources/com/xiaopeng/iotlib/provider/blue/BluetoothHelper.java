package com.xiaopeng.iotlib.provider.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.ArraySet;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import java.util.Iterator;
/* loaded from: classes.dex */
public class BluetoothHelper {
    private static final String TAG = "blue";
    private BluetoothAdapter mBluetoothAdapter;
    private ArraySet<BluetoothCallBack> mCallBacks;
    private BluetoothReceive mReceive;

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final BluetoothHelper INSTANCE = new BluetoothHelper();

        private SingletonHolder() {
        }
    }

    public static BluetoothHelper get() {
        return SingletonHolder.INSTANCE;
    }

    private BluetoothHelper() {
        this.mCallBacks = new ArraySet<>();
        LogUtils.d("blue", "BluetoothHelper");
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public int getState() {
        LogUtils.d("blue", "getState ");
        return this.mBluetoothAdapter.getState();
    }

    public boolean open() {
        LogUtils.d("blue", "open");
        return this.mBluetoothAdapter.enable();
    }

    public boolean close() {
        LogUtils.d("blue", "close");
        if (this.mBluetoothAdapter.isEnabled()) {
            return this.mBluetoothAdapter.disable();
        }
        return true;
    }

    public void addCallBack(BluetoothCallBack bluetoothCallBack) {
        if (bluetoothCallBack == null) {
            return;
        }
        LogUtils.d("blue", "addCallBack " + bluetoothCallBack);
        if (this.mCallBacks.size() == 0) {
            registerReceiver(Iot.getApp());
        }
        this.mCallBacks.add(bluetoothCallBack);
    }

    public void removeCallBack(BluetoothCallBack bluetoothCallBack) {
        if (bluetoothCallBack == null) {
            return;
        }
        LogUtils.d("blue", "removeCallBack " + bluetoothCallBack);
        this.mCallBacks.remove(bluetoothCallBack);
        if (this.mCallBacks.size() == 0) {
            unregisterReceiver(Iot.getApp());
        }
    }

    private void registerReceiver(Context context) {
        LogUtils.d("blue", "registerReceiver");
        if (context != null && this.mReceive == null) {
            this.mReceive = new BluetoothReceive();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            context.getApplicationContext().registerReceiver(this.mReceive, intentFilter);
        }
    }

    private void unregisterReceiver(Context context) {
        LogUtils.d("blue", "unregisterReceiver");
        if (context == null || this.mReceive == null) {
            return;
        }
        context.getApplicationContext().unregisterReceiver(this.mReceive);
        this.mReceive = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class BluetoothReceive extends BroadcastReceiver {
        BluetoothReceive() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            int hashCode = action.hashCode();
            if (hashCode == -1530327060) {
                if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    c = 2;
                }
                c = 65535;
            } else if (hashCode != -301431627) {
                if (hashCode == 1821585647 && action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                    c = 1;
                }
                c = 65535;
            } else {
                if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                    c = 0;
                }
                c = 65535;
            }
            if (c == 0) {
                LogUtils.i("blue", "onReceive: 蓝牙设备:" + bluetoothDevice.getName() + " 已连接");
            } else if (c == 1) {
                LogUtils.i("blue", "onReceive: 蓝牙设备:" + bluetoothDevice.getName() + " 已断开");
            } else if (c == 2) {
                final int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
                switch (intExtra) {
                    case 10:
                        LogUtils.i("blue", "onReceive:  蓝牙已关闭");
                        break;
                    case 11:
                        LogUtils.i("blue", "onReceive:  蓝牙开启中");
                        break;
                    case 12:
                        LogUtils.i("blue", "onReceive:  蓝牙已开启");
                        break;
                    case 13:
                        LogUtils.i("blue", "onReceive:  蓝牙关闭中");
                        break;
                    default:
                        LogUtils.i("blue", "onReceive: blueState " + intExtra);
                        break;
                }
                if (BluetoothHelper.this.mCallBacks != null) {
                    ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.blue.-$$Lambda$BluetoothHelper$BluetoothReceive$NNAFkXkV56j8NdzmK6ULR9h_Qz8
                        @Override // java.lang.Runnable
                        public final void run() {
                            BluetoothHelper.BluetoothReceive.this.lambda$onReceive$0$BluetoothHelper$BluetoothReceive(intExtra);
                        }
                    });
                }
            } else {
                LogUtils.i("blue", "onReceive: " + action);
            }
        }

        public /* synthetic */ void lambda$onReceive$0$BluetoothHelper$BluetoothReceive(int i) {
            Iterator it = BluetoothHelper.this.mCallBacks.iterator();
            while (it.hasNext()) {
                ((BluetoothCallBack) it.next()).onBleStateChanged(i);
            }
        }
    }
}
