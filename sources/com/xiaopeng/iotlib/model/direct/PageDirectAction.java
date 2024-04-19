package com.xiaopeng.iotlib.model.direct;

import android.content.Context;
import java.util.HashMap;
/* loaded from: classes.dex */
public abstract class PageDirectAction {
    public abstract HashMap<String, ElementDirect> createElements();

    public abstract void doAction(Context context);

    public boolean isSupport() {
        return true;
    }

    public ElementDirect getElement(PageDirect pageDirect, String str) {
        if (pageDirect.getElements() == null) {
            return null;
        }
        return pageDirect.getElements().get(str);
    }
}
