package com.xiaopeng.iotlib.utils;

import android.support.rastermill.FrameSequenceUtil;
import android.widget.ImageView;
/* loaded from: classes.dex */
public class WebpUtils {
    public static void loadWebp(ImageView imageView, int i) {
        try {
            FrameSequenceUtil.destroy(imageView);
            FrameSequenceUtil.with(imageView).resourceId(i).decodingThreadId(0).loopBehavior(2).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destroy(ImageView imageView) {
        try {
            FrameSequenceUtil.destroy(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
