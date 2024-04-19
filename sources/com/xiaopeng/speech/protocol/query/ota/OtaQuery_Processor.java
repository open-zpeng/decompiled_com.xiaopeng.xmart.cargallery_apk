package com.xiaopeng.speech.protocol.query.ota;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.OtaEvent;
/* loaded from: classes.dex */
public class OtaQuery_Processor implements IQueryProcessor {
    private OtaQuery mTarget;

    public OtaQuery_Processor(OtaQuery target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case 1851114339:
                if (event.equals(OtaEvent.IS_LATEST_OTA_VERSION)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return Boolean.valueOf(this.mTarget.isLatestVersion(event, data));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{OtaEvent.IS_LATEST_OTA_VERSION};
    }
}
