package com.xiaopeng.xmart.cargallery.presenter;

import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.model.Album360Model;
import com.xiaopeng.xmart.cargallery.model.AlbumDVRModel;
import com.xiaopeng.xmart.cargallery.model.AlbumFrontModel;
import com.xiaopeng.xmart.cargallery.model.AlbumModel;
import com.xiaopeng.xmart.cargallery.model.AlbumTopModel;
import com.xiaopeng.xmart.cargallery.view.album.IAlbumView;
import java.util.List;
/* loaded from: classes3.dex */
public abstract class AlbumPresenter {
    static final String TAG = "AlbumPresenter";
    final IAlbumView mIAlbumView;
    AlbumModel mModel;

    public abstract void requestDVRMediaData(String rootDir);

    public abstract void requestMediaData(boolean forceUpdate);

    /* JADX INFO: Access modifiers changed from: package-private */
    public AlbumPresenter(IAlbumView view, int albumType) {
        this.mIAlbumView = view;
        switch (albumType) {
            case 0:
                this.mModel = new Album360Model();
                return;
            case 1:
                this.mModel = new AlbumFrontModel();
                return;
            case 2:
                this.mModel = new AlbumTopModel();
                return;
            case 3:
                this.mModel = new AlbumDVRModel();
                return;
            default:
                throw new IllegalArgumentException("Invalid album type: " + albumType);
        }
    }

    public void deleteFiles(final List<BaseItem> itemList) {
        CameraLog.d(TAG, "deleteFiles size: " + itemList.size(), false);
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$AlbumPresenter$iK8WxuZQQmgs_NJTNtiKVja3kh4
            @Override // java.lang.Runnable
            public final void run() {
                AlbumPresenter.this.lambda$deleteFiles$0$AlbumPresenter(itemList);
            }
        });
    }

    public /* synthetic */ void lambda$deleteFiles$0$AlbumPresenter(final List itemList) {
        CameraLog.d(TAG, "start to delete files");
        notifyDataDeleted(this.mModel.deleteData(itemList));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyDataLoaded(final List<BaseItem> dataList, final int selectedIndex) {
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$AlbumPresenter$T1BjLXXcXfB43dkZ4FEXYG5ewzc
            @Override // java.lang.Runnable
            public final void run() {
                AlbumPresenter.this.lambda$notifyDataLoaded$1$AlbumPresenter(dataList, selectedIndex);
            }
        });
    }

    public /* synthetic */ void lambda$notifyDataLoaded$1$AlbumPresenter(final List dataList, final int selectedIndex) {
        this.mIAlbumView.onMediaDataLoaded(dataList, selectedIndex);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyDataDeleted(final List<BaseItem> deletedList) {
        ThreadPoolHelper.getInstance().postOnMainThread(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$AlbumPresenter$g0AKXrRCdii_PTWQnB-xy5Std2A
            @Override // java.lang.Runnable
            public final void run() {
                AlbumPresenter.this.lambda$notifyDataDeleted$2$AlbumPresenter(deletedList);
            }
        });
    }

    public /* synthetic */ void lambda$notifyDataDeleted$2$AlbumPresenter(final List deletedList) {
        this.mIAlbumView.onMediaDataDeleted(deletedList);
    }
}
