package com.xiaopeng.speech.protocol.query.aiassistant;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.QueryAiAssistantEvent;
/* loaded from: classes.dex */
public class AiAssistantQuery_Processor implements IQueryProcessor {
    private AiAssistantQuery mTarget;

    public AiAssistantQuery_Processor(AiAssistantQuery target) {
        this.mTarget = target;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -497888784:
                if (event.equals(QueryAiAssistantEvent.GUI_AI_VIDEO_OPEN)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getAiVideoOpenStatus(event, data));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryAiAssistantEvent.GUI_AI_VIDEO_OPEN};
    }
}
