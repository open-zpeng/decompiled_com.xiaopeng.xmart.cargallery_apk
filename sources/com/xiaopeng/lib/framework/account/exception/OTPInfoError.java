package com.xiaopeng.lib.framework.account.exception;

import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
/* loaded from: classes.dex */
public final class OTPInfoError implements IError {
    private int mCode;

    public OTPInfoError(int code) {
        this.mCode = code;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError
    public String getMessage() {
        switch (this.mCode) {
            case 1000:
                return IError.STR_SERVICE_DISCONNECTED;
            case 1001:
                return IError.STR_USER_LOGOUT;
            case 1002:
                return IError.STR_REQUEST_FAILED;
            default:
                return IError.STR_UNKNOWN_ERR;
        }
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError
    public int getCode() {
        return this.mCode;
    }
}
