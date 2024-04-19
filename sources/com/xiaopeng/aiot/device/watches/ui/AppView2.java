package com.xiaopeng.aiot.device.watches.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.aiot.device.watches.vm.IWatchesVM;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.widget.XSwitch;
@Deprecated
/* loaded from: classes.dex */
class AppView2 extends BaseAppView {
    private IWatchesVM mIViewData;
    private View mInfoView;
    private boolean mIsLogin;
    private boolean mRequesting;
    private TextView mTextInfo;
    private TextView mTextState;
    private WatchesDevice mWatchesDevice;
    private XSwitch xSwitch;

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "watches-";
    }

    public AppView2(Context context) {
        this(context, null);
    }

    public AppView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.watches_main_app_view, this);
        this.mTextState = (TextView) findViewById(R.id.watches_state);
        this.mInfoView = findViewById(R.id.watches_info_lay);
        findViewById(R.id.watches_btn_phone).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView2$f6JLghxJtsD3iuYEKmdt2Cm6xOs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView2.this.lambda$init$0$AppView2(view);
            }
        });
        findViewById(R.id.watches_btn_navigation).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView2$OhZdK066vyJ6AR8SurM9CyDrTUo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                XToast.show("unity直接调用导航方法，这里无法测试");
            }
        });
        this.xSwitch = (XSwitch) findViewById(R.id.watches_switch_location);
        this.mTextInfo = (TextView) findViewById(R.id.watches_info);
        this.xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView2$7BRIreWB4pFu3ZRL5l_dx7uebt8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AppView2.this.lambda$init$2$AppView2(compoundButton, z);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$AppView2(View view) {
        IWatchesVM iWatchesVM = this.mIViewData;
        if (iWatchesVM != null) {
            iWatchesVM.callPhone();
        }
    }

    public /* synthetic */ void lambda$init$2$AppView2(CompoundButton compoundButton, boolean z) {
        IWatchesVM iWatchesVM;
        if (this.xSwitch.isFromUser() && (iWatchesVM = this.mIViewData) != null) {
            iWatchesVM.setMarkEnable(z);
        }
    }

    void setViewData(IWatchesVM iWatchesVM) {
        this.mIViewData = iWatchesVM;
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        IWatchesVM iWatchesVM = this.mIViewData;
        if (iWatchesVM == null) {
            logI(" onCreate mIViewData is null ");
            return;
        }
        this.mRequesting = true;
        iWatchesVM.getState().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView2$ngCU1nSP0XSlazCUIK8ePYSXctU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView2.this.lambda$onCreate$3$AppView2((Boolean) obj);
            }
        });
        this.mIViewData.getWatches().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView2$b5Gd0ypAteQGR_7tOAxg4NO2FWQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView2.this.lambda$onCreate$4$AppView2((WatchesDevice) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$3$AppView2(Boolean bool) {
        this.mIsLogin = bool.booleanValue();
        refresh();
    }

    public /* synthetic */ void lambda$onCreate$4$AppView2(WatchesDevice watchesDevice) {
        this.mWatchesDevice = watchesDevice;
        this.mRequesting = false;
        refresh();
    }

    private void refresh() {
        logI("refresh " + this.mIsLogin + " , " + this.mWatchesDevice);
        if (this.mIsLogin) {
            this.mTextState.setText("未登录，显示添加设备，提示用户登录");
            this.mInfoView.setVisibility(4);
        } else if (this.mRequesting) {
            this.mTextState.setText("已登录，显示loading，正在获取设备中");
            this.mInfoView.setVisibility(4);
        } else {
            WatchesDevice watchesDevice = this.mWatchesDevice;
            if (watchesDevice == null) {
                this.mTextState.setText("未获取到设备");
                this.mInfoView.setVisibility(4);
                return;
            }
            this.mTextState.setText("已获取到设备");
            this.mInfoView.setVisibility(0);
            this.mTextInfo.setText(watchesDevice.toString());
        }
    }
}
