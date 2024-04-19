package com.xiaopeng.speech.protocol.node.oobe;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class OOBENode extends SpeechNode<OOBEListener> {
    private static final String TAG = "OOBENode";

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRecordResult(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onRecordResult(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRecordInput(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onRecordInput(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAddressSet(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onAddressSet(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSkip(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onSkip();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSearchError(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onSearchError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNetError(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onASRError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onASRError(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onASRError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onError(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onError(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onVolumeChange(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onVolumeChange(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNetWorkError(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((OOBEListener) obj).onNetWorkError();
            }
        }
    }
}
