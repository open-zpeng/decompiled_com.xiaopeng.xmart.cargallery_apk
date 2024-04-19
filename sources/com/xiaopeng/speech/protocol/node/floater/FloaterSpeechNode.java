package com.xiaopeng.speech.protocol.node.floater;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.WindowAnimState;
/* loaded from: classes.dex */
public class FloaterSpeechNode extends SpeechNode<FloaterSpeechListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseWindow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        int from = Integer.parseInt(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FloaterSpeechListener) obj).onCloseWindow(from);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSetAnimState(String event, String data) {
        WindowAnimState windowAnimState = WindowAnimState.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FloaterSpeechListener) obj).onSetAnimState(windowAnimState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onXiaopExpression(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FloaterSpeechListener) obj).onXiaopExpression(data);
            }
        }
    }
}
