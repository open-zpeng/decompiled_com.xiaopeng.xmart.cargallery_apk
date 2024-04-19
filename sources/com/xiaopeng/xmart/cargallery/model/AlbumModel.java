package com.xiaopeng.xmart.cargallery.model;

import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.helper.BIHelper;
import com.xiaopeng.xmart.cargallery.utils.FileUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes5.dex */
public abstract class AlbumModel {
    private static final String TAG = "AlbumModel";
    private final String mAlbumKey = getAlbumKey();

    public abstract String getAlbumKey();

    public List<BaseItem> getMediaDataList(boolean forceUpdate) {
        CameraLog.d(TAG, "start to get media data list for: " + this.mAlbumKey);
        return GalleryDataLoader.getInstance().getData(this.mAlbumKey, forceUpdate);
    }

    public List<BaseItem> getDVRMediaDataList(String dir) {
        CameraLog.d(TAG, "start to get media data list dir: " + dir);
        return GalleryDataLoader.getInstance().getData(dir);
    }

    public List<BaseItem> deleteData(List<BaseItem> dataList) {
        if (dataList != null && dataList.size() > 0) {
            CameraLog.d(TAG, "start to delete " + dataList.size() + " files", false);
            uploadDeleteBI(dataList);
            return GalleryDataLoader.getInstance().delete(dataList);
        }
        return dataList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void uploadDeleteBI(List<BaseItem> dataList) {
        BaseItem item = dataList.get(0);
        int eventType = 21;
        if ("camera_top".equals(item.getKey())) {
            eventType = 23;
        } else if ("camera_dvr".equals(item.getKey())) {
            eventType = 22;
        }
        Map<String, Number> params = new HashMap<>();
        params.put(BIConfig.PROPERTY.DATA_TYPE, 2);
        params.put("result", 1);
        BIHelper.getInstance().uploadGalleryBI(8, eventType, params);
        if (FileUtils.isExitShockFile(dataList)) {
            BIHelper.getInstance().uploadGalleryBI(8, 24, params);
        }
    }
}
