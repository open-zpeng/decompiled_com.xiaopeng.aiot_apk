package com.xiaopeng.iotlib.provider.router;

import android.net.Uri;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaopeng.aiot.BuildConfig;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.provider.iot.device.Freezer;
import com.xiaopeng.iotlib.provider.router.MessageContentBean;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class RouterHelper {
    private static final String TAG = "RouterHelper";

    private static MessageCenterBean getMessageCenterBean(String str, int i, String str2, String str3) {
        MessageContentBean createContent = MessageContentBean.createContent();
        createContent.setSensingType("环境感知");
        createContent.setPermanent(1);
        createContent.addTitle(str);
        createContent.setTts(str);
        if (str2 != null) {
            createContent.addButton(getMessageButton(str2, str3));
        }
        MessageCenterBean create = MessageCenterBean.create(1, createContent);
        create.setScene(i);
        create.setValidStartTs(System.currentTimeMillis());
        long currentTimeMillis = System.currentTimeMillis() + 86400000;
        create.setValidEndTs(currentTimeMillis);
        create.getContentBean().setValidTime(currentTimeMillis);
        return create;
    }

    private static MessageContentBean.MsgButton getMessageButton(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cmd", "open_url");
            jSONObject.put("data", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MessageContentBean.MsgButton create = MessageContentBean.MsgButton.create(str, VuiConstants.AI, jSONObject.toString(), true);
        create.setResponseWord(str);
        return create;
    }

    public static void sendAIMessage(String str, int i) {
        String json = new Gson().toJson(getMessageCenterBean(str, i, null, null));
        LogUtils.i(TAG, json);
        if (Iot.isForUnity()) {
            sendAIMessageUnity(json);
        } else {
            sendAIMessage(json);
        }
    }

    public static void sendAIMessage(String str, int i, String str2, String str3) {
        String json = new Gson().toJson(getMessageCenterBean(str, i, str2, str3));
        LogUtils.i(TAG, "sendAIMessage:" + json);
        if (Iot.isForUnity()) {
            sendAIMessageUnity(json);
        } else {
            sendAIMessage(json);
        }
    }

    private static void sendAIMessage(String str) {
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.message.center.IpcActionService").path("sendMessage").appendQueryParameter("msgId", "10009").appendQueryParameter(NotificationCompat.CATEGORY_MESSAGE, str);
        try {
            ApiRouter.route(builder.build());
        } catch (RemoteException | NoClassDefFoundError e) {
            e.printStackTrace();
            LogUtils.w(TAG, "onWebpEnd error for ApiRouter Exception.");
        }
    }

    private static void sendAIMessageUnity(String str) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("string_msg", str);
        jsonObject.addProperty("senderPackageName", BuildConfig.APPLICATION_ID);
        jsonObject.addProperty("receiverPackageName", VuiConstants.AI);
        String jsonObject2 = jsonObject.toString();
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.libtest.AllInIpcRouterService").path("onReceiverData").appendQueryParameter(VuiConstants.ELEMENT_ID, "10009").appendQueryParameter("bundle", jsonObject2);
        LogUtils.i(TAG, "sendAIMessageUnity:" + jsonObject2);
        try {
            ApiRouter.route(builder.build());
        } catch (RemoteException | NoClassDefFoundError e) {
            e.printStackTrace();
            LogUtils.w(TAG, "onWebpEnd error for ApiRouter Exception.");
        }
    }

    public static int getTrunkPowerSwitchStatus() {
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.chargecontrol.OpenApiService").path("getTrunkPowerSwitchStatus");
        try {
            return ((Integer) ApiRouter.route(builder.build())).intValue();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean setFridgeSwitchStatus(boolean z) {
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.chargecontrol.OpenApiService").path("setFridgeSwitchStatus").appendQueryParameter(Freezer.VAL_ON, String.valueOf(z));
        try {
            return ((Boolean) ApiRouter.route(builder.build())).booleanValue();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getTrunkPowerDelayOffTime() {
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.chargecontrol.OpenApiService").path("getTrunkPowerDelayOffTime");
        try {
            return (String) ApiRouter.route(builder.build());
        } catch (RemoteException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void sendDemo() {
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.aiot.AiotSpeechObserver").path("onQuery").appendQueryParameter(NotificationCompat.CATEGORY_EVENT, "1").appendQueryParameter("data", "2").appendQueryParameter("callback", "3");
        try {
            ApiRouter.route(builder.build());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
