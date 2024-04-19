package com.xiaopeng.xmart.cargallery.bean;
/* loaded from: classes9.dex */
public class FooterItemFactory {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static BaseItem createFooterItem(String carType) {
        char c;
        switch (carType.hashCode()) {
            case 2560:
                if (carType.equals("Q1")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2561:
                if (carType.equals("Q2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2562:
                if (carType.equals("Q3")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2563:
            case 2566:
            default:
                c = 65535;
                break;
            case 2564:
                if (carType.equals("Q5")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2565:
                if (carType.equals("Q6")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2567:
                if (carType.equals("Q8")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
                return new FooterItem();
            case 4:
            case 5:
                return null;
            default:
                return null;
        }
    }
}
