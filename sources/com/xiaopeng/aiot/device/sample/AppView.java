package com.xiaopeng.aiot.device.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.xiaopeng.aiot.R;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
/* loaded from: classes.dex */
class AppView extends BaseAppView {
    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "sample-";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppView(Context context) {
        this(context, null);
    }

    AppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.sample_main_app_view, this);
        findViewById(R.id.btn02).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.sample.-$$Lambda$AppView$0j_hPkKX2hdZca20gGoj1J_974Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VuiFloatingLayerManager.show(view);
            }
        });
    }
}
