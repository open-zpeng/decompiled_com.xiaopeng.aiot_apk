package com.xiaopeng.iotlib.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import java.lang.ref.WeakReference;
/* loaded from: classes.dex */
public abstract class BasePage<K extends BaseAppView> implements LifecycleObserver {
    private static final boolean ASYNC_LOADING_VIEW = true;
    private K mBaseView;
    private boolean mIsDestroy;
    private RelativeLayout mLayout;
    private OnAppViewListener mOnAppViewListener;
    private OnPageListener mOnPageListener;

    /* loaded from: classes.dex */
    public interface OnAppViewListener {
        void onDismiss();

        void onRefreshBackground(int i);

        void onRefreshBackground(Drawable drawable);
    }

    /* loaded from: classes.dex */
    public interface OnPageListener {
        void onAttachView(BasePage basePage);
    }

    protected abstract K onCreateAppView(Context context);

    protected abstract RelativeLayout.LayoutParams onCreateLayoutParams(Resources resources);

    protected abstract void onCreateViewModel(ViewModelProvider viewModelProvider, K k);

    public BasePage(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void createView(Context context, ViewModelStoreOwner viewModelStoreOwner) {
        logD("createView");
        this.mLayout = new RelativeLayout(context);
        asyncInitView(context, viewModelStoreOwner);
        this.mLayout.setGravity(17);
    }

    public View getPageView() {
        return this.mLayout;
    }

    private void asyncInitView(Context context, final ViewModelStoreOwner viewModelStoreOwner) {
        final WeakReference weakReference = new WeakReference(context);
        final WeakReference weakReference2 = new WeakReference(viewModelStoreOwner);
        ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.iotlib.base.-$$Lambda$BasePage$KYXq0KEH166xfbGIqCTLXQW9ytw
            @Override // java.lang.Runnable
            public final void run() {
                BasePage.this.lambda$asyncInitView$1$BasePage(weakReference, weakReference2, viewModelStoreOwner);
            }
        });
    }

    public /* synthetic */ void lambda$asyncInitView$1$BasePage(final WeakReference weakReference, WeakReference weakReference2, ViewModelStoreOwner viewModelStoreOwner) {
        if (this.mIsDestroy) {
            logI("asyncInitView mIsDestroy is true ");
            return;
        }
        Context context = (Context) weakReference.get();
        ViewModelStoreOwner viewModelStoreOwner2 = (ViewModelStoreOwner) weakReference2.get();
        if (context == null || viewModelStoreOwner2 == null) {
            logI("asyncInitView context or owner is null ");
            return;
        }
        this.mBaseView = onCreateAppView(context);
        onCreateViewModel(new ViewModelProvider(viewModelStoreOwner), this.mBaseView);
        this.mLayout.post(new Runnable() { // from class: com.xiaopeng.iotlib.base.-$$Lambda$BasePage$yCNEVCSGL-tEt9JL-kVrNTjXEMI
            @Override // java.lang.Runnable
            public final void run() {
                BasePage.this.lambda$null$0$BasePage(weakReference);
            }
        });
    }

    public /* synthetic */ void lambda$null$0$BasePage(WeakReference weakReference) {
        if (this.mIsDestroy) {
            logI("asyncInitView mIsDestroy is true ");
            return;
        }
        Context context = (Context) weakReference.get();
        if (context == null) {
            logI("asyncInitView context is null ");
        } else {
            attachView(context);
        }
    }

    private void attachView(Context context) {
        K k = this.mBaseView;
        if (k == null) {
            logI("attachView mBaseView is null ");
            return;
        }
        OnAppViewListener onAppViewListener = this.mOnAppViewListener;
        if (onAppViewListener != null) {
            k.setOnAppViewListener(onAppViewListener);
        }
        this.mBaseView.setLayoutParams(onCreateLayoutParams(context.getResources()));
        this.mLayout.addView(this.mBaseView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(350L);
        this.mBaseView.startAnimation(alphaAnimation);
        OnPageListener onPageListener = this.mOnPageListener;
        if (onPageListener != null) {
            onPageListener.onAttachView(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        this.mIsDestroy = ASYNC_LOADING_VIEW;
        logD("destroy");
    }

    public void setOnPageListener(OnPageListener onPageListener) {
        this.mOnPageListener = onPageListener;
    }

    public void setOnAppViewListener(OnAppViewListener onAppViewListener) {
        this.mOnAppViewListener = onAppViewListener;
    }

    protected void logD(String str) {
        LogUtils.d(LogConfig.TAG_PAGE, getClass().getSimpleName() + " " + hashCode() + "-" + str, 1);
    }

    protected void logI(String str) {
        LogUtils.i(LogConfig.TAG_PAGE, getClass().getSimpleName() + " " + hashCode() + "-" + str, 1);
    }
}
