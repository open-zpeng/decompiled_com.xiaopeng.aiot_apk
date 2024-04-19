package com.xiaopeng.lib.apirouter.server;

import java.util.HashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class AiotSpeechObserver_Manifest {
    public static final String DESCRIPTOR = "com.xiaopeng.iotlib.provider.voice.command.AiotSpeechObserver";
    public static final int TRANSACTION_onEvent = 0;
    public static final int TRANSACTION_onQuery = 1;

    public static String toJsonManifest() {
        return "{\"authority\":\"com.xiaopeng.iotlib.provider.voice.command.AiotSpeechObserver\",\"DESCRIPTOR\":\"com.xiaopeng.iotlib.provider.voice.command.AiotSpeechObserver\",\"TRANSACTION\":[{\"path\":\"onEvent\",\"METHOD\":\"onEvent\",\"ID\":0,\"parameter\":[{\"alias\":\"event\",\"name\":\"event\"},{\"alias\":\"data\",\"name\":\"data\"}]},{\"path\":\"onQuery\",\"METHOD\":\"onQuery\",\"ID\":1,\"parameter\":[{\"alias\":\"event\",\"name\":\"event\"},{\"alias\":\"data\",\"name\":\"data\"},{\"alias\":\"callback\",\"name\":\"callback\"}]}]}";
    }

    public static Set<String> getKey() {
        HashSet hashSet = new HashSet(2);
        hashSet.add("AiotSpeechObserver");
        return hashSet;
    }
}
