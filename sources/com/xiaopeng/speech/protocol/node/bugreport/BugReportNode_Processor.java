package com.xiaopeng.speech.protocol.node.bugreport;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.BugReportEvent;
/* loaded from: classes.dex */
public class BugReportNode_Processor implements ICommandProcessor {
    private BugReportNode mTarget;

    public BugReportNode_Processor(BugReportNode target) {
        this.mTarget = target;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String event, String data) {
        char c;
        switch (event.hashCode()) {
            case -1272286452:
                if (event.equals(BugReportEvent.BUG_REPORT_END)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1272269173:
                if (event.equals(BugReportEvent.BUG_REPORT_ENDTTS)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1265421229:
                if (event.equals(BugReportEvent.BUG_REPORT_BEGIN)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -508144235:
                if (event.equals(BugReportEvent.BUG_REPORT_VOLUME)) {
                    c = 2;
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
                this.mTarget.onBugReportBegin(event, data);
                return;
            case 1:
                this.mTarget.onBugReportEnd(event, data);
                return;
            case 2:
                this.mTarget.onBugReportVolume(event, data);
                return;
            case 3:
                this.mTarget.onBugReportEndtts(event, data);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{BugReportEvent.BUG_REPORT_BEGIN, BugReportEvent.BUG_REPORT_END, BugReportEvent.BUG_REPORT_VOLUME, BugReportEvent.BUG_REPORT_ENDTTS};
    }
}
