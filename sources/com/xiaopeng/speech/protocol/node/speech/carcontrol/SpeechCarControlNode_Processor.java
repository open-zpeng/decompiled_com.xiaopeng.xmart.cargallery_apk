package com.xiaopeng.speech.protocol.node.speech.carcontrol;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.SpeechCarControlCmdEvent;
/* loaded from: classes.dex */
public class SpeechCarControlNode_Processor implements ICommandProcessor {
    private SpeechCarControlNode mTarget;

    public SpeechCarControlNode_Processor(SpeechCarControlNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1772666300:
                if (event.equals(SpeechCarControlCmdEvent.CLOSE_CAR_CONTROL_SOC)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 81725566:
                if (event.equals(SpeechCarControlCmdEvent.OPEN_CAR_CONTROL_SOC)) {
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
                this.mTarget.onCloseSoc(event, data);
                return;
            case 1:
                this.mTarget.onOpenSoc(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{SpeechCarControlCmdEvent.CLOSE_CAR_CONTROL_SOC, SpeechCarControlCmdEvent.OPEN_CAR_CONTROL_SOC};
    }
}
