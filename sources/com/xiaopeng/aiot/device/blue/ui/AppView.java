package com.xiaopeng.aiot.device.blue.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.blue.data.ScanDevice;
import com.xiaopeng.aiot.device.blue.vm.IBlueVM;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.iotlib.base.BaseAdapter;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.iotlib.provider.iot.device.AirBed;
import com.xiaopeng.iotlib.provider.iot.device.ChildSafetySeat;
import com.xiaopeng.iotlib.provider.iot.device.Fridge;
import com.xiaopeng.xui.widget.XSwitch;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AppView extends BaseAppView implements CompoundButton.OnCheckedChangeListener, BaseAdapter.OnItemClickListener<ScanDevice> {
    protected Adapter mAdapter;
    protected Set<String> mAirbedName;
    protected Set<String> mChildSeats;
    private String mDeviceType;
    protected Set<String> mFridgeName;
    protected IBlueVM mIViewData;
    protected View mLoading;
    protected XSwitch mXSwitch;

    protected int headerLayout() {
        return R.layout.ble_header;
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "blue-";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppView(Context context) {
        this(context, null);
    }

    AppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.ble_main_app_view, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        View inflate = LayoutInflater.from(getContext()).inflate(headerLayout(), (ViewGroup) recyclerView, false);
        initHeaderView(inflate.findViewById(R.id.list_switch));
        this.mAdapter = new Adapter();
        this.mAdapter.setHeaderView(inflate);
        recyclerView.setAdapter(this.mAdapter);
        this.mChildSeats = new HashSet();
        this.mFridgeName = new HashSet();
        this.mAirbedName = new HashSet();
        this.mAdapter.setOnItemClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initHeaderView(View view) {
        this.mLoading = view.findViewById(R.id.loading);
        this.mXSwitch = (XSwitch) view.findViewById(R.id.x_list_action_switch);
        this.mXSwitch.setOnCheckedChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIViewData(IBlueVM iBlueVM, String str) {
        this.mIViewData = iBlueVM;
        this.mDeviceType = str;
        this.mAdapter.setDeviceType(str);
        logD(" setIViewData mDeviceType " + this.mDeviceType);
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onDestroy() {
        super.onDestroy();
        IBlueVM iBlueVM = this.mIViewData;
        if (iBlueVM != null) {
            iBlueVM.stop();
        }
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        if (this.mIViewData == null) {
            logI(" onCreate mIViewData is null ");
            return;
        }
        if (Fridge.DEVICE_TYPE.equals(this.mDeviceType)) {
            this.mFridgeName.clear();
            this.mFridgeName.add(getContext().getString(R.string.blue_fridge_device_filter_name));
            this.mIViewData.setMultiFilterName(this.mFridgeName);
        } else if (ChildSafetySeat.DEVICE_TYPE.equals(this.mDeviceType)) {
            this.mChildSeats.clear();
            this.mChildSeats.add(getContext().getString(R.string.blue_childseat_device_filter_name));
            this.mChildSeats.add(getContext().getString(R.string.blue_childseat_device_filter_name1));
            this.mIViewData.setMultiFilterName(this.mChildSeats);
        } else if (AirBed.DEVICE_TYPE.equals(this.mDeviceType)) {
            this.mAirbedName.clear();
            this.mAirbedName.add(getContext().getString(R.string.blue_airbed_device_filter_name));
            this.mIViewData.setMultiFilterName(this.mAirbedName);
        }
        this.mIViewData.getBlue().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.blue.ui.-$$Lambda$AppView$V4fefXUjVjBEQvOdhvIb_M7Nxjc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$0$AppView((List) obj);
            }
        });
        this.mIViewData.getBlueState().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.blue.ui.-$$Lambda$AppView$qE2kSmg4lC2LNcDBV6VIzB4ndcs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$1$AppView((Integer) obj);
            }
        });
        this.mIViewData.getBlueScan().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.blue.ui.-$$Lambda$AppView$2p95OENut0dNfESBMuqGCetBisQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppView.this.lambda$onCreate$2$AppView((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$0$AppView(List list) {
        if (list != null) {
            if (list.size() < 20) {
                logI("observe Blue size:" + list.size() + "==" + list);
            } else {
                logI("observe Blue size:" + list.size());
            }
            this.mAdapter.refreshDataWithContrast(list);
            logI("observe adapter size:" + this.mAdapter.getData().size());
        }
    }

    public /* synthetic */ void lambda$onCreate$1$AppView(Integer num) {
        if (num != null) {
            logI("observe BlueState:" + num);
            onBlueState(num.intValue());
        }
    }

    public /* synthetic */ void lambda$onCreate$2$AppView(Boolean bool) {
        logI("observe BlueScan:" + bool);
        if (bool != null) {
            onBlueScan();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBlueState(int i) {
        refreshState(i);
        refreshLoading();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBlueScan() {
        refreshLoading();
    }

    private void refreshState(int i) {
        switch (i) {
            case 10:
                this.mXSwitch.setEnabled(true);
                this.mXSwitch.setChecked(false);
                this.mIViewData.stop();
                this.mAdapter.clear();
                return;
            case 11:
                this.mXSwitch.setEnabled(false);
                this.mXSwitch.setChecked(true);
                return;
            case 12:
                this.mXSwitch.setEnabled(true);
                this.mXSwitch.setChecked(true);
                this.mIViewData.start();
                return;
            case 13:
                this.mXSwitch.setEnabled(false);
                this.mXSwitch.setChecked(false);
                return;
            default:
                logI("refreshState default:" + i);
                return;
        }
    }

    private void refreshLoading() {
        Boolean value = this.mIViewData.getBlueScan().getValue();
        Integer value2 = this.mIViewData.getBlueState().getValue();
        if (value == null || value2 == null) {
            return;
        }
        if (value2.intValue() == 12) {
            this.mLoading.setVisibility(value.booleanValue() ? 0 : 4);
        } else {
            this.mLoading.setVisibility(4);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        IBlueVM iBlueVM;
        logI(String.format("onCheckedChanged mChannelId :  , isChecked : %s , isPressed %s", Boolean.valueOf(z), Boolean.valueOf(compoundButton.isPressed())));
        if (!compoundButton.isPressed() || (iBlueVM = this.mIViewData) == null) {
            return;
        }
        iBlueVM.setBlueEnable(z);
        compoundButton.setEnabled(false);
    }

    @Override // com.xiaopeng.iotlib.base.BaseAdapter.OnItemClickListener
    public void onItemClick(BaseAdapter baseAdapter, BaseAdapter.ViewHolder viewHolder, ScanDevice scanDevice) {
        logI("onItemClick " + scanDevice);
        IBlueVM iBlueVM = this.mIViewData;
        if (iBlueVM != null) {
            iBlueVM.stop();
        }
        IBlueVM iBlueVM2 = this.mIViewData;
        if (iBlueVM2 == null || !iBlueVM2.bind(scanDevice, this.mDeviceType)) {
            return;
        }
        if (Fridge.DEVICE_TYPE.equals(this.mDeviceType)) {
            PageConfigFactory.FRIDGE2.go(getContext());
        } else if (ChildSafetySeat.DEVICE_TYPE.equals(this.mDeviceType)) {
            PageConfigFactory.CHILD_SEAT.go(getContext());
            if (getOnAppViewListener() != null) {
                getOnAppViewListener().onDismiss();
            }
        } else if (AirBed.DEVICE_TYPE.equals(this.mDeviceType)) {
            PageConfigFactory.AIR_BED.go(getContext());
            if (getOnAppViewListener() != null) {
                getOnAppViewListener().onDismiss();
            }
        }
    }
}
