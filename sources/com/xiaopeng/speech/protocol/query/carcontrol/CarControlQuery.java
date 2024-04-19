package com.xiaopeng.speech.protocol.query.carcontrol;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class CarControlQuery extends SpeechQuery<ICarControlCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportCloseMirror(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportCloseMirror();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getMirrorStatus(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getMirrorStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportOpenTrunk(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getSupportOpenTrunk();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportCloseTrunk(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getSupportCloseTrunk();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindowStatus(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getWindowStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportEngryRecycle(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportEnergyRecycle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportSeat(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getSupportSeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportDrivingMode(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportDrivingMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportAtmosphere(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportAtmosphere();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isTirePressureNormal(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isTirePressureNormal();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportUnlockTrunk(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportUnlockTrunk();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportCloseLeftChargePort(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportControlChargePort(0, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportCloseRightChargePort(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportControlChargePort(1, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportOpenLeftChargePort(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportControlChargePort(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportOpenRightChargePort(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportControlChargePort(1, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportControlMirror(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSupportControlMirror();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLegHeight(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getLegHeight();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStatusCloseLeftChargePort(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getStatusChargePortControl(0, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStatusCloseRightChargePort(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getStatusChargePortControl(1, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStatusOpenLeftChargePort(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getStatusChargePortControl(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getStatusOpenRightChargePort(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getStatusChargePortControl(1, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAtmosphereBrightnessStatus(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getAtmosphereBrightnessStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAtmosphereColorStatus(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getAtmosphereColorStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int isSteeringModeAdjustable(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).isSteeringModeAdjustable();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getGuiPageOpenState(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getGuiPageOpenState(data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWiperInterval(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getWiperInterval();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSupportPsnSeat(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getSupportPsnSeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getExtraTrunkStatus(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getExtraTrunkStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getTrunkStatus(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getTrunkStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getDoorKeyValue(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getDoorKeyValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlLowSpeedAnalogSoundSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlLowSpeedAnalogSoundSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlXpedalSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlXpedalSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlSupportEnergyRecycleReason(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlSupportEnergyRecycleReason();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlScissorDoorLeftOpenSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlScissorDoorLeftOpenSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlScissorDoorCloseSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlScissorDoorRightOpenSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlScissorDoorLeftCloseSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlScissorDoorLeftCloseSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlScissorDoorRightCloseSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlScissorDoorRightCloseSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlScissorDoorLeftRunningSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlScissorDoorLeftRunningSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlScissorDoorRightRunningSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlScissorDoorRightRunningSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlElectricCurtainSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlElectricCurtainSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float[] getControlWindowsStateSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlWindowsStateSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlComfortableDrivingModeSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlComfortableDrivingModeSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getControlLampSignalSupport(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getControlLampSignalSupport();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getNgpStatus(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getNgpStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getVipChairStatus(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getVipChairStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCapsuleCurrentMode(String event, String data) {
        return ((ICarControlCaller) this.mQueryCaller).getCapsuleCurrentMode();
    }
}
