package com.xiaopeng.xmart.cargallery.utils;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.xmart.cargallery.CameraLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes3.dex */
public class GsonUtils {
    private static final String TAG = GsonUtils.class.getSimpleName();

    public static String convertVO2String(Object object) {
        try {
            Gson gson = new Gson();
            return gson.toJson(object);
        } catch (Exception e) {
            CameraLog.e(TAG, "convertVO2String \r\n  error = \r\n    ", false);
            return null;
        }
    }

    public static <T> T convertString2Object(String jsonStr, Class<T> targetClass) {
        try {
            Gson gson = new Gson();
            if (!isJson(jsonStr)) {
                return null;
            }
            return (T) gson.fromJson(jsonStr, (Class<Object>) targetClass);
        } catch (Exception e) {
            e.printStackTrace();
            CameraLog.e(TAG, "convertString2Object \r\n error = \r\n ", false);
            return null;
        }
    }

    public static <T> T convertString2Collection(String jsonStr, TypeToken<T> typeToken) {
        try {
            Gson gson = new Gson();
            if (!isJson(jsonStr)) {
                return null;
            }
            T t = (T) gson.fromJson(jsonStr, typeToken.getType());
            return t;
        } catch (Exception e) {
            CameraLog.e(TAG, "convertString2Collection \r\n  error = \r\n    ");
            return null;
        }
    }

    public static boolean isJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JSONObject(json);
            return true;
        } catch (JSONException e1) {
            e1.printStackTrace();
            CameraLog.e(TAG, "isJson \r\n  error = \r\n    " + json);
            return false;
        }
    }

    public static ArrayList<String> convertJson2Array(String str) {
        ArrayList<String> strs = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(str);
            for (int i = 0; i < arr.length(); i++) {
                strs.add(arr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strs;
    }
}
