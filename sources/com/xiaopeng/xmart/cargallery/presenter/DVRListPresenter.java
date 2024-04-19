package com.xiaopeng.xmart.cargallery.presenter;

import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import com.xiaopeng.xmart.cargallery.App;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.bean.BaseItem;
import com.xiaopeng.xmart.cargallery.bean.DateCategoryItem;
import com.xiaopeng.xmart.cargallery.bean.FooterItem;
import com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper;
import com.xiaopeng.xmart.cargallery.carmanager.listener.ICiuController;
import com.xiaopeng.xmart.cargallery.carmanager.listener.IVcuController;
import com.xiaopeng.xmart.cargallery.helper.ThreadPoolHelper;
import com.xiaopeng.xmart.cargallery.manager.DVRStorageManager;
import com.xiaopeng.xmart.cargallery.view.album.IAlbumView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/* loaded from: classes3.dex */
public class DVRListPresenter extends ListPresenter {
    private static final String TAG = "DVRListPresenter";
    private volatile boolean mCarControllerInited;
    private ICiuController mCiuController;
    private String mDVRDir;
    private IVcuController mIVcuController;
    private boolean mIsDVRMounted;
    private StorageEventListener mStorageEventListener;
    private StorageManager mStorageManager;

    public DVRListPresenter(IAlbumView view, int albumType) {
        super(view, albumType);
        this.mStorageManager = (StorageManager) App.getInstance().getSystemService("storage");
        this.mStorageEventListener = new StorageEventListener() { // from class: com.xiaopeng.xmart.cargallery.presenter.DVRListPresenter.1
            public void onVolumeStateChanged(VolumeInfo vol, int oldState, int newState) {
                super.onVolumeStateChanged(vol, oldState, newState);
                if (oldState == 1 && newState == 2) {
                    CameraLog.d(DVRListPresenter.TAG, "onVolumeStateChanged VolumeInfo: " + vol.toString() + " oldState:" + oldState + " newState:" + newState + ",mIsDVRMounted:" + DVRListPresenter.this.mIsDVRMounted, false);
                    if (!DVRListPresenter.this.mIsDVRMounted) {
                        DVRListPresenter.this.mDVRDir = DVRStorageManager.getInstance().getDVRDirPath();
                        if (!TextUtils.isEmpty(DVRListPresenter.this.mDVRDir)) {
                            CameraLog.d(DVRListPresenter.TAG, "dvrPath VolumeState: " + DVRStorageManager.getInstance().getVolumeState(DVRListPresenter.this.mDVRDir), false);
                            CameraLog.d(DVRListPresenter.TAG, "startLoadingData: " + DVRListPresenter.this.mDVRDir, false);
                            DVRListPresenter.this.mIsDVRMounted = true;
                            int gearLevel = DVRListPresenter.this.mIVcuController.getGearLevel();
                            CameraLog.d(DVRListPresenter.TAG, "gearLevel: " + gearLevel, false);
                            if (gearLevel == 4) {
                                DVRListPresenter dVRListPresenter = DVRListPresenter.this;
                                dVRListPresenter.requestDVRMediaData(dVRListPresenter.mDVRDir);
                            }
                        }
                    }
                }
            }

            public void onStorageStateChanged(String path, String oldState, String newState) {
                CameraLog.d(DVRListPresenter.TAG, "onVolumeStateChanged path: " + path + " oldState:" + oldState + " newState:" + newState, false);
                if (DVRListPresenter.this.mIsDVRMounted && path.equals(DVRListPresenter.this.mDVRDir) && oldState.equals("mounted") && newState.equals("ejecting")) {
                    DVRListPresenter.this.mIAlbumView.onDvrSdCardRemoved();
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initController() {
        CameraLog.i(TAG, "  initController ", false);
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mCiuController = (ICiuController) carClientWrapper.getController(CarClientWrapper.XP_CIU_SERVICE);
        this.mIVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        this.mCarControllerInited = true;
    }

    public int getDvrMode() {
        ICiuController iCiuController = this.mCiuController;
        if (iCiuController != null) {
            try {
                return iCiuController.getDvrMode();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    public boolean isPGear() {
        IVcuController iVcuController = this.mIVcuController;
        return iVcuController != null && iVcuController.getGearLevel() == 4;
    }

    public void switchDvrReplayMode() {
        ICiuController iCiuController = this.mCiuController;
        if (iCiuController != null) {
            try {
                int dvrMode = iCiuController.getDvrMode();
                CameraLog.d(TAG, "switchDvrReplayMode dvr current mode:" + dvrMode, false);
                if (dvrMode == 0) {
                    this.mIsDVRMounted = false;
                    this.mCiuController.setDvrMode(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                CameraLog.d(TAG, "switchDvrReplayMode occurs exception:" + e.getMessage(), false);
            }
        }
    }

    public void switchDvrRecordMode() {
        try {
            ICiuController iCiuController = this.mCiuController;
            if (iCiuController != null) {
                int dvrMode = iCiuController.getDvrMode();
                CameraLog.d(TAG, "switchDvrRecordMode dvr current mode:" + dvrMode, false);
                if (dvrMode == 1) {
                    this.mCiuController.setDvrMode(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CameraLog.d(TAG, "switchDvrRecordMode occurs exception:" + e.getMessage(), false);
        }
    }

    public void registerStorageEventListener() {
        CameraLog.d(TAG, "registerStorageEventListener() ", false);
        try {
            Method method = StorageManager.class.getMethod("registerListener", StorageEventListener.class);
            method.invoke(this.mStorageManager, this.mStorageEventListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$DVRListPresenter$2DdYplXjQsnxhDq6pjriWA-YJiY
            @Override // java.lang.Runnable
            public final void run() {
                DVRListPresenter.this.initController();
            }
        });
    }

    public void unRegisterStorageEventListener() {
        CameraLog.d(TAG, "unRegisterStorageEventListener() ", false);
        try {
            Method method = StorageManager.class.getMethod("unregisterListener", StorageEventListener.class);
            method.invoke(this.mStorageManager, this.mStorageEventListener);
        } catch (Exception e) {
        }
        switchDvrRecordMode();
        this.mStorageManager = null;
        this.mDVRDir = null;
    }

    public void registerGearEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void unRegisterGearEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.presenter.ListPresenter, com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter
    public void deleteFiles(final List<BaseItem> itemList) {
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$DVRListPresenter$pHRkqGy_73ag73hIoTCflz_CuWA
            @Override // java.lang.Runnable
            public final void run() {
                DVRListPresenter.this.lambda$deleteFiles$0$DVRListPresenter(itemList);
            }
        });
    }

    public /* synthetic */ void lambda$deleteFiles$0$DVRListPresenter(final List itemList) {
        notifyDataDeleted(this.mModel.deleteData(itemList));
        requestDVRMediaData(this.mDVRDir);
    }

    @Override // com.xiaopeng.xmart.cargallery.presenter.ListPresenter, com.xiaopeng.xmart.cargallery.presenter.AlbumPresenter
    public void requestDVRMediaData(final String rootdir) {
        if (this.mIsLoading) {
            CameraLog.d(TAG, "Current is loading", false);
            return;
        }
        this.mIsLoading = true;
        ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.xiaopeng.xmart.cargallery.presenter.-$$Lambda$DVRListPresenter$nU7gNMx6QXpUYqW-TBqgjHVC418
            @Override // java.lang.Runnable
            public final void run() {
                DVRListPresenter.this.lambda$requestDVRMediaData$1$DVRListPresenter(rootdir);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: requestDVRMediaDataInner */
    public void lambda$requestDVRMediaData$1$DVRListPresenter(String dvrDir) {
        CameraLog.i(TAG, "requestDVRMediaDataInner :" + dvrDir, false);
        List<BaseItem> resultList = new ArrayList<>();
        List<BaseItem> dataList = this.mModel.getDVRMediaDataList(dvrDir);
        if (dataList != null) {
            String dateTakenKey = null;
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                BaseItem item = dataList.get(i);
                item.setSelected(false);
                String currentDateTakenKey = item.getDateTakenKey();
                if (dateTakenKey == null || !dateTakenKey.equals(currentDateTakenKey)) {
                    dateTakenKey = currentDateTakenKey;
                    resultList.add(new DateCategoryItem(item.getDateTaken()));
                }
                resultList.add(item);
            }
        }
        if (resultList.size() > 0) {
            resultList.add(new FooterItem());
        }
        this.mIsLoading = false;
        int gearLevel = this.mIVcuController.getGearLevel();
        CameraLog.d(TAG, "gearLevel: " + gearLevel, false);
        if (gearLevel == 4) {
            notifyDataLoaded(resultList, -1);
        }
    }

    public String getDVRDir() {
        return this.mDVRDir;
    }

    public boolean isCarControllerInited() {
        CameraLog.d(TAG, "mCarControllerInited:" + this.mCarControllerInited);
        return this.mCarControllerInited;
    }

    public void setOutputMode(int mode) {
        if (this.mCiuController != null) {
            try {
                CameraLog.d(TAG, "setOutputMode, mode : " + mode, false);
                this.mCiuController.setVideoOutputMode(mode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setOutputAVM() {
        setOutputMode(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBusGearChanged(IVcuController.CarGearLevelEventMsg msg) {
        if (msg == null) {
            CameraLog.e(TAG, "onReceiveEventBusGearChanged, msg is null", false);
            return;
        }
        CameraLog.d(TAG, "CarService VCU CarGearLevelEventMsg = " + msg.toString(), false);
        int gearLevel = msg.getData().intValue();
        if (gearLevel != 4) {
            this.mDVRDir = null;
            switchDvrRecordMode();
        }
        this.mIAlbumView.onGearChanged(gearLevel);
        if (gearLevel == 3) {
            setOutputAVM();
        }
    }
}
