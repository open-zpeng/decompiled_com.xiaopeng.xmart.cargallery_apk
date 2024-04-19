package com.xiaopeng.speech.protocol.query.speech.ui;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechQueryEvent;
/* loaded from: classes.dex */
public class SpeechUiQuery_Processor implements IQueryProcessor {
    private SpeechUiQuery mTarget;

    public SpeechUiQuery_Processor(SpeechUiQuery target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1584266840:
                if (event.equals(SpeechQueryEvent.IS_SUPERDIALOGUE_WHITELIST)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1417712984:
                if (event.equals(SpeechQueryEvent.IS_SUPERDIALOGUE_OPENED)) {
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
                return Boolean.valueOf(this.mTarget.isSuperDialogueWhitelist(event, data));
            case 1:
                return Boolean.valueOf(this.mTarget.isSuperDialogueOpened(event, data));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechQueryEvent.IS_SUPERDIALOGUE_WHITELIST, SpeechQueryEvent.IS_SUPERDIALOGUE_OPENED};
    }
}
