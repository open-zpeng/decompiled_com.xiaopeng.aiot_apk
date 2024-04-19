package com.xiaopeng.iotlib.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import com.xiaopeng.iotlib.R;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.utils.TimeLogs;
import com.xiaopeng.xui.app.delegate.XActivityBind;
import com.xiaopeng.xui.app.delegate.XActivityTemplate;
import com.xiaopeng.xui.widget.dialogview.XDialogView;
@XActivityBind(3)
@Deprecated
/* loaded from: classes.dex */
public abstract class BasePageDialogActivity extends BasePageActivity {
    private XDialogView mXDialogView;

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPageCreateInit(Bundle bundle) {
        TimeLogs create = TimeLogs.create();
        create.start("onPageInit ");
        this.mXDialogView = new XDialogView(this, R.style.XDialogView_Large);
        create.record("XDialogView");
        setContentView(this.mXDialogView.getContentView());
        create.record("setContentView");
        ((XActivityTemplate.Dialog) getActivityTemplate()).useXDialogView(this.mXDialogView);
        create.record("useXDialogView");
        initPageView();
        create.record("initPageView");
        create.end();
    }

    private void initPageView() {
        BasePageConfig pageConfig = getPageConfig();
        if (pageConfig == null) {
            logI("initPageView- config is null");
            return;
        }
        if (ApiConfig.API_DEBUG) {
            XDialogView xDialogView = this.mXDialogView;
            xDialogView.setTitle(getString(pageConfig.pageTitle()) + getString(R.string.demo));
        } else {
            this.mXDialogView.setTitle(pageConfig.pageTitle());
        }
        BasePage page = getPage();
        if (page == null || page.getPageView() == null) {
            logI("initPageView- page or getPageView is null");
        } else {
            this.mXDialogView.setCustomView(page.getPageView(), false);
        }
    }

    @Override // com.xiaopeng.iotlib.base.BasePageActivity, com.xiaopeng.iotlib.base.BasePage.OnAppViewListener
    public void onRefreshBackground(int i) {
        super.onRefreshBackground(i);
        this.mXDialogView.getContentView().setBackgroundResource(i);
    }

    @Override // com.xiaopeng.iotlib.base.BasePageActivity, com.xiaopeng.iotlib.base.BasePage.OnAppViewListener
    public void onRefreshBackground(Drawable drawable) {
        super.onRefreshBackground(drawable);
        this.mXDialogView.getContentView().setBackground(drawable);
    }

    @Override // com.xiaopeng.iotlib.base.BaseActivity
    protected View getRootView() {
        return this.mXDialogView.getContentView();
    }
}
