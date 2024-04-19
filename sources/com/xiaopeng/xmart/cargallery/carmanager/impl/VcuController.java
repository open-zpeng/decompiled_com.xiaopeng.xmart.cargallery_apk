package com.xiaopeng.xmart.cargallery.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.vcu.CarVcuManager;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.carmanager.BaseCarController;
import com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper;
import com.xiaopeng.xmart.cargallery.carmanager.listener.IVcuController;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes4.dex */
public class VcuController extends BaseCarController<CarVcuManager> implements IVcuController {
    private static final String TAG = "VcuController";
    private final CarVcuManager.CarVcuEventCallback mCarVcuEventCallback = new CarVcuManager.CarVcuEventCallback() { // from class: com.xiaopeng.xmart.cargallery.carmanager.impl.VcuController.1
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            if (carPropertyValue.getPropertyId() == 557847045) {
                carPropertyValue.getValue();
            }
            CameraLog.d(VcuController.TAG, "onChangeEvent: " + carPropertyValue);
            VcuController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int zone) {
            CameraLog.e(VcuController.TAG, "onErrorEvent: " + propertyId, false);
        }
    };

    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        List<Integer> propertyIds = new ArrayList<>();
        propertyIds.add(557847045);
        return propertyIds;
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    protected void initPropertyTypeMap() {
        this.mPropertyTypeMap.put(557847045, IVcuController.CarGearLevelEventMsg.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Type inference failed for: r1v5, types: [C, android.car.hardware.vcu.CarVcuManager] */
    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        CameraLog.d(TAG, "Init start");
        try {
            this.mCarManager = (CarVcuManager) carClient.getCarManager(CarClientWrapper.XP_VCU_SERVICE);
            if (this.mCarManager != 0) {
                ((CarVcuManager) this.mCarManager).registerPropCallback(this.mPropertyIds, this.mCarVcuEventCallback);
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
                ((CarVcuManager) this.mCarManager).unregisterPropCallback(this.mPropertyIds, this.mCarVcuEventCallback);
            } catch (CarNotConnectedException e) {
                CameraLog.e(TAG, e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IVcuController
    public int getGearLevel() {
        try {
            int value = getIntProperty(557847045);
            return value;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IVcuController
    public float getCarSpeed() throws Exception {
        return ((CarVcuManager) this.mCarManager).getRawCarSpeed();
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IVcuController
    public int getStallState() {
        try {
            int result = ((CarVcuManager) this.mCarManager).getDisplayGearLevel();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
