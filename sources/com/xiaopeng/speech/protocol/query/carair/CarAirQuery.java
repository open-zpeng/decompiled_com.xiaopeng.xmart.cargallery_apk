package com.xiaopeng.speech.protocol.query.carair;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class CarAirQuery extends SpeechQuery<ICarAirCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportAutoOff(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportAutoOff();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportDemistFoot(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportDemistFoot();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindMax(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getWindMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindMin(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getWindMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getTempMax(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getTempMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getTempMin(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getTempMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportDecimalValue(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportDecimalValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportPurifier(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportPurifier();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportPm25(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportPm25();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAirLevel(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getAirLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportSeatHeat(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportSeatHeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportPsnSeatHeat(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportPsnSeatHeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHeatMax(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getHeatMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHeatMin(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getHeatMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportSeatBlow(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportSeatBlow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSeatBlowMax(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getSeatBlowMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSeatBlowMin(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getSeatBlowMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isFastFridge(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isFastFridge();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isFreshAir(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isFreshAir();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportTempDual(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportTempDual();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getWindsStatus(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getWindsStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int isSupportMpQuery(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportOutSidePm();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindMode(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getCurrWindMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int isSupportMirrorHeat(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportMirrorHeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getOutSidePmQuality(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getOutSidePmQuality();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getDriWindDirectionMode(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getDriWindDirectionMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPsnWindDirectionMode(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getPsnWindDirectionMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAcPanelStatus(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getAcPanelStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPmLevelQuality(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getOutSidePmLevelQuality();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getIntelligentPassengerStatus(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getIntelligentPassengerStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSupportRearSeatHeat(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isSupportRearSeatHeat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLeftRearSeatHeatLevel(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getLeftRearSeatHeatLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRightRearSeatHeatLevel(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).getRightRearSeatHeatLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAcSupportPerfume(String event, String data) {
        return ((ICarAirCaller) this.mQueryCaller).isAcSupportPerfume();
    }
}
