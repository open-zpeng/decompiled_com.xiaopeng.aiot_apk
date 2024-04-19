package com.xiaopeng.aiot.device.fragrance.ui2;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.aiot.device.fragrance.vm.IFragranceVM;
import com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.voice.vui.VuiViewHelper;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XConstraintLayout;
/* loaded from: classes.dex */
public class ChannelView extends XConstraintLayout implements View.OnClickListener {
    private static int[] mTypeBgs;
    private static String[] mTypeNames;
    private static String[] mTypeTipsNames;
    private ImageView mBg;
    private int mChannelId;
    private IFragranceVM mFragranceVM;
    private View mOpenStatus;
    private int mPosition;
    private int mType;
    private TextView mTypeTitle;
    private TextView mTypeTitleTips;

    public ChannelView(Context context) {
        this(context, null);
    }

    public ChannelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.fragrance2_channel_view, this);
        this.mBg = (ImageView) findViewById(R.id.fragrance_channel_bg);
        this.mOpenStatus = findViewById(R.id.fragrance_type_open);
        this.mTypeTitleTips = (TextView) findViewById(R.id.fragrance_channel_type_tips);
        this.mTypeTitle = (TextView) findViewById(R.id.fragrance_type_title);
        if (mTypeNames == null) {
            mTypeNames = getResources().getStringArray(R.array.fragrance_types);
        }
        if (mTypeTipsNames == null) {
            mTypeTipsNames = getResources().getStringArray(R.array.fragrance_types_tips);
        }
        if (mTypeBgs == null) {
            mTypeBgs = TypeResources.CARD;
        }
        setOnClickListener(this);
    }

    public void setRole(int i, int i2) {
        this.mChannelId = i;
        this.mPosition = i2;
        VuiViewHelper.addHasFeedbackProp(this);
        VuiViewHelper.addDefaultPriorityValue(this, i2 + 1);
    }

    public void refresh(int i, int i2) {
        boolean z = i != this.mType;
        log(String.format("refresh- mChannelId : %s ,type : %s , selectChannel: %s , typeChanged :%s", Integer.valueOf(this.mChannelId), Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z)));
        this.mType = i;
        if (z) {
            refreshBg(i);
            refreshText(i);
            refreshTextTips(i);
            refreshVui(i);
        }
        refreshOpen(i2);
    }

    private void refreshBg(int i) {
        int typeIndex = FragranceDevice.typeIndex(i);
        int[] iArr = mTypeBgs;
        if (typeIndex >= 0 && typeIndex < iArr.length) {
            this.mBg.setImageResource(iArr[typeIndex]);
        } else {
            this.mBg.setImageResource(R.drawable.fragrance2_card_no);
        }
    }

    private void refreshVui(int i) {
        if (i == -1) {
            setVuiLabel(null);
            return;
        }
        int typeIndex = FragranceDevice.typeIndex(i);
        String[] strArr = mTypeNames;
        if (typeIndex < strArr.length) {
            setVuiLabel(getSwitchVui(strArr[typeIndex]));
        } else {
            setVuiLabel(null);
        }
    }

    private void refreshText(int i) {
        int typeIndex = FragranceDevice.typeIndex(i);
        if (typeIndex >= 0) {
            String[] strArr = mTypeNames;
            if (typeIndex < strArr.length) {
                this.mTypeTitle.setText(strArr[typeIndex]);
                return;
            }
        }
        this.mTypeTitle.setText(R.string.fragrance_not_install);
    }

    private void refreshTextTips(int i) {
        int typeIndex = FragranceDevice.typeIndex(i);
        if (typeIndex >= 0) {
            String[] strArr = mTypeTipsNames;
            if (typeIndex < strArr.length) {
                this.mTypeTitleTips.setText(strArr[typeIndex]);
                return;
            }
        }
        this.mTypeTitleTips.setText((CharSequence) null);
    }

    private void refreshOpen(int i) {
        if (this.mChannelId == i) {
            this.mOpenStatus.setVisibility(0);
            setSelected(true);
            return;
        }
        this.mOpenStatus.setVisibility(4);
        setSelected(false);
    }

    @Override // com.xiaopeng.xui.widget.XConstraintLayout, android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        log(" mPosition : " + this.mPosition + " , setSelected " + z);
        setChildSelected(this, z);
    }

    private void setChildSelected(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setChildSelected((ViewGroup) childAt, z);
            }
            childAt.setSelected(z);
        }
    }

    private String getSwitchVui(String str) {
        return TextUtils.isEmpty(str) ? getResources().getString(R.string.fragrance_type_vui_null, String.valueOf(this.mPosition + 1)) : getResources().getString(R.string.fragrance_type_vui, String.valueOf(this.mPosition + 1), str);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        setEnabled(z, true);
    }

    public void setEnabled(boolean z, boolean z2) {
        super.setEnabled(z);
        if (z2) {
            setChildEnabled(this, z);
        }
    }

    private void setChildEnabled(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setChildEnabled((ViewGroup) childAt, z);
            }
            childAt.setEnabled(z);
        }
    }

    public void setIViewData(IFragranceVM iFragranceVM) {
        this.mFragranceVM = iFragranceVM;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        boolean z = this.mOpenStatus.getVisibility() == 0;
        log(" mChannelId : " + this.mChannelId + " isOpen : " + z + " , isVuiEvent " + VuiViewHelper.isVuiEvent(view));
        if (this.mFragranceVM == null) {
            return;
        }
        int typeIndex = FragranceDevice.typeIndex(this.mType);
        if (z) {
            this.mFragranceVM.setEnable(false);
            BuriedPointManager.get().getIFragranceBP().close(typeIndex + 1);
            return;
        }
        this.mFragranceVM.setChoiceChannel(this.mChannelId);
        BuriedPointManager.get().getIFragranceBP().open(typeIndex + 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XConstraintLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XThemeManager.isThemeChanged(configuration);
    }

    private void log(String str) {
        LogUtils.i(LogConfig.TAG_APPVIEW, getClass().getSimpleName() + " " + hashCode() + "-" + str, 1);
    }
}
