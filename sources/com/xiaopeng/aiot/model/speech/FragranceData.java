package com.xiaopeng.aiot.model.speech;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.speech.vui.constants.VuiConstants;
/* loaded from: classes.dex */
class FragranceData {
    @SerializedName(VuiConstants.ELEMENT_VALUE)
    private String concentration;
    private int name = -1;
    private int position = -1;

    FragranceData() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getName() {
        return this.name;
    }

    void setName(int i) {
        this.name = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPosition() {
        return this.position;
    }

    void setPosition(int i) {
        this.position = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getConcentration() {
        return this.concentration;
    }

    void setConcentration(String str) {
        this.concentration = str;
    }

    public String toString() {
        return "FragranceData{name='" + this.name + "', position='" + this.position + "', concentration='" + this.concentration + "'}";
    }
}
