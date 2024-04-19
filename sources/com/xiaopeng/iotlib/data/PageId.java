package com.xiaopeng.iotlib.data;

import java.util.Objects;
/* loaded from: classes.dex */
public final class PageId {
    private String _pageId;

    public PageId(String str) {
        this._pageId = str;
    }

    public String get_pageId() {
        return this._pageId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this._pageId, ((PageId) obj)._pageId);
    }

    public int hashCode() {
        return Objects.hash(this._pageId);
    }

    public String toString() {
        return this._pageId;
    }
}
