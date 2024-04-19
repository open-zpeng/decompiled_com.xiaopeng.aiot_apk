package com.xiaopeng.aiot.device.fragrance.ui2;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.aiot.device.fragrance.vm.IFragranceVM;
import com.xiaopeng.aiot.device.watches.data.WatchesDevice;
import com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager;
import com.xiaopeng.iotlib.base.BaseAppView;
import com.xiaopeng.iotlib.model.sound.AssetsSoundSource;
import com.xiaopeng.iotlib.model.sound.SoundManager;
import com.xiaopeng.iotlib.provider.voice.vui.VuiViewHelper;
import com.xiaopeng.iotlib.utils.WebpUtils;
import com.xiaopeng.xui.widget.XSegmented;
import java.util.Arrays;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AppView2 extends BaseAppView {
    private static final int SCALE_DURATION = 800;
    private static final float SCALE_LARGE = 1.2f;
    private static final float SCALE_SMALL = 1.0f;
    private ChannelView[] mChannelView;
    private int mDensity;
    private int[] mDesForTypes;
    private boolean mFirstScale;
    private IFragranceVM mIViewData;
    private Interpolator mInterpolator;
    private XSegmented mSegmented;
    private int mSelectType;
    private String[] mSuperTypes;
    private TypeGreatMasterDialog mTypeGreatMasterDialog;
    private TypeIntroduceDialog mTypeIntroduceDialog;
    private ImageView mViewBg;
    private ImageView mViewClose;
    private ImageView mViewDes;
    private View mViewDesClick;
    private TextView mViewSuperTips;
    private int[] mWebpForTypes;

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    protected String logTag() {
        return "fragrance-";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppView2(Context context) {
        this(context, null);
    }

    AppView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChannelView = new ChannelView[3];
        this.mFirstScale = true;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.fragrance2_main_app_view, this);
        this.mViewBg = (ImageView) findViewById(R.id.view_bg);
        this.mViewClose = (ImageView) findViewById(R.id.view_bg_close);
        this.mViewDes = (ImageView) findViewById(R.id.fragrance_des);
        this.mViewDesClick = findViewById(R.id.fragrance_des_click);
        this.mSegmented = (XSegmented) findViewById(R.id.segmented);
        this.mViewSuperTips = (TextView) findViewById(R.id.fragrance_super_tips);
        this.mChannelView[0] = (ChannelView) findViewById(R.id.fragrance_channel_01);
        this.mChannelView[1] = (ChannelView) findViewById(R.id.fragrance_channel_02);
        this.mChannelView[2] = (ChannelView) findViewById(R.id.fragrance_channel_03);
        int i = 0;
        while (true) {
            ChannelView[] channelViewArr = this.mChannelView;
            if (i < channelViewArr.length) {
                channelViewArr[i].setRole(i + 100, i);
                this.mChannelView[i].setEnabled(false);
                VuiViewHelper.addHasFeedbackProp(this.mChannelView[i]);
                i++;
            } else {
                this.mSegmented.setEnabled(false);
                this.mSegmented.setSelection(-1);
                this.mSegmented.setSegmentListener(new XSegmented.SegmentListener() { // from class: com.xiaopeng.aiot.device.fragrance.ui2.AppView2.1
                    @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
                    public boolean onInterceptChange(XSegmented xSegmented, int i2) {
                        return false;
                    }

                    @Override // com.xiaopeng.xui.widget.XSegmented.SegmentListener
                    public void onSelectionChange(XSegmented xSegmented, int i2, boolean z) {
                        AppView2 appView2 = AppView2.this;
                        appView2.logI("onSelectionChange index : " + i2 + ", fromUser " + z);
                        AppView2.this.onTemperatureSetChange(z, i2);
                    }
                });
                VuiViewHelper.addHasFeedbackProp(this.mSegmented);
                this.mWebpForTypes = TypeResources.WEBP;
                this.mDesForTypes = TypeResources.DESCRIBE;
                this.mInterpolator = new DecelerateInterpolator(1.5f);
                this.mSuperTypes = getResources().getStringArray(R.array.fragrance2_types_super);
                this.mTypeGreatMasterDialog = new TypeGreatMasterDialog(getContext());
                this.mTypeIntroduceDialog = new TypeIntroduceDialog(getContext());
                this.mViewDesClick.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fragrance.ui2.-$$Lambda$AppView2$kn9exzTet47KhQ3oPQ-V9PI18_A
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AppView2.this.lambda$init$0$AppView2(view);
                    }
                });
                this.mViewSuperTips.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.device.fragrance.ui2.-$$Lambda$AppView2$TbmTyrdmRiKRnvOlZ-QIJl4h-M4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AppView2.this.lambda$init$1$AppView2(view);
                    }
                });
                return;
            }
        }
    }

    public /* synthetic */ void lambda$init$0$AppView2(View view) {
        this.mTypeIntroduceDialog.show(this.mSelectType);
        BuriedPointManager.get().getIFragranceBP().more(FragranceDevice.typeIndex(this.mSelectType) + 1);
    }

    public /* synthetic */ void lambda$init$1$AppView2(View view) {
        this.mTypeGreatMasterDialog.show(this.mSelectType);
        BuriedPointManager.get().getIFragranceBP().greatMaster(FragranceDevice.typeIndex(this.mSelectType) + 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIViewData(IFragranceVM iFragranceVM) {
        this.mIViewData = iFragranceVM;
        for (ChannelView channelView : this.mChannelView) {
            channelView.setIViewData(iFragranceVM);
        }
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onCreate() {
        super.onCreate();
        IFragranceVM iFragranceVM = this.mIViewData;
        if (iFragranceVM != null) {
            iFragranceVM.getFragrance().observe(this, new Observer() { // from class: com.xiaopeng.aiot.device.fragrance.ui2.-$$Lambda$AppView2$fZeE90I21tAO1IxSYnusiRrOBkY
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    AppView2.this.lambda$onCreate$2$AppView2((FragranceDevice) obj);
                }
            });
        } else {
            logI(" onCreate mIViewData is null ");
        }
    }

    public /* synthetic */ void lambda$onCreate$2$AppView2(FragranceDevice fragranceDevice) {
        if (fragranceDevice == null) {
            return;
        }
        logI("observe:" + fragranceDevice.toString());
        refresh(fragranceDevice);
    }

    private void refresh(FragranceDevice fragranceDevice) {
        if (fragranceDevice == null) {
            return;
        }
        int channel = fragranceDevice.getChannel();
        int density = fragranceDevice.getDensity();
        int[] channelType = fragranceDevice.getChannelType();
        int activeType = FragranceDevice.activeType(fragranceDevice);
        int[] iArr = new int[this.mChannelView.length];
        FragranceDevice.channelTypesProcess(channelType, iArr);
        logI("refresh-" + Arrays.toString(iArr));
        refreshType(activeType);
        refreshScale(density);
        refreshSuper();
        refreshDensity(fragranceDevice);
        refreshChannel(iArr, channel);
    }

    private void refreshType(int i) {
        if (i != this.mSelectType) {
            this.mSelectType = i;
            logI("refreshWebp--mSelectType : " + this.mSelectType);
            _refreshBg();
            _refreshDes();
            _refreshSound();
        }
    }

    private void _refreshBg() {
        int typeIndex = FragranceDevice.typeIndex(this.mSelectType);
        if (typeIndex >= 0) {
            int[] iArr = this.mWebpForTypes;
            if (typeIndex < iArr.length) {
                WebpUtils.loadWebp(this.mViewBg, iArr[typeIndex]);
                this.mViewBg.setVisibility(0);
                this.mViewClose.setVisibility(4);
                return;
            }
        }
        this.mViewBg.setVisibility(4);
        this.mViewClose.setVisibility(0);
    }

    private void _refreshDes() {
        int typeIndex = FragranceDevice.typeIndex(this.mSelectType);
        if (typeIndex >= 0) {
            int[] iArr = this.mDesForTypes;
            if (typeIndex < iArr.length) {
                this.mViewDes.setImageResource(iArr[typeIndex]);
                this.mViewDesClick.setVisibility(0);
                return;
            }
        }
        this.mViewDes.setImageResource(0);
        this.mViewDesClick.setVisibility(4);
    }

    private void _refreshSound() {
        int typeIndex = FragranceDevice.typeIndex(this.mSelectType);
        if (typeIndex >= 0 && typeIndex < AssetsSoundSource.FRAGRANCE_SOUND.length) {
            SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_SOUND[typeIndex], 1);
        } else {
            SoundManager.get().stopMedia();
        }
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onStop() {
        super.onStop();
        SoundManager.get().stopMedia();
    }

    private void refreshScale(int i) {
        if (this.mDensity != i) {
            this.mDensity = i;
            if (i == 101) {
                if (this.mFirstScale) {
                    this.mViewBg.setScaleX(SCALE_LARGE);
                    this.mViewBg.setScaleY(SCALE_LARGE);
                } else {
                    this.mViewBg.animate().scaleX(SCALE_LARGE).scaleY(SCALE_LARGE).setDuration(800L).setInterpolator(this.mInterpolator).start();
                }
            } else if (this.mFirstScale) {
                this.mViewBg.setScaleX(1.0f);
                this.mViewBg.setScaleY(1.0f);
            } else {
                this.mViewBg.animate().scaleX(1.0f).scaleY(1.0f).setDuration(800L).setInterpolator(this.mInterpolator).start();
            }
        }
        this.mFirstScale = false;
    }

    private void refreshChannel(int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            this.mChannelView[i2].refresh(iArr[i2], i);
            if (iArr[i2] == -1) {
                this.mChannelView[i2].setEnabled(false);
            } else {
                this.mChannelView[i2].setEnabled(true);
            }
        }
    }

    private void refreshSuper() {
        this.mViewSuperTips.setVisibility(FragranceDevice.isSuper(this.mSelectType) ? 0 : 4);
        int i = this.mSelectType + WatchesDevice.STATUS_LOGOUT;
        if (i >= 0) {
            String[] strArr = this.mSuperTypes;
            if (i < strArr.length) {
                this.mViewSuperTips.setText(Html.fromHtml(strArr[i]));
                return;
            }
        }
        this.mViewSuperTips.setText((CharSequence) null);
    }

    private void refreshDensity(FragranceDevice fragranceDevice) {
        if (fragranceDevice == null) {
            return;
        }
        boolean isEnabled = this.mSegmented.isEnabled();
        if (FragranceDevice.isOpen(fragranceDevice)) {
            this.mSegmented.setEnabled(true);
        } else {
            this.mSegmented.setEnabled(false);
        }
        int density = fragranceDevice.getDensity();
        if (density == 100) {
            setSegmented(0, isEnabled);
        } else if (density == 101) {
            setSegmented(1, isEnabled);
        } else {
            if (-1 != this.mSegmented.getSelection()) {
                this.mSegmented.setSelection(-1);
            }
            this.mSegmented.setEnabled(false);
        }
    }

    private void setSegmented(int i, boolean z) {
        if (i != this.mSegmented.getSelection()) {
            this.mSegmented.setSelection(i, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTemperatureSetChange(boolean z, int i) {
        IFragranceVM iFragranceVM;
        logI("onTemperatureSetChange index : " + i + ", fromUser " + z);
        if (!z || (iFragranceVM = this.mIViewData) == null) {
            return;
        }
        iFragranceVM.setDensity(i == 0 ? 100 : 101);
        BuriedPointManager.get().getIFragranceBP().density(i == 0 ? 1 : 2);
    }

    @Override // com.xiaopeng.iotlib.base.BaseAppView
    public void onDestroy() {
        super.onDestroy();
        ImageView imageView = this.mViewBg;
        if (imageView != null) {
            WebpUtils.destroy(imageView);
        }
    }
}
