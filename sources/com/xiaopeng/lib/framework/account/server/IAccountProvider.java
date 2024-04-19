package com.xiaopeng.lib.framework.account.server;

import android.os.Bundle;
import android.os.RemoteException;
import com.xiaopeng.lib.framework.account.IXpCallback;
import com.xiaopeng.lib.framework.account.action.AccountAction;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo;
/* loaded from: classes.dex */
public interface IAccountProvider {
    void action(long j, AccountAction accountAction, Bundle bundle);

    void authorize(long j, String str, IXpCallback iXpCallback) throws RemoteException;

    void requestOTP(long j, String str, String str2, IXpCallback iXpCallback);

    IUserInfo userInfo();
}
