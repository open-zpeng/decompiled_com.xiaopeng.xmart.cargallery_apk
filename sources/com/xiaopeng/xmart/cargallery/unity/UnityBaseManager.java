package com.xiaopeng.xmart.cargallery.unity;

import android.app.Activity;
import android.app.Application;
import com.xiaopeng.xmart.cargallery.CameraLog;
/* loaded from: classes8.dex */
public abstract class UnityBaseManager {
    private static final String TAG = UnityBaseManager.class.getSimpleName();

    public abstract void enterScene();

    public abstract void exitScene();

    public abstract void init();

    public abstract void init(Application app);

    public static Activity getUnityActivity() {
        try {
            Class<?> classType = Class.forName("com.unity3d.player.UnityPlayer");
            return (Activity) classType.getDeclaredField("currentActivity").get(classType);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException ex) {
            CameraLog.e(TAG, "getActivity ex:" + ex, false);
            ex.printStackTrace();
            return null;
        }
    }
}
