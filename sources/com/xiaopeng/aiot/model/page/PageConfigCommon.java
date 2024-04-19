package com.xiaopeng.aiot.model.page;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.iotlib.base.BasePageConfig;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public abstract class PageConfigCommon extends BasePageConfig {
    static final String PARAMETER_TYPE = "type";

    protected abstract String path();

    @Override // com.xiaopeng.iotlib.base.BasePageConfig
    public PageId pageId() {
        return new PageId(path() + "/" + pageKey());
    }

    @Override // com.xiaopeng.iotlib.base.BasePageConfig
    public Intent createIntent() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268468224);
        intent.setData(createUri());
        return intent;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean hasShow(com.xiaopeng.iotlib.data.PageId r3) {
        /*
            r2 = this;
            com.xiaopeng.iotlib.model.activity.ActivityState r0 = com.xiaopeng.iotlib.model.activity.ActivityState.getInstance()
            java.util.HashMap r3 = r0.getActivityState(r3)
            if (r3 == 0) goto L30
            java.util.Collection r3 = r3.values()
            java.util.Iterator r3 = r3.iterator()
        L12:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L30
            java.lang.Object r0 = r3.next()
            com.xiaopeng.iotlib.model.activity.ActivityState$State r0 = (com.xiaopeng.iotlib.model.activity.ActivityState.State) r0
            com.xiaopeng.iotlib.model.activity.ActivityState$State r1 = com.xiaopeng.iotlib.model.activity.ActivityState.State.CREATE
            if (r0 == r1) goto L2e
            com.xiaopeng.iotlib.model.activity.ActivityState$State r1 = com.xiaopeng.iotlib.model.activity.ActivityState.State.START
            if (r0 == r1) goto L2e
            com.xiaopeng.iotlib.model.activity.ActivityState$State r1 = com.xiaopeng.iotlib.model.activity.ActivityState.State.RESUME
            if (r0 == r1) goto L2e
            com.xiaopeng.iotlib.model.activity.ActivityState$State r1 = com.xiaopeng.iotlib.model.activity.ActivityState.State.PAUSE
            if (r0 != r1) goto L12
        L2e:
            r3 = 1
            return r3
        L30:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.aiot.model.page.PageConfigCommon.hasShow(com.xiaopeng.iotlib.data.PageId):boolean");
    }

    @Override // com.xiaopeng.iotlib.base.BasePageConfig
    public boolean go(Context context) {
        PageId pageId = pageId();
        boolean hasShow = hasShow(pageId);
        LogUtils.i(LogConfig.TAG_PAGE_CONFIG, "go page : " + pageId + " ,hasShow : " + hasShow);
        if (hasShow) {
            return false;
        }
        context.startActivity(createIntent());
        return true;
    }
}
