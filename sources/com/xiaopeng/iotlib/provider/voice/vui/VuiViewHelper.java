package com.xiaopeng.iotlib.provider.voice.vui;

import android.view.View;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
/* loaded from: classes.dex */
public class VuiViewHelper {
    public static boolean isVuiEvent(View view) {
        return VuiUtils.isPerformVuiAction(view);
    }

    public static boolean isVuiEventAndClear(View view) {
        boolean isPerformVuiAction = VuiUtils.isPerformVuiAction(view);
        clearVuiEvent(view);
        return isPerformVuiAction;
    }

    public static void clearVuiEvent(View view) {
        if (view instanceof IVuiElement) {
            ((IVuiElement) view).setPerformVuiAction(false);
        }
    }

    public static void addHasFeedbackProp(View view) {
        if (view instanceof IVuiElement) {
            VuiUtils.addHasFeedbackProp((IVuiElement) view);
        }
    }

    public static void addMatchFirstProp(View view) {
        if (view instanceof IVuiElement) {
            VuiUtils.addMatchFirstProp((IVuiElement) view);
        }
    }

    public static void addSKipAlreadyProp(View view) {
        if (view instanceof IVuiElement) {
            VuiUtils.addSKipAlreadyProp((IVuiElement) view);
        }
    }

    public static void addDefaultPriorityValue(View view, int i) {
        if (view instanceof IVuiElement) {
            VuiUtils.addDefaultPriorityValue((IVuiElement) view, i);
        }
    }
}
