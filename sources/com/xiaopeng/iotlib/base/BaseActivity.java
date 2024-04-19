package com.xiaopeng.iotlib.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.View;
import com.xiaopeng.aiot.device.fragrance.page.FragrancePageConfigUtil;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.model.activity.ActivityStateHelper;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.model.direct.DirectHelper;
import com.xiaopeng.iotlib.provider.voice.vui.VuiManager;
import com.xiaopeng.iotlib.provider.voice.vui.VuiSceneHelper;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.xui.app.XActivity;
/* loaded from: classes.dex */
public abstract class BaseActivity extends XActivity {
    private BroadcastReceiver mCloseReceiver;
    private DirectHelper mDirectHelper;
    private PageId mPageId;
    private VuiSceneHelper mVuiSceneHelper;
    private boolean isFirstFrame = true;
    private Choreographer.FrameCallback callback = new Choreographer.FrameCallback() { // from class: com.xiaopeng.iotlib.base.BaseActivity.1
        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            if (BaseActivity.this.isFirstFrame) {
                Choreographer.getInstance().postFrameCallback(BaseActivity.this.callback);
                BaseActivity.this.isFirstFrame = false;
            }
        }
    };

    protected abstract View getRootView();

    protected abstract void onCreateInit(Bundle bundle);

    protected abstract PageId onPageIdCreate();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Choreographer.getInstance().postFrameCallback(this.callback);
        StartPerfUtils.onCreateBegin();
        super.onCreate(bundle);
        registerCloseReceiver();
        onCreateInit(bundle);
        initActivityState();
        StartPerfUtils.onCreateEnd();
    }

    private void registerCloseReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FragrancePageConfigUtil.BROADCAST_ACTION);
        this.mCloseReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.iotlib.base.BaseActivity.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                BaseActivity baseActivity = BaseActivity.this;
                baseActivity.logI("receiver broadcast action:" + intent.getAction());
                BaseActivity.this.finish();
            }
        };
        registerReceiver(this.mCloseReceiver, intentFilter);
    }

    public PageId getPageId() {
        if (this.mPageId == null) {
            this.mPageId = onPageIdCreate();
        }
        return this.mPageId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initVui() {
        if (!VuiManager.supportVui()) {
            logI("initVui VUI_ENABLE is false");
        } else if (getRootView() == null) {
            logI("initVui rootview is null");
        } else {
            String pageIdToSceneId = pageIdToSceneId(getPageId());
            if (pageIdToSceneId == null) {
                logI("initVui sceneId is null");
                return;
            }
            this.mVuiSceneHelper = new VuiSceneHelper(getLifecycle(), pageIdToSceneId, getRootView());
            this.mVuiSceneHelper.init();
        }
    }

    private static String pageIdToSceneId(PageId pageId) {
        if (pageId == null || pageId.get_pageId() == null) {
            return null;
        }
        return pageId.get_pageId().replaceFirst("/", "").replace("/", "-");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initDirect() {
        if (getRootView() == null || getPageId() == null) {
            logI("initDirect rootview or pageid is null");
        } else {
            logI("initVui DIRECT_ENABLE is false");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void buildVuiScene() {
        VuiSceneHelper vuiSceneHelper = this.mVuiSceneHelper;
        if (vuiSceneHelper != null) {
            vuiSceneHelper.build();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void buildElementDirect() {
        DirectHelper directHelper = this.mDirectHelper;
        if (directHelper != null) {
            directHelper.buildElementDirect();
        }
    }

    private void initActivityState() {
        if (getPageId() == null) {
            logI("initActivityState pageid is null");
        } else {
            ActivityStateHelper.create(getLifecycle(), getPageId());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        StartPerfUtils.onStartBegin();
        super.onStart();
        StartPerfUtils.onStartEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        StartPerfUtils.onResumeBegin();
        super.onResume();
        StartPerfUtils.onResumeEnd();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        StartPerfUtils.onReStartBegin();
        super.onRestart();
        StartPerfUtils.onReStartEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void logI(String str) {
        LogUtils.i(LogConfig.TAG_ACTIVITY, getClass().getSimpleName() + " " + hashCode() + "-" + str, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.app.XActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mCloseReceiver);
    }
}
