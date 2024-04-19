package com.xiaopeng.xmart.cargallery.unity.album;

import android.graphics.Bitmap;
import android.net.Uri;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes12.dex */
public class AlbumManager extends GalleryAppManager implements IUnityAlbumView {
    private static final int DISK_CACHE_IMAGE_HEIGHT = 180;
    private static final int DISK_CACHE_IMAGE_WIDTH = 320;
    private static final int DISK_CACHE_SIZE = 268435456;
    private static final int DISK_THREAD_POOL_SIZE = 2;
    public static final String TAG = AlbumManager.class.getSimpleName();
    private UnityAlbumPresenter mAlbumPresenter;
    private DisplayImageOptions mDisplayImageOptions;
    private ImageLoader mImageLoader;
    private AlbumListener mListener;

    private AlbumManager() {
        this.mAlbumPresenter = new UnityAlbumPresenter(this, 0);
    }

    public static AlbumManager get_instance() {
        return SingleHolder.instance;
    }

    public void set_listener(AlbumListener listener) {
        this.mListener = listener;
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager, com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void init() {
        super.init();
        initImageLoader();
    }

    public void requestMediaData() {
        CameraLog.i(TAG, "requestMediaData:", false);
        this.mAlbumPresenter.requestMediaData(true);
    }

    public void deleteFiles(List<BaseItem> deletedList) {
        CameraLog.i(TAG, "deleteFiles: size " + deletedList.size(), false);
        this.mAlbumPresenter.deleteFiles(deletedList);
    }

    public void requestUsagePercent() {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.album.-$$Lambda$AlbumManager$7zDfofUg1PQU9ZEw0uWy-uXtCzI
            @Override // java.lang.Runnable
            public final void run() {
                AlbumManager.this.lambda$requestUsagePercent$1$AlbumManager();
            }
        });
    }

    public /* synthetic */ void lambda$requestUsagePercent$1$AlbumManager() {
        final long[] storageUsageInfo = FileUtils.getStorageUsageInfo(App.getInstance());
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.album.-$$Lambda$AlbumManager$hriboTS9XlM6OaBWed983bb8dNU
            @Override // java.lang.Runnable
            public final void run() {
                AlbumManager.this.lambda$null$0$AlbumManager(storageUsageInfo);
            }
        });
    }

    public /* synthetic */ void lambda$null$0$AlbumManager(final long[] storageUsageInfo) {
        AlbumListener albumListener = this.mListener;
        if (albumListener != null) {
            albumListener.onUsagePercent(storageUsageInfo[0], storageUsageInfo[1]);
        }
    }

    public String getThumbnailPath(String mediaPath) {
        String str = TAG;
        CameraLog.d(str, "getThumbnailPath:" + mediaPath, false);
        String thumbnailPath = null;
        try {
            File mediaFile = new File(mediaPath);
            if (mediaFile.exists()) {
                Uri uri = Uri.fromFile(mediaFile);
                File cacheFile = this.mImageLoader.getDiskCache().get(uri.toString());
                if (cacheFile != null && cacheFile.exists()) {
                    thumbnailPath = cacheFile.getAbsolutePath();
                    CameraLog.d(str, "thumbnail file :" + thumbnailPath + " exist,load form file", false);
                } else {
                    Bitmap thumb = this.mImageLoader.loadImageSync(uri.toString(), new ImageSize((int) DISK_CACHE_IMAGE_WIDTH, 180), this.mDisplayImageOptions);
                    if (thumb != null) {
                        thumb.recycle();
                    }
                    File cacheFile2 = this.mImageLoader.getDiskCache().get(uri.toString());
                    if (cacheFile2 != null && cacheFile2.exists()) {
                        thumbnailPath = cacheFile2.getAbsolutePath();
                        CameraLog.d(str, "thumbnail file :" + thumbnailPath + " extracted", false);
                    } else {
                        CameraLog.d(str, "media file :" + mediaPath + " extracted failed", false);
                    }
                }
            } else {
                CameraLog.d(str, "media file:" + mediaPath + " not exist", false);
            }
        } catch (Exception e) {
            CameraLog.d(TAG, "load media file thumbnail exception:" + mediaPath + e.getMessage(), false);
        }
        return thumbnailPath;
    }

    public boolean isPGear() {
        return this.mAlbumPresenter.isPGear();
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager, com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void enterScene() {
        CameraLog.d(TAG, "enter album scene!");
    }

    @Override // com.xiaopeng.xmart.cargallery.unity.app.GalleryAppManager, com.xiaopeng.xmart.cargallery.unity.UnityBaseManager
    public void exitScene() {
        CameraLog.d(TAG, "exit album scene!");
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.IAlbumView
    public void onMediaDataLoaded(final List<BaseItem> dataList, int selectedIndex) {
        CameraLog.i(TAG, "onMediaDataLoaded: size " + dataList.size(), false);
        if (this.mListener != null) {
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.album.-$$Lambda$AlbumManager$7RVhwdvnejIQDdtEkUlVCfxTGyg
                @Override // java.lang.Runnable
                public final void run() {
                    AlbumManager.this.lambda$onMediaDataLoaded$2$AlbumManager(dataList);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onMediaDataLoaded$2$AlbumManager(final List dataList) {
        this.mListener.onMediaDataLoaded(dataList);
    }

    @Override // com.xiaopeng.xmart.cargallery.view.album.IAlbumView
    public void onMediaDataDeleted(final List<BaseItem> deletedList) {
        CameraLog.i(TAG, "onMediaDataDeleted: size " + deletedList.size(), false);
        if (this.mListener != null) {
            ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.album.-$$Lambda$AlbumManager$jQsRUjUbfpu-wJDRm-Jo0IN1OGE
                @Override // java.lang.Runnable
                public final void run() {
                    AlbumManager.this.lambda$onMediaDataDeleted$3$AlbumManager(deletedList);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onMediaDataDeleted$3$AlbumManager(final List deletedList) {
        this.mListener.onMediaDataDeleted(deletedList);
    }

    private void initImageLoader() {
        File cacheDir = new File(App.getInstance().getFilesDir(), "gallery_thumbnail_cache");
        this.mDisplayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(App.getInstance()).threadPriority(3).defaultDisplayImageOptions(this.mDisplayImageOptions).discCacheFileNameGenerator(new Md5FileNameGenerator()).threadPoolSize(2).discCache(new UnlimitedDiskCache(cacheDir)).diskCacheSize(268435456).diskCacheExtraOptions(180, 180, (BitmapProcessor) null).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        this.mImageLoader = imageLoader;
        imageLoader.init(config);
    }

    public void uploadShow360TabBI() {
        Map<String, Number> params = new HashMap<>();
        BIHelper.getInstance().uploadGalleryBI(1, 0, params);
    }

    public void uploadClickShockFileBI() {
        Map<String, Number> params = new HashMap<>();
        BIHelper.getInstance().uploadGalleryBI(9, 25, params);
    }

    public void uploadPlayShockVideoBI() {
        Map<String, Number> params = new HashMap<>();
        BIHelper.getInstance().uploadGalleryBI(8, 26, params);
    }

    /* loaded from: classes12.dex */
    private static class SingleHolder {
        private static final AlbumManager instance = new AlbumManager();

        private SingleHolder() {
        }
    }
}
