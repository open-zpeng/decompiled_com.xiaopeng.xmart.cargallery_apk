package com.xiaopeng.speech.protocol.query.media;

import com.xiaopeng.speech.SpeechQuery;
/* loaded from: classes.dex */
public class MediaQuery extends SpeechQuery<IMediaQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public String getMediaInfo(String event, String data) {
        return ((IMediaQueryCaller) this.mQueryCaller).getMediaInfo();
    }
}
