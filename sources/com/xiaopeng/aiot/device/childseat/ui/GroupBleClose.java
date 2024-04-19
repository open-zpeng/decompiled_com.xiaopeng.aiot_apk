package com.xiaopeng.aiot.device.childseat.ui;

import android.view.View;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.childseat.data.ChildSeatDevice;
import com.xiaopeng.aiot.device.childseat.ui.Group;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XTextView;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class GroupBleClose extends Group {
    private XButton mButton;
    private XTextView mTextSub;

    /* JADX INFO: Access modifiers changed from: package-private */
    public GroupBleClose(Group.IActionListener iActionListener, View view) {
        super(iActionListener, view);
    }

    @Override // com.xiaopeng.aiot.device.childseat.ui.Group
    void init(View view) {
        XListTwo xListTwo = (XListTwo) view.findViewById(R.id.childseat_list_deviceinfo);
        this.mTextSub = (XTextView) xListTwo.findViewById(R.id.x_list_tv_sub);
        this.mButton = (XButton) xListTwo.findViewById(R.id.x_list_action_button);
        this.mButton.setText(R.string.childseat_ble_close_button);
        this.mButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.childseat.ui.-$$Lambda$GroupBleClose$ad5qYVNbqtJRMqa_gIwgF9kn4P4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GroupBleClose.this.lambda$init$0$GroupBleClose(view2);
            }
        });
        refreshDayOrNight();
    }

    public /* synthetic */ void lambda$init$0$GroupBleClose(View view) {
        this.mButton.setEnabled(false);
        this.mIActionListener.onOpenBle();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.aiot.device.childseat.ui.Group
    public void refresh(ChildSeatDevice childSeatDevice) {
        super.refresh(childSeatDevice);
        int status = childSeatDevice.getStatus();
        if (status == 1) {
            this.mButton.setEnabled(true);
        } else if (status != 2) {
        } else {
            this.mButton.setEnabled(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.aiot.device.childseat.ui.Group
    public void refreshDayOrNight() {
        ThreadUtils.UI.postDelay(new Runnable() { // from class: com.xiaopeng.aiot.device.childseat.ui.-$$Lambda$GroupBleClose$Lw2LzNkpw02Rh0xKDmzNPw0wGls
            @Override // java.lang.Runnable
            public final void run() {
                GroupBleClose.this.lambda$refreshDayOrNight$1$GroupBleClose();
            }
        }, 1L);
    }

    public /* synthetic */ void lambda$refreshDayOrNight$1$GroupBleClose() {
        this.mTextSub.setTextColor(Iot.getApp().getColor(R.color.childseat_ble_close_color));
    }
}
