package com.xiaopeng.speech.protocol.utils;

import android.os.SystemProperties;
import com.xiaopeng.speech.common.util.LogUtils;
/* loaded from: classes.dex */
public class CarTypeUtils {
    private static final String CAR_TYPE_D21EU = "D21EU";
    private static final String CAR_TYPE_D22EU = "D22EU";
    private static final String CAR_TYPE_D55EU = "D55EU";
    private static final String CAR_TYPE_D55ZH = "D55ZH";
    private static final String CAR_TYPE_E28EU = "E28EU";
    private static final String CAR_TYPE_E38EU = "E38EU";
    private static final String TAG = "CarTypeUtils";

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isOverseasCarType() {
        char c;
        String carType = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isOverseasCarType, carType: " + carType);
        switch (carType.hashCode()) {
            case 64338291:
                if (carType.equals(CAR_TYPE_D21EU)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 64339252:
                if (carType.equals(CAR_TYPE_D22EU)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 64431508:
                if (carType.equals(CAR_TYPE_D55EU)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 65268539:
                if (carType.equals(CAR_TYPE_E28EU)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 65298330:
                if (carType.equals(CAR_TYPE_E38EU)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    public static boolean isD21EU() {
        String carType = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isD21EU, carType: " + carType);
        return CAR_TYPE_D21EU.equalsIgnoreCase(carType);
    }

    public static boolean isD22EU() {
        String carType = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isD22EU, carType: " + carType);
        return CAR_TYPE_D22EU.equalsIgnoreCase(carType);
    }

    public static boolean isD55EU() {
        String carType = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isD55EU, carType: " + carType);
        return CAR_TYPE_D55EU.equalsIgnoreCase(carType);
    }

    public static boolean isE28EU() {
        String carType = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isE28V, carType: " + carType);
        return CAR_TYPE_E28EU.equalsIgnoreCase(carType);
    }

    public static boolean isE38EU() {
        String carType = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isE38V, carType: " + carType);
        return CAR_TYPE_E38EU.equalsIgnoreCase(carType);
    }

    public static boolean isD55ZH() {
        String carType = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isD55ZH, carType: " + carType);
        return CAR_TYPE_D55ZH.equalsIgnoreCase(carType);
    }

    private static String getVersionInCountryCode() {
        String versionFinger = SystemProperties.get("ro.xiaopeng.software", "");
        if (!"".equals(versionFinger) && versionFinger != null) {
            String countryCode = versionFinger.substring(7, 9);
            return countryCode;
        }
        return versionFinger;
    }

    private static String getHardwareCarType() {
        String versionFinger = SystemProperties.get("ro.xiaopeng.software", "");
        return ("".equals(versionFinger) || versionFinger == null) ? versionFinger : versionFinger.substring(9, 12);
    }
}
