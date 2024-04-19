package com.xiaopeng.speech.protocol.node.aiassistant;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechNode;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class AiAssistantNode extends SpeechNode<AiAssistantListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayVideo(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AiAssistantListener) obj).onPlayVideo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayVideoTtsend(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AiAssistantListener) obj).onPlayVideoTtsend();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMessageOpen(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AiAssistantListener) obj).onMessageOpen();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMessageClose(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AiAssistantListener) obj).onMessageClose();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenVideo(String event, String data) {
        try {
            JSONObject jsonData = new JSONObject(data);
            String videoName = jsonData.optString("video_name");
            String videoTag = jsonData.optString("video_tag");
            String videoCategory = jsonData.optString("video_category");
            Object[] listenerList = this.mListenerList.collectCallbacks();
            if (listenerList != null) {
                for (Object obj : listenerList) {
                    ((AiAssistantListener) obj).onOpenVideo(videoName, videoTag, videoCategory);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onXiaoPDance(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AiAssistantListener) obj).onXiaoPDance(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onXiaoPChangeClothe(String event, String data) {
        int type = 0;
        try {
            if (!TextUtils.isEmpty(data)) {
                JSONObject json = new JSONObject(data);
                type = json.optInt("skin");
            }
            Object[] listenerList = this.mListenerList.collectCallbacks();
            if (listenerList != null) {
                for (Object obj : listenerList) {
                    ((AiAssistantListener) obj).onXiaoPChangeClothe(type);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
