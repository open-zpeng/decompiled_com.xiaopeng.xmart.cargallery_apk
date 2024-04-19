package com.xiaopeng.lib.framework.account.adapt;

import com.xiaopeng.lib.framework.moduleinterface.accountmodule.AbsException;
/* loaded from: classes.dex */
public class AccountException extends AbsException {
    private int mCode;

    public AccountException(String message) {
        super(message);
    }

    public AccountException(int code, String message) {
        super(message);
        this.mCode = code;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.AbsException
    public int getCode() {
        return this.mCode;
    }
}
