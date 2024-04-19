package com.xiaopeng.aiot.device.blue.ui;

import android.content.Context;
import android.view.View;
import com.xiaopeng.aiot.R;
import com.xiaopeng.iotlib.model.config.ApiConfig;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AppViewDebug extends AppView {
    private View mSearch;

    @Override // com.xiaopeng.aiot.device.blue.ui.AppView
    protected int headerLayout() {
        return R.layout.ble_header_debug;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppViewDebug(Context context) {
        super(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.aiot.device.blue.ui.AppView
    public void initHeaderView(View view) {
        super.initHeaderView(view);
        this.mSearch = view.findViewById(R.id.blue_debug_button);
        this.mSearch.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.blue.ui.-$$Lambda$AppViewDebug$KC-TmvW2gZc5sM-NShpvxzut5HQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AppViewDebug.this.lambda$initHeaderView$0$AppViewDebug(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initHeaderView$0$AppViewDebug(View view) {
        this.mIViewData.start();
        this.mAdapter.clear();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.aiot.device.blue.ui.AppView
    public void onBlueState(int i) {
        super.onBlueState(i);
        refreshSearch();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.aiot.device.blue.ui.AppView
    public void onBlueScan() {
        super.onBlueScan();
        refreshSearch();
    }

    private void refreshSearch() {
        if (ApiConfig.API_DEBUG) {
            Boolean value = this.mIViewData.getBlueScan().getValue();
            Integer value2 = this.mIViewData.getBlueState().getValue();
            if (value == null || value2 == null) {
                return;
            }
            if (value2.intValue() == 12) {
                this.mSearch.setVisibility(value.booleanValue() ? 4 : 0);
            } else {
                this.mSearch.setVisibility(4);
            }
        }
    }
}
