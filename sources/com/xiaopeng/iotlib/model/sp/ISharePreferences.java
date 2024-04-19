package com.xiaopeng.iotlib.model.sp;

import android.content.SharedPreferences;
import java.util.Set;
/* loaded from: classes.dex */
public interface ISharePreferences {
    void apply();

    SharedPreferences.Editor clear();

    boolean commit();

    boolean getBoolean(String str, boolean z);

    float getFloat(String str, long j);

    int getInt(String str, int i);

    long getLong(String str, long j);

    String getString(String str, String str2);

    Set<String> getStringSet(String str, Set<String> set);

    SharedPreferences.Editor putBoolean(String str, boolean z);

    SharedPreferences.Editor putFloat(String str, float f);

    SharedPreferences.Editor putInt(String str, int i);

    SharedPreferences.Editor putLong(String str, long j);

    SharedPreferences.Editor putString(String str, String str2);

    SharedPreferences.Editor putStringSet(String str, Set<String> set);

    SharedPreferences.Editor remove(String str);
}
