package com.xiaopeng.lib.framework.account;

import android.os.Build;
import com.xiaopeng.lib.framework.account.adapt.AccountManagerProxy;
import com.xiaopeng.lib.framework.account.client.XpAccountProxy;
/* loaded from: classes.dex */
class AccountProxyFactory {

    /* loaded from: classes.dex */
    private static final class Holder {
        private static final AccountProxyFactory INSTANCE = new AccountProxyFactory();

        private Holder() {
        }
    }

    private AccountProxyFactory() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AccountProxyFactory getInstance() {
        return Holder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public IAccountProxy getProxy() {
        if (Build.VERSION.SDK_INT >= 28) {
            return AccountManagerProxy.getInstance();
        }
        return XpAccountProxy.getInstance();
    }
}
