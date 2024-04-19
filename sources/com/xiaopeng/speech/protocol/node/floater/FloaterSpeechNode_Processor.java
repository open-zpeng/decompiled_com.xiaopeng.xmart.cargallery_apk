package com.xiaopeng.speech.protocol.node.floater;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.FloaterSpeechEvent;
/* loaded from: classes.dex */
public class FloaterSpeechNode_Processor implements ICommandProcessor {
    private FloaterSpeechNode mTarget;

    public FloaterSpeechNode_Processor(FloaterSpeechNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1829252700:
                if (event.equals(FloaterSpeechEvent.XIAOP_EXPRESSION)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1493310260:
                if (event.equals(FloaterSpeechEvent.SET_ANIM_STATE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1920721242:
                if (event.equals(FloaterSpeechEvent.CLOSE_WINDOW)) {
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
                this.mTarget.onCloseWindow(event, data);
                return;
            case 1:
                this.mTarget.onSetAnimState(event, data);
                return;
            case 2:
                this.mTarget.onXiaopExpression(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{FloaterSpeechEvent.CLOSE_WINDOW, FloaterSpeechEvent.SET_ANIM_STATE, FloaterSpeechEvent.XIAOP_EXPRESSION};
    }
}
