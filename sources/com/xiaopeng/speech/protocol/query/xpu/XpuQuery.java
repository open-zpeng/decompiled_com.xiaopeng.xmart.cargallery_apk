package com.xiaopeng.speech.protocol.query.xpu;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class XpuQuery extends SpeechQuery<IXpuCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getAutoPilotStatus(String event, String data) {
        return ((IXpuCaller) this.mQueryCaller).getAutoPilotStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getALCStatus(String event, String data) {
        return ((IXpuCaller) this.mQueryCaller).getALCStatus();
    }
}
