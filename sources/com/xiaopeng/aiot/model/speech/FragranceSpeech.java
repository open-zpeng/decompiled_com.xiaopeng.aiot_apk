package com.xiaopeng.aiot.model.speech;

import android.app.Application;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.gson.Gson;
import com.xiaopeng.aiot.device.fragrance.page.FragrancePageConfigUtil;
import com.xiaopeng.aiot.device.fragrance.page.FragrancePageData;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent;
import com.xiaopeng.iotlib.provider.voice.command.SpeechManager;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes.dex */
class FragranceSpeech implements ISpeechEvent {
    static final String CHANGE = "ac.perfume.potency.set";
    static final int CHANGE_001 = 201;
    static final int CHANGE_002 = 202;
    static final int CHANGE_003 = 203;
    static final int CHANGE_004 = 204;
    static final String CLOSE = "ac.perfume.off";
    static final int CLOSE_001 = 100;
    static final int CLOSE_002 = 101;
    static final int CLOSE_003 = 103;
    static final int CON_001 = 100;
    static final int CON_002 = 101;
    static final int CON_003 = 102;
    static final int CON_004 = 103;
    static final int CON_005 = 104;
    static final int CON_006 = 105;
    static final String GLOBAL_CLOSE = "gui.page.close.aiot";
    static final String GLOBAL_OPEN = "gui.page.open.aiot";
    static final String GLOBAL_OPEN_STATE = "gui.page.open.state.aiot";
    static final String OPEN = "ac.perfume.on";
    static final int OPEN_001 = 100;
    static final int OPEN_002 = 101;
    static final int OPEN_003 = 102;
    private final Application mApp;
    private static final SparseArray<String> sOpenResult = new SparseArray<>();
    private static final SparseArray<String> sCloseResult = new SparseArray<>();
    private static final SparseArray<String> sConResult = new SparseArray<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public FragranceSpeech(Application application) {
        this.mApp = application;
        if (SpeechConfig.SPEECH_DEBUG) {
            sOpenResult.put(-1, "无效的语音指令");
            sOpenResult.put(0, "香氛获取出故障了，请稍后再试哦");
            sOpenResult.put(100, "OPEN_001 已经打开啦");
            sOpenResult.put(101, "OPEN_001 香氛打开啦");
            sOpenResult.put(102, "OPEN_001 请先插入香氛瓶哦");
            sOpenResult.put(CHANGE_001, "CHANGE_001 已调节香氛气味");
            sOpenResult.put(CHANGE_002, "CHANGE_002 已经是当前香味了哦");
            sOpenResult.put(CHANGE_003, "CHANGE_003 没有新的香味可以切换哦");
            sOpenResult.put(CHANGE_004, "CHANGE_004 没有打开香氛无法切换气味哦");
            sCloseResult.put(100, "CLOSE_001 香氛关闭了哦");
            sCloseResult.put(101, "CLOSE_002 已关闭该香氛气味了哦");
            sCloseResult.put(103, "CLOSE_003 请先插入香氛瓶哦");
            sConResult.put(100, "CON_001 香氛浓度已调高");
            sConResult.put(101, "CON_002 香氛浓度已调低");
            sConResult.put(102, "CON_003 香氛浓度已经是高浓度了哦");
            sConResult.put(103, "CON_004 香氛浓度已经是低浓度了哦");
            sConResult.put(104, "CON_005 需打开香氛后才能感受到香氛浓度变化哦");
            sConResult.put(105, "CON_006 请先插入香氛瓶哦");
        }
    }

    @Override // com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent
    public void onEvent(String str, String str2) {
        LogUtils.i(LogConfig.TAG_SPEECH, "Fragrance onEvent not work " + str);
    }

    @Override // com.xiaopeng.iotlib.provider.voice.command.ISpeechEvent
    public void onQuery(String str, String str2, String str3) {
        int i;
        LogUtils.i(LogConfig.TAG_SPEECH, "Fragrance onQuery event " + str + " , data " + str2 + " ,callback: " + str3);
        if (OPEN.equals(str)) {
            i = open(str2);
            LogUtils.i(LogConfig.TAG_SPEECH, "open " + i + " , " + sOpenResult.get(i));
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(" , ");
            sb.append(sOpenResult.get(i));
            showToast(sb.toString());
        } else if (CLOSE.equals(str)) {
            i = createEvent().close();
            LogUtils.i(LogConfig.TAG_SPEECH, "close " + i + " , " + sCloseResult.get(i));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(i);
            sb2.append(" , ");
            sb2.append(sCloseResult.get(i));
            showToast(sb2.toString());
        } else if (CHANGE.equals(str)) {
            i = changeCon(str2);
            LogUtils.i(LogConfig.TAG_SPEECH, "changeCon " + i + " , " + sConResult.get(i));
            StringBuilder sb3 = new StringBuilder();
            sb3.append(i);
            sb3.append(" , ");
            sb3.append(sConResult.get(i));
            showToast(sb3.toString());
        } else if (GLOBAL_OPEN_STATE.equals(str)) {
            i = judgeState(str2);
            LogUtils.i(LogConfig.TAG_SPEECH, "open state result: " + i);
        } else if (GLOBAL_OPEN.equals(str)) {
            i = openPage(str2);
            LogUtils.i(LogConfig.TAG_SPEECH, "global open and result: " + i);
        } else if (GLOBAL_CLOSE.equals(str)) {
            i = closePage(str2);
            LogUtils.i(LogConfig.TAG_SPEECH, "global close and result: " + i);
        } else {
            LogUtils.i(LogConfig.TAG_SPEECH, "onQuery illegal event " + str);
            showToast("onQuery illegal event " + str);
            i = -1;
        }
        SpeechManager.get().postQueryResult(str, str3, Integer.valueOf(i));
    }

