package com.xiaopeng.xmart.cargallery.model.transfer.send;
/* loaded from: classes11.dex */
public interface Transferable {
    void finish() throws Exception;

    void init() throws Exception;

    void parseBody() throws Exception;

    void parseHeader() throws Exception;

    void parseThumbnail() throws Exception;
}
