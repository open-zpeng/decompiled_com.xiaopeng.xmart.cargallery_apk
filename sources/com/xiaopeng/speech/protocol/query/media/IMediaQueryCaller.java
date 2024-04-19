package com.xiaopeng.speech.protocol.query.media;

import com.xiaopeng.speech.IQueryCaller;
/* loaded from: classes.dex */
public interface IMediaQueryCaller extends IQueryCaller {
    default String getMediaInfo() {
        return null;
    }
}
