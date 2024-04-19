package com.xiaopeng.lib.framework.account;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.xiaopeng.lib.framework.account.action.AccountAction;
import com.xiaopeng.lib.framework.account.utils.L;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.AbsException;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo;
/* loaded from: classes.dex */
public class AccountImpl implements IAccount {
    private static volatile AccountImpl sAccount;
    private static IAccountProxy sInnerProxy;
    private final String TAG = "AccountImpl";
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static AccountImpl getInstance() {
        if (sAccount == null) {
            synchronized (AccountImpl.class) {
                if (sAccount == null) {
                    sAccount = new AccountImpl();
                    sInnerProxy = AccountProxyFactory.getInstance().getProxy();
                }
            }
        }
        return sAccount;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount
    public void init(Application application, String appId) {
        init(application, appId, null);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount
    public void init(Application application, String appId, String packageName) {
        L.v("AccountImpl", "init");
        sInnerProxy.init(application, appId, packageName);
    }

    private void release(Context context) {
        L.v("AccountImpl", "release");
        sInnerProxy.release(context);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount
    public void requestOAuth(ICallback<IAuthInfo, IError> callback) {
        L.v("AccountImpl", "requestOAuth");
        sInnerProxy.requestOAuth(callback);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount
    public void requestOAuth(String appID, ICallback<IAuthInfo, IError> iCallback) {
        L.v("AccountImpl", "requestOAuth appID:" + appID);
        sInnerProxy.requestOAuth(appID, iCallback);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount
    public void requestOTP(String deviceID, ICallback<IOTPInfo, IError> callback) {
        L.v("AccountImpl", "requestOTP deviceID:" + deviceID);
        sInnerProxy.requestOTP(deviceID, callback);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount
    public IUserInfo getUserInfo() throws AbsException {
        L.v("AccountImpl", "getUserInfo");
        return sInnerProxy.getUserInfo();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount
    public void login() throws AbsException {
        L.v("AccountImpl", "login");
        sInnerProxy.sendAction(AccountAction.QR_LOGIN);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount
    public void logout() throws AbsException {
        L.v("AccountImpl", "logout");
        sInnerProxy.sendAction(AccountAction.LOGOUT);
    }
}
