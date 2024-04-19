package com.xiaopeng.speech.protocol.node.error;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class ErrorNode extends SpeechNode<IErrorListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onErrorAsrResult(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((IErrorListener) obj).onErrorAsrResult();
            }
        }
    }
}
