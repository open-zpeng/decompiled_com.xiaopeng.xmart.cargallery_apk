package com.xiaopeng.xmart.cargallery.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.ciu.CarCiuManager;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.carmanager.BaseCarController;
import com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper;
import com.xiaopeng.xmart.cargallery.carmanager.listener.ICiuController;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes4.dex */
public class CiuController extends BaseCarController<CarCiuManager> implements ICiuController {
    private static final String TAG = "CiuController";
    private final CarCiuManager.CarCiuEventCallback mCarVcuEventCallback = new CarCiuManager.CarCiuEventCallback() { // from class: com.xiaopeng.xmart.cargallery.carmanager.impl.CiuController.1
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            CameraLog.d(CiuController.TAG, "onChangeEvent: " + carPropertyValue);
            CiuController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int zone) {
            CameraLog.e(CiuController.TAG, "onErrorEvent: " + propertyId, false);
        }
    };

    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        List<Integer> propertyIds = new ArrayList<>();
        propertyIds.add(557852696);
        propertyIds.add(557852675);
        propertyIds.add(557852689);
        propertyIds.add(557852673);
        return propertyIds;
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    protected void initPropertyTypeMap() {
        this.mPropertyTypeMap.put(557852696, ICiuController.CiuDVRStatusEventMsg.class);
        this.mPropertyTypeMap.put(557852675, ICiuController.CiuSdStEventMsg.class);
        this.mPropertyTypeMap.put(557852689, ICiuController.CiuVideoOutputEventMsg.class);
        this.mPropertyTypeMap.put(557852673, ICiuController.CiuDVRModeEventMsg.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Type inference failed for: r1v5, types: [C, android.car.hardware.ciu.CarCiuManager] */
    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        CameraLog.d(TAG, "Init start");
        try {
            this.mCarManager = (CarCiuManager) carClient.getCarManager(CarClientWrapper.XP_CIU_SERVICE);
            if (this.mCarManager != 0) {
                ((CarCiuManager) this.mCarManager).registerPropCallback(this.mPropertyIds, this.mCarVcuEventCallback);
            }
        } catch (CarNotConnectedException e) {
            CameraLog.e(TAG, e.getMessage(), false);
        }
        CameraLog.d(TAG, "Init end");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    public void disconnect() {
        if (this.mCarManager != 0) {
            try {
                ((CarCiuManager) this.mCarManager).unregisterPropCallback(this.mPropertyIds, this.mCarVcuEventCallback);
            } catch (CarNotConnectedException e) {
                CameraLog.e(TAG, e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.ICiuController
    public void setDvrMode(int mode) throws Exception {
        ((CarCiuManager) this.mCarManager).setDvrMode(mode);
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.ICiuController
    public int getDvrMode() throws Exception {
        return ((CarCiuManager) this.mCarManager).getDvrMode();
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.ICiuController
    public int getSdStatus() throws Exception {
        return ((CarCiuManager) this.mCarManager).getSdStatus();
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.ICiuController
    public void setVideoOutputMode(int mode) throws Exception {
        ((CarCiuManager) this.mCarManager).setVideoOutputMode(mode);
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.ICiuController
    public int getDVRStatus() throws Exception {
        return ((CarCiuManager) this.mCarManager).getDvrStatus();
    }
}
