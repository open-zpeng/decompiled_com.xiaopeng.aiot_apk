package com.xiaopeng.aiot.device.blue.ui;

import android.content.Context;
import android.content.res.Resources;
import android.widget.RelativeLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.aiot.device.blue.vm.BlueVM;
import com.xiaopeng.iotlib.base.BasePage;
import com.xiaopeng.iotlib.model.config.ApiConfig;
/* loaded from: classes.dex */
public class BluePage extends BasePage<AppView> {
    private String mDeviceType;

    public BluePage(Lifecycle lifecycle, String str) {
        super(lifecycle);
        this.mDeviceType = str;
    }

    @Override // com.xiaopeng.iotlib.base.BasePage
    protected RelativeLayout.LayoutParams onCreateLayoutParams(Resources resources) {
        return new RelativeLayout.LayoutParams(-1, -1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.base.BasePage
    public AppView onCreateAppView(Context context) {
        if (ApiConfig.API_DEBUG) {
            return new AppViewDebug(context);
        }
        return new AppView(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.base.BasePage
    public void onCreateViewModel(ViewModelProvider viewModelProvider, AppView appView) {
        appView.setIViewData((BlueVM) viewModelProvider.get(BlueVM.class), this.mDeviceType);
    }
}
