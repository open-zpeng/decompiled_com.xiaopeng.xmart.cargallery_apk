package com.xiaopeng.xmart.cargallery;

import android.app.Application;
import com.xiaopeng.lib.bughunter.StartPerfUtils;
import com.xiaopeng.lib.utils.config.CommonConfig;
import com.xiaopeng.share.share.ShareProcedure;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.LoginManager;
import com.xiaopeng.xmart.cargallery.model.GalleryDataLoader;
import com.xiaopeng.xmart.cargallery.utils.DeviceUtils;
import com.xiaopeng.xui.Xui;
/* loaded from: classes13.dex */
public class App extends Application {
    private static final String TAG = "GalleryApp";
    protected static App sInstance;
    private final String mAppId = LoginManager.APP_ID;
    private final String mSecretDebug = "8akHD02indsn2slC";
    private final String mSecretRelease = "jhgdw9mdhHbdv08w";
    private final String mBucketName = "bjmarket";

    public static App getInstance() {
        return sInstance;
    }

    @Override // android.app.Application
    public void onCreate() {
        CameraLog.d(TAG, "onCreate", false);
        super.onCreate();
        init();
    }

    public void init() {
        StartPerfUtils.appOnCreateBegin();
        super.onCreate();
        sInstance = this;
        GalleryDataLoader.getInstance().setContext(this);
        DeviceUtils.init(this);
        CarClientWrapper.getInstance().connectToCar();
        Xui.init(this);
        Xui.setFontScaleDynamicChangeEnable(true);
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.-$$Lambda$App$TKDKBJzKb3zG4gbjp4rvMcSbbTk
            @Override // java.lang.Runnable
            public final void run() {
                App.this.lambda$init$0$App();
            }
        });
        SpeechClient.instance().init(this);
        SpeechClient.instance().setAppName(getInstance().getString(R.string.app_name));
        ShareProcedure.init(this, LoginManager.APP_ID, getAppSecret(), "bjmarket");
        LoginManager.getInstance().init(this);
        StartPerfUtils.appOnCreateEnd();
    }

    public /* synthetic */ void lambda$init$0$App() {
        BIHelper.getInstance().init(this);
    }

    private String getAppSecret() {
        if (CommonConfig.HTTP_HOST.startsWith("https://xmart.xiaopeng.com")) {
            return "jhgdw9mdhHbdv08w";
        }
        return "8akHD02indsn2slC";
    }
}
