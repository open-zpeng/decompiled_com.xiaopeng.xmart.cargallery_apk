package com.xiaopeng.speech.protocol.query.appstore;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryAppStoreEvent;
/* loaded from: classes.dex */
public class AppStoreQuery_Processor implements IQueryProcessor {
    private AppStoreQuery mTarget;

    public AppStoreQuery_Processor(AppStoreQuery target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case 305415920:
                if (event.equals(QueryAppStoreEvent.GET_OPEN_STATUS)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getOpenStatus(event, data));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryAppStoreEvent.GET_OPEN_STATUS};
    }
}
