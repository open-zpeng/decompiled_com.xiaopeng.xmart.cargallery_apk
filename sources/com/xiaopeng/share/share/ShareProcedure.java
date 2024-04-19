package com.xiaopeng.share.share;

import android.app.Application;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.share.ComponentManager;
/* loaded from: classes.dex */
public class ShareProcedure {
    public static String APP_ID = null;
    public static String APP_SECRET = null;
    public static String BUCKET_NAME = null;
    private static final String TAG = "ShareProcedure";

    public static void init(Application application, String appid, String secret, String bucketName) {
        APP_ID = appid;
        APP_SECRET = secret;
        BUCKET_NAME = bucketName;
        ComponentManager.init(application);
        try {
            ComponentManager.getInstance().getAccount().init(application, appid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void share(ShareBuilder shareBuilder) {
        if (shareBuilder == null) {
            return;
        }
        ThreadUtils.execute(new ShareTask(shareBuilder));
    }
}
