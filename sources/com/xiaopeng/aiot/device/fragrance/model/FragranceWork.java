package com.xiaopeng.aiot.device.fragrance.model;

import android.content.Context;
import com.xiaopeng.aiot.device.fragrance.model.FragranceInsertManager;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.aiot.service.IWorkService;
import com.xiaopeng.iotlib.utils.LogUtils;
@Deprecated
/* loaded from: classes.dex */
public class FragranceWork implements IWorkService, FragranceInsertManager.OnChannelInsertListener {
    private static final String TAG = FragranceWork.class.getSimpleName();
    private Context mContext;

    @Override // com.xiaopeng.aiot.service.IWorkService
    public void onCreate(Context context) {
        LogUtils.d(TAG, "onCreate: ");
        this.mContext = context;
        FragranceInsertManager.getInstance().setOnChannelInsertListener(this);
        FragranceInsertManager.getInstance().start();
    }

    @Override // com.xiaopeng.aiot.service.IWorkService
    public void onDestroy(Context context) {
        FragranceInsertManager.getInstance().stop();
    }

    @Override // com.xiaopeng.aiot.device.fragrance.model.FragranceInsertManager.OnChannelInsertListener
    public void onChannelInsert() {
        LogUtils.i(TAG, " onDataChanged Insert ");
        PageConfigFactory.FRAGRANCE2.go(this.mContext);
    }
}
