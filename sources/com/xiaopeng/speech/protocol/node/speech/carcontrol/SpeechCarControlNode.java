package com.xiaopeng.speech.protocol.node.speech.carcontrol;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class SpeechCarControlNode extends SpeechNode<SpeechCarControlListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseSoc(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechCarControlListener) obj).onCloseDriveMileIncrease();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenSoc(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechCarControlListener) obj).onOpenDriveMileIncrease();
            }
        }
    }

    protected void onRiseSpeaker(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechCarControlListener) obj).onOpenLoudspeaker();
            }
        }
    }
}
