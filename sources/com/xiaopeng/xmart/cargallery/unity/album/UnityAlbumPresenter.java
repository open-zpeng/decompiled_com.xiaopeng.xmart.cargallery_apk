package com.xiaopeng.xmart.cargallery.unity.album;

import com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper;
import com.xiaopeng.xmart.cargallery.carmanager.listener.IVcuController;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.presenter.ListPresenter;
import com.xiaopeng.xmart.cargallery.view.album.IAlbumView;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes12.dex */
public class UnityAlbumPresenter extends ListPresenter {
    private IVcuController mIVcuController;

    public UnityAlbumPresenter(IAlbumView view, int albumType) {
        super(view, albumType);
        final CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.unity.album.-$$Lambda$UnityAlbumPresenter$t2Ue3WE-ULwDqENpDbocjtQ4StM
            @Override // java.lang.Runnable
            public final void run() {
                UnityAlbumPresenter.this.lambda$new$0$UnityAlbumPresenter(carClientWrapper);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$UnityAlbumPresenter(final CarClientWrapper carClientWrapper) {
        this.mIVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
    }

    @Override // com.xiaopeng.xmart.cargallery.presenter.ListPresenter, com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter
    public void requestDVRMediaData(String rootDir) {
    }

    public boolean isPGear() {
        IVcuController iVcuController = this.mIVcuController;
        return iVcuController != null && iVcuController.getGearLevel() == 4;
    }
}
