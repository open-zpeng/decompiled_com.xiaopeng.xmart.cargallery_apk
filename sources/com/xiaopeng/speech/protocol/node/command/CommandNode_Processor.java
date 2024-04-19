package com.xiaopeng.speech.protocol.node.command;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.CommandEvent;
/* loaded from: classes.dex */
public class CommandNode_Processor implements ICommandProcessor {
    private CommandNode mTarget;

    public CommandNode_Processor(CommandNode target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case 380439127:
                if (event.equals(CommandEvent.COMMAND_JOSN_POST)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onJsonPost(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{CommandEvent.COMMAND_JOSN_POST};
    }
}
