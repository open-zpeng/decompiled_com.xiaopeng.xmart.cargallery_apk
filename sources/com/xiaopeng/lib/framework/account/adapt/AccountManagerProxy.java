package com.xiaopeng.lib.framework.account.adapt;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OnAccountsUpdateListener;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import com.xiaopeng.lib.framework.account.IAccountProxy;
import com.xiaopeng.lib.framework.account.action.AccountAction;
import com.xiaopeng.lib.framework.account.authorized.AuthInfoImpl;
import com.xiaopeng.lib.framework.account.exception.AuthInfoError;
import com.xiaopeng.lib.framework.account.exception.OTPInfoError;
import com.xiaopeng.lib.framework.account.info.UserInfoEventImpl;
import com.xiaopeng.lib.framework.account.info.UserInfoImpl;
import com.xiaopeng.lib.framework.account.otp.OTPInfoImpl;
import com.xiaopeng.lib.framework.account.utils.L;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.AbsException;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAuthInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.ICallback;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IError;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IOTPInfo;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IUserInfo;
import com.xiaopeng.xmart.cargallery.manager.LoginManager;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import org.greenrobot.eventbus.EventBus;
/* loaded from: classes.dex */
public class AccountManagerProxy implements IAccountProxy, OnAccountsUpdateListener {
    private static final String TAG = "AccountManagerProxy";
    private static AtomicBoolean sInitFlag = new AtomicBoolean(false);
    private volatile AccountManager mAccountManager;
    private String mAppId;
    private WeakReference<Application> mApplication;
    private CarAccountCallbackBroadcast mCarAccountCallbackBroadcast;
    private final byte[] mLock;
    private volatile RequestOAuthTask mPendingTask;
    private final byte[] mTaskLock;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Holder {
        private static final AccountManagerProxy INSTANCE = new AccountManagerProxy();

        private Holder() {
        }
    }

    private AccountManagerProxy() {
        this.mLock = new byte[0];
        this.mTaskLock = new byte[0];
    }

