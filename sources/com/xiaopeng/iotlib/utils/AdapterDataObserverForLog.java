package com.xiaopeng.iotlib.utils;

import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.iotlib.provider.iot.device.Freezer;
/* loaded from: classes.dex */
public class AdapterDataObserverForLog extends RecyclerView.AdapterDataObserver {
    private static final String TAG = "observer";

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onChanged() {
        super.onChanged();
        log("onChanged");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onItemRangeChanged(int i, int i2) {
        super.onItemRangeChanged(i, i2);
        log(String.format("Changed %s %s", Integer.valueOf(i), Integer.valueOf(i2)));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onItemRangeChanged(int i, int i2, Object obj) {
        super.onItemRangeChanged(i, i2, obj);
        log(String.format("Changed %s %s %s", Integer.valueOf(i), Integer.valueOf(i2), obj));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onItemRangeInserted(int i, int i2) {
        super.onItemRangeInserted(i, i2);
        log(String.format("Inserted %s %s", Integer.valueOf(i), Integer.valueOf(i2)));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onItemRangeRemoved(int i, int i2) {
        super.onItemRangeRemoved(i, i2);
        log(String.format("Removed %s %s", Integer.valueOf(i), Integer.valueOf(i2)));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onItemRangeMoved(int i, int i2, int i3) {
        super.onItemRangeMoved(i, i2, i3);
        log(String.format("Moved %s %s %s", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
    }

    public void refresh() {
        log("===================");
    }

    public void error() {
        log(Freezer.VAL_ERR);
    }

    private void log(String str) {
        LogUtils.d(TAG, str);
    }
}
