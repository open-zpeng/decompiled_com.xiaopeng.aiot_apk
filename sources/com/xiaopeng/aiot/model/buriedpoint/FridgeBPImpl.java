package com.xiaopeng.aiot.model.buriedpoint;
/* loaded from: classes.dex */
class FridgeBPImpl extends BaseBP implements IFridgeBP {
    private static final String BTN_FRIDGE_INTO = "B001";
    private static final String BTN_FRIDGE_POWER = "B002";
    private static final String BTN_FRIDGE_TEMPERATURE = "B003";
    private static final String PAGE_FRIDGE = "P10135";

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFridgeBP
    public void into(int i) {
        sendDataLog(PAGE_FRIDGE, BTN_FRIDGE_INTO, "source", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFridgeBP
    public void enable(boolean z) {
        sendDataLog(PAGE_FRIDGE, BTN_FRIDGE_POWER, "type", z ? 1 : 2);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFridgeBP
    public void setTemperature(int i) {
        sendDataLog(PAGE_FRIDGE, BTN_FRIDGE_TEMPERATURE, "type", i);
    }
}
