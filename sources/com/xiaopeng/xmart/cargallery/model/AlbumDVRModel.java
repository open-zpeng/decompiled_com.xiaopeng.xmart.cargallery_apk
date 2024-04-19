package com.xiaopeng.xmart.cargallery.model;

import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import java.util.List;
/* loaded from: classes5.dex */
public class AlbumDVRModel extends AlbumModel {
    private static final String TAG = "AlbumDVRModel";

    @Override // com.xiaopeng.xmart.cargallery.model.AlbumModel
    public String getAlbumKey() {
        return "camera_dvr";
    }

    @Override // com.xiaopeng.xmart.cargallery.model.AlbumModel
    public List<BaseItem> deleteData(List<BaseItem> dataList) {
        if (dataList != null && dataList.size() > 0) {
            CameraLog.d(TAG, "start to delete " + dataList.size() + " files", false);
            uploadDeleteBI(dataList);
            return GalleryDataLoader.getInstance().deleteDvr(dataList);
        }
        return dataList;
    }
}