    public static AccountManagerProxy getInstance() {
        return Holder.INSTANCE;
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void init(Application app, String appId, String packageName) {
        if (!sInitFlag.getAndSet(true)) {
            L.i(TAG, "init appId:" + appId + "; packageName:" + packageName);
            synchronized (this.mLock) {
                this.mAccountManager = AccountManager.get(app);
            }
            this.mAccountManager.addOnAccountsUpdatedListener(this, null, true, new String[]{LoginManager.ACCOUNT_TYPE_XP_VEHICLE});
            this.mAppId = appId;
            this.mApplication = new WeakReference<>(app);
            return;
        }
        L.e(TAG, "init multi times, appId:" + appId);
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void release(Context context) {
        if (sInitFlag.get()) {
            L.i(TAG, "release account manager");
            this.mAccountManager.removeOnAccountsUpdatedListener(this);
            synchronized (this.mLock) {
                this.mAccountManager = null;
            }
            this.mAppId = null;
            this.mApplication.clear();
            this.mApplication = null;
            sInitFlag.set(false);
            return;
        }
        L.e(TAG, "Invoke release without properly initialized!");
    }

    @Override // android.accounts.OnAccountsUpdateListener
    public void onAccountsUpdated(Account[] accounts) {
        if (sInitFlag.get()) {
            if (accounts != null) {
                if (accounts.length > 0 && accounts[0] != null) {
                    try {
                        IUserInfo userInfo = fromAccount(accounts[0]);
                        L.i(TAG, "onAccountsUpdated userInfo:" + userInfo);
                        EventBus.getDefault().post(new UserInfoEventImpl().setUserInfo(userInfo));
                        if (IUserInfo.InfoType.CHANGED == userInfo.getInfoType()) {
                            synchronized (this.mTaskLock) {
                                if (this.mPendingTask != null) {
                                    this.mPendingTask.execute(accounts);
                                }
                            }
                            return;
                        }
                        return;
                    } catch (AccountException e) {
                        L.e(TAG, "onAccountsUpdate exception:" + e);
                        return;
                    }
                } else if (accounts.length == 0) {
                    IUserInfo userInfo2 = new UserInfoImpl();
                    userInfo2.setUserName("");
                    userInfo2.setUserType(IUserInfo.UserType.TEMP);
                    userInfo2.setInfoType(IUserInfo.InfoType.CHANGED);
                    EventBus.getDefault().post(new UserInfoEventImpl().setUserInfo(userInfo2));
                    return;
                } else {
                    return;
                }
            }
            return;
        }
        L.e(TAG, "Invoking onAccountsUpdated without properly initialized which should never happened");
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void requestOAuth(ICallback<IAuthInfo, IError> callback) {
        requestOAuth(this.mAppId, callback);
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void requestOAuth(String appId, ICallback<IAuthInfo, IError> callback) {
        if (sInitFlag.get()) {
            L.i(TAG, "requestOAuth appId:" + appId);
            Account account = getCurrentAccount();
            RequestOAuthTask task = new RequestOAuthTask(appId, callback);
            if (account == null) {
                L.i(TAG, "requestOAuth without account, login first.");
                task.postpone();
                return;
            }
            task.execute(account);
            return;
        }
        L.e(TAG, "Invoke requestOAuth without properly initialized!");
        if (callback != null) {
            callback.onFail(new AuthInfoError(1));
        }
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.xiaopeng.lib.framework.account.adapt.AccountManagerProxy$1] */
    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void requestOTP(String deviceId, final ICallback<IOTPInfo, IError> callback) {
        if (sInitFlag.get()) {
            L.i(TAG, "requestOTP deviceId:" + deviceId);
            new AsyncTask<Void, Void, Void>() { // from class: com.xiaopeng.lib.framework.account.adapt.AccountManagerProxy.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public Void doInBackground(Void... voids) {
                    Account account = AccountManagerProxy.this.getCurrentAccount();
                    if (account == null) {
                        L.e(AccountManagerProxy.TAG, "Current account is null; Always requestOTP after login");
                        callback.onFail(new OTPInfoError(1001));
                        return null;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(LoginManager.AUTH_INFO_EXTRA_APP_ID, AccountManagerProxy.this.mAppId);
                    synchronized (AccountManagerProxy.this.mLock) {
                        if (AccountManagerProxy.this.mAccountManager != null) {
                            AccountManagerFuture<Bundle> future = AccountManagerProxy.this.mAccountManager.getAuthToken(account, LoginManager.AUTH_TYPE_AUTH_OTP, bundle, false, (AccountManagerCallback<Bundle>) null, (Handler) null);
                            String uid = AccountManagerProxy.this.mAccountManager.getUserData(account, "uid");
                            try {
                                String token = future.getResult().getString("authtoken");
                                if (!TextUtils.isEmpty(token)) {
                                    OTPInfoImpl oTPInfoImpl = new OTPInfoImpl();
                                    oTPInfoImpl.setUid(Long.valueOf(uid).longValue());
                                    oTPInfoImpl.setOTP(token);
                                    callback.onSuccess(oTPInfoImpl);
                                } else {
                                    String errMsg = future.getResult().getString("errorMessage", "");
                                    String code = future.getResult().getString("errorCode", "-1");
                                    L.e(AccountManagerProxy.TAG, "requestOTP failed, code:" + code + "; msg:" + errMsg);
                                    callback.onFail(new OTPInfoError(Integer.valueOf(code).intValue()));
                                }
                            } catch (Exception e) {
                                L.e(AccountManagerProxy.TAG, "requestOTP exception:" + e);
                                callback.onFail(new OTPInfoError(0));
                            }
                            return null;
                        }
                        L.e(AccountManagerProxy.TAG, "requestOTP without accountManager");
                        callback.onFail(new OTPInfoError(1000));
                        return null;
                    }
                }
            }.execute(new Void[0]);
            return;
        }
        L.e(TAG, "Invoke requestOTP without properly initialized!");
        callback.onFail(new OTPInfoError(1000));
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public IUserInfo getUserInfo() throws AbsException {
        if (sInitFlag.get()) {
            Account account = getCurrentAccount();
            if (account != null) {
                return fromAccount(account);
            }
            return null;
        }
        L.e(TAG, "Invoke getUserInfo without properly initialized!");
        return null;
    }

    /* renamed from: com.xiaopeng.lib.framework.account.adapt.AccountManagerProxy$3  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$lib$framework$account$action$AccountAction;

        static {
            int[] iArr = new int[AccountAction.values().length];
            $SwitchMap$com$xiaopeng$lib$framework$account$action$AccountAction = iArr;
            try {
                iArr[AccountAction.QR_LOGIN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$account$action$AccountAction[AccountAction.LOGOUT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$framework$account$action$AccountAction[AccountAction.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @Override // com.xiaopeng.lib.framework.account.IAccountProxy
    public void sendAction(AccountAction action) throws AbsException {
        if (sInitFlag.get()) {
            switch (AnonymousClass3.$SwitchMap$com$xiaopeng$lib$framework$account$action$AccountAction[action.ordinal()]) {
                case 1:
                    doLogin();
                    return;
                case 2:
                default:
                    return;
            }
        }
        throw new AccountException("Invoke account action without properly initialized!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class RequestOAuthTask extends AsyncTask<Account, Void, Void> {
        private ICallback<IAuthInfo, IError> mCallback;
        private String mTargetAppId;
        private CountDownTimer mTimer;

        private RequestOAuthTask(String appId, ICallback<IAuthInfo, IError> callback) {
            this.mTargetAppId = appId;
            this.mCallback = callback;
            CountDownTimer countDownTimer = new CountDownTimer(40000L, 40000L) { // from class: com.xiaopeng.lib.framework.account.adapt.AccountManagerProxy.RequestOAuthTask.1
                @Override // android.os.CountDownTimer
                public void onTick(long millisUntilFinished) {
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    L.i(AccountManagerProxy.TAG, "RequestOAuthTask timeout triggered");
                    RequestOAuthTask.this.notifyFailed(AuthInfoError.ERROR_CODE_AUTH_TIMEOUT);
                }
            };
            this.mTimer = countDownTimer;
            countDownTimer.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void postpone() {
            synchronized (AccountManagerProxy.this.mTaskLock) {
                if (AccountManagerProxy.this.mPendingTask == null) {
                    AccountManagerProxy.this.mPendingTask = this;
                    AccountManagerProxy.this.doLogin();
                    return;
                }
                notifyFailed(3);
            }
        }

        private void dismiss() {
            synchronized (AccountManagerProxy.this.mTaskLock) {
                if (AccountManagerProxy.this.mPendingTask != null && AccountManagerProxy.this.mPendingTask == this) {
                    AccountManagerProxy.this.mPendingTask = null;
                }
            }
            CountDownTimer countDownTimer = this.mTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.mTimer = null;
            }
        }

        private void notifySuccess(String code) {
            AccountManagerProxy.this.unregisterCallbackBroadcast();
            if (this.mCallback != null) {
                AuthInfoImpl info = new AuthInfoImpl();
                info.setAuthCode(code);
                this.mCallback.onSuccess(info);
                this.mCallback = null;
                dismiss();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyFailed(int code) {
            AccountManagerProxy.this.unregisterCallbackBroadcast();
            ICallback<IAuthInfo, IError> iCallback = this.mCallback;
            if (iCallback != null) {
                iCallback.onFail(new AuthInfoError(code));
                this.mCallback = null;
                dismiss();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Account... accounts) {
            if (accounts.length < 1 || accounts[0] == null) {
                L.e(AccountManagerProxy.TAG, "RequestOAuthTask doRequest but account is null");
                notifyFailed(0);
                return null;
            }
            Bundle bundle = new Bundle();
            bundle.putString(LoginManager.AUTH_INFO_EXTRA_APP_ID, this.mTargetAppId);
            AccountManagerFuture<Bundle> future = AccountManagerProxy.this.mAccountManager.getAuthToken(accounts[0], LoginManager.AUTH_TYPE_AUTH_CODE, bundle, false, (AccountManagerCallback<Bundle>) null, (Handler) null);
            try {
                String token = future.getResult().getString("authtoken");
                if (!TextUtils.isEmpty(token)) {
                    notifySuccess(token);
                } else {
                    String errMsg = future.getResult().getString("errorMessage", "");
                    String code = future.getResult().getString("errorCode", "-1");
                    L.e(AccountManagerProxy.TAG, "RequestOAuthTask doRequest failed, code:" + code + "; msg:" + errMsg);
                    notifyFailed(Integer.valueOf(code).intValue());
                }
            } catch (Exception e) {
                L.e(AccountManagerProxy.TAG, "RequestOAuthTask doRequest exception:" + e);
                notifyFailed(0);
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIntent(Intent intent) {
        WeakReference<Application> weakReference = this.mApplication;
        if (weakReference != null && weakReference.get() != null) {
            this.mApplication.get().startActivity(intent);
            registerCallbackBroadcast();
            return;
        }
        L.e(TAG, "application is null, can't start activity");
    }

    private void registerCallbackBroadcast() {
        this.mCarAccountCallbackBroadcast = new CarAccountCallbackBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(CarAccountCallbackBroadcast.CANCEL_LOGIN_ACTION);
        this.mApplication.get().registerReceiver(this.mCarAccountCallbackBroadcast, filter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterCallbackBroadcast() {
        if (this.mCarAccountCallbackBroadcast != null) {
            this.mApplication.get().unregisterReceiver(this.mCarAccountCallbackBroadcast);
            this.mCarAccountCallbackBroadcast = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doLogin() {
        this.mAccountManager.addAccount(LoginManager.ACCOUNT_TYPE_XP_VEHICLE, null, null, null, null, new AccountManagerCallback<Bundle>() { // from class: com.xiaopeng.lib.framework.account.adapt.AccountManagerProxy.2
            @Override // android.accounts.AccountManagerCallback
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    Intent intent = (Intent) future.getResult().getParcelable("intent");
                    if (intent != null) {
                        AccountManagerProxy.this.handleIntent(intent);
                    } else {
                        L.e(AccountManagerProxy.TAG, "doLogin intent is null");
                    }
                } catch (Exception e) {
                    L.e(AccountManagerProxy.TAG, "doLogin exception:" + e);
                }
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Account getCurrentAccount() {
        if (sInitFlag.get()) {
            Account[] accounts = this.mAccountManager.getAccountsByType(LoginManager.ACCOUNT_TYPE_XP_VEHICLE);
            if (accounts.length > 0 && accounts[0] != null) {
                return accounts[0];
            }
            L.w(TAG, "no valid account, accounts.length=" + accounts.length);
            return null;
        }
        L.e(TAG, "Invoke getCurrentAccount without properly initialized!");
        return null;
    }

    private IUserInfo fromAccount(Account account) throws AccountException {
        if (sInitFlag.get()) {
            if (account == null) {
                throw new AccountException("account is null");
            }
            UserInfoImpl userInfo = new UserInfoImpl();
            userInfo.setUserName(account.name);
            userInfo.setAvatar(this.mAccountManager.getUserData(account, LoginManager.USER_DATA_EXTRA_AVATAR));
            String userTypeStr = this.mAccountManager.getUserData(account, LoginManager.ACCOUNT_USER_TYPE);
            try {
                int userType = Integer.valueOf(userTypeStr).intValue();
                userInfo.setUserType(getUserType(userType));
                String infoTypeStr = this.mAccountManager.getUserData(account, "update");
                try {
                    int infoType = Integer.valueOf(infoTypeStr).intValue();
                    userInfo.setInfoType(getInfoType(infoType));
                    return userInfo;
                } catch (NumberFormatException e) {
                    throw new AccountException("infoType convert exception, value=" + infoTypeStr);
                }
            } catch (NumberFormatException e2) {
                throw new AccountException("userType convert exception, value=" + userTypeStr);
            }
        }
        throw new AccountException("Invoke fromAccount without properly initialized!");
    }

    private IUserInfo.UserType getUserType(int type) {
        switch (type) {
            case 1:
                return IUserInfo.UserType.OWNER;
            case 2:
                return IUserInfo.UserType.USER;
            case 3:
                return IUserInfo.UserType.TENANT;
            case 4:
                return IUserInfo.UserType.DRIVER;
            default:
                return IUserInfo.UserType.TEMP;
        }
    }

    private IUserInfo.InfoType getInfoType(int type) {
        switch (type) {
            case 0:
                return IUserInfo.InfoType.CHANGED;
            case 1:
                return IUserInfo.InfoType.UPDATE;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class CarAccountCallbackBroadcast extends BroadcastReceiver {
        public static final String CANCEL_LOGIN_ACTION = "com.xiaopeng.xvs.account.ACTION_ACCOUNT_DIALOG_CANCEL_RESP";

        private CarAccountCallbackBroadcast() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (CANCEL_LOGIN_ACTION.equals(action) && AccountManagerProxy.this.mPendingTask != null) {
                AccountManagerProxy.this.mPendingTask.notifyFailed(20002);
                L.d(AccountManagerProxy.TAG, "onReceive send action : AuthInfoError.ERROR_CODE_USER_CLOSE_QRCODE");
            }
        }
    }
}
