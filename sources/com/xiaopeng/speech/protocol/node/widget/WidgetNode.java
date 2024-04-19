package com.xiaopeng.speech.protocol.node.widget;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class WidgetNode extends SpeechNode<WidgetListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onAcWidgetOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((WidgetListener) obj).onAcWidgetOn();
            }
        }
    }
}
