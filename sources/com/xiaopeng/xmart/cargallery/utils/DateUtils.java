package com.xiaopeng.xmart.cargallery.utils;

import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.R;
import java.text.SimpleDateFormat;
import java.util.Locale;
/* loaded from: classes3.dex */
public class DateUtils {
    private static SimpleDateFormat sSimpleFormat7 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat sSimpleFormat8 = null;
    private static SimpleDateFormat sSimpleFormat9 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.getDefault());

    public static synchronized String formatDate9(long date) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat9.format(Long.valueOf(date));
        }
        return format;
    }

    public static synchronized String formatDate8(long date) {
        String format;
        synchronized (DateUtils.class) {
            if (sSimpleFormat8 == null) {
                sSimpleFormat8 = new SimpleDateFormat(App.getInstance().getString(R.string.simple_format_8), Locale.getDefault());
            }
            format = sSimpleFormat8.format(Long.valueOf(date));
        }
        return format;
    }

    public static synchronized String formatDate7(long date) {
        String format;
        synchronized (DateUtils.class) {
            format = sSimpleFormat7.format(Long.valueOf(date));
        }
        return format;
    }

    public static String timeParse(long duration) {
        int second = (int) (duration / 1000);
        int hour = second / 3600;
        int minute = (second % 3600) / 60;
        int ss = second % 60;
        if (hour != 0) {
            String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(ss));
            return time;
        }
        String time2 = String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf(minute), Integer.valueOf(ss));
        return time2;
    }
}
