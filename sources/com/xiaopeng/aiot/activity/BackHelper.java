package com.xiaopeng.aiot.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.xiaopeng.aiot.BuildConfig;
import com.xiaopeng.iotlib.base.BasePageActivity;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.receiver.IotReceiver;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.xui.app.delegate.XActivityDismiss;
/* loaded from: classes.dex */
class BackHelper {
    private static final String FROM_AIR = "air";
    private static final String FROM_CAR = "carlife";
    private static final String FROM_POWERCENTER = "powercenter";
    private static final String FROM_SPEECH = "speech";
    private BasePageActivity mActivity;
    private PageId mFragrancePageId;
    private PageId mFridgePageId;
    private String mFromIntent;
    private boolean mFromSuperPanel;
    private XActivityDismiss.OnDismissListener mOnDismissListener = new XActivityDismiss.OnDismissListener() { // from class: com.xiaopeng.aiot.activity.BackHelper.1
        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss.OnDismissListener
        public boolean onDismiss(int i, int i2) {
            BackHelper backHelper = BackHelper.this;
            backHelper.logI("onDismiss--" + i);
            if (i != 2) {
                try {
                    BackHelper.this.backApp();
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public BackHelper(BasePageActivity basePageActivity, PageId pageId, PageId pageId2) {
        this.mActivity = basePageActivity;
        this.mFridgePageId = pageId;
        this.mFragrancePageId = pageId2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public XActivityDismiss.OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onPageCreateInit(Bundle bundle) {
        this.mFromIntent = fromApp();
        this.mFromSuperPanel = (this.mActivity.getIntent().getFlags() & 1024) == 1024;
        logI(",mFromIntent:" + this.mFromIntent + " ,mFromSuperPanel:" + this.mFromSuperPanel);
        buriedPoint();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
        if (r0.equals(com.xiaopeng.aiot.activity.BackHelper.FROM_CAR) == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x008f, code lost:
        if (r0.equals(com.xiaopeng.aiot.activity.BackHelper.FROM_CAR) == false) goto L45;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void buriedPoint() {
        /*
            r10 = this;
            java.lang.String r0 = r10.mFromIntent
            if (r0 != 0) goto L5
            return
        L5:
            com.xiaopeng.iotlib.base.BasePageActivity r0 = r10.mActivity
            com.xiaopeng.iotlib.data.PageId r0 = r0.getPageId()
            com.xiaopeng.iotlib.data.PageId r1 = r10.mFragrancePageId
            boolean r1 = r1.equals(r0)
            r2 = 0
            java.lang.String r3 = "carlife"
            java.lang.String r4 = "speech"
            r5 = 554181840(0x210824d0, float:4.6127313E-19)
            r6 = -896071454(0xffffffffca9708e2, float:-4949105.0)
            r7 = -1
            r8 = 2
            r9 = 1
            if (r1 == 0) goto L78
            java.lang.String r0 = r10.mFromIntent
            int r1 = r0.hashCode()
            if (r1 == r6) goto L42
            r4 = 96586(0x1794a, float:1.35346E-40)
            if (r1 == r4) goto L38
            if (r1 == r5) goto L31
            goto L4a
        L31:
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L4a
            goto L4b
        L38:
            java.lang.String r1 = "air"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L4a
            r2 = r9
            goto L4b
        L42:
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L4a
            r2 = r8
            goto L4b
        L4a:
            r2 = r7
        L4b:
            if (r2 == 0) goto L6c
            if (r2 == r9) goto L60
            if (r2 == r8) goto L53
            goto Lb7
        L53:
            com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager r0 = com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager.get()
            com.xiaopeng.aiot.model.buriedpoint.IFragranceBP r0 = r0.getIFragranceBP()
            r1 = 4
            r0.into(r1)
            goto Lb7
        L60:
            com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager r0 = com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager.get()
            com.xiaopeng.aiot.model.buriedpoint.IFragranceBP r0 = r0.getIFragranceBP()
            r0.into(r8)
            goto Lb7
        L6c:
            com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager r0 = com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager.get()
            com.xiaopeng.aiot.model.buriedpoint.IFragranceBP r0 = r0.getIFragranceBP()
            r0.into(r9)
            goto Lb7
        L78:
            com.xiaopeng.iotlib.data.PageId r1 = r10.mFridgePageId
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto Lb7
            java.lang.String r0 = r10.mFromIntent
            int r1 = r0.hashCode()
            if (r1 == r6) goto L92
            if (r1 == r5) goto L8b
            goto L9a
        L8b:
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L9a
            goto L9b
        L92:
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L9a
            r2 = r9
            goto L9b
        L9a:
            r2 = r7
        L9b:
            if (r2 == 0) goto Lac
            if (r2 == r9) goto La0
            goto Lb7
        La0:
            com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager r0 = com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager.get()
            com.xiaopeng.aiot.model.buriedpoint.IFridgeBP r0 = r0.getIFridgeBP()
            r0.into(r8)
            goto Lb7
        Lac:
            com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager r0 = com.xiaopeng.aiot.model.buriedpoint.BuriedPointManager.get()
            com.xiaopeng.aiot.model.buriedpoint.IFridgeBP r0 = r0.getIFridgeBP()
            r0.into(r9)
        Lb7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.aiot.activity.BackHelper.buriedPoint():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backApp() {
        PageId pageId = this.mActivity.getPageId();
        if (this.mFridgePageId.equals(pageId)) {
            if (FROM_POWERCENTER.equals(this.mFromIntent)) {
                backPowerCenter();
            }
        } else if (this.mFragrancePageId.equals(pageId) && FROM_AIR.equals(this.mFromIntent)) {
            backAir();
        }
    }

    private String fromApp() {
        Intent intent = this.mActivity.getIntent();
        if (intent == null || intent.getData() == null) {
            return null;
        }
        return intent.getData().getQueryParameter(IotReceiver.KEY_FROM);
    }

    private void backPowerCenter() {
        logI("backPowerCenter");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268468224);
        if (this.mFromSuperPanel) {
            intent.addFlags(1024);
        }
        intent.setData(Uri.parse("xiaopeng://powercenter/main?from=aiot"));
        this.mActivity.startActivity(intent);
    }

    private void backAir() {
        logI("backAir");
        Intent intent = new Intent("com.xiaopeng.carcontrol.intent.action.SHOW_HVAC_PANEL");
        intent.addFlags(268468224);
        if (this.mFromSuperPanel) {
            intent.addFlags(1024);
        }
        intent.putExtra(IotReceiver.KEY_FROM, BuildConfig.AUTH);
        this.mActivity.startActivity(intent);
    }

    protected void logI(String str) {
        LogUtils.i(LogConfig.TAG_ACTIVITY, getClass().getSimpleName() + " " + hashCode() + "-" + str, 1);
    }
}
