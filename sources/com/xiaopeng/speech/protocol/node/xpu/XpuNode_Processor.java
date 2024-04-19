package com.xiaopeng.speech.protocol.node.xpu;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.XpuEvent;
/* loaded from: classes.dex */
public class XpuNode_Processor implements ICommandProcessor {
    private XpuNode mTarget;

    public XpuNode_Processor(XpuNode target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -47958497:
                if (event.equals(XpuEvent.XPU_VOICE_CHANGE_LANE)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.laneChange(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{XpuEvent.XPU_VOICE_CHANGE_LANE};
    }
}
