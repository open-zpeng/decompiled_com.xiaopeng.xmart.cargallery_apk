package com.xiaopeng.xmart.cargallery.helper;

import android.car.Car;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.xmart.cargallery.CameraLog;
/* loaded from: classes12.dex */
public class CarTypeHelper {
    public static final String SYS_CONFIG_DVR_DEBUG = "persist.sys.xiaopeng.DVR.feature";
    private static final String TAG = "CarTypeHelper";
    private static String mCarType;

    public static String getXpCduType() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            CameraLog.d(TAG, "getXpCduType exception:" + e.getMessage(), false);
        }
        if (!TextUtils.isEmpty(mCarType)) {
            return mCarType;
        }
        mCarType = Car.getXpCduType();
        CameraLog.d(TAG, "carType:" + mCarType, false);
        return mCarType;
    }

    public static boolean isXMartQ5() {
        return SystemProperties.getBoolean(SYS_CONFIG_DVR_DEBUG, false) || "Q5".equals(Car.getXpCduType());
    }
}
