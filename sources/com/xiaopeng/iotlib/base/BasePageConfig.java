package com.xiaopeng.iotlib.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.lifecycle.Lifecycle;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.provider.voice.vui.VuiManager;
/* loaded from: classes.dex */
public abstract class BasePageConfig {
    public abstract Intent createIntent();

    public abstract BasePage createPage(Lifecycle lifecycle);

    public abstract Uri createUri();

    public abstract boolean go(Context context);

    public abstract PageId pageId();

    public abstract String pageKey();

    public abstract int pageTitle();

    public int pageTitleColor() {
        return 0;
    }

    public boolean supportDirect() {
        return false;
    }

    public boolean supportVui() {
        return VuiManager.supportVui();
    }
}
