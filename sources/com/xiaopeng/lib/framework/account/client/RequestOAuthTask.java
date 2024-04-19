package com.xiaopeng.lib.framework.account.client;

import android.app.Application;
import android.os.Bundle;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import com.lzy.okgo.cache.CacheEntity;
import com.xiaopeng.lib.framework.account.IXpAccountService;
import com.xiaopeng.lib.framework.account.IXpCallback;
import com.xiaopeng.lib.framework.account.authorized.AuthInfoImpl;
import com.xiaopeng.lib.framework.account.exception.AuthInfoError;
import com.xiaopeng.lib.framework.account.utils.AppLifeCycleHelper;
import com.xiaopeng.lib.framework.account.utils.L;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
/* loaded from: classes.dex */
public class RequestOAuthTask implements Runnable {
    private static final long REQ_OAUTH_MAX_TIMEOUT = 60000;
    private static final long REQ_OAUTH_TIMEOUT = 8000;
    private static final String TAG = "RequestOAuthTask";
    private String mAppId;
    private AppLifeCycleHelper mAppLifeCycleHelper;
    private Application mApplication;
    private RequestOAuthCallback mCallback;
    private long mTimeout;
    private IXpAccountService mXpAccountService;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RequestOAuthTask(Application application, String id, IXpAccountService service, ICallback<IAuthInfo, IError> cb, long timeoutMillis) {
        this.mCallback = new RequestOAuthCallback(cb);
        this.mXpAccountService = service;
        this.mAppId = id;
        AppLifeCycleHelper appLifeCycleHelper = new AppLifeCycleHelper(application);
        this.mAppLifeCycleHelper = appLifeCycleHelper;
        this.mTimeout = timeoutMillis;
        if (timeoutMillis <= 0 || timeoutMillis > 60000) {
            this.mTimeout = REQ_OAUTH_TIMEOUT;
        }
        this.mApplication = application;
        appLifeCycleHelper.setOnAppTopListener(new AppLifeCycleHelper.OnAppTopListener() { // from class: com.xiaopeng.lib.framework.account.client.RequestOAuthTask.1
            @Override // com.xiaopeng.lib.framework.account.utils.AppLifeCycleHelper.OnAppTopListener
            public void onAppTop() {
                L.v(RequestOAuthTask.TAG, "onAppCycleChanged.errCode=10004");
                RequestOAuthTask.this.mCallback.dispatchMessage(AuthInfoError.ERROR_CODE_AUTH_TIMEOUT, RequestOAuthTask.this.mTimeout);
                RequestOAuthTask.this.mAppLifeCycleHelper.unregisterCallbacks(RequestOAuthTask.this.mApplication);
            }
        });
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.mXpAccountService == null) {
                L.v(TAG, "mXpAccountService is null.errCode=1");
                this.mCallback.dispatchMessage(1, 0L);
                return;
            }
            long timestamp = System.currentTimeMillis();
            L.v(TAG, "requestOAuth timestamp=" + timestamp + ";mAppId=" + this.mAppId);
            this.mXpAccountService.requestOAuth(timestamp, this.mAppId, new IXpCallback.Stub() { // from class: com.xiaopeng.lib.framework.account.client.RequestOAuthTask.2
                @Override // com.xiaopeng.lib.framework.account.IXpCallback
                public void onSuccess(long uuid, String pAction, Bundle pBundle) throws RemoteException {
                    if (pBundle == null) {
                        onFail(uuid, pAction, null);
                        return;
                    }
                    pBundle.setClassLoader(AuthInfoImpl.class.getClassLoader());
                    AuthInfoImpl authInfo = (AuthInfoImpl) pBundle.getParcelable(CacheEntity.DATA);
                    if (authInfo == null) {
                        onFail(uuid, pAction, pBundle);
                        return;
                    }
                    L.v(RequestOAuthTask.TAG, "onSuccess.authInfo=" + authInfo.toString());
                    RequestOAuthTask.this.mCallback.dispatchMessage(authInfo, 0L);
                    RequestOAuthTask.this.onAuthorizeFinished();
                }

                @Override // com.xiaopeng.lib.framework.account.IXpCallback
                public void onFail(long uuid, String pAction, Bundle pBundle) throws RemoteException {
                    int errCode = AuthInfoError.ERROR_CODE_AUTH_REQUEST_FAIL;
                    String err = null;
                    if (pBundle != null) {
                        pBundle.setClassLoader(AuthInfoImpl.class.getClassLoader());
                        errCode = pBundle.getInt("errCode", AuthInfoError.ERROR_CODE_AUTH_REQUEST_FAIL);
                        err = pBundle.getString(NotificationCompat.CATEGORY_ERROR);
                    }
                    L.v(RequestOAuthTask.TAG, "onFail errCode=" + errCode + "; err=" + err);
                    RequestOAuthTask.this.mCallback.dispatchMessage(errCode, err, 0L);
                    RequestOAuthTask.this.onAuthorizeFinished();
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            this.mCallback.dispatchMessage(1, 0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAuthorizeFinished() {
        this.mAppLifeCycleHelper.setOnAppTopListener(null);
        this.mAppLifeCycleHelper.unregisterCallbacks(this.mApplication);
    }
}
