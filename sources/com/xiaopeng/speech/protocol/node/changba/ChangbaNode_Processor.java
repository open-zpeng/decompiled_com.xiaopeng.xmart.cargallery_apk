package com.xiaopeng.speech.protocol.node.changba;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.ChangbaEvent;
/* loaded from: classes.dex */
public class ChangbaNode_Processor implements ICommandProcessor {
    private ChangbaNode mTarget;

    public ChangbaNode_Processor(ChangbaNode target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -2072002904:
                if (event.equals(ChangbaEvent.MUSIC_CHANGBA_SEARCH)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onMusicChangbaSearch(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{ChangbaEvent.MUSIC_CHANGBA_SEARCH};
    }
}