    private void showToast(final String str) {
        if (SpeechConfig.SPEECH_DEBUG) {
            ThreadUtils.UI.post(new Runnable() { // from class: com.xiaopeng.aiot.model.speech.-$$Lambda$FragranceSpeech$JQw9rsA0NlpHl5I9eet4jso8v44
                @Override // java.lang.Runnable
                public final void run() {
                    XToast.show(str);
                }
            });
        }
    }

    private int open(String str) {
        FragranceData fragranceData = !TextUtils.isEmpty(str) ? (FragranceData) new Gson().fromJson(str, (Class<Object>) FragranceData.class) : null;
        if (fragranceData == null || (fragranceData.getName() == -1 && fragranceData.getPosition() == -1)) {
            return createEvent().openFuzzy();
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "open " + fragranceData.toString());
        int name = fragranceData.getName();
        if (name == 0) {
            return createEvent().next();
        }
        if (name > 0 && name <= 15) {
            return createEvent().openToType((name + 101) - 1);
        }
        int position = fragranceData.getPosition();
        if (position > 0 && position <= 3) {
            return createEvent().openToPosition(position - 1);
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "FragranceSpeech open illegal data " + str);
        return -1;
    }

    private int changeCon(String str) {
        FragranceData fragranceData = !TextUtils.isEmpty(str) ? (FragranceData) new Gson().fromJson(str, (Class<Object>) FragranceData.class) : null;
        if (fragranceData != null && fragranceData.getConcentration() != null) {
            String concentration = fragranceData.getConcentration();
            if ("high".equals(concentration)) {
                return createEvent().changeConHigh();
            }
            if ("low".equals(concentration)) {
                return createEvent().changeConLow();
            }
            LogUtils.w(LogConfig.TAG_SPEECH, "FragranceSpeech changeCon illegal data " + str);
        }
        return -1;
    }

    private int openPage(String str) {
        FragrancePageData fragrancePageData = !TextUtils.isEmpty(str) ? (FragrancePageData) new Gson().fromJson(str, (Class<Object>) FragrancePageData.class) : null;
        if (fragrancePageData == null || fragrancePageData.getDisplay_location() == -1) {
            LogUtils.i(LogConfig.TAG_SPEECH, "fragrancePageData is illegal: " + fragrancePageData.getDisplay_location());
            return 1;
        }
        FragrancePageConfigUtil.showFragrancePage(this.mApp, fragrancePageData.getDisplay_location());
        LogUtils.i(LogConfig.TAG_SPEECH, "opened screen: " + fragrancePageData.getDisplay_location());
        return 0;
    }

    private int judgeState(String str) {
        FragrancePageData fragrancePageData = !TextUtils.isEmpty(str) ? (FragrancePageData) new Gson().fromJson(str, (Class<Object>) FragrancePageData.class) : null;
        if (FragrancePageConfigUtil.isSpeechLocation(fragrancePageData.getDisplay_location(), fragrancePageData.getPage_id(), this.mApp)) {
            LogUtils.i(LogConfig.TAG_SPEECH, "display location: " + fragrancePageData.getDisplay_location() + "PageId: " + fragrancePageData.getPage_id());
            return 0;
        }
        LogUtils.i(LogConfig.TAG_SPEECH, "fragrance page is closed for current screen");
        return 1;
    }

    private int closePage(String str) {
        FragrancePageData fragrancePageData = !TextUtils.isEmpty(str) ? (FragrancePageData) new Gson().fromJson(str, (Class<Object>) FragrancePageData.class) : null;
        if (fragrancePageData == null || fragrancePageData.getPage_id() == null) {
            LogUtils.i(LogConfig.TAG_SPEECH, "close failed,fragrancePageData is null");
            return 1;
        } else if (FragrancePageConfigUtil.hasShow(fragrancePageData.getPage_id())) {
            LogUtils.i(LogConfig.TAG_SPEECH, "fragrance closed pageId: " + fragrancePageData.getPage_id());
            FragrancePageConfigUtil.closeFragrancePage(this.mApp);
            return 0;
        } else {
            LogUtils.i(LogConfig.TAG_SPEECH, "fragrance page close failed");
            return 1;
        }
    }

    private FragranceEvent createEvent() {
        return new FragranceEventImpl();
    }
}
