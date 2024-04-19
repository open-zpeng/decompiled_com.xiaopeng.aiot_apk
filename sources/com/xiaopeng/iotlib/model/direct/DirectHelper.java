package com.xiaopeng.iotlib.model.direct;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class DirectHelper implements LifecycleObserver, IDirectElementListener {
    private static final String TAG = "DirectHelper";
    private ElementDirect mElement;
    private boolean mIsBuild;
    private PageId mPageId;
    private View mRootView;

    private DirectHelper(PageId pageId, View view) {
        this.mPageId = pageId;
        this.mRootView = view;
    }

    public static DirectHelper bind(Lifecycle lifecycle, PageId pageId, View view) {
        DirectHelper directHelper = new DirectHelper(pageId, view);
        lifecycle.addObserver(directHelper);
        return directHelper;
    }

    public void buildElementDirect() {
        this.mIsBuild = true;
        if (this.mElement != null) {
            LogUtils.i("DirectHelper", " buildElementDirect mElement :" + this.mElement);
            DirectManager.get().goElement(this.mRootView, this.mElement);
            this.mElement = null;
        }
    }

    @Override // com.xiaopeng.iotlib.model.direct.IDirectElementListener
    public void onElementDirect(ElementDirect elementDirect) {
        LogUtils.i("DirectHelper", " onElementDirect mIsBuild :" + this.mIsBuild);
        if (this.mIsBuild) {
            DirectManager.get().goElement(this.mRootView, elementDirect);
        } else {
            this.mElement = elementDirect;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        DirectManager.get().addDirectListener(this.mPageId, this);
        LogUtils.d("DirectHelper", " onCreate pageId :" + this.mPageId + " " + hashCode());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        DirectManager.get().removeDirectListener(this.mPageId);
        LogUtils.d("DirectHelper", " onDestroy pageId :" + this.mPageId + " " + hashCode());
    }
}
