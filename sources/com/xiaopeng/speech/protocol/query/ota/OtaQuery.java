package com.xiaopeng.speech.protocol.query.ota;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class OtaQuery extends SpeechQuery<IOtaCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLatestVersion(String event, String data) {
        return ((IOtaCaller) this.mQueryCaller).isLatestVersion();
    }
}
