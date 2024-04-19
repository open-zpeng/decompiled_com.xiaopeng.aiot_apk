package com.xiaopeng.aiot.device.watches.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.xiaopeng.iotlib.provider.map.MapUtils;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.widget.XSwitch;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AppView extends BaseAppView {
    private static final int TIME = 3000;
    private TextView mBtn;
    private Handler mHandler;
    private IWatchesVM mIViewData;
    private View mInfoView;
    private boolean mIsLogin;
    private Runnable mRunnable;
    private TextView mTextInfo;
    private TextView mTextState;
    private WatchesDevice mWatchesDevice;
    private XSwitch xSwitch;

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "watches-";
    }

    public AppView(Context context) {
        this(context, null);
    }

    public AppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRunnable = new Runnable() { // from class: com.xiaopeng.aiot.device.watches.ui.AppView.1
            @Override // java.lang.Runnable
            public void run() {
                if (AppView.this.mIViewData != null) {
                    AppView.this.mIViewData.questLocation();
                    AppView.this.mHandler.postDelayed(AppView.this.mRunnable, 3000L);
                }
            }
        };
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.watches_main_app_view, this);
        this.mTextState = (TextView) findViewById(R.id.watches_state);
        this.mBtn = (TextView) findViewById(R.id.watches_btn);
        this.mBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView$5HO91I1J0GD9KdJQOX8wUeBpdWI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$0$AppView(view);
            }
        });
        this.mInfoView = findViewById(R.id.watches_info_lay);
        findViewById(R.id.watches_btn_phone).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView$-IpVITOfvlZE_WvpC5wJgeZabNs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$1$AppView(view);
            }
        });
        findViewById(R.id.watches_btn_navigation).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView$5iHgdOgInuSkkPAP1YoAnoQsYP4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$2$AppView(view);
            }
        });
        this.xSwitch = (XSwitch) findViewById(R.id.watches_switch_location);
        this.mTextInfo = (TextView) findViewById(R.id.watches_info);
        this.xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView$4oJJ-uqHdyIbqBNlb8kJuDalS9I
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AppView.this.lambda$init$3$AppView(compoundButton, z);
            }
        });
        final XSwitch xSwitch = (XSwitch) findViewById(R.id.watches_switch_refresh);
        xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView$ghBBqT4KlXMLojo6VnIL6txoEJo
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AppView.this.lambda$init$4$AppView(xSwitch, compoundButton, z);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$AppView(View view) {
        login();
    }

    public /* synthetic */ void lambda$init$1$AppView(View view) {
        IWatchesVM iWatchesVM = this.mIViewData;
        if (iWatchesVM != null) {
            iWatchesVM.callPhone();
        }
    }

    public /* synthetic */ void lambda$init$2$AppView(View view) {
        WatchesDevice watchesDevice = this.mWatchesDevice;
        if (watchesDevice != null) {
            MapUtils.navigation(watchesDevice.getLatitude(), this.mWatchesDevice.getLongitude());
        }
    }

    public /* synthetic */ void lambda$init$3$AppView(CompoundButton compoundButton, boolean z) {
        IWatchesVM iWatchesVM;
        if (this.xSwitch.isFromUser() && (iWatchesVM = this.mIViewData) != null) {
            iWatchesVM.setMarkEnable(z);
        }
    }

    public /* synthetic */ void lambda$init$4$AppView(XSwitch xSwitch, CompoundButton compoundButton, boolean z) {
        if (xSwitch.isFromUser()) {
            logI("refreshSwitch : " + z);
            this.mHandler.removeCallbacks(this.mRunnable);
            if (z) {
                this.mHandler.postDelayed(this.mRunnable, 3000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setViewData(IWatchesVM iWatchesVM) {
        this.mIViewData = iWatchesVM;
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        if (this.mIViewData == null) {
            logI(" onCreate mIViewData is null ");
            return;
        }
        if (this.mHandler == null) {
            this.mHandler = new Handler();
        }
        this.mHandler.postDelayed(this.mRunnable, 3000L);
        this.mIViewData.getState().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView$94mxT3GhpQyTNOd6HkGOi-W0vwE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$5$AppView((Boolean) obj);
            }
        });
        this.mIViewData.getWatches().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView$Xg5kdsYf7gLnDa7P17kxZvGvkbQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$6$AppView((WatchesDevice) obj);
            }
        });
        this.mIViewData.getMarkEnable().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.watches.ui.-$$Lambda$AppView$baBWRRGWhLqkKd-AnAYOHTXUdwc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$7$AppView((Boolean) obj);
            }
        });
        this.mIViewData.getResult().observe(this, new Observer<Integer>() { // from class: com.xiaopeng.aiot.device.watches.ui.AppView.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer num) {
                if (num == null) {
                    return;
                }
                int intValue = num.intValue();
                if (intValue == -101) {
                    XToast.show("手表状态：账号登出，意味着手表不可用");
                } else if (intValue == -100) {
                    XToast.show("绑定状态:查询失败");
                } else if (intValue == -4) {
                    XToast.show("服务器查询数据结果:从服务器查询位置信息失败");
                } else if (intValue == -3) {
                    XToast.show("服务器查询数据结果:请求更新手表主动更新位置失败");
                } else if (intValue == -2) {
                    XToast.show("服务器查询数据结果:信号、状态、电量查询失败");
                } else if (intValue != -1) {
                } else {
                    XToast.show("服务器查询数据结果:查询绑定关系失败");
                }
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$5$AppView(Boolean bool) {
        this.mIsLogin = bool.booleanValue();
        refresh();
    }

    public /* synthetic */ void lambda$onCreate$6$AppView(WatchesDevice watchesDevice) {
        this.mWatchesDevice = watchesDevice;
        refresh();
    }

    public /* synthetic */ void lambda$onCreate$7$AppView(Boolean bool) {
        if (bool == null) {
            return;
        }
        this.xSwitch.setChecked(bool.booleanValue(), false);
    }

    private void refresh() {
        logI("refresh " + this.mIsLogin + " , " + this.mWatchesDevice);
        if (this.mIsLogin) {
            this.mBtn.setVisibility(4);
            WatchesDevice watchesDevice = this.mWatchesDevice;
            if (watchesDevice == null) {
                this.mTextState.setText("未获取到设备");
                this.mInfoView.setVisibility(4);
                return;
            }
            int state = watchesDevice.getState();
            if (state == -100) {
                this.mTextState.setText("查询失败");
                this.mInfoView.setVisibility(0);
                this.mTextInfo.setText(watchesDevice.toString());
                return;
            } else if (state == 0) {
                this.mTextState.setText("未获取到设备");
                this.mInfoView.setVisibility(4);
                return;
            } else if (state != 1) {
                this.mTextState.setText("正在查询中..........");
                this.mInfoView.setVisibility(4);
                return;
            } else {
                this.mTextState.setText("已获取到设备");
                this.mInfoView.setVisibility(0);
                this.mTextInfo.setText(watchesDevice.toString());
                return;
            }
        }
        this.mTextState.setText("未登录，显示添加设备，提示用户登录");
        this.mBtn.setVisibility(0);
        this.mInfoView.setVisibility(4);
    }

    void login() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(VuiConstants.CENTER, "com.xiaopeng.caraccount.MainActivity"));
        getContext().startActivity(intent);
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mRunnable);
        }
    }
}
