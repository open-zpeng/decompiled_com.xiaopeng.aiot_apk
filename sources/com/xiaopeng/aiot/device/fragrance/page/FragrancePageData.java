package com.xiaopeng.aiot.device.fragrance.page;

import java.io.Serializable;
/* loaded from: classes.dex */
public class FragrancePageData implements Serializable {
    private int display_location = -1;
    private String page_id;

    public int getDisplay_location() {
        return this.display_location;
    }

    public void setDisplay_location(int i) {
        this.display_location = i;
    }

    public String getPage_id() {
        return this.page_id;
    }

    public void setPage_id(String str) {
        this.page_id = str;
    }

    public String toString() {
        return "FragrancePageData{display_location='" + this.display_location + "', page_id='" + this.page_id + "'}";
    }
}
