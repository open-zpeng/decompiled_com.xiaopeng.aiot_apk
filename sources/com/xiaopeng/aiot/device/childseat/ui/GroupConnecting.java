package com.xiaopeng.aiot.device.childseat.ui;

import android.view.View;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.childseat.ui.Group;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class GroupConnecting extends Group {
    /* JADX INFO: Access modifiers changed from: package-private */
    public GroupConnecting(Group.IActionListener iActionListener, View view) {
        super(iActionListener, view);
    }

    @Override // com.xiaopeng.aiot.device.childseat.ui.Group
    void init(View view) {
        view.findViewById(R.id.childseat_btn_unbind).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.childseat.ui.-$$Lambda$GroupConnecting$FdELgOHszErtOzFYDONieleBt80
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GroupConnecting.this.lambda$init$0$GroupConnecting(view2);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$GroupConnecting(View view) {
        this.mIActionListener.onUnBind();
    }
}
