package com.xiaopeng.lib.framework.account.utils;

import android.util.Log;
/* loaded from: classes.dex */
public class L {
    private static boolean enable = true;

    public static boolean isLoggable() {
        return enable;
    }

    public static void setLoggable(boolean loggable) {
        enable = loggable;
    }

    public static void v(String tag, String msg) {
        if (enable) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (enable) {
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (enable) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (enable) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (enable) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (enable) {
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (enable) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (enable) {
            Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (enable) {
            Log.w(tag, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (enable) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (enable) {
            Log.e(tag, msg, tr);
        }
    }
}
