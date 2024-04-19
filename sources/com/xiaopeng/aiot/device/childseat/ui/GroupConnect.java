package com.xiaopeng.aiot.device.childseat.ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.childseat.data.ChildSeatDevice;
import com.xiaopeng.aiot.device.childseat.ui.Group;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XTextView;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class GroupConnect extends Group {
    private ChildSeatDevice mChildSeatDevice;
    private XListTwo mListTips;
    private XImageView mTipsIconView;
    private XTextView mTipsTextView;

    /* JADX INFO: Access modifiers changed from: package-private */
    public GroupConnect(Group.IActionListener iActionListener, View view) {
        super(iActionListener, view);
    }

    @Override // com.xiaopeng.aiot.device.childseat.ui.Group
    void init(View view) {
        XButton xButton = (XButton) ((XListTwo) view.findViewById(R.id.childseat_list_deviceinfo)).findViewById(R.id.x_list_action_button);
        xButton.setText(R.string.childseat_ble_connect_button);
        xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.childseat.ui.-$$Lambda$GroupConnect$XKvAo5oD3cnWTvvxAVtJZS_o2YI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GroupConnect.this.lambda$init$0$GroupConnect(view2);
            }
        });
        this.mListTips = (XListTwo) view.findViewById(R.id.childseat_list_tips);
        this.mTipsTextView = (XTextView) this.mListTips.findViewById(R.id.x_list_tv);
        this.mTipsIconView = (XImageView) this.mListTips.findViewById(R.id.x_list_action_icon);
        this.mTipsTextView.setCompoundDrawablePadding(8);
        this.mTipsIconView.setImageResource(R.drawable.childseat_ic_safetyseats_on);
    }

    public /* synthetic */ void lambda$init$0$GroupConnect(View view) {
        this.mIActionListener.onUnBind();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.aiot.device.childseat.ui.Group
    public void refresh(ChildSeatDevice childSeatDevice) {
        super.refresh(childSeatDevice);
        this.mChildSeatDevice = childSeatDevice;
        int status = childSeatDevice.getStatus();
        if (status == 4) {
            this.mListTips.setText(Iot.getApp().getText(R.string.childseat_uninstall_title));
            this.mTipsIconView.setImageResource(R.drawable.childseat_ic_safetyseats_off);
            refreshTextColor();
        } else if (status != 5) {
        } else {
            this.mListTips.setText(Iot.getApp().getText(R.string.childseat_install_title));
            this.mTipsIconView.setImageResource(R.drawable.childseat_ic_safetyseats_on);
            refreshTextColor();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.aiot.device.childseat.ui.Group
    public void refreshDayOrNight() {
        ThreadUtils.UI.postDelay(new Runnable() { // from class: com.xiaopeng.aiot.device.childseat.ui.-$$Lambda$GroupConnect$B0nvrqnO62va432VvSKnSsPs8Tc
            @Override // java.lang.Runnable
            public final void run() {
                GroupConnect.this.refreshTextColor();
            }
        }, 1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshTextColor() {
        int color;
        Drawable drawable;
        int status = this.mChildSeatDevice.getStatus();
        if (status == 4) {
            color = Iot.getApp().getColor(R.color.childseat_uninstall_color);
            drawable = Iot.getApp().getDrawable(R.drawable.childseat_ic_about);
        } else if (status != 5) {
            color = 0;
            drawable = null;
        } else {
            color = Iot.getApp().getColor(R.color.childseat_install_color);
            drawable = Iot.getApp().getDrawable(R.drawable.childseat_ic_selection);
        }
        this.mTipsTextView.setTextColor(color);
        this.mTipsTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
    }
}
