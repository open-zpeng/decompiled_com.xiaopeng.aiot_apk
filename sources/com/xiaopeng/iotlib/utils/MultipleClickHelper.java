package com.xiaopeng.iotlib.utils;

import android.os.SystemClock;
import android.view.View;
import java.util.Arrays;
/* loaded from: classes.dex */
public class MultipleClickHelper implements View.OnClickListener {
    private static final int COUNTS = 5;
    private static final long DURATION = 1000;
    private static final String TAG = "MultiClick";
    private long[] mClicks;
    private View.OnClickListener mOnClickListener;

    private MultipleClickHelper() {
        this.mClicks = new long[5];
    }

    private MultipleClickHelper(View.OnClickListener onClickListener) {
        this.mClicks = new long[5];
        this.mOnClickListener = onClickListener;
    }

    public static MultipleClickHelper bind(View view, View.OnClickListener onClickListener) {
        MultipleClickHelper multipleClickHelper = new MultipleClickHelper(onClickListener);
        view.setOnClickListener(multipleClickHelper);
        return multipleClickHelper;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        long[] jArr = this.mClicks;
        int i = 0;
        System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
        long[] jArr2 = this.mClicks;
        jArr2[jArr2.length - 1] = SystemClock.uptimeMillis();
        LogUtils.d(TAG, " arraycopy " + Arrays.toString(this.mClicks));
        if (SystemClock.uptimeMillis() - this.mClicks[0] < DURATION) {
            while (true) {
                long[] jArr3 = this.mClicks;
                if (i >= jArr3.length) {
                    break;
                }
                jArr3[i] = 0;
                i++;
            }
            View.OnClickListener onClickListener = this.mOnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }
}
