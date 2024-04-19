package com.xiaopeng.speech.protocol.query.speech.carstatus;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class SpeechCarStatusQuery extends SpeechQuery<ISpeechCarstatusQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentMode(String event, String data) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getCurrentMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getAcPowerStatus(String event, String data) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcPowerStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getAcQualityPurgeStatus(String event, String data) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcQualityPurgeStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getAcTempDriverValue(String event, String data) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcTempDriverValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getAcTempPsnValue(String event, String data) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcTempPsnValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAcWindSpeedLevel(String event, String data) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcWindSpeedLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAcWindBlowMode(String event, String data) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcWindBlowMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAcCirculationMode(String event, String data) {
        return ((ISpeechCarstatusQueryCaller) this.mQueryCaller).getAcCirculationMode();
    }
}
