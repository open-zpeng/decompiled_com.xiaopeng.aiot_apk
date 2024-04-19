package com.xiaopeng.iotlib.model.api;

import com.xiaopeng.iotlib.data.DeviceInfo;
/* loaded from: classes.dex */
public interface IDeviceApi<T extends DeviceInfo> {

    /* loaded from: classes.dex */
    public interface OnDataChangeListener<T extends DeviceInfo> {
        void onDataChanged(T t);

        default void onEvent(String str, String str2) {
        }
    }

    void addOnDataChangeListener(OnDataChangeListener<T> onDataChangeListener);

    T loadData();

    void removeOnDataChangeListener(OnDataChangeListener<T> onDataChangeListener);

    default boolean unBundling() {
        return false;
    }
}
