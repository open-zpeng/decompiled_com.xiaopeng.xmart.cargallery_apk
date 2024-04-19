package com.xiaopeng.speech.protocol.query.xpu;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.QueryXpuEvent;
/* loaded from: classes.dex */
public class XpuQuery_Processor implements IQueryProcessor {
    private XpuQuery mTarget;

    public XpuQuery_Processor(XpuQuery target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1809695763:
                if (event.equals(QueryXpuEvent.XPU_IS_ON_AUTOPILOT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -603903460:
                if (event.equals(QueryXpuEvent.XPU_IS_ON_ALC)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getAutoPilotStatus(event, data));
            case 1:
                return Integer.valueOf(this.mTarget.getALCStatus(event, data));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryXpuEvent.XPU_IS_ON_AUTOPILOT, QueryXpuEvent.XPU_IS_ON_ALC};
    }
}
