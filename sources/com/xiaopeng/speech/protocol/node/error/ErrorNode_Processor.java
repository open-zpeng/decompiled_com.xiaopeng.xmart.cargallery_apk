package com.xiaopeng.speech.protocol.node.error;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.ErrorEvent;
/* loaded from: classes.dex */
public class ErrorNode_Processor implements ICommandProcessor {
    private ErrorNode mTarget;

    public ErrorNode_Processor(ErrorNode target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case 780227936:
                if (event.equals(ErrorEvent.ERROR_ASR_RESULT)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onErrorAsrResult(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{ErrorEvent.ERROR_ASR_RESULT};
    }
}
