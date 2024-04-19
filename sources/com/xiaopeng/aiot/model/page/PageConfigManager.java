package com.xiaopeng.aiot.model.page;

import android.content.Intent;
import android.net.Uri;
import com.xiaopeng.aiot.BuildConfig;
import com.xiaopeng.iotlib.base.BasePageConfig;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.iot.IotManagers;
import com.xiaopeng.iotlib.provider.iot.device.Device;
import com.xiaopeng.iotlib.provider.iot.device.Fridge;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.HashMap;
import java.util.List;
/* loaded from: classes.dex */
public class PageConfigManager {
    private static volatile boolean sInit;
    private static final HashMap<PageId, BasePageConfig> sPageConfig = new HashMap<>();

    public static void init() {
        if (sInit) {
            return;
        }
        sInit = true;
        if (ApiConfig.USE_OLD_PAGE) {
            sPageConfig.put(PageConfigFactory.FRAGRANCE.pageId(), PageConfigFactory.FRAGRANCE);
            sPageConfig.put(PageConfigFactory.SAMPLE.pageId(), PageConfigFactory.SAMPLE);
            sPageConfig.put(PageConfigFactory.FRIDGE.pageId(), PageConfigFactory.FRIDGE);
            sPageConfig.put(PageConfigFactory.WATCHES.pageId(), PageConfigFactory.WATCHES);
        }
        sPageConfig.put(PageConfigFactory.BLUE_FRIDGE.pageId(), PageConfigFactory.BLUE_FRIDGE);
        sPageConfig.put(PageConfigFactory.BLUE_CHILDSEAT.pageId(), PageConfigFactory.BLUE_CHILDSEAT);
        sPageConfig.put(PageConfigFactory.BLUE_AIR_BED.pageId(), PageConfigFactory.BLUE_AIR_BED);
        sPageConfig.put(PageConfigFactory.FRIDGE2.pageId(), PageConfigFactory.FRIDGE2);
        sPageConfig.put(PageConfigFactory.FRAGRANCE2.pageId(), PageConfigFactory.FRAGRANCE2);
        sPageConfig.put(PageConfigFactory.CHILD_SEAT.pageId(), PageConfigFactory.CHILD_SEAT);
        sPageConfig.put(PageConfigFactory.AIR_BED.pageId(), PageConfigFactory.AIR_BED);
    }

    private static BasePageConfig getPageConfig(PageId pageId) {
        if (!sInit) {
            LogUtils.i(LogConfig.TAG_PAGE_CONFIG, "getPageConfig but must init: ");
            init();
        }
        return sPageConfig.get(pageId);
    }

    public static Uri.Builder createScheme() {
        return new Uri.Builder().scheme(BuildConfig.SCHEME).authority(BuildConfig.AUTH);
    }

    public static boolean checkScheme(Uri uri) {
        return uri != null && BuildConfig.SCHEME.equals(uri.getScheme()) && BuildConfig.AUTH.equals(uri.getAuthority());
    }

    public static PageId parseIntentForPageId(Uri uri) {
        if (uri == null) {
            LogUtils.w(LogConfig.TAG_PAGE_CONFIG, "parseIntentForPageId uri is null");
            return null;
        }
        LogUtils.i(LogConfig.TAG_PAGE_CONFIG, uri.toString());
        String path = uri.getPath();
        String queryParameter = uri.getQueryParameter("type");
        if (queryParameter == null) {
            queryParameter = "";
        }
        PageId pageId = new PageId(path + "/" + queryParameter);
        StringBuilder sb = new StringBuilder();
        sb.append("parseIntentForPageId ");
        sb.append(pageId);
        LogUtils.d(LogConfig.TAG_PAGE_CONFIG, sb.toString());
        return pageId;
    }

    public static BasePageConfig parseIntentForPageConfig(Intent intent) {
        List<Device> device;
        if (!"android.intent.action.VIEW".equals(intent.getAction())) {
            LogUtils.w(LogConfig.TAG_PAGE_CONFIG, "parseIntentForPageId action is not ACTION_VIEW. ");
            return null;
        } else if (intent.getData() == null) {
            LogUtils.w(LogConfig.TAG_PAGE_CONFIG, "parseIntentForPageId getData is null ");
            return null;
        } else {
            Uri data = intent.getData();
            if (!checkScheme(data)) {
                LogUtils.w(LogConfig.TAG_PAGE_CONFIG, "parseIntentForPageId uri not valid " + data);
                return null;
            }
            PageId parseIntentForPageId = parseIntentForPageId(data);
            if (PageConfigFactory.FRIDGE.pageId().equals(parseIntentForPageId) && ((device = IotManagers.getIotControl().getDevice(Fridge.DEVICE_TYPE)) == null || device.size() == 0)) {
                LogUtils.w(LogConfig.TAG_PAGE_CONFIG, "parseIntentForPageId fridge device is null ");
                return PageConfigFactory.BLUE_FRIDGE;
            }
            return getPageConfig(parseIntentForPageId);
        }
    }
}
