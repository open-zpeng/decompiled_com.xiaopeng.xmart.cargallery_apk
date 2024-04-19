package com.xiaopeng.speech.protocol.query.appstore;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class AppStoreQuery extends SpeechQuery<IAppStoreCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int getOpenStatus(String event, String data) {
        return ((IAppStoreCaller) this.mQueryCaller).getOpenStatus(data);
    }
}
