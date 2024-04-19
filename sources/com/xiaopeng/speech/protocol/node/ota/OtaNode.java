package com.xiaopeng.speech.protocol.node.ota;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class OtaNode extends SpeechNode<OtaListener> {
    private static final String TAG = "OtaNode";

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenOtaPage(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OtaListener) obj).onOpenOtaPage();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenReservationPage(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OtaListener) obj).onOpenReservationPage();
            }
        }
    }
}
