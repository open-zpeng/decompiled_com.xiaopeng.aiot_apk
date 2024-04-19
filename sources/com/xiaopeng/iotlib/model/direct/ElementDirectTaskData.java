package com.xiaopeng.iotlib.model.direct;

import com.xiaopeng.iotlib.data.PageId;
/* loaded from: classes.dex */
public class ElementDirectTaskData {
    private ElementDirect element;
    private PageId pageId;

    public ElementDirectTaskData(PageId pageId, ElementDirect elementDirect) {
        this.pageId = pageId;
        this.element = elementDirect;
    }

    public PageId getPageId() {
        return this.pageId;
    }

    public ElementDirect getElement() {
        return this.element;
    }

    public String toString() {
        return "ElementDirectTaskData{pageId='" + this.pageId + "', element=" + this.element + '}';
    }
}
