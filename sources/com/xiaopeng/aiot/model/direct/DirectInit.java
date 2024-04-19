package com.xiaopeng.aiot.model.direct;

import android.app.Application;
import android.net.Uri;
import com.xiaopeng.aiot.BuildConfig;
import com.xiaopeng.aiot.model.page.PageConfigDetail2;
import com.xiaopeng.aiot.model.page.PageConfigManager;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.model.direct.DirectManager;
import com.xiaopeng.iotlib.model.direct.IDirectUriPageIdConverter;
import com.xiaopeng.iotlib.utils.LogUtils;
/* loaded from: classes.dex */
public class DirectInit {
    private static IDirectUriPageIdConverter sIDirectUriPageIdConverter = new IDirectUriPageIdConverter() { // from class: com.xiaopeng.aiot.model.direct.-$$Lambda$DirectInit$-54yK_frR91hi777GJWzwcpO6d8
        @Override // com.xiaopeng.iotlib.model.direct.IDirectUriPageIdConverter
        public final PageId convert(Uri uri) {
            return DirectInit.lambda$static$0(uri);
        }
    };

    public static void init(Application application) {
        DirectManager.get().init(application, BuildConfig.SCHEME, BuildConfig.AUTH, "position", sIDirectUriPageIdConverter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ PageId lambda$static$0(Uri uri) {
        if (uri != null) {
            String path = uri.getPath();
            if (PageConfigDetail2.PATH.equals(path)) {
                return PageConfigManager.parseIntentForPageId(uri);
            }
            LogUtils.w(LogConfig.TAG_DIRECT, "convert path not valid" + path);
        }
        LogUtils.w(LogConfig.TAG_DIRECT, "convert uri is null");
        return null;
    }
}
