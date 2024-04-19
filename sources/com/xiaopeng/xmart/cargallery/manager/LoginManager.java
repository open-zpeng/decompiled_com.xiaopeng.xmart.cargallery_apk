package com.xiaopeng.xmart.cargallery.manager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
/* loaded from: classes9.dex */
public class LoginManager {
    public static final String ACCOUNT_TYPE_XP_VEHICLE = "com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE";
    public static final String ACCOUNT_USER_TYPE = "user_type";
    public static final String APP_ID = "xp_car_camera_biz";
    public static final String AUTH_INFO_EXTRA_APP_ID = "app_id";
    public static final String AUTH_INFO_EXTRA_APP_SECRET = "app_secret";
    public static final String AUTH_TYPE_AUTH_CODE = "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE";
    public static final String AUTH_TYPE_AUTH_OTP = "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_OTP";
    private static final String CARACCOUNT_NAME = "com.xiaopeng.caraccount";
    private static final String DIALOG_QR_NAME = "com.xiaopeng.xvs.account.ACTION_ACCOUNT_DIALOG_QR_REQUEST";
    private static LoginManager INSTANCE = null;
    private static final String TAG = "LoginManager";
    public static final String USER_DATA_EXTRA_AVATAR = "avatar";
    public static final String USER_DATA_EXTRA_UID = "uid";
    public static final String USER_DATA_EXTRA_UPDATE = "update";
    private static final int USER_TYPE_DRIVER = 4;
    private static final int USER_TYPE_TENANT = 3;
    private static final int USER_TYPE_TOWNER = 1;
    private static final int USER_TYPE_USER = 2;
    private AccountManager mAccountManager;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginManager();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.mAccountManager = AccountManager.get(context);
    }

    public AccountManager getmAccountManager() {
        return this.mAccountManager;
    }

    public void register(OnAccountsUpdateListener listener) {
        if (this.mAccountManager == null) {
            CameraLog.d(TAG, "mAccountManager = null, you should init first! ");
        } else if (Build.VERSION.SDK_INT >= 26) {
            this.mAccountManager.addOnAccountsUpdatedListener(listener, null, true, new String[]{ACCOUNT_TYPE_XP_VEHICLE});
        }
    }

    public void unregister(OnAccountsUpdateListener listener) {
        if (this.mAccountManager == null) {
            CameraLog.d(TAG, "mAccountManager = null, you should init first! ");
        } else if (Build.VERSION.SDK_INT >= 26) {
            this.mAccountManager.removeOnAccountsUpdatedListener(listener);
        }
    }

    private void login() {
        Intent intent = new Intent();
        intent.setPackage(CARACCOUNT_NAME);
        intent.setAction(DIALOG_QR_NAME);
        App.getInstance().startService(intent);
    }

    public Account getCurrentAccountInfo() {
        AccountManager accountManager = this.mAccountManager;
        if (accountManager == null) {
            CameraLog.d(TAG, "mAccountManager = null, you should init first! ");
            return null;
        }
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE_XP_VEHICLE);
        if (accounts.length > 0) {
            Account account = accounts[0];
            CameraLog.d(TAG, "getCurrentAccountInfo accounts.length=" + accounts.length + ";account[0].name=" + account.name, false);
            try {
                String avatar = this.mAccountManager.getUserData(account, USER_DATA_EXTRA_AVATAR);
                String update = this.mAccountManager.getUserData(account, "update");
                String uid = this.mAccountManager.getUserData(account, "uid");
                String type = this.mAccountManager.getUserData(account, ACCOUNT_USER_TYPE);
                CameraLog.d(TAG, "getCurrentAccountInfo name=" + account.name + ";头像=" + avatar + ";是否是更新=" + update + ";账号类型=" + type + ";uid=" + uid);
            } catch (Exception e) {
                CameraLog.d(TAG, "getCurrentAccountInfo Exception=" + e.getMessage(), false);
            }
            return account;
        }
        CameraLog.d(TAG, "getCurrentAccountInfo account is empty", false);
        return null;
    }

    public boolean isLogin() {
        if (getCurrentAccountInfo() == null) {
            login();
            return false;
        }
        return true;
    }
}
