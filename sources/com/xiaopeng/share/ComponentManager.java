package com.xiaopeng.share;

import android.accounts.AccountManager;
import android.app.Application;
import android.util.Log;
import com.xiaopeng.lib.framework.account.AccountModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.accountmodule.IAccount;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.IRemoteStorage;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.framework.netchannelmodule.common.TrafficeStaFlagInterceptor;
import com.xiaopeng.lib.http.HttpsUtils;
/* loaded from: classes.dex */
public class ComponentManager {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int DNS_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 3000;
    private static final String TAG = "ComponentManager";
    private static final int WRITE_TIMEOUT = 3000;
    private static AccountManager mAccountManager;
    private static boolean alreadyInit = false;
    private static ComponentManager INSTANCE = null;

    private ComponentManager() {
    }

    public static ComponentManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ComponentManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ComponentManager();
                }
            }
        }
        return INSTANCE;
    }

    public static void init(Application application) {
        Module.register(AccountModuleEntry.class, new AccountModuleEntry());
        Module.register(NetworkChannelsEntry.class, new NetworkChannelsEntry());
        registerHttp(application);
        HttpsUtils.init(application, true);
        IRemoteStorage storage = (IRemoteStorage) Module.get(NetworkChannelsEntry.class).get(IRemoteStorage.class);
        mAccountManager = AccountManager.get(application);
        try {
            storage.initWithContext(application);
        } catch (Exception e) {
            e.printStackTrace();
        }
        alreadyInit = true;
    }

    private static IHttp registerHttp(Application context) {
        IHttp http = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        http.config().connectTimeout(5000).addInterceptor(new TrafficeStaFlagInterceptor()).readTimeout(3000).writeTimeout(3000).dnsTimeout(5000).retryCount(1).applicationContext(context).enableTrafficStats().apply();
        return http;
    }

    public IHttp getHttp() {
        return (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
    }

    public IAccount getAccount() {
        IAccount account = (IAccount) Module.get(AccountModuleEntry.class).get(IAccount.class);
        if (account != null) {
            Log.e(TAG, "account not null");
            return account;
        }
        Log.e(TAG, "account is null");
        return null;
    }

    public IRemoteStorage getRemoteStorage() {
        IRemoteStorage storage = (IRemoteStorage) Module.get(NetworkChannelsEntry.class).get(IRemoteStorage.class);
        return storage;
    }

    public static AccountManager getAccountManager() {
        return mAccountManager;
    }
}
