package com.xiaopeng.xui.utils;
/* loaded from: classes.dex */
public class XKeyEventUtils {
    private static final int KEYCODE_CAR_ENTER = 1015;

    public static boolean isEnter(int keyCode) {
        return keyCode == 1015 || keyCode == 66;
    }

    public static boolean isCancel(int keyCode) {
        return keyCode == 4 || keyCode == 111;
    }
}
