package com.ut.mini.core.appstatus;

import android.app.Application;
/* loaded from: classes.dex */
public class UTMCAppStatusRegHelper {
    public static void registerAppStatusCallbacks(UTMCAppStatusCallbacks aCallbacks) {
        if (aCallbacks != null) {
            UTMCAppStatusMonitor.getInstance().registerAppStatusCallbacks(aCallbacks);
        }
    }

    public static void unRegisterAppStatusCallbacks(UTMCAppStatusCallbacks aCallbacks) {
        if (aCallbacks != null) {
            UTMCAppStatusMonitor.getInstance().unregisterAppStatusCallbacks(aCallbacks);
        }
    }

    public static void registeActivityLifecycleCallbacks(Application aApplicationInstance) {
        if (aApplicationInstance != null) {
            aApplicationInstance.registerActivityLifecycleCallbacks(UTMCAppStatusMonitor.getInstance());
        }
    }

    public static void unregisterActivityLifecycleCallbacks(Application aApplicationInstance) {
        if (aApplicationInstance != null) {
            aApplicationInstance.unregisterActivityLifecycleCallbacks(UTMCAppStatusMonitor.getInstance());
        }
    }
}
