package com.xiaopeng.lib.security;

import android.os.Build;
import com.xiaopeng.lib.security.none.NoneSecurity;
import com.xiaopeng.lib.security.xmartv1.RandomKeySecurity;
/* loaded from: classes.dex */
public final class SecurityModuleFactory {
    public static ISecurityModule getSecurityModule() {
        switch (Build.VERSION.SDK_INT) {
            case 19:
                return NoneSecurity.getInstance();
            default:
                return RandomKeySecurity.getInstance();
        }
    }
}
