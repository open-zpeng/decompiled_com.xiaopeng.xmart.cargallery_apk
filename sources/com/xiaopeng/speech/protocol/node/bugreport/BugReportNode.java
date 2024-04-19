package com.xiaopeng.speech.protocol.node.bugreport;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class BugReportNode extends SpeechNode<BugReportListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onBugReportBegin(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((BugReportListener) obj).onBugReportBegin();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBugReportEnd(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        BugReportEndValue bugReportEndValue = BugReportEndValue.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((BugReportListener) obj).onBugReportEnd(bugReportEndValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBugReportVolume(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((BugReportListener) obj).onBugReportVolume(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBugReportEndtts(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((BugReportListener) obj).onBugReportEndtts();
            }
        }
    }
}
