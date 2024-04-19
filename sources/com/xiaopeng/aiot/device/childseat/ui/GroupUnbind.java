package com.xiaopeng.aiot.device.childseat.ui;

import android.view.View;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.childseat.ui.Group;
import com.xiaopeng.xui.widget.XButton;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class GroupUnbind extends Group {
    /* JADX INFO: Access modifiers changed from: package-private */
    public GroupUnbind(Group.IActionListener iActionListener, View view) {
        super(iActionListener, view);
    }

    @Override // com.xiaopeng.aiot.device.childseat.ui.Group
    void init(View view) {
        ((XButton) view.findViewById(R.id.childseat_binding)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.childseat.ui.-$$Lambda$GroupUnbind$GlQNRGQw-VOQEtKwc6gGdrry3do
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GroupUnbind.this.lambda$init$0$GroupUnbind(view2);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$GroupUnbind(View view) {
        this.mIActionListener.onBind();
    }
}
