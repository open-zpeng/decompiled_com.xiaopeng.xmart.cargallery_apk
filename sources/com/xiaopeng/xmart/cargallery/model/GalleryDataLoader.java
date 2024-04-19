package com.xiaopeng.xmart.cargallery.model;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.PhotoItem;
import com.xiaopeng.xmart.cargallery.bean.VideoItem;
import com.xiaopeng.xmart.cargallery.define.DVRDefine;
import com.xiaopeng.xmart.cargallery.fastsp.FastSharedPreferences;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.model.GalleryDataLoader;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.paho.client.mqttv3.MqttTopic;
/* loaded from: classes5.dex */
public class GalleryDataLoader {
    private static final String DELETE_PHOTO_SELECTION_CAUSE = "_data=?";
    private static final String DELETE_VIDEO_SELECTION_CAUSE = "_data=?";
    public static final String KEY_360 = "360Camera";
    public static final String KEY_DVR = "camera_dvr";
    public static final String KEY_FRONT = "FrontCamera";
    public static final String KEY_SHOCK = "shockCamera";
    public static final String KEY_TOP = "camera_top";
    private static final String[] PROJECTION_PHOTO = {"_id", "_data", "_display_name", "_size", "longitude", "latitude", "bucket_id"};
    private static final String[] PROJECTION_VIDEO = {"_id", "_data", "_display_name", "_size", "longitude", "latitude", "bucket_id", "duration"};
    private static final boolean SHOCK_ARCHIVE_360 = true;
    private static final String TAG = "GalleryDataLoader";
    private Map<String, BaseItem> mCacheMap;
    private volatile boolean mCancelLoad;
    private Context mContext;
    private Hashtable<String, List<BaseItem>> mDataMap;
    private boolean mIsDataLoading;
    private final Object mLock;
    private Map<String, Boolean> mShockUnReadList;

    /* loaded from: classes5.dex */
    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

    private GalleryDataLoader() {
        this.mLock = new Object();
        this.mIsDataLoading = false;
        this.mCancelLoad = false;
        this.mShockUnReadList = new HashMap();
        this.mCacheMap = new ConcurrentHashMap();
        this.mDataMap = new Hashtable<>();
    }

    public static GalleryDataLoader getInstance() {
        return SingletonHolder.sINSTANCE;
    }

    public void setContext(Context app) {
        this.mContext = app;
    }

    private boolean isItemLoaded(BaseItem item) {
        if (item == null) {
            return false;
        }
        return this.mCacheMap.containsKey(item.getPath());
    }

    private boolean isItemLoaded(String itemPath) {
        if (itemPath == null) {
            return false;
        }
        return this.mCacheMap.containsKey(itemPath);
    }

    public boolean isDataLoaded() {
        return !this.mCacheMap.isEmpty();
    }

    private void loadData(OnDataLoadedListener listener) {
        loadData(listener, false);
    }

    private void loadData(final OnDataLoadedListener listener, boolean forceUpdate) {
        if (this.mIsDataLoading || (!forceUpdate && !this.mCacheMap.isEmpty())) {
            synchronized (this.mLock) {
                CameraLog.d(TAG, "loadData(), got lock for new request");
                if (!forceUpdate && !this.mCacheMap.isEmpty() && listener != null) {
                    CameraLog.d(TAG, "notify data loaded");
                    notifyLoadData(listener);
                }
            }
            return;
        }
        synchronized (this.mLock) {
            this.mIsDataLoading = true;
            CameraLog.d(TAG, "loadData(), get lock.", false);
            this.mCacheMap.clear();
            loadAllPhotoItems();
            loadAllVideoItems();
            CameraLog.d(TAG, "loadData() end, found " + this.mCacheMap.size() + " items", false);
            notifyLoadData(listener);
            this.mIsDataLoading = false;
        }
    }

    public void cancelLoadData(boolean needCancel) {
        CameraLog.d(TAG, "cancel load data..", false);
        this.mCancelLoad = needCancel;
    }

