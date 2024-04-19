package com.xiaopeng.speech.protocol.node.widget;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.WidgetEvent;
/* loaded from: classes.dex */
public class WidgetNode_Processor implements ICommandProcessor {
    private WidgetNode mTarget;

    public WidgetNode_Processor(WidgetNode target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -691162900:
                if (event.equals(WidgetEvent.AC_WIDGET_ON)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onAcWidgetOn(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{WidgetEvent.AC_WIDGET_ON};
    }
}
