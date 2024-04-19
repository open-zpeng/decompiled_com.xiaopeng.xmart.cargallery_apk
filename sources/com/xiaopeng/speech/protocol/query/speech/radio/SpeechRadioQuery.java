package com.xiaopeng.speech.protocol.query.speech.radio;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class SpeechRadioQuery extends SpeechQuery<ISpeechRadioQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public String getAudioDspStatus(String event, String data) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getAudioDspStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getRadioStatus(String event, String data) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getRadioStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRadioVolumeAutoFocus(String event, String data) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getRadioVolumeAutoFocus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getRadioFrequency(String event, String data) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getRadioFrequency();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int[] getAudioMode(String event, String data) {
        return ((ISpeechRadioQueryCaller) this.mQueryCaller).getAudioMode();
    }
}
