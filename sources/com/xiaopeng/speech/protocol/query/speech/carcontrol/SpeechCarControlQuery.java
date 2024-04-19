package com.xiaopeng.speech.protocol.query.speech.carcontrol;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class SpeechCarControlQuery extends SpeechQuery<ISpeechCarControlQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getRearFogLamp(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getRearFogLamp();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getNearLampState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getNearLampState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getLocationLampState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLocationLampState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getFarLampState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getFarLampState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHeadLampGroup(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getHeadLampGroup();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getInternalLight(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getInternalLight();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getEmergencyBrakeWarning(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getEmergencyBrakeWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getATWSState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getATWSState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getOled(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getOled();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLightMeHome(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLightMeHome();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getDriveAutoLock(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDriveAutoLock();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getParkingAutoUnlock(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getParkingAutoUnlock();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getDoorLockState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDoorLockState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getTrunk(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getTrunk();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getChairWelcomeMode(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getChairWelcomeMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getElectricSeatBelt(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getElectricSeatBelt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getRearSeatBeltWarning(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getRearSeatBeltWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getUnlockResponse(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getUnlockResponse();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getDoorsState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDoorsState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float[] getWindowsState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getWindowsState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getChairDirection(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getChairDirection();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getSeatErrorState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSeatErrorState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getChairLocationValue(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getChairLocationValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getWelcomeModeBackStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getWelcomeModeBackStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getDrivingMode(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDrivingMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSteeringWheelEPS(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSteeringWheelEPS();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getIcmAlarmVolume(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getIcmAlarmVolume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getSpeedLimitWarningSwitch(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSpeedLimitWarningSwitch();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSpeedLimitWarningValue(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSpeedLimitWarningValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getMeterMileageA(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getMeterMileageA();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getMeterMileageB(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getMeterMileageB();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getDriveTotalMileage(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDriveTotalMileage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getLastChargeMileage(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLastChargeMileage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getLastStartUpMileage(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLastStartUpMileage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getFrontCollisionSecurity(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getFrontCollisionSecurity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getIntelligentSpeedLimit(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getIntelligentSpeedLimit();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLaneChangeAssist(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLaneChangeAssist();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSideReversingWarning(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSideReversingWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLaneDepartureWarning(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLaneDepartureWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBlindAreaDetectionWarning(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getBlindAreaDetectionWarning();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getRadarWarningVoiceStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getRadarWarningVoiceStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getEnergyRecycleLevel(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getEnergyRecycleLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getShiftStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getShiftStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getDriverSeatStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getDriverSeatStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getSteerWheelRotationAngle(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getSteerWheelRotationAngle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getCarSpeed(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getCarSpeed();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getIcmConnectionState(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getIcmConnectionState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBCMIgStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getBCMIgStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getICMWindBlowMode(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getICMWindBlowMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getICMDriverTempValue(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getICMDriverTempValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getICMWindLevel(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getICMWindLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCarTrip(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).isCarTrip();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWiperInterval(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getWiperInterval();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float[] getTirePressureAll(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getTirePressureAll();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getAllTirePressureWarnings(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getAllTirePressureWarnings();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getACCStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getACCStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLCCStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLCCStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindowLockStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getWindowLockStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLowSocStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getLowSocStatus();
    }

    protected int getRiseSpeakerStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getRiseSpeakerStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCruiseControlStatus(String event, String data) {
        return ((ISpeechCarControlQueryCaller) this.mQueryCaller).getCruiseActive();
    }
}
