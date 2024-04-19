package com.xiaopeng.speech.protocol.node.custom;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class WakeupWordSettingNode extends SpeechNode<WakeupWordSettingListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onManualInput(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((WakeupWordSettingListener) obj).onManualInput(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSettingDone(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((WakeupWordSettingListener) obj).onSettingDone(data);
            }
        }
    }
}
