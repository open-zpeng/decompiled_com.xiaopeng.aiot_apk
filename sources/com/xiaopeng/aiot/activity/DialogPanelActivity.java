package com.xiaopeng.aiot.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.aiot.model.page.PageConfigManager;
import com.xiaopeng.iotlib.base.BasePage;
import com.xiaopeng.iotlib.base.BasePageActivity;
import com.xiaopeng.iotlib.base.BasePageConfig;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.xui.app.delegate.XActivityBind;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
@XActivityBind(3)
/* loaded from: classes.dex */
public class DialogPanelActivity extends BasePageActivity {
    private ViewGroup mRootView;
    private TextView mTitleView;

    @Override // com.xiaopeng.iotlib.base.BasePageActivity
    protected void onPageCreateInit(Bundle bundle, BasePageConfig basePageConfig, BasePage basePage, View view) {
        getWindowAttributes().setWidth(getResources().getDimensionPixelSize(R.dimen.device_detail_width)).setHeight(getResources().getDimensionPixelSize(R.dimen.device_detail_height));
        setContentView(R.layout.activity_panel);
        this.mRootView = (ViewGroup) findViewById(R.id.content);
        this.mRootView.addView(view, 0);
        View findViewById = findViewById(R.id.view_close);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$DialogPanelActivity$T_pEFlnF27KIXTPP9gQi6g27XuA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogPanelActivity.this.lambda$onPageCreateInit$0$DialogPanelActivity(view2);
            }
        });
        XTouchAreaUtils.extendTouchArea(findViewById, this.mRootView);
        this.mTitleView = (TextView) findViewById(R.id.view_title);
        if (this.mTitleView != null) {
            if (ApiConfig.API_DEBUG) {
                this.mTitleView.setText(String.format("%s%s", getString(basePageConfig.pageTitle()), getString(R.string.demo)));
            } else {
                this.mTitleView.setText(basePageConfig.pageTitle());
            }
            if (basePageConfig.pageTitleColor() != 0) {
                this.mTitleView.setTextColor(getResources().getColor(basePageConfig.pageTitleColor(), getTheme()));
            }
            this.mTitleView.setVisibility(4);
        }
        BackHelper backHelper = new BackHelper(this, PageConfigFactory.FRIDGE2.pageId(), PageConfigFactory.FRAGRANCE2.pageId());
        backHelper.onPageCreateInit(bundle);
        getActivityDismiss().setOnDismissListener(backHelper.getOnDismissListener());
    }

    public /* synthetic */ void lambda$onPageCreateInit$0$DialogPanelActivity(View view) {
        dismissActivity();
    }

    @Override // com.xiaopeng.iotlib.base.BaseActivity
    protected View getRootView() {
        return this.mRootView;
    }

    @Override // com.xiaopeng.iotlib.base.BasePageActivity
    protected BasePageConfig onPageConfigCreate() {
        return PageConfigManager.parseIntentForPageConfig(getIntent());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.base.BasePageActivity
    public void onPageViewLoadOver(BasePage basePage) {
        super.onPageViewLoadOver(basePage);
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setVisibility(0);
        }
    }

    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XThemeManager.isThemeChanged(configuration)) {
            BasePageConfig pageConfig = getPageConfig();
            if (this.mTitleView == null || pageConfig == null || pageConfig.pageTitleColor() == 0) {
                return;
            }
            this.mTitleView.setTextColor(getResources().getColor(pageConfig.pageTitleColor(), getTheme()));
        }
    }
}
