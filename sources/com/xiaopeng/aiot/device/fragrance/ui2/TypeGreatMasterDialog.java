package com.xiaopeng.aiot.device.fragrance.ui2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fragrance.data.FragranceDevice;
import com.xiaopeng.iotlib.provider.voice.vui.VuiManager;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.xui.app.XDialog;
/* loaded from: classes.dex */
public class TypeGreatMasterDialog {
    private Context mContext;
    private TextView mDes;
    private TextView mName;
    private ImageView mPic;
    private TextView mPost;
    private XDialog mXDialog;

    public TypeGreatMasterDialog(Context context) {
        this.mContext = context;
    }

    private void init() {
        if (this.mXDialog != null) {
            return;
        }
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.fragrance2_greatmaster_view, (ViewGroup) null);
        this.mXDialog = new XDialog(this.mContext, (int) R.style.Fragrance2_Introduce_Dialog);
        this.mXDialog.setCloseVisibility(true).setCustomView(inflate, true);
        this.mPic = (ImageView) inflate.findViewById(R.id.pic);
        this.mName = (TextView) inflate.findViewById(R.id.fragrance_master_name);
        this.mPost = (TextView) inflate.findViewById(R.id.fragrance_master_post);
        this.mDes = (TextView) inflate.findViewById(R.id.fragrance_master_des);
    }

    public void show(int i) {
        LogUtils.i("ui-great", "type " + i);
        init();
        if (FragranceDevice.isClementGavarry(i)) {
            this.mPic.setImageResource(R.drawable.fragrance2_clement);
            this.mName.setText(R.string.fragrance2_greatmaster_clementgavarry_name);
            this.mPost.setText(R.string.fragrance2_greatmaster_clementgavarry_post);
            this.mDes.setText(R.string.fragrance2_greatmaster_clementgavarry_des);
            this.mXDialog.show();
            XDialog xDialog = this.mXDialog;
            VuiManager.initDialogVuiScene(xDialog, getClass().getSimpleName() + "_XpDialog");
        } else if (FragranceDevice.isFlorianGallo(i)) {
            this.mPic.setImageResource(R.drawable.fragrance2_florian_gallo);
            this.mName.setText(R.string.fragrance2_greatmaster_floriangallo_name);
            this.mPost.setText(R.string.fragrance2_greatmaster_floriangallo_post);
            this.mDes.setText(R.string.fragrance2_greatmaster_floriangallo_des);
            this.mXDialog.show();
            XDialog xDialog2 = this.mXDialog;
            VuiManager.initDialogVuiScene(xDialog2, getClass().getSimpleName() + "_XpDialog");
        } else if (FragranceDevice.isHamidMeratiKashani(i)) {
            this.mPic.setImageResource(R.drawable.fragrance2_hamid);
            this.mName.setText(R.string.fragrance2_greatmaster_hamidmeratikashani_name);
            this.mPost.setText(R.string.fragrance2_greatmaster_hamidmeratikashani_post);
            this.mDes.setText(R.string.fragrance2_greatmaster_hamidmeratikashani_des);
            this.mXDialog.show();
            XDialog xDialog3 = this.mXDialog;
            VuiManager.initDialogVuiScene(xDialog3, getClass().getSimpleName() + "_XpDialog");
        } else if (FragranceDevice.isBarbaraZoebelein(i)) {
            this.mPic.setImageResource(R.drawable.fragrance2_barbara_zoebelein);
            this.mName.setText(R.string.fragrance2_greatmaster_barbara_zoebelein_name);
            this.mPost.setText(R.string.fragrance2_greatmaster_barbara_zoebelein_post);
            this.mDes.setText(R.string.fragrance2_greatmaster_barbara_zoebelein_des);
            this.mXDialog.show();
            XDialog xDialog4 = this.mXDialog;
            VuiManager.initDialogVuiScene(xDialog4, getClass().getSimpleName() + "_XpDialog");
        }
    }
}
