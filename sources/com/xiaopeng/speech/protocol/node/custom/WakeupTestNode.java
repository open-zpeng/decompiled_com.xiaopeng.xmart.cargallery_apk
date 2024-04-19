package com.xiaopeng.speech.protocol.node.custom;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class WakeupTestNode extends SpeechNode<WakeupTestListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onVADBeginSpeech(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((WakeupTestListener) obj).onVADBeginSpeech();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVADEndSpeech(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((WakeupTestListener) obj).onVADEndSpeech();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWakeupSucced(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((WakeupTestListener) obj).onWakeupSucced(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWakeupFailed(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((WakeupTestListener) obj).onWakeupFailed(data);
            }
        }
    }
}
