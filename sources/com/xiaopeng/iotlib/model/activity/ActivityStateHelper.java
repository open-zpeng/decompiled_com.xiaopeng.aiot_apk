package com.xiaopeng.iotlib.model.activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.model.activity.ActivityState;
/* loaded from: classes.dex */
public class ActivityStateHelper implements LifecycleObserver, ActivityStateItem {
    private PageId mPageId;

    public static ActivityStateHelper create(Lifecycle lifecycle, PageId pageId) {
        return new ActivityStateHelper(lifecycle, pageId);
    }

    private ActivityStateHelper(Lifecycle lifecycle, PageId pageId) {
        this.mPageId = pageId;
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        ActivityState.getInstance().setActivityState(this.mPageId, this, ActivityState.State.CREATE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        ActivityState.getInstance().setActivityState(this.mPageId, this, ActivityState.State.START);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        ActivityState.getInstance().setActivityState(this.mPageId, this, ActivityState.State.RESUME);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        ActivityState.getInstance().setActivityState(this.mPageId, this, ActivityState.State.PAUSE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        ActivityState.getInstance().setActivityState(this.mPageId, this, ActivityState.State.STOP);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        ActivityState.getInstance().clearActivityState(this.mPageId, this);
    }
}
