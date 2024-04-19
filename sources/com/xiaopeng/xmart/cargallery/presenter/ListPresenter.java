package com.xiaopeng.xmart.cargallery.presenter;

import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.DateCategoryItem;
import com.xiaopeng.xmart.cargallery.bean.FooterItemFactory;
import com.xiaopeng.xmart.cargallery.helper.CarTypeHelper;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.view.album.IAlbumView;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes3.dex */
public class ListPresenter extends AlbumPresenter {
    private static final String TAG = "ListPresenter";
    volatile boolean mIsLoading;

    public ListPresenter(IAlbumView view, int albumType) {
        super(view, albumType);
        this.mIsLoading = false;
    }

    @Override // com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter
    public void requestMediaData(final boolean forceUpdate) {
        if (this.mIsLoading) {
            CameraLog.d(TAG, "Current is loading", false);
            return;
        }
        this.mIsLoading = true;
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$ListPresenter$LkPNNGmL47ZXkRORPevNfjMc7Pw
            @Override // java.lang.Runnable
            public final void run() {
                ListPresenter.this.lambda$requestMediaData$0$ListPresenter(forceUpdate);
            }
        });
    }

    @Override // com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter
    public void requestDVRMediaData(String rootDir) {
    }

    @Override // com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter
    public void deleteFiles(final List<BaseItem> itemList) {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$ListPresenter$ApbayCph5EDQqwwHphxzJQQQvOY
            @Override // java.lang.Runnable
            public final void run() {
                ListPresenter.this.lambda$deleteFiles$1$ListPresenter(itemList);
            }
        });
    }

    public /* synthetic */ void lambda$deleteFiles$1$ListPresenter(final List itemList) {
        notifyDataDeleted(this.mModel.deleteData(itemList));
        lambda$requestMediaData$0$ListPresenter(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: requestMediaDataInner */
    public void lambda$requestMediaData$0$ListPresenter(boolean forceUpdate) {
        BaseItem footerItem;
        List<BaseItem> resultList = new ArrayList<>();
        List<BaseItem> dataList = this.mModel.getMediaDataList(forceUpdate);
        if (dataList != null) {
            String dateTakenKey = null;
            int size = dataList.size();
            CameraLog.i(TAG, "requestMediaDataInner force: " + forceUpdate + ", albumKey: " + this.mModel.getAlbumKey(), false);
            for (int i = 0; i < size; i++) {
                BaseItem item = dataList.get(i);
                String currentDateTakenKey = item.getDateTakenKey();
                if (dateTakenKey == null || !dateTakenKey.equals(currentDateTakenKey)) {
                    dateTakenKey = currentDateTakenKey;
                    resultList.add(new DateCategoryItem(item.getDateTaken()));
                }
                resultList.add(item);
            }
        }
        if (resultList.size() > 0 && (footerItem = FooterItemFactory.createFooterItem(CarTypeHelper.getXpCduType())) != null) {
            resultList.add(footerItem);
        }
        this.mIsLoading = false;
        notifyDataLoaded(resultList, -1);
    }
}
