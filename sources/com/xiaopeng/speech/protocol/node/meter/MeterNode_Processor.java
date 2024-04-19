package com.xiaopeng.speech.protocol.node.meter;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.MeterEvent;
/* loaded from: classes.dex */
public class MeterNode_Processor implements ICommandProcessor {
    private MeterNode mTarget;

    public MeterNode_Processor(MeterNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1943195853:
                if (event.equals(MeterEvent.SET_RIGHTT_CARD)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1464668575:
                if (event.equals(MeterEvent.DASHBOARD_LIGHTS_STATUS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 432832230:
                if (event.equals(MeterEvent.SET_LEFT_CARD)) {
                    c = 0;
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
                this.mTarget.setLeftCard(event, data);
                return;
            case 1:
                this.mTarget.setRightCard(event, data);
                return;
            case 2:
                this.mTarget.onDashboardLightsStatus(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{MeterEvent.SET_LEFT_CARD, MeterEvent.SET_RIGHTT_CARD, MeterEvent.DASHBOARD_LIGHTS_STATUS};
    }
}
