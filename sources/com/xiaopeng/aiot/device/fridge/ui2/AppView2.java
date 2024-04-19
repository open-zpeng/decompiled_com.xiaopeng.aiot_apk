package com.xiaopeng.aiot.device.fridge.ui2;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fridge.data.FridgeDevice;
import com.xiaopeng.aiot.device.fridge.vm2.IFridgeVM2;
import com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.iotlib.base.BaseActivity;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.iotlib.model.sound.AssetsSoundSource;
import com.xiaopeng.iotlib.model.sound.SoundManager;
import com.xiaopeng.iotlib.provider.voice.vui.VuiViewHelper;
import com.xiaopeng.iotlib.utils.MultipleClickHelper;
import com.xiaopeng.iotlib.utils.WebpUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XSegmented;
import com.xiaopeng.xui.widget.XSwitch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AppView2 extends BaseAppView {
    private int mBgIndex;
    private int mBleState;
    private View mBtnBind;
    private View mBtnBleClose;
    private View mBtnMaintainService;
    private final HashMap<String, Integer> mErrorString;
    private TextView mErrorViewOpen;
    private final ArrayList<TextView> mErrorViews;
    private XImageView mFridgeBg;
    private FridgeDevice mFridgeDevice;
    private XImageView mFridgeWebPBg;
    private boolean mHasBindDevice;
    private IFridgeVM2 mIViewData;
    private TextView mIndicatorText;
    private boolean mInitSwitch;
    private String mLastErrorCode;
    private ViewGroup mLayWarning;
    private boolean mOnlyOpenDoor;
    private boolean mPowerState;
    private XSwitch mPowerSwitch;
    private final ArrayList<TextView> mRecycleErrorViews;
    private XSegmented mSegmented;
    private TextView mShutDownTime;
    private TextView mViewCurrent;
    private View mWarningBleLoading;
    private View mWarningScrollView;

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "fridge-";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppView2(Context context) {
        this(context, null);
    }

    AppView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mErrorString = new HashMap<>();
        this.mErrorViews = new ArrayList<>();
        this.mRecycleErrorViews = new ArrayList<>();
        this.mBleState = -1;
        this.mInitSwitch = true;
        this.mBgIndex = -1;
        this.mOnlyOpenDoor = false;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.fridge2_main_app_view, this);
        this.mFridgeBg = (XImageView) findViewById(R.id.fridge_bg);
        this.mFridgeWebPBg = (XImageView) findViewById(R.id.fridge_webp_bg);
        this.mViewCurrent = (TextView) findViewById(R.id.temperature_current);
        this.mIndicatorText = (TextView) findViewById(R.id.tv_indicator);
        this.mSegmented = (XSegmented) findViewById(R.id.segmented);
        this.mWarningScrollView = findViewById(R.id.warning_scrollView);
        this.mLayWarning = (ViewGroup) findViewById(R.id.warning_lay);
        this.mBtnBleClose = findViewById(R.id.btn_bluetooth_close);
        this.mBtnMaintainService = findViewById(R.id.btn_maintain_service);
        this.mShutDownTime = (TextView) findViewById(R.id.shutdown_time);
        this.mPowerSwitch = (XSwitch) findViewById(R.id.fridge_power_switch);
        this.mWarningBleLoading = findViewById(R.id.waring_ble_loading);
        this.mBtnBind = findViewById(R.id.btn_bluetooth_bind);
        this.mSegmented.setSegmentListener(new XSegmented.SegmentListener() { // from class: com.xiaopeng.aiot.device.fridge.ui2.AppView2.1
            @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
            public boolean onInterceptChange(XSegmented xSegmented, int i) {
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
            public void onSelectionChange(XSegmented xSegmented, int i, boolean z) {
                AppView2 appView2 = AppView2.this;
                appView2.logI("onSelectionChange index : " + i + ", fromUser " + z);
                AppView2.this.onTemperatureSetChange(z, i);
            }
        });
        this.mSegmented.setSelection(-1);
        this.mSegmented.setEnabled(false);
        this.mPowerSwitch.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$m-qGAEgAWuXiLGbUgS65WVdlsRc
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return AppView2.this.lambda$init$1$AppView2(view, z);
            }
        });
        this.mBtnBleClose.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$McwER14Y7uJJRhSouWtyFBL24Zw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView2.this.lambda$init$2$AppView2(view);
            }
        });
        this.mShutDownTime.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$ECsfFFdw1mJJPxXhRTPP7RTmUg4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView2.this.lambda$init$3$AppView2(view);
            }
        });
        this.mBtnMaintainService.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$7_sXtvXhV3M9b9svqTzGDdieXCA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView2.this.lambda$init$4$AppView2(view);
            }
        });
        this.mErrorString.put("1", Integer.valueOf((int) R.string.fridge_warning_e1));
        this.mErrorString.put("2", Integer.valueOf((int) R.string.fridge_warning_e2));
        this.mErrorString.put("3", Integer.valueOf((int) R.string.fridge_warning_e3));
        this.mErrorString.put(BuildInfoUtils.BID_LAN, Integer.valueOf((int) R.string.fridge_warning_e4));
        this.mErrorString.put(BuildInfoUtils.BID_PT_SPECIAL_1, Integer.valueOf((int) R.string.fridge_warning_e5));
        this.mErrorString.put(BuildInfoUtils.BID_PT_SPECIAL_2, Integer.valueOf((int) R.string.fridge_warning_e6));
        VuiViewHelper.addHasFeedbackProp(this.mShutDownTime);
        VuiViewHelper.addHasFeedbackProp(this.mSegmented);
        MultipleClickHelper.bind(this.mIndicatorText, new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$Ks3bXqJPaJBtaPu4Cdul5YjZoM8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView2.this.lambda$init$5$AppView2(view);
            }
        });
        this.mBtnBind.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$j6-ZsHwETSftjqNgyLHQdfPlwwU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView2.this.lambda$init$6$AppView2(view);
            }
        });
    }

    public /* synthetic */ boolean lambda$init$1$AppView2(View view, boolean z) {
        boolean z2 = this.mPowerSwitch.isFromUser() || VuiViewHelper.isVuiEvent(view);
        logI("onInterceptCheck isChecked : " + z + ", fromUser " + z2 + " , enable " + this.mPowerSwitch.isEnabled());
        if (z2 && this.mIViewData != null) {
            this.mPowerSwitch.setEnabled(false);
            this.mPowerSwitch.postDelayed(new Runnable() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$ypiakCOFHZlll-MGXIXzXxOIuPg
                @Override // java.lang.Runnable
                public final void run() {
                    AppView2.this.lambda$null$0$AppView2();
                }
            }, 1000L);
            this.mIViewData.setPowerSwitch(z);
            XSoundEffectManager.get().play(z ? 3 : 4);
            BuriedPointManager.get().getIFridgeBP().enable(z);
        }
        return z2;
    }

    public /* synthetic */ void lambda$null$0$AppView2() {
        this.mPowerSwitch.setEnabled(true);
    }

    public /* synthetic */ void lambda$init$2$AppView2(View view) {
        if (this.mIViewData != null) {
            this.mBtnBleClose.setEnabled(false);
            this.mIViewData.openBluetooth();
        }
    }

    public /* synthetic */ void lambda$init$3$AppView2(View view) {
        IFridgeVM2 iFridgeVM2 = this.mIViewData;
        if (iFridgeVM2 != null) {
            iFridgeVM2.openTimeSetDialog();
        }
    }

    public /* synthetic */ void lambda$init$4$AppView2(View view) {
        IFridgeVM2 iFridgeVM2 = this.mIViewData;
        if (iFridgeVM2 != null) {
            iFridgeVM2.callMaintainService(getContext().getString(R.string.fridge2_maintain_number));
        }
    }

    public /* synthetic */ void lambda$init$5$AppView2(View view) {
        unBundling();
    }

    public /* synthetic */ void lambda$init$6$AppView2(View view) {
        bind();
    }

    private void bind() {
        if (!this.mPowerState || this.mHasBindDevice) {
            return;
        }
        logI("observe PageConfigFactory.BLUE.go!!!");
        PageConfigFactory.BLUE_FRIDGE.go(getContext());
    }

    private void unBundling() {
        logD("show unBundling dialog");
        if (this.mIViewData != null && !this.mHasBindDevice) {
            logI("show unBundling not device");
        } else {
            new XDialog(getContext()).setTitle(R.string.fridge_unbundling_dialog_title).setCancelable(false).setMessage(R.string.fridge_unbundling_dialog_msg).setPositiveButton(R.string.fridge_unbundling_dialog_button_ok, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$RMlsOBkMUeMFDM5T1l_Fukyk88s
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    AppView2.this.lambda$unBundling$7$AppView2(xDialog, i);
                }
            }).setNegativeButton(R.string.cancel).show();
        }
    }

    public /* synthetic */ void lambda$unBundling$7$AppView2(XDialog xDialog, int i) {
        IFridgeVM2 iFridgeVM2 = this.mIViewData;
        if (iFridgeVM2 != null && iFridgeVM2.unBundling() && (getContext() instanceof BaseActivity)) {
            ((BaseActivity) getContext()).dismissActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTemperatureSetChange(boolean z, int i) {
        IFridgeVM2 iFridgeVM2;
        if (!z || (iFridgeVM2 = this.mIViewData) == null) {
            return;
        }
        if (i == 0) {
            iFridgeVM2.setTemperature(102);
            SoundManager.get().playAssetSound(AssetsSoundSource.FRIDGE_TEMPERATURE_SIX, 1);
            BuriedPointManager.get().getIFridgeBP().setTemperature(3);
        } else if (i == 1) {
            iFridgeVM2.setTemperature(101);
            SoundManager.get().playAssetSound(AssetsSoundSource.FRIDGE_TEMPERATURE_ZERO, 1);
            BuriedPointManager.get().getIFridgeBP().setTemperature(2);
        } else if (i == 2) {
            iFridgeVM2.setTemperature(100);
            SoundManager.get().playAssetSound(AssetsSoundSource.FRIDGE_TEMPERATURE_SUBZERO_SIX, 1);
            BuriedPointManager.get().getIFridgeBP().setTemperature(1);
        } else {
            logI("onSelectionChange error!!! index : " + i + ", fromUser " + z);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setViewData(IFridgeVM2 iFridgeVM2) {
        this.mIViewData = iFridgeVM2;
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        IFridgeVM2 iFridgeVM2 = this.mIViewData;
        if (iFridgeVM2 == null) {
            logI(" onCreate mIViewData is null ");
            return;
        }
        iFridgeVM2.getPowerSwitch().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$quF9ClQ-qUKQeRi2ohQ9cDlIm0Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView2.this.lambda$onCreate$8$AppView2((Boolean) obj);
            }
        });
        this.mIViewData.getTimeSet().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$IAKyNnWHSVn41gmgUy4wvhXj96c
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView2.this.lambda$onCreate$9$AppView2((String) obj);
            }
        });
        this.mIViewData.getBlueState().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$n8PkD8I3LrzFLkIa2nn-suHnQng
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView2.this.lambda$onCreate$10$AppView2((Integer) obj);
            }
        });
        this.mIViewData.getFridge().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.fridge.ui2.-$$Lambda$AppView2$bcLPZd8BTPydT1Ia-oQRr6zw4l0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView2.this.lambda$onCreate$11$AppView2((FridgeDevice) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$8$AppView2(Boolean bool) {
        logI("observe PowerSwitch:" + bool);
        this.mPowerState = bool.booleanValue();
        refreshPowerState();
        refreshBle();
        refreshView(this.mFridgeDevice);
    }

    public /* synthetic */ void lambda$onCreate$9$AppView2(String str) {
        logI("observe TimeSet:" + str);
        this.mShutDownTime.setText(str);
    }

    public /* synthetic */ void lambda$onCreate$10$AppView2(Integer num) {
        if (num == null) {
            return;
        }
        logI("observe BlueState:" + num);
        this.mBleState = num.intValue();
        refreshBle();
        refreshView(this.mFridgeDevice);
    }

    public /* synthetic */ void lambda$onCreate$11$AppView2(FridgeDevice fridgeDevice) {
        if (fridgeDevice == null) {
            return;
        }
        logI("observe Fridge :" + fridgeDevice.toString());
        this.mHasBindDevice = fridgeDevice.isHasBinding();
        refreshView(fridgeDevice);
        refreshBle();
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onStop() {
        super.onStop();
        SoundManager.get().stopMedia();
    }

    private void refreshPowerState() {
        if (this.mPowerState) {
            this.mPowerSwitch.setChecked(true, !this.mInitSwitch);
            this.mShutDownTime.setEnabled(true);
        } else {
            this.mPowerSwitch.setChecked(false, true ^ this.mInitSwitch);
            this.mShutDownTime.setEnabled(false);
        }
        this.mInitSwitch = false;
    }

    private void refreshBle() {
        if (!this.mPowerState || !this.mHasBindDevice) {
            this.mBtnBleClose.setVisibility(4);
            return;
        }
        switch (this.mBleState) {
            case 10:
                this.mBtnBleClose.setEnabled(true);
                this.mBtnBleClose.setVisibility(0);
                return;
            case 11:
                this.mBtnBleClose.setEnabled(false);
                this.mBtnBleClose.setVisibility(0);
                return;
            case 12:
            case 13:
                this.mBtnBleClose.setVisibility(4);
                return;
            default:
                logI("observe BlueState default:" + this.mBleState);
                return;
        }
    }

    private void refreshLoading() {
        FridgeDevice fridgeDevice = this.mFridgeDevice;
        int connect = fridgeDevice != null ? fridgeDevice.getConnect() : 101;
        logD("refreshLoading-connect : " + connect + " ,power : " + this.mPowerState + ", ble : " + this.mBleState);
        if (this.mPowerState && this.mHasBindDevice && this.mBleState == 12 && connect != 100) {
            this.mIndicatorText.setText(R.string.fridge_warning_disconnect);
            this.mWarningBleLoading.setVisibility(0);
            return;
        }
        this.mIndicatorText.setText(R.string.fridge_temperature_current);
        this.mWarningBleLoading.setVisibility(4);
    }

    private void refreshView(FridgeDevice fridgeDevice) {
        boolean z;
        if (fridgeDevice == null) {
            return;
        }
        logD("refreshView Connect : " + fridgeDevice.getConnect() + " , BleState : " + this.mBleState);
        this.mFridgeDevice = fridgeDevice;
        refreshLoading();
        int i = 0;
        if (this.mPowerState && !this.mHasBindDevice) {
            this.mBtnBind.setVisibility(0);
        } else {
            this.mBtnBind.setVisibility(4);
        }
        if (this.mPowerState && this.mHasBindDevice && this.mBleState == 12 && fridgeDevice.getConnect() == 100) {
            if (fridgeDevice.getTemperature() != null) {
                this.mViewCurrent.setText(fridgeDevice.getTemperature().replace("+", ""));
                z = true;
            } else {
                this.mViewCurrent.setText(R.string.fridge2_temperature_current_error);
                z = false;
            }
            boolean isEnabled = this.mSegmented.isEnabled();
            this.mSegmented.setEnabled(true);
            switch (fridgeDevice.getTemperatureSet()) {
                case 100:
                    setSegmented(2, isEnabled);
                    break;
                case 101:
                    setSegmented(1, isEnabled);
                    break;
                case 102:
                    setSegmented(0, isEnabled);
                    break;
                default:
                    if (-1 != this.mSegmented.getSelection()) {
                        this.mSegmented.setSelection(-1);
                        break;
                    }
                    break;
            }
            if (fridgeDevice.getErrorCode() != null) {
                logD(" error : " + fridgeDevice.getErrorCode() + " , last : " + this.mLastErrorCode);
                if (!fridgeDevice.getErrorCode().equals(this.mLastErrorCode)) {
                    refreshError(fridgeDevice.getErrorCode());
                    this.mLastErrorCode = fridgeDevice.getErrorCode();
                }
                this.mWarningScrollView.setVisibility(this.mLayWarning.getChildCount() > 0 ? 0 : 4);
            } else {
                this.mWarningScrollView.setVisibility(4);
            }
        } else {
            this.mViewCurrent.setText(R.string.fridge2_temperature_current_error);
            this.mWarningScrollView.setVisibility(4);
            this.mSegmented.setSelection(-1);
            this.mSegmented.setEnabled(false);
            z = false;
        }
        if (!this.mSegmented.isEnabled() || this.mSegmented.getSelection() == -1) {
            SoundManager.get().stopMedia();
        }
        if (this.mOnlyOpenDoor) {
            this.mBtnMaintainService.setVisibility(4);
        } else {
            this.mBtnMaintainService.setVisibility(this.mWarningScrollView.getVisibility());
        }
        if (z && this.mWarningScrollView.getVisibility() == 4) {
            switch (fridgeDevice.getTemperatureSet()) {
                case 100:
                    i = 1;
                    break;
                case 101:
                    i = 2;
                    break;
                case 102:
                    i = 3;
                    break;
            }
        }
        if (this.mBgIndex != i) {
            logI("refreshBg--mBgIndex " + this.mBgIndex + ",bgIndex " + i);
            if (this.mBgIndex == 0) {
                this.mFridgeWebPBg.animate().alpha(1.0f).setDuration(500L).start();
                this.mFridgeBg.animate().alpha(0.0f).setDuration(500L).start();
            }
            if (i == 0) {
                this.mFridgeBg.animate().alpha(1.0f).setDuration(500L).start();
                this.mFridgeWebPBg.animate().alpha(0.0f).setDuration(500L).start();
            }
            this.mBgIndex = i;
            refreshBg();
        }
    }

    private void refreshBg() {
        int i = this.mBgIndex;
        if (i == 1) {
            WebpUtils.loadWebp(this.mFridgeWebPBg, R.drawable.fridge_low);
        } else if (i == 2) {
            WebpUtils.loadWebp(this.mFridgeWebPBg, R.drawable.fridge_mid);
        } else if (i != 3) {
        } else {
            WebpUtils.loadWebp(this.mFridgeWebPBg, R.drawable.fridge_high);
        }
    }

    private void setSegmented(int i, boolean z) {
        if (i != this.mSegmented.getSelection()) {
            this.mSegmented.setSelection(i, z);
        }
    }

    private void refreshError(String str) {
        this.mOnlyOpenDoor = false;
        this.mLayWarning.removeAllViews();
        this.mRecycleErrorViews.clear();
        this.mRecycleErrorViews.addAll(this.mErrorViews);
        List<String> asList = Arrays.asList(str.split(","));
        if (asList.contains("7")) {
            if (this.mErrorViewOpen == null) {
                this.mErrorViewOpen = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.fridge2_warning_tip, this.mLayWarning, false);
                this.mErrorViewOpen.setText(R.string.fridge_warning_e7);
                logD(" getErrorView inflate open ");
            }
            logD(" getErrorView add open");
            this.mLayWarning.addView(this.mErrorViewOpen);
            this.mOnlyOpenDoor = true;
        }
        for (Map.Entry<String, Integer> entry : this.mErrorString.entrySet()) {
            TextView errorView = getErrorView(asList, entry.getKey());
            if (errorView != null) {
                this.mOnlyOpenDoor = false;
                this.mLayWarning.addView(errorView);
            }
        }
        logI(" refreshError recycle size " + this.mRecycleErrorViews.size() + ", view size " + this.mErrorViews.size() + ", count " + this.mLayWarning.getChildCount());
    }

    private TextView getErrorView(List<String> list, String str) {
        TextView textView;
        if (list.contains(str)) {
            if (this.mRecycleErrorViews.size() > 0) {
                textView = this.mRecycleErrorViews.remove(0);
                logD(" getErrorView from list ; cur size " + this.mRecycleErrorViews.size() + " , codeType : " + str);
            } else {
                textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.fridge2_warning_tip, this.mLayWarning, false);
                this.mErrorViews.add(textView);
                logD(" getErrorView inflate codeType : " + str);
            }
            Integer num = this.mErrorString.get(str);
            logD(" getErrorView codeType : " + str);
            if (num != null) {
                textView.setText(num.intValue());
            }
            return textView;
        }
        return null;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XThemeManager.isThemeChanged(configuration)) {
            refreshBg();
        }
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onDestroy() {
        super.onDestroy();
        XImageView xImageView = this.mFridgeWebPBg;
        if (xImageView != null) {
            WebpUtils.destroy(xImageView);
        }
    }
}
