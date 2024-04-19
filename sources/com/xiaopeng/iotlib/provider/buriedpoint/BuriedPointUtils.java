package com.xiaopeng.iotlib.provider.buriedpoint;

import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
/* loaded from: classes.dex */
public class BuriedPointUtils {
    private static final String MODULE = "carlife";

    public static void sendButtonDataLog(final String str, final String str2, final String str3, final int i) {
        LogUtils.i("buriedp", "sendButtonDataLog:" + str + "," + str2 + "," + str3 + "," + i);
        ThreadUtils.MULTI.post(new Runnable() { // from class: com.xiaopeng.iotlib.provider.buriedpoint.-$$Lambda$BuriedPointUtils$iQmOhHxpKFqbpfDB5Fw6WM8fuVs
            @Override // java.lang.Runnable
            public final void run() {
                BuriedPointUtils.lambda$sendButtonDataLog$0(str, str2, str3, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sendButtonDataLog$0(String str, String str2, String str3, int i) {
        IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        IMoleEvent build = iDataLog.buildMoleEvent().setModule(MODULE).setPageId(str).setButtonId(str2).setProperty(str3, Integer.valueOf(i)).build();
        if (build != null) {
            iDataLog.sendStatData(build);
        }
    }
}
