package com.xiaopeng.speech.protocol.node.wakeup;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.jarvisproto.WakeupStatus;
/* loaded from: classes.dex */
public class WakeupStatusNode_Processor implements ICommandProcessor {
    private WakeupStatusNode mTarget;

    public WakeupStatusNode_Processor(WakeupStatusNode target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -304962696:
                if (event.equals(WakeupStatus.EVENT)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onWakeupStatusChanged(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{WakeupStatus.EVENT};
    }
}
