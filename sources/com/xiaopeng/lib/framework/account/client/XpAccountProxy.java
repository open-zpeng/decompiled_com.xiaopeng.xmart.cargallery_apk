package com.xiaopeng.lib.framework.account.client;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.xiaopeng.lib.framework.account.IAccountProxy;
import com.xiaopeng.lib.framework.account.IUserInfoChangedListener;
import com.xiaopeng.lib.framework.account.IXpAccountService;
import com.xiaopeng.lib.framework.account.action.AccountAction;
import com.xiaopeng.lib.framework.account.exception.AccountConnectionException;
import com.xiaopeng.lib.framework.account.info.UserInfoEventImpl;
import com.xiaopeng.lib.framework.account.info.UserInfoImpl;
import com.xiaopeng.lib.framework.account.utils.L;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.AbsException;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes.dex */
public class XpAccountProxy implements IAccountProxy {
    private static final String ACTION_XP_ACCOUNT_SERVER_SERVICE = "com.xiaopeng.lib.framework.account.server";
    private static final long LOOP_DELAY_IN_SEC = 1;
    private static final int ONLY_ONE_THREAD = 1;
    private static final String REMOTE_PACKAGE_NAME = "com.xiaopeng.caraccount";
    private static final String TAG = "XpAccountProxy";
    private static volatile XpAccountProxy mXpAccountProxy;
    private String mAppId;
    private Application mApplication;
    private ScheduledFuture<?> mBindingTask;
    private volatile IXpAccountService mXpAccountService;
    private String remotePackageName;
    private CopyOnWriteArrayList mCopyOnWriteArrayList = new CopyOnWriteArrayList();
    private ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.lib.framework.account.client.XpAccountProxy.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            L.i(XpAccountProxy.TAG, "onServiceConnected name=" + name.getPackageName());
            XpAccountProxy.this.mXpAccountService = IXpAccountService.Stub.asInterface(service);
            if (XpAccountProxy.this.mXpAccountService != null) {
                try {
                    XpAccountProxy.this.mXpAccountService.init(SystemClock.currentThreadTimeMillis(), XpAccountProxy.this.mAppId);
                    XpAccountProxy.this.mXpAccountService.subscribe(SystemClock.currentThreadTimeMillis(), null, XpAccountProxy.this.mUserInfoChangedListener);
                    service.linkToDeath(XpAccountProxy.this.mDeathRecipient, 0);
                    XpAccountProxy.this.onServiceConnected();
                } catch (RemoteException e) {
                }
                XpAccountProxy.this.stopBindTask();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            L.i(XpAccountProxy.TAG, "onServiceDisconnected name=" + name.getPackageName());
            XpAccountProxy.this.onServiceDisconnected();
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.xiaopeng.lib.framework.account.client.XpAccountProxy.2
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            L.i(XpAccountProxy.TAG, "onServiceDeathRecipient binderDied");
            XpAccountProxy.this.onServiceDisconnected();
        }
    };
    IUserInfoChangedListener mUserInfoChangedListener = new IUserInfoChangedListener.Stub() { // from class: com.xiaopeng.lib.framework.account.client.XpAccountProxy.4
        @Override // com.xiaopeng.lib.framework.account.IUserInfoChangedListener
        public void notifyUserInfoChanged(UserInfoImpl userInfo) throws RemoteException {
            L.v(XpAccountProxy.TAG, "notifyUserInfoChanged");
            EventBus.getDefault().post(new UserInfoEventImpl().setUserInfo(userInfo));
        }
    };
    private IContextProvider mContextProvider = new IContextProvider() { // from class: com.xiaopeng.lib.framework.account.client.XpAccountProxy.5
        @Override // com.xiaopeng.lib.framework.account.client.IContextProvider
        public Application getApplication() {
            return XpAccountProxy.this.mApplication;
        }

        @Override // com.xiaopeng.lib.framework.account.client.IContextProvider
        public String getAppID() {
            return XpAccountProxy.this.mAppId;
        }

        @Override // com.xiaopeng.lib.framework.account.client.IContextProvider
        public IXpAccountService getService() {
            return XpAccountProxy.this.mXpAccountService;
        }
    };
    private ScheduledExecutorService mBindingExecutor = new ScheduledThreadPoolExecutor(1);

    XpAccountProxy() {
    }

    public static XpAccountProxy getInstance() {
        if (mXpAccountProxy == null) {
            synchronized (XpAccountProxy.class) {
                if (mXpAccountProxy == null) {
                    mXpAccountProxy = new XpAccountProxy();
                }
            }
        }
        return mXpAccountProxy;
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void init(Application application, String appId, String packageName) {
        L.v(TAG, "init mAppId=" + appId + ";packageName=" + packageName);
        this.mAppId = appId;
        this.mApplication = application;
        this.remotePackageName = packageName;
        if (TextUtils.isEmpty(packageName)) {
            this.remotePackageName = REMOTE_PACKAGE_NAME;
        }
        startBindTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindRemoteService() {
        if (this.mApplication == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(ACTION_XP_ACCOUNT_SERVER_SERVICE);
        intent.setPackage(this.remotePackageName);
        this.mApplication.bindService(intent, this.mServiceConnection, 1);
    }

    private void startBindTask() {
        stopBindTask();
        if (this.mXpAccountService != null) {
            return;
        }
        if (this.mBindingExecutor == null) {
            this.mBindingExecutor = new ScheduledThreadPoolExecutor(1);
        }
        L.d(TAG, "startBindTask");
        this.mBindingTask = this.mBindingExecutor.scheduleWithFixedDelay(new Runnable() { // from class: com.xiaopeng.lib.framework.account.client.XpAccountProxy.3
            @Override // java.lang.Runnable
            public void run() {
                XpAccountProxy.this.bindRemoteService();
            }
        }, 0L, 1L, TimeUnit.SECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopBindTask() {
        ScheduledFuture<?> scheduledFuture = this.mBindingTask;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.mBindingTask = null;
        }
        ScheduledExecutorService scheduledExecutorService = this.mBindingExecutor;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            this.mBindingExecutor = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceConnected() {
        try {
            Log.v(TAG, "onServiceConnected before getUserInfo");
            IUserInfo info = getUserInfo();
            if (info != null) {
                EventBus.getDefault().post(new UserInfoEventImpl().setUserInfo(info));
                Log.v(TAG, "onServiceConnected getUserInfo and notify, info:" + info);
            }
        } catch (AccountConnectionException e) {
            e.printStackTrace();
            L.w(TAG, "onServiceConnected getUserInfo exception:" + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onServiceDisconnected() {
        L.i(TAG, "onServiceDisconnected mXpAccountService:" + this.mXpAccountService);
        if (this.mXpAccountService == null) {
            return;
        }
        this.mXpAccountService.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
        this.mXpAccountService = null;
        startBindTask();
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void release(Context context) {
        L.v(TAG, "disconnectService");
        context.unbindService(this.mServiceConnection);
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public IUserInfo getUserInfo() throws AccountConnectionException {
        L.v(TAG, "getUserInfo");
        if (this.mXpAccountService == null) {
            L.v(TAG, "mXpAccountService is null.");
            throw new AccountConnectionException(1);
        }
        IUserInfo userInfo = null;
        try {
            userInfo = this.mXpAccountService.getUserInfo(0L);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        L.v(TAG, "mXpAccountService userInfo=" + userInfo);
        return userInfo;
    }

    private void requestOAuth(String appID, ICallback<IAuthInfo, IError> callback, long timeoutMillis) {
        L.v(TAG, "requestOAuth appID=" + appID + "; timeoutMillis=" + timeoutMillis);
        if (this.mXpAccountService == null) {
            L.e(TAG, "requestOAuth mXpAccountService is null.");
        }
        if (TextUtils.isEmpty(this.mAppId)) {
            L.e(TAG, "requestOAuth appID is empty.");
        } else {
            ThreadUtils.execute(new RequestOAuthTask(this.mApplication, appID, this.mXpAccountService, callback, timeoutMillis));
        }
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void requestOAuth(ICallback<IAuthInfo, IError> callback) {
        requestOAuth(this.mAppId, callback, 0L);
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void requestOAuth(String appID, ICallback<IAuthInfo, IError> callback) {
        requestOAuth(appID, callback, 0L);
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void requestOTP(String deviceID, ICallback<IOTPInfo, IError> callback) {
        if (this.mXpAccountService == null) {
            L.w(TAG, "requestOTP mXpAccountService is null.");
        }
        ThreadUtils.execute(new RequestOTPTask(this.mContextProvider, deviceID, callback));
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void sendAction(AccountAction action) throws AbsException {
        L.v(TAG, "sendAction action=" + action.ordinal());
        if (this.mXpAccountService == null) {
            L.v(TAG, "mXpAccountService is null.");
            throw new AccountConnectionException(1);
        }
        try {
            this.mXpAccountService.requestAction(0L, action.ordinal(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
