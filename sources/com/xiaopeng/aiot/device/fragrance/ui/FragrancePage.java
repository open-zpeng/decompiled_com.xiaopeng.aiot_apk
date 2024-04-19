package com.xiaopeng.aiot.device.fragrance.ui;

import android.content.Context;
import android.content.res.Resources;
import android.widget.RelativeLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fragrance.vm.FragranceVM;
import com.xiaopeng.iotlib.base.BasePage;
@Deprecated
/* loaded from: classes.dex */
public class FragrancePage extends BasePage<AppView> {
    public FragrancePage(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override // com.xiaopeng.iotlib.base.BasePage
    protected RelativeLayout.LayoutParams onCreateLayoutParams(Resources resources) {
        return new RelativeLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.device_detail_fragrance_width), -2);
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
        appView.setIViewData((FragranceVM) viewModelProvider.get(FragranceVM.class));
    }
}
