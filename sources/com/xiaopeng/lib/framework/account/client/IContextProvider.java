package com.xiaopeng.lib.framework.account.client;

import android.app.Application;
import com.xiaopeng.lib.framework.account.IXpAccountService;
/* loaded from: classes.dex */
interface IContextProvider {
    String getAppID();

    Application getApplication();

    IXpAccountService getService();
}
