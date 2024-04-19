package com.xiaopeng.lib.bughunter.utils;

import android.text.TextUtils;
import java.lang.reflect.Array;
/* loaded from: classes.dex */
public class SimUtils {
    public static double getSim(String src, String dest) {
        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(dest)) {
            return 0.0d;
        }
        return sim(src, dest);
    }

    private static int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            return three;
        }
        return min;
    }

    private static int ld(String str1, String str2) {
        int temp;
        int n = str1.length();
        int m = str2.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        int[][] d = (int[][]) Array.newInstance(int.class, n + 1, m + 1);
        for (int i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (int i2 = 1; i2 <= n; i2++) {
            char ch1 = str1.charAt(i2 - 1);
            for (int j2 = 1; j2 <= m; j2++) {
                char ch2 = str2.charAt(j2 - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                d[i2][j2] = min(d[i2 - 1][j2] + 1, d[i2][j2 - 1] + 1, d[i2 - 1][j2 - 1] + temp);
            }
        }
        return d[n][m];
    }

    private static double sim(String str1, String str2) {
        int ld = ld(str1, str2);
        return 1.0d - (ld / Math.max(str1.length(), str2.length()));
    }
}
