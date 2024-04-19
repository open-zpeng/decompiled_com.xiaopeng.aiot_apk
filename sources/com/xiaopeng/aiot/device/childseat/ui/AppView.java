package com.xiaopeng.aiot.device.childseat.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.Observer;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.childseat.data.ChildSeatDevice;
import com.xiaopeng.aiot.device.childseat.ui.AppView;
import com.xiaopeng.aiot.device.childseat.ui.Group;
import com.xiaopeng.aiot.device.childseat.vm.IChildSeatVM;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.iotlib.base.BaseActivity;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AppView extends BaseAppView {
    private ChildSeatDevice mChildSeatDevice;
    private Group[] mGroup;
    private View mGroupBleCloseView;
    private View mGroupConnectView;
    private View mGroupConnectingView;
    private View mGroupUnbindView;
    private Group.IActionListener mIActionListener;
    private IChildSeatVM mIViewData;
    private ImageView mImageViewBg;
    private Receiver mReceiver;
    private View[] mViews;
    private XDialog mXDialog;

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "childseat-";
    }

    public AppView(Context context) {
        this(context, null);
    }

    public AppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mViews = new View[4];
        this.mGroup = new Group[4];
        this.mIActionListener = new AnonymousClass1();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.childseat_main_app_view, this);
        this.mImageViewBg = (ImageView) findViewById(R.id.childseat_bg);
        this.mGroupUnbindView = findViewById(R.id.childseat_unbind);
        this.mGroupConnectView = findViewById(R.id.childseat_connect);
        this.mGroupBleCloseView = findViewById(R.id.childseat_bleclose);
        this.mGroupConnectingView = findViewById(R.id.childseat_connecting);
        View[] viewArr = this.mViews;
        View view = this.mGroupUnbindView;
        viewArr[0] = view;
        viewArr[1] = this.mGroupConnectView;
        viewArr[2] = this.mGroupBleCloseView;
        viewArr[3] = this.mGroupConnectingView;
        this.mGroup[0] = new GroupUnbind(this.mIActionListener, view);
        this.mGroup[1] = new GroupConnect(this.mIActionListener, this.mGroupConnectView);
        this.mGroup[2] = new GroupBleClose(this.mIActionListener, this.mGroupBleCloseView);
        this.mGroup[3] = new GroupConnecting(this.mIActionListener, this.mGroupConnectingView);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setViewData(IChildSeatVM iChildSeatVM) {
        this.mIViewData = iChildSeatVM;
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        IChildSeatVM iChildSeatVM = this.mIViewData;
        if (iChildSeatVM == null) {
            logI(" onCreate mIViewData is null ");
            return;
        }
        iChildSeatVM.getChildSeat().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.childseat.ui.-$$Lambda$AppView$seE747kydqTqAP1W3FbN8luYEh8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$0$AppView((ChildSeatDevice) obj);
            }
        });
        registerReceiver();
    }

    public /* synthetic */ void lambda$onCreate$0$AppView(ChildSeatDevice childSeatDevice) {
        if (childSeatDevice == null) {
            return;
        }
        this.mChildSeatDevice = childSeatDevice;
        refresh();
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onStop() {
        super.onStop();
        XDialog xDialog = this.mXDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
    }

    private void refresh() {
        if (this.mChildSeatDevice == null) {
            return;
        }
        logI("refresh childSeatDevice:" + this.mChildSeatDevice);
        int status = this.mChildSeatDevice.getStatus();
        if (status == -1) {
            this.mImageViewBg.setImageResource(R.drawable.childseat_bg);
            showOnlyView(this.mGroupUnbindView);
        } else if (status == 1 || status == 2) {
            showOnlyView(this.mGroupBleCloseView);
            this.mImageViewBg.setImageResource(R.drawable.childseat_bg);
            XDialog xDialog = this.mXDialog;
            if (xDialog != null) {
                xDialog.dismiss();
            }
        } else if (status == 3) {
            showOnlyView(this.mGroupConnectingView);
            this.mImageViewBg.setImageResource(R.drawable.childseat_bg);
        } else if (status == 4) {
            showOnlyView(this.mGroupConnectView);
            this.mImageViewBg.setImageResource(R.drawable.childseat_bg_uninstalled);
        } else if (status == 5) {
            showOnlyView(this.mGroupConnectView);
            this.mImageViewBg.setImageResource(R.drawable.childseat_bg_installed);
        }
        for (Group group : this.mGroup) {
            group.refresh(this.mChildSeatDevice);
        }
    }

    private void showOnlyView(View view) {
        View[] viewArr;
        view.setVisibility(0);
        for (View view2 : this.mViews) {
            if (view2 != view) {
                view2.setVisibility(4);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.aiot.device.childseat.ui.AppView$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements Group.IActionListener {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.aiot.device.childseat.ui.Group.IActionListener
        public void onBind() {
            if (AppView.this.mIViewData != null) {
                PageConfigFactory.BLUE_CHILDSEAT.go(AppView.this.getContext());
            }
        }

        @Override // com.xiaopeng.aiot.device.childseat.ui.Group.IActionListener
        public void onUnBind() {
            AppView appView = AppView.this;
            appView.logD("show unBundling dialog " + AppView.this.mXDialog);
            if (AppView.this.mXDialog == null) {
                AppView appView2 = AppView.this;
                appView2.mXDialog = new XDialog(appView2.getContext()).setTitle(R.string.childseat_unbundling_dialog_title).setCancelable(false).setMessage(R.string.childseat_unbundling_dialog_msg).setPositiveButton(R.string.childseat_unbundling_dialog_button_ok, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.aiot.device.childseat.ui.-$$Lambda$AppView$1$1WWi-l07jHJQKkDMQo1Du6QepDA
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        AppView.AnonymousClass1.this.lambda$onUnBind$0$AppView$1(xDialog, i);
                    }
                }).setNegativeButton(R.string.childseat_unbundling_dialog_button_cancel);
            }
            AppView.this.mXDialog.show();
        }

        public /* synthetic */ void lambda$onUnBind$0$AppView$1(XDialog xDialog, int i) {
            if (AppView.this.mIViewData != null) {
                AppView.this.mIViewData.unBundling();
            } else {
                AppView.this.logD("show unBundling mIViewData is null ");
            }
        }

        @Override // com.xiaopeng.aiot.device.childseat.ui.Group.IActionListener
        public void onOpenBle() {
            if (AppView.this.mIViewData != null) {
                AppView.this.mIViewData.openBluetooth();
            }
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XThemeManager.isThemeChanged(configuration)) {
            for (Group group : this.mGroup) {
                group.refreshDayOrNight();
            }
        }
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    private void registerReceiver() {
        if (this.mReceiver != null || getContext() == null) {
            return;
        }
        this.mReceiver = new Receiver(this, null);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        getContext().registerReceiver(this.mReceiver, intentFilter);
        logI("onReceive--registerReceiver");
    }

    private void unregisterReceiver() {
        if (this.mReceiver == null || getContext() == null) {
            return;
        }
        getContext().unregisterReceiver(this.mReceiver);
        this.mReceiver = null;
        logI("onReceive--unregisterReceiver");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismiss() {
        if (getContext() == null || !(getContext() instanceof BaseActivity)) {
            return;
        }
        ((BaseActivity) getContext()).dismissActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class Receiver extends BroadcastReceiver {
        private static final String ACTION = "android.intent.action.PACKAGE_REMOVED";
        private static final String PKG = "package:com.xp.childseat";

        private Receiver() {
        }

        /* synthetic */ Receiver(AppView appView, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String dataString = intent.getDataString();
            AppView appView = AppView.this;
            appView.logI("onReceive--" + action + " , " + dataString);
            if (ACTION.equals(action) && PKG.equals(dataString)) {
                AppView.this.dismiss();
            }
        }
    }
}
