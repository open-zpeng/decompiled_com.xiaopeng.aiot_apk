package com.xiaopeng.iotlib.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import com.xiaopeng.iotlib.base.BasePage;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.utils.TimeLogs;
/* loaded from: classes.dex */
public abstract class BasePageActivity extends BaseActivity implements BasePage.OnAppViewListener {
    private BasePage mPage;
    private BasePageConfig mPageConfig;

    protected abstract BasePageConfig onPageConfigCreate();

    protected abstract void onPageCreateInit(Bundle bundle, BasePageConfig basePageConfig, BasePage basePage, View view);

    @Override // com.xiaopeng.iotlib.base.BasePage.OnAppViewListener
    public void onRefreshBackground(int i) {
    }

    @Override // com.xiaopeng.iotlib.base.BasePage.OnAppViewListener
    public void onRefreshBackground(Drawable drawable) {
    }

    @Override // com.xiaopeng.iotlib.base.BaseActivity
    protected void onCreateInit(Bundle bundle) {
        TimeLogs create = TimeLogs.create();
        create.start("onCreateInit ");
        initPage();
        create.record("initPage");
        if (getPageConfig() != null && getPage() != null && getPage().getPageView() != null) {
            onPageCreateInit(bundle, getPageConfig(), getPage(), getPage().getPageView());
            create.record("onPageCreateInit");
        }
        if (getPageConfig() != null && getPageConfig().supportVui()) {
            initVui();
            create.record("initVui");
        }
        if (getPageConfig() != null && getPageConfig().supportDirect()) {
            initDirect();
            create.record("initDirect");
        }
        create.end();
    }

    private void initPage() {
        this.mPageConfig = onPageConfigCreate();
        BasePageConfig basePageConfig = this.mPageConfig;
        if (basePageConfig == null) {
            logI("initPage- dismiss : not found pageConfig ");
            dismissActivity();
            return;
        }
        this.mPage = basePageConfig.createPage(getLifecycle());
        BasePage basePage = this.mPage;
        if (basePage == null) {
            logI("initPage- dismiss : not found devicePage ");
            dismissActivity();
            return;
        }
        basePage.createView(this, this);
        logI("initPage- mPageConfig mPage created ");
        this.mPage.setOnPageListener(new BasePage.OnPageListener() { // from class: com.xiaopeng.iotlib.base.-$$Lambda$RZXgAX5wemSicS4NLyeVQ3D209A
            @Override // com.xiaopeng.iotlib.base.BasePage.OnPageListener
            public final void onAttachView(BasePage basePage2) {
                BasePageActivity.this.onPageViewLoadOver(basePage2);
            }
        });
        this.mPage.setOnAppViewListener(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPageViewLoadOver(BasePage basePage) {
        buildVuiScene();
        buildElementDirect();
    }

    @Override // com.xiaopeng.iotlib.base.BaseActivity
    protected PageId onPageIdCreate() {
        if (getPageConfig() == null) {
            logI("getPageId- pageConfig is null ");
            return null;
        }
        return getPageConfig().pageId();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BasePageConfig getPageConfig() {
        return this.mPageConfig;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BasePage getPage() {
        return this.mPage;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        logI("onNewIntent- !!!" + intent);
    }

    @Override // com.xiaopeng.iotlib.base.BasePage.OnAppViewListener
    public void onDismiss() {
        dismissActivity();
    }
}
