package com.xiaopeng.aiot.activity;

import android.os.Bundle;
import android.view.View;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.aiot.model.page.PageConfigManager;
import com.xiaopeng.iotlib.base.BasePage;
import com.xiaopeng.iotlib.base.BasePageConfig;
import com.xiaopeng.iotlib.base.BasePageDialogActivity;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.xui.app.delegate.XActivityDismiss;
@Deprecated
/* loaded from: classes.dex */
public class DialogActivity extends BasePageDialogActivity {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$onPageCreateInit$0(int i, int i2) {
        return i == 2;
    }

    @Override // com.xiaopeng.iotlib.base.BasePageActivity
    protected BasePageConfig onPageConfigCreate() {
        return PageConfigManager.parseIntentForPageConfig(getIntent());
    }

    @Override // com.xiaopeng.iotlib.base.BasePageActivity
    protected void onPageCreateInit(Bundle bundle, BasePageConfig basePageConfig, BasePage basePage, View view) {
        super.onPageCreateInit(bundle);
        BackHelper backHelper = new BackHelper(this, PageConfigFactory.FRIDGE.pageId(), PageConfigFactory.FRAGRANCE.pageId());
        backHelper.onPageCreateInit(bundle);
        getActivityDismiss().setOnDismissListener(backHelper.getOnDismissListener());
        if (ApiConfig.API_DEBUG) {
            getActivityDismiss().setOnDismissListener(new XActivityDismiss.OnDismissListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$DialogActivity$EHaYaUMsTVVXOK8yJUCEzw2kUiQ
                @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss.OnDismissListener
                public final boolean onDismiss(int i, int i2) {
                    return DialogActivity.lambda$onPageCreateInit$0(i, i2);
                }
            });
        }
    }
}
