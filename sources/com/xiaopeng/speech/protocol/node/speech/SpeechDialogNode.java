package com.xiaopeng.speech.protocol.node.speech;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.SpeechUtils;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class SpeechDialogNode extends SpeechNode<SpeechDialogListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseWindow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onCloseWindow(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenWindow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onOpenWindow(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenSceneGuideWindow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onOpenSceneGuideWindow(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseSceneGuideWindow(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onCloseSceneGuideWindow(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenSpeechSceneSetting(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onOpenSpeechSceneSetting();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseSpeechSceneSetting(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onCloseSpeechSceneSetting();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onOpenSuperDialogue(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onOpenSuperDialogue();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCloseSuperDialogue(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onCloseSuperDialogue();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRefreshUi(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        int type = -1;
        boolean state = false;
        try {
            if (SpeechUtils.isJson(data)) {
                JSONObject json = new JSONObject(data);
                type = json.optInt(BIConfig.PROPERTY.DATA_TYPE, -1);
                state = json.optBoolean("state", false);
            }
        } catch (Exception e) {
        }
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((SpeechDialogListener) obj).onRefreshUi(type, state);
            }
        }
    }
}
