package com.xiaopeng.lib.framework.account;

import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount;
/* loaded from: classes.dex */
public class AccountModuleEntry implements IModuleEntry {
    @Override // com.xiaopeng.lib.framework.module.IModuleEntry
    public Object get(Class aClass) {
        if (aClass == IAccount.class) {
            return AccountImpl.getInstance();
        }
        return null;
    }
}
