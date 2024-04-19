package com.xiaopeng.speech.protocol.query.speech.ac;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class SpeechACQuery extends SpeechQuery<ISpeechACQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHvacPowerMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacPowerMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getHvacTempDriverValue(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacTempDriverValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getHvacTempPsnValue(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacTempPsnValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHvacTempSyncMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacTempSyncMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHvacAutoMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacAutoMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHvacAutoModeBlowLevel(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacAutoModeBlowLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHvacCirculationMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacCirculationMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHvacFrontDefrostMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacFrontDefrostMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHvacWindBlowMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacWindBlowMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHvacWindSpeedLevel(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacWindSpeedLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getHvacQualityPurgeMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacQualityPurgeMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHvacQualityInnerPM25Value(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacQualityInnerPM25Value();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHvacQualityOutsideStatus(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacQualityOutsideStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHvacQualityOutsideLevel(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacQualityOutsideLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getBCMBackDefrostMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getBCMBackDefrostMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getBCMBackMirrorHeatMode(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getBCMBackMirrorHeatMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBCMSeatHeatLevel(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getBCMSeatHeatLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBCMSeatBlowLevel(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getBCMSeatBlowLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public double getHvacInnerTemp(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getHvacInnerTemp();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPsnSeatHeatLv(String event, String data) {
        return ((ISpeechACQueryCaller) this.mQueryCaller).getPsnSeatHeatLv();
    }
}
