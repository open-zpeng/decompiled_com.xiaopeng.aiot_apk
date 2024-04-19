package com.xiaopeng.aiot.model.buriedpoint;
/* loaded from: classes.dex */
class FragranceDLImpl extends BaseDataLog implements IFragranceDL {
    private static final String BTN_FRAGRANCE_CLOSE = "B003";
    private static final String BTN_FRAGRANCE_DASHI = "B006";
    private static final String BTN_FRAGRANCE_DENSITY = "B004";
    private static final String BTN_FRAGRANCE_INTO = "B001";
    private static final String BTN_FRAGRANCE_MORE = "B005";
    private static final String BTN_FRAGRANCE_OPEN = "B002";
    private static final String PAGE_FRAGRANCE = "P10134";

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceDL
    public void into(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_INTO, "source", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceDL
    public void open(int i, int i2) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_OPEN, "type", i);
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_OPEN, "source", i2);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceDL
    public void close(int i, int i2) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_CLOSE, "type", i);
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_CLOSE, "source", i2);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceDL
    public void density(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_DENSITY, "type", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceDL
    public void more(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_MORE, "type", i);
    }

    @Override // com.xiaopeng.aiot.model.buriedpoint.IFragranceDL
    public void greatMaster(int i) {
        sendDataLog(PAGE_FRAGRANCE, BTN_FRAGRANCE_DASHI, "type", i);
    }
}
