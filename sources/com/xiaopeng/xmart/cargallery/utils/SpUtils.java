package com.xiaopeng.xmart.cargallery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
/* loaded from: classes3.dex */
public class SpUtils {
    public static final String FILE_NAME = "share_data";
    public static final String SP_KEY_WIFI_SSID = "sp_key_wifi_ssid";

    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, ((Integer) object).intValue());
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) object).booleanValue());
        } else if (object instanceof Float) {
            editor.putFloat(key, ((Float) object).floatValue());
        } else if (object instanceof Long) {
            editor.putLong(key, ((Long) object).longValue());
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        }
        if (defaultObject instanceof Integer) {
            return Integer.valueOf(sp.getInt(key, ((Integer) defaultObject).intValue()));
        }
        if (defaultObject instanceof Boolean) {
            return Boolean.valueOf(sp.getBoolean(key, ((Boolean) defaultObject).booleanValue()));
        }
        if (defaultObject instanceof Float) {
            return Float.valueOf(sp.getFloat(key, ((Float) defaultObject).floatValue()));
        }
        if (defaultObject instanceof Long) {
            return Long.valueOf(sp.getLong(key, ((Long) defaultObject).longValue()));
        }
        return null;
    }

    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 0);
        return sp.getAll();
    }

    /* loaded from: classes3.dex */
    private static class SharedPreferencesCompat {
        private static final Method APPLY_METHOD = findApplyMethod();

        private SharedPreferencesCompat() {
        }

        private static Method findApplyMethod() {
            try {
                return SharedPreferences.Editor.class.getMethod("apply", new Class[0]);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                Method method = APPLY_METHOD;
                if (method != null) {
                    method.invoke(editor, new Object[0]);
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
            editor.commit();
        }
    }
}
