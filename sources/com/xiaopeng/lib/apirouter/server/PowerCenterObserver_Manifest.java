package com.xiaopeng.lib.apirouter.server;

import java.util.HashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class PowerCenterObserver_Manifest {
    public static final String DESCRIPTOR = "com.xiaopeng.iotlib.provider.power.PowerCenterObserver";
    public static final int TRANSACTION_onEvent = 0;

    public static String toJsonManifest() {
        return "{\"authority\":\"com.xiaopeng.iotlib.provider.power.PowerCenterObserver\",\"DESCRIPTOR\":\"com.xiaopeng.iotlib.provider.power.PowerCenterObserver\",\"TRANSACTION\":[{\"path\":\"onEvent\",\"METHOD\":\"onEvent\",\"ID\":0,\"parameter\":[{\"alias\":\"event\",\"name\":\"event\"},{\"alias\":\"data\",\"name\":\"data\"}]}]}";
    }

    public static Set<String> getKey() {
        HashSet hashSet = new HashSet(2);
        hashSet.add("PowerCenterObserver");
        return hashSet;
    }
}
