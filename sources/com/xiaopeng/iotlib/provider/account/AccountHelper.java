package com.xiaopeng.iotlib.provider.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.util.ArraySet;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.model.config.ApiConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.Iterator;
/* loaded from: classes.dex */
public class AccountHelper {
    private static final String TAG = "account";
    private AccountManager mAccountManager;
    private ArraySet<AccountCallBack> mCallBacks;

    public void setLoginTest(int i) {
        if (ApiConfig.ACCOUNT_DEBUG) {
            LogUtils.i(TAG, "setLoginTest " + i);
            if (i == 1) {
                Iterator<AccountCallBack> it = this.mCallBacks.iterator();
                while (it.hasNext()) {
                    it.next().onAccountChanged(true);
                }
                return;
            }
            Iterator<AccountCallBack> it2 = this.mCallBacks.iterator();
            while (it2.hasNext()) {
                it2.next().onAccountChanged(false);
            }
        }
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final AccountHelper INSTANCE = new AccountHelper();

        private SingletonHolder() {
        }
    }

    public static AccountHelper get() {
        return SingletonHolder.INSTANCE;
    }

    private AccountHelper() {
        this.mCallBacks = new ArraySet<>();
        this.mAccountManager = AccountManager.get(Iot.getApp());
        this.mAccountManager.addOnAccountsUpdatedListener(new OnAccountsUpdateListener() { // from class: com.xiaopeng.iotlib.provider.account.-$$Lambda$AccountHelper$jocnu8p7mb_nL6MOFlfpkmg58Z4
            @Override // android.accounts.OnAccountsUpdateListener
            public final void onAccountsUpdated(Account[] accountArr) {
                AccountHelper.this.lambda$new$0$AccountHelper(accountArr);
            }
        }, null, false);
    }

    public /* synthetic */ void lambda$new$0$AccountHelper(Account[] accountArr) {
        boolean isLogin = isLogin();
        LogUtils.i(TAG, "onAccountsUpdated : " + isLogin);
        Iterator<AccountCallBack> it = this.mCallBacks.iterator();
        while (it.hasNext()) {
            it.next().onAccountChanged(isLogin);
        }
    }

    public boolean isLogin() {
        if (this.mAccountManager == null) {
            return false;
        }
        if (ApiConfig.ACCOUNT_DEBUG) {
            return true;
        }
        boolean z = this.mAccountManager.getAccountsByType(Constants.ACCOUNT_TYPE_XP_VEHICLE).length > 0;
        LogUtils.d(TAG, "isLogin : " + z);
        return z;
    }

    public void addCallBack(AccountCallBack accountCallBack) {
        if (accountCallBack != null) {
            LogUtils.d(TAG, "addCallBack " + accountCallBack);
            this.mCallBacks.add(accountCallBack);
        }
    }

    public void removeCallBack(AccountCallBack accountCallBack) {
        if (accountCallBack != null) {
            LogUtils.d(TAG, "removeCallBack " + accountCallBack);
            this.mCallBacks.remove(accountCallBack);
        }
    }
}
