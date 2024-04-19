package com.xiaopeng.speech.protocol.node.avatar;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.jarvisproto.DMListening;
import com.xiaopeng.speech.jarvisproto.DMRecognized;
/* loaded from: classes.dex */
public class AvatarNode extends SpeechNode<AvatarListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onSilence(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        DMRecognized dmRecognized = DMRecognized.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onSilence(dmRecognized);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onListening(String event, String data) {
        DMListening dmListening = DMListening.fromJson(data);
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onListening(dmListening);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSpeaking(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onSpeaking();
            }
        }
    }

    protected void onStandby(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onStandby();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onUnderstanding(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onUnderstanding();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAvatarExpression(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onAvatarExpression(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetCustom(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onWidgetCustom(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetText(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onWidgetText(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetList(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onWidgetList(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetMedia(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onWidgetMedia(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetCard(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onWidgetCard(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onWidgetSearch(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onWidgetSearch(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onAvatarWakerupEnable(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onAvatarWakerupEnable(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onAvatarWakerupDisable(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((AvatarListener) obj).onAvatarWakerupDisable(data);
            }
        }
    }
}
