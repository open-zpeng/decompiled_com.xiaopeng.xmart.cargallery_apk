package com.xiaopeng.speech.protocol.node.asr;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.jarvisproto.AsrEvent;
/* loaded from: classes.dex */
public class AsrNode_Processor implements ICommandProcessor {
    private AsrNode mTarget;

    public AsrNode_Processor(AsrNode target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1570875613:
                if (event.equals(AsrEvent.EVENT)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onAsrEvent(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{AsrEvent.EVENT};
    }
}
