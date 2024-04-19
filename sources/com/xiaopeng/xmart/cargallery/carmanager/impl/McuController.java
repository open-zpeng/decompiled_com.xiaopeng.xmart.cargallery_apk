package com.xiaopeng.xmart.cargallery.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.mcu.CarMcuManager;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.carmanager.BaseCarController;
import com.xiaopeng.xmart.cargallery.carmanager.CarClientWrapper;
import com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes4.dex */
public class McuController extends BaseCarController<CarMcuManager> implements IMcuController {
    private static final String TAG = "McuController";
    private final CarMcuManager.CarMcuEventCallback mCarMcuEventCallback = new CarMcuManager.CarMcuEventCallback() { // from class: com.xiaopeng.xmart.cargallery.carmanager.impl.McuController.1
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            CameraLog.d(McuController.TAG, "onChangeEvent: " + carPropertyValue);
            McuController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int zone) {
            CameraLog.e(McuController.TAG, "onErrorEvent: " + propertyId, false);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.car.hardware.mcu.CarMcuManager, C] */
    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        try {
            this.mCarManager = (CarMcuManager) carClient.getCarManager(CarClientWrapper.XP_MCU_SERVICE);
            if (this.mCarManager != 0) {
                ((CarMcuManager) this.mCarManager).registerPropCallback(this.mPropertyIds, this.mCarMcuEventCallback);
            }
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        List<Integer> propertyIds = new ArrayList<>();
        propertyIds.add(557847561);
        return propertyIds;
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    protected void initPropertyTypeMap() {
        this.mPropertyTypeMap.put(557847561, IMcuController.IGStatusChangeEvent.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xmart.cargallery.carmanager.BaseCarController
    public void disconnect() {
        try {
            ((CarMcuManager) this.mCarManager).unregisterPropCallback(this.mPropertyIds, this.mCarMcuEventCallback);
        } catch (CarNotConnectedException e) {
            CameraLog.e(TAG, e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController
    public String getHardwareCarType() throws Exception {
        return ((CarMcuManager) this.mCarManager).getHardwareCarType();
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController
    public String getHardwareCarStage() throws Exception {
        return ((CarMcuManager) this.mCarManager).getHardwareCarStage();
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController
    public int getHardwareVersion() {
        try {
            return ((CarMcuManager) this.mCarManager).getHardwareVersion();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController
    public void setFlash(boolean on) throws Exception {
        ((CarMcuManager) this.mCarManager).setFlash(on ? 1 : 0);
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController
    public int getCiuState() throws Exception {
        int ciuStatus = ((CarMcuManager) this.mCarManager).getCiuState();
        CameraLog.d(TAG, "getCiuState: " + ciuStatus);
        return ciuStatus;
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController
    public int getOcuState() throws Exception {
        int ocuStatus = ((CarMcuManager) this.mCarManager).getOcuState();
        CameraLog.d(TAG, "getOcuState: " + ocuStatus);
        return ocuStatus;
    }

    @Override // com.xiaopeng.xmart.cargallery.carmanager.listener.IMcuController
    public String getXpCduType() throws Exception {
        return ((CarMcuManager) this.mCarManager).getXpCduType();
    }
}
