package com.xiaopeng.speech.protocol.node.changba;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.node.changba.bean.ChangbaBean;
/* loaded from: classes.dex */
public class ChangbaNode extends SpeechNode<ChangbaListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onMusicChangbaSearch(String event, String data) {
        Object[] listenerList = this.mListenerList.collectCallbacks();
        ChangbaBean changbaBean = ChangbaBean.fromJson(data);
        if (listenerList != null) {
            for (Object obj : listenerList) {
                ((ChangbaListener) obj).onMusicChangbaSearch(changbaBean);
            }
        }
    }
}
