package com.xiaopeng.xmart.cargallery.unity.app;

import android.app.Activity;
import android.app.Application;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.unity.UnityApp;
import com.xiaopeng.xmart.cargallery.unity.UnityBaseManager;
/* loaded from: classes11.dex */
public class GalleryAppManager extends UnityBaseManager {
    private static final String TAG = GalleryAppManager.class.getSimpleName();
    private static UnityApp unityApp;

    /* loaded from: classes11.dex */
    public static class SingleInstance {
        public static GalleryAppManager instance = new GalleryAppManager();
    }

    public static GalleryAppManager get_instance() {
        return SingleInstance.instance;
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void init() {
        String str = TAG;
        CameraLog.i(str, "init: ", false);
        Activity activity = getUnityActivity();
        if (activity != null) {
            init(activity.getApplication());
        } else {
            CameraLog.i(str, "init activity is null", false);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void init(Application application) {
        CameraLog.i(TAG, "init application:" + application, false);
        if (application != null && unityApp == null) {
            UnityApp unityApp2 = new UnityApp();
            unityApp = unityApp2;
            unityApp2.attachBaseContext(application);
            unityApp.init();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void enterScene() {
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void exitScene() {
    }
}
