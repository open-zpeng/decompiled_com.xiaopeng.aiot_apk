package com.xiaopeng.iotlib.model.sp;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;
/* loaded from: classes.dex */
public class SharePreferenceImpl implements ISharePreferences {
    private Context mContext;
    private volatile SharedPreferences.Editor mEditor;
    private String mName;
    private volatile SharedPreferences mSharedPreferences;

    private SharePreferenceImpl() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SharePreferenceImpl(Context context, String str) {
        this.mContext = context.getApplicationContext();
        this.mName = str;
    }

    private SharedPreferences getSharedPreferences() {
        if (this.mSharedPreferences == null) {
            synchronized (this) {
                if (this.mSharedPreferences == null) {
                    this.mSharedPreferences = this.mContext.getSharedPreferences(this.mName, 0);
                }
            }
        }
        return this.mSharedPreferences;
    }

    private SharedPreferences.Editor getEditor() {
        if (this.mEditor == null) {
            synchronized (this) {
                if (this.mEditor == null) {
                    this.mEditor = getSharedPreferences().edit();
                }
            }
        }
        return this.mEditor;
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public SharedPreferences.Editor putString(String str, String str2) {
        return getEditor().putString(str, str2);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public SharedPreferences.Editor putStringSet(String str, Set<String> set) {
        return getEditor().putStringSet(str, set);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public Set<String> getStringSet(String str, Set<String> set) {
        return getSharedPreferences().getStringSet(str, set);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public SharedPreferences.Editor putInt(String str, int i) {
        return getEditor().putInt(str, i);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public SharedPreferences.Editor putLong(String str, long j) {
        return getEditor().putLong(str, j);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public long getLong(String str, long j) {
        return getSharedPreferences().getLong(str, j);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public SharedPreferences.Editor putFloat(String str, float f) {
        return getEditor().putFloat(str, f);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public float getFloat(String str, long j) {
        return getSharedPreferences().getFloat(str, (float) j);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public SharedPreferences.Editor putBoolean(String str, boolean z) {
        return getEditor().putBoolean(str, z);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public boolean getBoolean(String str, boolean z) {
        return getSharedPreferences().getBoolean(str, z);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public String getString(String str, String str2) {
        return getSharedPreferences().getString(str, str2);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public int getInt(String str, int i) {
        return getSharedPreferences().getInt(str, i);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public SharedPreferences.Editor remove(String str) {
        return getEditor().remove(str);
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public SharedPreferences.Editor clear() {
        return getEditor().clear();
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public boolean commit() {
        return getEditor().commit();
    }

    @Override // com.xiaopeng.iotlib.model.sp.ISharePreferences
    public void apply() {
        getEditor().apply();
    }
}
