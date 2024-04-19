package com.xiaopeng.iotlib.model.direct;
/* loaded from: classes.dex */
public class ElementDirect {
    private ElementDirectAction action;
    private int id;
    private int parentId;

    private ElementDirect() {
    }

    public ElementDirect(int i) {
        this.id = i;
    }

    public ElementDirect(int i, ElementDirectAction elementDirectAction) {
        this.id = i;
        this.action = elementDirectAction;
    }

    public ElementDirect(int i, int i2, ElementDirectAction elementDirectAction) {
        this.id = i;
        this.parentId = i2;
        this.action = elementDirectAction;
    }

    public int getId() {
        return this.id;
    }

    public int getParentId() {
        return this.parentId;
    }

    public ElementDirectAction getAction() {
        return this.action;
    }

    public String toString() {
        return "ElementDirect{id=" + this.id + ", parentId=" + this.parentId + ", action=" + this.action + '}';
    }
}
