package com.xiaopeng.aiot.device.fragrance.model;

import com.xiaopeng.aiot.device.fragrance.api.FragranceApi;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.iotlib.model.api.IDeviceApi;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.Arrays;
@Deprecated
/* loaded from: classes.dex */
public class FragranceInsertManager implements IDeviceApi.OnDataChangeListener<FragranceDevice> {
    private static final String TAG = "FragranceInsert";
    private int[] mChannels;
    private OnChannelInsertListener mOnChannelInsertListener;

    /* loaded from: classes.dex */
    public interface OnChannelInsertListener {
        void onChannelInsert();
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final FragranceInsertManager INSTANCE = new FragranceInsertManager();

        private SingletonHolder() {
        }
    }

    private FragranceInsertManager() {
        this.mChannels = FragranceApi.getApi().loadData().getChannelType();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static FragranceInsertManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void start() {
        FragranceApi.getApi().addOnDataChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stop() {
        FragranceApi.getApi().removeOnDataChangeListener(this);
    }

    @Override // com.xiaopeng.iotlib.model.api.IDeviceApi.OnDataChangeListener
    public void onDataChanged(FragranceDevice fragranceDevice) {
        OnChannelInsertListener onChannelInsertListener;
        LogUtils.i(TAG, "onDataChanged old : " + Arrays.toString(this.mChannels));
        LogUtils.i(TAG, "onDataChanged new : " + Arrays.toString(fragranceDevice.getChannelType()));
        if (checkInsert(fragranceDevice.getChannelType()) && (onChannelInsertListener = this.mOnChannelInsertListener) != null) {
            onChannelInsertListener.onChannelInsert();
        }
        this.mChannels = fragranceDevice.getChannelType();
    }

    private boolean checkInsert(int[] iArr) {
        int[] iArr2 = this.mChannels;
        if (iArr != null && iArr2 != null) {
            for (int i = 0; i < iArr.length; i++) {
                if (iArr[i] != -1 && (i >= iArr2.length || iArr2[i] == -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOnChannelInsertListener(OnChannelInsertListener onChannelInsertListener) {
        this.mOnChannelInsertListener = onChannelInsertListener;
    }
}
