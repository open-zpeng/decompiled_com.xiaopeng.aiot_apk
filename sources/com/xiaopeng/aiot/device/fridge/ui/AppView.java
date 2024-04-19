package com.xiaopeng.aiot.device.fridge.ui;

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
import com.xiaopeng.aiot.device.fridge.vm.IFridgeVM;
import com.xiaopeng.iotlib.base.BaseActivity;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.iotlib.provider.voice.vui.VuiViewHelper;
import com.xiaopeng.iotlib.utils.MultipleClickHelper;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XSegmented;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* JADX INFO: Access modifiers changed from: package-private */
@Deprecated
/* loaded from: classes.dex */
public class AppView extends BaseAppView {
    private int mBgRes;
    private int mBleState;
    private View mBtnBleClose;
    private HashMap<String, Integer> mErrorString;
    private TextView mErrorViewOpen;
    private ArrayList<TextView> mErrorViews;
    private FridgeDevice mFridgeDevice;
    private IFridgeVM mIViewData;
    private String mLastErrorCode;
    private ViewGroup mLayWarning;
    private ArrayList<TextView> mRecycleErrorViews;
    private XSegmented mSegmented;
    private String mSetTipPref;
    private String[] mSetTips;
    private TextView mViewCurrent;
    private TextView mViewCurrentError;
    private View mViewUnit;
    private View mViewUnitError;
    private View mWarningBleDisconnect;
    private View mWarningBleLoading;
    private View mWarningLine;
    private View mWarningLine2;
    private View mWarningScrollView;

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "fridge-";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppView(Context context) {
        this(context, null);
    }

    AppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mErrorString = new HashMap<>();
        this.mErrorViews = new ArrayList<>();
        this.mRecycleErrorViews = new ArrayList<>();
        this.mBleState = -1;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.fridge_main_app_view, this);
        this.mViewCurrent = (TextView) findViewById(R.id.temperature_current);
        this.mViewCurrentError = (TextView) findViewById(R.id.temperature_current_error);
        this.mViewUnit = findViewById(R.id.temperature_unit);
        this.mViewUnitError = findViewById(R.id.temperature_unit_error);
        this.mBtnBleClose = findViewById(R.id.btn_bluetooth_close);
        this.mSegmented = (XSegmented) findViewById(R.id.segmented);
        this.mWarningLine = findViewById(R.id.waring_line);
        this.mWarningLine2 = findViewById(R.id.waring_line2);
        this.mWarningScrollView = findViewById(R.id.warning_scrollView);
        this.mLayWarning = (ViewGroup) findViewById(R.id.warning_lay);
        this.mWarningBleDisconnect = findViewById(R.id.waring_ble_disconnect);
        this.mWarningBleLoading = findViewById(R.id.waring_ble_loading);
        this.mSegmented.setSegmentListener(new XSegmented.SegmentListener() { // from class: com.xiaopeng.aiot.device.fridge.ui.AppView.1
            @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
            public boolean onInterceptChange(XSegmented xSegmented, int i) {
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
            public void onSelectionChange(XSegmented xSegmented, int i, boolean z) {
                AppView appView = AppView.this;
                appView.logI("onSelectionChange index : " + i + ", fromUser " + z);
                AppView.this.onTemperatureSetChange(z, i);
            }
        });
        this.mSegmented.setSelection(-1);
        this.mSegmented.setEnabled(false);
        this.mBtnBleClose.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui.-$$Lambda$AppView$PqbhYbBY5nZU8I0q-B4S2RsQg9U
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$0$AppView(view);
            }
        });
        MultipleClickHelper.bind(findViewById(R.id.subtitle), new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui.-$$Lambda$AppView$UUY6RQlFAMOrjTrgtXo1ZzAtj6o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$1$AppView(view);
            }
        });
        this.mErrorString.put("1", Integer.valueOf((int) R.string.fridge_warning_e1));
        this.mErrorString.put("2", Integer.valueOf((int) R.string.fridge_warning_e2));
        this.mErrorString.put("3", Integer.valueOf((int) R.string.fridge_warning_e3));
        this.mErrorString.put(BuildInfoUtils.BID_LAN, Integer.valueOf((int) R.string.fridge_warning_e4));
        this.mErrorString.put(BuildInfoUtils.BID_PT_SPECIAL_1, Integer.valueOf((int) R.string.fridge_warning_e5));
        this.mErrorString.put(BuildInfoUtils.BID_PT_SPECIAL_2, Integer.valueOf((int) R.string.fridge_warning_e6));
        this.mSetTipPref = getResources().getString(R.string.fridge_temperature_set_tip);
        this.mSetTips = getResources().getStringArray(R.array.fridge_temperature_set);
        VuiViewHelper.addHasFeedbackProp(this.mSegmented);
    }

    public /* synthetic */ void lambda$init$0$AppView(View view) {
        if (this.mIViewData != null) {
            this.mBtnBleClose.setEnabled(false);
            this.mIViewData.openBluetooth();
        }
    }

    public /* synthetic */ void lambda$init$1$AppView(View view) {
        unBundling();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTemperatureSetChange(boolean z, int i) {
        IFridgeVM iFridgeVM;
        if (!z || (iFridgeVM = this.mIViewData) == null) {
            return;
        }
        if (i == 0) {
            iFridgeVM.setTemperature(102);
        } else if (i == 1) {
            iFridgeVM.setTemperature(101);
        } else if (i == 2) {
            iFridgeVM.setTemperature(100);
        } else {
            logI("onSelectionChange error!!! index : " + i + ", fromUser " + z);
        }
        String[] strArr = this.mSetTips;
        if (strArr == null || i < 0 || i >= strArr.length) {
            return;
        }
        XToast.show(String.format(this.mSetTipPref, strArr[i]));
    }

    private void unBundling() {
        logD("show unBundling dialog");
        new XDialog(getContext()).setTitle(R.string.fridge_unbundling_dialog_title).setCancelable(false).setMessage(R.string.fridge_unbundling_dialog_msg).setPositiveButton(R.string.fridge_unbundling_dialog_button_ok, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.aiot.device.fridge.ui.-$$Lambda$AppView$NmFBDTn4K8_ItsZZ1git4pDXERc
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                AppView.this.lambda$unBundling$2$AppView(xDialog, i);
            }
        }).setNegativeButton(R.string.cancel).show();
    }

    public /* synthetic */ void lambda$unBundling$2$AppView(XDialog xDialog, int i) {
        IFridgeVM iFridgeVM = this.mIViewData;
        if (iFridgeVM != null && iFridgeVM.unBundling() && (getContext() instanceof BaseActivity)) {
            ((BaseActivity) getContext()).dismissActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setViewData(IFridgeVM iFridgeVM) {
        this.mIViewData = iFridgeVM;
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        IFridgeVM iFridgeVM = this.mIViewData;
        if (iFridgeVM == null) {
            logI(" onCreate mIViewData is null ");
            return;
        }
        iFridgeVM.getFridge().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.fridge.ui.-$$Lambda$AppView$SF6E0JVPJWMjrPlsdfMTaiSlZks
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$3$AppView((FridgeDevice) obj);
            }
        });
        this.mIViewData.getBlueState().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.fridge.ui.-$$Lambda$AppView$k-oX8X8g-DEzOdsktfpEhW8R22c
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$4$AppView((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$3$AppView(FridgeDevice fridgeDevice) {
        if (fridgeDevice == null) {
            return;
        }
        logI("observe Fridge :" + fridgeDevice.toString());
        refreshView(fridgeDevice);
    }

    public /* synthetic */ void lambda$onCreate$4$AppView(Integer num) {
        if (num == null) {
            return;
        }
        logI("observe BlueState:" + num);
        this.mBleState = num.intValue();
        switch (this.mBleState) {
            case 10:
                this.mBtnBleClose.setEnabled(true);
                this.mBtnBleClose.setVisibility(0);
                break;
            case 11:
                this.mBtnBleClose.setEnabled(false);
                this.mBtnBleClose.setVisibility(0);
                break;
            case 12:
            case 13:
                this.mBtnBleClose.setVisibility(4);
                break;
            default:
                logI("observe BlueState default:" + num);
                break;
        }
        int i = this.mBleState;
        if (i == 12 || i == 10) {
            refreshView(this.mFridgeDevice);
        }
    }

    private void refreshView(FridgeDevice fridgeDevice) {
        if (fridgeDevice == null) {
            return;
        }
        this.mFridgeDevice = fridgeDevice;
        logD("refreshView Connect : " + fridgeDevice.getConnect() + " , BleState : " + this.mBleState);
        if (fridgeDevice.getConnect() == 101 && this.mBleState == 12) {
            this.mWarningBleDisconnect.setVisibility(0);
            this.mWarningBleLoading.setVisibility(0);
        } else {
            this.mWarningBleDisconnect.setVisibility(4);
            this.mWarningBleLoading.setVisibility(4);
        }
        if (fridgeDevice.getConnect() == 100 && this.mBleState == 12) {
            this.mViewCurrent.setVisibility(0);
            this.mViewUnit.setVisibility(0);
            this.mViewCurrentError.setVisibility(4);
            this.mViewUnitError.setVisibility(4);
            if (fridgeDevice.getTemperature() != null) {
                this.mViewCurrent.setText(fridgeDevice.getTemperature().replace("+", ""));
            } else {
                this.mViewCurrent.setText(R.string.fridge_temperature_current_error);
            }
            boolean isEnabled = this.mSegmented.isEnabled();
            this.mSegmented.setEnabled(true);
            switch (fridgeDevice.getTemperatureSet()) {
                case 100:
                    this.mSegmented.setSelection(2, isEnabled);
                    break;
                case 101:
                    this.mSegmented.setSelection(1, isEnabled);
                    break;
                case 102:
                    this.mSegmented.setSelection(0, isEnabled);
                    break;
                default:
                    this.mSegmented.setSelection(-1);
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
            refreshBg(this.mWarningScrollView.getVisibility() == 0);
        } else {
            this.mViewCurrent.setVisibility(4);
            this.mViewUnit.setVisibility(4);
            this.mViewCurrentError.setVisibility(0);
            this.mViewUnitError.setVisibility(0);
            this.mWarningScrollView.setVisibility(4);
            this.mSegmented.setSelection(-1);
            this.mSegmented.setEnabled(false);
            refreshBg(true);
        }
        refreshWaringLine();
    }

    private void refreshBg(boolean z) {
        this.mBgRes = z ? R.drawable.fridge_error_bg : R.drawable.fridge_bg;
        if (getOnAppViewListener() != null) {
            getOnAppViewListener().onRefreshBackground(this.mBgRes);
        } else {
            logI("getBgView is null !!!!!!");
        }
    }

    private void refreshWaringLine() {
        if (this.mWarningScrollView.getVisibility() == 0 || this.mWarningBleDisconnect.getVisibility() == 0) {
            this.mWarningLine.setVisibility(0);
            this.mWarningLine2.setVisibility(0);
            return;
        }
        this.mWarningLine.setVisibility(4);
        this.mWarningLine2.setVisibility(4);
    }

    private void refreshError(String str) {
        this.mLayWarning.removeAllViews();
        this.mRecycleErrorViews.clear();
        this.mRecycleErrorViews.addAll(this.mErrorViews);
        List<String> asList = Arrays.asList(str.split(","));
        if (asList.contains("7")) {
            if (this.mErrorViewOpen == null) {
                this.mErrorViewOpen = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.fridge_warning_e7_tip, this.mLayWarning, false);
                this.mErrorViewOpen.setText(R.string.fridge_warning_e7);
                logD(" getErrorView inflate open ");
            }
            logD(" getErrorView add open");
            this.mLayWarning.addView(this.mErrorViewOpen);
        }
        for (Map.Entry<String, Integer> entry : this.mErrorString.entrySet()) {
            TextView errorView = getErrorView(asList, entry.getKey());
            if (errorView != null) {
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
                textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.fridge_warning_tip, this.mLayWarning, false);
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
        if (!XThemeManager.isThemeChanged(configuration) || this.mBgRes == 0 || getOnAppViewListener() == null) {
            return;
        }
        getOnAppViewListener().onRefreshBackground(getResources().getDrawable(this.mBgRes, getContext().getTheme()));
    }
}
