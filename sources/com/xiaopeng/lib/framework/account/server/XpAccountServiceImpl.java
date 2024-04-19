package com.xiaopeng.lib.framework.account.server;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.xiaopeng.lib.framework.account.IUserInfoChangedListener;
import com.xiaopeng.lib.framework.account.IXpAccountService;
import com.xiaopeng.lib.framework.account.IXpCallback;
import com.xiaopeng.lib.framework.account.action.AccountAction;
import com.xiaopeng.lib.framework.account.info.UserInfoImpl;
import com.xiaopeng.lib.framework.account.utils.L;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes.dex */
public class XpAccountServiceImpl extends Service {
    private static final String TAG = "XpAccountServiceImpl";
    private static volatile XpAccountServiceBinder mCarAccountService;

    @Override // android.app.Service
    public void onCreate() {
        Log.v(TAG, "onCreate");
        super.onCreate();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind");
        return XpAccountServiceBinder.access$000();
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return 1;
    }

    /* loaded from: classes.dex */
    private static class XpAccountServiceBinder extends IXpAccountService.Stub {
        private IAccountProvider mAccountProvider;
        private final CallbackList mCallbacks = new CallbackList();

        static /* synthetic */ XpAccountServiceBinder access$000() {
            return getCarAccountService();
        }

        private XpAccountServiceBinder() {
        }

        public void initProvider(IAccountProvider provider) {
            this.mAccountProvider = provider;
        }

        public void postMessage(IUserInfo userInfo) {
            this.mCallbacks.notifyUserInfoChanged((UserInfoImpl) userInfo);
        }

        @Override // com.xiaopeng.lib.framework.account.IXpAccountService
        public void init(long timestamp, String id) throws RemoteException {
            Log.v(XpAccountServiceImpl.TAG, "init");
        }

        @Override // com.xiaopeng.lib.framework.account.IXpAccountService
        public UserInfoImpl getUserInfo(long timestamp) throws RemoteException {
            Log.v(XpAccountServiceImpl.TAG, "getUserInfo");
            return (UserInfoImpl) this.mAccountProvider.userInfo();
        }

        @Override // com.xiaopeng.lib.framework.account.IXpAccountService
        public void requestOAuth(long timestamp, String appId, IXpCallback callback) throws RemoteException {
            Log.v(XpAccountServiceImpl.TAG, "requestOAuth timestamp=" + timestamp);
            this.mAccountProvider.authorize(timestamp, appId, callback);
        }

        @Override // com.xiaopeng.lib.framework.account.IXpAccountService
        public void requestOTP(long timestamp, String appId, String deviceID, IXpCallback callback) throws RemoteException {
            Log.v(XpAccountServiceImpl.TAG, "requestOTP timestamp=" + timestamp + "; deviceID=" + deviceID);
            this.mAccountProvider.requestOTP(timestamp, appId, deviceID, callback);
        }

        @Override // com.xiaopeng.lib.framework.account.IXpAccountService
        public void requestAction(long timestamp, int pAction, Bundle pBundle) throws RemoteException {
            AccountAction action;
            L.v(XpAccountServiceImpl.TAG, "sendMessage timestamp=" + timestamp + "pAction=" + pAction);
            if (pAction >= 0 && pAction < AccountAction.values().length) {
                action = AccountAction.values()[pAction];
            } else {
                L.v(XpAccountServiceImpl.TAG, "sendMessage action is ArrayIndexOutBoundsException");
                action = AccountAction.NONE;
            }
            this.mAccountProvider.action(timestamp, action, pBundle);
        }

        @Override // com.xiaopeng.lib.framework.account.IXpAccountService
        public void subscribe(long timestamp, Bundle pBundle, IUserInfoChangedListener listener) throws RemoteException {
            Log.v(XpAccountServiceImpl.TAG, "subscribe ");
            this.mCallbacks.register(listener);
        }

        @Override // com.xiaopeng.lib.framework.account.IXpAccountService
        public void unsubscribe(long timestamp, Bundle pBundle, IUserInfoChangedListener listener) throws RemoteException {
            Log.v(XpAccountServiceImpl.TAG, "unsubscribe ");
            this.mCallbacks.unregister(listener);
        }

        private static XpAccountServiceBinder getCarAccountService() {
            if (XpAccountServiceImpl.mCarAccountService == null) {
                synchronized (XpAccountServiceBinder.class) {
                    if (XpAccountServiceImpl.mCarAccountService == null) {
                        XpAccountServiceBinder unused = XpAccountServiceImpl.mCarAccountService = new XpAccountServiceBinder();
                    }
                }
            }
            return XpAccountServiceImpl.mCarAccountService;
        }
    }

    public static void initProvider(IAccountProvider provider) {
        XpAccountServiceBinder.access$000().initProvider(provider);
    }

    public static void sendMessage(IUserInfo userInfo) {
        XpAccountServiceBinder.access$000().postMessage(userInfo);
    }

    @Override // android.app.Service
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
