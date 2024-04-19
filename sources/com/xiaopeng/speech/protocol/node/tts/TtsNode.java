package com.xiaopeng.speech.protocol.node.tts;

import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class TtsNode extends SpeechNode<TtsListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onTtsStart(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((TtsListener) obj).ttsStart(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTtsEnd(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((TtsListener) obj).ttsEnd(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTtsTimbreSetting(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        int type = 1;
        try {
            JSONObject json = new JSONObject(data);
            type = json.optInt("timbre_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((TtsListener) obj).onTtsTimbreSetting(type);
            }
        }
    }
}
