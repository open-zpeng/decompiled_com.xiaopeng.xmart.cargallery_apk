package com.xiaopeng.speech.protocol.node.fm;

import com.xiaopeng.speech.SpeechNode;
/* loaded from: classes.dex */
public class FMNode extends SpeechNode<FMListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmLocalOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FMListener) obj).onFmLocalOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmLocalOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FMListener) obj).onFmLocalOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmNetworkOn(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FMListener) obj).onFmNetworkOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmNetworkOff(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FMListener) obj).onFmNetworkOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmPlayChannel(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FMListener) obj).onFmPlayChannel(data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFmPlayChannelName(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FMListener) obj).onFmPlayChannelName(event, data);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayCollectFM(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((FMListener) obj).onPlayCollectFM();
            }
        }
    }
}
