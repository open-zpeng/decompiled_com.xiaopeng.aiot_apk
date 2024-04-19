package com.xiaopeng.lib.apirouter.server;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.iotlib.provider.power.PowerCenterObserver;
/* loaded from: classes.dex */
public class PowerCenterObserver_Stub extends Binder implements IInterface {
    public PowerCenterObserver provider = new PowerCenterObserver();
    public PowerCenterObserver_Manifest manifest = new PowerCenterObserver_Manifest();

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 0) {
            if (i == 1598968902) {
                parcel2.writeString(PowerCenterObserver_Manifest.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel.enforceInterface(PowerCenterObserver_Manifest.DESCRIPTOR);
        Uri uri = (Uri) Uri.CREATOR.createFromParcel(parcel);
        try {
            this.provider.onEvent((String) TransactTranslator.read(uri.getQueryParameter(NotificationCompat.CATEGORY_EVENT), "java.lang.String"), (String) TransactTranslator.read(uri.getQueryParameter("data"), "java.lang.String"));
            parcel2.writeNoException();
            TransactTranslator.reply(parcel2, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            parcel2.writeException(new IllegalStateException(e.getMessage()));
            return true;
        }
    }
}
