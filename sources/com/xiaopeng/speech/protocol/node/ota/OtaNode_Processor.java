package com.xiaopeng.speech.protocol.node.ota;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.OtaEvent;
/* loaded from: classes.dex */
public class OtaNode_Processor implements ICommandProcessor {
    private OtaNode mTarget;

    public OtaNode_Processor(OtaNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case 478800373:
                if (event.equals(OtaEvent.OTA_PAGE_OPEN)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1009711693:
                if (event.equals(OtaEvent.OTARESERVATION_PAGE_OPEN)) {
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
                this.mTarget.onOpenOtaPage(event, data);
                return;
            case 1:
                this.mTarget.onOpenReservationPage(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{OtaEvent.OTA_PAGE_OPEN, OtaEvent.OTARESERVATION_PAGE_OPEN};
    }
}
