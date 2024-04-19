package com.xiaopeng.aiot.device.airbed.ui;

import android.content.Context;
import android.content.res.Resources;
import android.widget.RelativeLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.aiot.device.airbed.vm.AirBedVM;
import com.xiaopeng.iotlib.base.BasePage;
/* loaded from: classes.dex */
public class AirBedPage extends BasePage<AppView> {
    public AirBedPage(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override // com.xiaopeng.iotlib.base.BasePage
    protected RelativeLayout.LayoutParams onCreateLayoutParams(Resources resources) {
        return new RelativeLayout.LayoutParams(-1, -1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.base.BasePage
    public AppView onCreateAppView(Context context) {
        return new AppView(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.base.BasePage
    public void onCreateViewModel(ViewModelProvider viewModelProvider, AppView appView) {
        appView.setViewData((AirBedVM) viewModelProvider.get(AirBedVM.class));
    }
}
