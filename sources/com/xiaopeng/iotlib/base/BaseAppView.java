package com.xiaopeng.iotlib.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.base.BasePage;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public abstract class BaseAppView extends ConstraintLayout implements LifecycleOwner, LifecycleObserver, ViewModelStoreOwner, HasDefaultViewModelProviderFactory {
    private boolean mInit;
    private final LifecycleRegistry mLifecycleRegistry;
    private BasePage.OnAppViewListener mOnAppViewListener;
    private ViewModelStore mViewModelStore;

    protected abstract String logTag();

    public BaseAppView(Context context) {
        this(context, null);
    }

    public BaseAppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLifecycleRegistry = new LifecycleRegistry(this);
    }

    public void initLifecycle() {
        if (this.mInit) {
            logI("init already...");
            return;
        }
        this.mInit = true;
        getLifecycle().addObserver(this);
        getLifecycle().addObserver(new LifecycleEventObserver() { // from class: com.xiaopeng.iotlib.base.-$$Lambda$BaseAppView$Qsem15OtsqUgNq91rpwXwhFgyVA
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                BaseAppView.this.lambda$initLifecycle$0$BaseAppView(lifecycleOwner, event);
            }
        });
    }

    public /* synthetic */ void lambda$initLifecycle$0$BaseAppView(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            getViewModelStore().clear();
        }
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        if (this.mViewModelStore == null) {
            this.mViewModelStore = new ViewModelStore();
        }
        return this.mViewModelStore;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(Iot.getApp());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initLifecycle();
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        } else {
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        logI("onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        logD("onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        logD("onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        logI("onDestroy");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void logD(String str) {
        LogUtils.d(LogConfig.TAG_APPVIEW, logTag() + hashCode() + "-" + str, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void logI(String str) {
        LogUtils.i(LogConfig.TAG_APPVIEW, logTag() + hashCode() + "-" + str, 1);
    }

    public void setOnAppViewListener(BasePage.OnAppViewListener onAppViewListener) {
        this.mOnAppViewListener = onAppViewListener;
    }

    public BasePage.OnAppViewListener getOnAppViewListener() {
        return this.mOnAppViewListener;
    }
}
