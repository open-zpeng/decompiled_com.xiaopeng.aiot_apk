package com.xiaopeng.iotlib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
@Deprecated
/* loaded from: classes.dex */
public abstract class BaseFragment<K extends BaseAppView> extends Fragment {
    protected K mBaseView;

    protected abstract K onCreateAppView(Context context);

    protected abstract FrameLayout.LayoutParams onCreateLayoutParams();

    protected abstract void onCreateViewModel(ViewModelProvider viewModelProvider);

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.iotlib.base.-$$Lambda$BaseFragment$CrnzaFMFb4M5aae4UfLdA7d0Mng
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$onCreateView$1$BaseFragment(frameLayout);
            }
        });
        return frameLayout;
    }

    public /* synthetic */ void lambda$onCreateView$1$BaseFragment(final ViewGroup viewGroup) {
        this.mBaseView = onCreateAppView(viewGroup.getContext());
        ThreadUtils.UI.post(new Runnable() { // from class: com.xiaopeng.iotlib.base.-$$Lambda$BaseFragment$stsgmKKQI5n3dQJv5LJMjfiPXHU
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment.this.lambda$null$0$BaseFragment(viewGroup);
            }
        });
    }

    public /* synthetic */ void lambda$null$0$BaseFragment(ViewGroup viewGroup) {
        if (isDetached() || isRemoving() || getContext() == null) {
            return;
        }
        this.mBaseView.setLayoutParams(onCreateLayoutParams());
        onCreateViewModel(new ViewModelProvider(this));
        viewGroup.addView(this.mBaseView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(350L);
        this.mBaseView.startAnimation(alphaAnimation);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        logI("onCreate");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        logI("onDestroy");
    }

    protected void logD(String str) {
        LogUtils.d(LogConfig.TAG_FRAGMENT, getClass().getSimpleName() + " " + hashCode() + "-" + str);
    }

    protected void logI(String str) {
        LogUtils.i(LogConfig.TAG_FRAGMENT, getClass().getSimpleName() + " " + hashCode() + "-" + str);
    }
}
