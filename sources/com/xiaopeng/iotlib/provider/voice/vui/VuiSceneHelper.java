package com.xiaopeng.iotlib.provider.voice.vui;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.speech.vui.Helper.IVuiSceneHelper;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.VuiUpdateType;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.Collections;
import java.util.List;
/* loaded from: classes.dex */
public class VuiSceneHelper implements IVuiSceneHelper, IVuiElementChangedListener, LifecycleObserver {
    private static final String TAG = "vuiScene";
    private Lifecycle mLifecycle;
    private View mRootView;
    private String mSceneId;

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public boolean isCustomBuildScene() {
        return true;
    }

    public VuiSceneHelper(Lifecycle lifecycle, String str, View view) {
        this.mLifecycle = lifecycle;
        this.mSceneId = str;
        this.mRootView = view;
        this.mLifecycle.addObserver(this);
    }

    public void init() {
        VuiManager.initScene(this.mLifecycle, getSceneId(), this.mRootView, this, this);
        LogUtils.d("vuiScene", " init SceneId : " + this.mSceneId + " " + hashCode());
    }

    public void build() {
        LogUtils.i("vuiScene", " buildVuiScene " + hashCode());
        buildScene();
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return this.mSceneId;
    }

    @Override // com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public List<View> getBuildViews() {
        return Collections.singletonList(this.mRootView);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementChangedListener
    public void onVuiElementChaned(View view, VuiUpdateType vuiUpdateType) {
        LogUtils.i("vuiScene", "onVuiElementChaned  SceneId :" + this.mSceneId + " vuiUpdateType: " + vuiUpdateType + " view: " + view);
        if (VuiUpdateType.UPDATE_VIEW.equals(vuiUpdateType)) {
            VuiManager.updateScene(getSceneId(), view);
        } else {
            VuiManager.updateElementAttribute(getSceneId(), view);
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(VuiEvent vuiEvent) {
        LogUtils.i("vuiScene", " onVuiEvent: ");
    }

    @Override // com.xiaopeng.vui.commons.IVuiSceneListener
    public boolean onInterceptVuiEvent(View view, VuiEvent vuiEvent) {
        LogUtils.i("vuiScene", " onInterceptVuiEvent: " + view);
        if (view != null) {
            VuiFloatingLayerManager.show(view);
            return false;
        }
        return false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        LogUtils.d("vuiScene", " onResume SceneId :" + this.mSceneId + " " + hashCode());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        LogUtils.d("vuiScene", " onPause SceneId :" + this.mSceneId + " " + hashCode());
        VuiFloatingLayerManager.hide();
        VuiFloatingLayerManager.hide(1);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        LogUtils.d("vuiScene", " onDestroy SceneId :" + this.mSceneId + " " + hashCode());
    }
}
