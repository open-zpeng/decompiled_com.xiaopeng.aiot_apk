package com.xiaopeng.aiot.device.airbed.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.airbed.data.AirBedDevice;
import com.xiaopeng.aiot.device.airbed.vm.AirBedVM;
import com.xiaopeng.aiot.device.airbed.vm.IAirBedVM;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.iotlib.base.BaseAppView;
/* loaded from: classes.dex */
public class AppView extends BaseAppView {
    private AirBedDevice mAirBedDevice;
    private TextView mBedStatus;
    private Button mBluStatus;
    private Button mBtnDeflation;
    private Button mBtnHardnessDown;
    private Button mBtnHardnessSet;
    private Button mBtnHardnessUp;
    private Button mBtnInflation;
    private Button mBtnStopDeflation;
    private Button mBtnStopInflation;
    private Button mBtnUnbind;
    private IAirBedVM mIViewData;

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "airbed-";
    }

    public AppView(Context context) {
        this(context, null);
    }

    public AppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void setViewData(AirBedVM airBedVM) {
        this.mIViewData = airBedVM;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.airbed_main_app_view, this);
        this.mBluStatus = (Button) findViewById(R.id.btn_blue_state);
        this.mBedStatus = (TextView) findViewById(R.id.btn_bed_state);
        this.mBtnInflation = (Button) findViewById(R.id.btn_inflate);
        this.mBtnStopInflation = (Button) findViewById(R.id.btn_stop_inflate);
        this.mBtnDeflation = (Button) findViewById(R.id.btn_deflate);
        this.mBtnStopDeflation = (Button) findViewById(R.id.btn_stop_deflate);
        this.mBtnHardnessUp = (Button) findViewById(R.id.btn_hardness_up);
        this.mBtnHardnessDown = (Button) findViewById(R.id.btn_hardness_down);
        this.mBtnHardnessSet = (Button) findViewById(R.id.btn_hardness_set);
        this.mBtnUnbind = (Button) findViewById(R.id.btn_bed_unbind);
        this.mBluStatus.setEnabled(false);
        this.mBluStatus.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$bHf3rfdqFInFtSE9LI3DF0Vg27Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$0$AppView(view);
            }
        });
        this.mBtnInflation.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$2ZhCwthpcTf5Y5_PTN3GT2-cnyo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$1$AppView(view);
            }
        });
        this.mBtnStopInflation.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$WmJucOsTgP0r8u5tQ6CxenjKmbk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$2$AppView(view);
            }
        });
        this.mBtnDeflation.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$rMg-m97EIep88NlRcYWr7Ij8mnU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$3$AppView(view);
            }
        });
        this.mBtnStopDeflation.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$7NHQiLb5CZP7MgetjfJSqid-WBk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$4$AppView(view);
            }
        });
        this.mBtnHardnessUp.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$FJu-S4wT7WXimYUv3xz_bBAb4Dc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$5$AppView(view);
            }
        });
        this.mBtnHardnessDown.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$tZqwfGgfvIeK_FUEdCmzly1FVDs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$6$AppView(view);
            }
        });
        this.mBtnHardnessSet.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$ZjUasb2e7LFx-v6aaVX6yfd-YCI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$7$AppView(view);
            }
        });
        this.mBtnUnbind.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$wLjeNdcEUhjdAomzvBp-02a5HAo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppView.this.lambda$init$8$AppView(view);
            }
        });
    }

    public /* synthetic */ void lambda$init$0$AppView(View view) {
        PageConfigFactory.BLUE_AIR_BED.go(getContext());
    }

    public /* synthetic */ void lambda$init$1$AppView(View view) {
        this.mIViewData.inflateBed();
    }

    public /* synthetic */ void lambda$init$2$AppView(View view) {
        this.mIViewData.interruptBump();
    }

    public /* synthetic */ void lambda$init$3$AppView(View view) {
        this.mIViewData.deflateBed();
    }

    public /* synthetic */ void lambda$init$4$AppView(View view) {
        this.mIViewData.interruptBump();
    }

    public /* synthetic */ void lambda$init$5$AppView(View view) {
        this.mIViewData.hardnessUp(String.valueOf(1));
    }

    public /* synthetic */ void lambda$init$6$AppView(View view) {
        this.mIViewData.hardnessDown(String.valueOf(1));
    }

    public /* synthetic */ void lambda$init$7$AppView(View view) {
        this.mIViewData.hardnessDown(String.valueOf(5));
    }

    public /* synthetic */ void lambda$init$8$AppView(View view) {
        this.mIViewData.unBundling();
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        IAirBedVM iAirBedVM = this.mIViewData;
        if (iAirBedVM == null) {
            logI(" onCreate mIViewData is null ");
        } else {
            iAirBedVM.getAirBed().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.airbed.ui.-$$Lambda$AppView$bWxQlLJe0I0YIaDCJ3YaQNwj_CE
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    AppView.this.lambda$onCreate$9$AppView((AirBedDevice) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onCreate$9$AppView(AirBedDevice airBedDevice) {
        if (airBedDevice == null) {
            return;
        }
        this.mAirBedDevice = airBedDevice;
        refresh();
    }

    private void refresh() {
        if (this.mAirBedDevice == null) {
            return;
        }
        TextView textView = this.mBedStatus;
        textView.setText("蓝牙状态： " + getBlueStatus() + "\n床垫硬件状态: " + getHardwareStatus() + "\n床垫泵状态: " + getBedBumpStatus() + "\n硬度状态： " + this.mAirBedDevice.getBedHardnessLevel());
        if (this.mAirBedDevice.getStatus() == 4) {
            this.mBluStatus.setEnabled(false);
        } else {
            this.mBluStatus.setEnabled(true);
        }
    }

    private String getBlueStatus() {
        AirBedDevice airBedDevice = this.mAirBedDevice;
        if (airBedDevice == null) {
            return "";
        }
        int status = airBedDevice.getStatus();
        return status != -1 ? status != 1 ? status != 2 ? status != 3 ? status != 4 ? "" : "蓝牙已经连接" : "蓝牙连接中" : "蓝牙打开中" : "蓝牙已经关闭" : "未绑定";
    }

    private String getBedBumpStatus() {
        AirBedDevice airBedDevice = this.mAirBedDevice;
        if (airBedDevice == null) {
            return "";
        }
        try {
            switch (Integer.parseInt(airBedDevice.getBedBumpStatus())) {
                case 1:
                    return "充气等待";
                case 2:
                    return "充气完成";
                case 3:
                    return "已经充满";
                case 4:
                    return "抽气等待";
                case 5:
                case 6:
                    return "抽气中";
                case 7:
                    return "抽气完成";
                case 8:
                    return "已经放完";
                default:
                    return "";
            }
        } catch (Exception unused) {
            return "";
        }
    }

    private String getHardwareStatus() {
        AirBedDevice airBedDevice = this.mAirBedDevice;
        if (airBedDevice == null) {
            return "";
        }
        try {
            int intValue = Integer.valueOf(Integer.parseInt(airBedDevice.getBedHardwareStatus())).intValue();
            return intValue != 0 ? intValue != 1 ? intValue != 2 ? intValue != 3 ? "" : "硬度调整中" : "排气中" : "充气中" : "待机";
        } catch (Exception unused) {
            return "";
        }
    }
}
