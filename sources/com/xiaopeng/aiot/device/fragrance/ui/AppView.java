package com.xiaopeng.aiot.device.fragrance.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.aiot.device.fragrance.vm.IFragranceVM;
import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.iotlib.provider.voice.vui.VuiViewHelper;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import java.util.Arrays;
/* JADX INFO: Access modifiers changed from: package-private */
@Deprecated
/* loaded from: classes.dex */
public class AppView extends BaseAppView implements XTabLayout.OnTabChangeListener {
    private ChannelView[] mChannelView;
    private IFragranceVM mIViewData;
    private int[] mTypeIcons;
    private String[] mTypeNames;
    private XTabLayout mXTabLayout;

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "fragrance-";
    }

    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
    public boolean onInterceptTabChange(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
        return false;
    }

    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
    public void onTabChangeEnd(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppView(Context context) {
        this(context, null);
    }

    AppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChannelView = new ChannelView[3];
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.fragrance_main_app_view, this);
        XImageView xImageView = (XImageView) findViewById(R.id.icon1);
        XSwitch xSwitch = (XSwitch) findViewById(R.id.switch1);
        this.mChannelView[0] = new ChannelView(xImageView, (TextView) findViewById(R.id.text1), xSwitch, 100, 0);
        XImageView xImageView2 = (XImageView) findViewById(R.id.icon2);
        XSwitch xSwitch2 = (XSwitch) findViewById(R.id.switch2);
        this.mChannelView[1] = new ChannelView(xImageView2, (TextView) findViewById(R.id.text2), xSwitch2, 101, 1);
        XImageView xImageView3 = (XImageView) findViewById(R.id.icon3);
        XSwitch xSwitch3 = (XSwitch) findViewById(R.id.switch3);
        this.mChannelView[2] = new ChannelView(xImageView3, (TextView) findViewById(R.id.text3), xSwitch3, 102, 2);
        this.mXTabLayout = (XTabLayout) findViewById(R.id.tabLayout);
        this.mXTabLayout.setOnTabChangeListener(this);
        this.mXTabLayout.setEnabled(false);
        this.mXTabLayout.selectedNoneTab(false, false);
        this.mTypeNames = getResources().getStringArray(R.array.fragrance_types);
        this.mTypeIcons = new int[]{R.drawable.fragrance_type1, R.drawable.fragrance_type2, R.drawable.fragrance_type3, R.drawable.fragrance_type4, R.drawable.fragrance_type5, R.drawable.fragrance_type6, R.drawable.fragrance_type7};
        logD(Arrays.toString(this.mTypeNames));
        VuiViewHelper.addHasFeedbackProp(this.mXTabLayout);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIViewData(IFragranceVM iFragranceVM) {
        this.mIViewData = iFragranceVM;
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        IFragranceVM iFragranceVM = this.mIViewData;
        if (iFragranceVM != null) {
            iFragranceVM.getFragrance().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.fragrance.ui.-$$Lambda$AppView$ZdIgXItTmsvvuWGLa8AxKANaEZE
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    AppView.this.lambda$onCreate$0$AppView((FragranceDevice) obj);
                }
            });
        } else {
            logI(" onCreate mIViewData is null ");
        }
    }

    public /* synthetic */ void lambda$onCreate$0$AppView(FragranceDevice fragranceDevice) {
        if (fragranceDevice == null) {
            return;
        }
        logI("observe:" + fragranceDevice.toString());
        refreshView(fragranceDevice);
    }

    private void refreshView(FragranceDevice fragranceDevice) {
        if (fragranceDevice == null) {
            return;
        }
        int[] channelType = fragranceDevice.getChannelType();
        int channel = fragranceDevice.getChannel();
        int i = 0;
        boolean z = 101 == fragranceDevice.getDensity();
        if (channelType != null) {
            int i2 = 0;
            while (i < channelType.length) {
                ChannelView[] channelViewArr = this.mChannelView;
                if (i >= channelViewArr.length) {
                    break;
                }
                channelViewArr[i].refresh(channelType[i], z, channel);
                i2 = i;
                i++;
            }
            int i3 = i2 + 1;
            while (true) {
                ChannelView[] channelViewArr2 = this.mChannelView;
                if (i3 >= channelViewArr2.length) {
                    break;
                }
                channelViewArr2[i3].refresh(-1, z, channel);
                i3++;
            }
        } else {
            ChannelView[] channelViewArr3 = this.mChannelView;
            int length = channelViewArr3.length;
            while (i < length) {
                channelViewArr3[i].refresh(-1, z, channel);
                i++;
            }
        }
        refreshDensity(fragranceDevice);
    }

    private void refreshDensity(FragranceDevice fragranceDevice) {
        if (fragranceDevice == null) {
            return;
        }
        boolean isEnabled = this.mXTabLayout.isEnabled();
        if (FragranceDevice.isOpen(fragranceDevice)) {
            this.mXTabLayout.setEnabled(true);
        } else {
            this.mXTabLayout.setEnabled(false);
        }
        int density = fragranceDevice.getDensity();
        if (density == 100) {
            this.mXTabLayout.selectTab(0, isEnabled);
        } else if (density == 101) {
            this.mXTabLayout.selectTab(1, isEnabled);
        } else {
            this.mXTabLayout.selectedNoneTab(false, false);
            this.mXTabLayout.setEnabled(false);
        }
    }

    @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
    public void onTabChangeStart(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
        IFragranceVM iFragranceVM;
        logI("onTabChangeStart index : " + i + ", fromUser " + z2);
        if (!z2 || (iFragranceVM = this.mIViewData) == null) {
            return;
        }
        iFragranceVM.setDensity(i == 0 ? 100 : 101);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ChannelView implements CompoundButton.OnCheckedChangeListener {
        private int mChannelId;
        private XImageView mIcon;
        private int mPosition;
        private boolean mSwitchAnimator;
        private TextView mText;
        private int mType;
        private int mViewOpen;
        private XSwitch mXSwitch;

        private ChannelView(XImageView xImageView, TextView textView, XSwitch xSwitch, int i, int i2) {
            this.mIcon = xImageView;
            xImageView.setClipToOutline(true);
            this.mXSwitch = xSwitch;
            this.mText = textView;
            this.mChannelId = i;
            this.mPosition = i2;
            this.mXSwitch.setOnCheckedChangeListener(this);
            VuiViewHelper.addHasFeedbackProp(xSwitch);
            VuiViewHelper.addDefaultPriorityValue(xSwitch, i2 + 1);
        }

        void refresh(int i, boolean z, int i2) {
            boolean z2 = i != this.mType;
            AppView.this.logI(String.format("refresh- mChannelId : %s ,type : %s , density: %s ,selectChannel: %s , mViewOpen :%s, typeChanged :%s", Integer.valueOf(this.mChannelId), Integer.valueOf(i), Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(this.mViewOpen), Boolean.valueOf(z2)));
            this.mType = i;
            if (z2) {
                refreshIcon(i);
                refreshText(i);
                refreshSwitch(i);
            }
            refreshSelect(i, i2);
        }

        private void refreshIcon(int i) {
            if (i != -1) {
                int[] iArr = AppView.this.mTypeIcons;
                int i2 = i + WatchesDevice.STATUS_LOGOUT;
                if (i2 < iArr.length) {
                    this.mIcon.setImageResource(iArr[i2]);
                    return;
                } else {
                    this.mIcon.setImageResource(R.drawable.fragrance_type_null);
                    return;
                }
            }
            this.mIcon.setImageResource(R.drawable.fragrance_type_null);
        }

        private void refreshText(int i) {
            if (i != -1) {
                int i2 = i + WatchesDevice.STATUS_LOGOUT;
                if (i2 < AppView.this.mTypeNames.length) {
                    this.mText.setText(AppView.this.mTypeNames[i2]);
                    return;
                } else {
                    this.mText.setText(R.string.fragrance_not_install);
                    return;
                }
            }
            this.mText.setText(R.string.fragrance_not_install);
        }

        private void refreshSwitch(int i) {
            if (i != -1) {
                int i2 = i + WatchesDevice.STATUS_LOGOUT;
                if (i2 < AppView.this.mTypeNames.length) {
                    this.mXSwitch.setEnabled(true);
                    this.mXSwitch.setVuiLabel(getSwitchVui(AppView.this.mTypeNames[i2]));
                    return;
                }
                this.mXSwitch.setEnabled(false);
                this.mXSwitch.setVuiLabel(null);
                return;
            }
            this.mXSwitch.setEnabled(false);
            this.mXSwitch.setVuiLabel(null);
        }

        private void refreshSelect(int i, int i2) {
            this.mXSwitch.setChecked(this.mChannelId == i2, this.mSwitchAnimator);
            this.mSwitchAnimator = true;
            if (this.mXSwitch.isChecked()) {
                openIcon();
            } else {
                closeIcon(i);
            }
        }

        private String getSwitchVui(String str) {
            return TextUtils.isEmpty(str) ? AppView.this.getResources().getString(R.string.fragrance_type_vui_null, String.valueOf(this.mPosition + 1)) : AppView.this.getResources().getString(R.string.fragrance_type_vui, String.valueOf(this.mPosition + 1), str);
        }

        private void openIcon() {
            int i = this.mViewOpen;
            if (i == 2) {
                return;
            }
            if (i == 0) {
                this.mIcon.setAlpha(1.0f);
            } else {
                this.mIcon.animate().alpha(1.0f).setDuration(400L).setStartDelay(50L).start();
            }
            this.mViewOpen = 2;
        }

        private void closeIcon(int i) {
            if (i == -1) {
                this.mIcon.setAlpha(1.0f);
                this.mViewOpen = 0;
                return;
            }
            int i2 = this.mViewOpen;
            if (i2 == 1) {
                return;
            }
            if (i2 == 0) {
                this.mIcon.setAlpha(0.3f);
            } else {
                this.mIcon.animate().alpha(0.3f).setDuration(400L).setStartDelay(50L).start();
            }
            this.mViewOpen = 1;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            ChannelView[] channelViewArr;
            AppView.this.logI("onCheckedChanged mChannelId : " + this.mChannelId + " , isChecked : " + z + " , isPressed " + compoundButton.isPressed() + " , isVuiEvent " + VuiViewHelper.isVuiEvent(compoundButton));
            if ((compoundButton.isPressed() || VuiViewHelper.isVuiEventAndClear(compoundButton)) && AppView.this.mIViewData != null) {
                if (z) {
                    AppView.this.mIViewData.setChoiceChannel(this.mChannelId);
                    openIcon();
                    for (ChannelView channelView : AppView.this.mChannelView) {
                        XSwitch xSwitch = channelView.mXSwitch;
                        if (xSwitch != compoundButton) {
                            xSwitch.setChecked(false);
                            channelView.closeIcon(channelView.mType);
                        }
                    }
                    return;
                }
                AppView.this.mIViewData.setEnable(false);
                closeIcon(this.mType);
                AppView.this.mXTabLayout.setEnabled(false);
            }
        }
    }
}
