package com.xiaopeng.aiot.model.page;

import android.net.Uri;
/* loaded from: classes.dex */
public abstract class PageConfigDetail extends PageConfigCommon {
    private static final String PATH = "/device/detail/old";

    @Override // com.xiaopeng.aiot.model.page.PageConfigCommon
    protected String path() {
        return PATH;
    }

    @Override // com.xiaopeng.iotlib.base.BasePageConfig
    public Uri createUri() {
        return PageConfigManager.createScheme().path(PATH).appendQueryParameter("type", pageKey()).build();
    }
}
