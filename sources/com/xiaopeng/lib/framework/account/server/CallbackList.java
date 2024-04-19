package com.xiaopeng.lib.framework.account.server;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.xiaopeng.lib.framework.account.IUserInfoChangedListener;
import com.xiaopeng.lib.framework.account.info.UserInfoImpl;
/* loaded from: classes.dex */
public final class CallbackList {
    private static final int CATEGORY_COUNT = 1;
    private static final String TAG = "CallbackList";
    private Object[] mCallbacks = new Object[1];

    public CallbackList() {
        for (int i = 0; i < 1; i++) {
            this.mCallbacks[i] = new RemoteCallbackList();
        }
    }

    public void register(IUserInfoChangedListener callback) {
        ((RemoteCallbackList) this.mCallbacks[0]).register(callback);
    }

    public void notifyUserInfoChanged(UserInfoImpl info) {
        RemoteCallbackList<IUserInfoChangedListener> callbacks = (RemoteCallbackList) this.mCallbacks[0];
        int count = callbacks.beginBroadcast();
        Log.v(TAG, "notifyUserInfoChanged count=" + count);
        for (int i = 0; i < count; i++) {
            try {
                callbacks.getBroadcastItem(i).notifyUserInfoChanged(info);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        callbacks.finishBroadcast();
    }

    public void unregister(IUserInfoChangedListener callback) {
        ((RemoteCallbackList) this.mCallbacks[0]).unregister(callback);
    }
}
