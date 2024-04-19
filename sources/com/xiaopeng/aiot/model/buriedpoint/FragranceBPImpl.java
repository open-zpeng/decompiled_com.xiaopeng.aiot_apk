package com.xiaopeng.aiot.model.buriedpoint;
/* loaded from: classes.dex */
class FragranceBPImpl extends BaseBP implements IFragranceBP {
    private static final String BTN_FRAGRANCE_CLOSE = "B003";
    private static final String BTN_FRAGRANCE_DASHI = "B006";
    private static final String BTN_FRAGRANCE_DENSITY = "B004";
    private static final String BTN_FRAGRANCE_INTO = "B001";
    private static final String BTN_FRAGRANCE_MORE = "B005";
    private static final String BTN_FRAGRANCE_OPEN = "B002";
    private static final String PAGE_FRAGRANCE = "P10134";

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceBP
    public void into(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_INTO, "source", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceBP
    public void open(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_OPEN, "type", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceBP
    public void close(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_CLOSE, "type", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceBP
    public void density(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_DENSITY, "type", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceBP
    public void more(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_MORE, "type", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceBP
    public void greatMaster(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_DASHI, "type", i);
    }
}
