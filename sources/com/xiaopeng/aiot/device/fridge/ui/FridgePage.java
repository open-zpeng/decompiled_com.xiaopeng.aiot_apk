package com.xiaopeng.aiot.device.fridge.ui;

import android.content.Context;
import android.content.res.Resources;
import android.widget.RelativeLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fridge.vm.FridgeVM;
import com.xiaopeng.iotlib.base.BasePage;
@Deprecated
/* loaded from: classes.dex */
public class FridgePage extends BasePage<AppView> {
    public FridgePage(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override // com.xiaopeng.iotlib.base.BasePage
    protected RelativeLayout.LayoutParams onCreateLayoutParams(Resources resources) {
        return new RelativeLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.device_detail_fridge_width), -2);
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
        appView.setViewData((FridgeVM) viewModelProvider.get(FridgeVM.class));
    }
}
