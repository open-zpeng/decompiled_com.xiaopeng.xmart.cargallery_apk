package com.xiaopeng.speech.protocol.node.asr;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.jarvisproto.AsrEvent;
import com.xiaopeng.speech.protocol.node.app.AppListener;
/* loaded from: classes.dex */
public class AsrNode extends SpeechNode<AppListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onAsrEvent(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (!TextUtils.isEmpty(data)) {
            AsrEvent asrEvent = AsrEvent.fromJson(data);
            if (listenerList != null) {
                for (int i = 0; i < listenerList.length; i++) {
                    ((AsrListener) listenerList[i]).onAsrEvent(asrEvent.mEvent);
                    ((AsrListener) listenerList[i]).onAsrEvent(asrEvent);
                }
            }
        }
    }
}
