package com.xiaopeng.xmart.cargallery.presenter;

import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.view.album.IAlbumView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/* loaded from: classes3.dex */
public class DetailPresenter extends AlbumPresenter {
    static final String TAG = "DetailPresenter";

    public DetailPresenter(IAlbumView view, int albumType) {
        super(view, albumType);
    }

    public void requestMediaData(final String path, final boolean isFromExternal) {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$DetailPresenter$LWt5d-1ch-_eyLXNi0q7byRD4nA
            @Override // java.lang.Runnable
            public final void run() {
                DetailPresenter.this.lambda$requestMediaData$1$DetailPresenter(isFromExternal, path);
            }
        });
    }

    public /* synthetic */ void lambda$requestMediaData$1$DetailPresenter(final boolean isFromExternal, final String path) {
        List<BaseItem> resultList = new ArrayList<>();
        List<BaseItem> dataList = this.mModel.getMediaDataList(isFromExternal);
        BaseItem selectedItem = null;
        if (path != null) {
            selectedItem = BaseItem.createItem(path);
        }
        if (dataList != null) {
            resultList.addAll(dataList);
        }
        int selectedIndex = -1;
        if (resultList.size() > 0) {
            if (selectedItem == null) {
                selectedIndex = 0;
            } else {
                selectedIndex = Collections.binarySearch(resultList, selectedItem, new Comparator() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$DetailPresenter$XTtG4fBzagGIEjiPEcOQrRFioVs
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compareTo;
                        compareTo = ((BaseItem) obj2).getName().compareTo(((BaseItem) obj).getName());
                        return compareTo;
                    }
                });
            }
        }
        notifyDataLoaded(resultList, selectedIndex);
    }

    @Override // com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter
    public void requestMediaData(boolean forceUpdate) {
        CameraLog.e(TAG, "requestMediaData is not valid in DetailPresenter!", false);
    }

    @Override // com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter
    public void requestDVRMediaData(String dvrDir) {
        CameraLog.e(TAG, "requestDVRMediaData dvrDir:" + dvrDir + ".do nothing here", false);
    }

    public void requestDVRMediaData(final String dvrDir, final String itemPath) {
        CameraLog.e(TAG, "requestDVRMediaData dvrDir:" + dvrDir + ",itemPath:" + itemPath, false);
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$DetailPresenter$bOtFe8U9libnjp7QMIJHilL2s-E
            @Override // java.lang.Runnable
            public final void run() {
                DetailPresenter.this.lambda$requestDVRMediaData$3$DetailPresenter(dvrDir, itemPath);
            }
        });
    }

    public /* synthetic */ void lambda$requestDVRMediaData$3$DetailPresenter(final String dvrDir, final String itemPath) {
        List<BaseItem> resultList = new ArrayList<>();
        List<BaseItem> dataList = this.mModel.getDVRMediaDataList(dvrDir);
        BaseItem selectedItem = null;
        if (itemPath != null) {
            selectedItem = BaseItem.createDVRItem(itemPath);
        }
        if (dataList != null) {
            resultList.addAll(dataList);
        }
        int selectedIndex = -1;
        if (resultList.size() > 0) {
            if (selectedItem == null) {
                selectedIndex = 0;
            } else {
                selectedIndex = Collections.binarySearch(resultList, selectedItem, new Comparator() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$DetailPresenter$h-dZRIo8Phn0vRfYYsWeTnMbGmM
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compareTo;
                        compareTo = ((BaseItem) obj2).getName().compareTo(((BaseItem) obj).getName());
                        return compareTo;
                    }
                });
            }
        }
        notifyDataLoaded(resultList, selectedIndex);
    }
}
