package com.xiaopeng.aiot.device.childseat.ui;

import android.view.View;
import com.xiaopeng.aiot.device.childseat.data.ChildSeatDevice;
/* loaded from: classes.dex */
abstract class Group {
    IActionListener mIActionListener;

    /* loaded from: classes.dex */
    interface IActionListener {
        void onBind();

        void onOpenBle();

        void onUnBind();
    }

    abstract void init(View view);

    /* JADX INFO: Access modifiers changed from: package-private */
    public void refresh(ChildSeatDevice childSeatDevice) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void refreshDayOrNight() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Group(IActionListener iActionListener, View view) {
        this.mIActionListener = iActionListener;
        init(view);
    }
}
