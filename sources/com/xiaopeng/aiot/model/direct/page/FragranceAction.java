package com.xiaopeng.aiot.model.direct.page;

import android.content.Context;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.model.direct.element.Channel3Action;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.model.direct.ElementDirect;
import com.xiaopeng.iotlib.model.direct.PageDirectAction;
import com.xiaopeng.iotlib.model.sp.IFragranceSave;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.HashMap;
/* loaded from: classes.dex */
public class FragranceAction extends PageDirectAction {
    @Override // com.xiaopeng.iotlib.model.direct.PageDirectAction
    public void doAction(Context context) {
        LogUtils.i(LogConfig.TAG_DIRECT, " doAction= ");
    }

    @Override // com.xiaopeng.iotlib.model.direct.PageDirectAction
    public HashMap<String, ElementDirect> createElements() {
        HashMap<String, ElementDirect> hashMap = new HashMap<>();
        hashMap.put(IFragranceSave.KEY_DENSITY, new ElementDirect(R.id.tabLayout));
        hashMap.put("channel1", new ElementDirect(R.id.icon1));
        hashMap.put("channel2", new ElementDirect(R.id.icon2));
        hashMap.put("channel3", new ElementDirect(R.id.icon3, new Channel3Action()));
        return hashMap;
    }
}
