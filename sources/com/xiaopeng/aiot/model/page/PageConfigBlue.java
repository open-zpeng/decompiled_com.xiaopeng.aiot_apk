package com.xiaopeng.aiot.model.page;

import android.net.Uri;
/* loaded from: classes.dex */
public abstract class PageConfigBlue extends PageConfigCommon {
    private static final String PATH = "/device/blue";

    @Override // com.xiaopeng.aiot.model.page.PageConfigCommon
    protected String path() {
        return PATH;
    }

    @Override // com.xiaopeng.iotlib.base.BasePageConfig
    public boolean supportDirect() {
        return false;
    }

    @Override // com.xiaopeng.iotlib.base.BasePageConfig
    public boolean supportVui() {
        return false;
    }

    @Override // com.xiaopeng.iotlib.base.BasePageConfig
    public Uri createUri() {
        return PageConfigManager.createScheme().path(PATH).appendQueryParameter("type", pageKey()).build();
    }
}
