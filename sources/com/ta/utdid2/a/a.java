package com.ta.utdid2.a;

import android.content.Context;
import android.util.Log;
import com.ta.utdid2.b.a.f;
import com.ta.utdid2.b.a.i;
import com.ta.utdid2.b.a.j;
/* compiled from: AidManager.java */
/* loaded from: classes.dex */
public class a {
    private Context mContext;
    private static a a = null;
    private static final String TAG = a.class.getName();

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (a == null) {
                a = new a(context);
            }
            aVar = a;
        }
        return aVar;
    }

    private a(Context context) {
        this.mContext = context;
    }

    public void a(String str, String str2, String str3, com.ut.device.a aVar) {
        if (aVar == null) {
            Log.e(TAG, "callback is null!");
        } else if (this.mContext == null || i.m94a(str) || i.m94a(str2)) {
            Log.e(TAG, "mContext:" + this.mContext + "; callback:" + aVar + "; has appName:" + (!i.m94a(str)) + "; has token:" + (!i.m94a(str2)));
            aVar.a(1002, "");
        } else {
            String m91a = c.m91a(this.mContext, str, str2);
            if (!i.m94a(m91a) && j.a(c.a(this.mContext, str, str2), 1)) {
                aVar.a(1001, m91a);
            } else if (f.m93a(this.mContext)) {
                b.a(this.mContext).a(str, str2, str3, m91a, aVar);
            } else {
                aVar.a(1003, m91a);
            }
        }
    }

    public String a(String str, String str2, String str3) {
        if (this.mContext == null || i.m94a(str) || i.m94a(str2)) {
            Log.e(TAG, "mContext:" + this.mContext + "; has appName:" + (!i.m94a(str)) + "; has token:" + (!i.m94a(str2)));
            return "";
        }
        String m91a = c.m91a(this.mContext, str, str2);
        if (!i.m94a(m91a) && j.a(c.a(this.mContext, str, str2), 1)) {
            return m91a;
        }
        if (f.m93a(this.mContext)) {
            return b(str, str2, str3);
        }
        return m91a;
    }

    private synchronized String b(String str, String str2, String str3) {
        Context context = this.mContext;
        if (context == null) {
            Log.e(TAG, "no context!");
            return "";
        }
        String str4 = "";
        if (f.m93a(context)) {
            str4 = b.a(this.mContext).a(str, str2, str3, c.m91a(this.mContext, str, str2));
        }
        c.a(this.mContext, str, str4, str2);
        return str4;
    }
}