    private List<BaseItem> loadData(String key) {
        File dirFile;
        int bucketId;
        File[] files;
        CameraLog.d(TAG, "loadData, key: " + key, false);
        List<BaseItem> dataList = new CopyOnWriteArrayList<>();
        if (TextUtils.isEmpty(key)) {
            return dataList;
        }
        char c = 65535;
        int i = 2;
        switch (key.hashCode()) {
            case -2011784901:
                if (key.equals("camera_top")) {
                    c = 3;
                    break;
                }
                break;
            case -701800526:
                if (key.equals("360Camera")) {
                    c = 0;
                    break;
                }
                break;
            case -606326130:
                if (key.equals("FrontCamera")) {
                    c = 2;
                    break;
                }
                break;
            case 711733351:
                if (key.equals("shockCamera")) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                int bucketId2 = FileUtils.BUCKET_ID_360;
                dirFile = new File(FileUtils.DIR_360_FULL_PATH);
                bucketId = bucketId2;
                break;
            case 1:
                int bucketId3 = FileUtils.BUCKET_ID_SHOCK;
                dirFile = new File(FileUtils.DIR_SHOCK_FULL_PATH);
                bucketId = bucketId3;
                break;
            case 2:
                int bucketId4 = FileUtils.BUCKET_ID_FRONT;
                dirFile = new File(FileUtils.DIR_FRONT_FULL_PATH);
                bucketId = bucketId4;
                break;
            case 3:
                int bucketId5 = FileUtils.BUCKET_ID_TOP;
                dirFile = new File(FileUtils.DIR_TOP_FULL_PATH);
                bucketId = bucketId5;
                break;
            default:
                dirFile = null;
                bucketId = 0;
                break;
        }
        synchronized (this.mLock) {
            this.mCancelLoad = false;
            final int finalBucketId = bucketId;
            if (dirFile != null && (files = dirFile.listFiles(new FilenameFilter() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$taKvQhber7356qO62jnIW7BCOBI
                @Override // java.io.FilenameFilter
                public final boolean accept(File file, String str) {
                    return GalleryDataLoader.lambda$loadData$0(file, str);
                }
            })) != null) {
                CameraLog.d(TAG, "List file found items: " + files.length, false);
                int length = files.length;
                int i2 = 0;
                while (i2 < length) {
                    File file = files[i2];
                    if (!this.mCancelLoad) {
                        if (!isItemLoaded(file.getPath())) {
                            BaseItem item = BaseItem.createItem(file.getPath());
                            if (item.getType() == i && item.getDuration() <= 0) {
                                item.setDuration(parseDurationFromMMRetriever(file.getPath()));
                            }
                            if (item.isValid()) {
                                this.mCacheMap.put(item.getPath(), item);
                            } else {
                                try {
                                    file.delete();
                                    CameraLog.d(TAG, "file:" + file.getPath() + " invalid,delete", false);
                                } catch (Exception e) {
                                    CameraLog.e(TAG, e.getMessage());
                                }
                            }
                        }
                        i2++;
                        i = 2;
                    }
                }
            }
            List<BaseItem> subList = (List) this.mCacheMap.values().stream().filter(new Predicate() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$jb93r0geyR5DOZ21kYtlsWnZEqU
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return GalleryDataLoader.lambda$loadData$1(finalBucketId, (BaseItem) obj);
                }
            }).collect(Collectors.toList());
            CameraLog.d(TAG, subList.size() + " items found for type: " + key, false);
            Iterator<BaseItem> iterator = subList.iterator();
            while (iterator.hasNext()) {
                BaseItem item2 = iterator.next();
                CameraLog.d(TAG, item2.getName() + " item.isValid():" + item2.isValid() + " isItemLoaded:" + isItemLoaded(item2));
                if (item2.isValid()) {
                    item2.setKey(key);
                    if (!isItemLoaded(item2)) {
                        this.mCacheMap.put(item2.getPath(), item2);
                    }
                } else {
                    iterator.remove();
                }
            }
            if ("shockCamera".equals(key)) {
                updateShockUnReadList();
                for (BaseItem item3 : subList) {
                    item3.setKey("360Camera");
                    item3.setBucketId(FileUtils.BUCKET_ID_360);
                    item3.setIsCollision(true);
                    item3.setRead(!this.mShockUnReadList.containsKey(item3.getName()));
                }
            }
            if (subList.size() > 0) {
                Collections.sort(subList, new Comparator() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$Yx9LKRhu7I7vwW8gyhIcwH5rRTs
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compareTo;
                        compareTo = ((BaseItem) obj2).getName().compareTo(((BaseItem) obj).getName());
                        return compareTo;
                    }
                });
            }
            dataList.clear();
            dataList.addAll(subList);
        }
        return dataList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$loadData$0(File dir, String filename) {
        return filename.endsWith(FileUtils.EXTEND_MP4) || filename.endsWith(".jpg");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$loadData$1(final int finalBucketId, BaseItem baseItem) {
        return baseItem.getBucketId() == finalBucketId;
    }

    private void notifyLoadData(final OnDataLoadedListener listener) {
        if (listener != null) {
            ThreadPoolHelper threadPoolHelper = ThreadPoolHelper.getInstance();
            listener.getClass();
            threadPoolHelper.postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$G482UxpMZlv21F7sms-AME4XAB4
                @Override // java.lang.Runnable
                public final void run() {
                    GalleryDataLoader.OnDataLoadedListener.this.onDataLoaded();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<BaseItem> getData(String key, boolean forceUpdate) {
        return getData(key, forceUpdate, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<BaseItem> getData(String dirPath) {
        List<BaseItem> dataList;
        synchronized (this.mLock) {
            CameraLog.d(TAG, "start getData dirPath: " + dirPath, false);
            FileUtils.creatNoMediaFileForDVR(dirPath);
            dataList = new CopyOnWriteArrayList<>();
            String recordDirPath = dirPath + File.separator + "RECORD";
            FileUtils.creatNoMediaFileForDVR(recordDirPath);
            File recordDir = new File(recordDirPath);
            String protectDirPath = dirPath + File.separator + "PROTECT";
            FileUtils.creatNoMediaFileForDVR(protectDirPath);
            File protectDir = new File(protectDirPath);
            this.mCancelLoad = false;
            File[] files = recordDir.listFiles(new FilenameFilter() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$TRFz1ae42hB3F960BqQh81aMbcQ
                @Override // java.io.FilenameFilter
                public final boolean accept(File file, String str) {
                    return GalleryDataLoader.lambda$getData$3(file, str);
                }
            });
            long j = 0;
            if (files != null) {
                CameraLog.d(TAG, "recordDir file found items: " + files.length, false);
                int length = files.length;
                int i = 0;
                while (i < length) {
                    File file = files[i];
                    if (this.mCancelLoad) {
                        break;
                    }
                    if (!isItemLoaded(file.getPath())) {
                        if (file.length() > j) {
                            BaseItem item = BaseItem.createDVRItem(file.getPath());
                            if (item.getType() == 2 && item.getDuration() <= j) {
                                setDVRVideoDuration(item);
                            }
                            if (item.isValid()) {
                                this.mCacheMap.put(item.getPath(), item);
                            }
                        } else {
                            CameraLog.d(TAG, "file: " + file.getAbsolutePath() + " length:" + file.length() + " delete", false);
                            try {
                                file.delete();
                            } catch (Exception e) {
                                CameraLog.e(TAG, e.getMessage());
                            }
                        }
                    }
                    i++;
                    j = 0;
                }
            }
            File[] files2 = protectDir.listFiles(new FilenameFilter() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$3JibwdwWycTHrw9QXuWaC7GxCQM
                @Override // java.io.FilenameFilter
                public final boolean accept(File file2, String str) {
                    return GalleryDataLoader.lambda$getData$4(file2, str);
                }
            });
            if (files2 != null) {
                CameraLog.d(TAG, "protectDir file found items: " + files2.length, false);
                for (File file2 : files2) {
                    if (this.mCancelLoad) {
                        break;
                    }
                    if (!isItemLoaded(file2.getPath())) {
                        BaseItem item2 = BaseItem.createDVRItem(file2.getPath());
                        if (item2.getType() == 2 && item2.getDuration() <= 0) {
                            setDVRVideoDuration(item2);
                        }
                        if (item2.isValid()) {
                            this.mCacheMap.put(item2.getPath(), item2);
                        }
                    }
                }
            }
            List<BaseItem> subList = (List) this.mCacheMap.values().stream().filter(new Predicate() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$sLo260CVeXKdT6nK1M_j7VB9414
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return GalleryDataLoader.lambda$getData$5((BaseItem) obj);
                }
            }).collect(Collectors.toList());
            Iterator<BaseItem> iterator = subList.iterator();
            while (iterator.hasNext()) {
                BaseItem item3 = iterator.next();
                CameraLog.d(TAG, item3.getName() + " item.isValid():" + item3.isValid() + " isItemLoaded:" + isItemLoaded(item3));
                if (item3.isValid()) {
                    if (!isItemLoaded(item3)) {
                        this.mCacheMap.put(item3.getPath(), item3);
                    }
                } else {
                    iterator.remove();
                }
            }
            if (subList.size() > 0) {
                Collections.sort(subList, new Comparator() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$uiTf32Fbmw9PrOi_tnqUeVfKh3A
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compareTo;
                        compareTo = ((BaseItem) obj2).getName().compareTo(((BaseItem) obj).getName());
                        return compareTo;
                    }
                });
            }
            dataList.clear();
            dataList.addAll(subList);
        }
        return dataList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$getData$3(File dir, String filename) {
        return filename.endsWith(FileUtils.EXTEND_MP4) || filename.endsWith(".jpg");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$getData$4(File dir, String filename) {
        return filename.endsWith(FileUtils.EXTEND_MP4) || filename.endsWith(".jpg");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$getData$5(BaseItem baseItem) {
        return baseItem.getBucketId() == FileUtils.BUCKET_ID_DVR;
    }

    private void setDVRVideoDuration(BaseItem item) {
        FastSharedPreferences fastSp = FastSharedPreferences.get(DVRDefine.DVR_VIDEO_DURATION_FILE_NAME);
        String videoName = item.getPath().substring(item.getPath().lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1);
        long savedVideoDuration = fastSp.getLong(videoName, -1L);
        if (savedVideoDuration > 0) {
            CameraLog.d(TAG, "FastSharedPreferences get " + videoName + ",videoDuration:" + savedVideoDuration, false);
            item.setDuration(savedVideoDuration);
            return;
        }
        item.setDuration(DVRDefine.DVR_VIDEO_DURATION_NOT_EXTRACTED);
    }

    private List<BaseItem> getData(String key, boolean forceUpdate, boolean newList) {
        List<BaseItem> list;
        synchronized (this.mLock) {
            CameraLog.d(TAG, "start getData, isForceUpdate: " + forceUpdate + " key:" + key, false);
            if (forceUpdate) {
                this.mCacheMap.clear();
                Hashtable<String, List<BaseItem>> hashtable = this.mDataMap;
                if (hashtable != null && (list = hashtable.get(key)) != null) {
                    list.clear();
                }
                if ("360Camera".equals(key)) {
                    this.mDataMap.remove("shockCamera");
                }
                this.mShockUnReadList.clear();
            }
            if (this.mCacheMap.isEmpty()) {
                loadData((OnDataLoadedListener) null);
            }
            List<BaseItem> dataList = this.mDataMap.get(key);
            if (dataList == null || dataList.size() == 0) {
                CameraLog.d(TAG, "No items found for " + key + ", load from mCacheMap", false);
                dataList = loadData(key);
                if ("360Camera".equals(key)) {
                    List<BaseItem> shockList = this.mDataMap.get("shockCamera");
                    if (shockList == null) {
                        shockList = getData("shockCamera", forceUpdate, false);
                    }
                    dataList.addAll(shockList);
                    if (shockList.size() > 0) {
                        Collections.sort(dataList, new Comparator() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$W7AVh8ovsiRumR0GtW6iejAAZlQ
                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                int compareTo;
                                compareTo = ((BaseItem) obj2).getName().compareTo(((BaseItem) obj).getName());
                                return compareTo;
                            }
                        });
                    }
                }
                this.mDataMap.put(key, dataList);
            }
            CameraLog.d(TAG, key + " getData, loaded item size: " + dataList.size(), false);
            return newList ? new ArrayList(dataList) : dataList;
        }
    }

    private void deleteShockCacheData(List<BaseItem> itemList) {
        CameraLog.d(TAG, "deleteShockCacheData item:" + itemList.size());
        List<BaseItem> shockItemList = this.mDataMap.get("shockCamera");
        if (shockItemList != null && itemList.size() > 0) {
            CameraLog.d(TAG, "deleteShockCacheData size:" + shockItemList.size());
            for (BaseItem item : itemList) {
                CameraLog.d(TAG, "deleteShockCacheData getPath:" + item.getPath());
                if (item.isCollision() && "360Camera".equals(item.getKey())) {
                    shockItemList.remove(item);
                    setShockUnReadState(item);
                }
            }
            CameraLog.d(TAG, "deleteShockCacheData size:" + shockItemList.size());
        }
    }

    private void updateShockUnReadList() {
        if (this.mShockUnReadList.size() > 0) {
            return;
        }
        File dirFile = new File(FileUtils.DIR_SHOCK_CACHE_FULL_PATH);
        File[] files = dirFile.listFiles(new FilenameFilter() { // from class: com.xiaopeng.xmart.cargallery.model.-$$Lambda$GalleryDataLoader$Z4m2cbVcKuAvdNr36M5UC_iLKFk
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return GalleryDataLoader.lambda$updateShockUnReadList$8(file, str);
            }
        });
        if (files != null) {
            CameraLog.d(TAG, "getShockUnReadList file found items: " + files.length, false);
            for (File file : files) {
                CameraLog.d(TAG, "getShockUnReadList file found items: " + file.getPath(), false);
                this.mShockUnReadList.put(file.getName(), Boolean.TRUE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$updateShockUnReadList$8(File dir, String filename) {
        return filename.endsWith(FileUtils.EXTEND_MP4) || filename.endsWith(".jpg");
    }

    public void setShockUnReadState(BaseItem item) {
        CameraLog.d(TAG, "setShockUnReadState size: " + this.mShockUnReadList.size() + ", item: " + item);
        if (item != null && item.isCollision()) {
            int last = item.getPath().lastIndexOf(File.separator);
            StringBuilder buffer = new StringBuilder(item.getPath());
            buffer.insert(last, File.separator + FileUtils.DIR_SHOCK_CACHE);
            File file = new File(buffer.toString());
            CameraLog.d(TAG, "setShockUnReadState path: " + buffer.toString() + ", path: " + file.getPath(), false);
            this.mShockUnReadList.remove(item.getName());
            if (file.exists()) {
                file.delete();
                item.setRead(true);
            }
            CameraLog.d(TAG, "setShockUnReadState size: " + this.mShockUnReadList.size());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<BaseItem> delete(final List<BaseItem> itemList) {
        Uri uri;
        String selection;
        CameraLog.d(TAG, "start to delete " + itemList.size() + " files", false);
        if (itemList.size() <= 0) {
            return itemList;
        }
        String key = itemList.get(0).getKey();
        synchronized (this.mLock) {
            CameraLog.d(TAG, "Delete procedure, get lock");
            List<BaseItem> deletedList = new ArrayList<>();
            List<BaseItem> dataList = this.mDataMap.get(key);
            if (dataList == null) {
                return deletedList;
            }
            CameraLog.d(TAG, "Delete list, original list size: " + dataList.size(), false);
            CameraLog.d(TAG, "Delete file list with size: " + itemList.size(), false);
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            for (BaseItem item : itemList) {
                if (item.getType() == 1) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    selection = "_data=?";
                } else if (item.getType() == 2) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    selection = "_data=?";
                }
                CameraLog.d(TAG, "Delete file path: " + item.getPath(), false);
                ContentProviderOperation op = ContentProviderOperation.newDelete(uri).withSelection(selection, new String[]{item.getPath()}).build();
                ops.add(op);
            }
            try {
                ContentProviderResult[] cps = App.getInstance().getContentResolver().applyBatch("media", ops);
                CameraLog.d(TAG, "Batch delete executed size: " + cps.length, false);
                if (cps.length == itemList.size()) {
                    for (int i = 0; i < cps.length; i++) {
                        ContentProviderResult cpr = cps[i];
                        if (cpr.count.intValue() > 0) {
                            BaseItem item2 = itemList.get(i);
                            this.mCacheMap.remove(item2.getPath());
                            dataList.remove(item2);
                            deletedList.add(item2);
                        } else {
                            BaseItem item3 = itemList.get(i);
                            CameraLog.d(TAG, "Batch delete failure file: " + item3.getPath() + "need delete by file api ", false);
                            File file = new File(item3.getPath());
                            if (file.exists() && file.delete()) {
                                this.mCacheMap.remove(item3.getPath());
                                dataList.remove(item3);
                                deletedList.add(item3);
                                CameraLog.d(TAG, item3.getPath() + "delete success by file api", false);
                            }
                        }
                    }
                }
                CameraLog.d(TAG, "Batch delete success size: " + deletedList.size(), false);
            } catch (OperationApplicationException | RemoteException e) {
                CameraLog.e(TAG, e.getMessage(), false);
            }
            deleteShockCacheData(deletedList);
            return deletedList;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<BaseItem> deleteDvr(final List<BaseItem> itemList) {
        CameraLog.d(TAG, "start to delete " + itemList.size() + " files", false);
        if (itemList.size() <= 0) {
            return itemList;
        }
        this.mCancelLoad = false;
        List<BaseItem> deletedList = new ArrayList<>();
        synchronized (this.mLock) {
            CameraLog.d(TAG, "Delete procedure, get lock");
            for (int i = 0; i < itemList.size() && !this.mCancelLoad; i++) {
                BaseItem item = itemList.get(i);
                File file = new File(item.getPath());
                if (file.exists() && file.delete()) {
                    this.mCacheMap.remove(item.getPath());
                    deletedList.add(item);
                    CameraLog.d(TAG, item.getPath() + "delete success by file api", false);
                }
            }
            CameraLog.d(TAG, "Batch delete success size: " + deletedList.size(), false);
        }
        return deletedList;
    }

    public void clear() {
        this.mCacheMap.clear();
        this.mDataMap.clear();
    }

    private void loadAllPhotoItems() {
        Cursor cursor = null;
        try {
            cursor = MediaStore.Images.Media.query(this.mContext.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION_PHOTO, "bucket_id in (?,?) and mime_type=?", new String[]{String.valueOf(FileUtils.BUCKET_ID_360), String.valueOf(FileUtils.BUCKET_ID_TOP), "image/jpeg"}, null);
            if (cursor != null) {
                fillCacheWithCursor(cursor, false);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void loadAllVideoItems() {
        Cursor cursor = null;
        try {
            cursor = MediaStore.Images.Media.query(this.mContext.getContentResolver(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI, PROJECTION_VIDEO, "bucket_id in (?,?) and mime_type=?", new String[]{String.valueOf(FileUtils.BUCKET_ID_360), String.valueOf(FileUtils.BUCKET_ID_TOP), "video/mp4"}, null);
            if (cursor != null) {
                fillCacheWithCursor(cursor, true);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void fillCacheWithCursor(Cursor cursor, boolean isVideo) {
        while (cursor.moveToNext()) {
            String data = cursor.getString(1);
            File file = new File(data);
            if (file.exists() && file.length() > 0) {
                BaseItem item = isVideo ? new VideoItem(data) : new PhotoItem(data);
                item.setMediaId(cursor.getInt(0));
                item.setName(cursor.getString(2));
                item.setSize(cursor.getInt(3));
                item.setLongitude(cursor.getDouble(4));
                item.setLatitude(cursor.getDouble(5));
                item.setBucketId(cursor.getInt(6));
                if (isVideo) {
                    long duration = cursor.getLong(7);
                    if (duration <= 0) {
                        duration = parseDurationFromMMRetriever(data);
                        CameraLog.d(TAG, "parse duration from media meta retriever... duration: " + duration, false);
                    }
                    item.setDuration(duration);
                    if (!item.isValid()) {
                        try {
                            try {
                                file.delete();
                                CameraLog.d(TAG, "file:" + file.getPath() + " invalid,delete", false);
                            } catch (Exception e) {
                                CameraLog.e(TAG, e.getMessage(), false);
                            }
                        } catch (Throwable th) {
                        }
                    }
                }
                CameraLog.d(TAG, "MediaInfo: " + item.toString(), false);
                this.mCacheMap.put(item.getPath(), item);
            }
        }
    }

    public static long parseDurationFromMMRetriever(String videoPath) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        long videoDuration = 0;
        try {
            try {
                CameraLog.d(TAG, "parseDurationFromMMRetriever videoPath:" + videoPath, false);
                retriever.setDataSource(videoPath);
                String duration = retriever.extractMetadata(9);
                if (!TextUtils.isEmpty(duration)) {
                    videoDuration = Long.parseLong(duration);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                CameraLog.d(TAG, "parse duration exception:" + ex.getMessage(), false);
            }
            return videoDuration;
        } finally {
            retriever.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class SingletonHolder {
        private static GalleryDataLoader sINSTANCE = new GalleryDataLoader();

        private SingletonHolder() {
        }
    }
}
