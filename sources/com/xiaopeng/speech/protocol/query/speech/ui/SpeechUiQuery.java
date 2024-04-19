package com.xiaopeng.speech.protocol.query.speech.ui;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class SpeechUiQuery extends SpeechQuery<ISpeechUiCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSuperDialogueWhitelist(String event, String data) {
        return ((ISpeechUiCaller) this.mQueryCaller).isSuperDialogueWhitelist();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSuperDialogueOpened(String event, String data) {
        return ((ISpeechUiCaller) this.mQueryCaller).isSuperDialogueOpened();
    }
}
