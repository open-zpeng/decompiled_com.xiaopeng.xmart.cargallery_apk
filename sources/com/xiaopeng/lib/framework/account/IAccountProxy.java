package com.xiaopeng.lib.framework.account;

import android.app.Application;
import android.content.Context;
import com.xiaopeng.lib.framework.account.action.AccountAction;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.AbsException;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo;
/* loaded from: classes.dex */
public interface IAccountProxy {
    IUserInfo getUserInfo() throws AbsException;

    void init(Application application, String str, String str2);

    void release(Context context);

    void requestOAuth(ICallback<IAuthInfo, IError> iCallback);

    void requestOAuth(String str, ICallback<IAuthInfo, IError> iCallback);

    void requestOTP(String str, ICallback<IOTPInfo, IError> iCallback);

    void sendAction(AccountAction accountAction) throws AbsException;
}
