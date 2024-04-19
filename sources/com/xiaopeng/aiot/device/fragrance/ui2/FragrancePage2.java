package com.xiaopeng.aiot.device.fragrance.ui2;

import android.content.Context;
import android.content.res.Resources;
import android.widget.RelativeLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fragrance.vm.FragranceVM;
import com.xiaopeng.iotlib.base.BasePage;
/* loaded from: classes.dex */
public class FragrancePage2 extends BasePage<AppView2> {
    public FragrancePage2(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override // com.xiaopeng.iotlib.base.BasePage
    protected RelativeLayout.LayoutParams onCreateLayoutParams(Resources resources) {
        return new RelativeLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.device_detail_width), resources.getDimensionPixelSize(R.dimen.device_detail_height));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.base.BasePage
    public AppView2 onCreateAppView(Context context) {
        return new AppView2(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.base.BasePage
    public void onCreateViewModel(ViewModelProvider viewModelProvider, AppView2 appView2) {
        appView2.setIViewData((FragranceVM) viewModelProvider.get(FragranceVM.class));
    }
}
