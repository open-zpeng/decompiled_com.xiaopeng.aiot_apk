package com.xiaopeng.aiot.model.common;

import com.xiaopeng.aiot.app.R;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.provider.router.RouterHelper;
/* loaded from: classes.dex */
public class AiCardUtils {
    private static final int SCENE_CO = 8004;
    private static final int SCENE_DOOR_OPEN = 5008;

    public static void sendCoCard() {
        RouterHelper.sendAIMessage(Iot.getApp().getString(R.string.fragrance_co_high), SCENE_CO);
    }

    public static void sendFridgeDoorCard() {
        RouterHelper.sendAIMessage(Iot.getApp().getString(R.string.fridge2_door_open_ai_card), SCENE_DOOR_OPEN, Iot.getApp().getString(R.string.fridge2_door_open_ai_card_button), "xiaopeng://aiot/device/detail?type=fridge&from=aiassistant");
    }
}
