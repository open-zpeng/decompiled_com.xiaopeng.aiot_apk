package com.xiaopeng.aiot.device.fragrance.ui2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.iotlib.provider.voice.vui.VuiManager;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.xui.app.XDialog;
/* loaded from: classes.dex */
public class TypeIntroduceDialog {
    private String[] mBaseString;
    private TextView mBaseView;
    private Context mContext;
    private TextView mDesView;
    private String[] mHeadString;
    private TextView mHeadView;
    private String[] mHeartString;
    private TextView mHeartView;
    private String[] mTitleString;
    private int[] mTypeDes = TypeResources.NODE_DES;
    private XDialog mXDialog;

    public TypeIntroduceDialog(Context context) {
        this.mContext = context;
        this.mTitleString = context.getResources().getStringArray(R.array.fragrance_types);
        this.mHeadString = context.getResources().getStringArray(R.array.fragrance2_introduce_head);
        this.mHeartString = context.getResources().getStringArray(R.array.fragrance2_introduce_heart);
        this.mBaseString = context.getResources().getStringArray(R.array.fragrance2_introduce_base);
    }

    private void init() {
        if (this.mXDialog != null) {
            return;
        }
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.fragrance2_introduce_view, (ViewGroup) null);
        this.mXDialog = new XDialog(this.mContext, (int) R.style.Fragrance2_Introduce_Dialog);
        this.mXDialog.setCloseVisibility(true).setCustomView(inflate, true);
        this.mHeadView = (TextView) inflate.findViewById(R.id.fragrance_head_des);
        this.mHeartView = (TextView) inflate.findViewById(R.id.fragrance_heart_des);
        this.mBaseView = (TextView) inflate.findViewById(R.id.fragrance_base_des);
        this.mDesView = (TextView) inflate.findViewById(R.id.fragrance_introduce_des);
    }

    public void show(int i) {
        int typeIndex = FragranceDevice.typeIndex(i);
        LogUtils.i("ui-intro", "type " + i + ", index: " + typeIndex);
        if (typeIndex >= 0 && typeIndex < this.mTitleString.length && typeIndex < this.mHeadString.length && typeIndex < this.mBaseString.length && typeIndex < this.mHeartString.length && typeIndex < this.mTypeDes.length) {
            init();
            this.mHeadView.setText(this.mHeadString[typeIndex]);
            this.mHeartView.setText(this.mHeartString[typeIndex]);
            this.mBaseView.setText(this.mBaseString[typeIndex]);
            this.mDesView.setText(this.mTypeDes[typeIndex]);
            this.mXDialog.setTitle(this.mTitleString[typeIndex]);
            this.mXDialog.show();
            XDialog xDialog = this.mXDialog;
            VuiManager.initDialogVuiScene(xDialog, getClass().getSimpleName() + "_XpDialog");
        }
    }
}
