package com.xiaopeng.speech.protocol.node.custom;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.WakeupTestEvent;
/* loaded from: classes.dex */
public class WakeupWordSettingNode_Processor implements ICommandProcessor {
    private WakeupWordSettingNode mTarget;

    public WakeupWordSettingNode_Processor(WakeupWordSettingNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1752205390:
                if (event.equals(WakeupTestEvent.WAKEUP_WORD_SETTING_DONE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -376932620:
                if (event.equals(WakeupTestEvent.WAKEUP_WORD_MANUAL_INPUT)) {
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
                this.mTarget.onManualInput(event, data);
                return;
            case 1:
                this.mTarget.onSettingDone(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{WakeupTestEvent.WAKEUP_WORD_MANUAL_INPUT, WakeupTestEvent.WAKEUP_WORD_SETTING_DONE};
    }
}
