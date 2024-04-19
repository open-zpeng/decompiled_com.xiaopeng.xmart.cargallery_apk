package com.xiaopeng.lib.framework.account.client;

import android.os.Bundle;
import android.os.RemoteException;
import com.lzy.okgo.cache.CacheEntity;
import com.xiaopeng.lib.framework.account.IXpAccountService;
import com.xiaopeng.lib.framework.account.IXpCallback;
import com.xiaopeng.lib.framework.account.exception.OTPInfoError;
import com.xiaopeng.lib.framework.account.otp.OTPInfoImpl;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo;
import com.xiaopeng.lib.utils.LogUtils;
/* loaded from: classes.dex */
public class RequestOTPTask implements Runnable {
    private static final String TAG = "RequestOTPTask";
    private ICallback<IOTPInfo, IError> mCallback;
    private IContextProvider mContextProvider;
    private String mDeviceID;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RequestOTPTask(IContextProvider provider, String deviceID, ICallback<IOTPInfo, IError> callback) {
        this.mContextProvider = provider;
        this.mDeviceID = deviceID;
        this.mCallback = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        IXpAccountService service = this.mContextProvider.getService();
        if (service == null) {
            LogUtils.e(TAG, "RequestOTPTask run, IXpAccountService is null");
            notifyFailed(1000);
            return;
        }
        try {
            service.requestOTP(System.currentTimeMillis(), this.mContextProvider.getAppID(), this.mDeviceID, new IXpCallback.Stub() { // from class: com.xiaopeng.lib.framework.account.client.RequestOTPTask.1
                @Override // com.xiaopeng.lib.framework.account.IXpCallback
                public void onSuccess(long uuid, String pAction, Bundle pBundle) throws RemoteException {
                    if (pBundle == null) {
                        LogUtils.w(RequestOTPTask.TAG, "onSuccess but bundle is null");
                        RequestOTPTask.this.notifyFailed(1002);
                        return;
                    }
                    pBundle.setClassLoader(OTPInfoImpl.class.getClassLoader());
                    OTPInfoImpl otpInfo = (OTPInfoImpl) pBundle.getParcelable(CacheEntity.DATA);
                    if (otpInfo == null) {
                        LogUtils.w(RequestOTPTask.TAG, "onSuccess but otpInfo is null");
                        RequestOTPTask.this.notifyFailed(1002);
                        return;
                    }
                    LogUtils.d(RequestOTPTask.TAG, "onSuccess " + otpInfo);
                    RequestOTPTask.this.mCallback.onSuccess(otpInfo);
                }

                @Override // com.xiaopeng.lib.framework.account.IXpCallback
                public void onFail(long uuid, String pAction, Bundle pBundle) throws RemoteException {
                    int code = 1002;
                    if (pBundle != null) {
                        code = pBundle.getInt("code", 1002);
                    }
                    RequestOTPTask.this.notifyFailed(code);
                }
            });
        } catch (RemoteException e) {
            LogUtils.e(TAG, "RequestOTPTask run, exception:" + e);
            notifyFailed(1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFailed(int code) {
        this.mCallback.onFail(new OTPInfoError(code));
    }
}
