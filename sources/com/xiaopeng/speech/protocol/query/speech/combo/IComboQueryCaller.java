package com.xiaopeng.speech.protocol.query.speech.combo;

import com.xiaopeng.speech.IQueryCaller;
import com.xiaopeng.speech.jarvisproto.DMEnd;
/* loaded from: classes.dex */
public interface IComboQueryCaller extends IQueryCaller {
    default String enterUserMode(String modeType) {
        return DMEnd.REASON_NORMAL;
    }

    default String exitUserMode(String modeType) {
        return DMEnd.REASON_NORMAL;
    }

    default String checkEnterUserMode(String modeType) {
        return DMEnd.REASON_NORMAL;
    }

    default String getCurrentUserMode() {
        return DMEnd.REASON_NORMAL;
    }
}
