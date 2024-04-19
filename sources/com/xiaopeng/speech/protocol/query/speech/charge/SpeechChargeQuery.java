package com.xiaopeng.speech.protocol.query.speech.charge;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class SpeechChargeQuery extends SpeechQuery<ISpeechChargeQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getAddedElectricity(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getAddedElectricity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargingError(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargingError();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargingGunStatus(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargingGunStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargeStatus(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargeStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMileageNumber(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getMileageNumber();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWltpMileageNumber(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getWltpMileageNumber();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCltcMileageNumber(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getCltcMieageNumber();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getEnduranceMileageMode(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getEnduranceMileageMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getElectricityPercent(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getElectricityPercent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getColdWarningTips(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getColdWarningTips();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getHvacConsume(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getHvacConsume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getBatteryWarmingStatus(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getBatteryWarmingStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getACChargingCurrent(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getACChargingCurrent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getACChargingVolt(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getACChargingVolt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getDCChargingCurrent(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getDCChargingCurrent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getDCChargingVolt(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getDCChargingVolt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getACInputStatus(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getACInputStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getAverageVehConsume(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getAverageVehConsume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getChargingMaxSoc(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargingMaxSoc();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getSOH(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getSOH();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getChargeGunLockSt(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargeGunLockSt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getChargeLimitSoc(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getChargeLimitSoc();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getBatteryCoolStatus(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getBatteryCoolStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getAverageVehConsume100km(String event, String data) {
        return ((ISpeechChargeQueryCaller) this.mQueryCaller).getAverageVehConsume100km();
    }
}
