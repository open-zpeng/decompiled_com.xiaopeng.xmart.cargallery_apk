package com.xiaopeng.xmart.cargallery.utils;

import android.content.Context;
import com.xiaopeng.xmart.cargallery.helper.CarTypeHelper;
/* loaded from: classes3.dex */
public class DeviceUtils {
    private static boolean sInter = false;

    public static void init(Context context) {
        sInter = !context.getResources().getConfiguration().locale.getLanguage().equals("zh");
    }

    public static boolean isInter() {
        return sInter;
    }

    public static boolean isBtTransferEnable() {
        return CarTypeHelper.getXpCduType().equals("Q1") || CarTypeHelper.getXpCduType().equals("Q8") || CarTypeHelper.getXpCduType().equals("Q3") || CarTypeHelper.getXpCduType().equals("Q2") || CarTypeHelper.getXpCduType().equals("Q6");
    }
}
