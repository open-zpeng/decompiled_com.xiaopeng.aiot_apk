package com.xiaopeng.iotlib.provider.voice.vui;

import android.app.Application;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.xui.app.XDialog;
/* loaded from: classes.dex */
public class VuiManager {
    private static Application mApp;

    public static void init(Application application) {
        mApp = application;
        String str = application.getPackageName() + ".VuiObserver";
        LogUtils.d(LogConfig.TAG_VUI_SCENE, " VuiManager init " + str);
        VuiEngine.getInstance(mApp).setLoglevel(3);
        VuiEngine.getInstance(mApp).subscribe(str);
    }

    public static boolean supportVui() {
        return mApp != null;
    }

    public static void initDialogVuiScene(XDialog xDialog, String str) {
        Application application = mApp;
        if (application == null) {
            return;
        }
        xDialog.initVuiScene(str, VuiEngine.getInstance(application));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void initScene(Lifecycle lifecycle, String str, View view, IVuiSceneListener iVuiSceneListener, IVuiElementChangedListener iVuiElementChangedListener) {
        Application application = mApp;
        if (application == null) {
            return;
        }
        VuiEngine.getInstance(application).initScene(lifecycle, str, view, iVuiSceneListener, iVuiElementChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void updateElementAttribute(String str, View view) {
        Application application = mApp;
        if (application == null) {
            return;
        }
        VuiEngine.getInstance(application).updateElementAttribute(str, view);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void updateScene(String str, View view) {
        Application application = mApp;
        if (application == null) {
            return;
        }
        VuiEngine.getInstance(application).updateScene(str, view);
    }
}
