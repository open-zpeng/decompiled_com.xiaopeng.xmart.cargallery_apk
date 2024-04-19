package com.xiaopeng.xmart.cargallery.utils;

import android.content.Context;
import com.xiaopeng.xui.app.XToast;
/* loaded from: classes3.dex */
public class ToastUtils {
    public static void showToast(Context context, int res) {
        XToast.show(res);
    }

    public static void showToast(Context context, String str) {
        XToast.show(str);
    }
}
