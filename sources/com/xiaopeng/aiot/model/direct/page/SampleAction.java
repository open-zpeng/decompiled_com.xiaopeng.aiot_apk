package com.xiaopeng.aiot.model.direct.page;

import android.content.Context;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.model.direct.ElementDirect;
import com.xiaopeng.iotlib.model.direct.PageDirectAction;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.HashMap;
/* loaded from: classes.dex */
public class SampleAction extends PageDirectAction {
    @Override // com.xiaopeng.iotlib.model.direct.PageDirectAction
    public boolean isSupport() {
        return false;
    }

    @Override // com.xiaopeng.iotlib.model.direct.PageDirectAction
    public void doAction(Context context) {
        LogUtils.i(LogConfig.TAG_DIRECT, " doAction= ");
        PageConfigFactory.SAMPLE.go(context);
    }

    @Override // com.xiaopeng.iotlib.model.direct.PageDirectAction
    public HashMap<String, ElementDirect> createElements() {
        HashMap<String, ElementDirect> hashMap = new HashMap<>();
        hashMap.put("channel3", new ElementDirect(R.id.btn01));
        return hashMap;
    }
}
