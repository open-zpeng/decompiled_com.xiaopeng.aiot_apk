package com.xiaopeng.iotlib.model.direct;

import com.xiaopeng.iotlib.data.PageId;
import java.util.HashMap;
/* loaded from: classes.dex */
public class PageDirect {
    private PageDirectAction action;
    private HashMap<String, ElementDirect> mElements;
    private PageId pageId;

    private PageDirect() {
    }

    public PageDirect(PageId pageId, PageDirectAction pageDirectAction) {
        this.pageId = pageId;
        this.action = pageDirectAction;
    }

    public PageDirectAction getAction() {
        return this.action;
    }

    public HashMap<String, ElementDirect> getElements() {
        return this.mElements;
    }

    public void setElements(HashMap<String, ElementDirect> hashMap) {
        this.mElements = hashMap;
    }

    public PageId getPageId() {
        return this.pageId;
    }

    public String toString() {
        return "PageDirect{pageId='" + this.pageId + "', action=" + this.action + ", mElements=" + this.mElements + '}';
    }
}
