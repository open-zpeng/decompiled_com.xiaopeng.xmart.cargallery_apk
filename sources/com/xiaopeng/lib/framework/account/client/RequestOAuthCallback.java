package com.xiaopeng.lib.framework.account.client;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaopeng.lib.framework.account.exception.AuthInfoError;
import com.xiaopeng.lib.framework.account.utils.L;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
/* loaded from: classes.dex */
public class RequestOAuthCallback implements Handler.Callback {
    private static final String TAG = "RequestOAuthCallback";
    private boolean isCallBackDone;
    private IAuthInfo mAuthInfo;
    private ICallback<IAuthInfo, IError> mCallback;
    private Handler mHandler = new Handler(Looper.getMainLooper(), this);

    /* JADX INFO: Access modifiers changed from: package-private */
    public RequestOAuthCallback(ICallback<IAuthInfo, IError> cb) {
        this.mCallback = cb;
    }

    public void dispatchMessage(IAuthInfo info, long delayMillis) {
        this.mAuthInfo = info;
        this.mHandler.sendEmptyMessageDelayed(1, delayMillis);
    }

    public void dispatchMessage(int errorCode, long delayMillis) {
        dispatchMessage(errorCode, null, delayMillis);
    }

    public void dispatchMessage(int errorCode, String err, long delayMillis) {
        Message msg = this.mHandler.obtainMessage();
        msg.what = 2;
        msg.arg1 = errorCode;
        msg.obj = err;
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.sendMessageDelayed(msg, delayMillis);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        ICallback<IAuthInfo, IError> iCallback;
        L.v(TAG, "handleMessage msg=" + message);
        switch (message.what) {
            case 1:
                if (!this.isCallBackDone && (iCallback = this.mCallback) != null) {
                    iCallback.onSuccess(this.mAuthInfo);
                    this.isCallBackDone = true;
                    break;
                }
                break;
            case 2:
                if (!this.isCallBackDone && this.mCallback != null) {
                    AuthInfoError error = new AuthInfoError(message.arg1);
                    if (message.obj instanceof String) {
                        error.setErr((String) message.obj);
                    }
                    this.mCallback.onFail(error);
                    this.isCallBackDone = true;
                    break;
                }
                break;
        }
        return true;
    }
}
