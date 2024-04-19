package com.xiaopeng.xmart.cargallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.io.File;
/* loaded from: classes21.dex */
public class CleanCacheReceiver extends BroadcastReceiver {
    private static final String CACHE_PATH = "/cache/image_manager_disk_cache";
    private static final String PACKAGE_DATA_PATH = "/data/data/";
    private static final String TAG = "CleanCacheReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String rootPath = PACKAGE_DATA_PATH + App.getInstance().getApplicationContext().getPackageName();
        LogUtils.d(TAG, "RootPath: " + rootPath);
        final File file_cache = new File(rootPath + CACHE_PATH);
        LogUtils.d(TAG, "FinalPath: " + file_cache.getPath());
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.CleanCacheReceiver.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (file_cache.exists()) {
                        File[] files = file_cache.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            LogUtils.d(CleanCacheReceiver.TAG, "Deleting: " + files[i].getName());
                            files[i].delete();
                        }
                    }
                } catch (Exception e) {
                    LogUtils.w(CleanCacheReceiver.TAG, e.getMessage());
                }
            }
        });
    }
}
